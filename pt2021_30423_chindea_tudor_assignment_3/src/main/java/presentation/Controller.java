package presentation;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Client;
import model.Product;
import model.Orders;


import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class that is used to execute all the ui functions
 * it allows the navigation between 4 windows and the performing of CRUD functins on the classes from the model package
 */
public class Controller {
    public Button client;
    public Button product;
    public Button order;
    public Button back;
    public TableView<Client> clientTa = new TableView<>();
    public TableColumn<Client,Integer> idclm = new TableColumn<>();
    public TableColumn<Client,String> nameclm = new TableColumn<>();
    public TableColumn<Client,String> emailclm = new TableColumn<>();
    public TableColumn<Client,String> addressclm = new TableColumn<>();
    public Button displayBtn;
    public TextField nameInsertCl;
    public TextField emailInsertCl;
    public TextField addrInsertCl;
    public Button uploadinsertclbtnbtn;
    public TextField nameupCl;
    public TextField emailUpCl;
    public TextField addrUpdateCl;
    public Button updateclbtn;
    public TextField idDelCl;
    public Button deleteClbtn;
    public TextField idUpdCl;
    public TableColumn<Orders, Integer> idClmO= new TableColumn<>();
    public TableColumn<Orders, Integer> clientidClmO = new TableColumn<>();
    public TableColumn<Orders, Integer> productidClmO = new TableColumn<>();
    public TableColumn<Orders, Integer> productQO = new TableColumn<>();
    public TableView orderTa = new TableView<>();
    public TextField clientIdO;
    public TextField productIdO;
    public TextField productQ;
    public Button placeOrderbtn;
    public TableView productTa = new TableView<>();
    public TableColumn<Product,Integer > idclmP = new TableColumn<>();
    public TableColumn<Product,String> nameclmP = new TableColumn<>();
    public TableColumn<Product, Integer> priceclmP = new TableColumn<>();
    public TableColumn<Product, Integer> quantityclmP = new TableColumn<>();
    public Button displayProdbtn;
    public Button insertProdBtn;
    public Button updateProdbtn;
    public Button deleteProdbtn;
    public TextField delProid;
    public TextField updateProdid;
    public TextField updateProdQuantity;
    public TextField updateProdPrice;
    public TextField updateProdName;
    public TextField insertProdName;
    public TextField insertProdPrice;
    public TextField insertProdQuantity;
    public Button printOrdersbtn;
    List<Client> clients = new ArrayList<>();
    List<Product> products = new ArrayList<>();
    List<Orders> orders = new ArrayList<>();
    ClientBLL clientBll = new ClientBLL();
    OrderBLL orderBll = new OrderBLL();
    ProductBLL productBll = new ProductBLL();

    /**
     * prints all Clients from database table in a tableview
     */
    public void print(){
        clients = clientBll.findAllClients();
        idclm.setCellValueFactory(new PropertyValueFactory<Client, Integer>("id"));
        nameclm.setCellValueFactory(new PropertyValueFactory<Client, String>("name"));
        emailclm.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
        addressclm.setCellValueFactory(new PropertyValueFactory<Client, String>("address"));
        clientTa.getItems().clear();
        ObservableList<Client> printClients= FXCollections.observableArrayList();
        printClients.addAll(clients);
        clientTa.getItems().addAll(printClients);
    }

    /**
     * prints all Products from database table in a tableview
     */
    public void printProduct() {
        products = productBll.findAllProducts();
        idclmP.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        nameclmP.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        priceclmP.setCellValueFactory(new PropertyValueFactory<Product, Integer>("price"));
        quantityclmP.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));

        ObservableList<Product> printProducts= FXCollections.observableArrayList();
        printProducts.addAll(products);
        productTa.getItems().clear();
        productTa.getItems().addAll(printProducts);
    }

    /**
     * prints all Orders from database table in a tableview
     */
    public void printOrders(){
        orders = orderBll.findAllOrders();
        orderTa.getItems().clear();
        ObservableList<Orders> printOrders= FXCollections.observableArrayList();
        printOrders.addAll(orders);
        orderTa.getItems().addAll(printOrders);
        idClmO.setCellValueFactory(new PropertyValueFactory<Orders, Integer>("id"));
        clientidClmO.setCellValueFactory(new PropertyValueFactory<Orders, Integer>("clientid"));
        productidClmO.setCellValueFactory(new PropertyValueFactory<Orders, Integer>("productid"));
        productQO.setCellValueFactory(new PropertyValueFactory<Orders, Integer>("quantityProduct"));

    }

    /**
     * Back button action to return to initial window
     * @param actionEvent
     * @throws IOException
     */
    public void changeSceneOnButtonActionBack(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("StartPage.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * button action used to open the Client window
     * @param actionEvent
     * @throws IOException
     */
    public void changeSceneOnButtonAction(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) client.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Client.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }

    /**
     * button action used to open the Products window
     * @param actionEvent
     * @throws IOException
     */
    public void changeSceneOnButtonActionProd(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) product.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Product.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     *button action used to open the Orders window
     * @param actionEvent
     * @throws IOException
     */
    public void changeSceneOnButtonActionOrder(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) order.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Order.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    /**
     * function that inserts the client given in the textfields when button is pressed
     * @param actionEvent
     */
    public void insertCl(ActionEvent actionEvent) {
        String name = nameInsertCl.getText();
        String addr=addrInsertCl.getText();
        String email = emailInsertCl.getText();
        Client client1 = new Client(name,email,addr);
        clientBll.insertClient(client1);
        print();
    }

    /**
     * function that inserts the product given in the textfields when button is pressed
     * @param actionEvent
     */
    public void insertProduct(ActionEvent actionEvent) {
        String name = insertProdName.getText();
        int price = Integer.parseInt(insertProdPrice.getText());
        int quantity = Integer.parseInt(insertProdQuantity.getText());
        Product prod1 = new Product(name,price,quantity);
        productBll.insertProdupt(prod1);
        printProduct();
    }

    /**
     * function that updates a client with id given by the user in the textfield the data from the textfields
     * @param actionEvent
     */
    public void updateCl(ActionEvent actionEvent) {
        int updateId = Integer.parseInt(idUpdCl.getText());
        String name = nameupCl.getText();
        String email = emailUpCl.getText();
        String addr = addrUpdateCl.getText();
        Client client = clientBll.findClientById(updateId);
        if(!name.equals(""))
            client.setName(name);
        if(!email.equals(""))
            client.setEmail(email);
        if(!addr.equals(""))
            client.setAddress(addr);
        clientBll.updateClient(client,updateId);
        print();
    }

    /**
     * function that updates a product with id given by the user in the textfield the data from the textfields
     * @param actionEvent
     */
    public void updateProduct(ActionEvent actionEvent) {
        int updateId = Integer.parseInt(updateProdid.getText());
        String name = updateProdName.getText();
        Product prod1 = productBll.findProductById(updateId);
        if(!name.equals(""))
            prod1.setName(name);
        if(!updateProdPrice.getText().equals("")) {
            int price = Integer.parseInt(updateProdPrice.getText());
            prod1.setPrice(price);
        }
        if(!updateProdQuantity.getText().equals("")) {
            int quantity = Integer.parseInt(updateProdQuantity.getText());
            prod1.setQuantity(quantity);
        }
        productBll.updateProduct(prod1,updateId);
        printProduct();
    }

    /**
     * funtion to delete the client with the id given in the textField when the delete button is pressed
     * @param actionEvent
     */
    public void deleteCl(ActionEvent actionEvent) {
        int delId = Integer.parseInt(idDelCl.getText());
        clientBll.deleteClient(delId);
        print();
    }

    /**
     * funtion to delete the product with the id given in the textField when the delete button is pressed
     * @param actionEvent
     */
    public void deleteProduct(ActionEvent actionEvent) {
        int delId = Integer.parseInt(delProid.getText());
        productBll.deleteProduct(delId);
        printProduct();
    }

    /**
     * funtion that inserts a new order in the order table with the data given in the ui in the textfields.
     * A pdf file with the order details is created
     * @param actionEvent
     */
    public void placeOrder(ActionEvent actionEvent) {
        int clientId=Integer.parseInt(clientIdO.getText());
        int productId = Integer.parseInt(productIdO.getText());
        int quantity = Integer.parseInt(productQ.getText());
        Product prod = productBll.findProductById(productId);
        if(prod.getQuantity() < quantity){
            final JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "Not enough items on stock", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        else {
            prod.setQuantity(prod.getQuantity() - quantity);
            productBll.updateProduct(prod,productId);
            Orders order2 = new Orders(clientId, productId, quantity);
            System.out.println(order2.getClientid());
            orderBll.insertProduct(order2);
            printOrders();
            StringBuilder string = new StringBuilder();
            string.append("OrderReport");
            string.append(order2.getClientid());
            string.append(".pdf");
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream(string.toString()));
                document.open();
                document.add(new Paragraph("Reciept:\nClient Data:"));
                //ClientBLL clientBLL = new ClientBLL();
                Client client = clientBll.findClientById(order2.getClientid());
                //ProductBLL productBLL = new ProductBLL();
                Product product = productBll.findProductById(order2.getProductid());
                document.add(new Paragraph(" Name: " + client.getName()));
                document.add(new Paragraph(" Address: " + client.getAddress()));
                document.add(new Paragraph(" Email: " + client.getEmail()));
                document.add(new Paragraph("\nProduct:"));
                document.add(new Paragraph(" Name: " + product.getName()));
                document.add(new Paragraph(" Quantity: " + order2.getQuantityProduct()));
                int price = prod.getPrice() * order2.getQuantityProduct();
                document.add(new Paragraph("Order Price: " + price));
                Desktop.getDesktop().open(new File("C:\\Users\\tudor\\OneDrive\\Desktop\\PT\\pt2021_30423_chindea_tudor_assignment_3\\" + string));
                document.close();
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }
        }
    }







}
