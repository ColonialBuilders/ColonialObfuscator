package colonialobfuscator.utils;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;

import static org.objectweb.asm.Opcodes.*;
public class Utils {
    public static int getInt(AbstractInsnNode ain) {
        int op;
        if((op = ain.getOpcode()) == ICONST_0) {
            return 0;
        } else
        if((op = ain.getOpcode()) == ICONST_1) {
            return 1;
        } else
        if((op = ain.getOpcode()) == ICONST_2) {
            return 2;
        } else
        if((op = ain.getOpcode()) == ICONST_3) {
            return 3;
        } else
        if((op = ain.getOpcode()) == ICONST_4) {
            return 4;
        } else
        if((op = ain.getOpcode()) == ICONST_5) {
            return 5;
        } else if(ain instanceof IntInsnNode) {
            return ((IntInsnNode)ain).operand;
        } else if(ain instanceof LdcInsnNode) {
            LdcInsnNode ldcInsnNode;
            if((ldcInsnNode = ((LdcInsnNode)(ain))).cst instanceof Integer) {
                return (Integer)ldcInsnNode.cst;
            }
        }
        return 0;
    }
}
