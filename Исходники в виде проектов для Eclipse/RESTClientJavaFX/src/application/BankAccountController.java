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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.BankAccount;
import model.ControlledScreen;
import model.User;

public class BankAccountController implements Initializable, ControlledScreen {
	private JsonObject jsonObject;
	ScreensController myController;
	public static User currentUser;
	private String resourceUrl;
	private String formatUrlMask = "http://%s:%s/RESTServer/bankAccount%s";
	private String formatUrlMaskForSearch = "http://%s:%s/RESTServer/bankAccountSearch%s";
	@FXML
	private TextField accountNumField;
	@FXML
	private TextField accountSummField;
	@FXML
	private TextField accountDescField;
	@FXML
	private AnchorPane listViewAnchor;
	@FXML
	private VBox bankAccountBox;
	@FXML
	private Button addAccountButton;
	@FXML
	private Text errorText;

	private ArrayList<BankAccount> bankAccountAR;

	ListView<BankAccount> listView;

	@Override
	public void setScreenParent(ScreensController screenParent) {
		// TODO Auto-generated method stub
		myController = screenParent;
	}

	@FXML
	public void addAccount() {
		if (accountNumField.getText().isEmpty() != true && accountNumField.getText().isEmpty() != true) {
			resourceUrl = String.format(formatUrlMaskForSearch, MainController.serverAdress, MainController.serverPort,
					"/" + accountNumField.getText());
			JsonObject searchBankAccount = new ConnectToResource().getResorce(resourceUrl);
			JsonArray arrayinSearch = searchBankAccount.getJsonArray("BankAccounts");

			if (arrayinSearch.size() == 0) {
				addAccountButton.setDisable(false);
				bankAccountBox.setVisible(false);
				JsonObject puttedJson = Json.createObjectBuilder().add("userId", currentUser.getId())
						.add("BankAccount",
								Json.createObjectBuilder().add("number", Long.parseLong(accountNumField.getText()))
										.add("description", accountDescField.getText())
										.add("summ", Long.parseLong(accountSummField.getText())).build())
						.build();
				resourceUrl = String.format(formatUrlMask, MainController.serverAdress, MainController.serverPort, "");
				jsonObject = new ConnectToResource().putResorce(resourceUrl, puttedJson);
			} else {
				errorText.setText("Счет с таким номером уже существует в системе.");
			}
		} else {
			errorText.setText("Поля 'Номер счета', 'Сумма' являются обязательными.");
		}

		initialize(null, null);
	}

	@FXML
	public void showAddAccount() {
		addAccountButton.setDisable(true);
		accountNumField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.matches("\\d*")) {
					try {

						int value = Integer.parseInt(newValue);
					} catch (NumberFormatException e) {
						// TODO: handle exception
					}
				} else {
					accountNumField.setText(oldValue);
				}
			}
		});

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
		bankAccountBox.setVisible(true);
	}

	public void load() {
		ObservableList<BankAccount> data = FXCollections.observableArrayList(bankAccountAR);
		listView = new ListView<BankAccount>(data);

		listView.setCellFactory(new Callback<ListView<BankAccount>, ListCell<BankAccount>>() {

			@Override
			public ListCell<BankAccount> call(ListView<BankAccount> arg0) {
				return new ListCell<BankAccount>() {
					@Override
					protected void updateItem(BankAccount item, boolean bln) {
						super.updateItem(item, bln);
						if (item != null) {
							this.setId("exerciseNameItem");
							VBox vBoxAccountItem = new VBox();
							vBoxAccountItem.setPrefWidth(USE_COMPUTED_SIZE);
							Label accountNum = new Label("Номер счета: " + Long.toString(item.getNumber()));
							Label accountSumm = new Label("Сумма на счету: " + Long.toString(item.getSumm()));
							Label accountDescription = new Label("Описание счета: " + item.getDescription());
							accountNum.setWrapText(true);
							accountSumm.setWrapText(true);
							accountDescription.setWrapText(true);
							if (item.getDescription().isEmpty() == true) {
								accountDescription.setVisible(false);
							}
							vBoxAccountItem.setSpacing(5);
							vBoxAccountItem.setMaxHeight(USE_COMPUTED_SIZE);
							vBoxAccountItem.getChildren().addAll(accountNum, accountSumm, accountDescription);
							setGraphic(vBoxAccountItem);
						}

						listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent event) {
								if (event.getClickCount() == 2) {
									selectAccount();
								}
							}
						});
						listView.setOnKeyPressed(new EventHandler<KeyEvent>() {
							public void handle(KeyEvent keyEvent) {
								if (keyEvent.getCode() == KeyCode.ENTER) {
									selectAccount();
								}
							}
						});
					}

				};
			}

		});
		AnchorPane.setTopAnchor(listView, 0.0);
		AnchorPane.setLeftAnchor(listView, 0.0);
		AnchorPane.setRightAnchor(listView, 0.0);
		AnchorPane.setBottomAnchor(listView, 0.0);
		listViewAnchor.getChildren().add(listView);
		listView.getSelectionModel().select(0);
	}

	@FXML
	public void backButton() {
		myController.setScreen(Main.screen1ID, Main.screen1File);
	}

	@FXML
	public void selectAccount() {
		BillingController.currentUser = currentUser;
		BillingController.currentAccount = listView.getSelectionModel().getSelectedItem();
		System.out.println(listView.getSelectionModel().getSelectedItem());
		myController.setScreen(Main.screen3ID, Main.screen3File);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		resourceUrl = String.format(formatUrlMask, MainController.serverAdress, MainController.serverPort,
				"/" + currentUser.getId());
		bankAccountAR = new ArrayList<BankAccount>();
		jsonObject = new ConnectToResource().getResorce(resourceUrl);
		System.out.println(jsonObject);
		if (jsonObject != null) {
			JsonArray jsonArray = jsonObject.getJsonArray("BankAccounts");
			for (JsonValue value : jsonArray) {
				JsonObject jsonObjectInArray = (JsonObject) value;
				bankAccountAR.add(new BankAccount(jsonObjectInArray.getJsonNumber("id").longValue(),
						jsonObjectInArray.getJsonNumber("number").longValue(),
						jsonObjectInArray.getString("description"),
						jsonObjectInArray.getJsonNumber("summ").longValue()));
			}

		}
		load();

	}

}
