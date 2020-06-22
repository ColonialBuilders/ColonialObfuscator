package colonialobfuscator.transforms;

import static colonialobfuscator.guis.SettingsPanel.massLaggEnabledCheckBox;
import static colonialobfuscator.guis.SettingsPanel.massLaggField;
import static colonialobfuscator.guis.SettingsPanel.namesLenghtField;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
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
	public static ArrayList<String> Methods = new ArrayList<String>();
	boolean MoreLagg = massLaggEnabledCheckBox.isSelected();
	@Override
	public void modify(ClassNode node) {
		int Pass = MoreLagg ? Integer.parseInt(massLaggField.getText()) : 1;
		for (int b = 0; b < Pass; b++) {
			//TODO Add BitShift
			
			
			
			MethodNode method = null;
			try {
				method = NodeUtils.getMethod(NodeUtils.toNode(getClass().getName()), "DeWithXOR");
			} catch (IOException e) {
				e.printStackTrace();
			}
			method.access = ACC_PRIVATE | ACC_STATIC;
			method.name = NameGen.colonial() + NameGen.String(Integer.parseInt(namesLenghtField.getText()));
			for(LocalVariableNode var : method.localVariables) {
				var.name = NameGen.colonial() + NameGen.String(Integer.parseInt(namesLenghtField.getText()));
			}
			int key = new Random().nextInt(9000) + 20000;
			for(AbstractInsnNode i : method.instructions) {
				if(i instanceof LdcInsnNode) {
			//		if((int)((LdcInsnNode)i).cst >= 1) {
						((LdcInsnNode)i).cst = key;
			//		}
				}
			}
			for(LocalVariableNode var : method.localVariables) {
				var.name = NameGen.colonial() + NameGen.String(Integer.parseInt(namesLenghtField.getText()));
			}
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
					mn.instructions.insertBefore(ain, new LdcInsnNode(EnWithXOR(s, key)));
					mn.instructions.insertBefore(ain, new MethodInsnNode(INVOKESTATIC, node.name, method.name, method.desc));
					
					mn.instructions.remove(ain);

				}
			}
			
			
			node.methods.add(method);
			Methods.add(method.name);
		}
	}
/*
 *     static String EnWithBIT(String a, int Key) {
    	int key = Key;
    	StringBuilder b = new StringBuilder();
    	for(char d : a.toCharArray()) {
    		b.append((char) (d << key));
    	  
    	}
    	return b.toString();
    }
    static String DeWithBIT(String a) {
    	int key = 10000000;
    	StringBuilder b = new StringBuilder();
    	for(char d : a.toCharArray()) {
    		b.append((char) (d >> key));
    	  
    	}
 */
    static String EnWithXOR(String a, int Key) {
    	int key = Key;
    	StringBuilder b = new StringBuilder();
    	for(char d : a.toCharArray()) {
    		b.append((char) (d ^ key));
    	  
    	}
    	return b.toString();
    }
    static String DeWithXOR(String a) {
    	int key = 10000000;
    	StringBuilder b = new StringBuilder();
    	for(char d : a.toCharArray()) {
    		b.append((char) (d ^ key));
    	  
    	}
    	return b.toString();
    }
}