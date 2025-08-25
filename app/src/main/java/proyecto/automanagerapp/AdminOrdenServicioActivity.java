package proyecto.automanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import proyecto.modelo.*;

import com.example.automanager.R;

import java.util.ArrayList;

public class AdminOrdenServicioActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private OrdenServicioAdapter ordenServicioAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adminordenes);

        Log.d("Administrar Ordenes de Servicio","en onCreate");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.adminordenes), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Método que se usa para llenar el RecyclerView con las ordenes de servicio
    private void llenarLista(){
        recyclerView = findViewById(R.id.lstOrdenesRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));;
        // Configurar el adapter
        ArrayList<OrdenServicio> lstOrdenes = new ArrayList<>();
        try{
            lstOrdenes = OrdenServicio.cargarOrdenes(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            Log.d("AutoManager", "Datos leidos desde el archivo");
        }catch(Exception e){
            lstOrdenes = OrdenServicio.obtenerOrdenes();
            Log.d("AutoManager", "Error al cargar datos"+e.getMessage());
        }


        Log.d("AutoManager",lstOrdenes.toString()); //muestra la lista en el log

        ordenServicioAdapter = new OrdenServicioAdapter(lstOrdenes, this);
        recyclerView.setAdapter(ordenServicioAdapter);
    }

    @Override
    public void onResume(){
        super.onResume();
        llenarLista();
        Log.d("Administrar Ordenes de Servicio","En onResume"); //muestra la lista en el log
    }

    // Método llamado al dar click en boton retroceder
    public void retroceder(View view){
        finish();
    }

    //se llama al dar click en Agregar
    public void agregarOrdenServicio(View view){
        Intent intent = new Intent(this, AggOrdenesServicioActivity.class);

        Log.d("App","Al dar click en Agregar");
        this.startActivity(intent);
    }
}
