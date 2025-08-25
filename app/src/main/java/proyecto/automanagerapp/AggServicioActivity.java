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
        String nombre = etNombreServicio.getText().toString().trim();

        // Variable para el precio y validación inicial
        double precio = 0.0;
        boolean precioValido;
        try {
            String precioStr = etPrecioServicio.getText().toString().replace(',', '.');
            if (precioStr.isEmpty()) {
                precioValido = false;
            } else {
                precio = Double.parseDouble(precioStr);
                if (precio <= 0) {
                    precioValido = false;
                }else{
                    precioValido=true;
                }
            }
        } catch (NumberFormatException e) {
            precioValido = false;
        }

        // Validar ambos campos
        if (nombre.isEmpty() && !precioValido) {
            // Caso 1: Ambos campos son inválidos
            Toast.makeText(this, "No se pudo guardar el servicio: Nombre y Precio inválidos.", Toast.LENGTH_LONG).show();
            return;
        } else if (nombre.isEmpty()) {
            // Caso 2: Solo el nombre es inválido
            Toast.makeText(this, "No se pudo guardar el servicio: Nombre inválido.", Toast.LENGTH_LONG).show();
            return;
        } else if (!precioValido) {
            // Caso 3: Solo el precio es inválido
            Toast.makeText(this, "No se pudo guardar el servicio: Precio inválido.", Toast.LENGTH_LONG).show();
            return;
        }

        // Si ambos campos son válidos, continuar con el proceso de guardar

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
            String codigo = "S" + String.format("%03d", max + 1); // Genera el código autoincremental
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
