package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
//import Controller.*;
import View.*;
public class Polinom {
    private ArrayList<Monom> polinom1 = new ArrayList<>();
    public Polinom addPolinomials(Polinom polinom2){
        Polinom result = new Polinom();
        int  poli2I = 0, poli1I = 0;
            while (poli1I < this.getPolinom().size() && poli2I < polinom2.getPolinom().size()) {
                if (polinom2.getPolinom().get(poli2I).getDegree() == this.getPolinom().get(poli1I).getDegree()) {
                    Monom m1=new Monom(this.getPolinom().get(poli1I).getDegree(),this.getPolinom().get(poli1I++).getCoeff() + polinom2.getPolinom().get(poli2I++).getCoeff());
                    result.getPolinom().add(m1);
                } else if (polinom2.polinom1.get(poli2I).getDegree() > this.polinom1.get(poli1I).getDegree()) {
                    Monom m2=new Monom(polinom2.polinom1.get(poli2I).getDegree(),polinom2.polinom1.get(poli2I++).getCoeff());
                    result.getPolinom().add(m2);
                } else {
                    Monom m3=new Monom(this.polinom1.get(poli1I).getDegree(),this.polinom1.get(poli1I++).getCoeff());
                    result.getPolinom().add(m3);
                }
            }
            while(poli1I < this.getPolinom().size())
                if(poli1I < this.getPolinom().size()){
                    Monom m5=new Monom(this.getPolinom().get(poli1I).getDegree(),this.getPolinom().get(poli1I++).getCoeff());
                    result.getPolinom().add(m5);
                }
            while(poli2I < polinom2.getPolinom().size())
                if(poli2I < polinom2.getPolinom().size()){
                    Monom m4=new Monom(polinom2.getPolinom().get(poli2I).getDegree(),polinom2.getPolinom().get(poli2I++).getCoeff());
                    result.getPolinom().add(m4);
                }
        return result;
    }

    public void strToPolin(String input) {
        Polinom polin = new Polinom();
        int  deg;
        float coeff;
        String newPoly = input.replaceAll("-", "+-");
        StringTokenizer stk = new StringTokenizer(newPoly, "+");
        while (stk.hasMoreElements()) {
            String mono= stk.nextToken();
            deg = 0;
            int xPos = mono.indexOf("x");
            int degPos = mono.indexOf("^");
            if (xPos != -1) {
                if (xPos == 0) {
                    coeff = 1;
                } else if (xPos == 1 && mono.charAt(0) == '-') {
                    coeff = -1;
                } else {
                    coeff = Float.parseFloat(mono.substring(0, xPos));
                }
            } else {
                coeff = Float.parseFloat(mono);
                deg = 0;
            }
            if (degPos != -1) {
                deg = Integer.parseInt(mono.substring(degPos + 1));
            } else if (xPos != -1) {
                deg = 1;
            }
            Monom m1=new Monom(deg,coeff);
            polin.getPolinom().add(m1);
        }
        this.setPolinom(polin.getPolinom());
    }
    public String makeString(){
        String output="";
        for(Monom monom: this.getPolinom())
        {
            if(monom.getCoeff()>0)
                output=output+"+"+String.valueOf(monom.getCoeff()) ;
            else if(monom.getCoeff() < 0)
                output=output+String.valueOf(monom.getCoeff()) ;
            if(monom.getDegree()>0 && monom.getCoeff() != 0)
                output=output + "x^" + String.valueOf(monom.getDegree());
        }
        if(!output.equals("")) {
            if (output.charAt(0) == '+')
                output = output.substring(1);
        }
        if(output.equals(""))
            return "0.0";
        else
            return  output;
    }
    public Polinom substrPolinomials(Polinom polinom2){
        Polinom result = new Polinom();
        for(Monom m1:polinom2.getPolinom()){
            m1.setCoeff(m1.getCoeff()*(-1));
        }
        result=addPolinomials(polinom2);
        return result;
    }
    public void deletZero(){
      ArrayList<Monom> copyR=new ArrayList<>();
                for(Monom m:this.getPolinom()){
                    if(m.getCoeff()==0 )
                        copyR.add(m);
                }
                this.getPolinom().removeAll(copyR);
    }
    public ArrayList<Polinom> dividePolinomials(Polinom polinom2){
        Polinom r=new Polinom();
        Polinom t1=new Polinom();
        ArrayList<Polinom> result =  new ArrayList<>();
        Polinom sum=new Polinom();
        Monom c=new Monom(0,0);
        Monom d=new Monom(0,0);
        t1.getPolinom().add(c);
        if(polinom2.getPolinom().get(0).getCoeff() != 0){
            Polinom q=new Polinom();
            q.getPolinom().add(d);
            r.setPolinom(this.getPolinom());
            while((r.getPolinom().size()>0 && r.getPolinom().get(0).getCoeff() != 0) && r.getPolinom().get(0).getDegree() >= polinom2.getPolinom().get(0).getDegree()){
                Monom t = new Monom(r.getPolinom().get(0).getDegree()-polinom2.getPolinom().get(0).getDegree(),r.getPolinom().get(0).getCoeff()/polinom2.getPolinom().get(0).getCoeff());
                t1.getPolinom().get(0).setDegree(t.getDegree());
                t1.getPolinom().get(0).setCoeff(t.getCoeff());
               sum=q.addPolinomials(t1);
                q.setPolinom(sum.getPolinom());
                sum=t1.multiplyPolinomials(polinom2);
                r=r.substrPolinomials(sum);
                r.deletZero();
            }
            result.add(r);
            result.add(q);
            return result;
        }
        r.getPolinom().add(c);
        result.add(r);
        result.add(r);
        return result;
    }
    public Polinom multiplyPolinomials(Polinom polinom2){
        Polinom result=new Polinom();
        int sem;
        for(Monom m1:this.getPolinom())
        {
            for(Monom m2:polinom2.getPolinom()){
                Monom m3=new Monom(m1.getDegree()+m2.getDegree(),m1.getCoeff()* m2.getCoeff());
                sem =0;
                for(Monom m4:result.getPolinom()){
                    if(m4.getDegree() == m3.getDegree()){
                        m4.setCoeff(m4.getCoeff()+m3.getCoeff());
                        sem=1;
                    }
                }
                if(sem == 0)
                    result.getPolinom().add(m3);
            }
        }
        Collections.sort(result.getPolinom());


        return result;
    }
    public Polinom integration(){
        Polinom result = new Polinom();
        for(Monom m1:this.getPolinom()){
            m1.setDegree(m1.getDegree()+1);
            m1.setCoeff(m1.getCoeff()/ m1.getDegree());
            result.getPolinom().add(m1);
        }
        return result;
    }
    public Polinom derivation(){
        Polinom result = new Polinom();

        for(Monom m1: this.getPolinom()){
            m1.setCoeff(m1.getCoeff() * m1.getDegree());
            m1.setDegree(m1.getDegree() - 1);
            result.getPolinom().add(m1);
        }
        return result;
    }
    public ArrayList<Monom> getPolinom() {
        return polinom1;
    }
    public void setPolinom(ArrayList<Monom> polinom1) {
        this.polinom1 = polinom1;
    }
}
