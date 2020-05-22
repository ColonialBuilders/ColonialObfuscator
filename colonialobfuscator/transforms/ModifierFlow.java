package colonialobfuscator.transforms;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import colonialobfuscator.utils.NameGen;

public class ModifierFlow implements ClassModifier {

    @Override
    public void modify(ClassNode node) {
    	
    	
        for (MethodNode method : node.methods) {
            for (AbstractInsnNode insnNode : method.instructions.toArray()) {
                // NOP
                if (RANDOM.nextInt(144) == 0)
                    method.instructions.insertBefore(insnNode, new InsnNode(Opcodes.NOP));
                // DUP SWAP
                if (insnNode.getOpcode() == Opcodes.DUP)
                    method.instructions.insert(insnNode, new InsnNode(Opcodes.SWAP));
                // final int n = a.hashCode() & (int)if.assert[747];
           /*     if (insnNode.getOpcode() == Opcodes.ARETURN) {
                    LabelNode label = new LabelNode();
                    InsnList list = new InsnList();
                    list.add(new InsnNode(Opcodes.DUP));
                    list.add(new JumpInsnNode(Opcodes.IFNULL, label));
                    list.add(new InsnNode(Opcodes.DUP));
                    list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, DeobfuscaterDump.getFile(), DeobfuscaterDump.METHOD, "(Ljava/lang/Object;)I", false));
                    int f = RANDOM.nextInt(Main.objects.size());
                    while (!(Main.objects.get(f) instanceof Integer))
                        f = RANDOM.nextInt(Main.objects.size());
                    list.add(new FieldInsnNode(Opcodes.GETSTATIC, DeobfuscaterDump.getFile(), DeobfuscaterDump.ARRAY, "[Ljava/lang/Object;"));
                    list.add(new IntInsnNode(Opcodes.SIPUSH, f + 2));
                    list.add(new InsnNode(Opcodes.AALOAD));
                    list.add(new TypeInsnNode(Opcodes.CHECKCAST, "java/lang/Integer"));
                    list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false));
                    list.add(new InsnNode(Opcodes.IAND));
                    list.add(new InsnNode(Opcodes.POP));
                    list.add(label);
                    method.instructions.insertBefore(insnNode, list);
                }
                if (insnNode instanceof LabelNode) {
                    InsnList list = new InsnList();

                    if (RANDOM.nextBoolean()) {
                        // if.assert[959] == null
                        list.add(new FieldInsnNode(Opcodes.GETSTATIC, DeobfuscaterDump.getFile(), DeobfuscaterDump.ARRAY, "[Ljava/lang/Object;"));
                        list.add(new IntInsnNode(Opcodes.SIPUSH, 2 + RANDOM.nextInt(Main.objects.size())));
                        list.add(new InsnNode(Opcodes.AALOAD));
                        list.add(new JumpInsnNode(Opcodes.IFNULL, (LabelNode) insnNode));
                    } else {
                        // (int)if.assert[41] > 31136
                        int f = RANDOM.nextInt(Main.objects.size());
                        while (!(Main.objects.get(f) instanceof Integer))
                            f = RANDOM.nextInt(Main.objects.size());
                        list.add(new FieldInsnNode(Opcodes.GETSTATIC, DeobfuscaterDump.getFile(), DeobfuscaterDump.ARRAY, "[Ljava/lang/Object;"));
                        list.add(new IntInsnNode(Opcodes.SIPUSH, f + 2));
                        list.add(new InsnNode(Opcodes.AALOAD));
                        list.add(new TypeInsnNode(Opcodes.CHECKCAST, "java/lang/Integer"));
                        list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false));
                        int s = RANDOM.nextInt(Short.MAX_VALUE);
                        list.add(new IntInsnNode(Opcodes.SIPUSH, s));
                        list.add(new JumpInsnNode((int) Main.objects.get(f) > s ? Opcodes.IF_ICMPLT : Opcodes.IF_ICMPGT, (LabelNode) insnNode));
                    }

                    // while () {} OR  if (!) {}
                    if (RANDOM.nextBoolean())
                        method.instructions.insert(insnNode, list);
                    else
                        method.instructions.insertBefore(insnNode, list);
                }*/
            }
        }
    }
}
