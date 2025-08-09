package proyecto.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Vehiculo implements Serializable {

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

    public static ArrayList<Vehiculo> obtenerVehiculos(){
        ArrayList<Vehiculo> lstVehiculos = new ArrayList<>();

        lstVehiculos.add(new Vehiculo("GTL-456", TipoVehiculo.AUTOMOVIL));
        lstVehiculos.add(new Vehiculo("TPX-888", TipoVehiculo.BUS));

        return lstVehiculos;

    }

    public static ArrayList<String> obtenerTipos(){
        ArrayList<String> lstTipos = new ArrayList<>();
        lstTipos.add("Automovil");
        lstTipos.add("Motocicleta");
        lstTipos.add("Bus");
        return lstTipos;
    }
  
}
