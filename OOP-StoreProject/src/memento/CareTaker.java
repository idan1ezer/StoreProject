package memento;

import java.util.Stack;

public class CareTaker {

	private Stack<StoreMemento> stack;

	public CareTaker() {
		this.stack = new Stack<StoreMemento>();
	}

	public void save(StoreMemento pm) {
		stack.push(pm);
	}

	public StoreMemento load() {
		return stack.pop();
	}

	@Override
	public String toString() {
		return "Saves:\n" + stack;
	}

}
