package colonialobfuscator.transforms;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.util.Textifier;

public class ModifierOptimizeCheck implements ClassModifier {
    @Override
    public void modify(ClassNode classNode) {
    //    if (Main.config.isOptimzeCheck())
            for (MethodNode method : classNode.methods) {
                for (AbstractInsnNode node : method.instructions.toArray()) {
                    int i = node.getOpcode();
                    if (node instanceof LineNumberNode)
                        System.out.println("LineNumberNode " + ((LineNumberNode) node).line + " at " + classNode.name + "." + method.name + method.desc);
                    else if (i == Opcodes.NOP)
                        System.out.println("NOP at " + classNode.name + "." + method.name + method.desc);
                    if ((i >= 26 && i <= 45) || (i >= 59 && i <= 78))
                        System.out.println("*LOAD_N / *STORE_N : " + i + " at " + classNode.name + "." + method.name + method.desc);
                }
                AbstractInsnNode[] aina = method.instructions.toArray();
                for (int i = aina.length - 1; i > 0; i--) {
                    AbstractInsnNode a = aina[i - 1];
                    AbstractInsnNode b = aina[i];
                    if (a.getOpcode() == b.getOpcode()) {
                        if (a instanceof VarInsnNode && ((VarInsnNode) a).var == ((VarInsnNode) b).var)
                            System.out.println(Textifier.OPCODES[a.getOpcode()] + " " + ((VarInsnNode) a).var + " " + ((VarInsnNode) b).var + " at " + classNode.name + "." + method.name + method.desc);
                        else if (a instanceof FieldInsnNode && ((FieldInsnNode) a).owner.equals(((FieldInsnNode) b).owner) && ((FieldInsnNode) a).name.equals(((FieldInsnNode) b).name) && ((FieldInsnNode) a).desc.equals(((FieldInsnNode) b).desc))
                            System.out.println(Textifier.OPCODES[a.getOpcode()] + " " + ((FieldInsnNode) a).owner + " " + ((FieldInsnNode) a).name + ((FieldInsnNode) a).desc + " at " + classNode.name + "." + method.name + method.desc);
                        else if (a.getOpcode() == Opcodes.POP)
                            System.out.println("POP POP != POP2 at " + classNode.name + "." + method.name + method.desc);
                    }
                }
                for (int i = aina.length - 1; i > 1; i--) {
                    if (aina[i].getOpcode() == Opcodes.DUP && aina[i - 1].getOpcode() == Opcodes.DUP && aina[i - 2].getOpcode() == Opcodes.DUP)
                        System.out.println("DUP DUP DUP != DUP DUP2 at " + classNode.name + "." + method.name + method.desc);
                }
            }
    }
}
