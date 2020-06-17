package colonialobfuscator.transforms;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import java.util.Random;

public interface ClassModifier extends Opcodes {
	void modify(ClassNode classNode);
	Random RANDOM = new Random();
}
