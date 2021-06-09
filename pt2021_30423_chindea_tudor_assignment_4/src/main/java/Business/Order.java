package Business;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * all orders of this type will be stored in a hashmap
 */
public class Order implements Serializable {
    String clientName;
    int id,totalPrice;
    LocalDateTime date;
    public Order(int id, String clientName){
        this.clientName = clientName;
        this.id = id;
        this.date = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && totalPrice == order.totalPrice && Objects.equals(clientName, order.clientName) && Objects.equals(date, order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientName, id, totalPrice, date);
    }

    public String getClientName() {
        return clientName;
    }
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTtotalPrice(int tatlPrice) {
        this.totalPrice = tatlPrice;
    }
}
