package proyecto.automanagerapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.automanager.R;

import java.util.ArrayList;

import proyecto.modelo.*;

public class AggOrdenesServicioActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aggorden);
        llenarSpinners();

        Log.d("Administrar Ordenes de Servicios","en onCreate");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.aggorden), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void llenarSpinners(){
        // llenar el spinner de nombres de clientes
        Spinner spClienteNombre = findViewById(R.id.spClienteNombre);
        ArrayList<String> lstNombres = Cliente.obtenerNombres();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lstNombres){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view).setTextColor(Color.BLACK); // cambia el color del texto
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spClienteNombre.setAdapter(adapter);

        // llenar el spinner de tipos de vehiculos
        Spinner spTipoVehiculo = findViewById(R.id.spTipoVehiculo);
        ArrayList<String> lstTipos = Vehiculo.obtenerTipos();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lstTipos){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view).setTextColor(Color.BLACK); // cambia el color del texto
                return view;
            }
        };
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoVehiculo.setAdapter(adapter1);

        // llenar el spinner de codigos de servicios
        Spinner spCodigoServicio = findViewById(R.id.spCodigoServicio);
        ArrayList<String> lstCodigos = Servicio.obtenerCodigos();
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lstCodigos){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view).setTextColor(Color.BLACK); // cambia el color del texto
                return view;
            }
        };
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCodigoServicio.setAdapter(adapter2);

    }



}
