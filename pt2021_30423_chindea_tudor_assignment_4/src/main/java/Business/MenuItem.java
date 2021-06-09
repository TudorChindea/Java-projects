package Business;

import javafx.scene.control.ChoiceDialog;

import java.io.Serializable;

/**
 * class that implements serializable as all items in the menu are of this type. The data is stored in object of this type
 */
public class MenuItem implements Serializable {
    private String title;
    private double rating;
    private int calories,protein, fat, sodium, price;
    public MenuItem(String title, double rating,int protein, int calories, int fat, int sodium, int price){
        this.title = title;
        this.rating=rating;
        this.protein=protein;
        this.calories=calories;
        this.fat=fat;
        this.sodium=sodium;
        this.price=price;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

}
