package sv.ues.fia.eisi.cafetinesues.sr17012.TipoProducto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;
import sv.ues.fia.eisi.cafetinesues.pm11074.Encargado.Encargado;

public class InsertarTipoProductoActivity extends Activity {

    ControlBD helper;
    EditText editTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_tipo_producto);

        helper = new ControlBD(this);
        editTipo = (EditText) findViewById(R.id.editTipo);
    }

    public void insertarTipoProducto(View v) {
        if(editTipo.getText().toString().isEmpty() == false) {
            String tipo = editTipo.getText().toString();

            String regInsertados;
            TipoProducto tp = new TipoProducto();
            tp.setTipo(tipo);
            helper.abrir();
            regInsertados = helper.insertar(tp);
            helper.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this, "Debe ingresar el nombre del tipo de producto", Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v) {
        editTipo.setText("");
    }
}