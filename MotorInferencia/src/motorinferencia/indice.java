package motorinferencia;

import java.io.IOException;
import java.io.RandomAccessFile;

public class indice {
    public int[] buscarIndice(int llave)throws IOException{
        // regresa la posicion si encuentra el llave si no regresa un -1
        RandomAccessFile file;
        int llaveLeida,posicion=-1,i,existe=-1;
        long tamaño;
        int []respuesta = new int[2];
        file=new RandomAccessFile("indice","rw");
        tamaño=file.length();
        if(tamaño==0){
            System.out.println("aqui entre");
            existe=-1;
            posicion=1;
        }
        else {
            System.out.println("aqui entre 2");
            for (i=0;i<tamaño/8;i++){
                llaveLeida=file.readInt();
                if(llave==llaveLeida){
                    existe=1;
                    posicion=i+1;
                    file.readInt();
                    break;
                }
                else{
                    existe=-1;
                    posicion=i+1;
                    file.readInt();
                }
            }//fin del for
        }//fin else
        respuesta[0]=existe;
        respuesta[1]=posicion;
        file.close();
        return respuesta;
    }

    public boolean escribir_indice(int llave)throws IOException{
        boolean escrito;
        long longitud;
        int [] respuesta;
        RandomAccessFile file;
        respuesta=buscarIndice(llave);
        if(respuesta[0]==-1){
            file=new RandomAccessFile("indice","rw");
            longitud=file.length();
            file.seek(longitud);
            file.writeInt(llave);
            if(respuesta[1]==1)
                file.writeInt(1);
            else
                file.writeInt(respuesta[1]+1);
            file.close();
            escrito=true;
        }
        else
            escrito=false;

        return escrito;
    }
    public void borrarIndice(int llave) throws IOException {
        int[] posicion;
        int pos;
        long longitud,desplazamiento;
        RandomAccessFile file;
        posicion=buscarIndice(llave);
        if(posicion[0]==1){
            file=new RandomAccessFile("indice","rw");
            longitud=desplazamiento();
            pos=posicion[1];
            desplazamiento=(pos-1)*longitud;
            file.seek(desplazamiento);
            file.writeInt(-1);
        }
        else
            System.out.println("intento borrar un registro que no existe");
    }
    public long desplazamiento() throws IOException {
        long desp;
        RandomAccessFile file=new RandomAccessFile("indice","rw");
        file.readInt();
        file.readInt();
        desp=file.getFilePointer();
        file.close();
        return desp;
    }
}
