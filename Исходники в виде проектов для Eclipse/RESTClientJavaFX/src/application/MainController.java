package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import org.eclipse.persistence.internal.oxm.mappings.Login;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.ControlledScreen;
import model.User;

public class MainController implements Initializable, ControlledScreen {
	@FXML
	private ChoiceBox<User> choiceBox;
	@FXML
	private VBox preferenceBox;
	@FXML
	private TextField serverField;
	@FXML
	private TextField portField;
	private JsonObject jsonObject;
	private Preferences usr = Preferences.userNodeForPackage(Preferences.class);
	ScreensController myController;
	public static String serverAdress = "localhost";
	public static String serverPort = "8080";
	private String resourceUrl;
	private String formatUrlMask = "http://%s:%s/RESTServer/user";
	private ArrayList<User> userAR = new ArrayList<User>();
	private ObservableList<User> data;
	
	
	@Override
	public void setScreenParent(ScreensController screenParent) {
		// TODO Auto-generated method stub
		myController = screenParent;
	}
	
	@FXML
	public void logIn(){
		if (choiceBox.getSelectionModel().getSelectedItem() != null)
		BankAccountController.currentUser = choiceBox.getSelectionModel().getSelectedItem();
		myController.setScreen(Main.screen2ID, Main.screen2File);
	}
	
	
	@FXML
	public void applyButton(){
		
		if (serverField.getText().isEmpty() != true){	
			usr.put("Server", serverField.getText());
		}
		
		if (portField.getText().isEmpty() != true){
			usr.put("Port", portField.getText());
		}
		preferenceBox.setVisible(false);
		initialize(null, null);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String currentServer = usr.get("Server", serverAdress);
		if (currentServer != serverAdress){
			serverAdress = currentServer;
			serverField.setText(currentServer);
		}
		
		String currentPort = usr.get("Port", serverPort);
		if (currentPort != serverPort){
			serverPort = currentPort;
			portField.setText(currentPort);
		}
		
		resourceUrl = String.format(formatUrlMask, serverAdress, serverPort);
		jsonObject = new ConnectToResource().getResorce(resourceUrl);
		if (jsonObject == null) {
			preferenceBox.setVisible(true);
		}else{
			JsonArray jsonArray = jsonObject.getJsonArray("Users");
			for (JsonValue value: jsonArray){
				JsonObject jsonObjectInArray = (JsonObject) value;
				userAR.add(new User(jsonObjectInArray.getInt("id"), jsonObjectInArray.getString("user")));
			}
			data = FXCollections.observableArrayList(userAR);
			choiceBox.setItems(data);
			choiceBox.setValue(userAR.get(0));
			
		}
				
	}

	

}
