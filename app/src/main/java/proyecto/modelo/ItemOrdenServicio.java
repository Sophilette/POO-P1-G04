package proyecto.modelo;

public class ItemOrdenServicio {

    // Variables de instancia
    private Servicio servicio;
    private int cantidad; // Cantidad solicitada para el servicio

    // Constructor
    public ItemOrdenServicio(Servicio servicio, int cantidad) {
        this.servicio = servicio;
        this.cantidad = cantidad;
    }

    // Getters
    public Servicio getServicio() {
        return servicio;
    }

    public int getCantidad() {
        return cantidad;
    }

    // Método setter para cantidad de servicio
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    // Calcular valor total del servicio por la cantidad solicitada
    public double calcularSubtotal() {
        return servicio.getPrecioActual() * cantidad;
    }

}
