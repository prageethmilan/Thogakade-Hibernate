package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;
import view.tm.CustomerTM;

import java.util.ArrayList;

public interface CustomerFormBO extends SuperBO {
    String generateId() throws Exception;

    boolean addCustomer(CustomerDTO customerDTO) throws Exception;

    boolean updateCustomer(CustomerDTO customerDTO) throws Exception;

    ArrayList<CustomerDTO> getAllCustomers() throws Exception;

    boolean deleteCustomer(CustomerTM selectedItem) throws Exception;
}
