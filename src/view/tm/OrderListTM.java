package view.tm;

public class OrderListTM {
    private String orderId;
    private String date;
    private double price;
    private String customerId;
    private String name;
    private String address;
    private int contact;

    public OrderListTM() {
    }

    public OrderListTM(String orderId, String date, double price, String customerId, String name, String address, int contact) {
        this.orderId = orderId;
        this.date = date;
        this.price = price;
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.contact = contact;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "OrderListTM{" +
                "orderId='" + orderId + '\'' +
                ", date='" + date + '\'' +
                ", price=" + price +
                ", customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact=" + contact +
                '}';
    }
}
