package colonialobfuscator.utils;

import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

import colonialobfuscator.MainGUI;

public class NodeUtils {
	public static ClassNode toNode(final String className) throws IOException {
		final ClassReader classReader = new ClassReader(MainGUI.class.getResourceAsStream("/" + className.replace('.', '/') + ".class"));
		final ClassNode classNode = new ClassNode();
		classReader.accept(classNode, 0);
		return classNode;
	}
	public static MethodNode getMethod(final ClassNode classNode, final String name) {
		for (final MethodNode method : classNode.methods)
			if (method.name.equals(name))
				return method;
		return null;
	}
	public static AbstractInsnNode generateIntPush(int i) {
		return new LdcInsnNode(i);
	}
	public static AbstractInsnNode nullValueForType(Type returnType) {
		switch (returnType.getSort()) {
		case Type.BOOLEAN:
		case Type.BYTE:
		case Type.CHAR:
		case Type.SHORT:
		case Type.INT:
			return new InsnNode(Opcodes.ICONST_0);
		case Type.FLOAT:
			return new InsnNode(Opcodes.FCONST_0);
		case Type.DOUBLE:
			return new InsnNode(Opcodes.DCONST_0);
		case Type.LONG:
			return new InsnNode(Opcodes.LCONST_0);
		case Type.ARRAY:
		case Type.OBJECT:
			return new InsnNode(Opcodes.ACONST_NULL);
		default:
			throw new UnsupportedOperationException();
		}
	}
}