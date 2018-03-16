package window;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import window.panels.ButtonsPanel;
import window.panels.ImagePanel;

public class Window extends JFrame {
	private static final long serialVersionUID = 1L;
	private ButtonsPanel buttonsPanel;
	public static ImagePanel imagePanel;
	private final String CELLULAR_TEXT = "Encrypt/Decrypt images with Cellular Automaton";
	private final int WINDOW_WIDTH = 1300;
	private final int WINDOW_HEIGHT = 1000;
	
	public Window() {
		super();
		configureWindow();
		inicializeComponents();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	private void configureWindow() {
		setTitle(CELLULAR_TEXT);                                              
        setResizable(true);       
        setVisible(true);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
        setLayout(new BorderLayout());  
        setLocationRelativeTo(null);  
	}
	

	private void inicializeComponents() {
		buttonsPanel = new ButtonsPanel();
		imagePanel = new ImagePanel();
		this.add(buttonsPanel, BorderLayout.NORTH);
		this.add(imagePanel, BorderLayout.CENTER);
	}

	public static void openImage(BufferedImage image) {
		imagePanel.setImagePanel(image);
	}
}