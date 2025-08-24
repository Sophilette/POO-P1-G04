package proyecto.automanagerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.automanager.R;

import java.util.ArrayList;

import proyecto.modelo.Proveedor;

/**
 * Mapea cada Proveedor a una fila (item_proveedor.xml) del RecyclerView.
 */
public class ProveedorAdapter extends RecyclerView.Adapter<ProveedorAdapter.VH>{

    private final ArrayList<Proveedor> data;

    public ProveedorAdapter(ArrayList<Proveedor> data) {
        this.data = (data != null) ? new ArrayList<>(data) : new ArrayList<>();
    }

    public void setData(ArrayList<Proveedor> nuevos) {
        this.data.clear();
        if (nuevos != null) this.data.addAll(nuevos);
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_proveedor, parent, /* attachToRoot = */ false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH h, int position) {
        Proveedor p = data.get(position);
        h.tvId.setText(p.getId());
        h.tvNombre.setText(p.getNombre());
        h.tvTelefono.setText(p.getTelefono());
        h.tvDescripcion.setText(p.getDescripcion());
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvId, tvNombre, tvTelefono, tvDescripcion;
        VH(View itemView) {
            super(itemView);
            tvId         = itemView.findViewById(R.id.tvProvId);
            tvNombre     = itemView.findViewById(R.id.tvProvNombre);
            tvTelefono   = itemView.findViewById(R.id.tvProvTelefono);
            tvDescripcion= itemView.findViewById(R.id.tvProvDescripcion);
        }
    }
}
