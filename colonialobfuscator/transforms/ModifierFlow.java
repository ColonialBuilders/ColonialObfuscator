package colonialobfuscator.transforms;

import static colonialobfuscator.guis.SettingsPanel.namesLenghtField;

import java.util.Random;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodNode;

import colonialobfuscator.utils.NameGen;
import colonialobfuscator.utils.NodeUtils;

public class ModifierFlow implements ClassModifier {

	@Override
	public void modify(ClassNode node) {
		FieldNode f = new FieldNode(ACC_STATIC | ACC_FINAL, NameGen.colonial() + NameGen.String(Integer.parseInt(namesLenghtField.getText())), "I", null, null);
		node.fields.add(f);

		for (MethodNode method : node.methods) {
			if(!method.name.startsWith("<")) {
			if(!StringEncryption.Methods.contains(method.name)) {
			for (AbstractInsnNode insnNode : method.instructions.toArray()) {

				//https://github.com/superblaubeere27/obfuscator/blob/master/obfuscator-core/src/main/java/me/superblaubeere27/jobf/processors/flowObfuscation/FlowObfuscator.java
				if (insnNode instanceof JumpInsnNode && insnNode.getOpcode() == Opcodes.GOTO) {
					JumpInsnNode insnNode2 = (JumpInsnNode) insnNode;
					final InsnList insnList = new InsnList();
					insnList.add(ifGoto(insnNode2.label, method, Type.getReturnType(method.desc), f, node));
					method.instructions.insert(insnNode, insnList);
					method.instructions.remove(insnNode);
				}

				// NOP
				if (RANDOM.nextInt(144) == 0)
					method.instructions.insertBefore(insnNode, new InsnNode(Opcodes.NOP));
				// DUP SWAP
				if (insnNode.getOpcode() == Opcodes.DUP)
					method.instructions.insert(insnNode, new InsnNode(Opcodes.SWAP));
			}
		}
	}
		}
	}
	static Random random = new Random();

	//https://github.com/superblaubeere27/obfuscator/blob/master/obfuscator-core/src/main/java/me/superblaubeere27/jobf/processors/flowObfuscation/FlowObfuscator.java
	private static InsnList ifGoto(LabelNode label, MethodNode methodNode, Type returnType, FieldNode f, ClassNode node) {
		InsnList insnList;

		int i = random.nextInt(14);

		insnList = generateIfGoto(i, label, f, node);

		if (methodNode.name.equals("<init>")) {
			insnList.add(new InsnNode(Opcodes.ACONST_NULL));
			insnList.add(new InsnNode(Opcodes.ATHROW));
		} else {
			if (returnType.getSize() != 0) insnList.add(NodeUtils.nullValueForType(returnType));
			insnList.add(new InsnNode(returnType.getOpcode(Opcodes.IRETURN)));
		}

		//for (int j = 0; j < random.nextInt(2) + 1; j++)
		//    insnList = NumberObfuscationTransformer.obfuscateInsnList(insnList);

		return insnList;
	}
	public static InsnList generateIfGoto(int i, LabelNode label, FieldNode f, ClassNode node) {
		InsnList insnList = new InsnList();

		switch (i) {
		case 0: {
			int first;
			int second;

			do {
				first = random.nextInt(6) - 1;
				second = random.nextInt(6) - 1;
			} while (second == first);

			insnList.add(NodeUtils.generateIntPush(first));
			insnList.add(NodeUtils.generateIntPush(second));
			insnList.add(new JumpInsnNode(Opcodes.IF_ICMPNE, label));
			break;
		}
		case 1: {
			int first;
			int second;

			do {
				first = random.nextInt(6) - 1;
				second = random.nextInt(6) - 1;
			} while (second != first);

			insnList.add(NodeUtils.generateIntPush(first));
			insnList.add(NodeUtils.generateIntPush(second));
			insnList.add(new JumpInsnNode(Opcodes.IF_ICMPEQ, label));
			break;
		}
		case 2: {
			int first;
			int second;

			do {
				first = random.nextInt(6) - 1;
				second = random.nextInt(6) - 1;
			} while (first >= second);

			insnList.add(NodeUtils.generateIntPush(first));
			insnList.add(NodeUtils.generateIntPush(second));
			insnList.add(new JumpInsnNode(Opcodes.IF_ICMPLT, label));
			break;
		}
		case 3: {
			int first;
			int second;

			do {
				first = random.nextInt(6) - 1;
				second = random.nextInt(6) - 1;
			} while (first < second);

			insnList.add(NodeUtils.generateIntPush(first));
			insnList.add(NodeUtils.generateIntPush(second));
			insnList.add(new JumpInsnNode(Opcodes.IF_ICMPGE, label));
			break;
		}
		case 4: {
			int first;
			int second;

			do {
				first = random.nextInt(6) - 1;
				second = random.nextInt(6) - 1;
			} while (first <= second);

			insnList.add(NodeUtils.generateIntPush(first));
			insnList.add(NodeUtils.generateIntPush(second));
			insnList.add(new JumpInsnNode(Opcodes.IF_ICMPGT, label));
			break;
		}
		case 5: {
			int first;
			int second;

			do {
				first = random.nextInt(6) - 1;
				second = random.nextInt(6) - 1;
			} while (first > second);

			insnList.add(NodeUtils.generateIntPush(first));
			insnList.add(NodeUtils.generateIntPush(second));
			insnList.add(new JumpInsnNode(Opcodes.IF_ICMPLE, label));
			break;
		}
		case 6: {
			int first;

			first = random.nextInt(5) + 1;

			insnList.add(NodeUtils.generateIntPush(first));
			insnList.add(new JumpInsnNode(Opcodes.IFNE, label));
			break;
		}
		case 7: {
			// int first = 0;


			//insnList.add(NodeUtils.generateIntPush(first));
			insnList.add(new FieldInsnNode(GETSTATIC, node.name, f.name, f.desc));
			insnList.add(new JumpInsnNode(Opcodes.IFEQ, label));
			break;
		}
		case 8: {
			int second;

			second = random.nextInt(5);

			insnList.add(NodeUtils.generateIntPush(second));
			insnList.add(new JumpInsnNode(Opcodes.IFGE, label));
			break;
		}
		case 9: {
			int second;

			second = random.nextInt(5) + 1;

			insnList.add(NodeUtils.generateIntPush(second));
			insnList.add(new JumpInsnNode(Opcodes.IFGT, label));
			break;
		}
		case 10: {
			int second;

			second = -random.nextInt(5);

			insnList.add(NodeUtils.generateIntPush(second));
			insnList.add(new JumpInsnNode(Opcodes.IFLE, label));
			break;
		}
		case 11: {
			int second;

			second = -random.nextInt(5) - 1;

			insnList.add(NodeUtils.generateIntPush(second));
			insnList.add(new JumpInsnNode(Opcodes.IFLT, label));
			break;
		}
		default: {
			insnList.add(new InsnNode(Opcodes.ACONST_NULL));
			insnList.add(new JumpInsnNode(Opcodes.IFNULL, label));
			break;
		}
		//            case 13: {
			//                insnList.add(NodeUtils.notNullPush());
		//                insnList.add(new JumpInsnNode(Opcodes.IFNONNULL, label));
		//                break;
		//            }
		}
		return insnList;
	}
}
