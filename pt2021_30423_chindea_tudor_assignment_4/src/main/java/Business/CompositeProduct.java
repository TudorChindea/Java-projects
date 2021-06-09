package Business;

import java.io.Serializable;

public class CompositeProduct extends MenuItem implements Serializable {
    public CompositeProduct(String title, double rating,int protein, int calories, int fat, int sodium, int price) {
        super(title, rating,protein, calories, fat, sodium, price);
    }
}
