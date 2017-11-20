package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.BankAccount;
import model.BillingList;
import model.ControlledScreen;
import model.User;

public class BillingController implements Initializable, ControlledScreen {
	private JsonObject jsonObject;
	ScreensController myController;
	public static User currentUser;
	public static BankAccount currentAccount; 
	private String resourceUrl;
	private String formatUrlMaskGetIO = "http://%s:%s/Sbertech_Server/billing/io%s";
	private String formatUrlMaskAddBill = "http://%s:%s/Sbertech_Server/billing";
	private String formatUrlMaskForFastSearch = "http://%s:%s/Sbertech_Server/bankAccountSearch/search%s";
	private String formatUrlMaskForSearch = "http://%s:%s/Sbertech_Server/bankAccountSearch%s" ;
	
	
	@FXML
	private TextField accountSummField;
	@FXML
	private TextField accountDescField;
	@FXML
	private Text errorText;
	@FXML
	private AnchorPane listViewAnchorOut;
	@FXML
	private AnchorPane listViewAnchorInput;
	@FXML
	private VBox newBillingBox;
	@FXML
	private Button addBillingButton;
	@FXML
	private ComboBox<BankAccount> searchBox;
	private ArrayList<BillingList> billingInputAR;
	private ArrayList<BillingList> billingOutAR;
	private ArrayList<BankAccount> bankAcountAR =  new ArrayList<BankAccount>();

	ListView<BillingList> listViewInput;
	ListView<BillingList> listViewOut;

	@Override
	public void setScreenParent(ScreensController screenParent) {
		// TODO Auto-generated method stub
		myController = screenParent;
	}

	@FXML
	public void addBilling() {
		errorText.setText("");
		if (searchBox.getEditor().getText().isEmpty() != true && accountSummField.getText().isEmpty() != true) {
			if ((currentAccount.getSumm() - Long.parseLong(accountSummField.getText())) > 0){
				
				resourceUrl = String.format(formatUrlMaskForSearch, MainController.serverAdress, MainController.serverPort, "/" + searchBox.getEditor().getText());
				JsonObject searchBankAccount = new ConnectToResource().getResorce(resourceUrl);
				JsonArray arrayinSearch = searchBankAccount.getJsonArray("BankAccounts");
				if (arrayinSearch.size() != 0) {
					addBillingButton.setDisable(false);
					newBillingBox.setVisible(false);
					JsonObject jsonObjectInSearchingArray = (JsonObject) arrayinSearch.get(0);
					Long bankAccountToId = jsonObjectInSearchingArray.getJsonNumber("id").longValue();
					
					JsonObject puttedJson = Json.createObjectBuilder().add("NewBilling", Json.createObjectBuilder()
											.add("userId", currentUser.getId())
											.add("bankAccountFromId", currentAccount.getId())
											.add("bankAccountToId", bankAccountToId)
											.add("summ", Long.parseLong(accountSummField.getText()))
											.add("description", accountDescField.getText()).build()).build();
					resourceUrl = String.format(formatUrlMaskAddBill, MainController.serverAdress, MainController.serverPort);
					jsonObject = new ConnectToResource().putResorce(resourceUrl, puttedJson);
				} else{
					errorText.setText("Счет получателя не найден.");
				}
				
			} else {
				errorText.setText("Баланс счета не может быть отрицательным.");
			}
			
		}else {
			errorText.setText("Поля 'Номер счета', 'Сумма' являются одинаковыми.");
		}

		initialize(null, null);
	}
	
	@FXML
	public void changeField() {
		if (searchBox.getEditor().getText().matches("\\d*") == true && searchBox.getEditor().getText().isEmpty() == false) {

			bankAcountAR.clear();
			resourceUrl = String.format(formatUrlMaskForFastSearch, MainController.serverAdress, MainController.serverPort,
					"/" + searchBox.getEditor().getText());
			System.out.println(resourceUrl);
			JsonObject searchBankAccount = new ConnectToResource().getResorce(resourceUrl);
			JsonArray arrayinSearch = searchBankAccount.getJsonArray("BankAccounts");
			if (arrayinSearch.size() > 0) {
				searchBox.show();
			} else {
				searchBox.hide();
			}

			for (JsonValue value : arrayinSearch) {
				JsonObject jsonObjectInArray = (JsonObject) value;
				bankAcountAR.add(new BankAccount(jsonObjectInArray.getJsonNumber("id").longValue(),
						jsonObjectInArray.getJsonNumber("number").longValue(),
						jsonObjectInArray.getString("description"),
						jsonObjectInArray.getJsonNumber("summ").longValue()));
			}
			System.out.println(searchBankAccount);

			searchBox.setItems(FXCollections.observableArrayList(bankAcountAR));
		}
	}

	@FXML
	public void showAddBilling() {
		accountSummField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.matches("\\d*")) {
					try {
						int value = Integer.parseInt(newValue);
					} catch (NumberFormatException e) {
						// TODO: handle exception
					}
				} else {
					accountSummField.setText(oldValue);
				}
			}
		});
				
		newBillingBox.setVisible(true);
		addBillingButton.setDisable(true);
	}

	public void loadInput() {
		ObservableList<BillingList> data = FXCollections.observableArrayList(billingInputAR);
		listViewInput = new ListView<BillingList>(data);

		listViewInput.setCellFactory(new Callback<ListView<BillingList>, ListCell<BillingList>>() {

			@Override
			public ListCell<BillingList> call(ListView<BillingList> arg0) {
				return new ListCell<BillingList>() {
					@Override
					protected void updateItem(BillingList item, boolean bln) {
						super.updateItem(item, bln);
						if (item != null) {
							VBox vBoxBillingItem = new VBox();
							vBoxBillingItem.setPrefWidth(USE_COMPUTED_SIZE);
							Label billingOperation = new Label("Операция: " + item.getOperation());
							Label billingBankAccountFrom = new Label("От счета: " + Long.toString(item.getBankAccountFrom()));
							Label billingSumm = new Label("Сумма: " + Long.toString(item.getSumm()));
							Label billingDescription = new Label("Описание: " + item.getDescription());
							billingOperation.setWrapText(true);
							billingBankAccountFrom.setWrapText(true);
							billingSumm.setWrapText(true);
							billingDescription.setWrapText(true);
							
							if (item.getDescription().isEmpty() == true) {
								billingDescription.setVisible(false);
							}
							vBoxBillingItem.setSpacing(5);
							vBoxBillingItem.setMaxHeight(USE_COMPUTED_SIZE);
							vBoxBillingItem.getChildren().addAll(billingOperation, billingBankAccountFrom, billingSumm, billingDescription);
							setGraphic(vBoxBillingItem);
						}
					}

				};
			}

		});
		AnchorPane.setTopAnchor(listViewInput, 0.0);
		AnchorPane.setLeftAnchor(listViewInput, 0.0);
		AnchorPane.setRightAnchor(listViewInput, 0.0);
		AnchorPane.setBottomAnchor(listViewInput, 0.0);
		listViewAnchorInput.getChildren().add(listViewInput);
		listViewInput.getSelectionModel().select(0);
	}
	
	public void loadOut() {
		ObservableList<BillingList> data = FXCollections.observableArrayList(billingOutAR);
		listViewOut = new ListView<BillingList>(data);

		listViewOut.setCellFactory(new Callback<ListView<BillingList>, ListCell<BillingList>>() {

			@Override
			public ListCell<BillingList> call(ListView<BillingList> arg0) {
				return new ListCell<BillingList>() {
					@Override
					protected void updateItem(BillingList item, boolean bln) {
						super.updateItem(item, bln);
						if (item != null) {
							VBox vBoxBillingItem = new VBox();
							vBoxBillingItem.setPrefWidth(USE_COMPUTED_SIZE);
							Label billingOperation = new Label("Операция: " + item.getOperation());
							Label billingBankAccountFrom = new Label("Для счета: " + Long.toString(item.getBankAccountTo()));
							Label billingSumm = new Label("Сумма: " + Long.toString(item.getSumm()));
							Label billingDescription = new Label("Описание: " + item.getDescription());
							billingOperation.setWrapText(true);
							billingBankAccountFrom.setWrapText(true);
							billingSumm.setWrapText(true);
							billingDescription.setWrapText(true);
							
							if (item.getDescription().isEmpty() == true) {
								billingDescription.setVisible(false);
							}
							vBoxBillingItem.setSpacing(5);
							vBoxBillingItem.setMaxHeight(USE_COMPUTED_SIZE);
							vBoxBillingItem.getChildren().addAll(billingOperation, billingBankAccountFrom, billingSumm, billingDescription);
							setGraphic(vBoxBillingItem);
						}
					}

				};
			}

		});
		AnchorPane.setTopAnchor(listViewOut, 0.0);
		AnchorPane.setLeftAnchor(listViewOut, 0.0);
		AnchorPane.setRightAnchor(listViewOut, 0.0);
		AnchorPane.setBottomAnchor(listViewOut, 0.0);
		listViewAnchorOut.getChildren().add(listViewOut);
		listViewOut.getSelectionModel().select(0);
	}

	@FXML
	public void backButton() {
		myController.setScreen(Main.screen2ID, Main.screen2File);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		resourceUrl = String.format(formatUrlMaskGetIO, MainController.serverAdress, MainController.serverPort,
				"/" + currentAccount.getId());
		System.out.println(resourceUrl);
		billingInputAR = new ArrayList<BillingList>();
		jsonObject = new ConnectToResource().getResorce(resourceUrl);
		JsonArray jsonArrayInput = jsonObject.getJsonArray("BillingInpuList");
		for (JsonValue value : jsonArrayInput) {
			JsonObject jsonObjectInArray = (JsonObject) value;
			billingInputAR.add(new BillingList(jsonObjectInArray.getJsonNumber("id").longValue(), 
					jsonObjectInArray.getString("operation"), 
					jsonObjectInArray.getJsonNumber("bankAccountFrom").longValue(),
					jsonObjectInArray.getJsonNumber("bankAccountTo").longValue(), 
					jsonObjectInArray.getString("description"), 
					jsonObjectInArray.getJsonNumber("summ").longValue())); 
					
		}
		loadInput();
		billingOutAR = new ArrayList<BillingList>();
		JsonArray jsonArrayOut = jsonObject.getJsonArray("BillingOutList");
		for (JsonValue value : jsonArrayOut) {
			JsonObject jsonObjectInArray = (JsonObject) value;
			billingOutAR.add(new BillingList(jsonObjectInArray.getJsonNumber("id").longValue(), 
					jsonObjectInArray.getString("operation"), 
					jsonObjectInArray.getJsonNumber("bankAccountFrom").longValue(),
					jsonObjectInArray.getJsonNumber("bankAccountTo").longValue(), 
					jsonObjectInArray.getString("description"), 
					jsonObjectInArray.getJsonNumber("summ").longValue())); 
		}
		loadOut();
	
		
		//load();
	}

}
