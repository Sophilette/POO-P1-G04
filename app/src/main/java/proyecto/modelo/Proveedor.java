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
 * Entidad Proveedor.
 * - Extiende Persona (id, nombre, telefono).
 * - Campo adicional: descripcion.
 * - Métodos estáticos para persistir lista en archivo (proveedores.ser):
 *      - cargarProveedores(...)
 *      - crearDatosIniciales(...)
 *      - guardarLista(...)
 */

public class Proveedor extends Persona implements Serializable {

    // Variables de instancia    
    private String descripcion;

    // === Nombre del archivo de persistencia ===
    public static final String NOM_ARCHIVO = "proveedores.ser";

    // Constructor
    public Proveedor(String id, String nombre, String telefono, String descripcion) {
        super(id, nombre, telefono);
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override public String toString() { return this.getNombre(); }

    // ============================================================
    //   SEMILLA (datos de ejemplo) para la primera ejecución
    // ============================================================
    public static ArrayList<Proveedor> obtenerProveedores() {
        ArrayList<Proveedor> lst = new ArrayList<>();
        lst.add(new Proveedor("P001", "LubriExpress", "022345678", "Lubricantes y filtros"));
        lst.add(new Proveedor("P002", "AutoPartes HG", "0998877665", "Repuestos varios"));
        lst.add(new Proveedor("P003","Frenos del Pacífico","023334455","Pastillas y discos de freno"));
        return lst;
    }

    /** Carga proveedores desde archivo. Si no existe, devuelve lista vacía. */
    public static ArrayList<Proveedor> cargarProveedores(File directorio) {
        ArrayList<Proveedor> lista = new ArrayList<>();
        try {
            File f = new File(directorio, NOM_ARCHIVO);
            if (f.exists()) {
                try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(f))) {
                    //noinspection unchecked
                    lista = (ArrayList<Proveedor>) is.readObject();
                }
            }
        } catch (Exception e) {
            // Silencioso: si falla, regresamos lista vacía (la UI lo maneja)
        }
        return lista;
    }

    /** Crea archivo con datos iniciales si aún no existe. */
    public static boolean crearDatosIniciales(File directorio) throws Exception {
        File f = new File(directorio, NOM_ARCHIVO);
        if (!f.exists()) {
            ArrayList<Proveedor> semilla = obtenerProveedores();
            try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f))) {
                os.writeObject(semilla);
                return true;
            } catch (IOException e) {
                throw new Exception(e.getMessage());
            }
        }
        return true;
    }

    /** Guarda la lista completa en el archivo. */
    public static boolean guardarLista(File directorio, ArrayList<Proveedor> lista) throws Exception {
        File f = new File(directorio, NOM_ARCHIVO);
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f))) {
            os.writeObject(lista);
            return true;
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        }
    }
}
