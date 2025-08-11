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

import proyecto.modelo.Tecnico;

/**
 * Pantalla "Administrar Técnicos".
 * - Muestra un listado de técnicos
 * - Botón "Agregar" abre AggTecnicoActivity por Intent.
 * - Incluye botón "Eliminar" en cada ítem que lanza confirmación
 */
public class AdminTecnicosActivity extends AppCompatActivity {

    private RecyclerView rvTecnicos;
    private  TecnicoAdapter tecnicoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admintecnicos);

        rvTecnicos = findViewById(R.id.lstTecnicosRv);
        rvTecnicos.setLayoutManager(new LinearLayoutManager(this));
        tecnicoAdapter = new TecnicoAdapter(Tecnico.obtenerTecnicos()); // lista temporal
        rvTecnicos.setAdapter(tecnicoAdapter);

        Log.d("Administrar Tecnicos","onCreate()");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.admintecnicos), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
    }

    /** Vuelve a la pantalla anterior. */
    public void retroceder(View view){
        finish();
    }

    /** Abre el formulario para crear técnico. */
    public void agregarTecnico(View view){
        startActivity(new Intent(this, AggTecnicoActivity.class));
    }
}
