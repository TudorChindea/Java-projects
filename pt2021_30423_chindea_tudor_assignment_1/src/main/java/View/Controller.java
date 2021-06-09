package View;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.Float;
import java.lang.Integer;
//import Controller.*;
import Model.*;
public class Controller {
    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;
    @FXML
    private Button btn4;
    @FXML
    private Button btn5;
    @FXML
    private Button btn6;
    @FXML
    private TextField polin1;
    @FXML
    private TextField polin2;
    @FXML
    private TextField result;
    @FXML
    private TextField result2;
        @FXML
    private TextField invalidInput;
        @FXML
    private TextField invalidInput1;

    @FXML
    private Button btn7;
    public String input1=new String(),input2 = new String();
    private Polinom poli1= new Polinom();
    private Polinom poli2 = new Polinom();
    void pauseOperations(){
        btn1.setDisable(true);
        btn2.setDisable(true);
        btn3.setDisable(true);
        btn4.setDisable(true);
        btn5.setDisable(true);
        btn6.setDisable(true);
    }
    void resumeOperations(){
        btn1.setDisable(false);
        btn2.setDisable(false);
        btn3.setDisable(false);
        btn4.setDisable(false);
        btn5.setDisable(false);
        btn6.setDisable(false);
    }
    void printPolinom(Polinom polinom, TextField result2){
        result2.setText("");
        String output="";
        output=polinom.makeString();
        result2.setText(output);
        pauseOperations();
    }
    @FXML
    void addPolinom(ActionEvent event) {
        result2.clear();
        Polinom result1 = new Polinom();
        result1 = poli1.addPolinomials(poli2);
        printPolinom(result1,result);
        pauseOperations();
    }
    @FXML
    void derivatePolinom(ActionEvent event) {
        result2.clear();
        Polinom result1 = new Polinom();
        result1 = poli1.derivation();
        printPolinom(result1,result);
        pauseOperations();
    }
    @FXML
    void dividePolinom(ActionEvent event) {
        ArrayList<Polinom> result1 = new ArrayList<>();
        result1=poli1.dividePolinomials(poli2);
        printPolinom(result1.get(1),result);
        printPolinom(result1.get(0),result2);
        pauseOperations();
    }
    Boolean validate(String input){
        String noSpaceStr=input.replaceAll("\\s+","");
        String regx="(([+-]?([0-9]([0-9]+)?)?(x(\\^[1-9]([0-9]+)?)?)?)+)";
        Pattern p = Pattern.compile(regx);
        Matcher m = p.matcher( noSpaceStr );
        return p.matcher(noSpaceStr).matches();
    }

    Polinom validation(String input, TextField warningT, TextField getPolin){
        Polinom polin = new Polinom();
        input = getPolin.getText();
        String noSpace=input.replaceAll("\\s+","");
        if(validate(noSpace)){
            polin.strToPolin(input);
            warningT.setText("");
        }
        else{
                warningT.setText("Invalid Model.Polinom");
            }
        return polin;
    }
    @FXML
    void getPolinoms(ActionEvent event) {

        poli1=validation( input1, invalidInput, polin1);
        poli2 =validation( input2, invalidInput1, polin2);
        resumeOperations();
    }
    @FXML
    void integratePolinom(ActionEvent event) {
        result2.clear();
        Polinom result1 = new Polinom();
        result1 = poli1.integration();
        printPolinom(result1,result);
        pauseOperations();
    }
    @FXML
    void multiplyPolinom(ActionEvent event) {
        result2.clear();
        Polinom result1 = new Polinom();
        result1=poli1.multiplyPolinomials(poli2);
        printPolinom(result1,result);
        pauseOperations();
    }
    @FXML
    void substractPolinom(ActionEvent event) {
        result2.clear();
        Polinom result1 = new Polinom();
        result1 = poli1.substrPolinomials(poli2);
        printPolinom(result1,result);
        pauseOperations();
    }
}