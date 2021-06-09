package Data;

import Business.DeliveryService;

import java.io.*;

/**
 * serializing methods are used to store the data when modified and to keep it in a file when the app is colsed
 */
public class Serializator implements Serializable{
    public void serializeaza(DeliveryService o){
        try {
            FileOutputStream fileOut = new FileOutputStream("allData.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(o);
            out.close();
            fileOut.close();

        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    public DeliveryService deserializeaza(){
        DeliveryService e = null;
        try {
            FileInputStream fileIn = new FileInputStream("allData.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (DeliveryService) in.readObject();
            in.close();
            fileIn.close();
            return e;
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return null;
        }
    }
}
