package main;

import command.StoreCommand;
import controller.StoreController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Store;
import view.StoreView;

public class Program extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Store model = new Store();
		StoreCommand storeCom = new StoreCommand(model);
		StoreView view = new StoreView(primaryStage);
		StoreController controller = new StoreController(storeCom, view);
	}
}
