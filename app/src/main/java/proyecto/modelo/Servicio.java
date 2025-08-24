package proyecto.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Servicio implements Serializable {

    // Variables de instancia
    private String codigo;
    private String nombre;
    private double precioActual;
    private ArrayList<PrecioHistorial> historialPrecios; // Lista de historial de precios

    public static final String nomArchivo = "servicios.ser";

    // Constructor
    public Servicio(String codigo, String nombre, double precioActual) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioActual = precioActual;
        this.historialPrecios = new ArrayList<>();
    }

    // Getters
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecioActual() {
        return precioActual;
    }

    public ArrayList<PrecioHistorial> getHistorialPrecios() {
        return historialPrecios;
    }

    // Método setter para actualizar el precio
    public void setPrecioActual(double nuevoPrecio) {
        this.precioActual = nuevoPrecio;
    }

    // Agregar precio a la lista del historial de precios del servicio
    public void registrarPrecioEnHistorial(double precio) {
        PrecioHistorial nuevoRegistro = new PrecioHistorial(LocalDate.now(), precio);
        historialPrecios.add(nuevoRegistro);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Servicio servicio = (Servicio) o;
        return Objects.equals(codigo, servicio.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigo);
    }

    public static ArrayList<Servicio> obtenerServicios(){
        ArrayList<Servicio> lstServicios = new ArrayList<>();

        lstServicios.add(new Servicio("S001", "Cambio de aceite", 20.00));
        lstServicios.add(new Servicio("S002", "Cambio de filtro", 15.00));
        lstServicios.add(new Servicio("S003", "Alineación", 30.00));
        lstServicios.add(new Servicio("S004", "Balanceo", 25.00));
        lstServicios.add(new Servicio("S005", "Revisión de frenos", 35.00));
        lstServicios.add(new Servicio("S006", "Diagnóstico electrónico", 40.00));

        return lstServicios;
    }
    @Override
    public String toString() {
        return  codigo +  " - " + nombre + " - " + precioActual;
    }

    public static ArrayList<String> obtenerCodigos(){
        ArrayList<String> lstCodigos = new ArrayList<>();
        ArrayList<Servicio> lstServicios = obtenerServicios();
        for (Servicio servicio : lstServicios) {
            lstCodigos.add(servicio.getCodigo());
        }
        return lstCodigos;
    }

    // Leer el archivo donde se encuentran los datos de los servicios
    /*public static ArrayList<Servicio> cargarServicios(File directorio) {
        ArrayList<Servicio> lstServicios = new ArrayList<>();
        File f = new File(directorio, nomArchivo);
        // Escribir la lista serializable
        if(f.exists()){
            try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))){
                lstServicios = (ArrayList<Servicio>) ois.readObject();
            }catch(Exception e){
                new Exception(e.getMessage());
            }
        }
        return lstServicios;

    }*/

    // Leer el archivo donde se encuentran los datos de los servicios
    public static ArrayList<Servicio> cargarServicios(File directorio) {
        ArrayList<Servicio> lstServicios = new ArrayList<>();
        File f = new File(directorio, nomArchivo);

        if (f.exists() && f.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                lstServicios = (ArrayList<Servicio>) ois.readObject();
            } catch (Exception e) {
                // Manejar la excepción, por ejemplo, reiniciando los datos
                e.printStackTrace();
                // Si hay un error, cargar los datos iniciales para no dejar la lista vacía
                lstServicios = obtenerServicios();
                try {
                    // Y guardarlos para solucionar el problema de persistencia
                    guardarServicios(lstServicios, directorio);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            // Si el archivo no existe o está vacío, crea la lista con los datos iniciales
            lstServicios = obtenerServicios();
            try {
                // Y guarda la lista en el archivo
                guardarServicios(lstServicios, directorio);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lstServicios;
    }

    /**
     * @param directorio directorio en android donde se guardará el archivo
     * @return true si se pudo crear el archivo o ya existe.
     */

    public static boolean crearDatosIniciales(File directorio) throws Exception{
        ArrayList<Servicio> lstServicios = obtenerServicios();
        boolean guardado = false;

        /*lstServicios.add(new Servicio("S001", "Cambio de aceite", 20.00));
        lstServicios.add(new Servicio("S002", "Cambio de filtro", 15.00));
        lstServicios.add(new Servicio("S003", "Alineación", 30.00));
        lstServicios.add(new Servicio("S004", "Balanceo", 25.00));
        lstServicios.add(new Servicio("S005", "Revisión de frenos", 35.00));
        lstServicios.add(new Servicio("S006", "Diagnóstico electrónico", 40.00));*/

        File f = new File(directorio, nomArchivo);
        if(!f.exists()){
            try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))){
                oos.writeObject(lstServicios);
                guardado = true;
            }catch(IOException e){
                throw new Exception(e.getMessage());
            }
        }else guardado = true;
        return guardado;
    }

    public static boolean guardarServicios(ArrayList<Servicio> lstServicios, File directorio) throws Exception{
        boolean guardado = false;
        File f = new File(directorio, nomArchivo);
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))){
            oos.writeObject(lstServicios);
            guardado = true;
        }catch(IOException e){
            throw new Exception(e.getMessage());
        }
        return guardado;
    }

    
}


