package proyecto.automanagerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.automanager.R;

import java.util.ArrayList;

import proyecto.modelo.Cliente;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.VH>{

    private final ArrayList<Cliente> data; // lista temporal a mostrar

    public ClienteAdapter(ArrayList<Cliente> data) {
        this.data = data;

    }


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cliente, parent, false);
        return new VH(v);
    }

    /**
     * Enlaza los datos de un Cliente con los TextViews de la fila.
     * Esta operación se llama cada vez que una fila aparece en pantalla.
     */
    @Override
    public void onBindViewHolder(VH h, int position) {
        Cliente c = data.get(position);
        h.tvId.setText(c.getId());        // Muestra el ID en el primer campo
        h.tvNombre.setText(c.getNombre());      // Muestra el nombre en el segundo campo
        h.tvTelefono.setText(c.getTelefono());    // Muestra el teléfono en el tercer campo
        // No se configura botón de detalles/editar: en este avance no corresponde.
    }

    /** Cantidad de elementos que la lista va a renderizar. */
    @Override
    public int getItemCount() { return data.size(); }

    /**
     * ViewHolder:
     * - Guarda referencias a los TextViews de una fila para no buscarlos repetidamente.
     * - Mejora el rendimiento del RecyclerView.
     */
    static class VH extends RecyclerView.ViewHolder {
        TextView tvId, tvNombre, tvTelefono;
        VH(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvNombre   = itemView.findViewById(R.id.tvNombre);
            tvTelefono   = itemView.findViewById(R.id.tvTelefono);
        }
    }
}

