package sv.ues.fia.eisi.cafetinesues;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IniciarSesion extends Activity {
    ControlBD helper;
    EditText editUser;
    EditText editClave;
    Button loginBtn;
    SharedPreferences shrPrf;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        helper = new ControlBD(this);
        editUser = (EditText) findViewById(R.id.editUser);
        editClave = (EditText) findViewById(R.id.editClave);
        loginBtn = (Button) findViewById(R.id.btnLogin);
        shrPrf = getSharedPreferences("user_details", MODE_PRIVATE);
        intent = new Intent(this, MainActivity.class);
        if (shrPrf.contains("usuario"))
            this.startActivity(intent);

        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                helper.abrir();
                String username = editUser.getText().toString();
                String clave = editClave.getText().toString();
                if (helper.validarUsuario(username,clave) != "false"){
                    String idUser = helper.validarUsuario(username,clave);
                    String us = helper.consultarPermisosUsuario(idUser);
                    SharedPreferences.Editor editor = shrPrf.edit();
                    editor.putString("username",username);
                    editor.putString("userId",idUser);
                    editor.putString("permisos", us); //permisos separados por coma
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "Bienvenido " + username,Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Usuario no encontrado " + username,Toast.LENGTH_SHORT).show();
                }
                helper.cerrar();
            }
        });
    }

    public void generarDatos(View v){
        helper.abrir();
        helper.llenarBD();
        helper.cerrar();
        Toast.makeText(getApplicationContext(),"Datos generados exitosamente", Toast.LENGTH_SHORT).show();
    }
}