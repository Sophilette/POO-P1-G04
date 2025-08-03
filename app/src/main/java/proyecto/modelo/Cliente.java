package automanager.modelo;

public class Cliente extends Persona {

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
  
}
