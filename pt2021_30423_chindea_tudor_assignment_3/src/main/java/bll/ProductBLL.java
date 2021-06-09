package bll;
import java.util.List;
import java.util.NoSuchElementException;


import bll.validator.Validator;
import dao.ProductDAO;
import model.Product;

/**
 * this class uses the ProductDAO specific methods to perform sql queries to modify the table Product
 *  we have method for each sql query in the AbstractDAO class
 */
public class ProductBLL {
    private ProductDAO productDAO;
    public ProductBLL() {
        productDAO = new ProductDAO();
    }

    /**
     * returns the product given with the the id given in the parameter line
     * throws exception if no product is found
     * @param id
     * @return
     */
    public Product findProductById(int id) {
        Product pt = productDAO.findById(id);
        if (pt == null) {
            throw new NoSuchElementException("Product with id =" + id + " was not found!");
        }
        return pt;
    }

    /**
     * returns a list of products in the table
     * throws exception if the product table is empty
     * @return
     */
    public List<Product> findAllProducts(){
        List<Product> pt = productDAO.findAll();
        if (pt == null) {
            throw new NoSuchElementException("there are no Products");
        }
        return pt;
    }

    /**
     * inserts product pt given as parameter in the product table
     * @param pt
     */
    public void insertProdupt(Product pt){
        productDAO.insert(pt);
    }

    /**
     * deletes the product with the id given as parameter
     * @param id
     */
    public void deleteProduct(int id){
        productDAO.deleteElementID(id);
    }

    /**
     * updates the value of the product with id given as parameter
     * @param pt
     * @param id
     */
    public void updateProduct(Product pt, int id){
        productDAO.update(pt, id);
    }
}
