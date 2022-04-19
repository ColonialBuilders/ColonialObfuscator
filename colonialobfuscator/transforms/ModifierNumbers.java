package colonialobfuscator.transforms;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import colonialobfuscator.utils.NameGen;
import colonialobfuscator.utils.NodeUtils;
import colonialobfuscator.utils.OutputUtil;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class ModifierNumbers implements ClassModifier {
	
	@Override
	public void modify(ClassNode classNode) {

		AtomicBoolean used = new AtomicBoolean(false);
		String MNAME = NameGen.colonial() + NameGen.String(OutputUtil.namesLenght);
		String desc = "(III)I";
		int xor = RANDOM.nextInt();
		String FNAME1 = NameGen.String(OutputUtil.namesLenght);

		classNode.methods.stream().forEach(mn -> {
			long key = new Random().nextLong();
             for (AbstractInsnNode insn : mn.instructions.toArray()) {
                 if (isIntInsn(insn)) {
                	 
                     int originalNum;
					try {
						originalNum = (getIntegerFromInsn(insn));
                     int value1 = new Random().nextInt();
                     int value2 = originalNum ^ value1;

                     InsnList insnList = new InsnList();
                     insnList.add(new LdcInsnNode(value1));
                     //insnList.add(new LdcInsnNode(value2));

						used.set(true);
						int k2 = RANDOM.nextInt();
						int k3 = RANDOM.nextInt();
						insnList.add(new LdcInsnNode(k3));
						insnList.add(new LdcInsnNode((value2 ^ k3) ^ (xor ^ k2)));
						insnList.add(new LdcInsnNode(k2 ^ k3));
						insnList.add(new MethodInsnNode(INVOKESTATIC, classNode.name, MNAME, desc));

                     insnList.add(new InsnNode(IXOR));

                     mn.instructions.insertBefore(insn, insnList);
                     mn.instructions.remove(insn);
					} catch (Exception e) {
						e.printStackTrace();
					}
                 } else if (isLongInsn(insn)) {
                	 try {
                     long originalNum = getLongFromInsn(insn);
                     long value1 = new Random().nextLong();
                     long value2 = originalNum ^ value1;

                     InsnList insnList = new InsnList();
                     insnList.add(new LdcInsnNode(value1));
                     insnList.add(new LdcInsnNode(value2));
                     insnList.add(new InsnNode(LXOR));

                     mn.instructions.insertBefore(insn, insnList);
                     mn.instructions.remove(insn);
					} catch (Exception e) {
						e.printStackTrace();
					}
                 }/* else if (isFloatInsn(insn)) {
                	 try {
                     float originalNum = getFloatFromInsn(insn);
                     long value1 = new Random().nextLong();
                     long value2 = (((long)originalNum)) ^ value1;

                     InsnList insnList = new InsnList();
                     insnList.add(new LdcInsnNode(value1));
                     insnList.add(new LdcInsnNode(value2));
                     insnList.add(new InsnNode(LXOR));
                     
                     insnList.add(new InsnNode(L2F));
                     
                     mn.instructions.insertBefore(insn, insnList);
                     mn.instructions.remove(insn);
					} catch (Exception e) {
						e.printStackTrace();
					}
                 }*/
             }
         });

		if(used.get()) {
			MethodVisitor mv = classNode.visitMethod(ACC_PUBLIC | ACC_STATIC, MNAME, desc, null, null);
			mv.visitCode();
			mv.visitVarInsn(ILOAD, 1);
			mv.visitVarInsn(ILOAD, 0);
			mv.visitInsn(IXOR);
			mv.visitFieldInsn(GETSTATIC, classNode.name, FNAME1, "I");
			mv.visitVarInsn(ILOAD, 2);
			mv.visitVarInsn(ILOAD, 0);
			mv.visitInsn(IXOR);
			mv.visitInsn(IXOR);
			mv.visitInsn(IXOR);
			mv.visitInsn(IRETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
			classNode.fields.add(new FieldNode(ACC_STATIC, FNAME1, "I", null, null));
			MethodNode clInit = NodeUtils.getMethod(classNode, "<clinit>");
			if (clInit == null) {
				clInit = new MethodNode(Opcodes.ACC_STATIC, "<clinit>", "()V", null, new String[0]);
				classNode.methods.add(clInit);
			}
			if (clInit.instructions == null)
				clInit.instructions = new InsnList();


			InsnList instructions = new InsnList();
			int key = RANDOM.nextInt();
			instructions.add(new LdcInsnNode(xor ^ key));
			instructions.add(new LdcInsnNode(key));
			instructions.add(new InsnNode(IXOR));
			instructions.add(new FieldInsnNode(PUTSTATIC, classNode.name, FNAME1, "I"));

			if (clInit.instructions == null || clInit.instructions.getFirst() == null) {
				clInit.instructions.add(instructions);
				clInit.instructions.add(new InsnNode(Opcodes.RETURN));
			} else {
				clInit.instructions.insertBefore(clInit.instructions.getFirst(), instructions);
			}


		}

	}
	 public static boolean isIntInsn(AbstractInsnNode insn) {
	        if (insn == null) {
	            return false;
	        }
	        int opcode = insn.getOpcode();
	        return ((opcode >= Opcodes.ICONST_M1 && opcode <= Opcodes.ICONST_5)
	                || opcode == Opcodes.BIPUSH
	                || opcode == Opcodes.SIPUSH
	                || (insn instanceof LdcInsnNode
	                && ((LdcInsnNode) insn).cst instanceof Integer));
	    }

	    public static boolean isLongInsn(AbstractInsnNode insn) {
	        int opcode = insn.getOpcode();
	        return (opcode == Opcodes.LCONST_0
	                || opcode == Opcodes.LCONST_1
	                || (insn instanceof LdcInsnNode
	                && ((LdcInsnNode) insn).cst instanceof Long));
	    }

	    public static boolean isFloatInsn(AbstractInsnNode insn) {
	        int opcode = insn.getOpcode();
	        return (opcode >= Opcodes.FCONST_0 && opcode <= Opcodes.FCONST_2)
	                || (insn instanceof LdcInsnNode && ((LdcInsnNode) insn).cst instanceof Float);
	    }

	    public static boolean isDoubleInsn(AbstractInsnNode insn) {
	        int opcode = insn.getOpcode();
	        return (opcode >= Opcodes.DCONST_0 && opcode <= Opcodes.DCONST_1)
	                || (insn instanceof LdcInsnNode && ((LdcInsnNode) insn).cst instanceof Double);
	    }

	    public static int getIntegerFromInsn(AbstractInsnNode insn) throws Exception {
	        int opcode = insn.getOpcode();

	        if (opcode >= Opcodes.ICONST_M1 && opcode <= Opcodes.ICONST_5) {
	            return opcode - 3;
	        } else if (insn instanceof IntInsnNode
	                && insn.getOpcode() != Opcodes.NEWARRAY) {
	            return ((IntInsnNode) insn).operand;
	        } else if (insn instanceof LdcInsnNode
	                && ((LdcInsnNode) insn).cst instanceof Integer) {
	            return (Integer) ((LdcInsnNode) insn).cst;
	        }

	        throw new Exception("Unexpected instruction");
	    }

	    public static long getLongFromInsn(AbstractInsnNode insn) throws Exception {
	        int opcode = insn.getOpcode();

	        if (opcode >= Opcodes.LCONST_0 && opcode <= Opcodes.LCONST_1) {
	            return opcode - 9;
	        } else if (insn instanceof LdcInsnNode
	                && ((LdcInsnNode) insn).cst instanceof Long) {
	            return (Long) ((LdcInsnNode) insn).cst;
	        }

	        throw new Exception("Unexpected instruction");
	    }

	    public static float getFloatFromInsn(AbstractInsnNode insn) throws Exception {
	        int opcode = insn.getOpcode();

	        if (opcode >= Opcodes.FCONST_0 && opcode <= Opcodes.FCONST_2) {
	            return opcode - 11;
	        } else if (insn instanceof LdcInsnNode
	                && ((LdcInsnNode) insn).cst instanceof Float) {
	            return (Float) ((LdcInsnNode) insn).cst;
	        }

	        throw new Exception("Unexpected instruction");
	    }

	    public static double getDoubleFromInsn(AbstractInsnNode insn) throws Exception {
	        int opcode = insn.getOpcode();

	        if (opcode >= Opcodes.DCONST_0 && opcode <= Opcodes.DCONST_1) {
	            return opcode - 14;
	        } else if (insn instanceof LdcInsnNode
	                && ((LdcInsnNode) insn).cst instanceof Double) {
	            return (Double) ((LdcInsnNode) insn).cst;
	        }

	        throw new Exception("Unexpected instruction");
	    }
}
