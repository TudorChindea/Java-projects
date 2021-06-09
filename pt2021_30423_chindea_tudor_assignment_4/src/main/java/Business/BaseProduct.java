package Business;

import java.io.Serializable;

public class BaseProduct extends MenuItem implements Serializable {

    public BaseProduct(String title, double rating,int protein, int calories, int fat, int sodium, int price) {
        super(title, rating,protein, calories, fat, sodium, price);
    }
}
