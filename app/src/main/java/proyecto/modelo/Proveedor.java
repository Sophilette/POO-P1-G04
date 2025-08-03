package automanager.modelo;

public class Proveedor extends Persona {

    // Variables de instancia    
    private String descripcion;

    // Constructor
    public Proveedor(String id, String nombre, String telefono, String descripcion) {
        super(id, nombre, telefono);
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
  
}
