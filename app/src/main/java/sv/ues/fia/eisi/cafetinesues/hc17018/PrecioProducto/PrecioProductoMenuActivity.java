package sv.ues.fia.eisi.cafetinesues.hc17018.PrecioProducto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;

public class PrecioProductoMenuActivity extends ListActivity {

    SharedPreferences prf;
    ArrayList<String> menu = new ArrayList<String>(); // menu a llenar con las opciones permitidas

    int[] idsPermisos = {49,50,51,52};
    String[] opcionesDisponibles = {"Insertar Precio/Producto","Consultar Precio/Producto","Borrar Precio/Producto","Actualizar Precio/Producto"};

    ArrayList<String> menuActivities = new ArrayList<String>(); // menu a llenar con las activities permitidas.
    String[] activities = {"InsertarPrecioProductoActivity","ConsultarPrecioProductoActivity","EliminarPrecioProductoActivity", "ActualizarPrecioProductoActivity"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setItemsList(); // Seteamos las opciones a las que tiene acceso
        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, menu));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);
        String nombreValue = menuActivities.get(position);

        try{
            Class<?> clase=Class.forName("sv.ues.fia.eisi.cafetinesues.hc17018.PrecioProducto."+nombreValue);
            Intent inte = new Intent(this,clase);
            this.startActivity(inte);
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private void setItemsList() {
        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        String permisos = prf.getString("permisos", "no hay");
        ArrayList<String> permisosList = ControlBD.retornarArrayPermisos(permisos);

        for(int i = 0; i < idsPermisos.length; i++){
            for (String p : permisosList) {
                if( Integer.parseInt(p) == idsPermisos[i]) {
                    menu.add(opcionesDisponibles[i]); // Esto solo es para el menu de Gestionar --
                    menuActivities.add(activities[i]); // Esto solo es para Agregar las Activities --
                }
            }
        }
    }
}