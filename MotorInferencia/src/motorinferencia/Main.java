package motorinferencia;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Maestro maestro = new Maestro();
        IndicePremisa premisas = new IndicePremisa();
//        maestro.escribirMaestro();
//        premisas.leerPremisas();
//        maestro.buscarReglaAleatorio();
        maestro.actualizarRegistro();
    }
}