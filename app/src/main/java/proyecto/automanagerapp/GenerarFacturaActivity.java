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
import com.example.automanager.R;
import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import proyecto.modelo.Cliente;
import proyecto.modelo.Factura;
import proyecto.modelo.ItemOrdenServicio;
import proyecto.modelo.OrdenServicio;

public class GenerarFacturaActivity extends AppCompatActivity {

    // Declaración de las vistas de la UI
    private Spinner spinnerEmpresa, spinnerMes;
    private EditText etAno;
    private Button btnConsultarServicios, btnGuardarFactura;
    private LinearLayout layoutDetalle;
    private TextView tvDetalleServicios, tvTotalPagar;
    private ImageButton btnRetroceder;

    // Lista para guardar las órdenes que coinciden con la búsqueda, para luego crear la factura
    private ArrayList<OrdenServicio> ordenesParaFacturar = new ArrayList<>();
    private Cliente empresaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generarfactura);

        // Enlazar las vistas del XML con las variables Java
        spinnerEmpresa = findViewById(R.id.spinnerEmpresa);
        spinnerMes = findViewById(R.id.spinnerMes);
        etAno = findViewById(R.id.etAno);
        btnConsultarServicios = findViewById(R.id.btnConsultarServicios);
        layoutDetalle = findViewById(R.id.layoutDetalle);
        tvDetalleServicios = findViewById(R.id.tvDetalleServicios);
        tvTotalPagar = findViewById(R.id.tvTotalPagar);
        btnGuardarFactura = findViewById(R.id.btnGuardarFactura);
        btnRetroceder = findViewById(R.id.btnRetrocederGenerarFactura);

        // Configurar las listas desplegables (Spinners)
        cargarEmpresasSpinner();
        cargarMesesSpinner();

        // Asignar funciones a los botones
        btnConsultarServicios.setOnClickListener(v -> consultarServicios());
        btnGuardarFactura.setOnClickListener(v -> guardarFactura());
        btnRetroceder.setOnClickListener(v -> finish());
    }

    private void cargarEmpresasSpinner() {
        // Obtenemos todos los clientes de tu método estático y filtramos solo las empresas.
        // Uso la lógica de tu clase Factura: cliente.getTipo() == Cliente.TipoCliente.EMPRESARIAL
        List<Cliente> empresas = Cliente.obtenerClientes().stream()
                .filter(c -> c.getTipo() == Cliente.TipoCliente.EMPRESARIAL)
                .collect(Collectors.toList());

        // Un ArrayAdapter convierte una lista de objetos en vistas para el Spinner.
        ArrayAdapter<Cliente> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, empresas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEmpresa.setAdapter(adapter);
    }

    private void cargarMesesSpinner() {
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, meses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMes.setAdapter(adapter);

        // Ponemos el año y mes actual por defecto para comodidad del usuario.
        etAno.setText(String.valueOf(LocalDate.now().getYear()));
        spinnerMes.setSelection(LocalDate.now().getMonthValue() - 1);
    }

    private void consultarServicios() {
        // Obtenemos los datos seleccionados por el usuario
        empresaSeleccionada = (Cliente) spinnerEmpresa.getSelectedItem();
        int ano = Integer.parseInt(etAno.getText().toString());
        // El mes del Spinner va de 0 a 11. LocalDate va de 1 a 12. Sumamos 1.
        int mes = spinnerMes.getSelectedItemPosition() + 1;

        // Limpiamos la lista de resultados anteriores
        ordenesParaFacturar.clear();

        // Llamamos a tu método estático para obtener todas las órdenes
        ArrayList<OrdenServicio> todasLasOrdenes = OrdenServicio.obtenerOrdenes();

        // Filtramos las órdenes que pertenecen a la empresa y período seleccionados
        for (OrdenServicio orden : todasLasOrdenes) {
            if (orden.getCliente().equals(empresaSeleccionada) &&
                    orden.getFecha().getYear() == ano &&
                    orden.getFecha().getMonthValue() == mes) {
                ordenesParaFacturar.add(orden);
            }
        }

        // Si no encontramos ninguna orden, mostramos un mensaje y ocultamos el detalle.
        if (ordenesParaFacturar.isEmpty()) {
            Toast.makeText(this, "No se encontraron servicios para esta empresa en el período seleccionado.", Toast.LENGTH_LONG).show();
            layoutDetalle.setVisibility(View.GONE);
            return;
        }

        // Si encontramos órdenes, las procesamos para mostrarlas
        double subtotal = 0;
        StringBuilder detalleTexto = new StringBuilder();

        // Recorremos las órdenes encontradas
        for (OrdenServicio orden : ordenesParaFacturar) {
            detalleTexto.append("Orden del ").append(orden.getFecha().toString()).append(":\n");
            // Recorremos los items de cada orden
            for (ItemOrdenServicio item : orden.getItems()) {
                detalleTexto.append(String.format(Locale.getDefault(),
                        "- %s (x%d): $%.2f\n",
                        item.getServicio().getNombre(),
                        item.getCantidad(),
                        item.calcularSubtotal()
                ));
            }
            subtotal += orden.calcularTotal();
            detalleTexto.append("\n");
        }

        // Mostramos el detalle en el TextView
        tvDetalleServicios.setText(detalleTexto.toString());

        // Calculamos y mostramos el total final
        double totalFinal = subtotal + 50.00; // El cargo fijo de $50 para empresas
        tvTotalPagar.setText(String.format(Locale.getDefault(),
                "Subtotal Servicios: $%.2f\nCargo Fijo: $50.00\nTotal a pagar: $%.2f",
                subtotal, totalFinal));

        // Hacemos visible la sección de detalles
        layoutDetalle.setVisibility(View.VISIBLE);
    }

    private void guardarFactura() {
        // Validamos que se haya hecho una consulta primero
        if (empresaSeleccionada == null || ordenesParaFacturar.isEmpty()) {
            Toast.makeText(this, "Debe consultar los servicios antes de guardar.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Creamos un nuevo objeto Factura
        Factura nuevaFactura = new Factura(empresaSeleccionada, LocalDate.now());
        // Agregamos todas las órdenes encontradas a la factura
        for (OrdenServicio orden : ordenesParaFacturar) {
            nuevaFactura.agregarOrden(orden);
        }

        // "Guardamos" la factura usando nuestro método estático
        Factura.guardarFactura(nuevaFactura);

        Toast.makeText(this, "Factura guardada exitosamente.", Toast.LENGTH_LONG).show();

        // Cerramos esta pantalla para volver al listado
        finish();
    }
}
