package colonialobfuscator.utils;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.SimpleRemapper;
import org.objectweb.asm.tree.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;

public class BytecodeHelper {
	
//	/https://github.com/CalebWhiting/java-asm-obfuscator/blob/master/src/main/java/com/github/jasmo/util/BytecodeHelper.java
	
	public static MethodNode getMethod(ClassNode node, String name, String desc) {
		return node.methods.stream()
		                   .filter(m -> m.name.equals(name))
		                   .filter(m -> m.desc.equals(desc))
		                   .findAny()
		                   .orElse(null);
	}

	public static FieldNode getField(ClassNode node, String name, String desc) {
		return node.fields.stream()
		                  .filter(m -> m.name.equals(name))
		                  .filter(m -> m.desc.equals(desc))
		                  .findAny()
		                  .orElse(null);
	}

	public static <T extends AbstractInsnNode> void forEach(InsnList instructions,
	                                                        Class<T> type,
	                                                        Consumer<T> consumer) {
		AbstractInsnNode[] array = instructions.toArray();
		for (AbstractInsnNode node : array) {
			if (node.getClass() == type) {
				//noinspection unchecked
				consumer.accept((T) node);
			}
		}
	}

	public static void forEach(InsnList instructions, Consumer<AbstractInsnNode> consumer) {
		forEach(instructions, AbstractInsnNode.class, consumer);
	}

	public static void applyMappings(Map<String, ClassNode> classMap, Map<String, String> remap) {
		for (Map.Entry<String, String> entry : remap.entrySet()) {
			String k = entry.getKey();
			String v = entry.getValue();
			if (k.equals(v))
				continue;
			// skip members with same name
			// field format =   [ "<owner>.<name>"          : "<newname>" ]
			// method format =  [ "<owner>.<name> <desc>"   : "<newname>" ]
			int n = k.indexOf('.');
			if (n != -1 && v.length() >= n && v.substring(n).equals(k)) {
				continue;
			}
		}
		SimpleRemapper remapper = new SimpleRemapper(remap);
		for (ClassNode node : new ArrayList<>(classMap.values())) {
			ClassNode copy = new ClassNode();
			ClassRemapper adapter = new ClassRemapper(copy, remapper);
			node.accept(adapter);
			classMap.put(node.name, copy);
		}
	}

	public static AbstractInsnNode newIntegerNode(int i) {
		if (i >= -1 && i <= 5) {
			return new InsnNode(Opcodes.ICONST_0 + i);
		} else if (i >= Byte.MIN_VALUE && i <= Byte.MAX_VALUE) {
			return new IntInsnNode(Opcodes.BIPUSH, i);
		} else if (i >= Short.MIN_VALUE && i <= Short.MAX_VALUE) {
			return new IntInsnNode(Opcodes.SIPUSH, i);
		} else {
			return new LdcInsnNode(i);
		}
	}}