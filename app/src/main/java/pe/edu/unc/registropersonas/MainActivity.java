package pe.edu.unc.registropersonas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import Models.Persona;

public class MainActivity extends AppCompatActivity {
    List<Persona> listPersonas = new ArrayList<>();
    EditText txtNombre,txtApellido,txtEdad,txtDNI,txtPeso,txtAltura;
    Button btnRegistrar,btnListar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtNombre = findViewById(R.id.txtNombres);
        txtApellido = findViewById(R.id.txtApellidos);
        txtEdad = findViewById(R.id.txtEdad);
        txtDNI = findViewById(R.id.txtDni);
        txtAltura = findViewById(R.id.txtAltura);
        txtPeso = findViewById(R.id.txtPeso);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnListar = findViewById(R.id.btnListar);
        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListarPersonas();
            }
        });
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarPersona();
            }
        });
        listPersonas = new ArrayList<>();
    }
    public void RegistrarPersona(){
        Persona persona = new Persona(txtNombre.getText().toString(),txtApellido.getText().toString(),Integer.parseInt(txtEdad.getText().toString()),txtDNI.getText().toString(),Float.parseFloat(txtPeso.getText().toString()),Float.parseFloat(txtAltura.getText().toString()));


        if (persona.verificarDNI()) {
            Toast.makeText(this, "RegistroCorrecto"+persona.toString(), Toast.LENGTH_SHORT).show();
            listPersonas.add(persona);
        }else {
            Toast.makeText(this, "DNI Invalido", Toast.LENGTH_SHORT).show();
            txtDNI.findFocus();
            limpiar();
        }
    }

    private void limpiar() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtEdad.setText("");
        txtDNI.setText("");
        txtPeso.setText("");
        txtAltura.setText("");
        txtNombre.requestFocus();
    }

    public void ListarPersonas(){

    }

}