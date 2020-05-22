package colonialobfuscator.transforms;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class ModifierBooleans implements ClassModifier {
    @Override
    public void modify(ClassNode classNode) {
        for (MethodNode method : classNode.methods) {
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
        }
    }
}
