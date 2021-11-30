package dao.custom;

import dao.SuperDAO;
import entity.Customer;

import java.util.ArrayList;

public interface CustomerDAO extends SuperDAO<Customer,String> {

    String generateNewId() throws Exception;

    ArrayList<String> getIds() throws Exception;
}
