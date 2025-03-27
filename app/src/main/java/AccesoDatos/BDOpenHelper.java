package AccesoDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BDOpenHelper extends SQLiteOpenHelper {
    String table_Persona = "CREATE TABLE IF NOT EXISTS Persona ("
            + "IDPersona INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + "nombres TEXT NOT NULL, "
            + "apellidos TEXT NOT NULL, "
            + "sexo TEXT NOT NULL, "
            + "ciudad TEXT NOT NULL, "
            + "edad INTEGER NOT NULL, "
            + "dni TEXT NOT NULL UNIQUE, "  // DNI debe ser único
            + "peso REAL NOT NULL, "  // SQLite usa REAL para valores decimales
            + "altura REAL NOT NULL, "
            + "foto BLOB)";  // Para imágenes
    public BDOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(table_Persona);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Persona");
        db.execSQL(table_Persona);
    }
}
