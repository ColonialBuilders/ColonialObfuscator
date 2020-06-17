package colonialobfuscator.guis;

import static colonialobfuscator.guis.SettingsPanel.massLaggEnabledCheckBox;
import static colonialobfuscator.guis.SettingsPanel.massLaggField;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 * @author Iloliks229
 * easy panel for ColonialObfuscator
 */
public class ObfuscationPanel extends JPanel {

	public static JCheckBox StringEcryptionCheckBox;
	public static JCheckBox AccessCodeCheckBox;
	public static JCheckBox BooleansCheckBox;
	public static JCheckBox flowObfuscationCheckBox;
	public static JCheckBox OptimizeCheckBox;
	public static JCheckBox localvariablesCheckBox;
	public static JCheckBox FakeDirectoriesCheckBox;
	
	/**
	 * Create the panel.
	 */
	public ObfuscationPanel() {
		setBackground(Color.WHITE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		StringEcryptionCheckBox = new JCheckBox("StringEncryption");
		StringEcryptionCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean enabled = StringEcryptionCheckBox.isSelected();
				massLaggEnabledCheckBox.setEnabled(enabled);
				massLaggField.setEnabled(enabled);
			}
		});
		StringEcryptionCheckBox.setBackground(Color.WHITE);
		GridBagConstraints gbc_StringEcryptionCheckBox = new GridBagConstraints();
		gbc_StringEcryptionCheckBox.anchor = GridBagConstraints.WEST;
		gbc_StringEcryptionCheckBox.insets = new Insets(0, 0, 5, 0);
		gbc_StringEcryptionCheckBox.gridx = 0;
		gbc_StringEcryptionCheckBox.gridy = 0;
		add(StringEcryptionCheckBox, gbc_StringEcryptionCheckBox);
		
		AccessCodeCheckBox = new JCheckBox("AccessCode");
		AccessCodeCheckBox.setBackground(Color.WHITE);
		GridBagConstraints gbc_AccessCodeCheckBox = new GridBagConstraints();
		gbc_AccessCodeCheckBox.anchor = GridBagConstraints.WEST;
		gbc_AccessCodeCheckBox.insets = new Insets(0, 0, 5, 0);
		gbc_AccessCodeCheckBox.gridx = 0;
		gbc_AccessCodeCheckBox.gridy = 1;
		add(AccessCodeCheckBox, gbc_AccessCodeCheckBox);
		
	    BooleansCheckBox = new JCheckBox("Booleans");
		BooleansCheckBox.setBackground(Color.WHITE);
		GridBagConstraints gbc_BooleansCheckBox = new GridBagConstraints();
		gbc_BooleansCheckBox.anchor = GridBagConstraints.WEST;
		gbc_BooleansCheckBox.insets = new Insets(0, 0, 5, 0);
		gbc_BooleansCheckBox.gridx = 0;
		gbc_BooleansCheckBox.gridy = 2;
		add(BooleansCheckBox, gbc_BooleansCheckBox);
		
		flowObfuscationCheckBox = new JCheckBox("FlowObfuscation");
		flowObfuscationCheckBox.setBackground(Color.WHITE);
		GridBagConstraints gbc_flowObfuscationCheckBox = new GridBagConstraints();
		gbc_flowObfuscationCheckBox.anchor = GridBagConstraints.WEST;
		gbc_flowObfuscationCheckBox.insets = new Insets(0, 0, 5, 0);
		gbc_flowObfuscationCheckBox.gridx = 0;
		gbc_flowObfuscationCheckBox.gridy = 3;
		add(flowObfuscationCheckBox, gbc_flowObfuscationCheckBox);
		
		OptimizeCheckBox = new JCheckBox("Optimize");
		OptimizeCheckBox.setBackground(Color.WHITE);
		GridBagConstraints gbc_OptimizeCheckBox = new GridBagConstraints();
		gbc_OptimizeCheckBox.insets = new Insets(0, 0, 5, 0);
		gbc_OptimizeCheckBox.anchor = GridBagConstraints.WEST;
		gbc_OptimizeCheckBox.gridx = 0;
		gbc_OptimizeCheckBox.gridy = 4;
		add(OptimizeCheckBox, gbc_OptimizeCheckBox);
		
		localvariablesCheckBox = new JCheckBox("LocalVariables");
		localvariablesCheckBox.setBackground(Color.WHITE);
		GridBagConstraints gbc_localvariablesCheckBox = new GridBagConstraints();
		gbc_localvariablesCheckBox.insets = new Insets(0, 0, 5, 0);
		gbc_localvariablesCheckBox.anchor = GridBagConstraints.WEST;
		gbc_localvariablesCheckBox.gridx = 0;
		gbc_localvariablesCheckBox.gridy = 5;
		add(localvariablesCheckBox, gbc_localvariablesCheckBox);
		
		FakeDirectoriesCheckBox = new JCheckBox("FakeDirectories");
		FakeDirectoriesCheckBox.setBackground(Color.WHITE);
		GridBagConstraints gbc_FakeDirectoriesCheckBox = new GridBagConstraints();
		gbc_FakeDirectoriesCheckBox.anchor = GridBagConstraints.WEST;
		gbc_FakeDirectoriesCheckBox.gridx = 0;
		gbc_FakeDirectoriesCheckBox.gridy = 6;
		add(FakeDirectoriesCheckBox, gbc_FakeDirectoriesCheckBox);

	}

}
