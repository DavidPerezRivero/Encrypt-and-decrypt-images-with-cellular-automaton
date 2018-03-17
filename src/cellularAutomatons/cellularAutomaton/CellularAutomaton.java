package cellularAutomatons.cellularAutomaton;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import cellularAutomatons.AbstractCellularAutomaton;
import cellularAutomatons.State;
import window.panels.ButtonsPanel;
import window.panels.ImagePanel;
import window.panels.InfoPanel;

public class CellularAutomaton extends AbstractCellularAutomaton {
	private BufferedImage image;
	private int m;
	private int n;
	private int[][] bitInit;
	private int[][] nextState;
	private int ROUNDS;
	private int RADIUS;
	public static int TTS;

	public CellularAutomaton(BufferedImage image, int rounds, int radius, int tts) {
		m = image.getWidth();
		n = image.getHeight();
		bitInit = new int[m][n];
		nextState = new int[m][n];
		ROUNDS = rounds;
		RADIUS = radius;
		TTS = tts;
		this.image = copyImage(image);
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
					InfoPanel.updateRound(k + 1);
					ArrayList<Integer> aliveCell = new ArrayList<Integer>();
					ArrayList<Integer> deadCell = new ArrayList<Integer>();
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
					bitInit = copyState(bitInit, nextState);
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
				}
				ButtonsPanel.finishEncrypt();
			}
		});
		t.start();
	}

	private void setImage(BufferedImage image) {
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				ImagePanel.image.setRGB(i, j, image.getRGB(i, j));
				try {
					Thread.sleep(TTS);
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
					if (r >= 0 && r < m && c >= 0 && c < n && bitInit[r][c] == 1) {
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
				getStates();
				getImage();
				ButtonsPanel.finishDecrypt();
			}

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
					states.add(new State(bitInit, 0, new ArrayList<Integer>()));
				}
			}

			private void getImage() {
				for (int k = 0; k < ROUNDS; k++) {
					InfoPanel.updateRound(k+1);
					State state = states.get(ROUNDS - 1 - k);
					for (int i = 0; i < m; i++) {
						for (int j = 0; j < n; j++) {
							state.getColors().add(image.getRGB(i, j));
							if (state.getState()[i][j] == 1) {
								state.increaseCount();
							}
						}
					}
					for (int i = 0; i < m; i++) {
						for (int j = 0; j < n; j++) {
							if (state.getState()[i][j] == 1) {
								image.setRGB(i, j, state.getColors().remove(0));
								state.decreaseCount();
							} else {
								image.setRGB(i, j, state.getColors().remove(state.getCount()));
							}
						}
					}
					setImage(copyImage(image));
				}
			}
		});
		t.start();
	}
}
