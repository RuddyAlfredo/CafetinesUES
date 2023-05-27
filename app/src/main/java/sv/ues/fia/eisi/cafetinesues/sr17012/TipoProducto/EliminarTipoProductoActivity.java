package sv.ues.fia.eisi.cafetinesues.sr17012.TipoProducto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;
import sv.ues.fia.eisi.cafetinesues.pm11074.Encargado.Encargado;

public class EliminarTipoProductoActivity extends Activity {

    EditText editId;
    Button eliminar;
    EditText editTipo;
    ControlBD helper;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_tipo_producto);

        helper = new ControlBD(this);
        editId = (EditText) findViewById(R.id.editId);
        editTipo = (EditText) findViewById(R.id.editTipo);
        eliminar = (Button) findViewById(R.id.eliminar);
        eliminar.setEnabled(false);
    }

    public void eliminarTipoProducto(View v){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Eliminar Tipo de Producto");
        alertDialog.setMessage("¿Está seguro que desea eliminar éste elemento?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Sí", (dialog, which) -> {
            String regEliminados;
            TipoProducto tp = new TipoProducto();
            tp.setIdTipoProducto(editId.getText().toString());
            helper.abrir();
            regEliminados = helper.eliminar(tp);
            helper.cerrar();
            Toast.makeText(this, regEliminados, Toast.LENGTH_SHORT).show();
            eliminar.setEnabled(false);
            editTipo.setText("");
            editId.setText("");
        });
        alertDialog.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        alertDialog.show();
    }

    public void consultarTipoProducto(View v) {
        if(editId.getText().toString().isEmpty() == false) {
            helper.abrir();
            TipoProducto tp = helper.consultarTipoProducto(editId.getText().toString());
            helper.cerrar();
            if (tp == null)
                Toast.makeText(this, "Tipo de Producto con Id " + editId.getText().toString() + " no encontrado", Toast.LENGTH_LONG).show();
            else {
                editTipo.setText(tp.getTipo());
                eliminar.setEnabled(true);
            }
        }else
            Toast.makeText(this, "Debe ingresar el ID del tipo de producto", Toast.LENGTH_SHORT).show();
    }
}