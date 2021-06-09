package Business;

import java.io.Serializable;

public class User implements Serializable {
    private String name,password;
    private int clientType;
    public User(String name, String password, int clientType){
        this.name=name;
        this.clientType=clientType;
        this.password=password;
    }
    public int getClientType() {
        return clientType; //0 for client, 1 emplyee, 2 for administrator
    }

    public void setClientType(int clientType) {
        this.clientType = clientType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
