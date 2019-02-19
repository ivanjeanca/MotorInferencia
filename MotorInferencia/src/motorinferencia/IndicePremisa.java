package motorinferencia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class IndicePremisa {
    int configuracion=4;
    int largo=2;
    protected void EscribirPremisa(String premisa,int regla){
        System.out.println("Entre a escribir");
        RandomAccessFile file;
        boolean escrito=false,existe=false;
        StringBuffer nPremisa;
        String prem,prueba;
        int valor,c=0;
        long apActual,apFinal,apuntador;
        char dato[]=new char[largo];
        try {
            file=new RandomAccessFile("indicePremisas","rw");
            if(file.length()==0){
                //escribimos normal
                System.out.println("primer regla");
                nPremisa=new StringBuffer(premisa);
                nPremisa.setLength(largo);
                prueba=nPremisa.toString();
                file.writeChars(prueba);
                for(c=0;c<configuracion;c++){
                    if(c==0)
                        file.writeInt(regla);
                    else
                        file.writeInt(-1);
                }
                file.close();

            }
            else{
                while ((apActual=file.getFilePointer()) != (apFinal=file.length())){
                    for(c=0;c<largo;c++){
                        dato[c]=file.readChar();
                        apuntador=file.getFilePointer();
                        System.out.print("apuntador n"+apuntador);
                    }//fin del for
                    prem=new String(dato);
                    if(prem.equalsIgnoreCase(premisa)){
                        System.out.print("ap1"+apActual+" ");
                        System.out.print("premisa"+prem+"/");
                        for(c=0;c<configuracion;c++){
                            valor=file.readInt();
                            apuntador=file.getFilePointer();
                            if(valor==regla){
                                escrito=false;
                                existe=true;
                                System.out.println("regla igual");
                                break;

                            }
                            else{
                                if(valor==-1){
                                    System.out.println(apActual);
                                    file.seek(apuntador-4);
                                    file.writeInt(regla);
                                    escrito= true;
                                    System.out.println("sobreescribi regla");
                                    break;
                                }
                            }
                        }//fin del for
                        if(escrito)
                            break;
                    }//fin del if
                    else {
                        for(c=0;c<configuracion;c++)
                            file.readInt();
                    }
                }
                //fin while (en teoria aqui quedamos al final y sabemos si esta escrito)
                if(!escrito && !existe){
                    System.out.println("nueva regla");
                    nPremisa=new StringBuffer(premisa);
                    nPremisa.setLength(largo);
                    prueba=nPremisa.toString();
                    file.writeChars(prueba);
                    for(c=0;c<configuracion;c++){
                        if(c==0)
                            file.writeInt(regla);
                        else
                            file.writeInt(-1);
                    }
                }//fin if esto checha si se escribio entonces no entra si no entra y escribe la premisa nueva y la regla donde la encontro al final del archivo
                file.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    protected int[] ObtenerReglas(String premisa){
        RandomAccessFile file;
        int[] reglas=new int[configuracion];
        int c,valor;
        long apActual,apFinal;
        char []dato=new char[largo];
        String prem;
        try {
            file=new RandomAccessFile("indicePremisas","rw");
            while ((apActual=file.getFilePointer()) != (apFinal=file.length())){
                for(c=0;c<dato.length;c++)
                    dato[c]=file.readChar();
                prem=new String(dato);
                if(prem.equalsIgnoreCase(premisa)){
                    for(c=0;c<configuracion;c++){
                        valor=file.readInt();
                        reglas[c]=valor;
                    }
                    break;
                }
                else {
                    for(c=0;c<configuracion;c++)
                        file.readInt();
                }
            }
            file.close();
        }catch (IOException e){ e.printStackTrace(); }

        return reglas;
    }
    protected void leerPremisas() throws IOException {
        RandomAccessFile file;
        file=new RandomAccessFile("indicePremisas","r");
        long longitud=file.length();
        for(int i=0;i<longitud/12;i++){
            System.out.println(file.readChar()+" "+file.readChar()+" ");
            System.out.println(file.readInt()+" "+file.readInt()+" ");
        }
    }

}
