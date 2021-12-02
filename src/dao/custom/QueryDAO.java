package dao.custom;

import dao.SuperDAO;

import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {

    ArrayList<Object[]> getOrdersList();

}
