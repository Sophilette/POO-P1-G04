package proyecto.automanagerapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import proyecto.modelo.ItemOrdenServicio;
import proyecto.modelo.OrdenServicio;
import com.example.automanager.R;

public class ReporteServiciosActivity extends AppCompatActivity{
    private EditText etAno;
    private Spinner spinnerMes;
    private Button btnConsultar;
    private RecyclerView recyclerViewReporte;
    private ReporteServiciosAdapter adapter;
    private View btnRetroceder;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_servicios);

        // Enlazar vistas
        etAno = findViewById(R.id.etReporteAno);
        spinnerMes = findViewById(R.id.spinnerReporteMes);
        btnConsultar = findViewById(R.id.btnReporteConsultar);
        recyclerViewReporte = findViewById(R.id.recyclerReporte);
        btnRetroceder = findViewById(R.id.btnRetrocederReporte);

        // Configurar RecyclerView
        recyclerViewReporte.setLayoutManager(new LinearLayoutManager(this));

        // Configurar Spinners y valores por defecto
        configurarFiltros();

        // Asignar listener al botón
        btnConsultar.setOnClickListener(v -> generarReporte());


        btnRetroceder.setOnClickListener(v -> {
                    // finish() cierra la actividad actual y regresa a la pantalla anterior.
                    finish();
                });
    }

    private void configurarFiltros() {
        // Llenar el spinner de meses
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        ArrayAdapter<String> mesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, meses);
        mesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMes.setAdapter(mesAdapter);

        // Poner año y mes actual por defecto
        etAno.setText(String.valueOf(LocalDate.now().getYear()));
        spinnerMes.setSelection(LocalDate.now().getMonthValue() - 1);
    }

    private void generarReporte() {
        // Validación simple
        if (etAno.getText().toString().isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese un año.", Toast.LENGTH_SHORT).show();
            return;
        }

        int ano = Integer.parseInt(etAno.getText().toString());
        // El mes del Spinner es 0-11, el de LocalDate es 1-12. Sumamos 1.
        int mes = spinnerMes.getSelectedItemPosition() + 1;

        // --- LÓGICA PRINCIPAL DEL CÁLCULO ---
        // Usamos un HashMap para almacenar el total por cada nombre de servicio.
        HashMap<String, Double> recaudadoPorServicio = new HashMap<>();

        // 1. Obtenemos todas las órdenes de tu método estático.
        ArrayList<OrdenServicio> todasLasOrdenes = OrdenServicio.obtenerOrdenes();

        // 2. Iteramos sobre cada orden.
        for (OrdenServicio orden : todasLasOrdenes) {
            // 3. Filtramos por el año y mes seleccionados.
            if (orden.getFecha().getYear() == ano && orden.getFecha().getMonthValue() == mes) {
                // 4. Si coincide, iteramos sobre sus items de servicio.
                for (ItemOrdenServicio item : orden.getItems()) {
                    String nombreServicio = item.getServicio().getNombre();
                    double subtotalItem = item.calcularSubtotal();

                    // 5. Acumulamos el monto en nuestro HashMap.
                    // getOrDefault busca el valor actual para el servicio, si no existe, empieza en 0.0.
                    recaudadoPorServicio.put(nombreServicio,
                            recaudadoPorServicio.getOrDefault(nombreServicio, 0.0) + subtotalItem);
                }
            }
        }

        if (recaudadoPorServicio.isEmpty()){
            Toast.makeText(this, "No se encontraron datos para este período.", Toast.LENGTH_SHORT).show();
        }

        // --- FIN DE LA LÓGICA DEL CÁLCULO ---

        // Convertimos el resultado del HashMap a una lista para el adaptador.
        ArrayList<Map.Entry<String, Double>> listaReporte = new ArrayList<>(recaudadoPorServicio.entrySet());

        // Opcional: Ordenar la lista por monto, de mayor a menor.
        Collections.sort(listaReporte, (entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Creamos y asignamos el adaptador al RecyclerView para mostrar los resultados.
        adapter = new ReporteServiciosAdapter(listaReporte);
        recyclerViewReporte.setAdapter(adapter);
    }
}
