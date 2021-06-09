import Model.Polinom;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PolinomTest {

    @Test
    void addPolinomials() {
        Polinom f=new Polinom();
        Polinom g=new Polinom();
        Polinom calculated = new Polinom();
        String expected=new String(),computed=new String();
        f.strToPolin("x^2-x+1");
        g.strToPolin("-x+1");
        calculated=f.addPolinomials(g);
        expected="1.0x^2-2.0x^1+2.0";
        computed=calculated.makeString();
        assertEquals(expected,computed);
        f.strToPolin("x^2-x+1");
        g.strToPolin("-x^3");
        calculated=f.addPolinomials(g);
        expected="-1.0x^3+1.0x^2-1.0x^1+1.0";
        computed=calculated.makeString();
        assertEquals(expected,computed);
        f.strToPolin("x^5-x^2+0");
        g.strToPolin("-x^6");
        calculated=f.addPolinomials(g);
        expected="-1.0x^6+1.0x^5-1.0x^2";
        computed=calculated.makeString();
        assertEquals(expected,computed);
    }

    @Test
    void substrPolinomials() {
        Polinom f=new Polinom();
        Polinom g=new Polinom();
        Polinom calculated = new Polinom();
        String expected=new String(),computed=new String();
        f.strToPolin("x^2-x+1");
        g.strToPolin("-x+1");
        calculated=f.substrPolinomials(g);
        expected="1.0x^2";
        computed=calculated.makeString();
        assertEquals(expected,computed);
    }

    @Test
    void dividePolinomials() {
        Polinom f=new Polinom();
        Polinom g=new Polinom();
        ArrayList<Polinom> calculated = new ArrayList<>();
        String expectedRest=new String(),computedRest=new String(),expectedQo=new String(),computedQo=new String();
        f.strToPolin("x^8-x^5+1");
        g.strToPolin("x^5+1");
        calculated=f.dividePolinomials(g);
        expectedQo="1.0x^3-1.0";
        expectedRest="-1.0x^3+2.0";
        computedRest=calculated.get(0).makeString();
        computedQo=calculated.get(1).makeString();
        assertEquals(expectedRest,computedRest);
        assertEquals(expectedQo,computedQo);
    }

    @Test
    void multiplyPolinomials() {
        Polinom f=new Polinom();
        Polinom g=new Polinom();
        Polinom calculated = new Polinom();
        String expected=new String(),computed=new String();
        f.strToPolin("x^5-x^2+1");
        g.strToPolin("x^3-2");
        calculated=f.multiplyPolinomials(g);
        expected="1.0x^8-3.0x^5+1.0x^3+2.0x^2-2.0";
        computed=calculated.makeString();
        assertEquals(expected,computed);
    }

    @Test
    void integration() {
        Polinom f=new Polinom();
        Polinom calculated = new Polinom();
        String expected=new String(),computed=new String();
        f.strToPolin("-2x^7-3x^3+x");
        calculated=f.integration();
        expected="-0.25x^8-0.75x^4+0.5x^2";
        computed=calculated.makeString();
        assertEquals(expected,computed);
    }

    @Test
    void derivation() {
        Polinom f=new Polinom();
        Polinom calculated = new Polinom();
        String expected=new String(),computed=new String();
        f.strToPolin("-2x^7-3x^3+x");
        calculated=f.derivation();
        expected="-14.0x^6-9.0x^2+1.0";
        computed=calculated.makeString();
        assertEquals(expected,computed);
    }
}