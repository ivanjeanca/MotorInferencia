package back;

import java.io.IOException;
import java.io.RandomAccessFile;

public class IndicePremisa {
    int configuracion=2;
    int largo=2;
    protected void EscribirPremisa(String premisa,int regla){
        RandomAccessFile file;
        boolean escrito=false,existe=false;
        StringBuffer nPremisa;
        String prem,prueba;
        int valor,c=0;
        long apActual,apFinal;
        char dato[]=new char[largo];
        try {
            file=new RandomAccessFile("indicePremisas","rw");
            if(file.length()==0){
                //escribimos normal
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
                    }//fin del for
                    prem=new String(dato);
                    if(prem.equalsIgnoreCase(premisa)){
                        for(c=0;c<configuracion;c++){
                            valor=file.readInt();
                            if(valor==regla){
                                escrito=false;
                                existe=true;
                                break;
                            }
                            else{
                                if(valor==-1){
                                    file.writeInt(regla);
                                    escrito= true;
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
            file=new RandomAccessFile("indicePremisa","rw");
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

}
