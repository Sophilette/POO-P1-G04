package proyecto.automanagerapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.automanager.R;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import proyecto.modelo.Cliente;
import proyecto.modelo.Factura;
import proyecto.modelo.ItemOrdenServicio;
import proyecto.modelo.OrdenServicio;
import proyecto.modelo.Servicio;

public class GenerarFacturaActivity extends AppCompatActivity {

    // Declaración de las vistas de la UI
    private Spinner spinnerEmpresa, spinnerMes;
    private EditText etAno;
    private Button btnGenerar; // Nuestro único botón principal
    private LinearLayout layoutDetalle;
    private TextView tvDetalleServicios, tvTotalPagar;
    private ImageButton btnRetroceder;

    // Variables para guardar el estado
    private ArrayList<OrdenServicio> ordenesParaFacturar = new ArrayList<>();
    private Cliente empresaSeleccionada;
    private boolean detalleVisible = false; // ¡Variable clave para saber qué hacer!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generarfactura); // Usa el layout corregido

        // Enlazar vistas
        spinnerEmpresa = findViewById(R.id.spinnerEmpresa);
        spinnerMes = findViewById(R.id.spinnerMes);
        etAno = findViewById(R.id.etAno);
        btnGenerar = findViewById(R.id.btnGenerar);
        layoutDetalle = findViewById(R.id.layoutDetalle);
        tvDetalleServicios = findViewById(R.id.tvDetalleServicios);
        tvTotalPagar = findViewById(R.id.tvTotalPagar);
        btnRetroceder = findViewById(R.id.btnRetrocederGenerarFactura);

        // Configurar las listas desplegables
        cargarEmpresasSpinner();
        cargarMesesSpinner();

        // Asignar funciones a los botones
        btnRetroceder.setOnClickListener(v -> finish());
        btnGenerar.setOnClickListener(v -> onGenerarClick());
    }

    private void onGenerarClick() {
        if (detalleVisible) {
            // 2da vez: Si el detalle ya es visible, guardamos la factura.
            guardarFactura();
        } else {
            // 1ra vez: Si el detalle está oculto, consultamos los servicios.
            consultarServicios();
        }
    }

    private void consultarServicios() {
        // Validar entradas
        empresaSeleccionada = (Cliente) spinnerEmpresa.getSelectedItem();
        if (empresaSeleccionada == null || etAno.getText().toString().isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        int ano = Integer.parseInt(etAno.getText().toString());
        int mes = spinnerMes.getSelectedItemPosition() + 1;

        // Limpiar resultados anteriores
        ordenesParaFacturar.clear();
        ArrayList<OrdenServicio> todasLasOrdenes = OrdenServicio.obtenerOrdenes();

        // Filtrar órdenes por empresa y período
        for (OrdenServicio orden : todasLasOrdenes) {
            if (orden.getCliente().getId().equals(empresaSeleccionada.getId()) &&
                    orden.getFecha().getYear() == ano &&
                    orden.getFecha().getMonthValue() == mes) {
                ordenesParaFacturar.add(orden);
            }
        }

        // Si no se encuentran órdenes, mostrar mensaje
        if (ordenesParaFacturar.isEmpty()) {
            Toast.makeText(this, "No se encontraron servicios para esta empresa en el período seleccionado.", Toast.LENGTH_LONG).show();
            layoutDetalle.setVisibility(View.GONE);
            detalleVisible = false;
            return;
        }

        // Si se encuentran órdenes, calcular y mostrar el detalle
        Map<String, Integer> conteoServicios = new HashMap<>();
        Map<String, Servicio> servicioMap = new HashMap<>();
        double subtotal = 0;

        for (OrdenServicio orden : ordenesParaFacturar) {
            for (ItemOrdenServicio item : orden.getItems()) {
                Servicio servicio = item.getServicio();
                conteoServicios.put(servicio.getNombre(), conteoServicios.getOrDefault(servicio.getNombre(), 0) + item.getCantidad());
                servicioMap.put(servicio.getNombre(), servicio);
            }
            subtotal += orden.calcularTotal();
        }

        StringBuilder detalleTexto = new StringBuilder();
        for (Map.Entry<String, Integer> entry : conteoServicios.entrySet()) {
            String nombreServicio = entry.getKey();
            int cantidad = entry.getValue();
            Servicio servicio = servicioMap.get(nombreServicio);
            double costoUnitario = servicio.getPrecioActual();
            double totalServicio = cantidad * costoUnitario;

            detalleTexto.append(String.format(Locale.getDefault(),
                    "Servicio: %s\nCantidad: %d\nCosto unitario: $%.2f\nTotal: $%.2f\n\n",
                    nombreServicio, cantidad, costoUnitario, totalServicio));
        }

        tvDetalleServicios.setText(detalleTexto.toString());

        double totalFinal = subtotal + 50.00; // Cargo fijo para empresas
        tvTotalPagar.setText(String.format(Locale.getDefault(), "Total a pagar: $%.2f", totalFinal));

        // Mostrar la sección de detalles y actualizar el estado
        layoutDetalle.setVisibility(View.VISIBLE);
        detalleVisible = true;
    }

    private void guardarFactura() {
        if (empresaSeleccionada == null || ordenesParaFacturar.isEmpty()) {
            // Esta validación es por si acaso, pero no debería ocurrir con el nuevo flujo.
            Toast.makeText(this, "No hay servicios para facturar.", Toast.LENGTH_SHORT).show();
            return;
        }

        String periodo = spinnerMes.getSelectedItem().toString() + " " + etAno.getText().toString();
        Factura nuevaFactura = new Factura(empresaSeleccionada, LocalDate.now(), periodo);
        for (OrdenServicio orden : ordenesParaFacturar) {
            nuevaFactura.agregarOrden(orden);
        }

        Factura.guardarFactura(nuevaFactura);
        Toast.makeText(this, "Factura generada exitosamente.", Toast.LENGTH_LONG).show();
        finish(); // Cierra la pantalla y vuelve a la lista de facturas
    }

    // Estos métodos no necesitan cambios.
    private void cargarEmpresasSpinner() {
        ArrayList<Cliente> todosLosClientes = Cliente.obtenerClientes();
        ArrayList<Cliente> soloEmpresas = new ArrayList<>();
        for (Cliente cliente : todosLosClientes) {
            if (cliente.getTipo() == Cliente.TipoCliente.EMPRESARIAL) {
                soloEmpresas.add(cliente);
            }
        }
        ArrayAdapter<Cliente> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, soloEmpresas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEmpresa.setAdapter(adapter);
    }

    private void cargarMesesSpinner() {
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, meses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMes.setAdapter(adapter);

        etAno.setText(String.valueOf(LocalDate.now().getYear()));
        spinnerMes.setSelection(LocalDate.now().getMonthValue() - 1);
    }

}
