package proyecto.modelo;

import java.io.Serializable;
import java.time.LocalDate;

// Tipo de objeto para la lista de historial de precios de la clase servicio
public class PrecioHistorial implements Serializable {

    // Variables de instancia
    private LocalDate fecha;
    private double precio;

    // Contructor
    public PrecioHistorial(LocalDate fecha, double precio) {
        this.fecha = fecha;
        this.precio = precio;
    }

    // Getters
    public LocalDate getFecha() { return fecha; }

    public double getPrecio() { return precio; }
    
}
