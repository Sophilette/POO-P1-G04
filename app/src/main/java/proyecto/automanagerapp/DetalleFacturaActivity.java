package proyecto.automanagerapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Locale;
import java.util.ArrayList;
import com.example.automanager.R;
import proyecto.modelo.Factura;
import proyecto.modelo.ItemOrdenServicio;
import proyecto.modelo.OrdenServicio;

public class DetalleFacturaActivity extends AppCompatActivity {

    private TextView tvEmpresa, tvFecha, tvOrdenes, tvTotal;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallefactura);

        tvEmpresa = findViewById(R.id.tvDetalleEmpresa);
        tvFecha = findViewById(R.id.tvDetalleFecha);
        tvOrdenes = findViewById(R.id.tvDetalleOrdenes);
        tvTotal = findViewById(R.id.tvDetalleTotal);

        // Obtenemos la posición de la factura que nos pasó la pantalla anterior
        int position = getIntent().getIntExtra("FACTURA_POSITION", -1);

        if (position != -1) {
            // Usamos la posición para obtener la factura correcta de nuestra lista estática
            // 1. Cargamos la lista completa de facturas desde el archivo.
            ArrayList<Factura> facturasGuardadas = Factura.cargarFacturas(getApplicationContext());

            // 2. ¡CRÍTICO! Ordenamos la lista EXACTAMENTE IGUAL que en la pantalla anterior.
            //    Esto asegura que la 'posición' que recibimos apunte a la factura correcta.
            Collections.sort(facturasGuardadas, (f1, f2) -> f2.getFechaEmision().compareTo(f1.getFechaEmision()));

            // 3. Verificamos que la posición sea válida para la lista que acabamos de cargar.
            if (position < facturasGuardadas.size()) {
                // 4. Usamos la posición para obtener la factura correcta de la lista.
                Factura factura = facturasGuardadas.get(position);
                mostrarDetalles(factura);
            } else {
                Toast.makeText(this, "Error: La posición de la factura es inválida.", Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }

    private void mostrarDetalles(Factura factura) {
        tvEmpresa.setText("Empresa: " + factura.getCliente().getNombre());
        tvFecha.setText("Fecha de Emisión: " + factura.getFechaEmision().format(formatter));

        // Construimos el texto con los detalles de todas las órdenes incluidas
        StringBuilder detalleOrdenes = new StringBuilder();
        for (OrdenServicio orden : factura.getOrdenes()) {
            detalleOrdenes.append("Orden del ").append(orden.getFecha().format(formatter))
                    .append(" (Placa: ").append(orden.getVehiculo().getPlaca()).append(")\n");
            for (ItemOrdenServicio item : orden.getItems()) {
                detalleOrdenes.append(String.format(Locale.getDefault(),
                        "  - %s: $%.2f\n",
                        item.getServicio().getNombre(),
                        item.calcularSubtotal()
                ));
            }
            detalleOrdenes.append("\n");
        }

        tvOrdenes.setText(detalleOrdenes.toString());
        tvTotal.setText(String.format(Locale.getDefault(), "Total: $%.2f", factura.calcularTotal()));
    }
}
