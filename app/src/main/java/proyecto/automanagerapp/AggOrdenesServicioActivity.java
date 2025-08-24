package proyecto.automanagerapp;

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
import proyecto.modelo.Cliente;
import proyecto.modelo.ItemOrdenServicio;
import proyecto.modelo.OrdenServicio;
import proyecto.modelo.Servicio;
import proyecto.modelo.Tecnico;
import proyecto.modelo.Vehiculo;

public class AggOrdenesServicioActivity extends AppCompatActivity {

    private ArrayList<ItemOrdenServicio> lstItems = new ArrayList<>();
    private RecyclerView recyclerView;
    private ItemOrdenServicioAdapter itemOrdenServicioAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aggorden);
        llenarSpinners();

        Log.d("Administrar Ordenes de Servicios", "en onCreate");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.aggorden), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Método que se usa para llenar los spinners
    private void llenarSpinners() {

        // llenar el spinner de nombres de clientes
        Spinner spClienteNombre = findViewById(R.id.spClienteNombre);

        //Cargar los clientes desde el archivo para obtener los nombres
        ArrayList<Cliente> lstClientes = new ArrayList<>();
        ArrayList<String> lstNombres = new ArrayList<>();
        try {
            lstClientes = Cliente.cargarClientes(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            for (Cliente cliente : lstClientes) {
                lstNombres.add(cliente.getNombre());
            }
            ;
        } catch (Exception e) {
            lstNombres = Cliente.obtenerNombres();
            Log.d("AutoManager", "Error al cargar datos" + e.getMessage());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lstNombres) {
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
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lstTipos) {
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
        ArrayList<Servicio> lstServicios = new ArrayList<>();
        ArrayList<String> lstCodigos = new ArrayList<>();

        try {
            lstServicios = Servicio.cargarServicios(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            for (Servicio servicio : lstServicios) {
                lstCodigos.add(servicio.getCodigo());
            }
            ;
        } catch (Exception e) {
            lstCodigos = Servicio.obtenerCodigos();
            Log.d("AutoManager", "Error al cargar datos" + e.getMessage());
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lstCodigos) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view).setTextColor(Color.BLACK); // cambia el color del texto
                return view;
            }
        };
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCodigoServicio.setAdapter(adapter2);

        // Llenar los spinners de fecha
        Spinner spAnio = findViewById(R.id.spAnio);
        Spinner spMes = findViewById(R.id.spMes);
        Spinner spDia = findViewById(R.id.spDia);

        // Llenar Spinner de años (ej. de 2020 a 2030)
        ArrayList<String> anios = new ArrayList<>();
        int anioActual = LocalDate.now().getYear();
        for (int i = anioActual - 5; i <= anioActual + 5; i++) {
            anios.add(String.valueOf(i));
        }

        ArrayAdapter<String> anioAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, anios){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view).setTextColor(Color.BLACK); // cambia el color del texto
                return view;
            }
        };
        anioAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAnio.setAdapter(anioAdapter);
        spAnio.setSelection(anios.indexOf(String.valueOf(anioActual)));

        // Llenar Spinner de meses
        ArrayList<String> meses = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            meses.add(String.format("%02d", i));
        }
        ArrayAdapter<String> mesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, meses){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view).setTextColor(Color.BLACK); // cambia el color del texto
                return view;
            }
        };
        mesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMes.setAdapter(mesAdapter);
        spMes.setSelection(LocalDate.now().getMonthValue() - 1);

        // Llenar Spinner de días
        ArrayList<String> dias = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            dias.add(String.format("%02d", i));
        }
        ArrayAdapter<String> diaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dias){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view).setTextColor(Color.BLACK); // cambia el color del texto
                return view;
            }
        };
        diaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDia.setAdapter(diaAdapter);
        spDia.setSelection(LocalDate.now().getDayOfMonth() - 1);
    }

    // Agregar los items de la orden de servicios
    public void agregarItem(View view) {
        // recolectar datos del formulario
        Spinner spCodigoServicio = findViewById(R.id.spCodigoServicio);
        EditText etCantidadServicio = findViewById(R.id.cantidadServicio);
        String cantidadStr = etCantidadServicio.getText().toString().trim();

        // Validar la cantidad
        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) {
                Toast.makeText(this, "No se pudo agregar el servicio: Cantidad inválida", Toast.LENGTH_LONG).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "No se pudo agregar el servicio: Cantidad inválida", Toast.LENGTH_LONG).show();
            return;
        }

        // Seleccionar en el archivo el servicio correspondiente al codigo seleccionado
        Servicio servicio = null;
        ArrayList<Servicio> lstServicios = new ArrayList<>();
        try {
            lstServicios = Servicio.cargarServicios(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            for (Servicio s : lstServicios) {
                if (s.getCodigo().equals(spCodigoServicio.getSelectedItem().toString())) {
                    servicio = s;
                    break;
                }
            }
        } catch (Exception e) {
            Log.d("AutoManager", "Error al cargar datos" + e.getMessage());
        }

        if (servicio == null) {
            Toast.makeText(this, "Error: Servicio no encontrado.", Toast.LENGTH_SHORT).show();
            return;
        }

        ItemOrdenServicio item = new ItemOrdenServicio(servicio, cantidad);
        Log.d("AutoManager", item.toString());

        // Agregar el item a la lista
        lstItems.add(item);

        // Actualizar el RecyclerView
        recyclerView = findViewById(R.id.lstServiciosAgg);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Llenar el recyclerView con los items de la orden de servicios
        if (lstItems != null) {
            itemOrdenServicioAdapter = new ItemOrdenServicioAdapter(lstItems, this);
            recyclerView.setAdapter(itemOrdenServicioAdapter);
            // Establecer el total en TextView total
            TextView tvTotal = findViewById(R.id.total);
            double total = 0.00;
            for (ItemOrdenServicio nuevoItem : lstItems) {
                total += nuevoItem.calcularSubtotal();
            }
            tvTotal.setText(String.format("$%.2f", total));
        }
    }


    //Guardar nueva orden de servicio en el archivo
    public void guardar(View view) {
        // Obtener los datos del formulario
        Spinner spClienteNombre = findViewById(R.id.spClienteNombre);
        Spinner spAnio = findViewById(R.id.spAnio);
        Spinner spMes = findViewById(R.id.spMes);
        Spinner spDia = findViewById(R.id.spDia);
        Spinner spTipoVehiculo = findViewById(R.id.spTipoVehiculo);
        EditText etPlacaLetras = findViewById(R.id.etPlacaLetras);
        EditText etPlacaNumeros = findViewById(R.id.etPlacaNumeros);

        // Obtener los valores de la placa y limpiar espacios
        String letras = etPlacaLetras.getText().toString().trim();
        String numeros = etPlacaNumeros.getText().toString().trim();

        // Usar banderas para rastrear el estado de las validaciones
        boolean placaInvalida = false;
        boolean itemsInvalidos = false;

        // 1. Validar la placa
        if (letras.isEmpty() || numeros.isEmpty() || letras.length() != 3 || numeros.length() != 3) {
            placaInvalida = true;
        }

        // 2. Validar que la lista de items no esté vacía
        if (lstItems == null || lstItems.isEmpty()) {
            itemsInvalidos = true;
        }

        // 3. Mostrar el mensaje de error correspondiente según el estado de las banderas
        if (placaInvalida && itemsInvalidos) {
            // Si ambos son inválidos, mostrar el mensaje genérico
            Toast.makeText(this, "No se pudo guardar la Orden de Servicios: Datos inválidos.", Toast.LENGTH_LONG).show();
            return;
        } else if (placaInvalida) {
            // Si solo la placa es inválida, mostrar su mensaje específico
            Toast.makeText(this, "No se pudo guardar la Orden de Servicios: Placa inválida.", Toast.LENGTH_LONG).show();
            return;
        } else if (itemsInvalidos) {
            // Si solo los ítems son inválidos, mostrar su mensaje específico
            Toast.makeText(this, "No se pudo guardar la Orden de Servicios: No se ha seleccionado Items de Servicios.", Toast.LENGTH_LONG).show();
            return;
        }

        // Si los datos son válidos, continuar con el proceso de guardado
        String nombreCliente = spClienteNombre.getSelectedItem().toString();

        // Obtener cliente en el archivo a partir del nombre
        ArrayList<Cliente> lstClientes = new ArrayList<>();
        Cliente cliente = null;
        try {
            lstClientes = Cliente.cargarClientes(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            for (Cliente c : lstClientes) {
                if (c.getNombre().equals(nombreCliente)) {
                    cliente = c;
                }
            }
        } catch (Exception e) {
            lstClientes = Cliente.obtenerClientes();
            for (Cliente c : lstClientes) {
                if (c.getNombre().equals(nombreCliente)) {
                    cliente = c;
                }
            }
            Log.d("AutoManager", "Error al cargar datos" + e.getMessage());
        }



        // Construir la fecha a partir de los Spinners
        String anio = spAnio.getSelectedItem().toString();
        String mes = spMes.getSelectedItem().toString();
        String dia = spDia.getSelectedItem().toString();
        LocalDate fecha = LocalDate.parse(anio + "-" + mes + "-" + dia);

        //Datos para crear instancia de carro
        Vehiculo.TipoVehiculo tipoVehiculo = spTipoVehiculo.getSelectedItem().toString().equals("Automovil") ? Vehiculo.TipoVehiculo.AUTOMOVIL : spTipoVehiculo.getSelectedItem().toString().equals("Motocicleta") ? Vehiculo.TipoVehiculo.MOTOCICLETA : Vehiculo.TipoVehiculo.BUS;
        String placa = letras.toUpperCase() + "-" + numeros;
        Vehiculo vehiculo = new Vehiculo(placa, tipoVehiculo);

        // Asignar tecnico de manera aleatoria
        Random random = new Random();
        // Cargar los técnicos desde el archivo
        ArrayList<Tecnico> lstTecnicos = new ArrayList<>();
        int indiceAleatorio = 0;
        Tecnico tecnicoAsignado = null;

        try {
            lstTecnicos = Tecnico.cargarTecnicos(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            indiceAleatorio = random.nextInt(lstTecnicos.size());
            tecnicoAsignado = lstTecnicos.get(indiceAleatorio);
        } catch (Exception e) {
            indiceAleatorio = random.nextInt(Tecnico.obtenerTecnicos().size());
            tecnicoAsignado = Tecnico.obtenerTecnicos().get(indiceAleatorio);
            Log.d("AutoManager", "Error al cargar datos" + e.getMessage());
        }

        // Crear la orden de servicio
        OrdenServicio ordenServicio = new OrdenServicio(cliente, vehiculo, tecnicoAsignado, fecha);
        Log.d("AutoManager", ordenServicio.toString());

        // Agregar los items de la orden de servicios
        if (lstItems != null) {
            for (ItemOrdenServicio item : lstItems) {
                ordenServicio.agregarItem(item);
            }
        }

        // Guardar la orden de servicio en el archivo
        ArrayList<OrdenServicio> lstOrdenes = new ArrayList<>();
        try {
            lstOrdenes = OrdenServicio.cargarOrdenes(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            lstOrdenes.add(ordenServicio);
            Log.d("Automanager", "Agregado a la lista");
            //Guardar la lista en el archivo
            OrdenServicio.guardarOrdenes(lstOrdenes, this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            Toast.makeText(getApplicationContext(), "Orden de Servicio agregada", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d("AutoManager", "Error al guardar datos al crear" + e.getMessage());
        }
        finish();
    }
}
