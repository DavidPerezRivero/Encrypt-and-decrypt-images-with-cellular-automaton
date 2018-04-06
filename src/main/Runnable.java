package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import cellularAutomatons.cellularAutomaton.CellularAutomaton;
import encryptText.EncryptAndDecryptText;
import window.Window;

public class Runnable {
	private Window window;
	private CellularAutomaton automaton;
	private String PATH = "/Users/" + System.getProperty("user.name");
	private String PATH_TO_SAVE_ENCRYPTED_IMG = PATH + "/encrypt.png";
	private String PATH_TO_SAVE_DECRYPTED_IMG = PATH + "/decrypt.png";
	private String PATH_TO_SAVE_TXT = PATH + "/encrypt.txt";
	private final String ONLY_IMAGES = "Only Images";
	private final String ONLY_TEXT = "Only Text";
	private final String PNG = "png";
	private final String JPG = "jpg";
	private final String TXT = "txt";

	public Runnable() {
		createWindow();
		if (System.getProperty("os.name").contains("Windows")) {
			PATH = "C:/Documents and Settings";
			PATH_TO_SAVE_ENCRYPTED_IMG = PATH + "/encrypt.png";
			PATH_TO_SAVE_DECRYPTED_IMG = PATH + "/decrypt.png";
			PATH_TO_SAVE_TXT = PATH + "/encrypt.txt";
		}
	}

	private void createCellularAutomaton() {
		automaton = new CellularAutomaton(window.getImage(), window.getRounds(), 
				window.getRadius(), window.getTTS(), window.getSeed());
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
		addOpenToEncryptButtonListener(window);
		addOpenToDecryptButtonListener(window);
		addDecryptButtonListener(window);
		addEncryptButtonListener(window);
		addSaveEncryptedButtonListener(window);
		addSaveDecryptedButtonListener(window);
		addSliderListener(window.getSlider());
		addRadiusComboBoxListener(window.getRadiusComboBox());
		addRoundsComboBoxListener(window.getRoundsComboBox());
	}
	
	private void openImage() {
		JFileChooser fileChooser = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter(ONLY_IMAGES, PNG, JPG);
		fileChooser.setFileFilter(filter);
		fileChooser.setCurrentDirectory(new File(PATH));
		int result = fileChooser.showOpenDialog(window);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try {
				BufferedImage image = ImageIO.read(selectedFile);
				if (image == null) {
					openImage();
				} else {
					window.openImage(image);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void saveImage(String path, Window window) {
		File outputImg = new File(PATH_TO_SAVE_ENCRYPTED_IMG);
		try {
			ImageIO.write(window.getImage(), PNG, outputImg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void addOpenToEncryptButtonListener(Window window) {
		window.getOpenToEncryptButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openImage();
				window.openToEncrypt();
			}
		});
	}

	private void addOpenToDecryptButtonListener(Window window) {
		window.getOpenToDecryptButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openImage();
				openTxt(window);
				createCellularAutomaton();
				window.openToDecrypt();
			}

			private void openTxt(Window window) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(PATH));
				FileFilter filter = new FileNameExtensionFilter(ONLY_TEXT, TXT);
				fileChooser.setFileFilter(filter);
				int result = fileChooser.showOpenDialog(window);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					// Instanciar descifrador
					EncryptAndDecryptText edt = new EncryptAndDecryptText();
					edt.readFile(window, selectedFile);
				}
			}
		});
	}

	private void addSaveEncryptedButtonListener(Window window) {
		window.getSaveEncryptedButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					saveImage(PATH_TO_SAVE_ENCRYPTED_IMG, window);
					saveText(PATH_TO_SAVE_TXT, window);
					window.saveEncryptedImage();
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
			
			protected void saveText(String path, Window window) {
				EncryptAndDecryptText edt = new EncryptAndDecryptText();
				edt.writeFile(window, path);
			}
		});
	}

	private void addSaveDecryptedButtonListener(Window window) {
		window.getSaveDecryptedButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					File outputImg = new File(PATH_TO_SAVE_DECRYPTED_IMG);
					ImageIO.write(window.getImage(), PNG, outputImg);
					window.saveDecryptedImage();
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
	}

	private void addEncryptButtonListener(Window window) {
		window.getEncryptButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.encrypt();
				createCellularAutomaton();
				automaton.encrypt();
			}
		});
	}

	private void addDecryptButtonListener(Window window) {
		window.getDecryptButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.decrypt();
				automaton.decrypt();
			}
		});
	}

	private void addSliderListener(JSlider slider) {
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				window.setSliderValue(slider.getValue());
				CellularAutomaton.timeToSleep = slider.getValue();
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
