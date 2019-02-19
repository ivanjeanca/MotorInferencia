package back;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Maestro {

    indice indice;
    IndicePremisa iPremisas;
    int configuracion,largo;
    public Maestro(){
        this.indice=new indice();
        this.iPremisas=new IndicePremisa();
        this.configuracion=4;
        this.largo=2;
    }
    public void escribirMaestro()throws IOException{
        int bandera=1,llave,n;
        int[]res;
        String[]aux;
        StringBuffer datos;
        long tamaño;
        String premisa,salida,prueba,dato;
        RandomAccessFile Maestro;
        Scanner entrada=new Scanner(System.in);
        do{
            String[]premisas=new String[configuracion];
            Maestro=new RandomAccessFile("Maestro","rw");
            tamaño=Maestro.length();
            Maestro.seek(tamaño);//nos vamos hasta el final del archivo
            System.out.println("ingrese el numero de regla a ingresar");
            llave=entrada.nextInt();
            res=indice.buscarIndice(llave);
            if(res[0]==1){
                System.out.println("Regla ya existente");
                System.out.println("Presione 0 para terminar o 1 para ingresar una nueva regla");
                bandera=entrada.nextInt();
            }
            else {
                indice.escribir_indice(llave);//escribimos la entrada en el indice
                Maestro.writeInt(llave);//escribimos la regla en el maestro
                System.out.println("ingrese las premisas separadas por una coma p1,p2,p3");
                premisa=entrada.next();
                aux=premisa.split(",");
                for(int i=0;i<aux.length;i++){
                    premisas[i]=aux[i];
                }
                aux = new String[0];
                for(int i=0;i<aux.length;i++){
                    System.out.println(aux[i]);
                }
                //Esto es un cambio hasta el siguiente comentario  de cierre de cambio

                for(n=0;n<configuracion;n++){
                    if(premisas[n]!=null){
                        dato=premisas[n];
                        datos=new StringBuffer(dato);
                        datos.setLength(largo);
                        prueba=datos.toString();
                        Maestro.writeChars(prueba);
                        iPremisas.EscribirPremisa(prueba,llave);
                    }
                    else {
                        dato="Pv";
                        datos=new StringBuffer(dato);
                        datos.setLength(largo);
                        prueba=datos.toString();
                        Maestro.writeChars(prueba);
                    }
                }//fin for cuando salga del for ya tiene que haber escrito todas las premisas ingresadas
                dato="->";
                datos=new StringBuffer(dato);
                datos.setLength(largo);
                prueba=datos.toString();
                Maestro.writeChars(prueba);
                System.out.println("ingrese la salida !solo una premisa!");
                dato=entrada.next();
                datos=new StringBuffer(dato);
                datos.setLength(2);
                prueba=datos.toString();
                Maestro.writeChars(prueba);
                Maestro.close();
                System.out.println("Presione 0 para terminar o 1 para ingresar una nueva regla");
                bandera=entrada.nextInt();
            }//fin else
        }while (bandera!=0);
    }

    public long desplazamiento()throws  IOException{
        long desp;
        int i,c;
        char[]dato=new char[largo];
        RandomAccessFile file=new RandomAccessFile("Maestro","rw");
        file.readInt();//lee la llave
        for(i=0;i<configuracion;i++){
            for(c=0;c<dato.length;c++)
                dato[c]=file.readChar();
        }
        for (c=0;c<dato.length;c++){//este solo lee el caracter de la condicional para avanzar
            dato[c]=file.readChar();
        }//fin del f
         for (c=0;c<dato.length;c++){//este solo lee el caracter de la condicional para avanzar
            dato[c]=file.readChar();
        }//fin del f
        desp=file.getFilePointer();
        file.close();
        return  desp;
    }
    public void buscarReglaAleatorio(int llave)throws IOException{
        RandomAccessFile file;
        int posicion,i,dt,key,c;
        char temp;
        int[] pos;
        long longitud=desplazamiento();
        long desplazamiento;
        char[]premisa=new char[largo];
        String[] premisas = new String[configuracion];//del tamaño de las configuraciones*2
        String puente,salida="",predicado="";
        pos=indice.buscarIndice(llave);
        posicion=pos[1];
        System.out.println("posicion:"+posicion);
        if(pos[0]==-1)
            System.out.println("el registro no existe");
        else{
            file=new RandomAccessFile("Maestro","rw");
            desplazamiento=(posicion-1)*longitud;
            System.out.println("desplazamiento:"+desplazamiento);
            System.out.println("longitud:"+longitud);
            file.seek(desplazamiento);
            key=file.readInt();
            //System.out.println(key);
            for(i=0;i<configuracion;i++){//este for debe leer todas las premisas antes del ->
                //de nuevo corregir el 8 por variable configuraciones
                for (c=0;c<premisa.length;c++){
                    temp=file.readChar();
                    predicado=predicado+temp;
                    //premisa[c]=temp;
                }//fin del for
                //puente=premisa.toString();
                premisas[i]=predicado;
                predicado="";
            }//fin del for
            for (c=0;c<premisa.length;c++){//este solo lee el caracter de la condicional para avanzar
                premisa[c]=file.readChar();
            }//fin del for
            for (c=0;c<premisa.length;c++){//// obtiene la premisa de salida
                temp=file.readChar();
                predicado=predicado+temp;
                //premisa[c]=file.readChar();
            }//fin del for
            puente=predicado;
            for (c=0;c<premisas.length;c++)
                salida=salida+premisas[c]+"^";
            salida=salida+"->"+puente;
            System.out.println(key+".-"+salida);
            file.close();
        }//fin else mas exterior
    }
    public void borrarRegistro() throws IOException {
        int llave;
        Scanner entrada=new Scanner(System.in);
        System.out.println("ingrese la regla a borrar");
        llave=entrada.nextInt();
        indice.borrarIndice(llave);
    }
}
