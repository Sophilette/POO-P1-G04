package proyecto.automanagerapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.automanager.R;
import androidx.recyclerview.widget.RecyclerView;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import proyecto.modelo.Factura;

public class FacturaAdapter extends RecyclerView.Adapter<FacturaAdapter.FacturaViewHolder> {
    private ArrayList<Factura> listaFacturas;
    private Context context;

    public FacturaAdapter(ArrayList<Factura> listaFacturas, Context context) {
        this.listaFacturas = listaFacturas;
        this.context = context;
    }

    @NonNull
    @Override
    public FacturaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_factura, parent, false);
        return new FacturaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacturaViewHolder holder, int position) {
        Factura factura = listaFacturas.get(position);
        holder.bind(factura, position);
    }

    @Override
    public int getItemCount() {
        return listaFacturas.size();
    }

    public class FacturaViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmpresa, tvFecha, tvTotal;
        Button btnDetalles;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        public FacturaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmpresa = itemView.findViewById(R.id.tvFacturaEmpresa);
            tvFecha = itemView.findViewById(R.id.tvFacturaFecha);
            tvTotal = itemView.findViewById(R.id.tvFacturaTotal);
            btnDetalles = itemView.findViewById(R.id.btnDetallesFactura);
        }

        void bind(Factura factura, final int position) {
            tvEmpresa.setText(factura.getCliente().getNombre());
            tvFecha.setText(factura.getFechaEmision().format(formatter));
            tvTotal.setText(String.format(Locale.getDefault(), "$%.2f", factura.calcularTotal()));

            // Manejar el clic del botón "Más Detalles"
            btnDetalles.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetalleFacturaActivity.class);
                // Pasamos la posición de la factura en la lista.
                // La siguiente pantalla usará esta posición para obtener la factura correcta.
                intent.putExtra("FACTURA_POSITION", position);
                context.startActivity(intent);
            });
        }
    }
}
