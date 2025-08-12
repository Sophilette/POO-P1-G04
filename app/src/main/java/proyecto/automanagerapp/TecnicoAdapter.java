package proyecto.automanagerapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.automanager.R;

import java.util.ArrayList;

import proyecto.modelo.Tecnico;

/**
 * Convierte cada Tecnico en una card (item_tecnico) para el RecyclerView.
 * - Muestra ID, Nombre, Teléfono y Especialidad.
 * - Botón "Eliminar" abre un diálogo de confirmación.
 *   (En este avance, NO elimina realmente: solo muestra un mensaje).
 */
public class TecnicoAdapter extends RecyclerView.Adapter<TecnicoAdapter.VH>{

    private final ArrayList<Tecnico> data;

    public TecnicoAdapter(ArrayList<Tecnico> data) {
        this.data = data;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tecnico, parent, /* attachToRoot = */ false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH h, int position) {
        Tecnico t = data.get(position);
        h.tvId.setText(t.getId());
        h.tvNombre.setText(t.getNombre());
        h.tvTelefono.setText(t.getTelefono());
        h.tvEspecialidad.setText(t.getEspecialidad());

        // Confirmación de eliminación (sin eliminar realmente)
        h.btnEliminar.setOnClickListener(v -> {
            AlertDialog.Builder b = new AlertDialog.Builder(v.getContext());
            b.setTitle("Eliminar técnico")
                    .setMessage("¿Deseas eliminar a " + t.getNombre() + "?")
                    .setPositiveButton("No", (dialog, which) -> dialog.dismiss())
                    .setNegativeButton("Sí", (dialog, which) -> {
                        // Aquí NO eliminamos. Solo mostramos aviso.
                        Toast.makeText(v.getContext(),
                                "El técnico ha sido eliminado correctamente",
                                Toast.LENGTH_SHORT).show();
                    });

            AlertDialog dialog = b.create();
            dialog.show();

            int colorBotones = Color.parseColor("#808080");

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(colorBotones);
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(colorBotones);
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
