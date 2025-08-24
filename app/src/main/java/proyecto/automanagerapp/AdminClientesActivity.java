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

import proyecto.modelo.Cliente;

/**
 * Pantalla "Administrar Clientes".
 * Administra Clientes con persistencia en archivo:
 * - Si no existe archivo, crea uno con datos semilla.
 * - Lista siempre leyendo desde el archivo.
 * - "Agregar" abre formulario; al volver, se refresca.
 */
public class AdminClientesActivity extends AppCompatActivity {

    // Vista de lista que mostrará los clientes
    private RecyclerView recyclerView;
    // Adaptador que convierte objetos Cliente en filas (cards) del RecyclerView
    private ClienteAdapter clienteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adminclientes);

        // 1) Sembrar datos iniciales si el archivo no existe aún
        try {
            Cliente.crearDatosIniciales(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
        } catch (Exception e) {
            Log.e("AdminClientes", "Error creando datos iniciales: " + e.getMessage());
        }

        // 2) Configurar lista
        recyclerView = findViewById(R.id.lstclientesRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        llenarLista();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.adminclientes), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
    }

    private File getSafeDir() {
        File dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (dir == null) dir = getFilesDir();
        return dir;
    }

    /** Siempre que vuelva a esta pantalla, relee del archivo y refresca. */
    @Override
    protected void onResume() {
        super.onResume();
        llenarLista();
    }
    /** Lee del archivo y refresca el RecyclerView. */
    private void llenarLista(){
        ArrayList<Cliente> lista;
        try{
            lista = Cliente.cargarClientes(getSafeDir());
        }catch (Exception e){
            lista = new ArrayList<>();
        }

        if (clienteAdapter == null) {
            clienteAdapter = new ClienteAdapter(lista);
            recyclerView.setAdapter(clienteAdapter);
        } else {
            clienteAdapter.setData(lista); // método nuevo en el adapter
        }
    }

    public void retroceder(View view){
        finish();
    }

    public void agregarCliente(View view){
        Intent intent = new Intent(this, AggClienteActivity.class);
        Log.d("Administrar Clientes","Abrir formulario de nuevo cliente");
        startActivity(intent);
    }

}
