package proyecto.automanagerapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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
            Factura factura = Factura.obtenerFacturasGuardadas().get(position);
            mostrarDetalles(factura);
        } else {
            Toast.makeText(this, "Error al cargar la factura", Toast.LENGTH_SHORT).show();
            finish();
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
