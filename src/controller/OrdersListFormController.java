package controller;

import bo.BOFactory;
import bo.custom.OrderListFormBO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.tm.OrderListTM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class OrdersListFormController {
    public AnchorPane root;
    public TableView<OrderListTM> tblOrderList;
    public TableColumn colOrderId;
    public TableColumn colDate;
    public TableColumn colPrice;
    public TableColumn colCustomerId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    OrderListFormBO orderListFormBO = BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDERLIST);

    public void initialize() {
        loadOrdersToTable();
    }

    private void loadOrdersToTable() {
        ArrayList<Object[]> list = orderListFormBO.getAllOrders();
        ObservableList<OrderListTM> tblList = FXCollections.observableArrayList();
        for (Object[] obj : list) {
            Double price = new Double(obj[6].toString());
            Integer contact = new Integer(obj[3].toString());
            tblList.add(new OrderListTM(String.valueOf(obj[4]),String.valueOf(obj[5]),price,String.valueOf(obj[0]),String.valueOf(obj[1]),String.valueOf(obj[2]),contact));
        }
        tblOrderList.setItems(tblList);
        initCols();
    }

    private void initCols() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
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
}
