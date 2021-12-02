package bo.custom.impl;

import bo.custom.OrderListFormBO;
import dao.DAOFactory;
import dao.custom.QueryDAO;

import java.util.ArrayList;

public class OrderListFormBOImpl implements OrderListFormBO {
    QueryDAO queryDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.QUERY);

    @Override
    public ArrayList<Object[]> getAllOrders() {
        ArrayList<Object[]> list = queryDAO.getOrdersList();
        return list;
    }
}
