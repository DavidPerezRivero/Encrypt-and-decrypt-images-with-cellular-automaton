package cellularAutomatons;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class State {
	private int[][] state;
	private int count;
	private ArrayList<Integer> colors;
	private BufferedImage image;

	public State(int[][] state, int count, ArrayList<Integer> colorCells, BufferedImage image) {
		this.state = state;
		this.count = count;
		this.colors = colorCells;
		this.image = image;
	}
	
	public ArrayList<Integer> getColors() {
		return colors;
	}

	public void setColors(ArrayList<Integer> colors) {
		this.colors = colors;
	}

	public int[][] getState() {
		return state;
	}

	public void setState(int[][] state) {
		this.state = state;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void decreaseCount() {
		count--;
	}

	public void increaseCount() {
		count++;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = new BufferedImage(image.getHeight(), image.getWidth(), image.getType());
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				this.image.setRGB(i, j, image.getRGB(i, j));
			}
		}
	}
}
