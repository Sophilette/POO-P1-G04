package proyecto.modelo;
import java.io.Serializable;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class Cliente extends Persona implements Serializable {

    // Variables de Instancia
    private String direccion;
    private TipoCliente tipo; // Enum tipo de cliente
    public static final String NOM_ARCHIVO = "clientes.ser";

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

    public static ArrayList<String> obtenerNombres(){
        ArrayList<String> lstNombres = new ArrayList<>();
        ArrayList<Cliente> lstClientes = obtenerClientes();
        for (Cliente cliente : lstClientes) {
            lstNombres.add(cliente.getNombre());
        }
        return lstNombres;
    }

    @Override
    public String toString() {
        // Devuelve el nombre, que es lo que queremos ver en el Spinner.
        return this.getNombre();
    }

    /** Carga clientes desde archivo. Si no existe, devuelve lista vacía. */
    public static ArrayList<Cliente> cargarClientes(File directorio) {
        ArrayList<Cliente> lista = new ArrayList<>();
        try {
            File f = new File(directorio, NOM_ARCHIVO);
            if (f.exists()) {
                try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(f))) {
                    //noinspection unchecked
                    lista = (ArrayList<Cliente>) is.readObject();
                }
            }
        } catch (Exception e) {
            // no lances: para este avance, si algo falla, vuelve lista vacía
        }
        return lista;
    }

    /** Crea archivo con datos iniciales si aún no existe. */
    public static boolean crearDatosIniciales(File directorio) throws Exception {
        boolean guardado = false;
        File f = new File(directorio, NOM_ARCHIVO);
        if (!f.exists()) {
            ArrayList<Cliente> semilla = obtenerClientes(); // tus 4 clientes de ejemplo
            try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f))) {
                os.writeObject(semilla);
                guardado = true;
            } catch (IOException e) {
                throw new Exception(e.getMessage());
            }
        } else {
            guardado = true;
        }
        return guardado;
    }

    /** Guarda la lista completa en el archivo. */
    public static boolean guardarLista(File directorio, ArrayList<Cliente> lista) throws Exception {
        boolean guardado = false;
        File f = new File(directorio, NOM_ARCHIVO);
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f))) {
            os.writeObject(lista);
            guardado = true;
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        }
        return guardado;
    }
  
}
