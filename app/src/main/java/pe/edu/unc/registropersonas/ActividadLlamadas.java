package pe.edu.unc.registropersonas;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import AccesoDatos.DAOLlamadas;

public class ActividadLlamadas extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ly_llamadas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Spinner sp_Llamadas = findViewById(R.id.spLlamadas);
        ListView lvListaLlamadas = findViewById(R.id.lvListaLLamadas);
        DAOLlamadas oLlamadas = new DAOLlamadas();

        String tipoLlamada [] = {"Todas las Llamadas", "Entrante", "Saliente", "Perdida", "No Contesta", "Bloqueada"};

        sp_Llamadas.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tipoLlamada));

        sp_Llamadas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ArrayAdapter<String> adapterLista = new ArrayAdapter<>(ActividadLlamadas.this,
                            android.R.layout.simple_list_item_1,
                            oLlamadas.ListarLlamadas(ActividadLlamadas.this, tipoLlamada[position]));
                    lvListaLlamadas.setAdapter(adapterLista);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
