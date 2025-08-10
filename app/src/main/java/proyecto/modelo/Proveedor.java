package proyecto.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Proveedor extends Persona implements Serializable {

    // Variables de instancia    
    private String descripcion;

    // Constructor
    public Proveedor(String id, String nombre, String telefono, String descripcion) {
        super(id, nombre, telefono);
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public static ArrayList<Proveedor> obtenerProveedores() {
        ArrayList<Proveedor> lst = new ArrayList<>();
        lst.add(new Proveedor("P001", "LubriExpress", "022345678", "Lubricantes y filtros"));
        lst.add(new Proveedor("P002", "AutoPartes HG", "0998877665", "Repuestos varios"));
        return lst;
    }
}
