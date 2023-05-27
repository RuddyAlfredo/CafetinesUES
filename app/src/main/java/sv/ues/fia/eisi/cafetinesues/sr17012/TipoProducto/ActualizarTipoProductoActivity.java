package sv.ues.fia.eisi.cafetinesues.sr17012.TipoProducto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;
import sv.ues.fia.eisi.cafetinesues.pm11074.Encargado.Encargado;

public class ActualizarTipoProductoActivity extends Activity {

    ControlBD helper;
    EditText editId;
    EditText editTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_tipo_producto);

        helper = new ControlBD(this);
        editId = (EditText) findViewById(R.id.editId);
        editTipo = (EditText) findViewById(R.id.editTipo);
        editTipo.setEnabled(false);
    }

    public void actualizarTipoProducto(View v) {
        if(editTipo.getText().toString().isEmpty() == false) {
            TipoProducto tipo = new TipoProducto();
            tipo.setIdTipoProducto(editId.getText().toString());
            tipo.setTipo(editTipo.getText().toString());

            helper.abrir();
            String estado = helper.actualizar(tipo);
            helper.cerrar();

            Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this, "Debe ingresar el nombre del tipo de producto", Toast.LENGTH_SHORT).show();
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
                editTipo.setEnabled(true);
                editId.setEnabled(false);
            }
        }else
            Toast.makeText(this, "Debe ingresar el ID del tipo de producto", Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        editId.setText("");
        editId.setEnabled(true);
        editTipo.setText("");
        editTipo.setEnabled(false);
    }
}