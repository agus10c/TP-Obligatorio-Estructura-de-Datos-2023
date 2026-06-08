package Clases;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class Log {

    private static PrintWriter pw = null;

    public static void inicializar(String rutaArchivo) {
        
        //Verifico que el printWriter sea nulo para no abrir otro nuevamente
        if (pw == null) {

            try {
                //FileWriter abre el archivo Log para poder escribir en el
                //PrintWriter para poder utilizar metodos para escribir sobre el archivo(Ej: printlns)
                pw = new PrintWriter(new FileWriter(rutaArchivo));
                
            } catch (IOException ex) {
                System.out.println("Error al intentar abrir el archivo log: " + ex.getMessage());
            }

        }

    }

    public static void escribir(String mensaje) {
        //Este metdoo escribe el mensaje recibido por parametro en el archivo 
        
        if (pw != null) {
            pw.println("[" + LocalDateTime.now() + "] " + mensaje);
            pw.flush();
        }
        
    }

    public static void cerrar() {
        //Cierra el printWriter
        
        if (pw != null) {
            pw.close();
        }
        
    }
    
}
