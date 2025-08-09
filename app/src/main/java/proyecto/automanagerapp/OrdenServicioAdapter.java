package proyecto.automanagerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.automanager.R;

import java.util.ArrayList;
import proyecto.modelo.*;

public class OrdenServicioAdapter extends RecyclerView.Adapter<OrdenServicioAdapter.OrdenServicioViewHolder>{
    private ArrayList<OrdenServicio> ordenes;
    private Context context;

    public OrdenServicioAdapter(ArrayList<OrdenServicio> ordenes, Context context) {
        this.ordenes = ordenes;
        this.context = context;
    }

    @Override
    public OrdenServicioAdapter.OrdenServicioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ordenservicio, parent, false);
        return new OrdenServicioAdapter.OrdenServicioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrdenServicioAdapter.OrdenServicioViewHolder holder, int position) {
        OrdenServicio orden = ordenes.get(position);
        holder.clienteTextView.setText(orden.getCliente().getNombre());
        holder.fechaTextView.setText(String.valueOf(orden.getFecha()));
        holder.placaTextView.setText(orden.getVehiculo().getPlaca());
        holder.totalTextView.setText(String.format("$%.2f",orden.calcularTotal()));
    }

    @Override
    public int getItemCount() {
        return ordenes.size();
    }

    public class OrdenServicioViewHolder extends RecyclerView.ViewHolder {
        public TextView clienteTextView;
        public TextView fechaTextView;
        public TextView placaTextView;
        public TextView totalTextView;
        public Button detallesButton;

        public OrdenServicioViewHolder(View itemView) {
            super(itemView);
            clienteTextView = itemView.findViewById(R.id.tvCliente2);
            fechaTextView = itemView.findViewById(R.id.tvFecha2);
            placaTextView = itemView.findViewById(R.id.tvPlaca2);
            totalTextView = itemView.findViewById(R.id.tvTotal2);
            detallesButton = itemView.findViewById(R.id.btndetalles);}

    }
}
