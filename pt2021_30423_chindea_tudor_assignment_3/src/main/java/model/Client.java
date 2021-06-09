package model;

/**
 * CLient class with the same fields as the Client table and with two constructors, getters and setters
 */
public class Client {
    private int id;
    private String name;
    private String email;
    private String address;
    public Client(){

    }
    public Client(String name, String email, String address){
        this.name=name;
        this.email=email;
        this.address=address;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
