package automanager.modelo;

public abstract class Persona {

    // Variables de instancia
    protected String id;
    protected String nombre;
    protected String telefono;

    // Constructor
    public Persona(String id, String nombre, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

}
