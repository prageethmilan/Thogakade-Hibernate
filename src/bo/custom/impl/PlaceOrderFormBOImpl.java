package bo.custom.impl;

import bo.custom.PlaceOrderFormBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailDAO;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import entity.Customer;
import entity.Item;
import entity.OrderDetail;
import entity.Orders;

import java.util.ArrayList;
import java.util.List;

public class PlaceOrderFormBOImpl implements PlaceOrderFormBO {
    OrderDAO orderDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ORDER);
    CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDetailDAO orderDetailDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ORDERDETAIL);

    @Override
    public String generateId() throws Exception {
        return orderDAO.generateOrderId();
    }

    @Override
    public ArrayList<String> getCustomerIds() throws Exception {
        return customerDAO.getIds();
    }

    @Override
    public ArrayList<String> getItemCodes() throws Exception {
        return itemDAO.getCodes();
    }

    @Override
    public CustomerDTO getCustomer(String id) throws Exception {
        Customer customer = customerDAO.search(id);
        CustomerDTO customerDTO = new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress(), customer.getContact());
        return customerDTO;
    }

    @Override
    public ItemDTO getItems(String code) throws Exception {
        Item item = itemDAO.search(code);
        ItemDTO itemDTO = new ItemDTO(item.getCode(), item.getDescription(), item.getPrice(), item.getQtyOnHand());
        return itemDTO;
    }

    @Override
    public boolean placeOrder(OrderDetail orderDetail) throws Exception {
        return orderDetailDAO.add(orderDetail);
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws Exception {
        List<OrderDTO> orders = dto.getOrders();
        ArrayList<Orders> list = new ArrayList<>();
        for (OrderDTO orderDTO : orders) {
            CustomerDTO customer = orderDTO.getCustomer();
            Customer c = new Customer(customer.getId(), customer.getName(), customer.getAddress(), customer.getContact());

            list.add(new Orders(orderDTO.getOrderId(), orderDTO.getDate(), orderDTO.getPrice(), c));
        }
/*
        return itemDAO.update(new Item(dto.getCode(),dto.getDescription(),dto.getPrice(),dto.getQtyOnHand(),list));
*/
        return true;
    }

    @Override
    public int getQuantity(String code) throws Exception {
        return itemDAO.getItemQty(code);
    }

    @Override
    public boolean updateQuantity(int quantity, String code) throws Exception {
        return itemDAO.updateQty(quantity, code);
    }
}
