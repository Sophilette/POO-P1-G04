package proyecto.automanagerapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.automanager.R;

import java.util.ArrayList;
import proyecto.modelo.*;

public class DetalleOrdenActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private ItemOrdenServicioAdapter itemAdapter;

    OrdenServicio orden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalleorden);
        mostrarDatos();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detalleorden), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void mostrarDatos(){
        Intent i = getIntent();
        orden = (OrdenServicio) i.getSerializableExtra("orden");
        TextView tvCliente = findViewById(R.id.tvCliente2);
        tvCliente.setText(orden.getCliente().getNombre());

        TextView tvFecha = findViewById(R.id.tvFecha2);
        tvFecha.setText(orden.getFecha().toString());

        TextView tvTipoVehiculo = findViewById(R.id.tvTipoVehiculo2);
        tvTipoVehiculo.setText(orden.getVehiculo().getTipo().toString());

        TextView tvPlaca = findViewById(R.id.tvPlaca2);
        tvPlaca.setText(orden.getVehiculo().getPlaca());

        TextView tvTotal = findViewById(R.id.tvTotal2);
        tvTotal.setText(String.format("%.2f",orden.calcularTotal()));

        // llenar el RecyclerView con los servicios de la orden
        recyclerView = findViewById(R.id.lstItemsRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemOrdenServicioAdapter(orden.getItems(), this);
        recyclerView.setAdapter(itemAdapter);
    }

    // Método llamado al dar click en boton retroceder
    public void retroceder(View view){
        finish();
    }

}
