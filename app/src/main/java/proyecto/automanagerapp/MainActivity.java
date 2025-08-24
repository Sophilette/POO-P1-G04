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

    //se llama al dar click en Generar Orden de Servicios
    public void adminordenes(View view){
        Intent intent = new Intent(this, AdminOrdenServicioActivity.class);
        Log.d("App","Al dar click en Generar Orden de Servicios");
        this.startActivity(intent);
    }

    public void adminClientes(View view){
        // Intent explícito para abrir la pantalla de administración de clientes
        startActivity(new Intent(this, proyecto.automanagerapp.AdminClientesActivity.class));
    }

    public void adminProveedores(View view){
        startActivity(new Intent(this, AdminProveedoresActivity.class));
    }

    public void adminTecnicos(View view){
        startActivity(new Intent(this, AdminTecnicosActivity.class));
    }

    public void adminFacturas(View view) {
        // Creamos la intención para abrir la pantalla de listado de facturas.
        Intent intent = new Intent(this, AdminFacturasActivity.class);
        Log.d("App","Al dar click en Generar Factura Empresarial");
        // Iniciamos la nueva actividad.
        this.startActivity(intent);
    }

    public void reportePorServicios(View view) {
        // Creamos la intención para abrir la pantalla del reporte.
        Intent intent = new Intent(this, ReporteServiciosActivity.class);
        Log.d("App","Al dar click en Reporte de Ingresos por Servicios");
        this.startActivity(intent);
    }

    public void reportePorTecnicos(View view) {
        Intent intent = new Intent(this, ReporteTecnicosActivity.class);
        Log.d("App","Al dar click en Reporte de Ingresos por Técnicos");
        this.startActivity(intent);
    }
}