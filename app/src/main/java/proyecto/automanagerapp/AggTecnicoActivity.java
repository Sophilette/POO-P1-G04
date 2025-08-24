package proyecto.automanagerapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.automanager.R;

import java.io.File;
import java.util.ArrayList;

import proyecto.modelo.Tecnico;

/**
 * Formulario "Nuevo Técnico".
 * - Campos: ID, Nombre, Teléfono, Especialidad.
 * - Guardar/Cancelar.
 * - Valida, verifica ID único, guarda en tecnicos.ser.
 */
public class AggTecnicoActivity extends AppCompatActivity {

    private EditText etId, etNombre, etTelefono, etEsp;

    private File getSafeDir() {
        File dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (dir == null) dir = getFilesDir();
        return dir;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aggtecnico);

        etId      = findViewById(R.id.etIdTecnico);
        etNombre  = findViewById(R.id.etNombreTecnico);
        etTelefono= findViewById(R.id.etTelefonoTecnico);
        etEsp     = findViewById(R.id.etEspecialidadTecnico);

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

    public void guardarTecnico(View view) {
        String id = etId.getText().toString().trim();
        String nom = etNombre.getText().toString().trim();
        String tel = etTelefono.getText().toString().trim();
        String esp = etEsp.getText().toString().trim();

        if (!validarObligatorios(id, nom, tel, esp)) return;
        if (!validarTelefono(tel)) {
            etTelefono.setError("Teléfono inválido (7–10 dígitos)");
            return;
        }

        ArrayList<Tecnico> lista = Tecnico.cargarTecnicos(getSafeDir());
        for (Tecnico t : lista) {
            if (t.getId().equalsIgnoreCase(id)) {
                etId.setError("Ya existe un técnico con ese ID");
                Toast.makeText(this, "ID duplicado", Toast.LENGTH_LONG).show();
                return;
            }
        }

        lista.add(new Tecnico(id, nom, tel, esp));
        try {
            Tecnico.guardarLista(getSafeDir(), lista);
            Toast.makeText(this, "Técnico guardado", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Log.e("AggTecnico", "Error al guardar: " + e.getMessage(), e);
            Toast.makeText(this, "Error al guardar", Toast.LENGTH_LONG).show();
        }
    }

        private boolean validarObligatorios(String id, String nom, String tel, String esp) {
            boolean ok = true;
            if (TextUtils.isEmpty(id))  { etId.setError("Requerido"); ok = false; }
            if (TextUtils.isEmpty(nom)) { etNombre.setError("Requerido"); ok = false; }
            if (TextUtils.isEmpty(tel)) { etTelefono.setError("Requerido"); ok = false; }
            if (TextUtils.isEmpty(esp)) { etEsp.setError("Requerido"); ok = false; }
            return ok;
        }
        private boolean validarTelefono(String tel) { return tel.matches("^\\d{7,10}$"); }
    }

