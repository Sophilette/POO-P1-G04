package automanager.modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class OrdenServicio {

    // Variables de instancia 
    private Cliente cliente;
    private Vehiculo vehiculo;
    private Tecnico tecnico;
    private LocalDate fecha;
    private EstadoOrden estado; // Enum de estado de la orden
    private ArrayList<ItemOrdenServicio> items; // Lista de items de la orden

    
        //  constructor
    public OrdenServicio(Cliente cliente, Vehiculo vehiculo, Tecnico tecnico, LocalDate fecha) {
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.fecha = fecha;
        this.tecnico = tecnico;
        this.items = new ArrayList<>();
    }

    // Getters
    public Cliente getCliente() {
        return cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public ArrayList<ItemOrdenServicio> getItems() {
        return items;
    }

    // Setters
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setEstado(EstadoOrden estado) {
        this.estado = estado;
    }

    //Agregar item a la lista de items de la orden
    public void agregarItem(ItemOrdenServicio item) {
        items.add(item);
    }

    // Calcular el total de la suma de los valores a pagar por cada item
    public double calcularTotal() {
        double total = 0;
        for (ItemOrdenServicio item : items) {
            total += item.calcularSubtotal();
        }
        return total;
    }

    public enum EstadoOrden {
    
    PENDIENTE, EN_PROCESO, COMPLETADA, CANCELADA

}

}