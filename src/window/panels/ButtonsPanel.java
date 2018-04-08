package window.panels;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class ButtonsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	// Elements
	public static JButton openToEncryptButton;
	public static JButton openToDecryptButton;
	public static JButton encryptButton;
	public static JButton decryptButton;
	public static JButton saveEncryptedButton;
	public static JButton saveDecryptedButton;
	public static JTextField seedText;
	private JComboBox<Object> roundsComboBox;
	private JComboBox<Object> radiusComboBox;
	private JSlider slider;
	// Values of encrypt/decrypt parameters
	private int sliderValue;
	private int radiusValue;
	private int roundsValue;
	// Constants values
	private final Integer[] ROUNDS = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	private final Integer[] RADIUS = { 1, 2, 3, 4 };
	private final String DEFAULT_SEED = "29121994";
	private final int TTS_MIN = 0;
	private final int TTS_MAX = 40;
	private final int TTS_SPACING = 10;
	private final int SIZE_SEED_TEXT = 9;
	private final String OPEN_TO_ENCRYPT_TEXT = "Open Image To Encrypt...";
	private final String OPEN_TO_DECRYPT_TEXT = "Open Image To Decrypt...";
	private final String ENCRYPT_TEXT = "Encrypt Image";
	private final String DECRYPT_TEXT = "Decrypt Image";
	private final String ROUNDS_TEXT = "Rounds:";
	private final String RADIUS_TEXT = "Radius:";
	private final String TTS_TEXT = "Time to Sleep:";
	private final String SAVE_ENCRYPTED_TEXT = "Save Encrypted Image";
	private final String SAVE_DECRYPTED_TEXT = "Save Decrypted Image";
	private final String SEED_TEXT = "Seed:";

	/**
	 * Constructor del panel
	 */
	public ButtonsPanel() {
		configurePanel();
		inicializeComponents();
		inicializeParameters();
		addComponents();
		setDefaultVisibility();
	}

	/**
	 * Configura el panel
	 */
	private void configurePanel() {
		setLayout(new FlowLayout());
		setBackground(Color.LIGHT_GRAY);
	}

	/**
	 * Inicializa los componentes del panel
	 */
	private void inicializeComponents() {
		openToEncryptButton = new JButton(OPEN_TO_ENCRYPT_TEXT);
		openToDecryptButton = new JButton(OPEN_TO_DECRYPT_TEXT);
		encryptButton = new JButton(ENCRYPT_TEXT);
		decryptButton = new JButton(DECRYPT_TEXT);
		saveEncryptedButton = new JButton(SAVE_ENCRYPTED_TEXT);
		saveDecryptedButton = new JButton(SAVE_DECRYPTED_TEXT);
		roundsComboBox = new JComboBox<Object>(ROUNDS);
		roundsComboBox.setSelectedIndex(0);
		radiusComboBox = new JComboBox<Object>(RADIUS);
		radiusComboBox.setSelectedIndex(0);
		slider = new JSlider(JSlider.HORIZONTAL, TTS_MIN, TTS_MAX, TTS_MIN);
		slider.setMajorTickSpacing(TTS_SPACING);
		slider.setMinorTickSpacing(TTS_MIN);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		seedText = new JTextField(SIZE_SEED_TEXT);
		seedText.setText(DEFAULT_SEED);
	}
	
	/**
	 * Añade los distintos elementos al panel
	 */
	private void addComponents() {
		add(openToEncryptButton);
		add(openToDecryptButton);
		add(encryptButton);
		add(decryptButton);
		add(saveEncryptedButton);
		add(saveDecryptedButton);
		add(new JLabel(ROUNDS_TEXT));
		add(roundsComboBox);
		add(new JLabel(RADIUS_TEXT));
		add(radiusComboBox);
		add(new JLabel(SEED_TEXT));
		add(seedText);
		add(new JLabel(TTS_TEXT));
		add(slider);
	}
	
	/**
	 * Inicializa los parametros
	 */
	private void inicializeParameters() {
		sliderValue = 0;
		radiusValue = 0;
		roundsValue = 0;
	}

	
	
	/**
	 * Establece la visibilidad de los botones y la
	 * posibilidad de modificar los parametros
	 */
	private void setDefaultVisibility() {
		encryptButton.setVisible(false);
		decryptButton.setVisible(false);
		saveEncryptedButton.setVisible(false);
		saveDecryptedButton.setVisible(false);
		this.repaint();
	}
	
	/**
	 * Establece la visibilidad de los botones y la
	 * posibilidad de modificar los parametros
	 */
	public void openToEncrypt() {
		openToEncryptButton.setVisible(false);
		openToDecryptButton.setVisible(false);
		saveEncryptedButton.setVisible(false);
		saveDecryptedButton.setVisible(false);
		encryptButton.setVisible(true);
		radiusComboBox.setEnabled(true);
		roundsComboBox.setEnabled(true);
		seedText.setEnabled(true);
	}
	
	/**
	 * Establece la visibilidad de los botones y la
	 * posibilidad de modificar los parametros
	 */
	public void openToDecrypt() {
		openToEncryptButton.setVisible(false);
		openToDecryptButton.setVisible(false);
		decryptButton.setVisible(true);
		saveEncryptedButton.setVisible(false);
		saveDecryptedButton.setVisible(false);
		radiusComboBox.setEnabled(false);
		roundsComboBox.setEnabled(false);
		seedText.setEnabled(false);
	}
	
	/**
	 * Establece la visibilidad de los botones y la
	 * posibilidad de modificar los parametros
	 */
	public void encrypt() {
		saveEncryptedButton.setEnabled(true);
		saveEncryptedButton.setVisible(true);
		openToEncryptButton.setVisible(true);
		openToDecryptButton.setVisible(true);
		encryptButton.setVisible(false);
		radiusComboBox.setEnabled(false);
		roundsComboBox.setEnabled(false);
		seedText.setEnabled(false);
	}
	
	/**
	 * Establece la visibilidad de los botones y la
	 * posibilidad de modificar los parametros
	 */
	public void decrypt() {
		saveDecryptedButton.setEnabled(true);
		saveDecryptedButton.setVisible(true);
		saveEncryptedButton.setVisible(false);
		openToEncryptButton.setVisible(true);
		openToDecryptButton.setVisible(true);
		decryptButton.setVisible(false);
		radiusComboBox.setEnabled(false);
		roundsComboBox.setEnabled(false);
		seedText.setEnabled(false);
	}
	
	/**
	 * Establece la visibilidad de los botones y la
	 * posibilidad de modificar los parametros
	 */
	public void saveEncryptedImage() {
		saveEncryptedButton.setEnabled(false);
	}
	
	/**
	 * Establece la visibilidad de los botones y la
	 * posibilidad de modificar los parametros
	 */
	public void saveDecryptedImage() {
		saveDecryptedButton.setEnabled(false);
	}

	public int getTTS() {
		return sliderValue;
	}

	public int getRounds() {
		return roundsValue;
	}

	public int getRadius() {
		return radiusValue;
	}

	public JButton getOpenToEncryptButton() {
		return openToEncryptButton;
	}

	public JButton getEncryptButton() {
		return encryptButton;
	}

	public JButton getDecryptButton() {
		return decryptButton;
	}

	public JComboBox<Object> getRadiusComboBox() {
		return radiusComboBox;
	}

	public JComboBox<Object> getRoundsComboBox() {
		return roundsComboBox;
	}

	public JSlider getSlider() {
		return slider;
	}

	public void setSliderValue(int value) {
		sliderValue = value;
	}

	public void setRoundsValue(int index) {
		roundsValue = ROUNDS[index];
	}

	public void setRadiusValue(int index) {
		radiusValue = RADIUS[index];
	}

	public JButton getSaveEncryptedButton() {
		return saveEncryptedButton;
	}
	
	public JButton getSaveDecryptedButton() {
		return saveDecryptedButton;
	}

	public JButton getOpenToDecryptButton() {
		return openToDecryptButton;
	}

	public long getSeed() {
		return Long.valueOf(seedText.getText());
	}

	public void setSeed(String seed) {
		seedText.setText(seed);
	}
}
