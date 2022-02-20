package colonialobfuscator.transforms;

import static colonialobfuscator.guis.SettingsPanel.namesLenghtField;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import colonialobfuscator.utils.NameGen;

public class ModifierBooleans implements ClassModifier {
	@Override
	public void modify(ClassNode classNode) {
		boolean used = false;
		String MNAME = NameGen.colonial() + NameGen.String(2 + RANDOM.nextInt(5));
			String desc = "(I)I";
		for (MethodNode method : classNode.methods) {
			for (AbstractInsnNode insnNode : method.instructions.toArray()) {
				if ((insnNode instanceof MethodInsnNode && ((MethodInsnNode) insnNode).desc.endsWith("Z")) || (insnNode instanceof FieldInsnNode && ((FieldInsnNode) insnNode).desc.equals("Z"))) {
					used = true;
					InsnList list = new InsnList();

						//	list.add(new LdcInsnNode(0));
						//	list.add(new InsnNode(Opcodes.IXOR));

						list.add(new MethodInsnNode(INVOKESTATIC, classNode.name, MNAME, desc));

					if (insnNode.getOpcode() == Opcodes.PUTFIELD || insnNode.getOpcode() == Opcodes.PUTSTATIC)
						method.instructions.insertBefore(insnNode, list);
					else
						method.instructions.insert(insnNode, list);
				}
			}
		}
		if(used) {
			MethodVisitor mv = classNode.visitMethod(ACC_PUBLIC | ACC_STATIC, MNAME, desc, null, null);
			mv.visitCode();
			mv.visitVarInsn(ILOAD, 0);
			mv.visitInsn(IRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
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
