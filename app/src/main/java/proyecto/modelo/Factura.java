package proyecto.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Factura implements Serializable {

    // Variables de instancia 
    private Cliente cliente;
    private LocalDate fechaEmision;
    private String periodo;
    private ArrayList<OrdenServicio> ordenes; // Lista de ordenes
    private static final double CARGO_FIJO_EMPRESARIAL = 50.0; // Cargo fijo para clientes de tipo empresarial

    // Constructor
    public Factura(Cliente cliente, LocalDate fechaEmision, String periodo) {
        this.cliente = cliente;
        this.fechaEmision = fechaEmision;
        this.periodo = periodo;
        this.ordenes = new ArrayList<>();
    }

    // Getters
    public Cliente getCliente() {
        return cliente;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public String getPeriodo() { return periodo; }

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

    // Esta será nuestra "base de datos" en memoria para las facturas.
    // Al ser 'static', esta lista será la misma para toda la aplicación.
    private static ArrayList<Factura> facturasGuardadas = new ArrayList<>();

    /**
     * "Guarda" una nueva factura añadiéndola a nuestra lista estática.
     * @param factura La factura generada que queremos guardar.
     */
    public static void guardarFactura(Factura factura) {
        facturasGuardadas.add(factura);
    }

    /**
     * Devuelve todas las facturas que hemos "guardado".
     * @return La lista de facturas generadas.
     */
    public static ArrayList<Factura> obtenerFacturasGuardadas() {
        // Comprobamos si la lista ya ha sido inicializada.
        if (facturasGuardadas.isEmpty()) {

            ArrayList<OrdenServicio> todasLasOrdenes = OrdenServicio.obtenerOrdenes();
            ArrayList<Cliente> todosLosClientes = Cliente.obtenerClientes();

            for (Cliente clienteActual : todosLosClientes) {
                if (clienteActual.getTipo() == Cliente.TipoCliente.EMPRESARIAL) {

                    // Lista temporal para las órdenes de este cliente
                    ArrayList<OrdenServicio> ordenesDelCliente = new ArrayList<>();

                    // Buscamos todas las órdenes que pertenecen a ESTA empresa
                    for (OrdenServicio orden : todasLasOrdenes) {
                        if (orden.getCliente().getId().equals(clienteActual.getId())) {
                            ordenesDelCliente.add(orden);
                        }
                    }

                    // Si encontramos órdenes para este cliente, procedemos a crear la factura
                    if (!ordenesDelCliente.isEmpty()) {

                        // --- LÓGICA DEL PERÍODO MEJORADA ---
                        // 1. Tomamos la fecha de la PRIMERA orden encontrada para este cliente.
                        LocalDate fechaReferencia = ordenesDelCliente.get(0).getFecha();

                        // 2. Formateamos esa fecha para obtener el período en formato "Mes Año".
                        //    Locale("es", "ES") es para que salga "Agosto" en vez de "August".
                        DateTimeFormatter formatterPeriodo = DateTimeFormatter.ofPattern("MMMM yyyy", new Locale("es", "ES"));
                        String periodoFactura = fechaReferencia.format(formatterPeriodo);

                        // 3. Capitalizamos la primera letra del mes.
                        periodoFactura = periodoFactura.substring(0, 1).toUpperCase() + periodoFactura.substring(1);

                        // 4. Creamos la factura pasándole el período que acabamos de calcular.

                        Factura nuevaFactura = new Factura(clienteActual, LocalDate.now(), periodoFactura);

                        // 5. Añadimos todas las órdenes encontradas a esta factura.
                        for(OrdenServicio orden : ordenesDelCliente){
                            nuevaFactura.agregarOrden(orden);
                        }

                        // 6. Finalmente, guardamos la factura completa.
                        facturasGuardadas.add(nuevaFactura);
                    }
                }
            }
        }
        return facturasGuardadas;
    }

}
