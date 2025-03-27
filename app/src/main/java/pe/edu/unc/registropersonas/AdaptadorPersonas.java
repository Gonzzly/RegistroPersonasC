package pe.edu.unc.registropersonas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import AccesoDatos.DAOPersona;
import Models.Persona;

public class AdaptadorPersonas extends BaseAdapter {

    private DAOPersona oDAOPersonas;
    private Context contexto;
    private LayoutInflater inflater;

    public AdaptadorPersonas(DAOPersona oDAOPersonas, Context contexto) {
        this.oDAOPersonas = oDAOPersonas;
        this.contexto = contexto;
        inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return oDAOPersonas.getSize();
    }

    @Override
    public Object getItem(int position) {
        return oDAOPersonas.getObjetoPersona(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View vista = inflater.inflate(R.layout.ly_item_lista_persona, null);

        ImageView imgFoto = vista.findViewById(R.id.imgItemFoto);
        TextView tvNombre = vista.findViewById(R.id.lbNombreCompleto);
        TextView tvTipoPeso = vista.findViewById(R.id.lbTipoPeso);
        TextView tvTipoPersona = vista.findViewById(R.id.lbTipoPersona);
        ImageView imgSexo = vista.findViewById(R.id.imgSexo);
        TextView tvProcedencia = vista.findViewById(R.id.lbProcedencia);

        Persona oP = oDAOPersonas.getObjetoPersona(i);

        tvNombre.setText(oP.getNombreCompleto());
        tvTipoPeso.setText(oP.getTipoPeso());
        tvTipoPersona.setText(oP.getTipoPersona());
        tvProcedencia.setText(oP.getCiudad());

        // Asignar la imagen de sexo
        if (oP.getSexo().equalsIgnoreCase("Femenino")) {
            imgSexo.setImageResource(R.drawable.femenino);
        } else {
            imgSexo.setImageResource(R.drawable.masculino);
        }

        // Verificar si la imagen no es nula antes de asignarla
        if (oP.getFoto() != null) {
            imgFoto.setImageBitmap(convertirBitMap(oP.getFoto()));
        } else {
            imgFoto.setImageResource(R.drawable.lgsalud); // Imagen por defecto
        }

        return vista;
    }

    private Bitmap convertirBitMap(byte[] foto) {
        if (foto == null || foto.length == 0) return null;
        return BitmapFactory.decodeByteArray(foto, 0, foto.length);
    }
}
