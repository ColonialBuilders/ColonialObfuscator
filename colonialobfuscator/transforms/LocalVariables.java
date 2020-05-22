package colonialobfuscator.transforms;

import java.io.IOException;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ParameterNode;

import colonialobfuscator.utils.NameGen;
import colonialobfuscator.utils.NodeUtils;

public class LocalVariables implements ClassModifier {
    @Override
    public void modify(ClassNode node) {
    	for(MethodNode method : node.methods) {
		for(LocalVariableNode var : method.localVariables) {
			var.name = "ColonialObfuscator_" + NameGen.String(10);
		}
		for(ParameterNode var : method.parameters) {
			var.name = "ColonialObfuscator_" + NameGen.String(10);
		}
    	}
    }
}