package cellularAutomatons;

public abstract class CellularAutomaton {
	public abstract void encrypt();
	public abstract void decrypt();
	public abstract void getNextState(int alive, int i, int j, int [][]next);
	public abstract int getNumOfNeighborsAlive(int i, int j);
}
