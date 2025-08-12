package proyecto.modelo;

import java.io.Serializable;

public abstract class Persona implements Serializable {

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
