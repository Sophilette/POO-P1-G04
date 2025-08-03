package proyecto.modelo;

public class Vehiculo {

    // variables de instancia 
    private String placa;
    private TipoVehiculo tipo;

    // Constructor
    public Vehiculo(String placa, TipoVehiculo tipo) {
        this.placa = placa;
        this.tipo = tipo;
    }

    //Getters y setters
    public String getPlaca() {
        return placa;
    }

    public TipoVehiculo getTipo() {
        return tipo;
    }


    public void setTipo(TipoVehiculo tipo) {
        this.tipo = tipo;
    }

    public enum TipoVehiculo {
    
    AUTOMOVIL, MOTOCICLETA, BUS
}
  
}
