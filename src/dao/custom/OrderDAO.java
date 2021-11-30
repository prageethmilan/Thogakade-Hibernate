package dao.custom;

import dao.SuperDAO;
import entity.Orders;

public interface OrderDAO extends SuperDAO<Orders, String> {
    String generateOrderId() throws Exception;
}
