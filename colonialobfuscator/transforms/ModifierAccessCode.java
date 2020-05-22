package colonialobfuscator.transforms;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ParameterNode;

public class ModifierAccessCode implements ClassModifier {

    @Override
    public void modify(ClassNode classNode) {
        classNode.access &= ~(Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED);
        classNode.access |= (Opcodes.ACC_SYNTHETIC | Opcodes.ACC_PUBLIC);

        for (MethodNode method : classNode.methods) {
            method.access &= ~(Opcodes.ACC_VARARGS | Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED);
            method.access |= Opcodes.ACC_PUBLIC;

            if ((Opcodes.ACC_STATIC & method.access) != 0)
                method.access |= Opcodes.ACC_SYNTHETIC;
            if (method.parameters != null)
                for (ParameterNode pn : method.parameters)
                    pn.access |= Opcodes.ACC_SYNTHETIC;
        }
        for (FieldNode field : classNode.fields) {
            field.access &= ~(Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED);
            field.access |= (Opcodes.ACC_SYNTHETIC | Opcodes.ACC_PUBLIC);
        }
    }
}
