package pe.edu.unc.registropersonas;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import Models.Persona;

public class MainActivity extends AppCompatActivity {
   public static List<Persona> listPersonas;
    String [] ciudades = {"Seleccionar Ciudad","Cajamarca","Trujillo","Chiclayo"};
    EditText txtNombre,txtApellido,txtEdad,txtDNI,txtPeso,txtAltura;
    TextView tvImgError;
    Button btnRegistrar,btnListar;
    RadioGroup rgSexo;
    Spinner sp_Ciudad;
    ImageView imgFoto;
    Uri imgSeleccionar = null;
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
        rgSexo = findViewById(R.id.rgSexo);
        sp_Ciudad = findViewById(R.id.sp_Ciudad);
        //Cargar los datos para sp_Ciudad(lista)
        sp_Ciudad.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,ciudades));
        txtEdad = findViewById(R.id.txtEdad);
        txtDNI = findViewById(R.id.txtDni);
        txtAltura = findViewById(R.id.txtAltura);
        txtPeso = findViewById(R.id.txtPeso);
        imgFoto = findViewById(R.id.imgFoto);
        tvImgError = findViewById(R.id.tvImgError);
        imgFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeleccioarFoto();
            }
        });

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

    private void SeleccioarFoto() {
        Intent oIntento = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //Filtrar los archivos de tipo Imagen
        oIntento.setType("image/*");
        startActivityIfNeeded(Intent.createChooser(oIntento,"Seleccionar Imagen"), 100);
    }
    //Metodo para recoger la imagen seleccionada


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent oIntento) {
        super.onActivityResult(requestCode, resultCode, oIntento);
        if(requestCode ==100){
            if (resultCode == RESULT_OK){
                imgSeleccionar = oIntento.getData(); //Aqui se esta pasando la imagen
                imgFoto.setImageURI(oIntento.getData());
            }
        }
    }

    public void RegistrarPersona(){
        if (Validar())
            return;
            Persona persona = new Persona(
                    txtNombre.getText().toString(),
                    txtApellido.getText().toString(),
                    ObtenerSexoSelect(),
                    sp_Ciudad.getSelectedItem().toString(),
                    Integer.parseInt(txtEdad.getText().toString()),
                    txtDNI.getText().toString(),
                    Float.parseFloat(txtPeso.getText().toString()),
                    Float.parseFloat(txtAltura.getText().toString()),
                    imgSeleccionar
            );

            if (persona.verificarDNI()) {
                Toast.makeText(this, "RegistroCorrecto" + persona.toString(), Toast.LENGTH_SHORT).show();
                listPersonas.add(persona);
                limpiar();

            }
            else {
                Toast.makeText(this, "DNI Invalido", Toast.LENGTH_SHORT).show();
                txtDNI.requestFocus();
                txtDNI.setError("¡Compleata Aqui!");
                //limpiar();
            }
    }


    private String ObtenerSexoSelect() {
        int identificador=rgSexo.getCheckedRadioButtonId();
        if (identificador == R.id.rbFemenino)
            return "Femenino";
        if (identificador == R.id.rbMasculino)
            return "Masculino";
        return "";
    }

    private boolean Validar() {
        if (comprobarCampo(txtNombre,"Nombres"))return true;
        if (comprobarCampo(txtApellido,"Apellido"))return true;
        if (ObtenerSexoSelect().isEmpty()){
            mostrarMensaje("Seleccionar un tipo de Sexo");
            rgSexo.requestFocus();
            return true;
        }
        if (sp_Ciudad.getSelectedItemPosition()==0){
            mostrarMensaje("Seleccionar ciudade de Procedencia");
            sp_Ciudad.requestFocus();
            return true;
        }
        if (comprobarCampo(txtEdad,"Edad"))return true;
        if (comprobarCampo(txtDNI,"DNI"))return true;
        if (comprobarCampo(txtPeso,"Peso"))return true;
        if (comprobarCampo(txtAltura,"Altura"))return true;
        if (imgSeleccionar==null){
            mostrarMensaje("Selecinar una Foto de galeria");
            tvImgError.setText("Selecionar Imagen");
            tvImgError.setTextColor(Color.RED);
            tvImgError.requestFocus();
            imgFoto.requestFocus();
            return true;
        }

        return false;
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this,mensaje, Toast.LENGTH_SHORT).show();
    }

    private boolean comprobarCampo(EditText campo, String mensaje){
        if (campo.getText().toString().trim().isEmpty()){
            campo.setError("Campo "+mensaje+" Obligatorio");
            campo.requestFocus();
            return true;
        }
        return false;
    }


    private void limpiar() {
        txtNombre.setText("");
        txtApellido.setText("");
        rgSexo.check(R.id.rbFemenino);
        sp_Ciudad.setSelection(0);
        sp_Ciudad.setBackgroundResource(R.color.Gris);
        txtEdad.setText("");
        txtDNI.setText("");
        txtPeso.setText("");
        txtAltura.setText("");
        imgFoto.setImageResource(R.drawable.click);
        tvImgError.setText("");
        imgSeleccionar=null;
        txtNombre.requestFocus();
    }

    public void ListarPersonas() {
        Intent oIntento = new Intent(MainActivity.this, Actividad_listPerson.class);
        //oIntento.putExtra("lista_p", new ArrayList<>(listPersonas)); // Enviar la lista correctamente
        startActivity(oIntento); // Usar startActivity en lugar de startActivities
    }

}