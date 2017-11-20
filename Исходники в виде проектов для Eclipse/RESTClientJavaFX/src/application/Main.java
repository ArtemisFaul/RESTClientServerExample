package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class Main extends Application {
	public static Stage primaryStage;
	public static String screen1ID = "Main";
	public static String screen1File = "/view/Main.fxml";
	public static String screen2ID = "BankAccountController";
	public static String screen2File = "/view/BankAccountController.fxml";
	public static String screen3ID = "BillingController";
	public static String screen3File = "/view/BillingController.fxml";
	
	@Override
	public void start(Stage primaryStage) {
		Main.primaryStage = primaryStage;
		primaryStage.setTitle("RESTClientJavaFX");
		primaryStage.setMinWidth(667);
		primaryStage.setMinHeight(462);
		ScreensController mainContainer = new ScreensController();
		mainContainer.setScreen(Main.screen1ID, Main.screen1File);
		Scene scene = new Scene(mainContainer);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
