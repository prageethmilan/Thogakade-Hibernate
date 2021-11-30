package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;
import view.tm.ItemTM;

import java.util.ArrayList;

public interface ItemFormBO extends SuperBO {

    String generateCode() throws Exception;

    boolean addItem(ItemDTO itemDTO) throws Exception;

    boolean updateItem(ItemDTO itemDTO) throws Exception;

    ArrayList<ItemDTO> getAllItems() throws Exception;

    boolean deleteItem(ItemTM selectedItem) throws Exception;
}
