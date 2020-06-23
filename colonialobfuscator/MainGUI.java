package colonialobfuscator;

import static colonialobfuscator.guis.FilePanel.input;
import static colonialobfuscator.guis.FilePanel.output;
import static colonialobfuscator.guis.SettingsPanel.massLaggField;
import static colonialobfuscator.guis.SettingsPanel.namesLenghtField;
import static colonialobfuscator.utils.OutputUtil.modules;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import colonialobfuscator.guis.FilePanel;
import colonialobfuscator.guis.ObfuscationPanel;
import colonialobfuscator.guis.SettingsPanel;
import colonialobfuscator.utils.OutputUtil;

/**
 * @author Iloliks229
 * easy gui for ColonialObfuscator
 */
public class MainGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				MainGUI frame = new MainGUI();
				frame.setVisible(true);
			} catch (Exception e) {}
		});
	}

	public MainGUI() {
		setTitle("ColonialObfuscator GUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 881, 522);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.NORTH);
		
		FilePanel file = new FilePanel();
		tabbedPane.addTab("File", file);
		
		ObfuscationPanel obf = new ObfuscationPanel();
		tabbedPane.addTab("Obfuscation", obf);
		
		SettingsPanel setSetting = new SettingsPanel();
		tabbedPane.addTab("Settings", setSetting);
		
		JPanel buttonBar = new JPanel();
		buttonBar.setBackground(Color.WHITE);
		contentPane.add(buttonBar, BorderLayout.SOUTH);
		buttonBar.setLayout(new BorderLayout(0, 0));
		
		JPanel buttons = new JPanel();
		buttons.setBackground(Color.WHITE);
		buttonBar.add(buttons, BorderLayout.EAST);
		
		JButton obfuscateButton = new JButton("Obfuscate");
		obfuscateButton.setVerticalAlignment(SwingConstants.TOP);
		obfuscateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(input() == null || input().trim().isEmpty()) {
					JOptionPane
					.showMessageDialog(null, "ZipStream is skipped");
				}
                if(modules().size() < 1 || modules().isEmpty()) {
                	JOptionPane
                	.showMessageDialog(null, " modules size < 1 ");
                	return;
                }
        		if(massLaggField.getText().isEmpty() || massLaggField.getText() == null || !massLaggField.isEditable() || !massLaggField.isEnabled()) {
        			massLaggField.setText("1"); // internal string not encrypted fixed
        		}
        		if(namesLenghtField.getText().isEmpty() || namesLenghtField.getText() == null) {
        			namesLenghtField.setText("40");
        		}
				OutputUtil.run(input(), output());
			}
		});
		buttons.add(obfuscateButton);
	}

}
