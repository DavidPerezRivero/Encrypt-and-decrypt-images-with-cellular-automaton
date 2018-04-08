package cellularAutomatons;

/**
 * Clase que representa una plantilla de automatas celulares
 *
 */
public abstract class AbstractCellularAutomaton {
	
	/**
	 * Proceso de encriptacion de imagenes
	 */
	public abstract void encrypt();
	
	/**
	 * Proceso de descencriptacion de imagenes
	 */
	public abstract void decrypt();
	
	/**
	 * Obtiene el estado siguiente
	 * 
	 * @param alive		numero de celulas vivas en la vecindad
	 * @param i			fila de la celula actual
	 * @param j			columna de la celula actual
	 * @param next		estado siguiente de cada celula
	 */
	public abstract void getNextState(int alive, int i, int j, int [][]next);
	
	/**
	 * Obtiene el numero de celulas vivas
	 * 
	 * @param i			fila de la celula actual
	 * @param j			columna de la celula actual
	 * @return			Numero de celulas vivas en la vecindad de la celula actual
	 */
	public abstract int getNumOfNeighborsAlive(int i, int j);
}
