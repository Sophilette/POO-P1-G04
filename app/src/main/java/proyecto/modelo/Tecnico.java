package proyecto.modelo;

import java.io.Serializable;
import java.util.ArrayList;
// I/O para guardar/cargar lista
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

/**
 * Entidad Técnico.
 * - Hereda de Persona (Serializable por herencia).
 * - Campo extra: especialidad.
 * - Métodos estáticos para persistir: cargar/sembrar/guardar.
 */

public class Tecnico extends Persona implements Serializable {

    // Variables de instancia
    private String especialidad;
    // Nombre del archivo de respaldo de técnicos
    public static final String NOM_ARCHIVO = "tecnicos.ser";

    // Constructor
    public Tecnico(String id, String nombre, String telefono, String especialidad) {
        super(id, nombre, telefono);
        this.especialidad = especialidad;
    }

    // Getters y Setters
    public String getEspecialidad() { return especialidad; }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public static ArrayList<Tecnico> obtenerTecnicos(){
        ArrayList<Tecnico> lstTecnicos = new ArrayList<>();

        lstTecnicos.add(new Tecnico("T001", "Mario Barcos", "0999001122", "Frenos"));
        lstTecnicos.add(new Tecnico("T002", "Alvaro López", "0999888777", "Suspensión"));

        return lstTecnicos;

    }

    @Override public String toString() { return this.getNombre(); }

    // ================= PERSISTENCIA =================

    /** Carga técnicos desde archivo. Si no existe, lista vacía. */
    public static ArrayList<Tecnico> cargarTecnicos(File directorio) {
        ArrayList<Tecnico> lista = new ArrayList<>();
        try {
            File f = new File(directorio, NOM_ARCHIVO);
            if (f.exists()) {
                try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(f))) {
                    //noinspection unchecked
                    lista = (ArrayList<Tecnico>) is.readObject();
                }
            }
        } catch (Exception ignored) { }
        return lista;
    }

    /** Crea archivo con datos de ejemplo si aún no existe. */
    public static boolean crearDatosIniciales(File directorio) throws Exception {
        File f = new File(directorio, NOM_ARCHIVO);
        if (!f.exists()) {
            ArrayList<Tecnico> semilla = obtenerTecnicos();
            try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f))) {
                os.writeObject(semilla);
                return true;
            } catch (IOException e) { throw new Exception(e.getMessage()); }
        }
        return true;
    }

    /** Guarda la lista completa en el archivo. */
    public static boolean guardarLista(File directorio, ArrayList<Tecnico> lista) throws Exception {
        File f = new File(directorio, NOM_ARCHIVO);
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f))) {
            os.writeObject(lista);
            return true;
        } catch (IOException e) { throw new Exception(e.getMessage()); }
    }
}
