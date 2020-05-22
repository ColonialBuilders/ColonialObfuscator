package colonialobfuscator.transforms;

import java.util.Random;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import colonialobfuscator.utils.NameGen;
import colonialobfuscator.utils.NodeUtils;

public class ModifierFlow implements ClassModifier {

    @Override
    public void modify(ClassNode node) {
    	
    	
        for (MethodNode method : node.methods) {
            for (AbstractInsnNode insnNode : method.instructions.toArray()) {
            	
            	//https://github.com/superblaubeere27/obfuscator/blob/master/obfuscator-core/src/main/java/me/superblaubeere27/jobf/processors/flowObfuscation/FlowObfuscator.java
                if (insnNode instanceof JumpInsnNode && insnNode.getOpcode() == Opcodes.GOTO) {
                    JumpInsnNode insnNode2 = (JumpInsnNode) insnNode;
                    final InsnList insnList = new InsnList();
                    insnList.add(ifGoto(insnNode2.label, method, Type.getReturnType(method.desc)));
                    method.instructions.insert(insnNode, insnList);
                    method.instructions.remove(insnNode);
                }
            	
            	
            	
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
    static Random random = new Random();
    
    //https://github.com/superblaubeere27/obfuscator/blob/master/obfuscator-core/src/main/java/me/superblaubeere27/jobf/processors/flowObfuscation/FlowObfuscator.java
    private static InsnList ifGoto(LabelNode label, MethodNode methodNode, Type returnType) {
        InsnList insnList;

        int i = random.nextInt(14);

        insnList = generateIfGoto(i, label);

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
    public static InsnList generateIfGoto(int i, LabelNode label) {
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
                int first = 0;


                insnList.add(NodeUtils.generateIntPush(first));
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
