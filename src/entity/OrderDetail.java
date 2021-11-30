package entity;

import javax.persistence.*;

@Entity
public class OrderDetail implements SuperEntity{

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Order_Id")
    private Orders order;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Item_Code")
    private Item item;
    private int orderQty;
    @Id
    @GeneratedValue
    private Integer id;

    public OrderDetail() {
    }

    public OrderDetail( Orders order, Item item, int orderQty) {
        this.order = order;
        this.item = item;
        this.orderQty = orderQty;
    }


    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                ", order=" + order +
                ", item=" + item +
                ", orderQty=" + orderQty +
                '}';
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
