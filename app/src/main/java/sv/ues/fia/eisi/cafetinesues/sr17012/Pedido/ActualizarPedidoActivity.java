package sv.ues.fia.eisi.cafetinesues.sr17012.Pedido;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import sv.ues.fia.eisi.cafetinesues.ControlBD;
import sv.ues.fia.eisi.cafetinesues.CuadroDialogo;
import sv.ues.fia.eisi.cafetinesues.R;
import sv.ues.fia.eisi.cafetinesues.hc17018.Producto.Producto;
import sv.ues.fia.eisi.cafetinesues.sr17012.Combo.Combo;

public class ActualizarPedidoActivity extends Activity implements CuadroDialogo.recibirValor{

    ControlBD helper;
    SharedPreferences prf;
    TextView editMonto;
    float total = 0.0F;
    Spinner editIdLocalSpinner;
    Spinner editIdTipoPedidoSpinner;
    Spinner editIdProductoSpinner;
    Spinner editIdComboSpinner;
    Spinner editIdTipoPagoSpinner;
    ListView listaProductos;
    ListView listaCombos;
    int cantidad;
    String precio;
    ArrayList<String> comboIds = new ArrayList<String>();
    ArrayList<String> comboNames = new ArrayList<String>();
    ArrayList<Integer> comboCantidades = new ArrayList<Integer>();
    ArrayList<String> comboPrecios = new ArrayList<String>();
    String idCombo = "";
    String idTipoPedido = "";
    String idTipoPago = "";
    String nomCombo = "";
    ArrayList<String> prodIds = new ArrayList<String>();
    ArrayList<String> prodNames = new ArrayList<String>();
    ArrayList<Integer> prodCantidades = new ArrayList<Integer>();
    ArrayList<String> prodPrecios = new ArrayList<String>();
    String idLocal = "--";
    String idProductos = "";
    String nomProductos = "";
    ArrayList<Combo> combosObj = new ArrayList<Combo>(); // Combos recuperados por lOcal
    ArrayList<Producto> productosObj = new ArrayList<Producto>(); // Productos recuperados por local
    TextView mensajeMonto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_pedido);
        helper = new ControlBD(this);
        editMonto = (TextView) findViewById(R.id.montoText);
        mensajeMonto = (TextView) findViewById(R.id.mensajeMonto);
        mensajeMonto.setAlpha(0);

        editIdLocalSpinner = (Spinner) findViewById(R.id.editIdLocalSpinner);
        editIdTipoPedidoSpinner = (Spinner) findViewById(R.id.editIdTipoPedidoSpinner);
        editIdProductoSpinner = (Spinner) findViewById(R.id.editIdProductoSpinner);
        editIdComboSpinner = (Spinner) findViewById(R.id.editIdComboSpinner);
        editIdTipoPagoSpinner = (Spinner) findViewById(R.id.editIdTipoPagoSpinner);
        llenarSpinner(0,"");
        llenarSpinner(3, "");
        llenarSpinner(4, "");

        listaProductos = (ListView) findViewById(R.id.listaProductos);
        listaCombos = (ListView) findViewById(R.id.listaCombos);

        editIdLocalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String cadena = editIdLocalSpinner.getSelectedItem().toString();
                for (int i = 0; i < cadena.length(); i++) { // En el spinner, el ID está mezaclado con el nombre, aqui lo separamos pa guardarlo como FK
                    if (cadena.charAt(i) == ' ') { // Si el carácter en [i] es un espacio (' ') ahi cortamos
                        idLocal = editIdLocalSpinner.getSelectedItem().toString().substring(0, i);
                        break;
                    }
                }
                llenarSpinner(1, idLocal);
                llenarSpinner(2, idLocal);
                clearLists();

                if(editIdLocalSpinner.getSelectedItemPosition() == 2) {
                    mensajeMonto.setText("Monto Minimo: " + " y Maximo: ");
                    mensajeMonto.setAlpha(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        editIdTipoPedidoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String cadena = editIdTipoPedidoSpinner.getSelectedItem().toString();
                for (int i = 0; i < cadena.length(); i++) { // En el spinner, el ID está mezaclado con el nombre, aqui lo separamos pa guardarlo como FK
                    if (cadena.charAt(i) == ' ') { // Si el carácter en [i] es un espacio (' ') ahi cortamos
                        idTipoPedido = editIdTipoPedidoSpinner.getSelectedItem().toString().substring(0, i);
                        break;
                    }
                }
                helper.abrir();
                ArrayList<String> m = helper.getMontos(idLocal);
                helper.cerrar();
                if(editIdTipoPedidoSpinner.getSelectedItemPosition() == 3) {
                    mensajeMonto.setAlpha(1);
                    mensajeMonto.setText("Monto Minimo: " + m.get(0) + " y Maximo: "+ m.get(1)+" Para éste Local");
                }else
                    mensajeMonto.setAlpha(0);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        editIdTipoPagoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String cadena = editIdTipoPagoSpinner.getSelectedItem().toString();
                for (int i = 0; i < cadena.length(); i++) { // En el spinner, el ID está mezaclado con el nombre, aqui lo separamos pa guardarlo como FK
                    if (cadena.charAt(i) == ' ') { // Si el carácter en [i] es un espacio (' ') ahi cortamos
                        idTipoPago = editIdTipoPagoSpinner.getSelectedItem().toString().substring(0, i);
                        break;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        editIdProductoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(editIdProductoSpinner.getSelectedItemPosition() != 0) {
                    idProductos = productosObj.get(position).getIdProducto();
                    nomProductos = productosObj.get(position).getNomProducto();
                    precio = productosObj.get(position).getPrecio();
                    cantidad = 0;

                    new CuadroDialogo(ActualizarPedidoActivity.this, ActualizarPedidoActivity.this, "p");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        editIdComboSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(editIdComboSpinner.getSelectedItemPosition() != 0) {
                    idCombo = combosObj.get(position).getIdCombo();
                    nomCombo = combosObj.get(position).getNomCombo();
                    precio = combosObj.get(position).getPrecioCombo();

                    cantidad = 0;

                    new CuadroDialogo(ActualizarPedidoActivity.this, ActualizarPedidoActivity.this, "c");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Eliminar elementos del listview
        listaProductos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView adapterView, View view, int i, long l) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ActualizarPedidoActivity.this);
                alertDialog.setTitle("Importante");
                alertDialog.setMessage("¿ Eliminar el Producto del Pedido ?");
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        if(prodIds.size() > 0) {
                            sumarTotal(prodPrecios.get(i), prodCantidades.get(i), "r");
                            prodNames.remove(i);
                            prodIds.remove(i);
                            prodCantidades.remove(i);
                            prodPrecios.remove(i);
                            listaProductos();
                        }
                    }
                });
                alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                    }
                });
                alertDialog.show();

                return false;
            }
        });

        // Eliminar elementos del listview
        listaCombos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView adapterView, View view, int i, long l) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ActualizarPedidoActivity.this);
                alertDialog.setTitle("Importante");
                alertDialog.setMessage("¿ Eliminar el Combo del Pedido ?");
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        if(comboIds.size() > 0) {
                            sumarTotal(comboPrecios.get(i), comboCantidades.get(i), "r");
                            comboNames.remove(i);
                            comboIds.remove(i);
                            comboCantidades.remove(i);
                            comboPrecios.remove(i);
                            listaCombos();
                        }
                    }
                });
                alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                    }
                });
                alertDialog.show();

                return false;
            }
        });


    }

    public void listaProductos(){
        String[] productoInfo = new String[prodNames.size()];
        for (int i = 0; i < prodNames.size(); i++) {
            productoInfo[i] = prodNames.get(i);
        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,productoInfo);
        listaProductos.setAdapter(adaptador);
    }

    public void listaCombos(){
        String[] comboInfo = new String[comboNames.size()];
        for (int i = 0; i < comboNames.size(); i++) {
            comboInfo[i] = comboNames.get(i);
        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,comboInfo);
        listaCombos.setAdapter(adaptador);
    }

    public void llenarSpinner(int spinner, String id){
        if(spinner == 0) { // Llenar Spinner de locales
            helper.abrir();
            ArrayList<String> allLocales = helper.getAllLocales();
            helper.cerrar();

            String[] locales = new String[allLocales.size()];
            locales = allLocales.toArray(locales);

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locales);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editIdLocalSpinner.setAdapter(dataAdapter);
        }
        else if(spinner == 1){ //Llenar Spinner de productos
            helper.abrir();
            ArrayList<Producto> allProductos = helper.allProductos(id);
            helper.cerrar();

            productosObj = allProductos;

            String[] productos = new String[allProductos.size()];
            for (int i = 0; i < allProductos.size(); i++) {
                String idP = "";
                String prec = "";
                if(allProductos.get(i).getIdProducto() != null) {
                    idP = allProductos.get(i).getIdProducto();
                    prec = "  $ " + allProductos.get(i).getPrecio();
                }
                productos[i] = idP + " - " + allProductos.get(i).getNomProducto()+ " " + prec;
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, productos);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editIdProductoSpinner.setAdapter(dataAdapter);
        }else if(spinner == 2){ // Llenar Spinner de Combos
            helper.abrir();
            ArrayList<Combo> allCombos = helper.allCombos(id);
            helper.cerrar();

            combosObj = allCombos;

            String[] combos = new String[allCombos.size()];
            for (int i = 0; i < allCombos.size(); i++) {
                String idP ="";
                String prec = "";
                if(allCombos.get(i).getIdCombo() != null) {
                    idP = allCombos.get(i).getIdCombo();
                    prec = "  $ " + allCombos.get(i).getPrecioCombo();
                }
                combos[i] = idP + " - " + allCombos.get(i).getNomCombo() + " " + prec;
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, combos);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editIdComboSpinner.setAdapter(dataAdapter);
        } else if(spinner == 3){ // Llenar Spinner de TipoPagos
            helper.abrir();
            ArrayList<String> allTipoPago = helper.getAllTipoPago();
            helper.cerrar();

            String[] tipoPagos = new String[allTipoPago.size()];
            tipoPagos = allTipoPago.toArray(tipoPagos);

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipoPagos);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editIdTipoPagoSpinner.setAdapter(dataAdapter);
        }else if(spinner == 4){ // Llenar Spinner de TipoPedidos
            helper.abrir();
            ArrayList<String> allTipoPedidos = helper.getAllTipoPedido();
            helper.cerrar();

            String[] tipoPedidos = new String[allTipoPedidos.size()];
            tipoPedidos = allTipoPedidos.toArray(tipoPedidos);

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipoPedidos);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editIdTipoPedidoSpinner.setAdapter(dataAdapter);
        }
    }

    public void sumarTotal(String p, int cant, String operacion){
        if(operacion.equals("r")) /// Restar
            total -= (Float.parseFloat(p) * cant);
        else
            total += (Float.parseFloat(p) * cant);

        editMonto.setText("total: $" + String.format("%.2f", total));
    }


    public String[] concatStringArrays(String[] ar1, String[] ar2){
        String[] res = new String[ ar1.length + ar2.length ];
        System.arraycopy( ar1, 0, res, 0, ar1.length );
        System.arraycopy( ar2, 0, res, ar1.length, ar2.length );

        return res;
    }

    @Override
    public void retornarCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void addProducto(){
        if (!prodIds.contains(idProductos)) { //Solo agregamos el producto si no esta ya metido
            prodIds.add(idProductos);
            prodNames.add(nomProductos + "   $" + precio + "     X" + cantidad);
            prodCantidades.add(cantidad);
            prodPrecios.add(precio);
            editIdProductoSpinner.setSelection(0);
            sumarTotal(precio, cantidad, "s");
            listaProductos();
        }
    }

    public void addCombo(){
        if (!comboIds.contains(idCombo)) { //Solo agregamos el combo si no esta ya metido
            comboIds.add(idCombo);
            comboNames.add(nomCombo + "   $" + precio+ "          X" + cantidad);
            comboCantidades.add(cantidad);
            comboPrecios.add(precio);
            editIdComboSpinner.setSelection(0);
            sumarTotal(precio,cantidad,"s");
            listaCombos();
        }
    }

    public void limpiarTexto(View v) {
        editIdLocalSpinner.setSelection(0);
        editIdProductoSpinner.setSelection(0);
        editIdComboSpinner.setSelection(0);
        editIdTipoPedidoSpinner.setSelection(0);
        editIdTipoPagoSpinner.setSelection(0);
        clearLists();
    }

    public void clearLists(){
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,new String[] {""});
        listaProductos.setAdapter(adaptador);
        listaCombos.setAdapter(adaptador);
        prodNames.clear();
        prodIds.clear();
        comboNames.clear();
        comboIds.clear();
        comboCantidades.clear();
        prodCantidades.clear();
        prodPrecios.clear();
        comboPrecios.clear();
        total = 0.0F;
        editMonto.setText("Total: $00.00");
    }

    public void insertarPedido(View v) {
        String regInsertados;
        Pedido pedido = new Pedido();

        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        String userId = prf.getString("userId", "no hay");
        pedido.setIdUsuario(userId);

        pedido.setIdLocal(idLocal);
        pedido.setIdTipoPedido(idTipoPedido);
        pedido.setIdTipoPago(idTipoPago);
        pedido.setMonto(String.valueOf(total));
        pedido.setComboIds(comboIds);
        pedido.setComboCant(comboCantidades);
        pedido.setProdIds(prodIds);
        pedido.setProdCant(prodCantidades);

        String[] locales = new String[comboIds.size()];
        locales = comboIds.toArray(locales);
        System.out.println(comboIds);

        if(editIdLocalSpinner.getSelectedItemPosition() != 0 && editIdTipoPedidoSpinner.getSelectedItemPosition() != 0
                && editIdTipoPagoSpinner.getSelectedItemPosition() != 0 && (!idProductos.isEmpty() || !idCombo.isEmpty())) {
            helper.abrir();
            regInsertados = helper.insertar(pedido);
            helper.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, "Faltan Datos", Toast.LENGTH_SHORT).show();
    }
}