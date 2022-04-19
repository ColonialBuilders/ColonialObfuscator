package colonialobfuscator.transforms;

import static colonialobfuscator.guis.SettingsPanel.namesLenghtField;

import colonialobfuscator.utils.NodeUtils;
import colonialobfuscator.utils.OutputUtil;
import colonialobfuscator.utils.Utils;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import colonialobfuscator.utils.NameGen;

import java.util.Arrays;

public class ModifierBooleans implements ClassModifier {
	@Override
	public void modify(ClassNode classNode) {
		boolean used = false;
		String MNAME = NameGen.colonial() + NameGen.String(OutputUtil.namesLenght);
			String desc = "(II)I";
			int xor = RANDOM.nextInt();
			String FNAME1 = NameGen.String(OutputUtil.namesLenght);
		for (MethodNode method : classNode.methods) {
			for (AbstractInsnNode insnNode : method.instructions.toArray()) {
			/*	if ((insnNode instanceof MethodInsnNode && ((MethodInsnNode) insnNode).desc.endsWith("Z")) || (insnNode instanceof FieldInsnNode && ((FieldInsnNode) insnNode).desc.equals("Z"))) {
					used = true;
					InsnList list = new InsnList();
			//		method.instructions.remove(insnNode.getPrevious());
				//
			//		list.add(new LdcInsnNode(Utils.getInt(insnNode.getPrevious()) ^ xor));
						list.add(new MethodInsnNode(INVOKESTATIC, classNode.name, MNAME, desc));

					if (insnNode.getOpcode() == Opcodes.PUTFIELD || insnNode.getOpcode() == Opcodes.PUTSTATIC)
						method.instructions.insertBefore(insnNode, list);
					else
						method.instructions.insert(insnNode, list);*/
				//}
				if(insnNode.getOpcode() == ICONST_0 || insnNode.getOpcode() == ICONST_1) {
					used = true;
					InsnList list = new InsnList();
					int k2 = RANDOM.nextInt();
					list.add(new LdcInsnNode(Utils.getInt(insnNode) ^ (xor ^ k2)));
					list.add(new LdcInsnNode(k2));
					list.add(new MethodInsnNode(INVOKESTATIC, classNode.name, MNAME, desc));
					method.instructions.insert(insnNode, list);
					method.instructions.remove(insnNode);
				}
			}
		}
		if(used) {
			MethodVisitor mv = classNode.visitMethod(ACC_PUBLIC | ACC_STATIC, MNAME, desc, null, null);
			mv.visitCode();
			mv.visitVarInsn(ILOAD, 0);
			mv.visitFieldInsn(GETSTATIC, classNode.name, FNAME1, "I");
			mv.visitVarInsn(ILOAD, 1);
			mv.visitInsn(IXOR);
			mv.visitInsn(IXOR);
			mv.visitInsn(IRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
			classNode.fields.add(new FieldNode(ACC_STATIC, FNAME1, "I", null, null));
			MethodNode clInit = NodeUtils.getMethod(classNode, "<clinit>");
			if (clInit == null) {
				clInit = new MethodNode(Opcodes.ACC_STATIC, "<clinit>", "()V", null, new String[0]);
				classNode.methods.add(clInit);
			}
			if (clInit.instructions == null)
				clInit.instructions = new InsnList();


			InsnList instructions = new InsnList();
			int key = RANDOM.nextInt();
			instructions.add(new LdcInsnNode(xor ^ key));
			instructions.add(new LdcInsnNode(key));
			instructions.add(new InsnNode(IXOR));
			instructions.add(new FieldInsnNode(PUTSTATIC, classNode.name, FNAME1, "I"));

			if (clInit.instructions == null || clInit.instructions.getFirst() == null) {
				clInit.instructions.add(instructions);
				clInit.instructions.add(new InsnNode(Opcodes.RETURN));
			} else {
				clInit.instructions.insertBefore(clInit.instructions.getFirst(), instructions);
			}


		}



		/*for (MethodNode method : classNode.methods) {
			for (AbstractInsnNode insnNode : method.instructions.toArray()) {
				if ((insnNode instanceof MethodInsnNode && ((MethodInsnNode) insnNode).desc.endsWith("Z")) || (insnNode instanceof FieldInsnNode && ((FieldInsnNode) insnNode).desc.equals("Z"))) {
					InsnList list = new InsnList();
					switch (RANDOM.nextInt(4)) {
					case 0:
						list.add(new LdcInsnNode(RANDOM.nextInt(Short.MAX_VALUE) * 2 + 1));
						list.add(new InsnNode(Opcodes.IAND));
						break;
					case 1:
						list.add(new LdcInsnNode(0));
						list.add(new InsnNode(Opcodes.IXOR));
						break;
					case 2:
						list.add(new LdcInsnNode(RANDOM.nextInt(Short.MAX_VALUE) + 1));
						list.add(new InsnNode(Opcodes.IREM));
						break;
					case 3:
						int i = RANDOM.nextInt(7);
						list.add(new LdcInsnNode(i));
						list.add(new InsnNode(Opcodes.ISHL));
						list.add(new LdcInsnNode(1 << i));
						list.add(new InsnNode(Opcodes.IDIV));
						break;
					}

					if (insnNode.getOpcode() == Opcodes.PUTFIELD || insnNode.getOpcode() == Opcodes.PUTSTATIC)
						method.instructions.insertBefore(insnNode, list);
					else
						method.instructions.insert(insnNode, list);
				}
			}
		}*/
	}
}
