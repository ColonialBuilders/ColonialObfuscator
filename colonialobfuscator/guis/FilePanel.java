package colonialobfuscator.guis;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * @author Iloliks229
 * easy panel for ColonialObfuscator
 */
public class FilePanel extends JPanel {
	public static JTextField outputField;
	public static JTextField inputField;
	
	private java.io.File Path;

	/**
	 * Create the panel.
	 */
	public FilePanel() {
		setBackground(Color.WHITE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 468, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("File:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		
		inputField = new JTextField();
		GridBagConstraints gbc_inputField = new GridBagConstraints();
		gbc_inputField.insets = new Insets(0, 0, 5, 5);
		gbc_inputField.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputField.gridx = 1;
		gbc_inputField.gridy = 0;
		add(inputField, gbc_inputField);
		inputField.setColumns(10);
		
		JButton inputButton = new JButton("Select");
		inputButton.addActionListener((e) -> {
            JFileChooser chooser = new JFileChooser();
            if (inputField.getText() != null && !inputField.getText().isEmpty()) {
                chooser.setSelectedFile(new java.io.File(inputField.getText()));
            }
            chooser.setMultiSelectionEnabled(false);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (Path != null)
                chooser.setCurrentDirectory(Path);
            int result = chooser.showOpenDialog(this);
            if (result == 0) {
                SwingUtilities.invokeLater(() -> {
                	inputField.setText(chooser.getSelectedFile().getAbsolutePath());
                	Path = chooser.getSelectedFile();
                });
            }
        });
		GridBagConstraints gbc_inputButton = new GridBagConstraints();
		gbc_inputButton.anchor = GridBagConstraints.WEST;
		gbc_inputButton.insets = new Insets(0, 0, 5, 0);
		gbc_inputButton.gridx = 2;
		gbc_inputButton.gridy = 0;
		add(inputButton, gbc_inputButton);
		
		JLabel lblNewLabel_1 = new JLabel("Output:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		outputField = new JTextField();
		GridBagConstraints gbc_outputField = new GridBagConstraints();
		gbc_outputField.insets = new Insets(0, 0, 5, 5);
		gbc_outputField.fill = GridBagConstraints.HORIZONTAL;
		gbc_outputField.gridx = 1;
		gbc_outputField.gridy = 1;
		add(outputField, gbc_outputField);
		outputField.setColumns(10);
		
		JButton outputButton = new JButton("Select");
		outputButton.addActionListener((e) -> {
            JFileChooser chooser = new JFileChooser();
            if (outputField.getText() != null && !outputField.getText().isEmpty()) {
                chooser.setSelectedFile(new java.io.File(outputField.getText()));
            }
            chooser.setMultiSelectionEnabled(false);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (Path != null)
                chooser.setCurrentDirectory(Path);
            int result = chooser.showOpenDialog(this);
            if (result == 0) {
                SwingUtilities.invokeLater(() -> {
                	outputField.setText(chooser.getSelectedFile().getAbsolutePath());
                	Path = chooser.getSelectedFile();
                });
            }
        });
		GridBagConstraints gbc_outputButton = new GridBagConstraints();
		gbc_outputButton.anchor = GridBagConstraints.WEST;
		gbc_outputButton.insets = new Insets(0, 0, 5, 0);
		gbc_outputButton.gridx = 2;
		gbc_outputButton.gridy = 1;
		add(outputButton, gbc_outputButton);

	}
	
	public static String output() {
		return outputField.getText();
	}
	
	public static String input() {
		return inputField.getText();
	}

}
