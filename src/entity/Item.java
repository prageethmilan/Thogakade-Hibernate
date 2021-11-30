package entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Item implements SuperEntity{
    @Id
    private String code;
    private String description;
    private double price;
    private int qtyOnHand;
    @OneToMany(mappedBy = "item")
    private Set<OrderDetail> orderDetails = new HashSet<OrderDetail>();

    public Item() {
    }

    public Item(String code, String description, double price, int qtyOnHand) {
        this.code = code;
        this.description = description;
        this.price = price;
        this.qtyOnHand = qtyOnHand;
    }

    public Item(String code, String description, double price, int qtyOnHand, Set<OrderDetail> orderDetails) {
        this.code = code;
        this.description = description;
        this.price = price;
        this.qtyOnHand = qtyOnHand;
        this.orderDetails = orderDetails;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public Set<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(Set<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "Item{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", qtyOnHand=" + qtyOnHand +
                ", orderDetails=" + orderDetails +
                '}';
    }
}
