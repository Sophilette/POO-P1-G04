package proyecto.automanagerapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
        mostrarDatos();
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
        etPrecioServicio.setText(String.valueOf(servicio.getPrecioActual()));

        Spinner spCodServicio = findViewById(R.id.spCodServicio);
        ArrayList<String> lstCodigos = Servicio.obtenerCodigos();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lstCodigos){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view).setTextColor(Color.BLACK); // cambia el color del texto
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCodServicio.setAdapter(adapter);
        int spinnerPosition = adapter.getPosition(servicio.getCodigo());
        spCodServicio.setSelection(spinnerPosition);

    }

    //método llamado al dar click en botón cancelar
    public void cancelar(View view){
        finish();
    }
}
