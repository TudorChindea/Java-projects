package Model;
//import Controller.*;
import View.*;
public class Monom implements Comparable{
    private float coeff;
    private int degree;
    public Monom(int degree,float coeff) {
        this.coeff=coeff;
        this.degree=degree;
    }

    @Override
    public int compareTo(Object compareMon) {
        int compareDeg = ((Monom)compareMon).getDegree();
        return compareDeg-this.getDegree();
    }

    public float getCoeff() {
        return coeff;
    }

    public void setCoeff(float coeff) {
        this.coeff = coeff;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }
}
