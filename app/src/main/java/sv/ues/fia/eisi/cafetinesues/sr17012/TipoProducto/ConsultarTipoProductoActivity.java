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

public class ConsultarTipoProductoActivity extends Activity {

    ControlBD helper;
    EditText editId;
    EditText editNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_tipo_producto);

        helper = new ControlBD(this);
        editId = (EditText) findViewById(R.id.editId);
        editNombre = (EditText) findViewById(R.id.editNombre);
    }

    public void consultarTipoProducto(View v) {
        if(editId.getText().toString().isEmpty() == false) {
            helper.abrir();
            TipoProducto tp = helper.consultarTipoProducto(editId.getText().toString());
            helper.cerrar();
            if (tp == null)
                Toast.makeText(this, "Tipo de Producto con Id " + editId.getText().toString() + " no encontrado", Toast.LENGTH_LONG).show();
            else {
                editNombre.setText(tp.getTipo());
            }
        }else
            Toast.makeText(this, "Debe ingresar el ID del tipo de producto", Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        editId.setText("");
        editNombre.setText("");
    }
}