package proyecto.automanagerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import com.example.automanager.R;
public class ReporteServiciosAdapter extends RecyclerView.Adapter<ReporteServiciosAdapter.ReporteViewHolder> {
    private ArrayList<Map.Entry<String, Double>> dataList;

    public ReporteServiciosAdapter(ArrayList<Map.Entry<String, Double>> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ReporteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reporte_servicio, parent, false);
        return new ReporteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReporteViewHolder holder, int position) {
        Map.Entry<String, Double> entry = dataList.get(position);
        holder.bind(entry);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ReporteViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvMonto;

        public ReporteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvReporteNombreItem);
            tvMonto = itemView.findViewById(R.id.tvReporteMontoItem);
        }

        void bind(Map.Entry<String, Double> data) {
            tvNombre.setText(data.getKey());
            tvMonto.setText(String.format(Locale.getDefault(), "$%.2f", data.getValue()));
        }
    }
}
