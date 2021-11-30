package bo.custom.impl;

import bo.custom.ItemFormBO;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dto.ItemDTO;
import entity.Item;
import view.tm.ItemTM;

import java.util.ArrayList;

public class ItemFormBOImpl implements ItemFormBO {

    ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public String generateCode() throws Exception {
        return itemDAO.generateItemCode();
    }

    @Override
    public boolean addItem(ItemDTO dto) throws Exception {
        Item item = new Item(dto.getCode(), dto.getDescription(), dto.getPrice(), dto.getQtyOnHand());
        return itemDAO.add(item);
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws Exception {
        Item item = new Item(dto.getCode(),dto.getDescription(),dto.getPrice(),dto.getQtyOnHand());
        return itemDAO.update(item);
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws Exception {
        ArrayList<Item> all = itemDAO.getAll();
        ArrayList<ItemDTO> items = new ArrayList<>();
        for (Item item : all) {
            items.add(new ItemDTO(item.getCode(),item.getDescription(),item.getPrice(),item.getQtyOnHand()));
        }
        return items;
    }

    @Override
    public boolean deleteItem(ItemTM itemTM) throws Exception {
        return itemDAO.delete(itemTM.getCode());
    }

}
