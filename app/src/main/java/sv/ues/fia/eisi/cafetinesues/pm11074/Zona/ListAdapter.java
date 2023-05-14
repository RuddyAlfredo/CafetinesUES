package sv.ues.fia.eisi.cafetinesues.pm11074.Zona;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.io.Console;
import java.util.ArrayList;

import sv.ues.fia.eisi.cafetinesues.R;

public class ListAdapter extends ArrayAdapter<Zona> {
    private ArrayList<Zona> zonas;
    private Context context;
    private int resourceLayout;

    public ListAdapter(@NonNull Context context, int resource, ArrayList<Zona> zonas) {
        super(context, resource, zonas);
        this.zonas = zonas;
        this.context = context;
        this.resourceLayout = resource;
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater .from(context).inflate(resourceLayout, null);
        }
        Zona zona = zonas.get(position);
        TextView textIdZonas = (TextView) view.findViewById(R.id.textIdZonas);
        textIdZonas.setText(zona.getIdZona()+"");
        TextView textNombreZonas = (TextView) view.findViewById(R.id.textNombreZonas);
        textNombreZonas.setText(zona.getNomZona());
        return view;
    }

}
