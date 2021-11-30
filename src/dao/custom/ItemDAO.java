package dao.custom;

import dao.SuperDAO;
import entity.Item;

import java.util.ArrayList;

public interface ItemDAO extends SuperDAO<Item,String> {
    String generateItemCode() throws Exception;

    ArrayList<String> getCodes() throws Exception;

    int getItemQty(String code) throws Exception;

    boolean updateQty(int quantity, String code) throws Exception;
}
