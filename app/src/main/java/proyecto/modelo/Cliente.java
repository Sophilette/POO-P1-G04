package proyecto.modelo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Cliente extends Persona implements Serializable {

    // Variables de Instancia
    private String direccion;
    private TipoCliente tipo; // Enum tipo de cliente

    // Constructor
    public Cliente(String id, String nombre, String telefono, String direccion, TipoCliente tipo) {
        super(id, nombre, telefono);
        this.direccion = direccion;
        this.tipo = tipo;
    }

    // Getters
    public String getDireccion() {
        return direccion;
    }

    public TipoCliente getTipo() {
        return tipo;
    }

    // Setters
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo;
    }

    public enum TipoCliente {
    
    PERSONAL, EMPRESARIAL
    
    }

    public static ArrayList<Cliente> obtenerClientes(){
        ArrayList<Cliente> lstClientes = new ArrayList<>();

        lstClientes.add(new Cliente("C001", "Carlos Pérez", "0991112222", "Av. Quito", TipoCliente.PERSONAL));
        lstClientes.add(new Cliente("C002", "Lucía Gómez", "0993334444", "Av. América", TipoCliente.PERSONAL));
        lstClientes.add(new Cliente("C003", "Grupo HG S.A", "023456789", "Parque Industrial", TipoCliente.EMPRESARIAL));
        lstClientes.add(new Cliente("C004", "Transporte Express", "022345678", "Av. 10 de Agosto", TipoCliente.EMPRESARIAL));

        return lstClientes;

    }
  
}
