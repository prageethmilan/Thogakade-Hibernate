package bo;

import bo.custom.impl.CustomerFormBOImpl;
import bo.custom.impl.ItemFormBOImpl;
import bo.custom.impl.OrderListFormBOImpl;
import bo.custom.impl.PlaceOrderFormBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){

    }
    public static BOFactory getBoFactory(){
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }
    public <T extends SuperBO> T getBO(BOTypes daoType){
        switch (daoType){
            case CUSTOMER:
                return (T) new CustomerFormBOImpl();
            case ITEM:
                return (T) new ItemFormBOImpl();
            case ORDER:
                return (T) new PlaceOrderFormBOImpl();
            case ORDERLIST:
                return (T) new OrderListFormBOImpl();
            default:
                return null;
        }

    }
    public enum BOTypes{
        CUSTOMER,ITEM,ORDER,ORDERLIST
    }
}
