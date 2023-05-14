package sv.ues.fia.eisi.cafetinesues.pm11074.Zona;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;

public class ActualizarZonaActivity extends Activity {

  private ControlBD helper;
  private EditText IdZona;
  private EditText NomZona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_actualizar_zona);
    helper = new ControlBD(this);
    IdZona = (EditText) findViewById(R.id.editIdZona);
    NomZona = (EditText) findViewById(R.id.editNombreZona);
  }

  public void actualizarZona(android.view.View v) {
    Zona zona = new Zona();
    zona.setIdZona(Integer.parseInt(IdZona.getText().toString()));
    zona.setNomZona(NomZona.getText().toString());
    helper.abrir();
    String estado = helper.actualizarZona(zona);
    helper.cerrar();
    Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
  }


}
