package proyecto.modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Factura {

    // Variables de instancia 
    private Cliente cliente;
    private LocalDate fechaEmision;
    private ArrayList<OrdenServicio> ordenes; // Lista de ordenes
    private static final double CARGO_FIJO_EMPRESARIAL = 50.0; // Cargo fijo para clientes de tipo empresarial

    // Constructor
    public Factura(Cliente cliente, LocalDate fechaEmision) {
        this.cliente = cliente;
        this.fechaEmision = fechaEmision;
        this.ordenes = new ArrayList<>();
    }

    // Getters
    public Cliente getCliente() {
        return cliente;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public ArrayList<OrdenServicio> getOrdenes() {
        return ordenes;
    }

    // Agregar Orden a lista de ordenes
    public void agregarOrden(OrdenServicio orden) {
        if (orden != null) {
            ordenes.add(orden);
        }
    }

    // Calcular el total de la factura, teniendo en cuenta el recargo para clientes tipo empresarial
    public double calcularTotal() {
        double total = 0.0;
        for (OrdenServicio orden : ordenes) {
            total += orden.calcularTotal();
        }
        if (cliente.getTipo() == Cliente.TipoCliente.EMPRESARIAL) {
            total += CARGO_FIJO_EMPRESARIAL;
        }
        return total;
    }

}
