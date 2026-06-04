package Clases;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class Log {

    private static String file;
    private static PrintWriter pw = null;

    public static void inicializar(String rutaArchivo) {
        if (pw == null) {
            file = rutaArchivo;
            try {
                pw = new PrintWriter(new FileWriter(file));
            } catch (IOException ex) {
                System.out.println("Error al intentar abrir el archivo log: "+ex.getMessage());
            }         
        }
    }

    public static void escribir(String mensaje) {
        if (pw != null) {
            pw.println("[" + LocalDateTime.now() + "] " + mensaje);
            pw.flush(); // Vacía el buffer si el programa finaliza inesperadamente antes de cerrar el archivo
        }
    }

    public static void cerrar() {
        if (pw != null) {
            pw.close();
        }
    }
}
