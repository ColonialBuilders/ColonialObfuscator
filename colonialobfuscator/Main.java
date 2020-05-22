package colonialobfuscator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import colonialobfuscator.transforms.ClassModifier;
import colonialobfuscator.transforms.LocalVariables;
import colonialobfuscator.transforms.ModifierAccessCode;
import colonialobfuscator.transforms.ModifierBooleans;
import colonialobfuscator.transforms.ModifierFlow;
import colonialobfuscator.transforms.ModifierOptimizeCheck;
import colonialobfuscator.transforms.StringEncryption;

public class Main extends JFrame implements ActionListener {
    //static final ObjectMapper mapper = new ObjectMapper();
    static ZipFile in;
    static ZipOutputStream out;
    public static List<Object> objects = new ArrayList<>();
    static List<ClassModifier> modifier = Arrays.asList(new ModifierBooleans(),
            new ModifierAccessCode(), new ModifierOptimizeCheck(),
            new ModifierFlow(), new StringEncryption(), new LocalVariables());
    
    
    static JTextField t; 
    static JTextField t2; 
      static JFrame f; 
      static JButton b; 
      static JLabel l; 
      public static void main(String[] args) 
    { 
    	  //D:\OBFTEST\ColonialObfuscator\LOGIN.jar
      	f = new JFrame("textfield"); 
        l = new JLabel("nothing entered"); 
        b = new JButton("obfuscate"); 
        b.addActionListener(new Main()); 
        t = new JTextField("input", 16); 
        t2 = new JTextField("output", 16); 
        JPanel p = new JPanel(); 
        p.add(t); 
        p.add(t2); 
        p.add(b); 
        p.add(l); 
        f.add(p); 
        f.setSize(300, 300); 
        f.show(); 
    } 
      public void actionPerformed(ActionEvent e) 
    { 
        String s = e.getActionCommand(); 
        if (s.equals("obfuscate")) { 
        	try {
				run(t.getText(), t2.getText());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        }
    } 

    
    
    public static void run(String input, String output) throws Exception {
    	
    	
    	
    	
    	
       // if (args.length == 0) {

        ClassPathHacker.addFile(new File(input));
        in = new ZipFile(new File(input), ZipFile.OPEN_READ);
        File f = new File(output);
        f.delete();
        out = new ZipOutputStream(new FileOutputStream(f));

        Enumeration<? extends ZipEntry> entries = in.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            String filename = entry.getName();
            if (!filename.endsWith("/")) {
                byte[] data = getBytesFromInputStream(in.getInputStream(entry));

                if (filename.endsWith(".class")) {
                    ClassNode classNode = new ClassNode();
                    ClassReader classReader = new ClassReader(data);
                    classReader.accept(classNode, 0);
  //                  if(c) {
    //                	classNode.innerClasses.clear();
      //              	classNode.sourceFile = null;
        //            	classNode.attrs.clear();
          //          	classNode.nestHostClass = null;
            //        	classNode.nestMembers.clear();
              //      	classNode.outerClass = null;
                //    	System.out.println("Optimized " + classNode.name);
                  //  } else {
                    for (ClassModifier m : modifier)
                        try {
                            m.modify(classNode);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                  //  }
                    try {
                        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
                        classNode.accept(classWriter);
                        data = classWriter.toByteArray();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println("filename: " + filename);
                    }
                }
                //if (keep(filename, config.getFiles(), true)) {
                    out.putNextEntry(new ZipEntry(filename));
                    out.write(data);
                    out.closeEntry();
                //}
            }
        }

        /*try {
            out.putNextEntry(new ZipEntry(DeobfuscaterDump.getFile() + ".class"));
            out.write(DeobfuscaterDump.dump());
            out.closeEntry();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        out.close();
        System.out.println("array size: " + objects.size());
        
    }

    public static byte[] getBytesFromInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        byte[] buffer = new byte[0xFFFF];
        for (int len = is.read(buffer); len != -1; len = is.read(buffer))
            os.write(buffer, 0, len);

        return os.toByteArray();
    }

    public static String getName(int i) {
        return new String(new char[]{(char) (i >= '.' ? i + 2 : i)});
    }
}
