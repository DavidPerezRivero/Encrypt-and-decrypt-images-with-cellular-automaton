package window.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import cellularAutomatons.cellularAutomaton1.CellularAutomaton1;
import window.Window;

public class ButtonsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton openButton;
	private JButton encrypt1Button;
	private JButton encrypt2Button;
	private JButton decrypt1Button;
	private JButton decrypt2Button;
	CellularAutomaton1 cellularAutomaton;
	private static JComboBox roundsSelection;
	private final Integer[] ROUNDS = { 1, 2, 3, 4};
	
	public ButtonsPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		inicializeComponents();
		addButtons();
		addListeners();
		setDefaultVisibility();
	}

	private void inicializeComponents() {
		openButton = new JButton("Abrir...");
		encrypt1Button = new JButton("Encriptar Automata 1");
		encrypt2Button = new JButton("Encriptar Automata 2");
		decrypt1Button = new JButton("Desencriptar Encriptacion 1");
		decrypt2Button = new JButton("Desencriptar Encriptacion 2");
		roundsSelection = new JComboBox(ROUNDS);
		roundsSelection.setSelectedIndex(0);
	}

	private void addButtons() {
		this.add(openButton);
		this.add(encrypt1Button);
		this.add(encrypt2Button);
		this.add(decrypt1Button);
		this.add(roundsSelection);
	}
	
	private void setDefaultVisibility() {
		encrypt1Button.setEnabled(false);
		encrypt2Button.setEnabled(false);
		decrypt1Button.setEnabled(false);
		decrypt2Button.setEnabled(false);
		this.repaint();
	}
	
	private void addListeners() {
		addOpenButtonListener();
		addEncryptButtonListener();
		addDecryptButtonListener();
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
					encrypt2Button.setEnabled(true);
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
						ROUNDS[roundsSelection.getSelectedIndex()]);
				cellularAutomaton.encrypt();
				encrypt(1);
			}
		});
		encrypt2Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				encrypt(2);
			}
		});
	}
	
	private void encrypt(int opt) {
		encrypt1Button.setEnabled(false);
		encrypt2Button.setEnabled(false);
		if (opt == 1) {
			decrypt1Button.setEnabled(true);
		} else {
			decrypt2Button.setEnabled(true);
		}
	}
	
	private void addDecryptButtonListener() {
		decrypt1Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cellularAutomaton.decrypt();
				decryptImage();
			}
		});
		decrypt2Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				decryptImage();
			}
		});
	}
	
	private void decryptImage() {
		encrypt1Button.setEnabled(true);
		encrypt2Button.setEnabled(true);
		decrypt2Button.setEnabled(false);
		decrypt1Button.setEnabled(false);
	}
	
}
