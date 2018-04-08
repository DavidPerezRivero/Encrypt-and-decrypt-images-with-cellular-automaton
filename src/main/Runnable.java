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
	// ventana de la aplicación
	private Window window;
	// automata celular para encriptar y descencriptar la imagen
	private CellularAutomaton automaton;
	// Textos a utilizar
	private String PATH;
	private String PATH_TO_SAVE_ENCRYPTED_IMG;
	private String PATH_TO_SAVE_DECRYPTED_IMG;
	private String PATH_TO_SAVE_TXT;
	private final String ONLY_IMAGES = "Only Images";
	private final String ONLY_TEXT = "Only Text";
	private final String PNG = "png";
	private final String JPG = "jpg";
	private final String TXT = "txt";
	private final String ENCRYPT_IMAGE = "/encrypt.png";
	private final String DECRYPT_IMAGE = "/decrypt.png";
	private final String ENCRYPT_TXT = "/encrypt.txt";
	private final String NEW_DIR = "/EncryptDecryptImages";

	/**
	 * Constructor que establece las rutas a utilizar por la aplicación
	 * y crea la ventana.
	 */
	public Runnable() {
		setCurrentPath();
		createWindow();
	}
	
	/**
	 * Establece las rutas a utilizar dependiendo del sistema operativo
	 */
	public void setCurrentPath() {
		if (System.getProperty("os.name").contains("Windows")) {
			PATH = System.getProperty("user.dir") + NEW_DIR;
		} else {
			PATH = "/Users/" + System.getProperty("user.name") + NEW_DIR;
		}
		File dir = new File(PATH);
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}
		PATH_TO_SAVE_ENCRYPTED_IMG = PATH + ENCRYPT_IMAGE;
		PATH_TO_SAVE_DECRYPTED_IMG = PATH + DECRYPT_IMAGE;
		PATH_TO_SAVE_TXT = PATH + ENCRYPT_TXT;
	}

	/**
	 * Crea un automata celular
	 */
	private void createCellularAutomaton() {
		automaton = new CellularAutomaton(window.getImage(), window.getRounds(), window.getRadius(), window.getTTS(),
				window.getSeed());
	}

	/**
	 * Crea la ventana 
	 */
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

	/**
	 * Establece escuchas a los botones de la ventana y a los parametros
	 */
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

	/**
	 * Abre una imagen seleccionada por el usuario y la muestra
	 */
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

	/**
	 * Almacena la imagen que se muestra en la pantalla
	 * @param path
	 * @param window
	 */
	protected void saveImage(String path, Window window) {
		File outputImg = new File(PATH_TO_SAVE_ENCRYPTED_IMG);
		try {
			ImageIO.write(window.getImage(), PNG, outputImg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Escucha del boton para abrir una imagen para encriptarla
	 * @param window
	 */
	private void addOpenToEncryptButtonListener(Window window) {
		window.getOpenToEncryptButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openImage();
				window.openToEncrypt();
			}
		});
	}

	/**
	 * Escucha del boton para abrir una imagen para descencriptarla.
	 * @param window
	 */
	private void addOpenToDecryptButtonListener(Window window) {
		window.getOpenToDecryptButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// abre la imagen
				openImage();
				// abre el fichero de texto con los parametros
				openTxt();
				// crea un automata celular
				createCellularAutomaton();
				window.openToDecrypt();
			}

			/**
			 * Abre el fichero de texto que contiene los parametros
			 * necesarios para descencriptar la imagen abierta 
			 * previamente
			 */
			private void openTxt() {
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
	
	/**
	 * Escucha del boton que guarda la imagen encriptada y el texto con los
	 * parametros
	 * @param window
	 */
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

	/**
	 * Escucha del boton que guarda la imagen descencriptada
	 * @param window
	 */
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

	/**
	 * Escucha del boton para encriptar la imagen
	 * @param window
	 */
	private void addEncryptButtonListener(Window window) {
		window.getEncryptButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.encrypt();
				// Crea un automata celular y ejecuta la encriptacion
				createCellularAutomaton();
				automaton.encrypt();
			}
		});
	}

	/**
	 * Escucha del boton para encriptar la imagen
	 * @param window
	 */
	private void addDecryptButtonListener(Window window) {
		window.getDecryptButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.decrypt();
				automaton.decrypt();
			}
		});
	}

	/**
	 * Escucha del deslizador para ajustar la velocidad del algoritmo
	 * @param slider
	 */
	private void addSliderListener(JSlider slider) {
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				window.setSliderValue(slider.getValue());
				CellularAutomaton.timeToSleep = slider.getValue();
			}
		});
	}

	/**
	 * Escucha del combobox para ajustar el numero de rondas a ejecutar el algoritmo
	 * @param roundsComboBox
	 */
	private void addRoundsComboBoxListener(JComboBox<Object> roundsComboBox) {
		roundsComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.setRoundsValue(roundsComboBox.getSelectedIndex());
			}
		});

	}

	/**
	 * Escucha del combobox para ajustar el radio de la vencidad de cada celula
	 * @param radiusComboBox
	 */
	private void addRadiusComboBoxListener(JComboBox<Object> radiusComboBox) {
		radiusComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.setRadiusValue(radiusComboBox.getSelectedIndex());
			}
		});
	}
}
