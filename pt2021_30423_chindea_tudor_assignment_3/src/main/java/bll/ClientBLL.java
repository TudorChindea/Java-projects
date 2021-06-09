package bll;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validator.EmailValidator;
import bll.validator.Validator;
import dao.ClientDAO;
import model.Client;

/**
 * this class uses the client validators and all the methods corresponding to ClientDAO
 * all methods are an implementation of the sql queries
 */
public class ClientBLL {
    private List<Validator<Client>> validators;
    private ClientDAO clientDAO;

    /**
     * constructor that initializez the validators and ClientDAO
     */
    public ClientBLL() {
        validators = new ArrayList<Validator<Client>>();
        validators.add(new EmailValidator());
        clientDAO = new ClientDAO();
    }

    /**
     * returns the client returned by findById(id) or throws new exception if the client was not found
     * @param id
     * @return
     */
    public Client findClientById(int id) {
        Client ct = clientDAO.findById(id);
        if (ct == null) {
            throw new NoSuchElementException("Client with id =" + id + " was not found!");
        }
        return ct;
    }

    /**
     * returns a list of clients returned by findAll() or throws a new exception if no clients exist
     * @return
     */
    public List<Client> findAllClients(){
        List<Client> ct = clientDAO.findAll();
        if (ct == null) {
            throw new NoSuchElementException("there are no clients");
        }
        return ct;
    }

    /**
     * calls function insert for the client ct
     * @param ct
     */
    public void insertClient(Client ct){
        clientDAO.insert(ct);
    }

    /**
     * calls function deleteElementID for integer id
     * @param id
     */
    public void deleteClient(int id){
        clientDAO.deleteElementID(id);
    }

    /**
     * calls function update for the Client ct and the intgere id
     * @param ct
     * @param id
     */
    public void updateClient(Client ct, int id){
        clientDAO.update(ct, id);
    }
}
