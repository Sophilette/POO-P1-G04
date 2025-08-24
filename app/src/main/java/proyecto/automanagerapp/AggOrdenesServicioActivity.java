package proyecto.automanagerapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.automanager.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import proyecto.modelo.*;

public class AggOrdenesServicioActivity extends AppCompatActivity{

    private ArrayList<ItemOrdenServicio> lstItems = new ArrayList<>();
    private RecyclerView recyclerView;
    private ItemOrdenServicioAdapter itemOrdenServicioAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aggorden);
        llenarSpinners();

        Log.d("Administrar Ordenes de Servicios","en onCreate");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.aggorden), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void llenarSpinners(){
        // llenar el spinner de nombres de clientes
        Spinner spClienteNombre = findViewById(R.id.spClienteNombre);
        ArrayList<String> lstNombres = Cliente.obtenerNombres();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lstNombres){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view).setTextColor(Color.BLACK); // cambia el color del texto
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spClienteNombre.setAdapter(adapter);

        // llenar el spinner de tipos de vehiculos
        Spinner spTipoVehiculo = findViewById(R.id.spTipoVehiculo);
        ArrayList<String> lstTipos = Vehiculo.obtenerTipos();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lstTipos){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view).setTextColor(Color.BLACK); // cambia el color del texto
                return view;
            }
        };
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoVehiculo.setAdapter(adapter1);

        // llenar el spinner de codigos de servicios
        Spinner spCodigoServicio = findViewById(R.id.spCodigoServicio);
        //Cargar los servicios desde el archivo para obtener los codigo
        ArrayList<Servicio> lstServicios = Servicio.cargarServicios(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));

        ArrayList<String> lstCodigos = new ArrayList<>();
        for (Servicio servicio : lstServicios) {
            lstCodigos.add(servicio.getCodigo());
        };
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lstCodigos){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view).setTextColor(Color.BLACK); // cambia el color del texto
                return view;
            }
        };
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCodigoServicio.setAdapter(adapter2);

    }

    // Agregar los items de la orden de servicios
    public void agregarItem(View view){
        // recolectar datos del formulario
        Spinner spCodigoServicio = findViewById(R.id.spCodigoServicio);

        // Seleccionar en el archivo el servicio correspondiente al codigo seleccionado
        Servicio servicio = null;
        ArrayList<Servicio> lstServicios = Servicio.cargarServicios(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
        for (Servicio s : lstServicios) {
            if (s.getCodigo().equals(spCodigoServicio.getSelectedItem().toString())) {
                servicio = s;
            }
        }
        EditText etCantidadServicio = findViewById(R.id.cantidadServicio);
        int cantidad = Integer.parseInt(etCantidadServicio.getText().toString());
        ItemOrdenServicio item = new ItemOrdenServicio(servicio, cantidad);
        Log.d("AutoManager", item.toString());
        // Agregar el item a la lista
        lstItems.add(item);
        // Actualizar el RecyclerView

        recyclerView = findViewById(R.id.lstServiciosAgg);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (lstItems != null){
            itemOrdenServicioAdapter = new ItemOrdenServicioAdapter(lstItems, this);
            recyclerView.setAdapter(itemOrdenServicioAdapter);
            // Establecer el total en TextView total
            TextView tvTotal = findViewById(R.id.total);
            double total = 0.00;
            for (ItemOrdenServicio nuevoItem : lstItems) {
                total += nuevoItem.calcularSubtotal();
            }
            tvTotal.setText(String.valueOf(total));
            tvTotal.setText(String.format("$%.2f",total));
        }

    }


    //Guardar nueva orden de servicio en el archivo

    public void guardar(View view){
        // Obtener los datos del formulario
        Spinner spClienteNombre = findViewById(R.id.spClienteNombre); //obtener cliente a partir del nombre
        EditText etFecha = findViewById(R.id.fecha);
        Spinner spTipoVehiculo = findViewById(R.id.spTipoVehiculo);
        EditText etPlaca = findViewById(R.id.placa);

        String nombreCliente = spClienteNombre.getSelectedItem().toString();
        ArrayList<Cliente> lstClientes = Cliente.obtenerClientes();
        Cliente cliente = null;
        for (Cliente c : lstClientes) {
            if (c.getNombre().equals(nombreCliente)) {
                cliente = c;
            }
        }

        LocalDate fecha = etFecha.getText().toString().equals("") ? LocalDate.now() : LocalDate.parse(etFecha.getText().toString());

        //Datos para crear instancia de carro
        Vehiculo.TipoVehiculo tipoVehiculo = spTipoVehiculo.getSelectedItem().toString().equals("Automovil") ? Vehiculo.TipoVehiculo.AUTOMOVIL : spTipoVehiculo.getSelectedItem().toString().equals("Motocicleta") ? Vehiculo.TipoVehiculo.MOTOCICLETA : Vehiculo.TipoVehiculo.BUS;
        String placa = etPlaca.getText().toString().toUpperCase();
        Vehiculo vehiculo = new Vehiculo(placa, tipoVehiculo);

        // Asignar tecnico de manera aleatoria
        Random random = new Random();
        int indiceAleatorio = random.nextInt(Tecnico.obtenerTecnicos().size());
        Tecnico tecnicoAsignado = Tecnico.obtenerTecnicos().get(indiceAleatorio);

        // Crear la orden de servicio
        OrdenServicio ordenServicio = new OrdenServicio(cliente, vehiculo, tecnicoAsignado, fecha);
        Log.d("AutoManager", ordenServicio.toString());

        // Agregar los items de la orden de servicios
        if (lstItems != null){
            for (ItemOrdenServicio item : lstItems) {
                ordenServicio.agregarItem(item);}
        }



        ArrayList<OrdenServicio> lstOrdenes = new ArrayList<>();
        try{
            lstOrdenes=OrdenServicio.cargarOrdenes(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            lstOrdenes.add(ordenServicio);
            Log.d("Automanager","Agregado a la lista");
            //Guardar la lista en el archivo
            OrdenServicio.guardarOrdenes(lstOrdenes, this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            Toast.makeText(getApplicationContext(), "Orden de Servicio agregada", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.d("AutoManager","Error al guardar datos al crear"+e.getMessage());
        }
        finish();

    }



}
