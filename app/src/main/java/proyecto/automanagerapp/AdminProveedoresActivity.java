package proyecto.automanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.os.Environment;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.automanager.R;

import java.io.File;
import java.util.ArrayList;

import proyecto.modelo.Proveedor;

/**
 * Pantalla "Administrar Proveedores".
 * - Si no existe archivo, crea uno con datos semilla (proveedores.ser).
 * - Lista siempre leyendo desde el archivo.
 * - "Agregar" abre AggProveedorActivity; al volver, se refresca.
 */
public class AdminProveedoresActivity extends AppCompatActivity {

    private RecyclerView rvProveedores;
    private ProveedorAdapter proveedorAdapter;

    // Directorio seguro: externo privado o interno (fallback)
    private File getSafeDir() {
        File dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (dir == null) dir = getFilesDir();
        return dir;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adminproveedores);

        // Sembrar datos iniciales (una sola vez)
        try { Proveedor.crearDatosIniciales(getSafeDir()); }
        catch (Exception e) { Log.e("AdminProveedores","Semilla: "+e.getMessage()); }

        rvProveedores = findViewById(R.id.lstProveedoresRv);
        rvProveedores.setLayoutManager(new LinearLayoutManager(this));

        llenarLista();

        Log.d("Administrar Proveedores","onCreate()");

        // Ajuste de insets (status/navigation)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.adminproveedores), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        llenarLista(); // refresco al volver del formulario
    }

    /** Lee del archivo y refresca el RecyclerView. */
    private void llenarLista(){
        ArrayList<Proveedor> lista = Proveedor.cargarProveedores(getSafeDir());
        if (proveedorAdapter == null) {
            proveedorAdapter = new ProveedorAdapter(lista);
            rvProveedores.setAdapter(proveedorAdapter);
        } else {
            proveedorAdapter.setData(lista);
        }
    }

    /** Botón volver. */
    public void retroceder(View view){ finish(); }

    /** Abre formulario para “Nuevo Proveedor”. */
    public void agregarProveedor(View view){
        startActivity(new Intent(this, AggProveedorActivity.class));
    }
}
