/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motorinferencia;

import java.util.ArrayList;

/**
 *
 * @author EliasMancera
 */
public class Equiparacion {

    String cc = "", regla = "";
    String[] reglas, resolucion;
    int j = 0;
    Maestro ma = new Maestro();

    void encadenamiento(Object[][] hI) {
        for (int i = 0; i < hI.length; i++) {
            if (hI[i][1] != null) {
                cc += hI[i][1] + ",";
            }
            //System.out.println(hI[i][1]);
        }
        resolucion = new String[hI.length];
        reglas = cc.split(",");
        cc = "{";
        for (int i = 0; i < reglas.length; i++) {
            if (!reglas[i].equals("-1")) {
                cc += reglas[i] + ",";
                resolucion[j] = reglas[i];
                j++;
            }
            //System.out.println(reglas[i]);
        }
        cc = cc.substring(0, cc.length() - 1);
        cc += "}";
        System.out.println(cc);

        for (int i = 0; i < resolucion.length; i++) {
            //regla = ma.buscarReglaAleatorio((Integer.parseInt(resolucion[i])));
        }

    }

    boolean contenida(String meta, String[] bh) {
        for (String hecho : bh) {
            if (hecho == meta) {
                return true;
            }
        }
        return true;
    }

    boolean vacio(String[] cc) {
        if (cc.length != 0) {
            return false;
        }
        return true;
    }

    String extraerRegla() {
        String regla = "a,b->p";
        return regla;
    }

    String[] equiparacion(String[] bh) {

        return new String[10];
    }
}
