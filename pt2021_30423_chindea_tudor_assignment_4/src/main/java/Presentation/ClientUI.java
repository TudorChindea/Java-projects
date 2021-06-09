package Presentation;

import Business.DeliveryService;
import Business.MenuItem;
import Business.Order;
import Business.User;
import Data.Serializator;
import javafx.beans.property.ReadOnlyObjectProperty;
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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * class used for the reister and login funtions
 * in this class we have the functions of a client to place order and to filter products by any fields he wants.
 * For each order placed a bill.txt with the clients name is created
 */
public class ClientUI extends UIBasic {
    public Button signupbtn;
    public TextField nametxt;
    public TextField passtxt;
    public Button loginbtn;
    public TextField warningtxt;
    public Button importBtn;
    public TextField usertxt;
    public TextField passwordtxt;
    public Button backbtn;
    public TableColumn<MenuItem, String > titleclm;
    public TableColumn<MenuItem, Double> ratingclm;
    public TableColumn<MenuItem, Integer> proteinclm;
    public TableColumn<MenuItem, Integer> caloriesclm;
    public TableColumn<MenuItem, Integer> fatclm;
    public TableColumn<MenuItem, Integer> sodiumclm;
    public TableColumn<MenuItem, Integer> priceclm;
    public Button backbtn2;
    public Button printBtn;
    public TableView<MenuItem> menuTa;
    public TextField titletxt;
    public TextField ratingtxt;
    public TextField proteintxt;
    public TextField caloriestxt;
    public TextField fattxt;
    public TextField sodiumtxt;
    public TextField pricetxt;
    public Button filterbtn;
    public Button selectItembtn;
    public Button placeorderBtn;

    //private DeliveryService allData = new DeliveryService();
    //private Serializator serializ = new Serializator();
    ArrayList<MenuItem> itemsOrdered = new ArrayList<>();
    private static String name = new String();

    public void login(ActionEvent actionEvent)  throws IOException {
        super.allData = serializ.deserializeaza();

        this.name = nametxt.getText();

        String password=passtxt.getText();

        User user = new User("a","a",3);
        int clientExists=0;
        for(User usr: super.allData.getAllUsers())
        {
            if(usr.getName().equals(this.name) && usr.getPassword().equals(password)) {
                user=usr;

                clientExists =1;

            }
        }
        if(clientExists == 0){
            warningtxt.setText("Not a user");
        }
        else{
            if(user.getClientType() == 0){
                Stage stage = (Stage) loginbtn.getScene().getWindow();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Client.fxml")));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                stage.setOnCloseRequest(event->serializ.serializeaza(super.allData));
            }
            else if(user.getClientType() == 2){
                Stage stage = (Stage) loginbtn.getScene().getWindow();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Administrator.fxml")));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                stage.setOnCloseRequest(event->serializ.serializeaza(super.allData));
            }
        }
    }

    public void importStart(ActionEvent actionEvent) {
        super.allData.importItems();
        serializ.serializeaza(super.allData);
    }

    public void signup(ActionEvent actionEvent) {
        System.out.println(usertxt.getText()+" "+passwordtxt.getText());
        User u = new User(usertxt.getText(),passwordtxt.getText(),0);
        super.allData.getAllUsers().add(u);
        serializ.serializeaza(super.allData);
    }

    public void changeScene(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) backbtn.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Login.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event->serializ.serializeaza(super.allData));
    }

    public void changetoRegister(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) signupbtn.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Register.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event->serializ.serializeaza(super.allData));
    }
    public void printItem() {
        List<MenuItem> meniu = super.allData.getAllItems();
        titleclm.setCellValueFactory(new PropertyValueFactory<>("title"));
        ratingclm.setCellValueFactory(new PropertyValueFactory<>("rating"));
        proteinclm.setCellValueFactory(new PropertyValueFactory<>("protein"));
        caloriesclm.setCellValueFactory(new PropertyValueFactory<>("calories"));
        fatclm.setCellValueFactory(new PropertyValueFactory<>("fat"));
        sodiumclm.setCellValueFactory(new PropertyValueFactory<>("sodium"));
        priceclm.setCellValueFactory(new PropertyValueFactory<>("price"));
        menuTa.getItems().clear();
        ObservableList<MenuItem> printItems= FXCollections.observableArrayList();
        printItems.addAll(meniu);
        menuTa.getItems().addAll(printItems);
    }
    public void backLogin(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) backbtn2.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Login.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event->serializ.serializeaza(super.allData));
    }
    public void filterby(){
        List<MenuItem> meniu ;
        List<MenuItem> secondaryMenu ;
        if(!titletxt.getText().equals("")){
            meniu = super.allData.getAllItems().stream().filter(o -> o.getTitle().equals(titletxt.getText())).collect(Collectors.toList());
        }
        else{
            meniu = super.allData.getAllItems();
        }
        if(!ratingtxt.getText().equals("")){
            secondaryMenu = meniu.stream().filter(o -> o.getRating() == Double.parseDouble(ratingtxt.getText())).collect(Collectors.toList());
        }
        else{
            secondaryMenu = meniu;
        }
        if(!proteintxt.getText().equals("")){
            meniu = secondaryMenu.stream().filter((o -> o.getProtein() == Integer.parseInt(proteintxt.getText()))).collect(Collectors.toList());
        }
        else{
            meniu = secondaryMenu;
        }
        if(!caloriestxt.getText().equals("")){
            secondaryMenu = meniu.stream().filter(o -> o.getCalories() == Integer.parseInt(caloriestxt.getText())).collect(Collectors.toList());
        }
        else{
            secondaryMenu = meniu;
        }
        if(!fattxt.getText().equals("")){
            meniu = secondaryMenu.stream().filter((o -> o.getFat() == Integer.parseInt(fattxt.getText()))).collect(Collectors.toList());
        }
        else{
            meniu = secondaryMenu;
        }
        if(!sodiumtxt.getText().equals("")){
            secondaryMenu = meniu.stream().filter(o -> o.getSodium() == Integer.parseInt(sodiumtxt.getText())).collect(Collectors.toList());
        }
        else{
            secondaryMenu = meniu;
        }
        if(!pricetxt.getText().equals("")){
            meniu = secondaryMenu.stream().filter((o -> o.getPrice() == Integer.parseInt(pricetxt.getText()))).collect(Collectors.toList());
        }
        else{
            meniu = secondaryMenu;
        }
        titleclm.setCellValueFactory(new PropertyValueFactory<>("title"));
            ratingclm.setCellValueFactory(new PropertyValueFactory<>("rating"));
            proteinclm.setCellValueFactory(new PropertyValueFactory<>("protein"));
            caloriesclm.setCellValueFactory(new PropertyValueFactory<>("calories"));
            fatclm.setCellValueFactory(new PropertyValueFactory<>("fat"));
            sodiumclm.setCellValueFactory(new PropertyValueFactory<>("sodium"));
            priceclm.setCellValueFactory(new PropertyValueFactory<>("price"));
            menuTa.getItems().clear();
            ObservableList<MenuItem> printItems= FXCollections.observableArrayList();
            printItems.addAll(meniu);
            menuTa.getItems().addAll(printItems);
    }


    public void selectItem(ActionEvent actionEvent) {
        ReadOnlyObjectProperty<MenuItem> clickedItem =  menuTa.getSelectionModel().selectedItemProperty();
        itemsOrdered.add(clickedItem.get());
    }

    public void placeOrder(ActionEvent actionEvent) throws FileNotFoundException {
        System.out.println(this.name);
        Order order = super.allData.createOrder(this.name, itemsOrdered);
        StringBuilder str = new StringBuilder();
        str.append(name + super.allData.getOrders().size()+".txt");
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(str.toString()), StandardCharsets.UTF_8))) {
            writer.append("\n    Bill for client "+ name +"\n\n    ");
            writer.append("Date: " + LocalDateTime.now()+ "\n   Products\n   ");
            int totalPrice=0;
            for(MenuItem item: itemsOrdered){
                writer.append(item.getTitle()+"  Price:  "+ item.getPrice()+"\n   ");
                totalPrice += item.getPrice();
            }
            writer.append("Total price:  " + totalPrice);
            order.setTtotalPrice(totalPrice);
        } catch (IOException e) {
            e.printStackTrace();
        }
        serializ.serializeaza(super.allData);
        super.allData = serializ.deserializeaza();
    }
}
