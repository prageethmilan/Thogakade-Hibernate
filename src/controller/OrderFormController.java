package controller;

import bo.BOFactory;
import bo.custom.PlaceOrderFormBO;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import dto.ItemDTO;
import entity.Customer;
import entity.Item;
import entity.OrderDetail;
import entity.Orders;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.tm.CartTM;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class OrderFormController {
    public AnchorPane root;
    public Label lblOrderId;
    public JFXComboBox<String> cmbCustomerId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXComboBox<String> cmbItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtUnitPrice;
    public TextField txtQtyForSell;
    public Button btnAddToCart;
    public TableView<CartTM> tblCart;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public Label lblTotal;
    public Button btnPlaceOrder;
    public TableColumn colTotal;
    public TableColumn colDelete;
    PlaceOrderFormBO orderFormBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);
    ObservableList<CartTM> cartList = FXCollections.observableArrayList();
    private ArrayList<ItemDTO> allItems;

    public void initialize() {
        setItemToTable();
        btnAddToCart.setDisable(true);
        try {
            loadCustomerIds();
            loadItemCodes();
            generateOrderId();
        } catch (Exception e) {

        }
        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!cmbItemCode.getSelectionModel().isEmpty()) {
                    btnAddToCart.setDisable(false);
                }
                try {
                    setCustomerDetails(newValue);
                } catch (Exception e) {

                }
            } else {
                txtName.clear();
                txtAddress.clear();
                txtContact.clear();
            }
        });
        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!cmbCustomerId.getSelectionModel().isEmpty()) {
                    btnAddToCart.setDisable(false);
                }
                try {
                    setItemDetails(newValue);
                } catch (Exception e) {

                }
            } else {
                txtDescription.clear();
                txtUnitPrice.clear();
                txtQtyOnHand.clear();
            }
        });
    }

    private void setItemToTable() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btn"));

    }

    private void setItemDetails(String code) throws Exception {
        ItemDTO itemDTO = orderFormBO.getItems(code);
        if (itemDTO == null) {

        } else {
            txtDescription.setText(itemDTO.getDescription());
            txtUnitPrice.setText(String.valueOf(itemDTO.getPrice()));
            Optional<CartTM> optOrderDetail = tblCart.getItems().stream().filter(detail -> detail.getCode().equals(code)).findFirst();
            txtQtyOnHand.setText((optOrderDetail.isPresent() ? itemDTO.getQtyOnHand() - optOrderDetail.get().getQty() : itemDTO.getQtyOnHand()) + "");
        }
    }

    private void setCustomerDetails(String id) throws Exception {
        CustomerDTO customerDTO = orderFormBO.getCustomer(id);
        txtName.setText(customerDTO.getName());
        txtAddress.setText(customerDTO.getAddress());
        txtContact.setText(String.valueOf(customerDTO.getContact()));
    }

    private void loadItemCodes() throws Exception {
        ArrayList<String> itemCodes = orderFormBO.getItemCodes();
        cmbItemCode.getItems().addAll(itemCodes);
    }

    private void loadCustomerIds() throws Exception {
        ArrayList<String> customerIds = orderFormBO.getCustomerIds();
        cmbCustomerId.getItems().addAll(customerIds);
    }

    private void generateOrderId() throws Exception {
        lblOrderId.setText(orderFormBO.generateId());
    }

    public void navigateToHomeOnAction(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/MainForm.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }

    public void addToCartOnAction(MouseEvent mouseEvent) {
        if (!txtQtyForSell.getText().matches("^\\d+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid quantity").show();
            return;
        }
        Button button = new Button("Delete");
        if (!cmbCustomerId.getSelectionModel().isEmpty() && !cmbItemCode.getSelectionModel().isEmpty() && !txtQtyForSell.getText().equals("")) {
            String itemCode = cmbItemCode.getSelectionModel().getSelectedItem();
            String description = txtDescription.getText();
            double unitPrice = Double.parseDouble(txtUnitPrice.getText());
            int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
            int qtyForSell = Integer.parseInt(txtQtyForSell.getText());
            double total = unitPrice * qtyForSell;
            if (qtyOnHand < qtyForSell) {
                new Alert(Alert.AlertType.WARNING, "Invalid Quantity", ButtonType.CLOSE).showAndWait();
                return;
            }
            CartTM cartTM = new CartTM(itemCode, description, unitPrice, qtyForSell, total, button);
            int rowNumber = isExists(cartTM);
            if (rowNumber == -1) {
                cartList.add(cartTM);
            } else {
                CartTM temp = cartList.get(rowNumber);
                CartTM newTm = new CartTM(itemCode, description, unitPrice, qtyForSell + temp.getQty(), total + temp.getTotal(), button);
                cartList.remove(rowNumber);
                cartList.add(newTm);
            }
            btnOnAction(button);
            tblCart.setItems(cartList);
            txtQtyForSell.clear();
            calculateTotal();
            cmbItemCode.getSelectionModel().clearSelection();
            btnAddToCart.setDisable(true);
        }
    }

    private void btnOnAction(Button button) {
        button.setOnAction((e) -> {
            try {
                ButtonType ok = new ButtonType("YES",
                        ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("NO",
                        ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are You Sure ?", ok, no);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.orElse(no) == ok) {
                    int index = tblCart.getSelectionModel().getFocusedIndex();
                    cartList.remove(index);
                    tblCart.refresh();
                } else {
                }

            } catch (Exception e1) {

            }

        });
    }

    private void calculateTotal() {
        double ttl = 0;
        for (CartTM tm : cartList
        ) {
            ttl += tm.getTotal();
        }
        lblTotal.setText(String.valueOf(ttl));
    }

    private int isExists(CartTM cartTM) {
        for (int i = 0; i < cartList.size(); i++) {
            if (cartTM.getCode().equals(cartList.get(i).getCode())) {
                return i;
            }
        }
        return -1;
    }

    public void clearOrderOnAction(MouseEvent mouseEvent) {
        cmbItemCode.getSelectionModel().clearSelection();
        btnAddToCart.setDisable(true);
    }

    public void placeOrderOnAction(MouseEvent mouseEvent) throws Exception {
        Customer customer = new Customer(cmbCustomerId.getValue(), txtName.getText(), txtAddress.getText(), Integer.parseInt(txtContact.getText()));
        ArrayList<ItemDTO> itemList = getAllItems();
        Orders order = new Orders(lblOrderId.getText(), Date.valueOf(LocalDate.now()), Double.parseDouble(lblTotal.getText()), customer);
        boolean placeOrder = true;
        for (ItemDTO itemDTO : itemList) {
            int quantity = orderFormBO.getQuantity(itemDTO.getCode());
            Item item = new Item(itemDTO.getCode(), itemDTO.getDescription(), itemDTO.getPrice(), (quantity - itemDTO.getQtyOnHand()));
            OrderDetail orderDetail = new OrderDetail(order, item, itemDTO.getQtyOnHand());
            placeOrder = orderFormBO.placeOrder(orderDetail);
        }
        if (placeOrder) {
            new Alert(Alert.AlertType.CONFIRMATION,"Order saved successfully",ButtonType.OK).show();
            URL resource = getClass().getResource("../view/OrderForm.fxml");
            Parent load = FXMLLoader.load(resource);
            root.getChildren().clear();
            root.getChildren().add(load);
        }else{
            new Alert(Alert.AlertType.WARNING,"Try again",ButtonType.CLOSE).show();
        }
    }

    private ArrayList<ItemDTO> getAllItems() {
        try {
            allItems = new ArrayList<>();
            int size = colItemCode.getTableView().getItems().size();
            for (int i = 0; i < size; i++) {
                String code = (String) colItemCode.getCellData(i);
                String description = (String) colDescription.getCellData(i);
                int qtyForSell = (int) colQty.getCellData(i);
                double unitPrice = (double) colUnitPrice.getCellData(i);

                ItemDTO itemDTO = new ItemDTO(code, description, unitPrice, qtyForSell);
                allItems.add(itemDTO);
            }
        } catch (Exception e) {

        }
        return allItems;
    }

    public void cancleOrderOnAction(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../view/OrderForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root.getChildren().clear();
        root.getChildren().add(load);
    }
}
