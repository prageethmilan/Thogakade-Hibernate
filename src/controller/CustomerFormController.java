package controller;

import bo.BOFactory;
import bo.custom.CustomerFormBO;
import dto.CustomerDTO;
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
import view.tm.CustomerTM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;

public class CustomerFormController {
    public TextField txtName;
    public TextField txtAddress;
    public Label lblId;
    public TextField txtContactNo;
    public Button btnSave;
    public TableView<CustomerTM> tblCustomers;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public AnchorPane root;
    CustomerFormBO customerBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize() {
        btnSave.setText("Save");
        try {
            loadCustomersToTable(customerBO.getAllCustomers());
            generateCustomerId();
        } catch (Exception e) {

        }
        tblCustomers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lblId.setText(newValue.getId());
                txtName.setText(newValue.getName());
                txtAddress.setText(newValue.getAddress());
                txtContactNo.setText("0"+String.valueOf(newValue.getContact()));
                btnSave.setText("Update");
            }
        });

    }

    private void loadCustomersToTable(ArrayList<CustomerDTO> allCustomers) {
        ObservableList<CustomerTM> obList = FXCollections.observableArrayList();
        allCustomers.forEach(e -> {
            obList.add(new CustomerTM(e.getId(), e.getName(), e.getAddress(), e.getContact()));
        });
        tblCustomers.setItems(obList);
        initCols();
    }

    private void initCols() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    private void generateCustomerId() throws Exception {
        lblId.setText(customerBO.generateId());
    }

    public void newCustomerOnAction(MouseEvent mouseEvent) {
        try {
            loadCustomersToTable(customerBO.getAllCustomers());
            generateCustomerId();
            txtName.clear();
            txtAddress.clear();
            txtContactNo.clear();
            btnSave.setText("Save");
        } catch (Exception e) {

        }
    }

    public void btnSaveAndUpdateOnAction(MouseEvent mouseEvent) throws Exception {
        if (!txtName.getText().matches("[A-Za-z. ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Name", ButtonType.CLOSE).showAndWait();
            txtName.requestFocus();
            return;
        } else if (!txtAddress.getText().matches("[A-Za-z,0-9]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Address", ButtonType.CLOSE).showAndWait();
            txtAddress.requestFocus();
            return;
        } else if (!txtContactNo.getText().matches("[0-9]{10}")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Contact No", ButtonType.CLOSE).showAndWait();
            txtContactNo.requestFocus();
            return;
        }
        CustomerDTO customerDTO = new CustomerDTO(lblId.getText(), txtName.getText(), txtAddress.getText(), Integer.parseInt(txtContactNo.getText()));

        if (btnSave.getText().equalsIgnoreCase("Save")) {
            boolean added = customerBO.addCustomer(customerDTO);
            if (added) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Saved Successfully", ButtonType.OK).show();
                generateCustomerId();
                txtName.clear();
                txtAddress.clear();
                txtContactNo.clear();
                loadCustomersToTable(customerBO.getAllCustomers());
            } else {
                new Alert(Alert.AlertType.WARNING, "Try again", ButtonType.CLOSE).show();
            }
        } else if (btnSave.getText().equalsIgnoreCase("Update")) {
            boolean update = customerBO.updateCustomer(customerDTO);
            if (update) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer updated successfully", ButtonType.OK).show();
                generateCustomerId();
                txtName.clear();
                txtAddress.clear();
                txtContactNo.clear();
                btnSave.setText("Save");
                loadCustomersToTable(customerBO.getAllCustomers());
            } else {
                new Alert(Alert.AlertType.WARNING, "Try again", ButtonType.CLOSE).show();
            }
        }
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

    public void deleteCustomerOnAction(ActionEvent actionEvent) {
        CustomerTM selectedItem = tblCustomers.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure whether you want to delete this customer?", yes, no);
            alert.setTitle("Confirmation Alert");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(no) == yes) {
                try {
                    boolean deleted = customerBO.deleteCustomer(selectedItem);
                    if (deleted) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Customer deleted successfully", ButtonType.OK).show();
                        generateCustomerId();
                        txtName.clear();
                        txtAddress.clear();
                        txtContactNo.clear();
                        btnSave.setText("Save");
                        loadCustomersToTable(customerBO.getAllCustomers());
                    }
                } catch (Exception e) {

                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a customer", ButtonType.CLOSE).show();
        }
    }
}
