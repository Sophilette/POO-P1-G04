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

import proyecto.modelo.Proveedor;

/**
 * Formulario de "Nuevo Proveedor".
 * - Valida campos obligatorios y formato de teléfono.
 * - Verifica ID único contra lo cargado del archivo.
 * - Agrega a la lista y la guarda en proveedores.ser.
 */
public class AggProveedorActivity extends AppCompatActivity {

    private EditText etId, etNombre, etTelefono, etDescripcion;

    private File getSafeDir() {
        File dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (dir == null) dir = getFilesDir();
        return dir;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aggproveedor);

        etId         = findViewById(R.id.etIdProveedor);
        etNombre     = findViewById(R.id.etNombreProveedor);
        etTelefono   = findViewById(R.id.etTelefonoProveedor);
        etDescripcion= findViewById(R.id.etDescripcionProveedor);

        Log.d("AggProveedor","onCreate()");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.aggproveedor), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
    }
    public void cancelar(View view){ finish(); }

    /** Solo cierra el form por ahora */
    public void guardarProveedor(View view){
        String id   = etId.getText().toString().trim();
        String nom  = etNombre.getText().toString().trim();
        String tel  = etTelefono.getText().toString().trim();
        String des  = etDescripcion.getText().toString().trim();

        if (!validarObligatorios(id, nom, tel, des)) return;
        if (!validarTelefono(tel)) { etTelefono.setError("Teléfono inválido (7–10 dígitos)"); return; }

        ArrayList<Proveedor> lista = Proveedor.cargarProveedores(getSafeDir());
        for (Proveedor p : lista) {
            if (p.getId().equalsIgnoreCase(id)) {
                etId.setError("Ya existe un proveedor con ese ID");
                Toast.makeText(this, "ID duplicado", Toast.LENGTH_LONG).show();
                return;
            }
        }

        lista.add(new Proveedor(id, nom, tel, des));
        try {
            Proveedor.guardarLista(getSafeDir(), lista);
            Toast.makeText(this, "Proveedor guardado", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Log.e("AggProveedor","Error al guardar: "+e.getMessage(), e);
            Toast.makeText(this, "Error al guardar", Toast.LENGTH_LONG).show();
        }
    }

    /* ===== Validaciones ===== */

    private boolean validarObligatorios(String id, String nom, String tel, String des) {
        boolean ok = true;
        if (TextUtils.isEmpty(id))   { etId.setError("Requerido"); ok = false; }
        if (TextUtils.isEmpty(nom))  { etNombre.setError("Requerido"); ok = false; }
        if (TextUtils.isEmpty(tel))  { etTelefono.setError("Requerido"); ok = false; }
        if (TextUtils.isEmpty(des))  { etDescripcion.setError("Requerido"); ok = false; }
        return ok;
    }

    private boolean validarTelefono(String tel) { return tel.matches("^\\d{7,10}$"); }
}
