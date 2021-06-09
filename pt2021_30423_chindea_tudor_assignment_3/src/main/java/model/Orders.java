package model;

/**
 * Orders class with the same fields as the Orders table and with two constructors, getters and setters
 */
public class Orders {
    private int id;
    private int clientid;
    private int productid;
    private int quantityProduct;
    public Orders(){

    }
    public Orders(int clientid, int productid, int quantityProduct){
        this.clientid=clientid;
        this.productid=productid;
        this.quantityProduct=quantityProduct;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientid() {
        return clientid;
    }

    public void setClientid(int clientid) {
        this.clientid = clientid;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public int getQuantityProduct() {
        return quantityProduct;
    }

    public void setQuantityProduct(int quantityProduct) {
        this.quantityProduct = quantityProduct;
    }
}
