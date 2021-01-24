package colonialobfuscator.transforms;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import colonialobfuscator.utils.NodeUtils;
import colonialobfuscator.utils.OutputUtil;

import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.InstructionAdapter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import colonialobfuscator.utils.BytecodeHelper;
import colonialobfuscator.utils.NameGen;

public class StringEncryption implements ClassModifier {
	/*private static String FIELD_NAME = "string_store";
	private static String CALL_NAME = "unscramble";
	private static final String CALL_DESC = "(IILjava/lang/String;)Ljava/lang/String;";
	//private static final String XOR_Name = "a";
		
	public static ClassNode unscrambleClass;
	public static List<String> stringList;
		
	public static void Start() {//TODO add MassLagg(Run StringEncryption more times)
		
		FIELD_NAME = NameGen.String(10);
		CALL_NAME = NameGen.String(10);
		
		

		stringList = new ArrayList<>();
		unscrambleClass = new ClassNode();
		unscrambleClass.visit(52, ACC_PUBLIC | ACC_SUPER | ACC_SYNTHETIC, NameGen.colonial() + new Random().nextLong(), null, "java/lang/Object", null);
		unscrambleClass.visitField(ACC_PUBLIC | ACC_STATIC, FIELD_NAME, "[Ljava/lang/String;", null, null);

	}
*/
	private static String XOR(int i, int j, String string, int k, int l, char[] s) {
		StringBuilder sb = new StringBuilder();
		int i1 = 0;
		for(char c : string.toCharArray()) {
			sb.append((char)((((c ^ s[i1 % s.length]) ^ (i ^ k + i1)) ^ j) ^ l));
			i1++;
		}
		return sb.toString();
	}
	
	private static String EncryptKey(String s, char[] b) {
		 final char[] charArray = s.toCharArray();
	        final int i = charArray.length;
	        for (int n = 0; i > n; ++n) {
	            final int n2 = n;
	            final char c = charArray[n2];
	            char c2 = '\0';
	            switch (n % 7) {
	                case 0: {
	                    c2 = b[0];
	                    break;
	                }
	                case 1: {
	                    c2 = b[1];
	                    break;
	                }
	                case 2: {
	                    c2 = b[2];
	                    break;
	                }
	                case 3: {
	                    c2 = b[3];
	                    break;
	                }
	                case 4: {
	                    c2 = b[4];
	                    break;
	                }
	                case 5: {
	                    c2 = b[5];
	                    break;
	                }
	                default: {
	                    c2 = b[6];
	                    break;
	                }
	            }
	            charArray[n2] = (char)(c ^ c2);
	        }
	        return new String(charArray).intern();
	}
	
	
	@Override
	public void modify(ClassNode node) {
	try {
		if(node.methods != null && node.methods.size() > 0) {
			
			String key = NameGen.colonial() + NameGen.String(100);
			Random ran = new Random();
			char[] key2 = new char[] {(char) ran.nextInt(126), (char) ran.nextInt(126), (char) ran.nextInt(126)
					, (char) ran.nextInt(126), (char) ran.nextInt(126), (char) ran.nextInt(126), (char) ran.nextInt(126)};
			
	//		String NAME2 = NameGen.colonial() + NameGen.String(2);
			String NAME3 = NameGen.colonial() + NameGen.String(1);
		    
			
			
			
			{
				FieldVisitor fieldVisitor = node.visitField(ACC_STATIC, NAME3, "[C", null, null);
				fieldVisitor.visitEnd();
				}
			
			String name = NameGen.String(4);
			for(MethodNode mn : node.methods) {
				BytecodeHelper.<LdcInsnNode>forEach(mn.instructions, LdcInsnNode.class, ldc -> {
					if (ldc.cst instanceof String) {
						int k1 = new Random().nextInt();
						int k2 = new Random().nextInt();
						int k3 = new Random().nextInt();
						int k4 = new Random().nextInt();
						String s = (String)ldc.cst;
						InsnList il = new InsnList();
						il.add(new LdcInsnNode(k1));
						il.add(new LdcInsnNode(k2));
						il.add(new LdcInsnNode(XOR(k1, k2, s, k3, k4, key.toCharArray())));
						il.add(new LdcInsnNode(k3));
						il.add(new LdcInsnNode(k4));
					//	methodVisitor.visitMethodInsn(INVOKESTATIC, "test/TEST", "XOR", "(IILjava/lang/String;II)Ljava/lang/String;", false);
					//	methodVisitor.visitInsn(POP);
						il.add(new MethodInsnNode(INVOKESTATIC, node.name, name, "(IILjava/lang/String;II)Ljava/lang/String;", false));
						mn.instructions.insert(ldc, il);
						mn.instructions.remove(ldc);
					}
				});
			}
			
			{
			MethodVisitor methodVisitor = node.visitMethod(ACC_PRIVATE | ACC_STATIC, name, "(IILjava/lang/String;II)Ljava/lang/String;", null, null);
			methodVisitor.visitCode();
			Label label0 = new Label();
			methodVisitor.visitLabel(label0);
			methodVisitor.visitTypeInsn(NEW, "java/lang/StringBuilder");
			methodVisitor.visitInsn(DUP);
			methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
			methodVisitor.visitVarInsn(ASTORE, 5);
			Label label1 = new Label();
			methodVisitor.visitLabel(label1);
			methodVisitor.visitInsn(ICONST_0);
			methodVisitor.visitVarInsn(ISTORE, 6);
			Label label2 = new Label();
			methodVisitor.visitLabel(label2);
			methodVisitor.visitVarInsn(ALOAD, 2);
			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "toCharArray", "()[C", false);
			methodVisitor.visitInsn(DUP);
			methodVisitor.visitVarInsn(ASTORE, 10);
			methodVisitor.visitInsn(ARRAYLENGTH);
			methodVisitor.visitVarInsn(ISTORE, 9);
			methodVisitor.visitInsn(ICONST_0);
			methodVisitor.visitVarInsn(ISTORE, 8);
			Label label3 = new Label();
			methodVisitor.visitJumpInsn(GOTO, label3);
			Label label4 = new Label();
			methodVisitor.visitLabel(label4);
			methodVisitor.visitFrame(Opcodes.F_FULL, 11, new Object[] {Opcodes.INTEGER, Opcodes.INTEGER, "java/lang/String", Opcodes.INTEGER, Opcodes.INTEGER, "java/lang/StringBuilder", Opcodes.INTEGER, Opcodes.TOP, Opcodes.INTEGER, Opcodes.INTEGER, "[C"}, 0, new Object[] {});
			methodVisitor.visitVarInsn(ALOAD, 10);
			methodVisitor.visitVarInsn(ILOAD, 8);
			methodVisitor.visitInsn(CALOAD);
			methodVisitor.visitVarInsn(ISTORE, 7);
			Label label5 = new Label();
			methodVisitor.visitLabel(label5);
			methodVisitor.visitVarInsn(ALOAD, 5);
			methodVisitor.visitVarInsn(ILOAD, 7);
			methodVisitor.visitFieldInsn(GETSTATIC, node.name, NAME3, "[C");
			methodVisitor.visitVarInsn(ILOAD, 6);
			methodVisitor.visitFieldInsn(GETSTATIC, node.name, NAME3, "[C");
			methodVisitor.visitInsn(ARRAYLENGTH);
			methodVisitor.visitInsn(IREM);
			methodVisitor.visitInsn(CALOAD);
			methodVisitor.visitInsn(IXOR);
			methodVisitor.visitVarInsn(ILOAD, 0);
			methodVisitor.visitVarInsn(ILOAD, 3);
			methodVisitor.visitVarInsn(ILOAD, 6);
			methodVisitor.visitInsn(IADD);
			methodVisitor.visitInsn(IXOR);
			methodVisitor.visitInsn(IXOR);
			methodVisitor.visitVarInsn(ILOAD, 1);
			methodVisitor.visitInsn(IXOR);
			methodVisitor.visitVarInsn(ILOAD, 4);
			methodVisitor.visitInsn(IXOR);
			methodVisitor.visitInsn(I2C);
			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(C)Ljava/lang/StringBuilder;", false);
			methodVisitor.visitInsn(POP);
			Label label6 = new Label();
			methodVisitor.visitLabel(label6);
			methodVisitor.visitIincInsn(6, 1);
			Label label7 = new Label();
			methodVisitor.visitLabel(label7);
			methodVisitor.visitIincInsn(8, 1);
			methodVisitor.visitLabel(label3);
			methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			methodVisitor.visitVarInsn(ILOAD, 8);
			methodVisitor.visitVarInsn(ILOAD, 9);
			methodVisitor.visitJumpInsn(IF_ICMPLT, label4);
			Label label8 = new Label();
			methodVisitor.visitLabel(label8);
			methodVisitor.visitVarInsn(ALOAD, 5);
			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
			methodVisitor.visitInsn(ARETURN);
			Label label9 = new Label();
			methodVisitor.visitLabel(label9);
			methodVisitor.visitMaxs(5, 11);
			methodVisitor.visitEnd();
		}
			{
				{
			    	//MethodVisitor methodVisitor = node.visitMethod(ACC_PRIVATE | ACC_STATIC, NAME2, "()V", null, null);
					MethodNode methodVisitor = new MethodNode();
					methodVisitor.visitCode();
					methodVisitor.visitLdcInsn(EncryptKey(key, key2));
					methodVisitor.visitInsn(ICONST_M1);
					Label label0 = new Label();
					methodVisitor.visitJumpInsn(GOTO, label0);
					Label label1 = new Label();
					methodVisitor.visitLabel(label1);
					methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/lang/String"});
					
					
					methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "toCharArray", "()[C", false);
					methodVisitor.visitFieldInsn(PUTSTATIC, node.name, NAME3, "[C");
					
				//	methodVisitor.visitVarInsn(ASTORE, 1);
					
					Label label2 = new Label();
					methodVisitor.visitJumpInsn(GOTO, label2);
					methodVisitor.visitLabel(label0);
					methodVisitor.visitFrame(Opcodes.F_FULL, 0, new Object[] {}, 2, new Object[] {"java/lang/String", Opcodes.INTEGER});
					methodVisitor.visitInsn(SWAP);
					methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "toCharArray", "()[C", false);
					methodVisitor.visitInsn(DUP);
					methodVisitor.visitInsn(ARRAYLENGTH);
					methodVisitor.visitInsn(SWAP);
					methodVisitor.visitInsn(ICONST_0);
					methodVisitor.visitVarInsn(ISTORE, 0);
					Label label3 = new Label();
					methodVisitor.visitJumpInsn(GOTO, label3);
					Label label4 = new Label();
					methodVisitor.visitLabel(label4);
					methodVisitor.visitFrame(Opcodes.F_FULL, 1, new Object[] {Opcodes.INTEGER}, 3, new Object[] {Opcodes.INTEGER, Opcodes.INTEGER, "[C"});
					methodVisitor.visitInsn(DUP);
					methodVisitor.visitVarInsn(ILOAD, 0);
					Label label5 = new Label();
					methodVisitor.visitLabel(label5);
					methodVisitor.visitFrame(Opcodes.F_FULL, 1, new Object[] {Opcodes.INTEGER}, 5, new Object[] {Opcodes.INTEGER, Opcodes.INTEGER, "[C", "[C", Opcodes.INTEGER});
					methodVisitor.visitInsn(DUP2);
					methodVisitor.visitInsn(CALOAD);
					methodVisitor.visitVarInsn(ILOAD, 0);
					methodVisitor.visitIntInsn(BIPUSH, 7);
					methodVisitor.visitInsn(IREM);
					Label label6 = new Label();
					Label label7 = new Label();
					Label label8 = new Label();
					Label label9 = new Label();
					Label label10 = new Label();
					Label label11 = new Label();
					Label label12 = new Label();
					methodVisitor.visitTableSwitchInsn(0, 5, label12, new Label[] { label6, label7, label8, label9, label10, label11 });
					methodVisitor.visitLabel(label6);
					methodVisitor.visitFrame(Opcodes.F_FULL, 1, new Object[] {Opcodes.INTEGER}, 6, new Object[] {Opcodes.INTEGER, Opcodes.INTEGER, "[C", "[C", Opcodes.INTEGER, Opcodes.INTEGER});
					methodVisitor.visitIntInsn(BIPUSH, key2[0]);
					Label label13 = new Label();
					methodVisitor.visitJumpInsn(GOTO, label13);
					methodVisitor.visitLabel(label7);
					methodVisitor.visitFrame(Opcodes.F_FULL, 1, new Object[] {Opcodes.INTEGER}, 6, new Object[] {Opcodes.INTEGER, Opcodes.INTEGER, "[C", "[C", Opcodes.INTEGER, Opcodes.INTEGER});
					methodVisitor.visitIntInsn(BIPUSH, key2[1]);
					methodVisitor.visitJumpInsn(GOTO, label13);
					methodVisitor.visitLabel(label8);
					methodVisitor.visitFrame(Opcodes.F_FULL, 1, new Object[] {Opcodes.INTEGER}, 6, new Object[] {Opcodes.INTEGER, Opcodes.INTEGER, "[C", "[C", Opcodes.INTEGER, Opcodes.INTEGER});
					methodVisitor.visitIntInsn(BIPUSH, key2[2]);
					methodVisitor.visitJumpInsn(GOTO, label13);
					methodVisitor.visitLabel(label9);
					methodVisitor.visitFrame(Opcodes.F_FULL, 1, new Object[] {Opcodes.INTEGER}, 6, new Object[] {Opcodes.INTEGER, Opcodes.INTEGER, "[C", "[C", Opcodes.INTEGER, Opcodes.INTEGER});
					methodVisitor.visitIntInsn(BIPUSH, key2[3]);
					methodVisitor.visitJumpInsn(GOTO, label13);
					methodVisitor.visitLabel(label10);
					methodVisitor.visitFrame(Opcodes.F_FULL, 1, new Object[] {Opcodes.INTEGER}, 6, new Object[] {Opcodes.INTEGER, Opcodes.INTEGER, "[C", "[C", Opcodes.INTEGER, Opcodes.INTEGER});
					methodVisitor.visitIntInsn(BIPUSH, key2[4]);
					methodVisitor.visitJumpInsn(GOTO, label13);
					methodVisitor.visitLabel(label11);
					methodVisitor.visitFrame(Opcodes.F_FULL, 1, new Object[] {Opcodes.INTEGER}, 6, new Object[] {Opcodes.INTEGER, Opcodes.INTEGER, "[C", "[C", Opcodes.INTEGER, Opcodes.INTEGER});
					methodVisitor.visitIntInsn(BIPUSH, key2[5]);
					methodVisitor.visitJumpInsn(GOTO, label13);
					methodVisitor.visitLabel(label12);
					methodVisitor.visitFrame(Opcodes.F_FULL, 1, new Object[] {Opcodes.INTEGER}, 6, new Object[] {Opcodes.INTEGER, Opcodes.INTEGER, "[C", "[C", Opcodes.INTEGER, Opcodes.INTEGER});
					methodVisitor.visitIntInsn(BIPUSH, key2[6]);
					methodVisitor.visitLabel(label13);
					methodVisitor.visitFrame(Opcodes.F_FULL, 1, new Object[] {Opcodes.INTEGER}, 7, new Object[] {Opcodes.INTEGER, Opcodes.INTEGER, "[C", "[C", Opcodes.INTEGER, Opcodes.INTEGER, Opcodes.INTEGER});
					methodVisitor.visitInsn(IXOR);
					methodVisitor.visitInsn(I2C);
					methodVisitor.visitInsn(CASTORE);
					methodVisitor.visitIincInsn(0, 1);
					methodVisitor.visitLabel(label3);
					methodVisitor.visitFrame(Opcodes.F_FULL, 1, new Object[] {Opcodes.INTEGER}, 3, new Object[] {Opcodes.INTEGER, Opcodes.INTEGER, "[C"});
					methodVisitor.visitInsn(SWAP);
					methodVisitor.visitInsn(DUP_X1);
					methodVisitor.visitVarInsn(ILOAD, 0);
					methodVisitor.visitJumpInsn(IF_ICMPGT, label4);
					methodVisitor.visitTypeInsn(NEW, "java/lang/String");
					methodVisitor.visitInsn(DUP_X1);
					methodVisitor.visitInsn(SWAP);
					methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/String", "<init>", "([C)V", false);
					methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "intern", "()Ljava/lang/String;", false);
					methodVisitor.visitInsn(SWAP);
					methodVisitor.visitInsn(POP);
					methodVisitor.visitInsn(SWAP);
					methodVisitor.visitInsn(POP);
					methodVisitor.visitJumpInsn(GOTO, label1);
					methodVisitor.visitLabel(label2);
					methodVisitor.visitFrame(Opcodes.F_CHOP,1, null, 0, null);
				//	methodVisitor.visitVarInsn(ALOAD, 1);
				//	methodVisitor.visitInsn(RETURN);
					
					 MethodNode clInit = NodeUtils.getMethod(node, "<clinit>");
		             if (clInit == null) {
		                 clInit = new MethodNode(Opcodes.ACC_STATIC, "<clinit>", "()V", null, new String[0]);
		                 node.methods.add(clInit);
		             }
		             if (clInit.instructions == null)
		                 clInit.instructions = new InsnList();
		             
		             
		             if (clInit.instructions == null || clInit.instructions.getFirst() == null) {
		                 clInit.instructions.add(methodVisitor.instructions);
		                 clInit.instructions.add(new InsnNode(Opcodes.RETURN));
		             } else {
		                 clInit.instructions.insertBefore(clInit.instructions.getFirst(), methodVisitor.instructions);
		             }
					
					
				}
			}
			
			
		}
	} catch (Exception ex) {
		ex.printStackTrace();
	}
	}
	/*
	public static void END() {
		createUnscramble();
		try {
			createStaticConstructor(unscrambleClass);


		OutputUtil.ToAdd.put(unscrambleClass.name, unscrambleClass);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void buildStringList(MethodNode mn) {
		BytecodeHelper.<LdcInsnNode>forEach(mn.instructions, LdcInsnNode.class, ldc -> {
			if (ldc.cst instanceof String && !stringList.contains(ldc.cst)) {
				stringList.add((String) ldc.cst);
			}
		});
	}

	private void scramble(MethodNode mn) {
		List<LdcInsnNode> ldcNodes = new LinkedList<>();
		BytecodeHelper.forEach(mn.instructions, LdcInsnNode.class, ldcNodes::add);
		for (LdcInsnNode node : ldcNodes) {
			if (node.cst instanceof String && stringList.size() <= 18000) {
				int index = stringList.indexOf(node.cst);
				if (index == -1)
					continue;
				MethodInsnNode call = new MethodInsnNode(Opcodes.INVOKESTATIC, unscrambleClass.name, CALL_NAME, CALL_DESC, false);
				int key = new Random().nextInt();
				int key2 = new Random().nextInt();
				byte[] b = new byte[new Random().nextInt(4) + 4];
				new Random().nextBytes(b);
				String stringkey = new String(b);
				mn.instructions.set(node, call);
				mn.instructions.insertBefore(call, BytecodeHelper.newIntegerNode((index ^ key) ^ key2));
				mn.instructions.insertBefore(call, BytecodeHelper.newIntegerNode(key2));
				mn.instructions.insertBefore(call, new InsnNode(Opcodes.IXOR));
				mn.instructions.insertBefore(call, BytecodeHelper.newIntegerNode(key ^ stringkey.hashCode()));
				mn.instructions.insertBefore(call, new LdcInsnNode(stringkey));
			}
		}
	}
	
	private static void createUnscramble() {
		MethodVisitor mv = unscrambleClass.visitMethod(ACC_PUBLIC | ACC_STATIC, CALL_NAME, CALL_DESC, null, null);
		mv.visitCode();
		mv.visitFieldInsn(GETSTATIC, unscrambleClass.name, FIELD_NAME, "[Ljava/lang/String;");
		mv.visitVarInsn(ILOAD, 0);
		mv.visitVarInsn(ILOAD, 1);
		mv.visitVarInsn(ALOAD, 2);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "hashCode", "()I", false);
		mv.visitInsn(IXOR);
		mv.visitInsn(IXOR);
		mv.visitInsn(AALOAD);




	//	mv.visitMethodInsn(Opcodes.INVOKESTATIC, unscrambleClass.name, XOR_Name, mn.desc, false);

		mv.visitInsn(ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
	}
	private static String XOR(String s, int i) {
		StringBuilder b = new StringBuilder();
		for(char c : s.toCharArray()) {
		b.append((char) c ^ i);
		}
		return b.toString();
	}
	private static void createStaticConstructor(ClassNode owner) {
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

			builder.aconst(new String(Objects.requireNonNull(aesEncrypt(stringList.get(i), Key)).getBytes(StandardCharsets.UTF_8)));
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
	            byte[] key = secret.getBytes(StandardCharsets.UTF_8);
	            MessageDigest sha = MessageDigest.getInstance("SHA-256");
	            key = sha.digest(key);
	            key = Arrays.copyOf(key, 16);
	            secretKey = new SecretKeySpec(key, "AES");
	            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	            return Base64.getEncoder().encodeToString(cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8)));
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
	    }*/
}