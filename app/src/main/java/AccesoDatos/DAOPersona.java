package AccesoDatos;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import Models.Persona;

public class DAOPersona {
    private final String nombreBD = "BDSalud";
    private final int versionBD = 1;
    private List<Persona> listaPersonas;

    public DAOPersona() {
        listaPersonas = new ArrayList<>();
    }

    public boolean Agregar(Activity contexto, Persona op) {
        boolean rpta = false;
        BDOpenHelper oOH = new BDOpenHelper(contexto, nombreBD, null, versionBD);
        SQLiteDatabase oBD = oOH.getWritableDatabase();

        if (oBD != null) {
            ContentValues oColumnas = new ContentValues();
            oColumnas.put("nombres", op.getNombre());
            oColumnas.put("apellidos", op.getApellidos());
            oColumnas.put("sexo", op.getSexo());
            oColumnas.put("ciudad", op.getCiudad());
            oColumnas.put("edad", op.getEdad());
            oColumnas.put("dni", op.getDni());
            oColumnas.put("peso", op.getPeso());
            oColumnas.put("altura", op.getAltura());
            oColumnas.put("foto", op.getFoto());

            long fila = oBD.insert("Persona", null, oColumnas);
            if (fila != -1) {
                rpta = true;
            }
            oBD.close();
            oOH.close();
        }
        return rpta;
    }

    public boolean cargarLista(Activity contexto) {
        boolean rpta = false;
        BDOpenHelper oOH = new BDOpenHelper(contexto, nombreBD, null, versionBD);
        SQLiteDatabase oBD = oOH.getReadableDatabase();

        String sql = "SELECT * FROM Persona";
        Cursor oRegistros = oBD.rawQuery(sql, null);

        listaPersonas.clear();  // Evitar duplicados

        if (oRegistros.moveToFirst()) {
            rpta = true;
            do {
                String nombres = oRegistros.getString(1);
                String apellidos = oRegistros.getString(2);
                String sexo = oRegistros.getString(3);
                String ciudad = oRegistros.getString(4);
                int edad = oRegistros.getInt(5);
                String dni = oRegistros.getString(6);
                double peso = oRegistros.getDouble(7);
                double altura = oRegistros.getDouble(8);
                byte[] foto = oRegistros.getBlob(9);

                Persona oP = new Persona(nombres, apellidos, sexo, ciudad, edad, dni, peso, altura, foto);
                listaPersonas.add(oP);
            } while (oRegistros.moveToNext());
        }

        oRegistros.close();
        oBD.close();
        oOH.close();

        return rpta;
    }

    public List<Persona> getListaPersonas() {
        return listaPersonas;
    }

    public int getSize() {
        return listaPersonas.size();
    }

    public Persona getObjetoPersona(int indice) {
        return listaPersonas.get(indice);
    }
}
