package proyecto.automanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.automanager.R;

import proyecto.modelo.Proveedor;

/**
 * Pantalla "Administrar Proveedores".
 * - Lista proveedores en un RecyclerView.
 * - Botón "Agregar" abre el formulario (AggProveedorActivity) vía Intent.
 */
public class AdminProveedoresActivity extends AppCompatActivity {

    private RecyclerView rvProveedores;
    private ProveedorAdapter proveedorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adminproveedores);

        // Configura lista
        rvProveedores = findViewById(R.id.lstProveedoresRv);
        rvProveedores.setLayoutManager(new LinearLayoutManager(this));
        proveedorAdapter = new ProveedorAdapter(Proveedor.obtenerProveedores());
        rvProveedores.setAdapter(proveedorAdapter);

        Log.d("Administrar Proveedores","onCreate()");

        // Ajuste de insets (status/navigation)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.adminproveedores), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
    }

    /** Botón volver. */
    public void retroceder(View view){ finish(); }

    /** Abre formulario para “Nuevo Proveedor”. */
    public void agregarProveedor(View view){
        startActivity(new Intent(this, AggProveedorActivity.class));
    }
}
