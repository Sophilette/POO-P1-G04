package proyecto.automanagerapp;

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

import com.example.automanager.R;

/**
 * Formulario de "Crear Cliente".
 * - Incluye un Spinner de tipo de cliente.
 */
public class AggClienteActivity extends AppCompatActivity {

    // Lista de tipos para el Spinner (PERSONAL/EMPRESARIAL)
    private Spinner spTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aggcliente);

        configurarSpinner();

        Log.d("Crear Cliente","Formulario inicializado");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.aggcliente), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
    }

    private void configurarSpinner(){
        spTipo = findViewById(R.id.spTipoCliente);
        String[] items = new String[]{"PERSONAL","EMPRESARIAL"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items){
            @Override public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view).setTextColor(Color.BLACK);
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipo.setAdapter(adapter);
    }

    public void cancelar(View view){
        finish();
    }

    public void guardarCliente(View view){
        finish();
    }
}


