package proyecto.automanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.automanager.R;

import java.util.ArrayList;

import proyecto.modelo.Servicio;

public class EditarServicioActivity extends AppCompatActivity{
    Servicio servicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editarservicio);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editarservicio), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void mostrarDatos(){
        Intent i = getIntent();
        servicio = (Servicio) i.getSerializableExtra("servicio");
        EditText etPrecioServicio = findViewById(R.id.etPrecioServicio);
        TextView tvCodigoServicio = findViewById(R.id.tvCodigoServicio);
        etPrecioServicio.setText(String.format("%.2f",servicio.getPrecioActual()));
        tvCodigoServicio.setText(servicio.getCodigo());

        // Agregar precio al historial de precios
        servicio.registrarPrecioEnHistorial(servicio.getPrecioActual());
        Log.d("AutoManager", "Precio agregado al historial: " + servicio.getHistorialPrecios());



    }

    //método llamado al dar click en botón cancelar
    public void cancelar(View view){
        finish();
    }

    //metodo para guardar cambios, agregar precio al historial
    public void guardar(View view){
        // recolectar el precio del formulario
        EditText etPrecioServicio = findViewById(R.id.etPrecioServicio);
        String precioStr = etPrecioServicio.getText().toString().trim();

        double precio;
        boolean precioValido = false;

        // Validar el precio
        try {
            if (!precioStr.isEmpty()) {
                precio = Double.parseDouble(precioStr.replace(',', '.'));
                if (precio > 0) {
                    // El precio es válido
                    precioValido = true;
                    servicio.setPrecioActual(precio);
                    servicio.registrarPrecioEnHistorial(precio);
                }
            }
        } catch (NumberFormatException e) {
            // El precio no es un número válido
            precioValido = false;
        }

        // Si el precio no es válido, mostrar el mensaje de error y detener la ejecución
        if (!precioValido) {
            Toast.makeText(this, "No se pudo guardar los cambios: Precio inválido.", Toast.LENGTH_LONG).show();
            return;
        }

        // Si el precio es válido, continuar con el proceso de guardar
        ArrayList<Servicio> lstServicios = new ArrayList<>();
        try{
            lstServicios = Servicio.cargarServicios(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            int i = lstServicios.indexOf(servicio);
            if (i != -1) {
                lstServicios.set(i, servicio);
                Log.d("AutoManager", "Actualizado en lstServicios");
                //guardar la lista en el archivo
                Servicio.guardarServicios(lstServicios, this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
                Toast.makeText(getApplicationContext(), "Datos guardados", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("AutoManager", "Servicio no encontrado en la lista.");
                Toast.makeText(getApplicationContext(), "Error al actualizar el servicio.", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Log.d("AuotoManager", "Error al cargar datos al editar"+e.getMessage());
            Toast.makeText(getApplicationContext(), "Error al guardar los datos.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        mostrarDatos();
        Log.d("Editar Servicios","En onResume");
    }
}
