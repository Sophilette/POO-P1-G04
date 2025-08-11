package proyecto.automanagerapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.automanager.R;

/**
 * Formulario de "Nuevo Proveedor".
 */
public class AggProveedorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aggproveedor);

        Log.d("AggProveedor","onCreate()");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.aggproveedor), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
    }
    public void cancelar(View view){ finish(); }

    /** Solo cierra el form por ahora */
    public void guardarProveedor(View view){ finish(); }
}
