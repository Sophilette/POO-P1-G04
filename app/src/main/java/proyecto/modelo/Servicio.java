package automanager.modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Servicio {

    // Variables de instancia
    private String codigo;
    private String nombre;
    private double precioActual;
    private ArrayList<PrecioHistorial> historialPrecios; // Lista de historial de precios

    // Constructor
    public Servicio(String codigo, String nombre, double precioActual) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioActual = precioActual;
        this.historialPrecios = new ArrayList<>();
    }

    // Getters
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecioActual() {
        return precioActual;
    }

    public ArrayList<PrecioHistorial> getHistorialPrecios() {
        return historialPrecios;
    }

    // Método setter para actualizar el precio
    public void setPrecioActual(double nuevoPrecio) {
        this.precioActual = nuevoPrecio;
    }

    // Agregar precio a la lista del historial de precios del servicio
    public void registrarPrecioEnHistorial(double precio) {
        PrecioHistorial nuevoRegistro = new PrecioHistorial(LocalDate.now(), precio);
        historialPrecios.add(nuevoRegistro);
    }
    
}
