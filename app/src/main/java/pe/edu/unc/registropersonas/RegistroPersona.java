package pe.edu.unc.registropersonas;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.ByteArrayOutputStream;

import AccesoDatos.DAOPersona;
import Models.Persona;

public class RegistroPersona extends AppCompatActivity {
    String[] ciudades = {"Seleccionar Ciudad", "Cajamarca", "Trujillo", "Chiclayo"};
    EditText txtNombre, txtApellido, txtEdad, txtDNI, txtPeso, txtAltura;
    TextView tvImgError;
    Button btnRegistrar, btnListar;
    RadioGroup rgSexo;
    Spinner sp_Ciudad;
    ImageView imgFoto;
    byte[] imgSeleccionar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_registrarpersona);
        Toolbar oBarra = findViewById(R.id.toolbarRegistroPersonas);
        setSupportActionBar(oBarra);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtNombre = findViewById(R.id.txtNombres);
        txtApellido = findViewById(R.id.txtApellidos);
        rgSexo = findViewById(R.id.rgSexo);
        sp_Ciudad = findViewById(R.id.sp_Ciudad);
        sp_Ciudad.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ciudades));
        txtEdad = findViewById(R.id.txtEdad);
        txtDNI = findViewById(R.id.txtDni);
        txtAltura = findViewById(R.id.txtAltura);
        txtPeso = findViewById(R.id.txtPeso);
        imgFoto = findViewById(R.id.imgItemFoto);
        tvImgError = findViewById(R.id.tvImgError);

        imgFoto.setOnClickListener(v -> SeleccionarFoto());
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnListar = findViewById(R.id.btnListar);
        btnListar.setOnClickListener(v -> ListarPersonas());
        btnRegistrar.setOnClickListener(v -> RegistrarPersona());
    }

    private void SeleccionarFoto() {
        Intent oIntento = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        oIntento.setType("image/*");
        startActivityForResult(Intent.createChooser(oIntento, "Seleccionar Imagen"), 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent oIntento) {
        super.onActivityResult(requestCode, resultCode, oIntento);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Uri foto = oIntento.getData();
            try {
                Bitmap oBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), foto);
                imgFoto.setImageBitmap(oBitmap);
                ByteArrayOutputStream oFlujoSalida = new ByteArrayOutputStream();
                oBitmap.compress(Bitmap.CompressFormat.PNG, 100, oFlujoSalida);
                imgSeleccionar = oFlujoSalida.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al procesar la imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void RegistrarPersona() {
        if (Validar()) return;
        Persona persona = new Persona(
                txtNombre.getText().toString(),
                txtApellido.getText().toString(),
                ObtenerSexoSelect(),
                sp_Ciudad.getSelectedItem().toString(),
                Integer.parseInt(txtEdad.getText().toString()),
                txtDNI.getText().toString(),
                Double.parseDouble(txtPeso.getText().toString()),
                Double.parseDouble(txtAltura.getText().toString()),
                imgSeleccionar
        );
        if (!persona.verificarDNI()) {
            txtDNI.setError("¡Debe contener 8 dígitos numéricos!");
            return;
        }
        new DAOPersona().Agregar(this, persona);
        cuadroDeDialogo();
    }

    private String ObtenerSexoSelect() {
        int identificador = rgSexo.getCheckedRadioButtonId();
        if (identificador == R.id.rbFemenino) return "Femenino";
        if (identificador == R.id.rbMasculino) return "Masculino";
        mostrarMensaje("Seleccione un género");
        return null;
    }

    private boolean Validar() {
        if (comprobarCampo(txtNombre, "Nombres")) return true;
        if (comprobarCampo(txtApellido, "Apellido")) return true;
        if (ObtenerSexoSelect() == null) return true;
        if (sp_Ciudad.getSelectedItemPosition() == 0) {
            mostrarMensaje("Seleccionar ciudad de procedencia");
            return true;
        }
        if (comprobarCampo(txtEdad, "Edad")) return true;
        if (comprobarCampo(txtDNI, "DNI")) return true;
        if (comprobarCampo(txtPeso, "Peso")) return true;
        if (comprobarCampo(txtAltura, "Altura")) return true;
        if (imgSeleccionar == null) {
            mostrarMensaje("Seleccionar una foto de galería");
            tvImgError.setText("Seleccionar Imagen");
            tvImgError.setTextColor(Color.RED);
            return true;
        }
        return false;
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private boolean comprobarCampo(EditText campo, String mensaje) {
        if (campo.getText().toString().trim().isEmpty()) {
            campo.setError("Campo " + mensaje + " Obligatorio");
            return true;
        }
        return false;
    }

    private void cuadroDeDialogo() {
        new AlertDialog.Builder(this)
                .setTitle("Aviso")
                .setMessage("¿Desea seguir registrando?")
                .setIcon(R.drawable.baseline_add_alert_24)
                .setNegativeButton("No", (dialog, which) -> finish())
                .setPositiveButton("Sí", (dialog, which) -> limpiar())
                .show();
    }

    private void limpiar() {
        txtNombre.setText("");
        txtApellido.setText("");
        rgSexo.check(R.id.rbFemenino);
        sp_Ciudad.setSelection(0);
        txtEdad.setText("");
        txtDNI.setText("");
        txtPeso.setText("");
        txtAltura.setText("");
        imgFoto.setImageResource(R.drawable.click);
        tvImgError.setText("");
        imgSeleccionar = null;
    }

    public void ListarPersonas() {
        startActivity(new Intent(this, Actividad_listPerson.class));
    }
}
