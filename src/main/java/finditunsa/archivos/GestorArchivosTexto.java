package finditunsa.archivos;

import finditunsa.modelos.Reporte;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

//MODULO: Persistencia de Reportes y Archivos de Texto
//DESCRIPCION: Registra actividades, exporta reportes y permite leer el historial utilizando las clases PrintWriter, FileWriter, Scanner y FileReader.

public class GestorArchivosTexto {
    private static final String RUTA_HISTORIAL = "data/texto/historial.txt";
    private static final String RUTA_REPORTES = "data/texto/reportes.txt";

    public static void registrarActividad(String mensaje) {
        File archivo = new File(RUTA_HISTORIAL);
        File carpeta = archivo.getParentFile();
        if (carpeta != null) carpeta.mkdirs(); // Crea la carpeta si no existe

        // El segundo parámetro (true) activa el modo "append" para no borrar el texto anterior
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo, true))) {
            pw.println("[" + LocalDateTime.now() + "] " + mensaje);
        } catch (IOException e) {
            System.err.println("Error al escribir historial: " + e.getMessage());
        }
    }

    public static void exportarReportes(List<Reporte> reportes) {
        File archivo = new File(RUTA_REPORTES);
        File carpeta = archivo.getParentFile();
        if (carpeta != null) carpeta.mkdirs();

        // Al no poner "true", el archivo se sobrescribe con la lista actualizada
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            pw.println("=== LISTA DE REPORTES EN EL SISTEMA ===");
            for (Reporte r : reportes) {
                String nombre = r.getObjeto() == null ? "Sin objeto" : r.getObjeto().getNombre();
                pw.println(r.getId() + " | " + r.getTipoReporte() + " | " + nombre + " | " + r.getEstado());
            }
        } catch (IOException e) {
            System.err.println("Error al exportar reportes: " + e.getMessage());
        }
    }

    public static void leerHistorial() {
        File archivo = new File(RUTA_HISTORIAL);
        if (!archivo.exists()) return;

        // Lectura secuencial del archivo línea por línea usando Scanner y FileReader
        try (Scanner sc = new Scanner(new FileReader(archivo))) {
            System.out.println("--- LEYENDO HISTORIAL DE ACTIVIDAD ---");
            while (sc.hasNextLine()) System.out.println(sc.nextLine());
        } catch (IOException e) {
            System.err.println("Error al leer historial: " + e.getMessage());
        }
    }
}
