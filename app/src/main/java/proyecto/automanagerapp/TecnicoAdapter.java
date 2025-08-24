package proyecto.automanagerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.automanager.R;

import java.util.ArrayList;

import proyecto.modelo.Tecnico;

/**
 * Convierte cada Tecnico en una card (item_tecnico) para el RecyclerView.
 * - Muestra ID, Nombre, Teléfono y Especialidad.
 * - Botón "Eliminar" abre un diálogo de confirmación.
 */
public class TecnicoAdapter extends RecyclerView.Adapter<TecnicoAdapter.VH>{

    private final ArrayList<Tecnico> data;
    private final OnDeleteListener listener;

    public interface OnDeleteListener {
        void onDeleteRequest(Tecnico t);
    }
    public TecnicoAdapter(ArrayList<Tecnico> data, OnDeleteListener listener) {
        this.data = (data != null) ? new ArrayList<>(data) : new ArrayList<>();
        this.listener = listener;
    }

    public void setData(ArrayList<Tecnico> nuevos) {
        this.data.clear();
        if (nuevos != null) this.data.addAll(nuevos);
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tecnico, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH h, int position) {
        Tecnico t = data.get(position);
        h.tvId.setText(t.getId());
        h.tvNombre.setText(t.getNombre());
        h.tvTelefono.setText(t.getTelefono());
        h.tvEspecialidad.setText(t.getEspecialidad());

        // Confirmación de eliminación
        h.btnEliminar.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteRequest(t);
        });
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvId, tvNombre, tvTelefono, tvEspecialidad;
        Button btnEliminar;

        VH(View itemView) {
            super(itemView);
            tvId          = itemView.findViewById(R.id.tvTecId);
            tvNombre      = itemView.findViewById(R.id.tvTecNombre);
            tvTelefono    = itemView.findViewById(R.id.tvTecTelefono);
            tvEspecialidad= itemView.findViewById(R.id.tvTecEspecialidad);
            btnEliminar   = itemView.findViewById(R.id.btnEliminarTec);
        }
    }
}
