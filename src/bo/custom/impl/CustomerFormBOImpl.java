package bo.custom.impl;

import bo.custom.CustomerFormBO;
import dao.custom.CustomerDAO;
import dao.DAOFactory;
import dto.CustomerDTO;
import entity.Customer;
import view.tm.CustomerTM;

import java.util.ArrayList;

public class CustomerFormBOImpl implements CustomerFormBO {
    CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public String generateId() throws Exception {
        return customerDAO.generateNewId();
    }

    @Override
    public boolean addCustomer(CustomerDTO dto) throws Exception {
        Customer customer = new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getContact());
        return customerDAO.add(customer);
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws Exception {
        Customer customer = new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getContact());
        return customerDAO.update(customer);
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws Exception {
        ArrayList<Customer> all = customerDAO.getAll();
        ArrayList<CustomerDTO> customers=new ArrayList<>();
        for (Customer customer : all) {
            customers.add(new CustomerDTO(customer.getId(),customer.getName(),customer.getAddress(),customer.getContact()));
        }
        return customers;
    }

    @Override
    public boolean deleteCustomer(CustomerTM customerTM) throws Exception {
        return customerDAO.delete(customerTM.getId());
    }
}
