package cellularAutomatons.cellularAutomaton;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import cellularAutomatons.AbstractCellularAutomaton;
import cellularAutomatons.State;
import window.panels.ImagePanel;
import window.panels.InfoPanel;

/**
 * Clase que representa el automata celular
 * 
 */
public class CellularAutomaton extends AbstractCellularAutomaton {
	private BufferedImage image;		// imagen actual
	private int m;						// numero de filas/alto de la imagen
	private int n;						// numero de columnas/ancho de la imagen
	private int[][] bitInit;			// estado actual de cada celula
	private int[][] nextState;			// estado siguiente de cada celula
	private int rounds;					// numero de rondas a ejecutar el algoritmo
	private int radius;					// radio de la vecindad
	public static int timeToSleep;		// tiempo a dormir al generar cada pixel
	private long seed;					// semilla para generar la matriz aleatoria
	
	/**
	 * Constructor del automata celular
	 * 
	 * @param image			Imagen seleccionada
	 * @param rounds		Numero de rondas del algoritmo
	 * @param radius		Radio de vecindad del algoritmo
	 * @param tts			Tiempo que duerme por cada pixel
	 * @param seed			Semilla para generar la matriz aleatoria
	 */
	public CellularAutomaton(BufferedImage image, int rounds, int radius, int tts, long seed) {
		m = image.getWidth();
		n = image.getHeight();
		bitInit = new int[m][n];
		nextState = new int[m][n];
		this.rounds = rounds;
		this.radius = radius;
		timeToSleep = tts;
		this.seed = seed;
		this.image = copyImage(image);
	}
	
	/**
	 * Encripta la imagen
	 * 		1- Genera la matriz aleatoria a partir de la semilla
	 *		2- Genera el estado siguiente mediante
	 *		3- Almacena el color de cada pixel
	 *		4- Establece primero los colores de las celulas vivas y lueg
	 *				los de las celulas muertas
	 *		5- Actualiza la imagen a mostrar con la nueva imagen encriptada
	 */
	public void encrypt() {
		//genera la matriz aleatoria
		generateRandomMatrix();
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int k = 0; k < rounds; k++) {
					// Actualiza el panel con la ronda actual del algoritmo
					InfoPanel.updateRound(k + 1);
					// Estructuras que almacenan los colores de las celulas
					//		vivas y muertas
					ArrayList<Integer> aliveCell = new ArrayList<Integer>();
					ArrayList<Integer> deadCell = new ArrayList<Integer>();
					for (int i = 0; i < m; i++) {
						for (int j = 0; j < n; j++) {
							// Obtiene el numero de celulas vivas en la vecindad
							//		de la celula actual
							int alive = getNumOfNeighborsAlive(i, j);
							// Obtiene el estado siguiente
							getNextState(alive, i, j, nextState);
							if (nextState[i][j] == 1) {
								// Si la celula en el estado siguiente esta viva
								//		se copia en la estructura de celulas vivas
								aliveCell.add(image.getRGB(i, j));
							} else {
								// Si esta muerta en el estado siguiente,
								// 		se copia en la estructura de celulas muertas
								deadCell.add(image.getRGB(i, j));
							}
						}
					}
					// Va estableciendo en los n primeros pixeles los colores de las
					//		n celulas vivas y a continuacion los colores de las restantes
					//		celulas muertas
					for (int i = 0; i < m; i++) {
						for (int j = 0; j < n; j++) {
							if (!aliveCell.isEmpty()) {
								image.setRGB(i, j, aliveCell.remove(0));
							} else {
								image.setRGB(i, j, deadCell.remove(0));
							}
						}
					}
					// Establece como estado actual el estado siguiente obtenido
					bitInit = copyState(nextState);
					// Actualiza la imagen a mostrar con la nueva imagen encriptada
					setImage(copyImage(image));
				}
			}
		});
		t.start();
	}
	
	/**
	 * Realiza el proceso de descencriptar la imagen
	 * 		1- Genera todos los estados a partir de la semilla y los almacena
	 * 		2- 
	 */
	@Override
	public void decrypt() {
		// Estructura para almacenar los estados desde el principio hasta el ultimo
		//		que es el que genera la imagen a descencriptar
		ArrayList<State> states = new ArrayList<>();
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				getStates();
				getImage();
			}

			/**
			 * Obtiene todos los estados, incluyendo el inicial que es el
			 * obtenido mediante la generacion de la matriz aleatoria
			 */
			private void getStates() {
				// Genera la matriz aleatoria
				generateRandomMatrix();
				// Estado siguiente
				nextState = new int[m][n];
				// Bucles para obtener los rounds estados siguientes
				for (int k = 0; k < rounds; k++) {
					for (int i = 0; i < m; i++) {
						for (int j = 0; j < n; j++) {
							// Obtiene vecinos vivos de la celula actual
							int alive = getNumOfNeighborsAlive(i, j);
							// Obtiene el estado siguiente de la celula actual
							getNextState(alive, i, j, nextState);
						}
					}
					// Realiza una copia del estado siguiente y la establece
					// como estado actual
					bitInit = copyState(nextState);
					// Almacena el estado actual
					states.add(new State(bitInit, 0, new ArrayList<Integer>()));
				}
			}

			/**
			 * Genera las distintas imagenes descencriptandolas.
			 * Para ello, se va utilizando desde el ultimo estado generado
			 * hacia el primero.
			 */
			private void getImage() {
				for (int k = 0; k < rounds; k++) {
					// Actualiza la informacion con el numero de rondas actuales
					InfoPanel.updateRound(k+1);
					// Obtiene el ultimo estado no utilizado
					State state = states.get(rounds - 1 - k);
					for (int i = 0; i < m; i++) {
						for (int j = 0; j < n; j++) {
							// Obtiene los colores de la imagen actual
							state.getColors().add(image.getRGB(i, j));
							if (state.getState()[i][j] == 1) {
								// Si la celula esta viva, incrementa un contador
								state.increaseCount();
							}
						}
					}
					for (int i = 0; i < m; i++) {
						for (int j = 0; j < n; j++) {
							// Si la celula esta viva, saca el primer color,
							// decrementando el contador
							if (state.getState()[i][j] == 1) {
								image.setRGB(i, j, state.getColors().remove(0));
								state.decreaseCount();
							} else {
								// Si la celula esta muerta, saca el color apuntado
								// por el contador
								image.setRGB(i, j, state.getColors().remove(state.getCount()));
							}
						}
					}
					// Establece la imagen a mostrar con la imagen generada
					setImage(copyImage(image));
				}
			}
		});
		t.start();
	}

	/**
	 * Realiza una copia de cada pixel de la imagen pasada como argumento
	 * 
	 * @param img		Imagen a copiar
	 * @return			Copia de la imagen como parametro
	 */
	private BufferedImage copyImage(BufferedImage img) {
		BufferedImage aux = new BufferedImage(m, n, img.getType());
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				aux.setRGB(i, j, img.getRGB(i, j));
			}
		}
		return aux;
	}

	/**
	 * Genera una matriz aleatoria a partir de la semilla
	 */
	private void generateRandomMatrix() {
		bitInit = new int[m][n];
		Random random = new Random();
		random.setSeed(seed);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				bitInit[i][j] = (random.nextBoolean()) ? 1 : 0;
			}
		}
	}

	/**
	 * Realiza una copia del estado de cada una de las celulas
	 * 
	 * @param next			Matriz de estados a copiar
	 * @return				Copia de la matriz de estados
	 */
	private int[][] copyState(int[][] next) {
		int[][] aux = new int[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				aux[i][j] = next[i][j];
			}
		}
		return aux;
	}

	/**
	 * Establece en el panel de la imagen a mostrar la imagen
	 * pasada como parametro
	 * 
	 * @param image		Imagen a establecer en el panel
	 */
	private void setImage(BufferedImage image) {
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				ImagePanel.image.setRGB(i, j, image.getRGB(i, j));
				try {
					Thread.sleep(timeToSleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * Genera el siguiente estado a partir de la celula actual
	 * y los vecinos vivos en su vecindad.
	 * 
	 * La generacion del estado siguiente se basa en el algoritmo
	 * del juego de la vida
	 * 
	 * @param alive		Numero de celulas vivas
	 * @param i			Fila de la celula actual
	 * @param j			Columna de la celula actual
	 * @param next		Matriz de estados
	 */
	public void getNextState(int alive, int i, int j, int[][] next) {
		if ((bitInit[i][j]) == 0 && (alive == 3)) {
			next[i][j] = 1;
		} else if (bitInit[i][j] == 1 && (alive == 2 || alive == 3)) {
			next[i][j] = 1;
		} else {
			next[i][j] = 0;
		}
	}
	
	/**
	 * Obtiene el numero de vecinos vivos en la vecindad de la celula i,j.
	 * 
	 * Para obtener este numero, se utiliza la vecindad de moore, que observa
	 * un cuadrado de radio radius alrededor de la celula
	 */
	public int getNumOfNeighborsAlive(int i, int j) {
		int alive = 0;
		for (int r = radius * -1 + i; r <= radius + i; r++) {
			for (int c = radius * -1 + j; c <= radius + j; c++) {
				if (r != i && c != j) {
					if (r >= 0 && r < m && c >= 0 && c < n && bitInit[r][c] == 1) {
						alive++;
					}
				}
			}
		}
		return alive;
	}
}
