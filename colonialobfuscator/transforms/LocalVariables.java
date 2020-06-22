package colonialobfuscator.transforms;

import static colonialobfuscator.guis.SettingsPanel.namesLenghtField;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ParameterNode;

import colonialobfuscator.utils.NameGen;

public class LocalVariables implements ClassModifier {
	@Override
	public void modify(ClassNode node) {
		for(MethodNode method : node.methods) {
			for(LocalVariableNode var : method.localVariables) {
				var.name = NameGen.colonial() + NameGen.String(Integer.parseInt(namesLenghtField.getText()));
			}
			if(method.parameters != null && method.parameters.size() > 0) {
			for(ParameterNode var : method.parameters) {
				var.name = NameGen.colonial() + NameGen.String(Integer.parseInt(namesLenghtField.getText()));
			}
		}
		}
	}
}