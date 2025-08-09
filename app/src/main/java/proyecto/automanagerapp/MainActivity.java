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

import com.example.automanager.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Log.d("AutoManager","en onCreate");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.adminservicio), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //se llama al dar click en Administrar Servicios
    public void adminServicios(View view){
        Intent intent = new Intent(this, AdminServiciosActivity.class);
        Log.d("App","Al dar click en Administrar Servicios");
        this.startActivity(intent);
    }

    public void adminordenes(View view){
        Intent intent = new Intent(this, AdminOrdenServicioActivity.class);
        Log.d("App","Al dar click en Generar Orden de Servicios");
        this.startActivity(intent);
    }
}