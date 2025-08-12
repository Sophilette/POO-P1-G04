package proyecto.automanagerapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.automanager.R;

import java.util.ArrayList;
import proyecto.modelo.*;
public class ItemOrdenServicioAdapter extends RecyclerView.Adapter<ItemOrdenServicioAdapter.ItemOrdenServicioViewHolder>{
    private ArrayList<ItemOrdenServicio> items;
    private Context context;

    public ItemOrdenServicioAdapter(ArrayList<ItemOrdenServicio> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ItemOrdenServicioAdapter.ItemOrdenServicioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_itemordenservicio, parent, false);
        return new ItemOrdenServicioAdapter.ItemOrdenServicioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemOrdenServicioAdapter.ItemOrdenServicioViewHolder holder, int position) {
        ItemOrdenServicio item = items.get(position);
        holder.nombreTextView.setText(item.getServicio().getNombre());
        holder.cantidadTextView.setText(String.valueOf(item.getCantidad()));
        holder.subtotalTextView.setText(String.valueOf(item.calcularSubtotal()));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemOrdenServicioViewHolder extends RecyclerView.ViewHolder {
        public TextView nombreTextView;
        public TextView cantidadTextView;
        public TextView subtotalTextView;

        public ItemOrdenServicioViewHolder(View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.tvNombre);
            cantidadTextView = itemView.findViewById(R.id.tvCantidad);
            subtotalTextView = itemView.findViewById(R.id.tvSubTotal);
        }
    }


}

