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
 * Formulario "Nuevo Técnico".
 * - Campos: ID, Nombre, Teléfono, Especialidad.
 * - Guardar/Cancelar.
 */
public class AggTecnicoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aggtecnico);

        Log.d("AggTecnico","onCreate()");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.aggtecnico), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
    }

    public void cancelar(View view){
        finish();
    }

    public void guardarTecnico(View view){
        finish();
    }
}

