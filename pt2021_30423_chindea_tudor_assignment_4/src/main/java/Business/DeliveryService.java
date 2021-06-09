package Business;

import jdk.dynalink.Operation;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This class implements the interface IDeliverySServiceProcessing with all the funtions for the menu items processing
 * with modify, delete and create. also has login and register used for users. These methods are the most important
 */
public class DeliveryService implements IDeliveryServiceProcessing, Serializable{
    private ArrayList<MenuItem> allItems = new ArrayList<>();
    private ArrayList<User> allUsers = new ArrayList<>();
    User u1 = new User("Tudor","123",0);
    User u4 = new User("Alex","321",0);
    User u2 = new User("Mircea","234",1);
    User u3 = new User("Matei", "345",2);
    HashMap<Order,ArrayList<MenuItem>> orders = new HashMap<>();
    public static <T> Predicate<T> distinctByKey( Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    @Override
    public void importItems() {
        try {
            allUsers.add(u1);
            allUsers.add(u2);
            allUsers.add(u3);
            allUsers.add(u4);
            BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\tudor\\OneDrive\\Desktop\\PT\\pt2021_30423_chindea_tudor_assignment_4\\products.csv"));
            String entry = bufferedReader.readLine();
            while((entry = bufferedReader.readLine()) != null){
                String[] data = entry.split(",");
                MenuItem menuItem = new MenuItem(data[0], Double.parseDouble(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]),Integer.parseInt(data[5]), Integer.parseInt(data[6]));
                allItems.add(menuItem);
            }
            allItems = (ArrayList<MenuItem>) allItems.stream()
                    .filter(distinctByKey(p -> p.getTitle()))
                    .collect(Collectors.toList());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void manageProducts( MenuItem prodVechi, MenuItem prodNou) {

        allItems.remove(prodVechi);
        allItems.add(prodNou);
    }

    @Override
    public void generateReports() {

    }
    public Order createOrder(String clientName, ArrayList<MenuItem>items){
        Order order=new Order(orders.size()+1,clientName);
        System.out.println(order.getDate());
        orders.put(order,items);
        System.out.println("Order size "+orders.size());
        return order;
    }

    @Override
    public void deleteProducts(MenuItem oldItem){
        allItems.remove(oldItem);
    }

    public HashMap<Order, ArrayList<MenuItem>> getOrders() {
        return orders;
    }

    public void setOrders(HashMap<Order, ArrayList<MenuItem>> orders) {
        this.orders = orders;
    }

    public ArrayList<MenuItem> getAllItems() {
        return allItems;
    }

    public void setAllItems(ArrayList<MenuItem> allItems) {
        this.allItems = allItems;
    }

    public ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(ArrayList<User> allUsers) {
        this.allUsers = allUsers;
    }
}
