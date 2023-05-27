package sv.ues.fia.eisi.cafetinesues;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;


public class CuadroDialogo {
    Button aceptar;
    Button cancelar;

    public interface recibirValor{
        void retornarCantidad (int cantidad);

        void addProducto();

        void addCombo();
    }

    private recibirValor interfaz;

    public CuadroDialogo(Context contexto, recibirValor actividad, String tipo) {

        interfaz = actividad;
        final Dialog dialogo = new Dialog(contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.setContentView(R.layout.cantidad);

        final EditText cantidad = (EditText) dialogo.findViewById(R.id.cantidad);
        aceptar = (Button) dialogo.findViewById(R.id.aceptar);
        cancelar = (Button) dialogo.findViewById(R.id.cancelar);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cantidad.getText().toString().isEmpty() == false) {
                    interfaz.retornarCantidad(Integer.parseInt(cantidad.getText().toString()));
                    if (tipo.equals("c"))
                        interfaz.addCombo();
                    else
                        interfaz.addProducto();
                    dialogo.dismiss();
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo.dismiss();
            }
        });

        dialogo.show();
    }

}
