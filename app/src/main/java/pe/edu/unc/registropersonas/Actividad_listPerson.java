package pe.edu.unc.registropersonas;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import AccesoDatos.DAOPersona;

public class Actividad_listPerson extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ly_actividad_list_person);

        ListView listaPersonas = findViewById(R.id.lvListaPerson);

        DAOPersona daoPersona = new DAOPersona();
        boolean datosCargados = daoPersona.cargarLista(this); // ✅ Ahora se carga bien la lista

        if (datosCargados) {
            listaPersonas.setAdapter(new AdaptadorPersonas(daoPersona, this));
        } else {
            Toast.makeText(this, "No hay personas registradas", Toast.LENGTH_SHORT).show();
        }
    }
}
