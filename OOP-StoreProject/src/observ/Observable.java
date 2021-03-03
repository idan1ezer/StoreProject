package observ;

public interface Observable {

	void addObserver(Observer obs);
	void deleteObserver(Observer obs);
	void notifyObsevers();
	void notifyObsever(Observer obs, int index);

	
}
