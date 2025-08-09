package proyecto.modelo;

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
    public static ArrayList<OrdenServicio> obtenerOrdenes(){
        ArrayList<OrdenServicio> lstOrdenes = new ArrayList<>();
        ArrayList<Cliente> clientes = Cliente.obtenerClientes();
        ArrayList<Vehiculo> vehiculos = Vehiculo.obtenerVehiculos();
        ArrayList<Servicio> servicios = Servicio.obtenerServicios();
        ArrayList<Tecnico> tecnicos = Tecnico.obtenerTecnicos();

        OrdenServicio os1 = new OrdenServicio(clientes.get(0), vehiculos.get(0), tecnicos.get(0), LocalDate.of(2025, 4, 4));
        os1.agregarItem(new ItemOrdenServicio(servicios.get(0), 1));
        os1.agregarItem(new ItemOrdenServicio(servicios.get(1), 1));
        lstOrdenes.add(os1);

        OrdenServicio os2 = new OrdenServicio(clientes.get(2), vehiculos.get(1), tecnicos.get(0), LocalDate.of(2025, 4, 4));
        os1.agregarItem(new ItemOrdenServicio(servicios.get(2), 1));
        os1.agregarItem(new ItemOrdenServicio(servicios.get(3), 1));
        lstOrdenes.add(os2);

        OrdenServicio os3 = new OrdenServicio(clientes.get(1), new Vehiculo("LUC-789", Vehiculo.TipoVehiculo.AUTOMOVIL), tecnicos.get(1), LocalDate.of(2025, 4, 4));
        os1.agregarItem(new ItemOrdenServicio(servicios.get(4), 1));
        os1.agregarItem(new ItemOrdenServicio(servicios.get(5), 1));
        lstOrdenes.add(os3);

        OrdenServicio os4 = new OrdenServicio(clientes.get(1), new Vehiculo("TRN-999", Vehiculo.TipoVehiculo.BUS), tecnicos.get(1), LocalDate.of(2025, 4, 4));
        os1.agregarItem(new ItemOrdenServicio(servicios.get(5), 1));
        os1.agregarItem(new ItemOrdenServicio(servicios.get(0), 1));
        lstOrdenes.add(os4);

        return lstOrdenes;
    }

    @Override
    public String toString() {
        return  cliente.getNombre() +  " - " + fecha +  " - " + vehiculo.getPlaca() + " - " + calcularTotal();
    }

    public enum EstadoOrden {
    
    PENDIENTE, EN_PROCESO, COMPLETADA, CANCELADA

}

}