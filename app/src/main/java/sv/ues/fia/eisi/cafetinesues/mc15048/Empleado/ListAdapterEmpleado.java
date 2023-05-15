package sv.ues.fia.eisi.cafetinesues.mc15048.Empleado;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import sv.ues.fia.eisi.cafetinesues.R;

public class ListAdapterEmpleado extends ArrayAdapter<Empleado> {
    private ArrayList<Empleado> emps;
    private Context context;
    private int resourceLayout;

    public ListAdapterEmpleado(@NonNull Context context, int resource, ArrayList<Empleado> empleados) {
        super(context, resource, empleados);
        this.emps = empleados;
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
        Empleado emp = emps.get(position);
        TextView textId = (TextView) view.findViewById(R.id.textIdEmpleado);
        textId.setText("ID "+emp.getIdEmpleado());
        TextView textNombre = (TextView) view.findViewById(R.id.textNombreEmpleado);
        textNombre.setText("Nombre"+emp.getNombreEmpleado());
        TextView textApellido = (TextView) view.findViewById(R.id.textApellidoEmpleado);
        textApellido.setText("Apellidos:"+emp.getApeEmpleado());
        TextView textIdLocal = (TextView) view.findViewById(R.id.textLocalEmpleado);
        textIdLocal.setText("ID Local "+emp.getIdLocal());
        TextView textIdZona = (TextView) view.findViewById(R.id.textZonaEmpleado);
        textIdZona.setText("ID Zona "+emp.getIdZona());

        return view;
    }

}
