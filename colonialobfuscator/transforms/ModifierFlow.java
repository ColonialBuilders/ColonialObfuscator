package colonialobfuscator.transforms;

import colonialobfuscator.utils.NameGen;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Random;

public class ModifierFlow implements ClassModifier {
	@Override
	public void modify(ClassNode node) {
		for (MethodNode method : node.methods) {
			if (!method.name.startsWith("<")) {
				for (AbstractInsnNode insnNode : method.instructions.toArray()) {

					if (insnNode.getOpcode() == Opcodes.DUP ||
							insnNode.getOpcode() == Opcodes.POP ||
							insnNode.getOpcode() == Opcodes.SWAP ||
							insnNode.getOpcode() == Opcodes.FSUB ||
							insnNode.getOpcode() == Opcodes.ISUB ||
							insnNode.getOpcode() == Opcodes.DSUB ||
							insnNode.getOpcode() == Opcodes.ATHROW
					) {
						for (int i = 0; i < 1 + RANDOM.nextInt(5); i++) {
							method.instructions.insertBefore(insnNode,
									new LdcInsnNode(NameGen.String(new Random().nextInt(5))));
							method.instructions.insertBefore(insnNode, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/String", "length", "()I", false));
							if (RANDOM.nextBoolean()) {
								method.instructions.insertBefore(insnNode, new InsnNode(Opcodes.DUP));
			//					for (int i2 = 0; i2 < 2 + random.nextInt(5); i2++) {
			//						method.instructions.insert(new InsnNode(Opcodes.SWAP));
			//					}
								method.instructions.insertBefore(insnNode, new InsnNode(Opcodes.POP2));
							} else {
								method.instructions.insertBefore(insnNode, new InsnNode(Opcodes.DUP));
								method.instructions.insertBefore(insnNode, new InsnNode(Opcodes.POP));
								method.instructions.insertBefore(insnNode, new InsnNode(Opcodes.POP));
							}
						}
					}

					// NOP
					//if (RANDOM.nextInt(144) == 0)
					//	method.instructions.insertBefore(insnNode, new InsnNode(Opcodes.NOP));
					// DUP SWAP
					if (insnNode.getOpcode() == Opcodes.DUP) {//Anti JDGUI
						//	method.instructions.insert(insnNode, new InsnNode(Opcodes.SWAP));
						method.instructions.insert(new InsnNode(Opcodes.POP2));
						method.instructions.insert(new LdcInsnNode(NameGen.String(1)));
						method.instructions.insert(new InsnNode(Opcodes.POP));
						if (RANDOM.nextBoolean()) {
							for (int i = 0; i < 2 + RANDOM.nextInt(5); i++) {
								method.instructions.insert(new InsnNode(Opcodes.SWAP));
							}
						}
						//method.instructions.insert(new InsnNode(Opcodes.SWAP));
						if (RANDOM.nextBoolean()) {
							method.instructions.insert(new InsnNode(Opcodes.POP));
						} else {
							method.instructions.insert(new InsnNode(Opcodes.DUP));
							method.instructions.insert(new InsnNode(Opcodes.POP2));

						}
						method.instructions.insert(new LdcInsnNode(NameGen.String(1)));
						method.instructions.insert(new LdcInsnNode(NameGen.String(1)));
						method.instructions.insert(new LdcInsnNode(NameGen.String(1)));

					}
				}
			}
		}
	}
}
