package view;

import java.io.File;
import java.util.Vector;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import listeners.ViewListener;

public class StoreView extends Application {

	private static Vector<ViewListener> allListeners = new Vector<ViewListener>();
	
	boolean selectionByUser = true;
	private File binFile = new File("products.txt");

	// declare scene
	Scene firstScene;
	Scene secondScene;

	// main pane: VBox
	StackPane stackPane = new StackPane();
	VBox mainPane = new VBox();

	// headlines texts nodes
	Text title = new Text("Welcome to the store");
	Text customerDetails = new Text("    Customer datails: ");
	Text productDetails = new Text("    Product datails: ");
	Text storeStatus = new Text("Store status: ");
	Text status = new Text("");
	Text selection = new Text("Please select sort type");

	// products nodes
	Label labelProductName = new Label("Product name:                  ");
	Label labelProductNumber = new Label("Product number:              ");
	Label priceOfProduct = new Label("Product's price:                 ");
	Label priceOfProductSale = new Label("Product's price of sale:     ");
	Label labelMessege = new Label("Enter messege: ");
	Label lblMust = new Label("*");
	
	// customer nodes
	Label customerName = new Label("Customer name:               ");
	Label customerPhoneNumber = new Label("Customer phone-number:");
	CheckBox checkBox = new CheckBox("Get notifications");

	// create gridPanes
	GridPane gridPaneProduct = new GridPane();
	GridPane gridPaneCustomer = new GridPane();
	GridPane gridPaneButtons = new GridPane();

	// text fields
	static TextField tfProductName = new TextField();
	static TextField tfProductNumber = new TextField();
	static TextField tfPriceOfProduct = new TextField();
	static TextField tfPriceOfProductSale = new TextField();
	static TextField tfCustomerName = new TextField();
	static TextField tfCustomerPhoneNumber = new TextField();
	static TextField tfMessege = new TextField();

	// scroll bar for output
	VBox allMessages = new VBox();
	ScrollPane scrollBar = new ScrollPane();

	// Radio buttons
	ToggleGroup tglSort = new ToggleGroup();
	RadioButton rbASC = new RadioButton("Ascending");
	RadioButton rbDESC = new RadioButton("Descending");
	RadioButton rbInsert = new RadioButton("Insertion");

	// panes
	Pane messegePane = new HBox(20);
	Pane hBPane = new HBox(40);
	Pane textFieldsPane = new VBox(15);
	Pane rbPane = new VBox(30);
	Pane productDetailsPane = new VBox(20);
	Pane customerDetailsPane = new VBox(20);
	Pane userPane = new HBox(20);
	Pane rightPane = new VBox(20);

	// Buttons
	Button btnInsertProduct = new Button("Insert New Product");
	Button btnShowProductInfoByNumber = new Button("Search Product");
	Button btnShowAllProducts = new Button("Show All Products");
	Button btnRemoveLastProductAdded = new Button("Remove Last Product");
	Button btnRemoveByProductNumber = new Button("Remove By P-Number");
	Button btnRemoveAllProducts = new Button("Remove all products");
	Button btnShowProfit = new Button("Show profit");
	Button btnSendMessage = new Button("Send Message");
	Button btnSubmitSort = new Button("Submit");

	// label for exeptions
	Label lblException = new Label();
	Vector<Label> customers = new Vector<Label>();

	public StoreView(Stage primaryStage) {

		/* creating tgl group for RBs */
		rbASC.setToggleGroup(tglSort);
		rbDESC.setToggleGroup(tglSort);
		rbInsert.setToggleGroup(tglSort);

		/* scroll bar */
		scrollBar.setContent(lblException);
		scrollBar.setPrefSize(400, 330);

		/* panes getChilden() */
		productDetailsPane.getChildren().addAll(productDetails);
		rbPane.getChildren().addAll(selection, rbASC, rbDESC, rbInsert, btnSubmitSort);
		userPane.getChildren().addAll(scrollBar, messegePane);
		messegePane.getChildren().addAll(labelMessege, tfMessege, btnSendMessage);
		rightPane.getChildren().addAll(userPane, messegePane);

		/* product's gridPane */
		gridPaneProduct.add(labelProductName, 1, 0); // column=1 row=0
		gridPaneProduct.add(tfProductName, 2, 0); // column=2 row=0
		gridPaneProduct.add(labelProductNumber, 1, 1);
		gridPaneProduct.add(tfProductNumber, 2, 1);
		gridPaneProduct.add(lblMust, 3, 1);	// adding *
		gridPaneProduct.add(priceOfProduct, 1, 2);
		gridPaneProduct.add(tfPriceOfProduct, 2, 2);
		gridPaneProduct.add(priceOfProductSale, 1, 3);
		gridPaneProduct.add(tfPriceOfProductSale, 2, 3);
		productDetailsPane.getChildren().add(gridPaneProduct);

		/* customer's gridPane */
		gridPaneCustomer.add(customerName, 1, 0); // column=1 row=0
		gridPaneCustomer.add(tfCustomerName, 2, 0); // column=2 row=0
		gridPaneCustomer.add(customerPhoneNumber, 1, 1);
		gridPaneCustomer.add(tfCustomerPhoneNumber, 2, 1);
		gridPaneCustomer.add(checkBox, 1, 2);
		customerDetailsPane.getChildren().add(gridPaneCustomer);

		/* Buttons gridPane */
		gridPaneButtons.add(btnInsertProduct, 1, 0); // column=1 row=0
		gridPaneButtons.add(btnShowProductInfoByNumber, 2, 0); // column=2 row=0
		gridPaneButtons.add(btnShowAllProducts, 3, 0);
		gridPaneButtons.add(btnShowProfit, 4, 0);
		gridPaneButtons.add(btnRemoveLastProductAdded, 1, 1);
		gridPaneButtons.add(btnRemoveByProductNumber, 2, 1);
		gridPaneButtons.add(btnRemoveAllProducts, 3, 1);

		/* design gridPanes */
		gridPaneProduct.setHgap(10); // horizontal gap in pixels => that's what you are asking for
		gridPaneProduct.setVgap(10); // vertical gap in pixels
		gridPaneProduct.setPadding(new Insets(10, 10, 10, 10)); // margins around the whole grid

		gridPaneCustomer.setHgap(10);
		gridPaneCustomer.setVgap(10);
		gridPaneCustomer.setPadding(new Insets(10, 10, 10, 10));

		gridPaneButtons.setHgap(10);
		gridPaneButtons.setVgap(10);
		gridPaneButtons.setPadding(new Insets(10, 10, 10, 10));
		gridPaneButtons.setHgap(30);
		gridPaneButtons.setAlignment(Pos.CENTER);

		/* buttons design */
		btnInsertProduct.setMinWidth(150);
		btnShowProductInfoByNumber.setMinWidth(150);
		btnShowAllProducts.setMinWidth(150);
		btnRemoveLastProductAdded.setMinWidth(150);
		btnShowProfit.setMinWidth(150);
		btnSendMessage.setMinWidth(100);
		btnRemoveAllProducts.setMinWidth(150);
		btnRemoveByProductNumber.setMinWidth(150);

		/* design rbPane */
		selection.setStyle("-fx-font: 20 Ariel;");
		btnSubmitSort.setMinWidth(140);
		btnSubmitSort.setStyle("-fx-font-size:14");
		rbPane.setStyle("-fx-border-width: .25px; -fx-padding: 220 200 300 330;");
		rbASC.setScaleX(1.2);
		rbASC.setScaleY(1.2);
		rbDESC.setScaleX(1.2);
		rbDESC.setScaleY(1.2);
		rbInsert.setScaleX(1.2);
		rbInsert.setScaleY(1.2);

		/* design labels */
		lblMust.setTextFill(Color.RED);

		/* design headline (title) */
		title.setFill(Color.SILVER);
		title.setStroke(Color.BLACK);
		title.setFont(Font.font("Verdana", FontWeight.BOLD, 40));

		/* design user massages pane */
		status.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
		lblException.setPadding(new Insets(10));

		/* design buttons and buttonsPane */
		btnInsertProduct.setTextFill(Color.BLACK);
		btnShowProductInfoByNumber.setTextFill(Color.BLACK);
		btnShowAllProducts.setTextFill(Color.BLACK);
		btnRemoveLastProductAdded.setTextFill(Color.BLACK);
		btnShowProfit.setTextFill(Color.BLACK);
		btnSendMessage.setTextFill(Color.BLACK);
		btnRemoveAllProducts.setTextFill(Color.BLACK);

		btnSendMessage.setStyle("-fx-text-fill: rgb(33, 33, 33);\r\n" + "    -fx-border-color: rgb(33, 33, 33);\r\n"
				+ "    -fx-border-radius: 4;\r\n" + "    -fx-padding: 3 6 6 6;");
		btnRemoveByProductNumber
				.setStyle("-fx-text-fill: rgb(33, 33, 33);\r\n" + "    -fx-border-color: rgb(33, 33, 33);\r\n"
						+ "    -fx-border-radius: 4;\r\n" + "    -fx-padding: 7 6 6 6;");
		btnInsertProduct.setStyle("-fx-text-fill: rgb(33, 33, 33);\r\n" + "    -fx-border-color: rgb(33, 33, 33);\r\n"
				+ "    -fx-border-radius: 4;\r\n" + "    -fx-padding: 7 6 6 6;");
		btnShowProductInfoByNumber
				.setStyle("-fx-text-fill: rgb(33, 33, 33);\r\n" + "    -fx-border-color: rgb(33, 33, 33);\r\n"
						+ "    -fx-border-radius: 4;\r\n" + "    -fx-padding: 7 6 6 6;");
		btnShowAllProducts.setStyle("-fx-text-fill: rgb(33, 33, 33);\r\n" + "    -fx-border-color: rgb(33, 33, 33);\r\n"
				+ "    -fx-border-radius: 4;\r\n" + "    -fx-padding: 7 6 6 6;");
		btnRemoveLastProductAdded
				.setStyle("-fx-text-fill: rgb(33, 33, 33);\r\n" + "    -fx-border-color: rgb(33, 33, 33);\r\n"
						+ "    -fx-border-radius: 4;\r\n" + "    -fx-padding: 7 6 6 6;");
		btnShowProfit.setStyle("-fx-text-fill: rgb(33, 33, 33);\r\n" + "    -fx-border-color: rgb(33, 33, 33);\r\n"
				+ "    -fx-border-radius: 4;\r\n" + "    -fx-padding: 7 6 6 6;");
		btnRemoveAllProducts
				.setStyle("-fx-text-fill: rgb(33, 33, 33);\r\n" + "    -fx-border-color: rgb(33, 33, 33);\r\n"
						+ "    -fx-border-radius: 4;\r\n" + "    -fx-padding: 7 6 6 6;");

		/* design headline of customerDetails & productDetails */
		productDetails.setFill(Color.BLACK);
		productDetails.setStyle("-fx-font: 20 Allan;");
		customerDetails.setFill(Color.BLACK);
		customerDetails.setStyle("-fx-font: 20 Allan;");
		storeStatus.setFill(Color.BLACK);
		storeStatus.setStyle("-fx-font: 20 Allan;");

		/* add all nodes to pane */
		mainPane.getChildren().addAll(title, hBPane, gridPaneButtons);
		hBPane.getChildren().addAll(textFieldsPane, rightPane);
		textFieldsPane.getChildren().addAll(productDetails, productDetailsPane, customerDetails, customerDetailsPane);

		mainPane.setAlignment(Pos.CENTER);

		/* vBox pane design */
		mainPane.setSpacing(20);
		mainPane.setPadding(new Insets(20));

		/* to able the use of number only in textField: tfPriceOfProduct */
		tfPriceOfProduct.setTextFormatter(new TextFormatter<>(c -> {
			if (!c.getControlNewText().matches("\\d*")) {
				// user message
				status.setFill(Color.RED);
				status.setText(" Error: Price can contain numbers only.");
				return null;
			} else {
				status.setText(" ");
				return c;
			}

		}));

		/* to able the use of number only in textField: tfPriceOfProduct */
		tfPriceOfProductSale.setTextFormatter(new TextFormatter<>(c -> {
			if (!c.getControlNewText().matches("\\d*")) {
				// user message
				lblException.setText(" Error: Price can contain numbers only.");
				return null;
			} else {
				lblException.setVisible(false);
				return c;
			}
		}));

		btnSubmitSort.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				RadioButton selected = (RadioButton) tglSort.getSelectedToggle();
				String sort = selected.getText();

				for (ViewListener l : allListeners) {
					l.selectionSortToModel(sort);
				}

				stackPane.getChildren().addAll(mainPane);
				rbPane.setVisible(false);
			}
		});

		// Main stage button handler
		btnInsertProduct.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				// get TextFields
				String name = tfProductName.getText();
				String catalogProduct = tfProductNumber.getText();
				int price,priceOfSale;
				if(getTfPriceOfProduct().getText().isEmpty())
					price = 0;
				else
					price = Integer.parseInt(getTfPriceOfProduct().getText());
				if(getTfPriceOfProductSale().getText().isEmpty())
					priceOfSale = 0;
				else
					priceOfSale = Integer.parseInt(getTfPriceOfProductSale().getText());

				String customerName = tfCustomerName.getText();
				String phoneNumber = getTfCustomerPhoneNumber().getText();
				boolean notifications = checkBox.isSelected();

				if (!tfProductNumber.getText().isEmpty()) {
					for (ViewListener l : allListeners) {
						l.addProductToModel(name, catalogProduct, price, priceOfSale, customerName, phoneNumber,
								notifications);
					}
					// clear after insert
					tfProductName.clear();
					tfProductNumber.clear();
					tfPriceOfProduct.clear();
					tfPriceOfProductSale.clear();

					tfCustomerName.clear();
					tfCustomerPhoneNumber.clear();
					checkBox.setSelected(false);
					
					lblException.setVisible(true);
					lblException.setText("Product has been successfully added");
				} else {
					lblException.setText("Product number can't be empty");
					lblException.setVisible(true);
				}
			}
		});

		// loadProductToModel

		btnShowProductInfoByNumber.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				String catalogProduct = tfProductNumber.getText();

				if (!tfProductNumber.getText().isEmpty()) {
					lblException.setVisible(false);
					for (ViewListener l : allListeners) {
						l.loadProductToModel(catalogProduct);
					}
				} else {
					lblException.setText("Product number can't be empty");
					lblException.setVisible(true);
				}
			}
		});

		btnShowAllProducts.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				for (ViewListener l : allListeners) {
					l.showProductsToModel();
				}

			}
		});

		btnRemoveLastProductAdded.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				for (ViewListener l : allListeners) {
					l.removeLastProductAddedToModel();
				}

			}
		});
		
		btnRemoveByProductNumber.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				String pn = tfProductNumber.getText();
				for (ViewListener l : allListeners) {
					try {
						l.removeProductByPN(pn);
						tfProductNumber.clear();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
		
		btnRemoveAllProducts.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				for (ViewListener l : allListeners) {
					l.removeAllProductsToModel();
				}

			}
		});

		btnShowProfit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				for (ViewListener l : allListeners) {
					l.showProfitToModel();
				}

			}
		});

		btnSendMessage.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String msg = tfMessege.getText();
				
				if(msg.length() != 0) {
					for (ViewListener l : allListeners) {
						l.sendMessageToModel(msg);
					}
					tfMessege.clear();
					scrollBar.setContent(allMessages);
					
					Thread tr = new Thread(() -> {
						try {
							for (Label l : customers) {
								Thread.sleep(2000);
								Platform.runLater(() -> {
									l.setVisible(true);
								});
							}
							Thread.sleep(3500);
							Platform.runLater(() -> {
								setLblException("");
								scrollBar.setContent(lblException);
							});
							
						} catch (Exception e) {}
					});
					tr.start();
					
				}
				else {
					lblException.setVisible(true);
					lblException.setText("Message can not be empty");
				}
				
			}
		});
			
		if (isFileEmpty()) {
			stackPane.getChildren().addAll(rbPane);
			Scene mainScene = new Scene(stackPane, 830, 600);
			this.firstScene = mainScene;
			rbPane.setPadding(new Insets(200));
			primaryStage.setScene(firstScene);

			// activateStartingScreen();
		} else {
			// activateMainScreen();
			stackPane.getChildren().addAll(mainPane);
			Scene mainScene = new Scene(stackPane);
			this.secondScene = mainScene;
			primaryStage.setScene(secondScene);

		}
		primaryStage.centerOnScreen();
		primaryStage.setResizable(false);
		primaryStage.alwaysOnTopProperty();
		primaryStage.setTitle("DisplayMovingMessage");
		primaryStage.show();

		/*
		 * Scene mainScene = new Scene(stackPane, 830, 600); this.secendScene =
		 * mainScene;
		 * 
		 * primaryStage.setScene(secendScene); primaryStage.centerOnScreen();
		 * primaryStage.setResizable(false); primaryStage.alwaysOnTopProperty();
		 * primaryStage.setTitle("DisplayMovingMessage"); primaryStage.show();
		 */

	}

	private void activateMainScreen() {
		stackPane.getChildren().addAll(mainPane);
	}

	private void activateStartingScreen() {

		rbPane.setPadding(new Insets(170));
		stackPane.getChildren().addAll(rbPane);
	}
	
	public boolean isFileEmpty() {
		if(binFile.length() != 0)
			return false;
		else return true;
	}

	public ScrollPane getScrollBar() {
		return scrollBar;
	}

	public void setScrollBar(ScrollPane scrollBar) {
		this.scrollBar = scrollBar;
	}

	public VBox getAllMessages() {
		return allMessages;
	}

	public Vector<Label> getCustomers() {
		return customers;
	}

	public void createNewMsg(String msg) {
		customers.add(new Label(msg));
	}

	public Text getStoreStatus() {
		return storeStatus;
	}

	public void setStoreStatus(Text storeStatus) {
		this.storeStatus = storeStatus;
	}

	public Text getStatus() {
		return status;
	}

	public void setStatus(Text status) {
		this.status = status;
	}

	public Pane getUserPane() {
		return userPane;
	}

	public void setUserPane(Pane userPane) {
		this.userPane = userPane;
	}

	public Text getText() {
		return title;
	}

	public void setText(Text text) {
		this.title = text;
	}

	public Text getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(Text customerDetails) {
		this.customerDetails = customerDetails;
	}

	public Text getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(Text productDetails) {
		this.productDetails = productDetails;
	}

	public Label getLabelProductName() {
		return labelProductName;
	}

	public void setLabelProductName(Label labelProductName) {
		this.labelProductName = labelProductName;
	}

	public Label getLabelProductNumber() {
		return labelProductNumber;
	}

	public void setLabelProductNumber(Label labelProductNumber) {
		this.labelProductNumber = labelProductNumber;
	}

	public Label getPriceOfProduct() {
		return priceOfProduct;
	}

	public void setPriceOfProduct(Label priceOfProduct) {
		this.priceOfProduct = priceOfProduct;
	}

	public Label getPriceOfProductSale() {
		return priceOfProductSale;
	}

	public void setPriceOfProductSale(Label priceOfProductSale) {
		this.priceOfProductSale = priceOfProductSale;
	}

	public Label getCustomerName() {
		return customerName;
	}

	public void setCustomerName(Label customerName) {
		this.customerName = customerName;
	}

	public Label getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}

	public void setCustomerPhoneNumber(Label customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}

	public CheckBox getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(boolean booleanNotifications) {
		this.checkBox.setSelected(booleanNotifications);
	}

	public static TextField getTfProductName() {
		return tfProductName;
	}

	public void setTfProductName(TextField tfProductName) {
		this.tfProductName = tfProductName;
	}

	public void setTfProductName(String tfProductName) {
		this.tfProductName.setText(tfProductName);
	}

	public TextField getTfProductNumber() {
		return tfProductNumber;
	}

	public void setTfProductNumber(TextField tfProductNumber) {
		this.tfProductNumber = tfProductNumber;
	}

	public static TextField getTfPriceOfProduct() {
		return tfPriceOfProduct;
	}

	public void setTfPriceOfProduct(int price) {
		this.tfPriceOfProduct.setText(Integer.toString(price));
	}

	public static TextField getTfPriceOfProductSale() {
		return tfPriceOfProductSale;
	}

	public void setTfPriceOfProductSale(int priceOfSale) {
		this.tfPriceOfProductSale.setText(Integer.toString(priceOfSale));
		;
	}

	public TextField getTfCustomerName() {
		return tfCustomerName;
	}

	public void setTfCustomerName(TextField tfCustomerName) {
		this.tfCustomerName = tfCustomerName;
	}

	public void setTfCustomerName(String tfCustomerName) {
		this.tfCustomerName.setText(tfCustomerName); // set the text in the tf
	}

	public TextField getTfCustomerPhoneNumber() {
		return tfCustomerPhoneNumber;
	}

	public void setTfCustomerPhoneNumber(String phoneNumber) {
		this.tfCustomerPhoneNumber.setText(phoneNumber);
	}

	public Pane getPricesPane() {
		return customerDetailsPane;
	}

	public void setPricesPane(Pane pricesPane) {
		this.customerDetailsPane = pricesPane;
	}

	public Pane getCustomerDetailsPane() {
		return customerDetailsPane;
	}

	public void setCustomerDetailsPane(Pane customerDetailsPane) {
		this.customerDetailsPane = customerDetailsPane;
	}

	public Button getBtnInsertProduct() {
		return btnInsertProduct;
	}

	public void setBtnInsertProduct(Button btnInsertProduct) {
		this.btnInsertProduct = btnInsertProduct;
	}

	public Button getBtnRemoveAllProducts() {
		return btnRemoveAllProducts;
	}

	public void setBtnRemoveAllProducts(Button btnRemoveAllProducts) {
		this.btnRemoveAllProducts = btnRemoveAllProducts;
	}

	public Button getBtnShowProductInfoByNumber() {
		return btnShowProductInfoByNumber;
	}

	public void setBtnShowProductInfoByNumber(Button btnShowProductInfoByNumber) {
		this.btnShowProductInfoByNumber = btnShowProductInfoByNumber;
	}

	public Button getBtnSshowAllProducts() {
		return btnShowAllProducts;
	}

	public Button getBtnRemoveLastProductAdded() {
		return btnRemoveLastProductAdded;
	}

	public void setBtnSshowAllProducts(Button btnSshowAllProducts) {
		this.btnShowAllProducts = btnSshowAllProducts;
	}

	public void clearTextFields() {
		tfCustomerName.clear();
		tfCustomerPhoneNumber.clear();
		tfPriceOfProduct.clear();
		tfPriceOfProductSale.clear();
		tfProductName.clear();
		tfProductNumber.clear();
	}

	public void registerListeners(ViewListener newListener) {
		allListeners.add(newListener);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	}

	public Label getLblException() {
		return lblException;
	}

	public void setLblException(String string) {
		lblException.setText(string);
	}

}
