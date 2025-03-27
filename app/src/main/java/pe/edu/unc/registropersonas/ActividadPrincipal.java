package pe.edu.unc.registropersonas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Locale;

public class ActividadPrincipal extends AppCompatActivity {
    private TextView lbContar;
    private int contador;
    private String fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_principal);

        Toolbar oBarra = findViewById(R.id.toolbarPrincipal);
        setSupportActionBar(oBarra);

        lbContar = findViewById(R.id.tvIdentificador);

        // Cargar valores desde SharedPreferences
        SharedPreferences oFlujo = getSharedPreferences("control", Context.MODE_PRIVATE);
        contador = oFlujo.getInt("contar", 1);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss yyyy/MM/dd", Locale.getDefault());
        fecha = oFlujo.getString("fecha", sdf.format(System.currentTimeMillis()));

        lbContar.setText("N de ingresos: " + contador + "\n" + fecha);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences oFlujo = getSharedPreferences("control", Context.MODE_PRIVATE);
        SharedPreferences.Editor oEditar = oFlujo.edit();

        contador++;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss yyyy/MM/dd", Locale.getDefault());
        fecha = sdf.format(System.currentTimeMillis());

        oEditar.putInt("contar", contador);
        oEditar.putString("fecha", fecha);
        oEditar.apply();  // Mejor que commit()
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent oIntent = null;
        if (item.getItemId() == R.id.itemSalir) {
            finish();
        }
        if (item.getItemId() == R.id.itemRegistrarPersonas) {
            oIntent = new Intent(this, RegistroPersona.class);
            startActivity(oIntent);
        }
        if (item.getItemId() == R.id.itemLlamadas) {
            oIntent = new Intent(this, ActividadLlamadas.class);
            startActivity(oIntent);
        }
        if (item.getItemId() == R.id.itemListarPersonas) {
            oIntent = new Intent(this, Actividad_listPerson.class);
            startActivity(oIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
