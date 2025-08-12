package proyecto.automanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import com.example.automanager.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

import proyecto.modelo.Factura;

public class AdminFacturasActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFacturas;
    private FacturaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminfacturas);

        recyclerViewFacturas = findViewById(R.id.recyclerFacturas);
        recyclerViewFacturas.setLayoutManager(new LinearLayoutManager(this));

        // Botón para ir a la pantalla de generar una nueva factura
        Button btnIrAGenerar = findViewById(R.id.btnIrAGenerarFactura);
        btnIrAGenerar.setOnClickListener(v -> {
            Intent intent = new Intent(AdminFacturasActivity.this, GenerarFacturaActivity.class);
            startActivity(intent);
        });

        // Botón para retroceder
        ImageButton btnRetroceder = findViewById(R.id.retrocederButtonFacturas);
        btnRetroceder.setOnClickListener(v -> finish());
    }

    /**
     * onResume se llama cada vez que la actividad se vuelve visible.
     * Esto asegura que la lista se actualice después de generar una nueva factura.
     */
    @Override
    protected void onResume() {
        super.onResume();
        cargarFacturas();
    }

    private void cargarFacturas() {
        // Obtenemos la lista de facturas desde nuestro "repositorio" estático en el modelo
        ArrayList<Factura> facturasGuardadas = Factura.obtenerFacturasGuardadas();

        // Opcional: Ordenar la lista para que las más nuevas aparezcan primero
        Collections.sort(facturasGuardadas, (f1, f2) -> f2.getFechaEmision().compareTo(f1.getFechaEmision()));

        // Creamos el adaptador y lo asignamos al RecyclerView
        adapter = new FacturaAdapter(facturasGuardadas, this);
        recyclerViewFacturas.setAdapter(adapter);
    }
}
