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
	
	public Window() {
		super();
		configureWindow();
		inicializeComponents();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	private void configureWindow() {
		setTitle("Cellular Automaton");                                              
        setResizable(true);       
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
        setSize(1300, 1000);  
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