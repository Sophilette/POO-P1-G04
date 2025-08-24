package proyecto.automanagerapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.os.Environment;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.automanager.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import proyecto.modelo.Tecnico;

/**
 * Pantalla "Administrar Técnicos".
 * - Lista desde archivo (tecnicos.ser).
 * - Agregar abre AggTecnicoActivity.
 * - Eliminar: pide confirmación y persiste cambios.
 */
public class AdminTecnicosActivity extends AppCompatActivity implements TecnicoAdapter.OnDeleteListener {

    private RecyclerView rvTecnicos;
    private  TecnicoAdapter tecnicoAdapter;

    private File getSafeDir() {
        File dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (dir == null) dir = getFilesDir();
        return dir;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admintecnicos);

        // Semilla si el archivo no existe
        try { Tecnico.crearDatosIniciales(getSafeDir()); }
        catch (Exception e) { Log.e("AdminTecnicos","Semilla: "+e.getMessage()); }

        rvTecnicos = findViewById(R.id.lstTecnicosRv);
        rvTecnicos.setLayoutManager(new LinearLayoutManager(this));

        llenarLista();

        Log.d("Administrar Tecnicos","onCreate()");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.admintecnicos), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        llenarLista();
    }

    private void llenarLista() {
        ArrayList<Tecnico> lista = Tecnico.cargarTecnicos(getSafeDir());
        if (tecnicoAdapter == null) {
            tecnicoAdapter = new TecnicoAdapter(lista, this /* OnDeleteListener */);
            rvTecnicos.setAdapter(tecnicoAdapter);
        } else {
            tecnicoAdapter.setData(lista);
        }
    }

    /** Vuelve a la pantalla anterior. */
    public void retroceder(View view){
        finish();
    }

    /** Abre el formulario para crear técnico. */
    public void agregarTecnico(View view){
        startActivity(new Intent(this, AggTecnicoActivity.class));
    }

    // ===== Callback del Adapter para eliminar =====
    @Override
    public void onDeleteRequest(Tecnico t) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Eliminar técnico")
                .setMessage("¿Deseas eliminar a \"" + t.getNombre() + "\"?")
                .setPositiveButton("Sí", (d, w) -> eliminarPersistente(t)) // aquí SÍ se elimina
                .setNegativeButton("No", null)
                .create();

        dialog.setOnShowListener(dlg -> {
            int color = Color.parseColor("#808080");
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color);
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(color);
        });

        dialog.show();
    }

    private void eliminarPersistente(Tecnico t) {
        ArrayList<Tecnico> lista = Tecnico.cargarTecnicos(getSafeDir());
        // elimina por ID (case-insensitive)
        for (Iterator<Tecnico> it = lista.iterator(); it.hasNext();) {
            if (it.next().getId().equalsIgnoreCase(t.getId())) {
                it.remove();
                break;
            }
        }
        try {
            Tecnico.guardarLista(getSafeDir(), lista);
            Toast.makeText(this, "Técnico eliminado", Toast.LENGTH_SHORT).show();
            llenarLista();
        } catch (Exception e) {
            Toast.makeText(this, "Error al eliminar", Toast.LENGTH_LONG).show();
            Log.e("AdminTecnicos","eliminarPersistente: "+e.getMessage(), e);
        }
    }
}
