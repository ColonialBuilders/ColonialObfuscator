package colonialobfuscator.utils;

import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import colonialobfuscator.Main;

public class NodeUtils {
    public static ClassNode toNode(final String className) throws IOException {
        final ClassReader classReader = new ClassReader(Main.class.getResourceAsStream("/" + className.replace('.', '/') + ".class"));
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
}
