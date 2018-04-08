package window.panels;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import cellularAutomatons.cellularAutomaton.CellularAutomaton;

public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public CellularAutomaton cellularAutomaton;
	public static BufferedImage image;
	
	public ImagePanel() {
		super();
	}
	
	/**
	 * Establece la imagen 
	 * @param image
	 */
	public void setImagePanel(BufferedImage image) {
		this.setLayout(new GridLayout(image.getWidth(), image.getHeight()));
		ImagePanel.image = image;
	}
	
	/**
	 * Pinta la imagen
	 */
	@Override
    public void paint(Graphics g) {
        g.drawImage((Image) image, 0, 0, getWidth(), getHeight(), this);
        setOpaque(false);
        super.paint(g);
        super.repaint();
    }
	
	public BufferedImage getImage() {
		return image;
	}
}
