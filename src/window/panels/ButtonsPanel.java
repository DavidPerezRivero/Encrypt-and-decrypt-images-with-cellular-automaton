package window.panels;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cellularAutomatons.cellularAutomaton1.CellularAutomaton1;
import window.Window;

public class ButtonsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static JButton openButton;
	private static JButton encrypt1Button;
	private static JButton decrypt1Button;
	CellularAutomaton1 cellularAutomaton;
	private static JComboBox<Object> roundsSelection;
	private static JComboBox<Object> radiusSelection;
	private JSlider slider;
	private final Integer[] ROUNDS = {1, 2, 3, 4};
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
		setLayout(new FlowLayout());
		setBorder(BorderFactory.createLineBorder(Color.black));
		setBackground(Color.LIGHT_GRAY);
		inicializeComponents();
		addComponents();
		addListeners();
		setDefaultVisibility();
	}

	private void inicializeComponents() {
		openButton = new JButton(OPEN_TEXT);
		encrypt1Button = new JButton(ENCRYPT_TEXT);
		decrypt1Button = new JButton(DECRYPT_TEXT);
		roundsSelection = new JComboBox<Object>(ROUNDS);
		roundsSelection.setSelectedIndex(0);
		radiusSelection = new JComboBox<Object>(ROUNDS);
		radiusSelection.setSelectedIndex(0);
		slider = new JSlider(JSlider.HORIZONTAL, TTS_MIN, TTS_MAX, TTS_MIN);
		slider.setMajorTickSpacing(TTS_SPACING);
		slider.setMinorTickSpacing(TTS_MIN);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
	}

	private void addComponents() {
		add(openButton);
		add(encrypt1Button);
		add(decrypt1Button);
		add(new JLabel(ROUNDS_TEXT));
		add(roundsSelection);
		add(new JLabel(RADIUS_TEXT));
		add(radiusSelection);
		add(new JLabel(TTS_TEXT));
		add(slider);
	}

	private void setDefaultVisibility() {
		encrypt1Button.setEnabled(false);
		decrypt1Button.setEnabled(false);
		this.repaint();
	}
	
	private void addListeners() {
		addOpenButtonListener();
		addEncryptButtonListener();
		addDecryptButtonListener();
		addSliderListener();
	}

	private void addOpenButtonListener() {
		openButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openImage();
			}
		});
	}
	
	private void openImage() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("/Users/"));
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
			try {
				BufferedImage image = ImageIO.read(selectedFile);
				if (image == null) {
					openImage();
				} else {
					Window.openImage(image);
					encrypt1Button.setEnabled(true);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void addEncryptButtonListener() {
		encrypt1Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cellularAutomaton = new CellularAutomaton1((BufferedImage) ImagePanel.image, 
						ROUNDS[roundsSelection.getSelectedIndex()],
						ROUNDS[radiusSelection.getSelectedIndex()],
						slider.getValue());
				encrypt1Button.setEnabled(false);
				openButton.setEnabled(false);
				cellularAutomaton.encrypt();
			}
		});
	}
	
	public static void finishEncrypt() {
		decrypt1Button.setEnabled(true);
	}
	
	private void addDecryptButtonListener() {
		decrypt1Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cellularAutomaton.decrypt();
				decrypt1Button.setEnabled(false);
			}
		});
	}
	
	public static void finishDecrypt() {
		encrypt1Button.setEnabled(true);
		openButton.setEnabled(true);
	}
	
	private void addSliderListener() {
		slider.addChangeListener(new ChangeListener () {
			@Override
			public void stateChanged(ChangeEvent e) {
				CellularAutomaton1.TTS = slider.getValue();
			}
		});
	}
}
