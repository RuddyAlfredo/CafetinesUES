package sv.ues.fia.eisi.cafetinesues.mc15048.Empleado;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.internal.TextWatcherAdapter;

import java.util.ArrayList;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.R;

public class ConsultarEmpleadoActivity extends AppCompatActivity {
    private ControlBD helper;
    private ListView listaEmpleados;
    private EditText search;
    ArrayList<Empleado> empleados = new ArrayList<Empleado>();

    ListAdapterEmpleado adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_empleado);
        helper = new ControlBD(this);
        search = (EditText) findViewById(R.id.SearchEmpleado);
        listaEmpleados = (ListView) findViewById(R.id.listViewEmpleados);
        helper.abrir();
        empleados = helper.consultarEmpleado();
        helper.cerrar();
        adapter = new ListAdapterEmpleado(this,R.layout.empleado_row ,empleados);
        if(empleados.isEmpty()) {
            Toast.makeText(this, "No hay registros", Toast.LENGTH_SHORT).show();
        }else{
             listaEmpleados.setAdapter(adapter);
        }

        search.addTextChangedListener(new TextWatcher(){
            public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
                ArrayList<Empleado> suggestions = new ArrayList<>();

                //Si no hay nada que filtrar retorna todos los elementos
                if(s == null || s.length() == 0){
                    suggestions.addAll(empleados);
                }else{
                    String filterPattern = s.toString().toLowerCase().trim();
                    for(Empleado empleado: empleados){
                        if(empleado.getNombreEmpleado().toLowerCase().contains(filterPattern)){
                            suggestions.add(empleado);
                        }
                        else if(empleado.getApeEmpleado().toLowerCase().contains(filterPattern)){
                            suggestions.add(empleado);
                        }
                    }
                }
                adapter = new ListAdapterEmpleado(ConsultarEmpleadoActivity.this,R.layout.empleado_row, suggestions);
                listaEmpleados.setAdapter(adapter);
            }
            public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            }

            public void afterTextChanged(Editable s) {

            }
        });
    }



}
