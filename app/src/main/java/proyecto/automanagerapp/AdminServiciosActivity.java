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
import proyecto.modelo.Servicio;

import com.example.automanager.R;

public class AdminServiciosActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private ServicioAdapter servicioAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adminservicio);
        llenarLista();

        Log.d("Administrar Servicios","en onCreate");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.adminservicio), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Método que se usa para llenar el RecyclerView con los servicios
    private void llenarLista(){
        recyclerView = findViewById(R.id.lstServiciosRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));;
        servicioAdapter = new ServicioAdapter(Servicio.obtenerServicios(), this);
        recyclerView.setAdapter(servicioAdapter);
    }

    // Método llamado al dar click en boton retroceder
    public void retroceder(View view){
        finish();
    }

    //se llama al dar click en Agregar
    public void agregarServicio(View view){
        Intent intent = new Intent(this, AggServicioActivity.class);
        Log.d("App","Al dar click en Agregar");
        this.startActivity(intent);
    }
}
