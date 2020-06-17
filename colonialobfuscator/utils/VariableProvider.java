package colonialobfuscator.utils;
//https://github.com/superblaubeere27/obfuscator/blob/master/obfuscator-core/src/main/java/me/superblaubeere27/jobf/utils/VariableProvider.java

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import static org.objectweb.asm.Opcodes.*;
import java.lang.reflect.Modifier;

public class VariableProvider {
	private int max = 0;
	private int argumentSize;

	private VariableProvider() {

	}

	public VariableProvider(MethodNode method) {
		this();

		if (!Modifier.isStatic(method.access)) registerExisting(0, Type.getType("Ljava/lang/Object;"));

		for (Type argumentType : Type.getArgumentTypes(method.desc)) {
			registerExisting(argumentType.getSize() + max - 1, argumentType);
		}

		argumentSize = max;

		for (AbstractInsnNode abstractInsnNode : method.instructions.toArray()) {
			if (abstractInsnNode instanceof VarInsnNode) {
				registerExisting(((VarInsnNode) abstractInsnNode).var, getType((VarInsnNode) abstractInsnNode));
			}
		}
	}
	public static Type getType(VarInsnNode abstractInsnNode) {
		int offset;

		if (abstractInsnNode.getOpcode() >= ISTORE && abstractInsnNode.getOpcode() <= ASTORE)
			offset = abstractInsnNode.getOpcode() - ISTORE;
		else if (abstractInsnNode.getOpcode() >= ILOAD && abstractInsnNode.getOpcode() <= ALOAD)
			offset = abstractInsnNode.getOpcode() - ILOAD;
		else if (abstractInsnNode.getOpcode() == RET)
			throw new UnsupportedOperationException("RET is not supported");
		else
			throw new UnsupportedOperationException();

		switch (offset) {
		case 0:
			return Type.INT_TYPE;
		case LLOAD - ILOAD:
			return Type.LONG_TYPE;
		case FLOAD - ILOAD:
			return Type.FLOAT_TYPE;
		case DLOAD - ILOAD:
			return Type.DOUBLE_TYPE;
		case ALOAD - ILOAD:
			return Type.getType("Ljava/lang/Object;");
		}

		throw new UnsupportedOperationException();
	}
	private void registerExisting(int var, Type type) {
		if (var >= max) max = var + type.getSize();
	}

	public boolean isUnallocated(int var) {
		return var >= max;
	}

	public boolean isArgument(int var) {
		return var < argumentSize;
	}


	public int allocateVar() {
		return max++;
	}

}