package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cellularAutomatons.cellularAutomaton.CellularAutomaton;
import window.Window;

public class Runnable {
	private Window window;
	private CellularAutomaton automaton;

	public Runnable() {
		createWindow();
	}

	private void createCellularAutomaton() {
		System.out.println(window.getRounds() + "     " + window.getTTS() + "    "+ window.getRadius());
		automaton = new CellularAutomaton(window.getImage(), window.getRounds(), window.getRadius(), window.getTTS());
	}

	private void createWindow() {
		window = new Window();
		window.setSliderValue(0);
		window.setRoundsValue(0);
		window.setRadiusValue(0);
		addListeners();
		while (window.getImage() == null) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void addListeners() {
		addOpenButtonListener(window.getOpenButton(), window.getEncryptButton());
		addDecryptButtonListener(window.getDecryptButton(), window.getEncryptButton(), window.getOpenButton());
		addSliderListener(window.getSlider());
		addRadiusComboBoxListener(window.getRadiusComboBox());
		addRoundsComboBoxListener(window.getRoundsComboBox());
		addEncryptButtonListener(window.getEncryptButton(), window.getOpenButton(), window.getDecryptButton());
	}

	private void addOpenButtonListener(JButton openButton, JButton encryptButton) {
		openButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openImage(encryptButton);
			}
		});
	}

	private void openImage(JButton encryptButton) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("/Users/"));
		int result = fileChooser.showOpenDialog(window);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try {
				BufferedImage image = ImageIO.read(selectedFile);
				if (image == null) {
					openImage(encryptButton);
				} else {
					window.openImage(image);
					encryptButton.setEnabled(true);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void addEncryptButtonListener(JButton encryptButton, JButton openButton, JButton decryptButton) {
		encryptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createCellularAutomaton();
				automaton.encrypt();
				encryptButton.setEnabled(false);
				openButton.setEnabled(false);
				decryptButton.setEnabled(true);
			}
		});
	}

	private void addDecryptButtonListener(JButton decryptButton, JButton encryptButton, JButton openButton) {
		decryptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				automaton.decrypt();
				decryptButton.setEnabled(false);
				encryptButton.setEnabled(true);
				openButton.setEnabled(true);
			}
		});
	}

	private void addSliderListener(JSlider slider) {
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				window.setSliderValue(slider.getValue());
			}
		});
	}

	private void addRoundsComboBoxListener(JComboBox<Object> roundsComboBox) {
		roundsComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.setRoundsValue(roundsComboBox.getSelectedIndex());
			}
		});

	}

	private void addRadiusComboBoxListener(JComboBox<Object> radiusComboBox) {
		radiusComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.setRadiusValue(radiusComboBox.getSelectedIndex());
			}
		});
	}
}
