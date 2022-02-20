package colonialobfuscator.transforms;

import colonialobfuscator.utils.NameGen;
import static org.objectweb.asm.Opcodes.*;

import colonialobfuscator.utils.OutputUtil;
import org.objectweb.asm.tree.*;

public class ModifierCodeLogic implements ClassModifier {
    @Override
    public void modify(ClassNode classNode) {
       // boolean ixor = false;
     //   String xorname = NameGen.colonial() + NameGen.String(4);
        boolean iadd = false;
        String addname = NameGen.colonial() + NameGen.String(OutputUtil.namesLenght);
        for (MethodNode method : classNode.methods) {
            for (AbstractInsnNode node : method.instructions.toArray()) {
                if (node instanceof IincInsnNode) {
                    IincInsnNode insn = (IincInsnNode) node;
                    int key = RANDOM.nextInt();
                    if (RANDOM.nextBoolean()) {
                        method.instructions.insert(insn, new IincInsnNode(insn.var, -key));
                        method.instructions.insert(insn, new IincInsnNode(insn.var, insn.incr + key));
                    } else {
                        method.instructions.insert(insn, new IincInsnNode(insn.var, insn.incr - key));
                        method.instructions.insert(insn, new IincInsnNode(insn.var, +key));
                    }
                    method.instructions.remove(insn);
                } else if (node.getOpcode() == IADD) {//TODO add ISUB, ISHL and IAND
                    iadd = true;
                    method.instructions.set(node, new MethodInsnNode(INVOKESTATIC, classNode.name, addname, "(II)I", false));
                } else if (node.getOpcode() == IXOR) {
                  //  ixor = true;
   //                 method.instructions.set(node, new MethodInsnNode(INVOKESTATIC, classNode.name, xorname, "(II)I", false));
                }
            }
        }
        if(iadd) {
            MethodNode mn = new MethodNode(ACC_PUBLIC | ACC_STATIC, addname, "(II)I", null, null);
            mn.instructions.add(new VarInsnNode(ILOAD, 0));
            mn.instructions.add(new VarInsnNode(ILOAD, 1));
            mn.instructions.add(new InsnNode(IOR));
            mn.instructions.add(new InsnNode(ICONST_1));
            mn.instructions.add(new InsnNode(ISHL));
            mn.instructions.add(new VarInsnNode(ILOAD, 0));
            mn.instructions.add(new VarInsnNode(ILOAD, 1));
            mn.instructions.add(new InsnNode(IXOR));

            mn.instructions.add(new InsnNode(ICONST_M1));
            mn.instructions.add(new InsnNode(IXOR));
            mn.instructions.add(new InsnNode(IADD));
            mn.instructions.add(new InsnNode(ICONST_1));
            mn.instructions.add(new InsnNode(IADD));

            mn.instructions.add(new InsnNode(IRETURN));
            classNode.methods.add(mn);
        }
        /*if(ixor) {
            System.out.println(classNode.name);
            MethodNode mn = new MethodNode(ACC_PUBLIC | ACC_STATIC, xorname, "(II)I", null, null);
            mn.instructions.add(new VarInsnNode(ILOAD, 0));
            mn.instructions.add(new VarInsnNode(ILOAD, 1));
            mn.instructions.add(new InsnNode(IOR));
            mn.instructions.add(new VarInsnNode(ILOAD, 0));
            mn.instructions.add(new VarInsnNode(ILOAD, 1));
            mn.instructions.add(new InsnNode(IAND));
            mn.instructions.add(new InsnNode(ICONST_M1));
            mn.instructions.add(new InsnNode(IXOR));
            mn.instructions.add(new InsnNode(IADD));
            mn.instructions.add(new InsnNode(ICONST_1));
            mn.instructions.add(new InsnNode(IADD));
            mn.instructions.add(new InsnNode(IRETURN));
            classNode.methods.add(mn);
        }*/


    }
            }