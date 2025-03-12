package pe.edu.unc.registropersonas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import Models.Persona;

public class Actividad_ListaPersona extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_lista_persona);

        TableLayout tableLayout = findViewById(R.id.tly_lista);
        ArrayList<Persona> listaPersonas = (ArrayList<Persona>) getIntent().getSerializableExtra("lista_p");

        // Agregar encabezados de la tabla
        TableRow encabezado = new TableRow(this);
        String[] titulos = {"Nombre", "Apellidos", "Edad", "DNI", "Peso", "Altura"};

        for (String titulo : titulos) {
            TextView tvTitulo = new TextView(this);
            tvTitulo.setText(titulo);
            tvTitulo.setTextColor(Color.WHITE);
            tvTitulo.setGravity(Gravity.CENTER);
            tvTitulo.setPadding(20, 20, 20, 20);
            tvTitulo.setBackgroundResource(R.drawable.bordes);
            encabezado.addView(tvTitulo);
        }

        // Agregar la fila de encabezado a la tabla
        tableLayout.addView(encabezado);


        // Llenar la tabla con los datos de la lista de personas
        if (listaPersonas != null) {
            for (Persona persona : listaPersonas) {
                TableRow personaX = new TableRow(this);

                TextView tvNombre = new TextView(this);
                tvNombre.setText(persona.getNombre());
                personaX.addView(tvNombre);

                TextView tvApellidos = new TextView(this);
                tvApellidos.setText(persona.getApellidos());
                personaX.addView(tvApellidos);

                TextView tvEdad = new TextView(this);
                tvEdad.setText(String.valueOf(persona.getEdad()));
                personaX.addView(tvEdad);

                TextView tvDNI = new TextView(this);
                tvDNI.setText(persona.getDNI());
                personaX.addView(tvDNI);

                TextView tvPeso = new TextView(this);
                tvPeso.setText(String.valueOf(persona.getPeso()));
                personaX.addView(tvPeso);

                TextView tvAltura = new TextView(this);
                tvAltura.setText(String.valueOf(persona.getAltura()));
                personaX.addView(tvAltura);
                //Stilos
                Stilo(tvNombre);
                Stilo(tvApellidos);
                Stilo(tvEdad);
                Stilo(tvDNI);
                Stilo(tvPeso);
                Stilo(tvAltura);

                tableLayout.addView(personaX);
            }

        }

    }

    private void Stilo(TextView tv) {
        tv.setBackgroundResource(R.drawable.bodes_texview);
        tv.setTextColor(Color.BLACK);
    }

}
