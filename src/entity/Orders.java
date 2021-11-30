package entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Orders implements SuperEntity{
    @Id
    private String orderId;
    private Date date;
    private double price;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetails = new HashSet<OrderDetail>();

    public Orders() {
    }

    public Orders(String orderId, Date date, double price, Customer customer) {
        this.orderId = orderId;
        this.date = date;
        this.price = price;
        this.customer = customer;
    }

    public Orders(String orderId, Date date, double price, Customer customer, Set<OrderDetail> orderDetails) {
        this.orderId = orderId;
        this.date = date;
        this.price = price;
        this.customer = customer;
        this.orderDetails = orderDetails;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(Set<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderId='" + orderId + '\'' +
                ", date=" + date +
                ", price=" + price +
                ", customer=" + customer +
                ", orderDetails=" + orderDetails +
                '}';
    }
}
