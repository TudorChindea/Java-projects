package Presentation;

import Business.MenuItem;
import Business.Order;
import Business.User;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * class that implemets all admin funtions
 * The admin can update, modify and delete items in the menu
 * the reports are generated and .txt files are made or overwriden
 */
public class AdministratorUI extends UIBasic{
    public TableColumn<MenuItem, String > titleclm;
    public TableColumn<MenuItem, Double> ratingclm;
    public TableColumn<MenuItem, Integer> proteinclm;
    public TableColumn<MenuItem, Integer> caloriesclm;
    public TableColumn<MenuItem, Integer> fatclm;
    public TableColumn<MenuItem, Integer> sodiumclm;
    public TableColumn<MenuItem, Integer> priceclm;
    public Button backbtn;
    public Button printbtn;
    public TableView<MenuItem> menuTa;
    public TextField titletxt;
    public Button storeProdbtn;
    public Button selectProdbtn;
    public Button deleteProdbtn;
    public Button modifybtn;
    public TextField titlemtxt;
    public TextField ratingtxt;
    public TextField proteintxt;
    public TextField caloriestxt;
    public TextField fattxt;
    public TextField pricetxt;
    public TextField sodiumtxt;
    public Button gotoReports;
    public Button report1btn;
    public Button report2btn;
    public Button report3btn;
    public Button report4btn;
    public Button backRepbtn;
    public TextField startHourtxt;
    public TextField endHourtxt;
    public TextField repo2txt;
    public TextField repo4txt;
    public TextField repo3txt;
    public TextField repo3txt1;
    private int price = 0, sodium = 0, fat = 0, calories = 0, protein = 0;
    private String newName;
    private double rating = 0;
    //ArrayList<User> admins= new ArrayList();
    public void changeSceneLogin() throws IOException {
        Stage stage = (Stage) backbtn.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Login.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event->serializ.serializeaza(super.allData));
    }

    public void printItems() {
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

    public void selectProd() {
        ReadOnlyObjectProperty<MenuItem> clickedItem =  menuTa.getSelectionModel().selectedItemProperty();
        price = price + clickedItem.get().getPrice();
        sodium += clickedItem.get().getSodium();
        fat += clickedItem.get().getFat();
        calories += clickedItem.get().getCalories();
        protein += clickedItem.get().getProtein();
    }

    public void storeProd() {
        MenuItem newItem = new MenuItem(titletxt.getText(),0,protein, calories,fat, sodium, price);
        super.allData.getAllItems().add(newItem);
        printItems();
        super.serializ.serializeaza(super.allData);
        super.serializ.deserializeaza();
        price = 0;
        sodium = 0;
        fat = 0;
        calories = 0;
        protein = 0;
    }

    public void deleteProd() {
        ReadOnlyObjectProperty<MenuItem> clickedItem =  menuTa.getSelectionModel().selectedItemProperty();
        super.allData.deleteProducts(clickedItem.get());
        super.serializ.serializeaza(super.allData);
        super.serializ.deserializeaza();
        printItems();
    }

    public void modifyProduct() {
        ReadOnlyObjectProperty<MenuItem> clickedItem =  menuTa.getSelectionModel().selectedItemProperty();
        if(!pricetxt.getText().equals(""))
            price=Integer.parseInt(pricetxt.getText());
        else
            price=clickedItem.get().getPrice();
        if(!titlemtxt.getText().equals(""))
            newName = titlemtxt.getText();
        else
            newName = clickedItem.get().getTitle();
        if(!ratingtxt.getText().equals(""))
            rating = Double.parseDouble(ratingtxt.getText());
        else
            rating= clickedItem.get().getRating();
        if(!caloriestxt.getText().equals(""))
            calories = Integer.parseInt(caloriestxt.getText());
        else
            calories = clickedItem.get().getCalories();
        if(!fattxt.getText().equals(""))
            fat = Integer.parseInt(fattxt.getText());
        else
            fat = clickedItem.get().getFat();
        if(!sodiumtxt.getText().equals(""))
            sodium = Integer.parseInt(sodiumtxt.getText());
        else
            sodium = clickedItem.get().getSodium();
        if(!proteintxt.getText().equals(""))
            protein = Integer.parseInt(proteintxt.getText());
        else
            protein = clickedItem.get().getProtein();
        MenuItem modifiedItem = new MenuItem(newName,rating,protein,calories,fat,sodium,price);
        price = 0;
        sodium = 0;
        fat = 0;
        calories = 0;
        protein = 0;
        super.allData.manageProducts(clickedItem.get(),modifiedItem);
//       // super.serializ.serializeaza(super.allData);
//       // super.serializ.deserializeaza();
        printItems();
    }

    public void changeReport(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) gotoReports.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Reports.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event->serializ.serializeaza(super.allData));
    }

    public void createRep1(ActionEvent actionEvent) {
        int startHour = Integer.parseInt(startHourtxt.getText());
        int endHour = Integer.parseInt(endHourtxt.getText());
        List<Map.Entry<Order, ArrayList<MenuItem>>> orders = super.allData.getOrders().entrySet().stream().collect(Collectors.toList());

        List<Map.Entry<Order, ArrayList<MenuItem>>> matchingOrders = orders.stream().filter(o ->o.getKey().getDate().getHour()>startHour && o.getKey().getDate().getHour()<endHour).collect(Collectors.toList());
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("report1.txt"), StandardCharsets.UTF_8))) {
            writer.append("\n   Start hour:" + startHour+ "\n   End hour:"+ endHour);
            for(int i=0; i< matchingOrders.size();i++)
            {
                writer.append("\n\n   Client name: "+matchingOrders.get(i).getKey().getClientName() +"\n\n  ");
                writer.append("Order hour:" + matchingOrders.get(i).getKey().getDate().getHour()+"\n  ");
                writer.append("Order id: "+ matchingOrders.get(i).getKey().getId());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createRep2(ActionEvent actionEvent) {
        int timesOrdered = Integer.parseInt(repo2txt.getText());
        int numberApp;
        List<MenuItem> itemsInMenu = new ArrayList<>();

        List<Map.Entry<Order, ArrayList<MenuItem>>> orders = super.allData.getOrders().entrySet().stream().collect(Collectors.toList());
        List<MenuItem> goodItem = new ArrayList<>();
        for(int i=0 ;i< orders.size();i++) {
            goodItem.addAll(orders.get(i).getValue().stream().collect(Collectors.toList()));
        }

        itemsInMenu.addAll(goodItem.stream().filter(o -> Collections.frequency(goodItem,o) >= timesOrdered).collect(Collectors.toList()));

         try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("report2.txt"), StandardCharsets.UTF_8))) {
            writer.append("\n   Items ordered at least "+ timesOrdered+" times ");
            ArrayList<String> printedItems = new ArrayList<>();
            for(MenuItem it: itemsInMenu)
            {
                if(!printedItems.contains(it.getTitle())){
                    printedItems.add(it.getTitle());
                    writer.append("\n "+ it.getTitle()+" Price "+it.getPrice());
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void createRep3(ActionEvent actionEvent) {
        List<Map.Entry<Order, ArrayList<MenuItem>>> orders = super.allData.getOrders().entrySet().stream().collect(Collectors.toList());
        List<String> clientsNames = new ArrayList<>();

        int minPrice = Integer.parseInt(repo3txt1.getText());
        int orderNr = Integer.parseInt(repo3txt.getText());
        List<Map.Entry<Order, ArrayList<MenuItem>>> matchingOrders = orders.stream().filter(o -> o.getKey().getTotalPrice() > minPrice).collect(Collectors.toList());
        for(int i=0;i<matchingOrders.size();i++){
            clientsNames.add(matchingOrders.get(i).getKey().getClientName());
        }
        List<String> goodClients = new ArrayList<>();
        goodClients.addAll(clientsNames.stream().filter(o -> Collections.frequency(clientsNames,o)>= orderNr).collect(Collectors.toList()));

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("report3.txt"), StandardCharsets.UTF_8))) {
            writer.append("\n  Clients with at least "+ orderNr+" orders of minimum value "+minPrice);
            ArrayList<String> printedItems = new ArrayList<>();
            for(String client: goodClients)
            {
                if(!printedItems.contains(client)){
                    printedItems.add(client);
                    writer.append("\n Client: "+client );
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createRep4(ActionEvent actionEvent) {
        List<Map.Entry<Order, ArrayList<MenuItem>>> orders = super.allData.getOrders().entrySet().stream().collect(Collectors.toList());
        int day = Integer.parseInt(repo4txt.getText());
        List<Map.Entry<Order, ArrayList<MenuItem>>> matchingOrders = orders.stream().filter(o -> o.getKey().getDate().getDayOfMonth() == day).collect(Collectors.toList());
        List<MenuItem> produse = new ArrayList<>();
        for(int i=0;i<matchingOrders.size();i++){
            produse.addAll(matchingOrders.get(i).getValue().stream().collect(Collectors.toList()));
        }
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("report4.txt"), StandardCharsets.UTF_8))) {
            writer.append("\n   Items ordered on day "+ day);
            ArrayList<String> printedItems = new ArrayList<>();
            for(MenuItem it: produse)
            {
                int amount = Collections.frequency(produse,it);
                if(!printedItems.contains(it.getTitle()) ){
                    printedItems.add(it.getTitle());
                    writer.append("\n "+ it.getTitle()+" Amount "+amount);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changetoAdmin(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) backRepbtn.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Administrator.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event->serializ.serializeaza(super.allData));
    }
}
