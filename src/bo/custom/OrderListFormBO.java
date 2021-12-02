package bo.custom;

import bo.SuperBO;

import java.util.ArrayList;

public interface OrderListFormBO extends SuperBO {
    ArrayList<Object[]> getAllOrders();
}
