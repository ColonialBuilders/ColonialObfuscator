package colonialobfuscator.utils;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import colonialobfuscator.guis.ObfuscationPanel;
import colonialobfuscator.transforms.ClassModifier;
import colonialobfuscator.transforms.LocalVariables;
import colonialobfuscator.transforms.ModifierAccessCode;
import colonialobfuscator.transforms.ModifierBooleans;
import colonialobfuscator.transforms.ModifierFlow;
import colonialobfuscator.transforms.ModifierOptimizeCheck;
import colonialobfuscator.transforms.StringEncryption;

public class OutputUtil {

	public static List<ClassModifier> modules() {
		List<ClassModifier> modifier = new ArrayList<ClassModifier>(Arrays.asList());
		
		modifier.clear();
		
		if(ObfuscationPanel.StringEcryptionCheckBox.isSelected()) {
			modifier.add(new StringEncryption());
		}
		if(ObfuscationPanel.flowObfuscationCheckBox.isSelected()) {
			modifier.add(new ModifierFlow());
		}
		if(ObfuscationPanel.OptimizeCheckBox.isSelected()) {
			modifier.add(new ModifierOptimizeCheck());
		}
		if(ObfuscationPanel.AccessCodeCheckBox.isSelected()) {
			modifier.add(new ModifierAccessCode());
		}
		if(ObfuscationPanel.BooleansCheckBox.isSelected()) {
			modifier.add(new ModifierBooleans());
		}
		if(ObfuscationPanel.localvariablesCheckBox.isSelected()) {
			modifier.add(new LocalVariables());
		}
		
		StringEncryption.Methods.clear();
		
		return modifier;
	}

	public static void run(String input, String output) {
		try {
	        ZipFile zipFile = new ZipFile(input);
	        Enumeration<? extends ZipEntry> entries = zipFile.entries();
	        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(output));
	        try {
	            while (entries.hasMoreElements()) {
	                ZipEntry entry = entries.nextElement();

                    
	                if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
	                	
	                    try (InputStream in = zipFile.getInputStream(entry)) {
	                        ClassReader cr = new ClassReader(in);
	                        ClassNode classNode = new ClassNode();
	                        cr.accept(classNode, 0);
	                        
	                        for (ClassModifier m : modules()) {
					m.modify(classNode);
				}

	                        ClassWriter cw = new ClassWriter(0);
	                        classNode.accept(cw);
	                        
	                        /*
	                         * FakeDirectories
	                         */
	                        String name = entry.getName();
	                        if(ObfuscationPanel.FakeDirectoriesCheckBox.isSelected() && name.endsWith(".class")) {
	                        	name += "/";
	                        }
	                        
	                        ZipEntry newEntry = new ZipEntry(name);
	                        out.putNextEntry(newEntry);
	                        writeToFile(out, new ByteArrayInputStream(cw.toByteArray()));
	                    }
	                } else {
	                    out.putNextEntry(entry);
	                    writeToFile(out, zipFile.getInputStream(entry));
	                }
	            }
	        } finally {
	            zipFile.close();
	            out.close();
	        }
		} catch (Exception e) {
			e.printStackTrace();
	}

        System.out.println("[Stopped] array size == " + modules().size());
    }

    private static void writeToFile(ZipOutputStream outputStream, InputStream inputStream) {
    	try {
            byte[] buffer = new byte[Byte.MAX_VALUE];
            try {
                while (inputStream.available() > 0) {
                    int data = inputStream.read(buffer);
                    outputStream.write(buffer, 0, data);
                }
            } finally {
                inputStream.close();
                outputStream.closeEntry();
            }
	} catch (Exception e) {}
    }
    
}
