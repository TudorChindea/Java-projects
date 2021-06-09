package bll;
import java.util.List;
import java.util.NoSuchElementException;


import dao.OrderDAO;
import model.Orders;

/**
 * this class uses the OrderDAO specific methods to perform sql queries to modify the table Order
 * we have method for each sql query in the AbstractDAO class
 */
public class OrderBLL {
    private OrderDAO orderDAO;

    /**
     * the constructor initializez the orderDAO element
     */
    public OrderBLL() {
        orderDAO = new OrderDAO();
    }

    /**
     * Method that finds the Order with the id given as parameter and returns it
     * if no object is found, an exception will pe thrown
     * @param id
     * @return
     */
    public Orders findOrderById(int id) {
        Orders od = orderDAO.findById(id);
        if (od == null) {
            throw new NoSuchElementException("Order with id =" + id + " was not found!");
        }
        return od;
    }

    /**
     * method that returns all all orders given by orderDAO. If there are no orders in the table, an exception will be thrown
     * @return
     */
    public List<Orders> findAllOrders(){
        List<Orders> od = orderDAO.findAll();
        if (od == null) {
            throw new NoSuchElementException("there are no Orders");
        }
        return od;
    }

    /**
     * inserts an order given as parameter the in order table
     * @param od
     */
    public void insertProduct(Orders od){
        orderDAO.insert(od);
    }

    /**
     * delets the order with the id given as parameter
     * @param id
     */
    public void deleteOrder(int id){
        orderDAO.deleteElementID(id);
    }

    /**
     * updates the order with the id given as parameter by giving it the value of the Order given as parameter
     * @param od
     * @param id
     */
    public void updateOrder(Orders od, int id){
        orderDAO.update(od, id);
    }
}

