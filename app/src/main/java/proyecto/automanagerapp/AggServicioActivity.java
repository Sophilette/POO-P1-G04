package proyecto.automanagerapp;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.automanager.R;

import java.util.ArrayList;

import proyecto.modelo.Servicio;

public class AggServicioActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aggservicio);

        Log.d("Administrar Servicios","en onCreate");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.aggcliente), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Método llamado al dar click en boton cancelar
    public void cancelar(View view){
        finish();
    }

    //Método para guardar el nuevo servicio
    // El usuario ingresa nombre y precio
    // se le asigna codigo autoincremental

    public void guardar(View view){
        // Obtener los datos del formulario
        EditText etNombreServicio = findViewById(R.id.etNombreServicio);
        EditText etPrecioServicio = findViewById(R.id.etPrecioServicio);
        String nombre = etNombreServicio.getText().toString();
        double precio = Double.parseDouble(etPrecioServicio.getText().toString().replace(',', '.'));

        ArrayList<Servicio> lstServicios = new ArrayList<>();
        try{
            lstServicios=Servicio.cargarServicios(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            int max = 0;
            for (Servicio servicio : lstServicios) {
                int numero = Integer.parseInt(servicio.getCodigo().substring(1));
                if (numero > max) {
                    max = numero;
                }

            }
            String codigo = "S" + String.format("%03d", max + 1);
            Servicio nuevoServicio = new Servicio(codigo, nombre, precio);
            Log.d("AutoManager", nuevoServicio.toString());
            lstServicios.add(nuevoServicio);
            Log.d("Automanager","Agregado a la lista");

            //Guardar la lista en el archivo
            Servicio.guardarServicios(lstServicios, this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            Toast.makeText(getApplicationContext(), "Servicio agregado", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.d("AutoManager","Error al guardar datos al crear"+e.getMessage());
        }
        finish();
    }



}
