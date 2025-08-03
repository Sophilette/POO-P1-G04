package automanager.modelo;

public class Tecnico extends Persona {

    // Variables de instancia
    private String especialidad;

    // Constructor
    public Tecnico(String id, String nombre, String telefono, String especialidad) {
        super(id, nombre, telefono);
        this.especialidad = especialidad;
    }

    // Getters y Setters
    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
   
}
