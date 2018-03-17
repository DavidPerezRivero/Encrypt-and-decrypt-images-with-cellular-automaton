package window.panels;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class ButtonsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton openButton;
	private JButton encryptButton;
	private JButton decryptButton;
	private JComboBox<Object> roundsComboBox;
	private JComboBox<Object> radiusComboBox;
	private JSlider slider;
	private int sliderValue;
	private int radiusValue;
	private int roundsValue;
	private final Integer[] ROUNDS = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	private final Integer[] RADIUS = { 1, 2, 3, 4 };
	private final int TTS_MIN = 0;
	private final int TTS_MAX = 40;
	private final int TTS_SPACING = 10;
	private final String OPEN_TEXT = "Open Image...";
	private final String ENCRYPT_TEXT = "Encrypt Image";
	private final String DECRYPT_TEXT = "Decrypt Image";
	private final String ROUNDS_TEXT = "Rounds:";
	private final String RADIUS_TEXT = "Radius:";
	private final String TTS_TEXT = "Time to Sleep:";

	public ButtonsPanel() {
		configurePanel();
		inicializeComponents();
		addComponents();
		setDefaultVisibility();
	}

	private void configurePanel() {
		setLayout(new FlowLayout());
		setBackground(Color.LIGHT_GRAY);
	}

	private void inicializeComponents() {
		openButton = new JButton(OPEN_TEXT);
		encryptButton = new JButton(ENCRYPT_TEXT);
		decryptButton = new JButton(DECRYPT_TEXT);
		roundsComboBox = new JComboBox<Object>(ROUNDS);
		roundsComboBox.setSelectedIndex(0);
		radiusComboBox = new JComboBox<Object>(RADIUS);
		radiusComboBox.setSelectedIndex(0);
		slider = new JSlider(JSlider.HORIZONTAL, TTS_MIN, TTS_MAX, TTS_MIN);
		slider.setMajorTickSpacing(TTS_SPACING);
		slider.setMinorTickSpacing(TTS_MIN);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
	}

	private void addComponents() {
		add(openButton);
		add(encryptButton);
		add(decryptButton);
		add(new JLabel(ROUNDS_TEXT));
		add(roundsComboBox);
		add(new JLabel(RADIUS_TEXT));
		add(radiusComboBox);
		add(new JLabel(TTS_TEXT));
		add(slider);
	}

	private void setDefaultVisibility() {
		encryptButton.setEnabled(false);
		decryptButton.setEnabled(false);
		this.repaint();
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

	public JButton getOpenButton() {
		return openButton;
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
		roundsValue = RADIUS[index];
	}

	public void setRadiusValue(int index) {
		radiusValue = RADIUS[index];
	}

}
