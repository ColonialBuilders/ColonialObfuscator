package colonialobfuscator.transforms;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Random;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import colonialobfuscator.utils.NameGen;
import colonialobfuscator.utils.NodeUtils;
import colonialobfuscator.utils.VariableProvider;

public class StringEncryption implements ClassModifier {
	boolean MoreLagg = false;
    @Override
    public void modify(ClassNode node) {
    	int Pass = MoreLagg ? 3 : 1;
    	for (int b = 0; b < Pass; b++) {
		MethodNode method = null;
	    try {
			method = NodeUtils.getMethod(NodeUtils.toNode(getClass().getName()), "enToArr");
		} catch (IOException e) {
			e.printStackTrace();
		}
	    method.access = ACC_PRIVATE | ACC_STATIC;
		method.name = "ColonialObfuscator_" + NameGen.String(10);
		for(LocalVariableNode var : method.localVariables) {
			var.name = "ColonialObfuscator_" + NameGen.String(10);
		}
		node.methods.add(method);
		MethodNode method2 = null;
	    try {
			method2 = NodeUtils.getMethod(NodeUtils.toNode(getClass().getName()), new Random().nextBoolean() ? "enArr" : 
				new Random().nextBoolean() ? "enArr2" : "enArr3");
		} catch (IOException e) {
			e.printStackTrace();
		}
	    int key = new Random().nextInt(9000) + 20000;
	    method2.access = ACC_PRIVATE | ACC_STATIC;
		method2.name = "ColonialObfuscator_" + NameGen.String(10);
		for(AbstractInsnNode i : method2.instructions) {
			if(i instanceof LdcInsnNode) {
				if((int)((LdcInsnNode)i).cst > 1000) {
					((LdcInsnNode)i).cst = key;
				}
			}
		}
		for(LocalVariableNode var : method2.localVariables) {
			var.name = "ColonialObfuscator_" + NameGen.String(10);
		}
		if(new Random().nextBoolean()/*Flow*/) {
			mangleSwitches(method2);
		}
	    node.methods.add(method2);
	for (MethodNode mn : node.methods) {
		for (AbstractInsnNode ain : mn.instructions.toArray()) {
			if (ain.getType() != AbstractInsnNode.LDC_INSN) {
				continue;
			}
			LdcInsnNode ldc = (LdcInsnNode) ain;
			if (!(ldc.cst instanceof String)) {
				continue;
			}
			String s = ldc.cst.toString();
			if (s.length() == 0) {
				continue;
			}
			mn.instructions.insertBefore(ain, new LdcInsnNode(EnWithKey(enToArr(s), key)));
			mn.instructions.insertBefore(ain, new MethodInsnNode(INVOKESTATIC, node.name, method.name, method.desc));
			mn.instructions.insertBefore(ain, new MethodInsnNode(INVOKESTATIC, node.name, method2.name, method2.desc));
			mn.instructions.remove(ain);
		}
	}
    }
    }
    
    private static String EnWithKey(char[] cArr, int key) {
    	char c;
    	int length = cArr.length;
    	for (int i = 0; length > i; i++) {
    	    char c2 = cArr[i];
    	    switch (i % 10) {
    	        case 0:
    	            c = (char) ((char) ((i ^ key) % 10) ^ key);
    	            break;
    	        case 1:
    	            c = (char) ((char) ((i ^ key) % 10) ^ key);
    	            break;
    	        case 2:
    	            c = (char) ((char) ((i ^ key) % 10) ^ key);
    	            break;
    	        case 3:
    	            c = (char) ((char) ((i ^ key) % 10) ^ key);
    	            break;
    	        case 4:
    	            c = (char) ((char) ((i ^ key) % 10) ^ key);
    	            break;
    	        case 5:
    	            c = (char) ((char) ((i ^ key) % 10) ^ key);
    	            break;
    	        case 6:
    	            c = (char) ((char) ((i ^ key) % 10) ^ key);
    	            break;
    	        case 7:
    	            c = (char) ((char) ((i ^ key) % 10) ^ key);
    	            break;
    	        case 8:
    	            c = (char) ((char) ((i ^ key) % 10) ^ key);
    	            break;
    	        case 9:
    	            c = (char) ((char) ((i ^ key) % 10) ^ key);
    	            break;
    	        case 10:
    	            c = (char) ((char) ((i ^ key) % 10) ^ key);
    	            break;
    	        default:
    	            c = 64;
    	            break;
    	    }
    	    cArr[i] = (char) (c ^ c2);
    	}
    	return new String(cArr).intern();
    	}
    private static String enArr3(char[] cArr) {
    	char c;
    	int length = cArr.length;
    	for (int i = 0; length > i; i++) {
    	    char c2 = cArr[i];
    	    switch (i % 10) {
    	        case 0:
    	        	int key = 1000000000;
    	            c = (char) ((char) ((i ^ key) % 10) ^ key);
    	            break;
    	        case 1:
    	        	int key2 = 1000000000;
    	            c = (char) ((char) ((i ^ key2) % 10) ^ key2);
    	            break;
    	        case 2:
    	        	int key3 = 1000000000;
    	            c = (char) ((char) ((i ^ key3) % 10) ^ key3);
    	            break;
    	        case 3:
    	        	int key4 = 1000000000;
    	            c = (char) ((char) ((i ^ key4) % 10) ^ key4);
    	            break;
    	        case 4:
    	        	int key5 = 1000000000;
    	            c = (char) ((char) ((i ^ key5) % 10) ^ key5);
    	            break;
    	        case 5:
    	        	int key6 = 1000000000;
    	            c = (char) ((char) ((i ^ key6) % 10) ^ key6);
    	            break;
    	        case 6:
    	        	int key7 = 1000000000;
    	            c = (char) ((char) ((i ^ key7) % 10) ^ key7);
    	            break;
    	        case 7:
    	        	int key8 = 1000000000;
    	            c = (char) ((char) ((i ^ key8) % 10) ^ key8);
    	            break;
    	        case 8:
    	        	int key9 = 1000000000;
    	            c = (char) ((char) ((i ^ key9) % 10) ^ key9);
    	            break;
    	        case 9:
    	        	int key10 = 1000000000;
    	            c = (char) ((char) ((i ^ key10) % 10) ^ key10);
    	            break;
    	        case 10:
    	        	int key11 = 1000000000;
    	            c = (char) ((char) ((i ^ key11) % 10) ^ key11);
    	            break;
    	        default:
    	            c = 64;
    	            break;
    	    }
    	    cArr[i] = (char) (c ^ c2);
    	}
    	return new String(cArr).intern();
    	}
    private static String enArr2(char[] cArr) {
    	char c;
    	int length = cArr.length;
    	for (int i = 0; length > i; i++) {
    	    char c2 = cArr[i];
    	    switch (i % 10) {
    	        case 0:
    	            c = (char) ((char) ((i ^ 1000000000) % 10) ^ 1000000000);
    	            break;
    	        case 1:
    	            c = (char) ((char) ((i ^ 1000000000) % 10) ^ 1000000000);
    	            break;
    	        case 2:
    	            c = (char) ((char) ((i ^ 1000000000) % 10) ^ 1000000000);
    	            break;
    	        case 3:
    	            c = (char) ((char) ((i ^ 1000000000) % 10) ^ 1000000000);
    	            break;
    	        case 4:
    	            c = (char) ((char) ((i ^ 1000000000) % 10) ^ 1000000000);
    	            break;
    	        case 5:
    	            c = (char) ((char) ((i ^ 1000000000) % 10) ^ 1000000000);
    	            break;
    	        case 6:
    	            c = (char) ((char) ((i ^ 1000000000) % 10) ^ 1000000000);
    	            break;
    	        case 7:
    	            c = (char) ((char) ((i ^ 1000000000) % 10) ^ 1000000000);
    	            break;
    	        case 8:
    	            c = (char) ((char) ((i ^ 1000000000) % 10) ^ 1000000000);
    	            break;
    	        case 9:
    	            c = (char) ((char) ((i ^ 1000000000) % 10) ^ 1000000000);
    	            break;
    	        case 10:
    	            c = (char) ((char) ((i ^ 1000000000) % 10) ^ 1000000000);
    	            break;
    	        default:
    	            c = 64;
    	            break;
    	    }
    	    cArr[i] = (char) (c ^ c2);
    	}
    	return new String(cArr).intern();
    	}
    
    
 private static String enArr(char[] cArr) {
    	int key = 1000000000;
    	char c;
    	int length = cArr.length;
    	for (int i = 0; length > i; i++) {
    	    char c2 = cArr[i];
    	    switch (i % 10) {
    	        case 0:
    	            c = (char) ((char) ((i ^ key) % 10) ^ key);
    	            break;
    	        case 1:
    	            c = (char) ((char) ((i ^ key) % 10) ^ key);
    	            break;
    	        case 2:
    	            c = (char) ((char) ((i ^ key) % 10) ^ key);
    	            break;
    	        case 3:
    	            c = (char) ((char) ((i ^ key) % 10) ^ key);
    	            break;
    	        case 4:
    	            c = (char) ((char) ((i ^ key) % 10) ^ key);
    	            break;
    	        case 5:
    	            c = (char) ((char) ((i ^ key) % 10) ^ key);
    	            break;
    	        case 6:
    	            c = (char) ((char) ((i ^ key) % 10) ^ key);
    	            break;
    	        case 7:
    	            c = (char) ((char) ((i ^ key) % 10) ^ key);
    	            break;
    	        case 8:
    	            c = (char) ((char) ((i ^ key) % 10) ^ key);
    	            break;
    	        case 9:
    	            c = (char) ((char) ((i ^ key) % 10) ^ key);
    	            break;
    	        case 10:
    	            c = (char) ((char) ((i ^ key) % 10) ^ key);
    	            break;
    	        default:
    	            c = 64;
    	            break;
    	    }
    	    cArr[i] = (char) (c ^ c2);
    	}
    	return new String(cArr).intern();
    	}

private static char[] enToArr(String str) {
char[] charArray = str.toCharArray();
if (charArray.length < 2) {
charArray[0] = (char) (charArray[0] ^ 16);
}
return charArray;
}
static void mangleSwitches(MethodNode node) {
    if (Modifier.isAbstract(node.access) || Modifier.isNative(node.access))
        return;

    VariableProvider provider = new VariableProvider(node);
    int resultSlot = provider.allocateVar();

    for (AbstractInsnNode abstractInsnNode : node.instructions.toArray()) {
        if (abstractInsnNode instanceof TableSwitchInsnNode) {
            TableSwitchInsnNode switchInsnNode = (TableSwitchInsnNode) abstractInsnNode;

            InsnList insnList = new InsnList();
            insnList.add(new VarInsnNode(Opcodes.ISTORE, resultSlot));

            int j = 0;

            for (int i = switchInsnNode.min; i <= switchInsnNode.max; i++) {
                insnList.add(new VarInsnNode(Opcodes.ILOAD, resultSlot));
                insnList.add(NodeUtils.generateIntPush(i));
                insnList.add(new JumpInsnNode(Opcodes.IF_ICMPEQ, switchInsnNode.labels.get(j)));

                j++;
            }
            insnList.add(new JumpInsnNode(Opcodes.GOTO, switchInsnNode.dflt));


            node.instructions.insert(abstractInsnNode, insnList);
            node.instructions.remove(abstractInsnNode);
        }
        if (abstractInsnNode instanceof LookupSwitchInsnNode) {
            LookupSwitchInsnNode switchInsnNode = (LookupSwitchInsnNode) abstractInsnNode;

            InsnList insnList = new InsnList();
            insnList.add(new VarInsnNode(Opcodes.ISTORE, resultSlot));

            List<Integer> keys = switchInsnNode.keys;
            for (int i = 0; i < keys.size(); i++) {
                Integer key = keys.get(i);
                insnList.add(new VarInsnNode(Opcodes.ILOAD, resultSlot));
                insnList.add(NodeUtils.generateIntPush(key));
                insnList.add(new JumpInsnNode(Opcodes.IF_ICMPEQ, switchInsnNode.labels.get(i)));

            }

            insnList.add(new JumpInsnNode(Opcodes.GOTO, switchInsnNode.dflt));


            node.instructions.insert(abstractInsnNode, insnList);
            node.instructions.remove(abstractInsnNode);
        }
    }
}
    }