package Business;

import jdk.dynalink.Operation;

import java.io.IOException;
import java.util.ArrayList;

public interface IDeliveryServiceProcessing {
    void importItems() throws IOException;
    void manageProducts( MenuItem prodVechi, MenuItem prodNou);
    void deleteProducts(MenuItem oldItem);
    void generateReports();
    Order createOrder(String clientName, ArrayList<MenuItem>items);
    //ArrayList<MenuItem> searchProduct(ArrayList<CriteriaValue> criteria);
}
