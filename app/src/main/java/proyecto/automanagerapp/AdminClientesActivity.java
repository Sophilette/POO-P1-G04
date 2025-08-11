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

import proyecto.modelo.Cliente;

/**
 * Pantalla "Administrar Clientes".
 * - Muestra una lista de clientes en un RecyclerView.
 * - Incluye un botón para abrir el formulario de creación usando Intent.
 * - No persiste datos: la fuente es una lista temporal suministrada por Cliente.obtenerClientes().
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
        llenarLista();

        Log.d("Administrar Clientes","onCreate() ejecutado");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.adminclientes), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Configura el RecyclerView:
     * - Busca la vista por ID.
     * - Define un LayoutManager vertical.
     * - Crea y asigna el adaptador con una lista temporal de clientes.
     */
    private void llenarLista(){
        recyclerView = findViewById(R.id.lstclientesRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fuente de datos temporal (no persistente). Cada llamada devuelve una lista de ejemplo.
        clienteAdapter = new ClienteAdapter(Cliente.obtenerClientes());
        recyclerView.setAdapter(clienteAdapter);
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
