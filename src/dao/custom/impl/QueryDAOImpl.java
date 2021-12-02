package dao.custom.impl;

import dao.custom.QueryDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.FactoryConfiguration;

import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public ArrayList<Object[]> getOrdersList() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<Object[]> list = session.createQuery("SELECT c.id,c.name,c.address,c.contact,o.orderId,o.date,o.price FROM Customer c INNER JOIN Orders o ON c.id=o.customer").list();
        if (list != null) {
            transaction.commit();
            session.close();
            return (ArrayList<Object[]>) list;
        }
        transaction.commit();
        session.close();
        return null;
    }

    @Override
    public boolean add(Object o) throws Exception {
        return false;
    }

    @Override
    public boolean delete(Object o) throws Exception {
        return false;
    }

    @Override
    public boolean update(Object o) throws Exception {
        return false;
    }

    @Override
    public Object search(Object o) throws Exception {
        return null;
    }

    @Override
    public ArrayList getAll() throws Exception {
        return null;
    }
}
