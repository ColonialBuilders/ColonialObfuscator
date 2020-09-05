package colonialobfuscator.transforms;

import static colonialobfuscator.guis.SettingsPanel.massLaggEnabledCheckBox;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.InstructionAdapter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import colonialobfuscator.utils.BytecodeHelper;
import colonialobfuscator.utils.NameGen;

public class StringEncryption implements ClassModifier {
	public static ArrayList<String> Methods = new ArrayList<String>();
	boolean MoreLagg = massLaggEnabledCheckBox.isSelected();
	private static final String FIELD_NAME = "string_store";
	private static final String CALL_NAME = "unscramble";
	private static final String CALL_DESC = "(I)Ljava/lang/String;";
	
	
	private static final String CHIPHER = NameGen.colonial();
	
	public static ClassNode unscrambleClass;
	public static List<String> stringList;
	public static void Start() {
		stringList = new ArrayList<>();
		unscrambleClass = new ClassNode();
		unscrambleClass.visit(52, ACC_PUBLIC | ACC_SUPER | ACC_SYNTHETIC, NameGen.colonial() + new Random().nextLong(), null, "java/lang/Object", null);
		unscrambleClass.visitField(ACC_PUBLIC | ACC_STATIC, FIELD_NAME, "[Ljava/lang/String;", null, null);
	}
	
	@Override
	public void modify(ClassNode node) {
		node.methods.stream().forEach(this::buildStringList);
		node.methods.stream().forEach(mn -> scramble(node, mn));
	}
	public static void END() {
		createUnscramble();
		try {
			createStaticConstructor(unscrambleClass);
		} catch (Exception ex) {
		}
	}

	private void buildStringList(MethodNode mn) {
		BytecodeHelper.forEach(mn.instructions, LdcInsnNode.class, ldc -> {
			if (ldc.cst instanceof String && !stringList.contains(ldc.cst)) {
				stringList.add((String) ldc.cst);
			}
		});
	}

	private void scramble(ClassNode cn, MethodNode mn) {
		List<LdcInsnNode> ldcNodes = new LinkedList<>();
		BytecodeHelper.forEach(mn.instructions, LdcInsnNode.class, ldcNodes::add);
		for (LdcInsnNode node : ldcNodes) {
			if (node.cst instanceof String && stringList.size() <= 18000) {
				int index = stringList.indexOf(node.cst);
				if (index == -1)
					continue;
				MethodInsnNode call = new MethodInsnNode(Opcodes.INVOKESTATIC, unscrambleClass.name, CALL_NAME, CALL_DESC, false);
				mn.instructions.set(node, call);
				mn.instructions.insertBefore(call, BytecodeHelper.newIntegerNode(index));
			}
		}
	}
	
	private static void createUnscramble() {
		MethodVisitor mv = unscrambleClass.visitMethod(ACC_PUBLIC | ACC_STATIC, CALL_NAME, CALL_DESC, null, null);
		mv.visitCode();
		mv.visitFieldInsn(GETSTATIC, unscrambleClass.name, FIELD_NAME, "[Ljava/lang/String;");
		mv.visitVarInsn(ILOAD, 0);
		mv.visitInsn(AALOAD);
		mv.visitInsn(ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
	}

	private static void createStaticConstructor(ClassNode owner) throws UnsupportedEncodingException {
		MethodNode original = BytecodeHelper.getMethod(owner, "<clinit>", "()V");
		MethodVisitor mv = owner.visitMethod(Opcodes.ACC_STATIC, "<clinit>", "()V", null, null);
		// generate instructions
		
		String NAME2 = NameGen.String(10);
		owner.methods.add(normalMethod(NAME2));
		InstructionAdapter builder = new InstructionAdapter(mv);
		builder.iconst(stringList.size());
		builder.newarray(Type.getType(String.class));
		for (int i = 0; i < stringList.size(); i++) {
			builder.dup();
			builder.iconst(i);
			
			String Key = NameGen.String(10);
			
			builder.aconst(new String(aesEncrypt(stringList.get(i), Key).getBytes("UTF-8")));
            builder.aconst(Key);
			builder.visitMethodInsn(Opcodes.INVOKESTATIC, unscrambleClass.name, NAME2, "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", false);
			
			
			builder.astore(InstructionAdapter.OBJECT_TYPE);
		}
		builder.putstatic(unscrambleClass.name, FIELD_NAME, "[Ljava/lang/String;");
		// merge with original if it exists
		if (original != null) {
			// original should already end with RETURN
			owner.methods.remove(original);
			original.instructions.accept(builder);
		} else {
			builder.areturn(Type.VOID_TYPE);
		}
		
	
	}
	 public static String aesEncrypt(String msg, String secret) {
	        try {
	            SecretKeySpec secretKey;
	            byte[] key = secret.getBytes("UTF-8");
	            MessageDigest sha = MessageDigest.getInstance("SHA-256");
	            key = sha.digest(key);
	            key = Arrays.copyOf(key, 16);
	            secretKey = new SecretKeySpec(key, "AES");
	            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	            return Base64.getEncoder().encodeToString(cipher.doFinal(msg.getBytes("UTF-8")));
	        } catch (Throwable t) {
	            // t.printStackTrace();
	        }
	        return null;
	    }	
	  public static MethodNode normalMethod(String decryptionMethodName) {//https://github.com/ItzSomebody/radon/blob/0958c434173b38f8b0e0a070b2985d338b46644e/src/me/itzsomebody/radon/methods/StringEncryption.java
	        MethodNode methodNode = new MethodNode(ACC_PUBLIC + ACC_STATIC + ACC_SYNTHETIC + ACC_BRIDGE, decryptionMethodName, "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", null, null);
	        methodNode.visitCode();
	        Label l0 = new Label();
	        Label l1 = new Label();
	        Label l2 = new Label();
	        methodNode.visitTryCatchBlock(l0, l1, l2, "java/lang/Throwable");
	        methodNode.visitLabel(l0);
	        methodNode.visitInsn(ACONST_NULL);
	        methodNode.visitVarInsn(ASTORE, 4);
	        Label l3 = new Label();
	        methodNode.visitLabel(l3);
	        methodNode.visitVarInsn(ALOAD, 1);
	        methodNode.visitLdcInsn("UTF-8");
	        methodNode.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "getBytes", "(Ljava/lang/String;)[B", false);
	        methodNode.visitVarInsn(ASTORE, 3);
	        Label l4 = new Label();
	        methodNode.visitLabel(l4);
	        methodNode.visitLdcInsn("SHA-256");
	        methodNode.visitMethodInsn(INVOKESTATIC, "java/security/MessageDigest", "getInstance", "(Ljava/lang/String;)Ljava/security/MessageDigest;", false);
	        methodNode.visitVarInsn(ASTORE, 4);
	        Label l5 = new Label();
	        methodNode.visitLabel(l5);
	        methodNode.visitVarInsn(ALOAD, 4);
	        methodNode.visitVarInsn(ALOAD, 3);
	        methodNode.visitMethodInsn(INVOKEVIRTUAL, "java/security/MessageDigest", "digest", "([B)[B", false);
	        methodNode.visitVarInsn(ASTORE, 3);
	        Label l6 = new Label();
	        methodNode.visitLabel(l6);
	        methodNode.visitVarInsn(ALOAD, 3);
	        methodNode.visitIntInsn(BIPUSH, 16);
	        methodNode.visitMethodInsn(INVOKESTATIC, "java/util/Arrays", "copyOf", "([BI)[B", false);
	        methodNode.visitVarInsn(ASTORE, 3);
	        Label l7 = new Label();
	        methodNode.visitLabel(l7);
	        methodNode.visitTypeInsn(NEW, "javax/crypto/spec/SecretKeySpec");
	        methodNode.visitInsn(DUP);
	        methodNode.visitVarInsn(ALOAD, 3);
	        methodNode.visitLdcInsn("AES");
	        methodNode.visitMethodInsn(INVOKESPECIAL, "javax/crypto/spec/SecretKeySpec", "<init>", "([BLjava/lang/String;)V", false);
	        methodNode.visitVarInsn(ASTORE, 2);
	        Label l8 = new Label();
	        methodNode.visitLabel(l8);
	        methodNode.visitLdcInsn("AES/ECB/PKCS5PADDING");
	        methodNode.visitMethodInsn(INVOKESTATIC, "javax/crypto/Cipher", "getInstance", "(Ljava/lang/String;)Ljavax/crypto/Cipher;", false);
	        methodNode.visitVarInsn(ASTORE, 5);
	        Label l9 = new Label();
	        methodNode.visitLabel(l9);
	        methodNode.visitVarInsn(ALOAD, 5);
	        methodNode.visitInsn(ICONST_2);
	        methodNode.visitVarInsn(ALOAD, 2);
	        methodNode.visitMethodInsn(INVOKEVIRTUAL, "javax/crypto/Cipher", "init", "(ILjava/security/Key;)V", false);
	        Label l10 = new Label();
	        methodNode.visitLabel(l10);
	        methodNode.visitTypeInsn(NEW, "java/lang/String");
	        methodNode.visitInsn(DUP);
	        methodNode.visitVarInsn(ALOAD, 5);
	        methodNode.visitMethodInsn(INVOKESTATIC, "java/util/Base64", "getDecoder", "()Ljava/util/Base64$Decoder;", false);
	        methodNode.visitVarInsn(ALOAD, 0);
	        methodNode.visitMethodInsn(INVOKEVIRTUAL, "java/util/Base64$Decoder", "decode", "(Ljava/lang/String;)[B", false);
	        methodNode.visitMethodInsn(INVOKEVIRTUAL, "javax/crypto/Cipher", "doFinal", "([B)[B", false);
	        methodNode.visitMethodInsn(INVOKESPECIAL, "java/lang/String", "<init>", "([B)V", false);
	        methodNode.visitLabel(l1);
	        methodNode.visitInsn(ARETURN);
	        methodNode.visitLabel(l2);
	        methodNode.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"java/lang/Throwable"});
	        methodNode.visitVarInsn(ASTORE, 2);
	        Label l11 = new Label();
	        methodNode.visitLabel(l11);
	        methodNode.visitInsn(ACONST_NULL);
	        methodNode.visitInsn(ARETURN);
	        Label l12 = new Label();
	        methodNode.visitLabel(l12);
	        methodNode.visitMaxs(5, 6);
	        methodNode.visitEnd();

	        return methodNode;
	    }
}