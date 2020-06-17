package colonialobfuscator.guis;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.UIManager;

/**
 * @author Iloliks229
 * easy panel for ColonialObfuscator
 */
public class SettingsPanel extends JPanel {
	public static JTextField massLaggField;
	public static JTextField namesLenghtField;
	
	public static JCheckBox massLaggEnabledCheckBox;
	
	private JPanel NamesPanel;
	
	private JLabel lblNewLabel;
	
	/**
	 * Create the panel.
	 */
	public SettingsPanel() {
		setBackground(Color.WHITE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{140, 98, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel MassLaggPanel = new JPanel();
		MassLaggPanel.setBackground(Color.WHITE);
		MassLaggPanel.setBorder(new TitledBorder(null, "MassLagg", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_MassLaggPanel = new GridBagConstraints();
		gbc_MassLaggPanel.insets = new Insets(0, 0, 5, 0);
		gbc_MassLaggPanel.fill = GridBagConstraints.BOTH;
		gbc_MassLaggPanel.gridx = 0;
		gbc_MassLaggPanel.gridy = 0;
		add(MassLaggPanel, gbc_MassLaggPanel);
		GridBagLayout gbl_MassLaggPanel = new GridBagLayout();
		gbl_MassLaggPanel.columnWidths = new int[]{0, 0, 0};
		gbl_MassLaggPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_MassLaggPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_MassLaggPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		MassLaggPanel.setLayout(gbl_MassLaggPanel);
		
		massLaggEnabledCheckBox = new JCheckBox("Enabled");
		massLaggEnabledCheckBox.setBackground(Color.WHITE);
		massLaggEnabledCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean enabled = massLaggEnabledCheckBox.isSelected();
				massLaggField.setEditable(enabled);
			}
		});
		massLaggEnabledCheckBox.setEnabled(false);
		GridBagConstraints gbc_massLaggEnabledCheckBox = new GridBagConstraints();
		gbc_massLaggEnabledCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_massLaggEnabledCheckBox.gridx = 0;
		gbc_massLaggEnabledCheckBox.gridy = 0;
		MassLaggPanel.add(massLaggEnabledCheckBox, gbc_massLaggEnabledCheckBox);
		
		massLaggField = new JTextField();
		massLaggField.setText("0");
		massLaggField.setEditable(false);
		GridBagConstraints gbc_massLaggField = new GridBagConstraints();
		gbc_massLaggField.insets = new Insets(0, 0, 5, 0);
		gbc_massLaggField.fill = GridBagConstraints.HORIZONTAL;
		gbc_massLaggField.gridx = 1;
		gbc_massLaggField.gridy = 1;
		MassLaggPanel.add(massLaggField, gbc_massLaggField);
		massLaggField.setColumns(10);
		
		NamesPanel = new JPanel();
		NamesPanel.setBackground(Color.WHITE);
		NamesPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Names", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_NamesPanel = new GridBagConstraints();
		gbc_NamesPanel.fill = GridBagConstraints.BOTH;
		gbc_NamesPanel.gridx = 0;
		gbc_NamesPanel.gridy = 1;
		add(NamesPanel, gbc_NamesPanel);
		GridBagLayout gbl_NamesPanel = new GridBagLayout();
		gbl_NamesPanel.columnWidths = new int[]{0, 0, 0};
		gbl_NamesPanel.rowHeights = new int[]{0, 0};
		gbl_NamesPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_NamesPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		NamesPanel.setLayout(gbl_NamesPanel);
		
		lblNewLabel = new JLabel("Lenght:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		NamesPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		namesLenghtField = new JTextField();
		namesLenghtField.setText("40");
		GridBagConstraints gbc_namesLenghtField = new GridBagConstraints();
		gbc_namesLenghtField.fill = GridBagConstraints.HORIZONTAL;
		gbc_namesLenghtField.gridx = 1;
		gbc_namesLenghtField.gridy = 0;
		NamesPanel.add(namesLenghtField, gbc_namesLenghtField);
		namesLenghtField.setColumns(10);

	}

}
