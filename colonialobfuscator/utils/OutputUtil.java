package colonialobfuscator.utils;

import java.io.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

import colonialobfuscator.guis.ObfuscationPanel;
import colonialobfuscator.transforms.ClassModifier;
import colonialobfuscator.transforms.LocalVariables;
import colonialobfuscator.transforms.ModifierAccessCode;
import colonialobfuscator.transforms.ModifierBooleans;
import colonialobfuscator.transforms.ModifierFlow;
import colonialobfuscator.transforms.ModifierNumbers;
import colonialobfuscator.transforms.ModifierOptimizeCheck;
import colonialobfuscator.transforms.StringEncryption;

public class OutputUtil {
	private static JarOutputStream outputStream = null;
	public static Map<String, ClassNode> classes = new HashMap<>();
	public static Map<String, ClassNode> ToAdd = new HashMap<>();
	private static JarOutputStream finalOutputStream = null;
	public static List<ClassModifier> modules() {
		List<ClassModifier> modifier = new ArrayList<ClassModifier>(Arrays.asList());
		
		modifier.clear();
		if(ObfuscationPanel.NumberObfuscationCheckBox.isSelected()) {
			modifier.add(new ModifierNumbers());
		}
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

		return modifier;
	}

	public static void run(String input, String output) {
		/*try {
	        ZipFile zipFile = new ZipFile(input);
	        Enumeration<? extends ZipEntry> entries = zipFile.entries();
	        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(output));
	        
	        StringEncryption.Start();
	        
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

	                        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
	                        classNode.accept(cw);

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
	        	StringEncryption.END();
                       ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                       StringEncryption.unscrambleClass.accept(cw);
                       String name = StringEncryption.unscrambleClass.name + ".class";
                       if(ObfuscationPanel.FakeDirectoriesCheckBox.isSelected() && name.endsWith(".class")) {
                       	name += "/";
                       }
                       
                       ZipEntry newEntry = new ZipEntry(name);
                       out.putNextEntry(newEntry);
                       writeToFile(out, new ByteArrayInputStream(cw.toByteArray()));
	                   
	                   
	        
	            zipFile.close();
	            out.close();
	        }
		} catch (Exception e) {
			e.printStackTrace();
	}*/


		JarFile jarFile = null;
		try {
			jarFile = new JarFile(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		JarFile finalJarFile = jarFile;
		jarFile.stream().forEach(entry -> {
			try {
				if (entry.getName().endsWith(".class")) {
					final ClassReader classReader = new ClassReader(
							finalJarFile.getInputStream(entry));
					final ClassNode classNode = new ClassNode();
					classReader.accept(classNode, ClassReader.SKIP_DEBUG);
					classes.put(classNode.name, classNode);
				} else if (!entry.isDirectory()) {
					finalOutputStream.putNextEntry(new ZipEntry(entry.getName()));
					finalOutputStream
							.write(toByteArray(finalJarFile.getInputStream(entry)));
					finalOutputStream.closeEntry();
				}
			} catch (final Exception e) {
			//	e.printStackTrace();
			}
		});
		try {
			jarFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringEncryption.Start();
		try {
			outputStream = new JarOutputStream(
						new FileOutputStream(output));

		parseInput(input);
			classes.values().forEach(classNode -> {
				for (ClassModifier m : modules()) {
					m.modify(classNode);
				}
			});
			StringEncryption.END();
			classes.putAll(ToAdd);
			classes.values().forEach(classNode -> {
				ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
				try {
					classNode.accept(classWriter);//Add this ObfuscationPanel.FakeDirectoriesCheckBox.isSelected()
					final JarEntry jarEntry = new JarEntry(
							classNode.name.concat(".class"));
					outputStream.putNextEntry(jarEntry);
					outputStream.write(classWriter.toByteArray());
				} catch (final Exception e) {
					e.printStackTrace();
				}
			});

			//
			outputStream.setComment("https://github.com/ColonialBuilders/ColonialObfuscator");
			outputStream.close();


			
		} catch (IOException e) {
			e.printStackTrace();
		}

		StringEncryption.stringList.clear();
		ToAdd.clear(); 
		classes.clear();
        System.out.println("[Stopped] array size == " + modules().size());
    }
	private static void parseInput(String input) {
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		JarFile finalJarFile = jarFile;
		jarFile.stream().forEach(entry -> {
			try {
				if (entry.getName().endsWith(".class")) {
					final ClassReader classReader;
					classReader = new ClassReader(
							finalJarFile.getInputStream(entry));
					final ClassNode classNode = new ClassNode();
					classReader.accept(classNode, ClassReader.SKIP_DEBUG);
					classes.put(classNode.name, classNode);
				} else if (!entry.isDirectory()) {
					outputStream.putNextEntry(new ZipEntry(entry.getName()));
					outputStream
							.write(toByteArray(finalJarFile.getInputStream(entry)));
					outputStream.closeEntry();
				}
			} catch (final Exception e) {
				e.printStackTrace();
			}
		});
		try {
			jarFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static byte[] toByteArray(InputStream inputStream) throws IOException {
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final byte[] buffer = new byte[0xFFFF];
		int length;
		while ((length = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, length);
		}
		outputStream.flush();
		return outputStream.toByteArray();
	}
    
}
