package cellularAutomatons;

import java.util.ArrayList;

public class State {
	private int[][] state;
	private int count;
	private ArrayList<Integer> colors;

	public State(int[][] state, int count, ArrayList<Integer> colorCells) {
		this.state = state;
		this.count = count;
		this.colors = colorCells;
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
}
