package proyecto.automanagerapp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.automanager.R;

import java.io.File;
import java.util.ArrayList;

import proyecto.modelo.Cliente;

/**
 * Formulario de "Crear Cliente".
 * - Incluye un Spinner de tipo de cliente.
 * - Lee la lista actual del archivo
 * - Valida y verifica ID único
 * - Agrega y guarda la lista completa
 */
public class AggClienteActivity extends AppCompatActivity {

    // Lista de tipos para el Spinner (PERSONAL/EMPRESARIAL)
    private Spinner spTipo;
    private EditText etId, etNombre, etTelefono, etDireccion;
    private File getSafeDir() {
        File dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (dir == null) dir = getFilesDir();
        return dir;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aggcliente);

        etId        = findViewById(R.id.etIdCliente);
        etNombre    = findViewById(R.id.etNombreCliente);
        etTelefono  = findViewById(R.id.etTelefonoCliente);
        etDireccion = findViewById(R.id.etDireccionCliente);
        spTipo      = findViewById(R.id.spTipoCliente);

        // Spinner con el enum
        ArrayAdapter<Cliente.TipoCliente> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, Cliente.TipoCliente.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipo.setAdapter(adapter);

        // Insets
        Log.d("Crear Cliente","Formulario inicializado");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.aggcliente), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
    }

    public void cancelar(View view){
        finish();
    }

    public void guardarCliente(View view){
        String id   = etId.getText().toString().trim();
        String nom  = etNombre.getText().toString().trim();
        String tel  = etTelefono.getText().toString().trim();
        String dir  = etDireccion.getText().toString().trim();
        Cliente.TipoCliente tipo = (Cliente.TipoCliente) spTipo.getSelectedItem();

        // Validaciones básicas
        if (!validarObligatorios(id, nom, tel, dir)) return;
        if (!validarTelefono(tel)) return;

        // 1) Leer lista actual
        ArrayList<Cliente> lista = Cliente.cargarClientes(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
        // 2) No permitir IDs duplicados (case-insensitive)
        for (Cliente c : lista) {
            if (c.getId().equalsIgnoreCase(id)) {
                etId.setError("Ya existe un cliente con ese ID");
                Toast.makeText(this, "ID duplicado", Toast.LENGTH_LONG).show();
                return;
            }
        }
        // 3) Agregar nuevo y guardar
        lista.add(new Cliente(id, nom, tel, dir, tipo));
        try {
            Cliente.guardarLista(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), lista);
            Toast.makeText(this, "Cliente guardado", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Log.e("AggCliente", "Error al guardar: " + e.getMessage(), e);
            Toast.makeText(this, "Error al guardar", Toast.LENGTH_LONG).show();
        }
    }

    private boolean validarObligatorios(String id, String nom, String tel, String dir) {
        boolean ok = true;
        if (TextUtils.isEmpty(id))  { etId.setError("Requerido"); ok = false; }
        if (TextUtils.isEmpty(nom)) { etNombre.setError("Requerido"); ok = false; }
        if (TextUtils.isEmpty(tel)) { etTelefono.setError("Requerido"); ok = false; }
        if (TextUtils.isEmpty(dir)) { etDireccion.setError("Requerido"); ok = false; }
        return ok;
    }

    private boolean validarTelefono(String tel) {
        return tel.matches("^\\d{7,10}$");
    }
}


