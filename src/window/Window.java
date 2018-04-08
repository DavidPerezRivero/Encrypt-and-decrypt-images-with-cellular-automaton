package window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

import window.panels.ButtonsPanel;
import window.panels.ImagePanel;
import window.panels.InfoPanel;

/**
 * Ventana del programa
 *
 */
public class Window extends JFrame {
	private static final long serialVersionUID = 1L;
	private ButtonsPanel buttonsPanel;
	private ImagePanel imagePanel;
	private InfoPanel infoPanel;
	private final String CELLULAR_TEXT = "Encrypt/Decrypt images with Cellular Automaton";
	private final int WINDOW_WIDTH = 1300;
	private final int WINDOW_HEIGHT = 1000;

	/**
	 * Constructor de la ventana
	 */
	public Window() {
		super();
		configureWindow();
		inicializeComponents();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	/**
	 * Configuracion de la ventana
	 */
	private void configureWindow() {
		setTitle(CELLULAR_TEXT);
		setResizable(true);
		setVisible(true);
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
	}

	/**
	 * Inicializa los distintos paneles que componen la ventana
	 */
	private void inicializeComponents() {
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.setBorder(BorderFactory.createLineBorder(Color.black));
		container.setBackground(Color.LIGHT_GRAY);
		buttonsPanel = new ButtonsPanel();
		infoPanel = new InfoPanel();
		imagePanel = new ImagePanel();
		container.add(buttonsPanel, BorderLayout.CENTER);
		container.add(infoPanel, BorderLayout.EAST);
		add(imagePanel, BorderLayout.CENTER);
		add(container, BorderLayout.NORTH);
	}

	public void openImage(BufferedImage image) {
		imagePanel.setImagePanel(image);
	}

	public BufferedImage getImage() {
		return imagePanel.getImage();
	}

	public int getRadius() {
		return buttonsPanel.getRadius();
	}

	public int getRounds() {
		return buttonsPanel.getRounds();
	}

	public int getTTS() {
		return buttonsPanel.getTTS();
	}

	public JButton getOpenToEncryptButton() {
		return buttonsPanel.getOpenToEncryptButton();
	}

	public JButton getEncryptButton() {
		return buttonsPanel.getEncryptButton();
	}

	public JButton getDecryptButton() {
		return buttonsPanel.getDecryptButton();
	}

	public JComboBox<Object> getRadiusComboBox() {
		return buttonsPanel.getRadiusComboBox();
	}

	public JComboBox<Object> getRoundsComboBox() {
		return buttonsPanel.getRoundsComboBox();
	}

	public JSlider getSlider() {
		return buttonsPanel.getSlider();
	}

	public void setSliderValue(int value) {
		buttonsPanel.setSliderValue(value);
	}

	public void setRoundsValue(int index) {
		buttonsPanel.setRoundsValue(index);
	}

	public void setRadiusValue(int index) {
		buttonsPanel.setRadiusValue(index);
	}

	public JButton getSaveEncryptedButton() {
		return buttonsPanel.getSaveEncryptedButton();
	}
	
	public JButton getSaveDecryptedButton() {
		return buttonsPanel.getSaveDecryptedButton();
	}

	public JButton getOpenToDecryptButton() {
		return buttonsPanel.getOpenToDecryptButton();
	}
	
	public void openToEncrypt() {
		buttonsPanel.openToEncrypt();
	}
	
	public void encrypt() {
		buttonsPanel.encrypt();
	}
	
	public void openToDecrypt() {
		buttonsPanel.openToDecrypt();
	}
	
	public void decrypt() {
		buttonsPanel.decrypt();
	}
	
	public void saveDecryptedImage() {
		buttonsPanel.saveDecryptedImage();
	}
	
	public void saveEncryptedImage() {
		buttonsPanel.saveEncryptedImage();
	}
	
	public long getSeed() {
		return buttonsPanel.getSeed();
	}
	
	public void setSeed(String seed) {
		buttonsPanel.setSeed(seed);
	}
}