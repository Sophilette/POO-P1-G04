package proyecto.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Tecnico extends Persona implements Serializable {

    // Variables de instancia
    private String especialidad;

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
   
}
