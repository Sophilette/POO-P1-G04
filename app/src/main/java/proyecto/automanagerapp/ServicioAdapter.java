package proyecto.automanagerapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.automanager.R;

import java.util.ArrayList;

import proyecto.modelo.Servicio;

public class ServicioAdapter extends RecyclerView.Adapter<ServicioAdapter.ServicioViewHolder>{

    private ArrayList<Servicio> servicios;
    private Context context;

    public ServicioAdapter(ArrayList<Servicio> servicios, Context context) {
        this.servicios = servicios;
        this.context = context;
    }

    @Override
    public ServicioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servicio, parent, false);
        return new ServicioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServicioViewHolder holder, int position) {
        Servicio servicio = servicios.get(position);
        holder.codigoTextView.setText(servicio.getCodigo());
        holder.nombreTextView.setText(servicio.getNombre());
        holder.precioTextView.setText(String.valueOf(servicio.getPrecioActual()));
        holder.editarButton.setOnClickListener(v -> {
            // Iniciamos la nueva actividad, pasando información sobre el empleado
            Intent intent = new Intent(context, EditarServicioActivity.class);
            intent.putExtra("servicio", servicio);

            Log.d("Administrar Servicios","Al dar click en editar");
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return servicios.size();
    }

    public class ServicioViewHolder extends RecyclerView.ViewHolder {
        public TextView codigoTextView;
        public TextView nombreTextView;
        public TextView precioTextView;
        public Button editarButton;

        public ServicioViewHolder(View itemView) {
            super(itemView);
            codigoTextView = itemView.findViewById(R.id.tvCodigo);
            nombreTextView = itemView.findViewById(R.id.tvNombre);
            precioTextView = itemView.findViewById(R.id.tvPrecio);
            editarButton = itemView.findViewById(R.id.btnEditar);}

    }


}
