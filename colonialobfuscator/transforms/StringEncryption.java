package colonialobfuscator.transforms;

import java.io.IOException;
import java.util.Random;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import colonialobfuscator.utils.NameGen;
import colonialobfuscator.utils.NodeUtils;

public class StringEncryption implements ClassModifier {
    @Override
    public void modify(ClassNode node) {
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
			method2 = NodeUtils.getMethod(NodeUtils.toNode(getClass().getName()), "enArr");
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
    }