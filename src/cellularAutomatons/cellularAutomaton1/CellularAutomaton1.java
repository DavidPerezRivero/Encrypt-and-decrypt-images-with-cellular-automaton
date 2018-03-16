package cellularAutomatons.cellularAutomaton1;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import cellularAutomatons.CellularAutomaton;
import cellularAutomatons.State;
import window.panels.ImagePanel;

public class CellularAutomaton1 extends CellularAutomaton {
	BufferedImage image;
	int m;
	int n;
	int[][] bitInit;
	int[][] nextState;
	int ROUNDS;
	final int RADIUS = 1;
	ArrayList<Integer> deadCell;
	ArrayList<Integer> aliveCell;
	
	
	ArrayList<int[][]> a;
	ArrayList<BufferedImage> b;
	ArrayList<Integer> c;
	ArrayList<ArrayList<Integer>> d;

	public CellularAutomaton1(BufferedImage image, int rounds) {
		m = image.getWidth();
		n = image.getHeight();
		bitInit = new int[m][n];
		nextState = new int[m][n];
		ROUNDS = rounds;
		this.image = copyImage(image);
		
		
		a = new ArrayList<>();
		b = new ArrayList<>();
		c = new ArrayList<>();
		d = new ArrayList<>();
	}

	private BufferedImage copyImage(BufferedImage img) {
		BufferedImage aux = new BufferedImage(m, n, img.getType());
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				aux.setRGB(i, j, img.getRGB(i, j));
			}
		}
		return aux;
	}

	private void generateRandomMatrix() {
		bitInit = new int[m][n];
		Random random = new Random();
		random.setSeed(29121994);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				bitInit[i][j] = (random.nextBoolean()) ? 1 : 0;
			}
		}
	}
	
	private int[][] copyState(int[][] init, int[][] next) {
		int[][] aux = new int[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				aux[i][j] = next[i][j];
			}
		} 
		return aux;
	}

	public void encrypt() {
		generateRandomMatrix();
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int k = 0; k < ROUNDS; k++) {
					aliveCell = new ArrayList<Integer>();
					deadCell = new ArrayList<Integer>();
					for (int i = 0; i < m; i++) {
						for (int j = 0; j < n; j++) {
							int alive = getNumOfNeighborsAlive(i, j);
							getNextState(alive, i, j, nextState);
							if (nextState[i][j] == 1) {
								aliveCell.add(image.getRGB(i, j));
							} else {
								deadCell.add(image.getRGB(i, j));
							}
						}
					}
					c.add(aliveCell.size());
					bitInit = copyState(bitInit, nextState);
					a.add(bitInit);
					for (int i = 0; i < m; i++) {
						for (int j = 0; j < n; j++) {
							if (!aliveCell.isEmpty()) {
								image.setRGB(i, j, aliveCell.remove(0));
							} else {
								image.setRGB(i, j, deadCell.remove(0));
							}
						}
					}
					setImage(copyImage(image));
					b.add(copyImage(image));
				}
			}
		});
		t.start();
	}

	private void setImage(BufferedImage image) {
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				ImagePanel.image.setRGB(i, j, image.getRGB(i, j));
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	// Game of Life's
	public void getNextState(int alive, int i, int j, int[][] next) {
		if ((bitInit[i][j]) == 0 && (alive == 3)) {
			next[i][j] = 1;
		} else if (bitInit[i][j] == 1 && (alive == 2 || alive == 3)) {
			next[i][j] = 1;
		} else {
			next[i][j] = 0;
		}
	}

	// Moore Neighborhood
	public int getNumOfNeighborsAlive(int i, int j) {
		int alive = 0;
		for (int r = RADIUS * -1 + i; r <= RADIUS + i; r++) {
			for (int c = RADIUS * -1 + j; c <= RADIUS + j; c++) {
				if (r != i && c != j) {
					if (r >= 0 && r < m && c>=0 && c < n && bitInit[r][c] == 1) {
						alive++;
					}
				}
			}
		}
		return alive;
	}

	@Override
	public void decrypt() {
		ArrayList<State> states = new ArrayList<>();
		nextState = new int[m][n];
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("--------------NEW DECRYPT----------------");
				getStates();
				getImage();
			}

			// los valores image y colors solo son correctos para ROUNDS - 1
			private void getStates() {
				generateRandomMatrix();
				nextState = new int[m][n];
				for (int k = 0; k < ROUNDS; k++) {
					for (int i = 0; i < m; i++) {
						for (int j = 0; j < n; j++) {
							int alive = getNumOfNeighborsAlive(i, j);
							getNextState(alive, i, j, nextState);
						}
					}
					bitInit = copyState(bitInit, nextState);
					states.add(new State(bitInit, 0, new ArrayList<Integer>(), null));
				}
			}

			private void getImage() {
				for (int k = 0; k < ROUNDS; k++) {
					State state = states.get(ROUNDS - 1 - k);
					for (int i = 0; i < m; i++) {
						for (int j = 0; j < n; j++) {
							state.getColors().add(image.getRGB(i, j));
							if (state.getState()[i][j] == 1) {
								state.increaseCount();
							}							
						}
					}
					int alive = 0;
					int dead = state.getCount();
					for (int i = 0; i < m; i++) {
						for (int j = 0; j < n; j++) {
							if (state.getState()[i][j] == 1) {
								image.setRGB(i, j, state.getColors().get(alive));
								alive++;
							} else {
								image.setRGB(i, j, state.getColors().get(dead));
								dead++;
							}
						}
					}
					setImage(copyImage(image));
					state.setImage(copyImage(image));
				}
				
////////////////////////////////////////////////////////////////////////////////////////////DEBUG
				boolean estado;
				for (int k = 0; k < ROUNDS; k++) {
					estado = true;
					for (int i = 0; i < m; i++) {
						for (int j = 0; j < n; j++) {
							if (states.get(k).getState()[i][j] != a.get(k)[i][j]) {
								estado = false;
							}
						}
					}
					if (estado) {
						System.out.println("Estado " + k + " correcto.");
					} else {
						System.out.println("ERROR EN EL ESTADO " + k);
					}
				}
				for (int k = 0; k < ROUNDS; k++) {
					if (states.get(k).getCount() != c.get(k)) {
						System.out.println("Error en el contador " + k);
					} else {
						System.out.println("Contador "+k+" correcto");
					}
				}
				for (int k = 0; k < ROUNDS; k++) {
					boolean im = true;
					for (int i = 0; i < m; i++) {
						for (int j = 0; j < n; j++) {
							if (states.get(k).getImage().getRGB(i, j) != b.get(k).getRGB(i, j)) {
								im = false;
							}
						}
					}
					if (im) {
						System.out.println("Image " + k + " correcta.");
					} else {
						System.out.println("Error en la imagen " + k + ".");
					}
				}
////////////////////////////////////////////////////////////////////////////////////////////
			}
		});
		t.start();
	}
}
