package controller;

import bo.BOFactory;
import bo.custom.ItemFormBO;
import dto.ItemDTO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.tm.ItemTM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;

public class ItemsFormController {

    public Label lblCode;
    public AnchorPane root;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQty;
    public Button btnSave;
    public TableView<ItemTM> tblItems;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    ItemFormBO itemFormBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    public void initialize() {
        btnSave.setText("Save");
        try {
            loadItemsToTable(itemFormBO.getAllItems());
            generateItemCode();
        } catch (Exception e) {
        }
        tblItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lblCode.setText(newValue.getCode());
                txtDescription.setText(newValue.getDescription());
                txtUnitPrice.setText(newValue.getUnitPrice() + "0");
                txtQty.setText(String.valueOf(newValue.getQty()));
                btnSave.setText("Update");
            }
        });
    }

    private void loadItemsToTable(ArrayList<ItemDTO> allItems) {
        ObservableList<ItemTM> obList = FXCollections.observableArrayList();
        allItems.forEach(e -> {
            obList.add(new ItemTM(e.getCode(), e.getDescription(), e.getPrice(), e.getQtyOnHand()));
        });
        tblItems.setItems(obList);
        initcols();
    }

    private void initcols() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    private void generateItemCode() throws Exception {
        lblCode.setText(itemFormBO.generateCode());
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

    public void newItemOnAction(MouseEvent mouseEvent) {
        try {
            loadItemsToTable(itemFormBO.getAllItems());
            generateItemCode();
            txtDescription.clear();
            txtUnitPrice.clear();
            txtUnitPrice.clear();
            btnSave.setText("Save");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnSaveAndUpdateOnAction(MouseEvent mouseEvent) throws Exception {
        if (!txtDescription.getText().matches("^[A-z0-9 &,]{3,}$")) {
            new Alert(Alert.AlertType.ERROR, "Description must be at least 3 characters long").show();
            return;
        } else if (!txtUnitPrice.getText().matches("^[0-9]+[.]?[0-9]{2}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid unit price.(Ex :- 50.00)").show();
            return;
        } else if (!txtQty.getText().matches("^\\d+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Quantity.(Ex :- 100)").show();
            return;
        }
        ItemDTO itemDTO = new ItemDTO(lblCode.getText(), txtDescription.getText(), Double.parseDouble(txtUnitPrice.getText()), Integer.parseInt(txtQty.getText()));

        if (btnSave.getText().equalsIgnoreCase("Save")) {
            boolean added = itemFormBO.addItem(itemDTO);
            if (added) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item saved Successfully", ButtonType.OK).show();
                generateItemCode();
                txtDescription.clear();
                txtUnitPrice.clear();
                txtQty.clear();
                loadItemsToTable(itemFormBO.getAllItems());
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again", ButtonType.CLOSE).show();
            }
        } else if (btnSave.getText().equalsIgnoreCase("Update")) {
            boolean update = itemFormBO.updateItem(itemDTO);
            if (update) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item updated Successfully", ButtonType.OK).show();
                generateItemCode();
                txtDescription.clear();
                txtUnitPrice.clear();
                txtQty.clear();
                loadItemsToTable(itemFormBO.getAllItems());
                btnSave.setText("Save");
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again", ButtonType.CLOSE).show();
            }
        }
    }

    public void deleteItemOnAction(ActionEvent actionEvent) {
        ItemTM selectedItem = tblItems.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure whether you want to delete this customer?", yes, no);
            alert.setTitle("Confirmation Alert");
            Optional<ButtonType> result = alert.showAndWait();
            try {
                if (result.orElse(no) == yes) {
                    boolean delete = itemFormBO.deleteItem(selectedItem);
                    if(delete){
                        new Alert(Alert.AlertType.CONFIRMATION,"Item deleted successfully",ButtonType.CLOSE).show();
                        generateItemCode();
                        txtDescription.clear();
                        txtUnitPrice.clear();
                        txtQty.clear();
                        btnSave.setText("Save");
                        loadItemsToTable(itemFormBO.getAllItems());
                    }
                }
            }catch (Exception e){

            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a item");
        }
    }
}
