package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;
import dto.ItemDTO;
import entity.OrderDetail;

import java.util.ArrayList;

public interface PlaceOrderFormBO extends SuperBO {
    String generateId() throws Exception;

    ArrayList<String> getCustomerIds() throws Exception;

    ArrayList<String> getItemCodes() throws Exception;

    CustomerDTO getCustomer(String id) throws Exception;

    ItemDTO getItems(String code) throws Exception;

    boolean placeOrder(OrderDetail orderDetail) throws Exception;

    boolean updateItem(ItemDTO dto1) throws Exception;

    int getQuantity(String code) throws Exception;

    boolean updateQuantity(int quantity, String code) throws Exception;
}
