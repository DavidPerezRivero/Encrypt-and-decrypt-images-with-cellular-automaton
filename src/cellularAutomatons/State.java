package cellularAutomatons;

import java.util.ArrayList;

/**
 * Clase utilizada como estructura de datos para almacenar
 * la informacion de los estados generados en el proceso
 * de descencriptacion
 */
public class State {
	private int[][] state;				// Estado de cada celula
	private int count;					// Contador de celulas vivas
	private ArrayList<Integer> colors;	// Colores de la imagen

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
