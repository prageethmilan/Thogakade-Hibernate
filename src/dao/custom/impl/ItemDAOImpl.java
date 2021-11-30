package dao.custom.impl;

import dao.custom.ItemDAO;
import entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import util.FactoryConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean add(Item item) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(item);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String code) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("DELETE FROM Item WHERE code = :itemCode");
        query.setParameter("itemCode", code);
        if (query.executeUpdate() > 0) {
            transaction.commit();
            session.close();
            return true;
        }
        transaction.commit();
        session.close();
        return false;
    }

    @Override
    public boolean update(Item item) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(item);
        try {
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            return false;
        }
    }

    @Override
    public Item search(String code) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Item item = session.get(Item.class, code);
        if (item != null) {
            transaction.commit();
            session.close();
            return item;
        }
        transaction.commit();
        session.close();
        return null;
    }

    @Override
    public ArrayList<Item> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Item");
        List<Item> list = query.list();
        transaction.commit();
        session.close();
        return (ArrayList<Item>) list;
    }

    @Override
    public String generateItemCode() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery sqlQuery = session.createSQLQuery("SELECT code FROM Item ORDER BY code DESC LIMIT 1");
        String code = (String) sqlQuery.uniqueResult();
        transaction.commit();
        session.close();
        if (code != null) {
            int tempId = Integer.
                    parseInt(code.split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "I-00" + tempId;
            } else if (tempId <= 99) {
                return "I-0" + tempId;
            } else {
                return "I-" + tempId;
            }
        } else {
            return "I-001";
        }
    }

    @Override
    public ArrayList<String> getCodes() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("SELECT code FROM Item");
        List<String> list = query.list();
        transaction.commit();
        session.close();
        return (ArrayList<String>) list;
    }

    @Override
    public int getItemQty(String code) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("SELECT qtyOnHand FROM Item WHERE code = :itemCode");
        query.setParameter("itemCode", code);
        int qty = Integer.parseInt(String.valueOf(query.uniqueResult()));
        transaction.commit();
        session.close();
        return qty;
    }

    @Override
    public boolean updateQty(int quantity, String code) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("UPDATE Item SET qtyOnHand = :qty WHERE code = :itemCode");
        query.setParameter("qty", quantity);
        query.setParameter("itemCode", code);
        if (query.executeUpdate() > 0) {
            transaction.commit();
            session.close();
            return true;
        } else {
            transaction.rollback();
            session.close();
            return false;
        }
    }
}
