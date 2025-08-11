package proyecto.automanagerapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import proyecto.modelo.OrdenServicio;
import proyecto.modelo.Tecnico;
import com.example.automanager.R;

public class ReporteTecnicosActivity extends AppCompatActivity{
    private EditText etAno;
    private Spinner spinnerMes;
    private Button btnConsultar;
    private RecyclerView recyclerViewReporte;
    private ReporteServiciosAdapter adapter; // ¡Reutilizamos el mismo adapter!
    private ImageButton btnRetroceder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_tecnicos); // Usamos el nuevo layout

        // Enlazar vistas
        etAno = findViewById(R.id.etReporteAno);
        spinnerMes = findViewById(R.id.spinnerReporteMes);
        btnConsultar = findViewById(R.id.btnReporteConsultar);
        recyclerViewReporte = findViewById(R.id.recyclerReporte);
        btnRetroceder = findViewById(R.id.btnRetrocederReporte);

        btnRetroceder.setOnClickListener(v -> finish());

        // Configurar RecyclerView
        recyclerViewReporte.setLayoutManager(new LinearLayoutManager(this));

        // Configurar Spinners (la misma lógica que antes)
        configurarFiltros();

        // Asignar listener al botón
        btnConsultar.setOnClickListener(v -> generarReporte());
    }

    private void configurarFiltros() {
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        ArrayAdapter<String> mesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, meses);
        mesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMes.setAdapter(mesAdapter);

        etAno.setText(String.valueOf(LocalDate.now().getYear()));
        spinnerMes.setSelection(LocalDate.now().getMonthValue() - 1);
    }

    private void generarReporte() {
        if (etAno.getText().toString().isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese un año.", Toast.LENGTH_SHORT).show();
            return;
        }

        int ano = Integer.parseInt(etAno.getText().toString());
        int mes = spinnerMes.getSelectedItemPosition() + 1;

        // --- LÓGICA DE CÁLCULO (AQUÍ ESTÁ EL CAMBIO) ---
        // Usamos un HashMap para almacenar el total por cada nombre de TÉCNICO.
        HashMap<String, Double> recaudadoPorTecnico = new HashMap<>();

        // 1. Obtenemos todas las órdenes.
        ArrayList<OrdenServicio> todasLasOrdenes = OrdenServicio.obtenerOrdenes();

        // 2. Iteramos sobre cada orden.
        for (OrdenServicio orden : todasLasOrdenes) {
            // 3. Filtramos por el año y mes seleccionados.
            if (orden.getFecha().getYear() == ano && orden.getFecha().getMonthValue() == mes) {
                // 4. Obtenemos el técnico de la orden.
                Tecnico tecnico = orden.getTecnico();
                // Verificamos que el técnico no sea nulo para evitar errores.
                if (tecnico != null) {
                    String nombreTecnico = tecnico.getNombre();
                    double totalOrden = orden.calcularTotal();

                    // 5. Acumulamos el monto en nuestro HashMap, usando el nombre del técnico como clave.
                    recaudadoPorTecnico.put(nombreTecnico,
                            recaudadoPorTecnico.getOrDefault(nombreTecnico, 0.0) + totalOrden);
                }
            }
        }

        if (recaudadoPorTecnico.isEmpty()){
            recyclerViewReporte.setAdapter(null); // Limpia la lista si no hay resultados
            Toast.makeText(this, "No se encontraron datos para este período.", Toast.LENGTH_SHORT).show();
            return;
        }

        // --- FIN DE LA LÓGICA DEL CÁLCULO ---

        // Convertimos el resultado del HashMap a una lista para el adaptador.
        ArrayList<Map.Entry<String, Double>> listaReporte = new ArrayList<>(recaudadoPorTecnico.entrySet());

        // Opcional: Ordenar por monto recaudado.
        Collections.sort(listaReporte, (entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // ¡Reutilizamos el mismo ReporteAdapter!
        adapter = new ReporteServiciosAdapter(listaReporte);
        recyclerViewReporte.setAdapter(adapter);
    }
}
