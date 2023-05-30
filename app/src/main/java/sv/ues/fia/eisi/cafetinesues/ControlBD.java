package sv.ues.fia.eisi.cafetinesues;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;

import sv.ues.fia.eisi.cafetinesues.Modelos.AccesoUsuario;
import sv.ues.fia.eisi.cafetinesues.Modelos.OpcionCRUD;
import sv.ues.fia.eisi.cafetinesues.Modelos.Usuario;
import sv.ues.fia.eisi.cafetinesues.hc17018.PrecioProducto.PrecioProducto;
import sv.ues.fia.eisi.cafetinesues.hc17018.Producto.Producto;
import sv.ues.fia.eisi.cafetinesues.mc15048.Empleado.Empleado;
import sv.ues.fia.eisi.cafetinesues.pm11074.Encargado.Encargado;
import sv.ues.fia.eisi.cafetinesues.pm11074.Facultad.Facultad;
import sv.ues.fia.eisi.cafetinesues.pm11074.Local.Local;
import sv.ues.fia.eisi.cafetinesues.mc15048.Zona.Zona;
import sv.ues.fia.eisi.cafetinesues.sr17012.Combo.Combo;
import sv.ues.fia.eisi.cafetinesues.sr17012.Pedido.Pedido;
import sv.ues.fia.eisi.cafetinesues.sr17012.TipoProducto.TipoProducto;

public class ControlBD {

//DEFINICIÓN DE CAMPOS PARA LAS TABLAS---------------------------------------------
    private static final String[] camposUsuario = new String[] {"idUsuario","nomUsuario","clave"};
    private static final String[] camposAccesoUsuario = new String[] {"idUsuario","idOpcion"};
    private static final String[] camposOpcionCrud = new String[] {"idOpcion","desOpcion","numCrud"};
    private static final String[] camposTipoProducto = new String[] {"idTipoProducto","tipo"};

    private static final String[] camposEncargado = new String[] {"idEncargado","nomEncargado"};
    private static final String[] camposLocal = new String[] {"idLocal","idEncargado","nomLocal","esInterno"};
    private static final String[] camposFacultad = new String[] {"idFacultad","nomFacultad"};

    private static final String[] camposZona = new String[] {"idZona","nomZona"};
    private static final String[] camposEmpleado = new String[] {"idEmpleado","idZona","idLocal","nomEmpleado"};

    private static final String[] camposProducto = new String[] {"idProducto","idLocal","idTipoProducto","nomProducto","descripcion"};
    private static final String[] camposPrecioProducto = new String[] {"idPrecioProducto","idProducto","precioProducto","esActivo","fechaPrecio"};

    private static final String[] camposCombo = new String[] {"idCombo","idLocal","nomCombo","precioCombo","disponible"};
    private static final String[] camposPedido = new String[] {"idPedido","idUsuario","idLocal","idTipoPedido","idTipoPago","monto","fechaPedido","pagado"};
    private static final String[] camposDetallePedido = new String[] {"idDetallePedido","idPedido","idProducto","cantidadProducto","idCombo","cantidadCombo"};


    //---------------------------------------------------------------------------------

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    final String[] permisosCliente = {
            "004","033","034","036",
    };

    final String[] permisosEncargado = {
            "002","003","004","025","026","027","028","030","034"
    };

    public ControlBD (Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

     private static class DatabaseHelper extends SQLiteOpenHelper {
        private static final String BASE_DATOS = "cafetines.s3db";
        private static final int VERSION = 1;
        DatabaseHelper(Context context) {
            super(context, BASE_DATOS, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
            // TABLAS DE CATALOCOS-------------------------------
                db.execSQL("CREATE TABLE tipoPago (idTipoPago INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nomTipoPago VARCHAR(30) NOT NULL);");
                db.execSQL("CREATE TABLE tipoPedido (idTipoPedido INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nomTipoPedido VARCHAR(50) NOT NULL);");


                db.execSQL("CREATE TABLE usuario( idUsuario VARCHAR(2) PRIMARY KEY, nomUsuario VARCHAR(50), clave VARCHAR(5));");
                db.execSQL("CREATE TABLE opcionCrud(idOpcion VARCHAR(3), desOpcion VARCHAR(50), numCrud INTEGER);");
                db.execSQL("CREATE TABLE accesoUsuario( idUsuario VARCHAR(2), idOpcion VARCHAR(3), PRIMARY KEY(idUsuario, idOpcion),CONSTRAINT fk_usuario FOREIGN KEY(idUsuario) REFERENCES usuario(idUsuario),CONSTRAINT fk_opcion FOREIGN KEY(idOpcion) REFERENCES opcionCrud(idOpcion));");

                db.execSQL("CREATE TABLE encargado( idEncargado INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nomEncargado VARCHAR(50) NOT NULL);");
                db.execSQL("CREATE TABLE local( idLocal INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, idEncargado INTEGER, nomLocal VARCHAR(50) NOT NULL, esInterno INTEGER, CONSTRAINT fk_encargado FOREIGN KEY(idEncargado) REFERENCES encargado(idEncargado) ON DELETE RESTRICT);");
                db.execSQL("CREATE TABLE facultad( idFacultad INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nomFacultad VARCHAR(50) NOT NULL);");

                db.execSQL("CREATE TABLE zona ( idZona INTEGER PRIMARY KEY, nomZona VARCHAR(30) NOT NULL);");
                db.execSQL("CREATE TABLE empleado( idEmpleado INTEGER PRIMARY KEY, idZona INTEGER, idLocal VARCHAR(2), nomEmpleado VARCHAR(100) NOT NULL, apEmpleado VARCHAR (100), CONSTRAINT fk_zona FOREIGN KEY(idZona) REFERENCES zona(idZona), CONSTRAINT fk_local FOREIGN KEY(idLocal) REFERENCES local(idLocal) ON DELETE RESTRICT);");
                db.execSQL("CREATE TABLE ubicacion( idUbicacion INTEGER PRIMARY KEY AUTOINCREMENT, idFacultad INTEGER, idUsuario VARCHAR(2), puntoReferencia VARCHAR(200), CONSTRAINT fk_usuario FOREIGN KEY(idUsuario) REFERENCES usuario(idUsuario) ON DELETE RESTRICT, CONSTRAINT fk_facultad FOREIGN KEY(idFacultad) REFERENCES facultad(idFacultad) ON DELETE RESTRICT);");


                db.execSQL("CREATE TABLE tipoProducto (idTipoProducto INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, tipo VARCHAR(30) NOT NULL);");
                db.execSQL("CREATE TABLE producto(idProducto INTEGER PRIMARY KEY AUTOINCREMENT, idLocal INTEGER NOT NULL, idTipoProducto INTEGER NOT NULL, nomProducto VARCHAR(50) NOT NULL, descripcion VARCHAR(150), CONSTRAINT fk_local FOREIGN KEY(idLocal) REFERENCES local(idLocal), CONSTRAINT fk_tipoProducto FOREIGN KEY(idTipoProducto) REFERENCES tipoProducto(idTipoProducto));");
                db.execSQL("CREATE TABLE precioProducto( idPrecioProducto INTEGER PRIMARY KEY AUTOINCREMENT, idProducto INTEGER, precioProducto FLOAT(3) NOT NULL, esActivo INTEGER DEFAULT 1, fechaPrecio Date DEFAULT CURRENT_DATE, CONSTRAINT fk_producto FOREIGN KEY(idProducto) REFERENCES producto(idProducto) ON DELETE RESTRICT);");

                db.execSQL("CREATE TABLE combo(idCombo INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, idLocal INTEGER NOT NULL, nomCombo VARCHAR(50) NOT NULL, precioCombo FLOAT(3) NOT NULL, disponible INTEGER, CONSTRAINT fk_local FOREIGN KEY(idLocal) REFERENCES local(idLocal) ON DELETE RESTRICT);");
                db.execSQL("CREATE TABLE comboProducto( idCombo INTEGER, idProducto INTEGER, CONSTRAINT fk_combo FOREIGN KEY(idCombo) REFERENCES combo(idCombo) ON DELETE RESTRICT, CONSTRAINT fk_producto FOREIGN KEY(idProducto) REFERENCES producto(idProducto) ON DELETE RESTRICT);");

                db.execSQL("CREATE TABLE pedido(" +
                        "idPedido INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "idUsuario VARCHAR(2) NOT NULL, " +
                        "idLocal INTEGER NOT NULL, " +
                        "idTipoPago INTEGER NOT NULL, " +
                        "idTipoPedido INTEGER NOT NULL, " +
                        "monto FLOAT(5) NOT NULL, " +
                        "fechaPedido Date DEFAULT CURRENT_DATE, " +
                        "pagado INTEGER DEFAULT 0, " +
                        "CONSTRAINT fk_usuario FOREIGN KEY(idUsuario) REFERENCES usuario(idUsuario) ON DELETE RESTRICT, " +
                        "CONSTRAINT fk_local FOREIGN KEY(idLocal) REFERENCES local(idLocal) ON DELETE RESTRICT, " +
                        "CONSTRAINT fk_tipoPago FOREIGN KEY(idTipoPago) REFERENCES tipoPago(idTipoPago) ON DELETE RESTRICT, " +
                        "CONSTRAINT fk_tipoPedido FOREIGN KEY(idTipoPedido) REFERENCES tipoPedido(idTipoPedido) ON DELETE RESTRICT);");

                db.execSQL("CREATE TABLE detallePedido(" +
                        "idDetallePedido INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "idPedido INTEGER NOT NULL, " +
                        "idLocal INTEGER NOT NULL, " +
                        "idProducto INTEGER, " +
                        "cantidadProducto INTEGER , " +
                        "idCombo INTEGER, " +
                        "cantidadCombo INTEGER, " +
                        "CONSTRAINT fk_local FOREIGN KEY(idLocal) REFERENCES local(idLocal) ON DELETE RESTRICT, " +
                        "CONSTRAINT fk_producto FOREIGN KEY(idPedido) REFERENCES pedido(idPedido) ON DELETE RESTRICT, " +
                        "CONSTRAINT fk_combo FOREIGN KEY(idCombo) REFERENCES combo(idCombo) ON DELETE RESTRICT, " +
                        "CONSTRAINT fk_pedido FOREIGN KEY(idPedido) REFERENCES pedido(idPedido) ON DELETE RESTRICT);");

                db.execSQL("CREATE TABLE monto(" +
                        "idMonto INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "idLocal INTEGER NOT NULL, " +
                        "max FLOAT(5) NOT NULL, " +
                        "min FLOAT(5) NOT NULL, " +
                        "CONSTRAINT fk_local FOREIGN KEY(idLocal) REFERENCES local(idLocal) ON DELETE RESTRICT);");


               // "select * from asistencia where time(fechahora)>='00:00:00' and time(fechahora)<='12:00:00';


                db.execSQL("CREATE TRIGGER fk_lomite_tres " +
                        "BEFORE INSERT ON detallePedido " +
                        "FOR EACH ROW WHEN new.cantidadProducto < 3 AND new.id>=6" +
                        "BEGIN " +
                        "SELECT CASE WHEN ((SELECT carnet FROM alumno WHERE carnet = NEW.carnet) IS NULL) " +
                        "THEN RAISE(ABORT, 'No existe alumno') " +
                        "END; " +
                        "END;");

                db.execSQL("CREATE TRIGGER delete_zona " +
                        "BEFORE DELETE ON zona " +
                        "FOR EACH ROW " +
                        "BEGIN " +
                        "DELETE FROM empleado WHERE idZona = OLD.idZona; " +
                        "END;");

                db.execSQL("CREATE TRIGGER delete_local " +
                        "BEFORE DELETE ON local " +
                        "FOR EACH ROW " +
                        "BEGIN " +
                        "DELETE FROM empleado WHERE idLocal = OLD.idLocal; " +
                        "END;");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    // ---Abrir la BD---
    public void abrir() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return;
    }
    // ---Cerrar BD---
    public void cerrar() {
        DBHelper.close();
    }

    public void insertar(Usuario usuario){  // PM11074 =========
        ContentValues user = new ContentValues();

        user.put("idUsuario", usuario.getIdUsuario());
        user.put("nomUsuario", usuario.getNomUsuario());
        user.put("clave", usuario.getClave());

        db.insert("usuario", null, user);
    }

    public void insertar(OpcionCRUD opcionCrud){  // PM11074 =========
        ContentValues opcCrud = new ContentValues();

        opcCrud.put("idOpcion", opcionCrud.getIdOpcion());
        opcCrud.put("desOpcion", opcionCrud.getDesOpcion());
        opcCrud.put("numCrud", opcionCrud.getNumCrud());

        db.insert("opcionCrud", null, opcCrud);
    }

    public void insertar(AccesoUsuario accesoUsuario){  // PM11074 =========
        ContentValues acUser = new ContentValues();
        acUser.put("idUsuario", accesoUsuario.getIdUsuario());
        acUser.put("idOpcion", accesoUsuario.getIdOpcion());

        db.insert("accesoUsuario", null, acUser);
    }


    public void llenarBD(){

    // VALORES PARA USUARIO-----------( POR AHORA SON 5)-------------------------------
        final String[] valIdUsuario = {"01","02","03"};
        final String[] valNomUsuario = {"admin","cliente","encargado"};
        final String[] valClave = {"12345","12345","12345"};

    // VALORES PARA OPCIONCRUD---------( POR AHORA SON 22)------------------------------
        final String[] valIdOpcion = {
                "001","002","003","004","005",
                "006",/*"007",*/"008","009","010", // ID's para opciones de gestionar (se reserva desde el 001 hasta el 020)
                "011",

                "021","022","023","024", // ID's para las opciones del CRUD de Encargado.
                "025","026","027","028", // ID's para las opciones del CRUD de Local.
                "029","030","031","032", // ID's para las opciones del CRUD de Facultad.
                "033","034","035","036", // ID's para las opciones del CRUD de Pedido.
                "061","062","063","064", // ID's para las opciones del CRUD de Combos.
                "037","038","039","040", // ID's para las opciones del CRUD de ZONA.
                //"041","042","043","044", // ID's para las opciones del CRUD de Ubicacion.
                "045","046","047","048", // ID's para las opciones del CRUD de Empleados.
                "049","050","051","052", // ID's para las opciones del CRUD de PrecioProducto.
                "053","054","055","056", // ID's para las opciones del CRUD de Producto.
                "057","058","059","060" // ID's para las opciones del CRUD de Tipo de Producto.
                // SIGUE EN 65

        };
        final String[] valDesOpcion = {
                "Gestionar Encargados","Gestionar Locales","Gestionar Facultades","Gestionar Pedido","Gestionar Combo",
                "Gestionar Zona",/*"Gestionar Ubicación",*/"Gestionar Empleados","Gestionar Precio/Producto","Gestionar Producto",
                "Gestionar Tipo de Producto",

                "Insertar Encargado","Consultar Encargado","Borrar Encargado","Actualizar Encargado", //CRUD Encargados.
                "Insertar Local","Consultar Local","Borrar Local","Actualizar Local", //CRUD Locales.
                "Insertar Facultad","Consultar Facultad","Borrar Facultad","Actualizar Facultad", //CRUD Facultad.
                "Insertar Pedido", "Consultar Pedido","Eliminar Pedido","Actualizar Pedido", // CRUD Pedidos.
                "Insertar Combo", "Consultar Combo","Eliminar Combo","Actualizar Combo", // CRUD Combo.
                "Insertar Zona", "Consultar Zona","Eliminar Zona","Actualizar Zona", // CRUD Zona.
               // "Insertar Ubicación", "Consultar Ubicación","Eliminar Ubicación","Actualizar Ubicación", // CRUD Ubicación.
                "Insertar Empleado", "Consultar Empleado","Eliminar Empleado","Actualizar Empleado", // CRUD Empleado.
                "Insertar PrecioProducto", "Consultar PrecioProducto","Eliminar PrecioProducto","Actualizar PrecioProducto", // CRUD Empleado.
                "Insertar Producto", "Consultar Producto","Eliminar Producto","Actualizar Producto", // CRUD Producto.
                "Insertar TipoProducto", "Consultar TipoProducto","Eliminar TipoProducto","Actualizar TipoProducto", // CRUD TipoProducto.
        };
        final int[] valNumCrud = {
                0, 0, 0, 0, 0,
                0, /*0,*/ 0, 0, 0,
                0,

                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
               /* 1, 2, 3, 4,*/
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
        };

    // VALORES PARA ACCESOUSUARIO-------( POR AHORA SON 26)----------------------------
        final String[] valAccesoIdUsuario = {
                "01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01",/*"01","01","01","01","01",*/
                "02","02","02","02","02","02","02","02","02","02","02","02","02","02","02","02","02","02","02",  // ID del cliente1 3 veces pues solo le permitimos 3 opciones
                "03","03","03","03","03","03","03","03","03","03","03","03","03","03","03","03","03","03","03","03","03","03","03","03","03","03","03","03","03","03","03","03","03"     // ID del encargado1 (para ejemplo solo le permitimos crear y consultar INGREDIENTE)
        };
        final String[] valAccesoIdOpcion = {                // El admin tiene acceso a todo
                "001","002","003","004","005","006",/*"007",*/"008","009","010","011","021","022","023","024","025","026","027","028","029","030","031","032","033","034","035","036","037","038","039","040",/*"041","042","043","044",*/"045","046","047","048","049","050","051","052","053","054","055","056","057","058","059","060","061","062","063","064",
                "002","003","004","005","006","009","010","011","026","029","030","032","033","034","062","038","050","054","058", // El cliente puede ver el la gestion de Pedido y solo crear/consultar (por eso 3 opciones)
                "001","002","004","005","006","008","009","010","022","025","026","033","034","061","062","063","064","037","038" ,"040" ,"045","048" ,"049" ,"050" ,"052","053" ,"054" ,"055","056" ,"057" ,"058","059" ,"060"          // El encargado 1 puede ver el la gestion de Productos y solo crear/consultar (por eso 3 opciones)
        };





// TODO Poner las demás tablas


        abrir();
        db.execSQL("DELETE FROM usuario");
        db.execSQL("DELETE FROM opcionCrud");
        db.execSQL("DELETE FROM accesoUsuario");

        db.execSQL("DELETE FROM tipoProducto");
        db.execSQL("DELETE FROM tipoPedido");
        db.execSQL("DELETE FROM tipoPago");
        db.execSQL("DELETE FROM producto");
        db.execSQL("DELETE FROM encargado");
        db.execSQL("DELETE FROM local");
        db.execSQL("DELETE FROM facultad");
        db.execSQL("DELETE FROM empleado");
        db.execSQL("DELETE FROM zona");
        db.execSQL("DELETE FROM combo");
        db.execSQL("DELETE FROM comboProducto");
        db.execSQL("DELETE FROM precioProducto");

        db.execSQL("DELETE FROM pedido");
        db.execSQL("DELETE FROM detallePedido");
        db.execSQL("DELETE FROM monto");
        db.execSQL("DELETE FROM comboProducto");

// Llenado de catalogos---------------
        db.execSQL("INSERT INTO tipoProducto VALUES (1,'Bebidas');");
        db.execSQL("INSERT INTO tipoProducto VALUES (2,'Postres');");
        db.execSQL("INSERT INTO tipoProducto VALUES (3,'Carnes Rojas');");
        db.execSQL("INSERT INTO tipoProducto VALUES (4,'Carnes Blancas');");
        db.execSQL("INSERT INTO tipoProducto VALUES (5,'Complementos');");
        db.execSQL("INSERT INTO tipoProducto VALUES (6,'Comida Rápida');");
        db.execSQL("INSERT INTO tipoProducto VALUES (7,'Pastas');");
        db.execSQL("INSERT INTO tipoProducto VALUES (8,'Antojito Típico');");
        db.execSQL("INSERT INTO tipoProducto VALUES (9,'Comida Tradicional');");

        db.execSQL("INSERT INTO tipoPedido VALUES (1,'Para Entregar');");
        db.execSQL("INSERT INTO tipoPedido VALUES (2,'Para Recoger');");
        db.execSQL("INSERT INTO tipoPedido VALUES (3,'Pedido Especial');");

        db.execSQL("INSERT INTO tipoPago VALUES (1,'Efectivo');");
        db.execSQL("INSERT INTO tipoPago VALUES (2,'Terjata de Crédito');");
        db.execSQL("INSERT INTO tipoPago VALUES (3,'Bitcoin');");

        db.execSQL("INSERT INTO producto VALUES (1,1,4,'Pechuga de Pollo','Pechuga mediana de pollo americano');");
        db.execSQL("INSERT INTO producto VALUES (2,1,7,'Espaqueti','Pasta con salsa y queso');");
        db.execSQL("INSERT INTO producto VALUES (3,1,5,'Arroz Frito','Arroz blanco con vejetales');");
        db.execSQL("INSERT INTO producto VALUES (4,1,5,'Casamiento','Arroz blanco con frijol rojo de seda');");
        db.execSQL("INSERT INTO producto VALUES (5,1,1,'Refresco de Tamarindo','Vaso mediano, con refill');");
        db.execSQL("INSERT INTO producto VALUES (6,1,5,'Tortillas','Medianas, hechas en comal');");

        db.execSQL("INSERT INTO producto VALUES (7,3,3,'Carne Asada','Lomo de Aguja de Res');");
        db.execSQL("INSERT INTO producto VALUES (8,3,5,'Ensalada Fresca','Lechuga, tomate, pepino, rábano');");
        db.execSQL("INSERT INTO producto VALUES (9,3,7,'CocaCola Lata','Coca-Cola clásica lata 12oz.');");
        db.execSQL("INSERT INTO producto VALUES (10,3,6,'Hamburguesa','Cuarto de libra con queso');");
        db.execSQL("INSERT INTO producto VALUES (11,3,8,'Pastelitos de carne','Con vejetales');");
        db.execSQL("INSERT INTO producto VALUES (12,3,8,'Yuca Frita','Con pepescas o chicharrón.');");

        db.execSQL("INSERT INTO producto VALUES (13,2,9,'Pupusas','Revueltas, de queso, de maiz y de arrroz');");
        db.execSQL("INSERT INTO producto VALUES (14,2,5,'Sopa de Pata','Con verdura y tripa');");
        db.execSQL("INSERT INTO producto VALUES (15,2,7,'Café','Taza mediana, con refill.');");
        db.execSQL("INSERT INTO producto VALUES (16,2,6,'Papas Fritas','Plato mediano');");
        db.execSQL("INSERT INTO producto VALUES (17,2,8,'Empanadas','De frijol o de leche');");
        db.execSQL("INSERT INTO producto VALUES (18,2,5,'Cerveza Lata','Marcas variadas, latas dede 12oz.');");

        db.execSQL("INSERT INTO precioProducto VALUES (1,2,0.50,0,'14/05/2023');");
        db.execSQL("INSERT INTO precioProducto VALUES (2,1,1.25,0,'14/05/2023');");
        db.execSQL("INSERT INTO precioProducto VALUES (3,1,1.20,1,'14/05/2023');");
        db.execSQL("INSERT INTO precioProducto VALUES (4,2,1.1,1,'14/05/2023');");
        db.execSQL("INSERT INTO precioProducto VALUES (5,3,0.5,1,'14/05/2023');");
        db.execSQL("INSERT INTO precioProducto VALUES (6,4,0.70,1,'14/05/2023');");
        db.execSQL("INSERT INTO precioProducto VALUES (7,5,0.7,1,'14/05/2023');");
        db.execSQL("INSERT INTO precioProducto VALUES (8,7,1.75,1,'14/05/2023');");
        db.execSQL("INSERT INTO precioProducto VALUES (9,8,0.75,1,'14/05/2023');");
        db.execSQL("INSERT INTO precioProducto VALUES (10,9,0.85,1,'14/05/2023');");
        db.execSQL("INSERT INTO precioProducto VALUES (11,10,2,1,'14/05/2023');");
        db.execSQL("INSERT INTO precioProducto VALUES (12,11,0.30,1,'14/05/2023');");
        db.execSQL("INSERT INTO precioProducto VALUES (13,12,1.25,1,'14/05/2023');");
        db.execSQL("INSERT INTO precioProducto VALUES (14,13,0.40,1,'14/05/2023');");
        db.execSQL("INSERT INTO precioProducto VALUES (15,14,2.50,1,'14/05/2023');");
        db.execSQL("INSERT INTO precioProducto VALUES (16,15,1.50,1,'14/05/2023');");
        db.execSQL("INSERT INTO precioProducto VALUES (17,16,0.50,1,'14/05/2023');");
        db.execSQL("INSERT INTO precioProducto VALUES (18,17,0.25,1,'14/05/2023');");
        db.execSQL("INSERT INTO precioProducto VALUES (19,18,2.50,1,'14/05/2023');");
        db.execSQL("INSERT INTO precioProducto VALUES (20,6,0.20,1,'14/05/2023');");



// Resto de tablas --------------------------
        db.execSQL("INSERT INTO encargado VALUES (1,'José Antonio');");
        db.execSQL("INSERT INTO encargado VALUES (2,'Andrew Steve');");
        db.execSQL("INSERT INTO encargado VALUES (3,'Juan Pérez');");
        db.execSQL("INSERT INTO encargado VALUES (5,'Carlos López');");

        db.execSQL("INSERT INTO local VALUES (1,1,'El Buen Sabor',1);");
        db.execSQL("INSERT INTO local VALUES (2,2,'Comedor Lucia',0);");;
        db.execSQL("INSERT INTO local VALUES (3,3,'Las Carnitas',0);");

        db.execSQL("INSERT INTO facultad VALUES (1,'Ingeniería y Arquitectura');");
        db.execSQL("INSERT INTO facultad VALUES (2,'Ciencias y Humanidades');");
        db.execSQL("INSERT INTO facultad VALUES (3,'Jurisprudencia');");
        db.execSQL("INSERT INTO facultad VALUES (4,'Odontología');");

        db.execSQL("INSERT INTO monto VALUES (1,1,300, 1000);");
        db.execSQL("INSERT INTO monto VALUES (2,2,100, 500);");
        db.execSQL("INSERT INTO monto VALUES (3,3,200, 700);");
        db.execSQL("INSERT INTO monto VALUES (4,4,500, 8000);");

        db.execSQL("INSERT INTO combo VALUES (1,1,'Combo de Pollo', 3.5, 1);");
        db.execSQL("INSERT INTO combo VALUES (2,1,'Combo de Pasta', 2.00, 1);");
        db.execSQL("INSERT INTO combo VALUES (3,3,'Combo de Carne', 4.00, 1);");
        db.execSQL("INSERT INTO combo VALUES (4,3,'Combo de Amburguesas', 2.50, 1);");
        db.execSQL("INSERT INTO combo VALUES (5,2,'Combo Tipico', 1.50, 1);");
        db.execSQL("INSERT INTO combo VALUES (6,2,'Combo Dopero', 2.50, 1);");

        db.execSQL("INSERT INTO comboProducto VALUES (1,1);");
        db.execSQL("INSERT INTO comboProducto VALUES (1,2);");
        db.execSQL("INSERT INTO comboProducto VALUES (1,3);");

        db.execSQL("INSERT INTO comboProducto VALUES (2,2);");
        db.execSQL("INSERT INTO comboProducto VALUES (2,6);");
        db.execSQL("INSERT INTO comboProducto VALUES (2,5);");

        db.execSQL("INSERT INTO comboProducto VALUES (3,7);");
        db.execSQL("INSERT INTO comboProducto VALUES (3,8);");
        db.execSQL("INSERT INTO comboProducto VALUES (3,9);");

        db.execSQL("INSERT INTO comboProducto VALUES (4,7);");
        db.execSQL("INSERT INTO comboProducto VALUES (4,6);");

        db.execSQL("INSERT INTO comboProducto VALUES (5,17);");
        db.execSQL("INSERT INTO comboProducto VALUES (5,15);");

        db.execSQL("INSERT INTO comboProducto VALUES (6,14);");
        db.execSQL("INSERT INTO comboProducto VALUES (6,18);");

        db.execSQL("INSERT INTO pedido VALUES (1,1,1,1,1,2.5,'13/05/2023',0);");
        db.execSQL("INSERT INTO pedido VALUES (2,1,2,3,3,4,'13/05/2023',0);");
        db.execSQL("INSERT INTO pedido VALUES (3,1,3,2,2,3.35,'13/05/2023',0);");

        db.execSQL("INSERT INTO detallePedido VALUES (1,6,1,6,3,null,null);");
        db.execSQL("INSERT INTO detallePedido VALUES (2,6,1,1,1,null,null);");
        db.execSQL("INSERT INTO detallePedido VALUES (3,6,1,5,1,null,null);");

        db.execSQL("INSERT INTO detallePedido VALUES (4,7,2,14,1,null,null);");
        db.execSQL("INSERT INTO detallePedido VALUES (5,7,2,null,null,5,1);");

        db.execSQL("INSERT INTO detallePedido VALUES (6,8,3,7,1,null,null);");
        db.execSQL("INSERT INTO detallePedido VALUES (7,8,3,9,1,null,null);");
        db.execSQL("INSERT INTO detallePedido VALUES (8,8,3,8,1,null,null);");

        Usuario usuario = new Usuario();
        for(int i = 0; i < valIdUsuario.length; i++){
            usuario.setIdUsuario(valIdUsuario[i]);
            usuario.setNomUsuario(valNomUsuario[i]);
            usuario.setClave(valClave[i]);
            insertar(usuario);
        }

        OpcionCRUD oc = new OpcionCRUD();
        for(int i = 0; i < valIdOpcion.length; i++){
            oc.setIdOpcion(valIdOpcion[i]);
            oc.setDesOpcion(valDesOpcion[i]);
            oc.setNumCrud(valNumCrud[i]);
            insertar(oc);
        }

        AccesoUsuario au = new AccesoUsuario();
        for(int i = 0; i < valAccesoIdUsuario.length; i++){
            au.setIdUsuario(valAccesoIdUsuario[i]);
            au.setIdOpcion(valAccesoIdOpcion[i]);
            insertar(au);
        }

        //Datos iniciales de Zona
        db.execSQL("INSERT INTO zona VALUES('1','Sur');");
        db.execSQL("INSERT INTO zona VALUES('2','Norte');");
        db.execSQL("INSERT INTO zona VALUES('3','Este');");
        db.execSQL("INSERT INTO zona VALUES('4','Oeste');");
        db.execSQL("INSERT INTO zona VALUES('5','Noreste');");
        //Datos iniciales de Empleado
        db.execSQL("INSERT INTO empleado VALUES(1,1,1,'Juan','Perez');");
        db.execSQL("INSERT INTO empleado VALUES(2,3,2,'Maria','Lopez');");
        db.execSQL("INSERT INTO empleado VALUES(3,5,3,'Pedro','Gonzalez');");
        db.execSQL("INSERT INTO empleado VALUES(4,2,3,'Jose','Rodriguez');");
        db.execSQL("INSERT INTO empleado VALUES(5,4,2,'Ana','Garcia');");
        db.execSQL("INSERT INTO empleado VALUES(6,4,1,'Carlos','Ramirez');");

        cerrar();

    }

//   ENCARGADO ======
    public String insertar(Encargado encargado){ // PM11074 =========
        String regInsertados="Registro Insertado Nº= ";
        long contador = 0;

        ContentValues encar = new ContentValues();
        encar.put("idEncargado", encargado.getIdEncargado());
        encar.put("nomEncargado", encargado.getNomEncargado());

        contador = db.insert("encargado", null, encar);

        if(contador==-1 || contador==0) {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados = regInsertados + contador;

            Usuario usuario = new Usuario(); // Creandole el usuario al Encargado recien creaado
            usuario.setIdUsuario(String.valueOf(contador));
            usuario.setNomUsuario(encargado.getNomEncargado());
            usuario.setClave("12345");
            insertar(usuario);

            for (int i = 0; i < permisosEncargado.length; i++) {
                AccesoUsuario au = new AccesoUsuario(); // Dandole los permisos
                au.setIdUsuario(String.valueOf(contador));
                au.setIdOpcion(permisosEncargado[i]);
                insertar(au);
            }
        }
        return regInsertados;
    }

    public Encargado consultarEncargado(String idEnc){ // PM11074 =========
        String[] id = {idEnc};
        Cursor cursor = db.query("encargado", camposEncargado, "idEncargado = ?", id, null, null, null);
        if(cursor.moveToFirst()){
            Encargado encargado = new Encargado();
            encargado.setIdEncargado(cursor.getString(0));
            encargado.setNomEncargado(cursor.getString(1));

            return encargado;
        }else{
            return null;
        }
    }

    public ArrayList<String>  getAllEncargados(){ // PM11074 =========
        ArrayList<String> encs = new ArrayList<String>();
        Cursor cursor = db.query("encargado", camposEncargado, null, null, null, null, null);
        if(cursor.moveToFirst()){
            encs.add("-- Seleccione Encargado --");  //  Primer elemento de la lsita siempre será el mensaje que debe elegir el encargado
            encs.add(cursor.getString(0) + " - " + cursor.getString(1));
            while (cursor.moveToNext()){
                encs.add(cursor.getString(0) + " - " + cursor.getString(1));
            }
        }else
            encs.add("-- No Hay Encargados --");

        return encs;
    }

    public String eliminar(Encargado encargado){  // PM11074 =========
        String regAfectados="El registro no existe";
        int contador=0;

        if (verificarIntegridad(encargado,1)) {
            contador += db.delete("encargado", "idEncargado='" + encargado.getIdEncargado() + "'", null);
            regAfectados = "filas afectadas = " + contador;
        }
        return regAfectados;
    }

    public String actualizar(Encargado encargado){  // PM11074 =========
        if(verificarIntegridad(encargado, 1)){
            String[] id = {encargado.getIdEncargado()};
            ContentValues cv = new ContentValues();
            cv.put("nomEncargado", encargado.getNomEncargado());
            db.update("encargado", cv, "idEncargado = ?", id);
            return "Registro Actualizado Correctamente";
        }else{
            return "Encargado con Id: " + encargado.getIdEncargado() + " no existe";
        }
    }

// LOCAL ======
    public String insertar(Local local){ // PM11074 =========
        String regInsertados="Registro Insertado Nº= ";
        long contador=0;

        ContentValues loc = new ContentValues();
        loc.put("idEncargado", local.getIdEncargado());
        loc.put("nomLocal", local.getNomLocal());
        loc.put("esInterno", local.getEsInterno());

        Encargado e = new Encargado(); // Pa verificar que exite el encargado asignado
        e.setIdEncargado(local.getIdEncargado());
        if(verificarIntegridad(e,1)) {
            contador = db.insert("local", null, loc);

            if (contador == -1 || contador == 0) {
                regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
            } else {
                regInsertados = regInsertados + contador;
            }
            return regInsertados;
        }
        else
            return "El encargado con Id: " + local.getIdEncargado() +" NO existe";
    }

    public Local consultarLocal(String idLoc) { // PM11074 =========
        String[] id = {idLoc};
        Cursor cursor = db.query("local", camposLocal, "idLocal = ?", id, null, null, null);

        if(cursor.moveToFirst()){
            Local local = new Local();
            local.setIdLocal(cursor.getString(0));

            String[] idE = {cursor.getString(1)}; // BUscar al encargado de este loacl
            Cursor cursor2 = db.query("encargado", camposEncargado, "idEncargado = ?", idE, null, null, null);
            if(cursor2.moveToFirst()) {
                local.setIdEncargado(cursor.getString(1) + " - " + cursor2.getString(1));
            }else{
                local.setIdEncargado("No tiene encargado Asignado");
            }

            local.setNomLocal(cursor.getString(2));
            local.setEsInterno(cursor.getInt(3));

            return local;
        }else{
            return null;
        }
    }

    public String eliminar(Local local){  // PM11074 =========
        String regAfectados="El registro no existe";
        int contador = 0;

        if (verificarIntegridad(local,2)) {
            contador += db.delete("local", "idLocal ='" + local.getIdLocal() + "'", null);
            regAfectados = "filas afectadas = " + contador;
        }
        return regAfectados;
    }

    public String actualizar(Local local){  // PM11074 =========
        if(verificarIntegridad(local, 2)){
            String[] id = {local.getIdLocal()};
            ContentValues cv = new ContentValues();

            cv.put("idLocal", local.getIdLocal());

            String[] idE = {local.getIdEncargado()}; // BUscar al encargado de este loacl
            Cursor cursor = db.query("encargado", camposEncargado, "idEncargado = ?", idE, null, null, null);
            if(cursor.moveToFirst()) {
                cv.put("idEncargado", local.getIdEncargado());
            }else{
                return "Encargado con Id " + local.getIdEncargado() + " no existe";
            }
            cv.put("nomLocal", local.getNomLocal());
            cv.put("esInterno", local.getEsInterno());

            db.update("local", cv, "idLocal = ?", id);
            return "Registro Actualizado Correctamente";
        }else{
            return "Local con Id " + local.getIdLocal() + " no existe";
        }
    }

// FACULTAD ======
    public String insertar(Facultad facultad){ // PM11074 =========
        String regInsertados = "Registro Insertado Nº= ";
        long contador = 0;

        ContentValues facu = new ContentValues();
        facu.put("idFacultad", facultad.getIdFacultad());
        facu.put("nomFacultad", facultad.getNomFacultad());

        contador = db.insert("facultad", null, facu);

        if(contador == -1 || contador == 0) {
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados = regInsertados + contador;
        }
        return regInsertados;
    }

    public Facultad consultarFacultad(String idFac){ // PM11074 =========
        String[] id = {idFac};
        Cursor cursor = db.query("facultad", camposFacultad, "idFacultad = ?", id, null, null, null);
        if(cursor.moveToFirst()){
            Facultad facultad = new Facultad();
            facultad.setIdFacultad(cursor.getString(0));
            facultad.setNomFacultad(cursor.getString(1));

            return facultad;
        }else{
            return null;
        }
    }

    public String eliminar(Facultad facultad){
        String regAfectados="El registro no existe";
        int contador = 0;

        if (verificarIntegridad(facultad,3)) {
            contador += db.delete("facultad", "idFacultad ='" + facultad.getIdFacultad() + "'", null);
            regAfectados = "filas afectadas = " + contador;
        }
        return regAfectados;
    }

    public String actualizar(Facultad facultad){
        if(verificarIntegridad(facultad, 3)){
            String[] id = {facultad.getIdFacultad()};
            ContentValues cv = new ContentValues();
            cv.put("idFacultad", facultad.getIdFacultad());
            cv.put("nomFacultad", facultad.getNomFacultad());
            db.update("facultad", cv, "idFacultad = ?", id);
            return "Registro Actualizado Correctamente";
        }else{
            return "Registro con Id " + facultad.getIdFacultad() + " no existe";
        }
    }

// ZONA ======
    public ArrayList<Zona> consultarZona(){
        ArrayList<Zona> zonas = new ArrayList<>();
        Cursor cursor =db.rawQuery("SELECT * FROM zona",null);
        if(cursor.moveToFirst()){
            do{
                Zona zona = new Zona();
                zona.setIdZona(cursor.getInt(0));
                zona.setNomZona(cursor.getString(1));
                zonas.add(zona);
            }while(cursor.moveToNext());
        }
        return zonas;
    }

    public String insertar(Zona zona){
        String regInsertados = "Registro Insertado Nº= ";
        long contador = 0;

        ContentValues zon = new ContentValues();
        zon.put("nomZona", zona.getNomZona());

        contador = db.insert("zona", null, zon);

        if(contador == -1 || contador == 0) {
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados = regInsertados + contador;
        }
        return regInsertados;
    }

 public String eliminarZona(Zona zona){
        String regAfectados="filas afectadas= ";
        int contador=0;
        if(zona.getIdZona()!=-1){
            contador+=db.delete("zona", "idZona='"+zona.getIdZona()+"'", null);
            regAfectados+=contador;
            return regAfectados;
        }else if(zona.getNomZona()!=null){
            contador+=db.delete("zona", "nomZona='"+zona.getNomZona()+"'", null);
            regAfectados+=contador;
            return regAfectados;
        }

        return regAfectados+"Registro no encontrado.";
 }

    public String actualizarZona(Zona zona){
            String[] id = {zona.getIdZona()+""};
            ContentValues cv = new ContentValues();
            cv.put("idZona", zona.getIdZona());
            cv.put("nomZona", zona.getNomZona());
         if(db.update("zona", cv, "idZona = ?", id)>0){
            return "Registro Actualizado Correctamente";
        }else{
            return "Registro con id " + zona.getIdZona() + " no existe";
        }
    }
//EMPLEADOS
    public String insertar(Empleado empleado){ // PM11074 ========
        String regInsertados = "Registro Insertado Nº= ";
        long contador = 0;

        ContentValues emp = new ContentValues();
        emp.put("idEmpleado", empleado.getIdEmpleado());
        emp.put("nomEmpleado", empleado.getNombreEmpleado());
        emp.put("apEmpleado", empleado.getApeEmpleado());
        emp.put("idLocal", empleado.getIdLocal());
        emp.put("idZona", empleado.getIdZona());


        contador = db.insert("empleado", null, emp);

        if(contador == -1 || contador == 0) {
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados = regInsertados + contador;
        }
        return regInsertados;
    }

    public String eliminarEmpleado(Empleado empleado){
        String regAfectados="filas afectadas= ";
        int contador=0;
        if(empleado.getIdEmpleado()!=-1){
            contador+=db.delete("empleado", "idEmpleado='"+empleado.getIdEmpleado()+"'", null);
            regAfectados+=contador;
            return regAfectados;
        }

        return regAfectados+"Registro no encontrado.";
    }

    public String actualizarEmpleado(Empleado empleado){
            String[] id = {empleado.getIdEmpleado()+""};
            ContentValues cv = new ContentValues();
            cv.put("idEmpleado", empleado.getIdEmpleado());
            cv.put("nomEmpleado", empleado.getNombreEmpleado());
            cv.put("apEmpleado", empleado.getApeEmpleado());
            cv.put("idLocal", empleado.getIdLocal());
            cv.put("idZona", empleado.getIdZona());
         if(db.update("empleado", cv, "idEmpleado = ?", id)>0){
            return "Registro Actualizado Correctamente";
        }else{
            return "Registro con id " + empleado.getIdEmpleado() + " no existe";
        }
    }

    public ArrayList<Empleado> consultarEmpleado(){ // PM11074 =========
        ArrayList<Empleado> empleados = new ArrayList<>();
        Cursor cursor =db.rawQuery("SELECT * FROM empleado",null);
        if(cursor.moveToFirst()){
            do{
                Empleado empleado = new Empleado();
                empleado.setIdEmpleado(cursor.getInt(0));
                empleado.setNombreEmpleado(cursor.getString(1));
                empleado.setApeEmpleado(cursor.getString(2));
                empleado.setIdLocal(cursor.getString(3));
                empleado.setIdZona(cursor.getInt(4));
                empleados.add(empleado);
            }while(cursor.moveToNext());
        }
        return empleados;
    }
    public Empleado obtenerEmpleado(String idEmpleado){
        Empleado empleado = new Empleado();
        String[] id = {idEmpleado};
        Cursor cursor = db.query("empleado", camposEmpleado, "idEmpleado = ?", id, null, null, null);
        if(cursor.moveToFirst()){
            empleado.setIdEmpleado(cursor.getInt(0));
            empleado.setNombreEmpleado(cursor.getString(1));
            empleado.setApeEmpleado(cursor.getString(2));
            empleado.setIdLocal(cursor.getString(3));
            empleado.setIdZona(cursor.getInt(4));
        }
        else
            empleado = null;
        return empleado;
    }

// PRODUCTO =======

    public String insertar(Producto producto){

        String regInsertados="Registro Insertado Nº= ";
        long contador = 0;

        ContentValues prod = new ContentValues();
        prod.put("idLocal", producto.getIdLocal());
        prod.put("idTipoProducto", producto.getIdTipoProducto());
        prod.put("nomProducto", producto.getNomProducto());
        prod.put("descripcion", producto.getDescripcion());

        if(verificarIntegridad(producto, 5)) {
            regInsertados = "Ya existe éste producto para éste Local con éste Precio y Tipo de Producto";
        }else{
            contador = db.insert("producto", null, prod);

            if (contador == -1 || contador == 0) {
                regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
            } else {
                regInsertados = regInsertados + contador;

                // Le creamos su primer precio a este producto
                PrecioProducto pp = new PrecioProducto();
                pp.setIdProducto(String.valueOf(contador));
                pp.setPrecioProducto(producto.getPrecio());
                pp.setEsActivo(1);
                insertar(pp);
            }
        }
        return regInsertados;
    }

    public Producto consultarProducto(String idProd){
        String[] id = {idProd};
        Cursor cursor = db.query("producto", camposProducto, "idProducto = ?", id, null, null, null);
        if(cursor.moveToFirst()){
            Producto prod = new Producto();
            prod.setIdProducto(cursor.getString(0));
            prod.setTipo(cursor.getString(2));
            prod.setNomProducto(cursor.getString(3));
            prod.setDescripcion(cursor.getString(4));

            //Obtenenmos el precio de este producto
            Cursor precio = db.query("precioProducto", camposPrecioProducto, "idProducto = ? AND esActivo = 1", id, null, null, null);
            String prec = "--.--"; // Si no lo tiene
            if(precio.moveToFirst())
                prec = precio.getString(2);
            prod.setPrecio(prec);

            String[] idTipo = {cursor.getString(2)};
            Cursor tipo = db.query("tipoProducto", camposTipoProducto, "idTipoProducto = ?", idTipo, null, null, null);
            String type = "No tiene Tipo asignado";
            if(tipo.moveToFirst())
                type = tipo.getString(1);

            prod.setIdTipoProducto(type);

            return prod;
        }else {
            return null;
        }
    }

    public String  actualizar(Producto producto){

        String[] id = {producto.getIdProducto()};

        ContentValues cv = new ContentValues();
        cv.put("idTipoProducto", producto.getIdTipoProducto());
        cv.put("nomProducto", producto.getNomProducto());
        cv.put("descripcion", producto.getDescripcion());

        db.update("producto", cv, "idProducto = ?", id);

        ContentValues cv2 = new ContentValues();// Actualizar el precio
        cv2.put("precioProducto", producto.getPrecio());
        if(db.update("precioProducto", cv2, "idProducto = ? AND esActivo = 1", id) == 1){
            Log.d("myTag", producto.getPrecio());
        }else{
            //Le creamos su primer precio a este producto
            PrecioProducto pp = new PrecioProducto();
            pp.setIdProducto(producto.getIdProducto());
            pp.setPrecioProducto(producto.getPrecio());
            pp.setEsActivo(1);
            insertar(pp);

            return "Producto " + producto.getNomProducto() + " no tenia precio Actualizado, Se creará con el valor ingresado en PRECIO";
        }
        return "Registro Actualizado Correctamente";
    }

    public ArrayList<String>  getAllLocales(){
        ArrayList<String> locs = new ArrayList<String>();
        Cursor cursor = db.query("local", camposLocal, null, null, null, null, null);
        if(cursor.moveToFirst()){
            locs.add("-- Seleccione Local --");  //  Primer elemento de la lsita siempre será el mensaje que debe elegir el local
            locs.add(cursor.getString(0) + " - " + cursor.getString(2));
            while (cursor.moveToNext()){
                locs.add(cursor.getString(0) + " - " + cursor.getString(2));
            }
        }else
            locs.add("-- No Hay Locales --");

        return locs;
    }

    public ArrayList<String>  getAllTipoProducto(){
        ArrayList<String> tp = new ArrayList<String>();
        Cursor cursor = db.query("tipoProducto", camposTipoProducto, null, null, null, null, null);
        if(cursor.moveToFirst()){
            tp.add("-- Seleccione Tipo de Producto --");  //  Primer elemento de la lsita siempre será el mensaje que debe elegir el local
            tp.add(cursor.getString(0) + " - " + cursor.getString(1));
            while (cursor.moveToNext()){
                tp.add(cursor.getString(0) + " - " + cursor.getString(1));
            }
        }else
            tp.add("- No Hay Tipos de Producto -");

        return tp;
    }

    public String eliminar(Producto producto){
        String regAfectados="El registro no existe";
        int contador = 0;

        if (verificarIntegridad(producto,7)) {
            contador += db.delete("precioProducto", "idProducto ='" + producto.getIdProducto() + "'", null);
        }
            contador += db.delete("producto", "idProducto ='" + producto.getIdProducto() + "'", null);
            regAfectados = "filas afectadas = " + contador;
        return regAfectados;
    }


// PRECIOPRODUCTO =======
    public String insertar(PrecioProducto precioP){ // PM11074 =========
        String regInsertados="Registro Insertado Nº= ";
        long contador = 0;

        if (verificarIntegridad(precioP,6)) {
            String[] idP = {precioP.getIdProducto()}; // BUscar el precioPorducto que esté activo y ponerlo inactivo
            Cursor cursor = db.query("precioProducto", camposPrecioProducto, "idProducto = ? AND esActivo = 1", idP, null, null, null);
            if (cursor.moveToFirst()) {
                String[] id = {cursor.getString(0)};
                ContentValues cv = new ContentValues();
                cv.put("esActivo", 0);
                db.update("precioProducto", cv, "idPrecioProducto = ?", id);
            }

            ContentValues pp = new ContentValues();
            pp.put("idProducto", precioP.getIdProducto());
            pp.put("precioProducto", precioP.getPrecioProducto());

            contador = db.insert("precioProducto", null, pp);

            if (contador == -1 || contador == 0)
                regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
            else
                regInsertados = regInsertados + contador;

            return regInsertados;
        }
        return "Error al Insertar, Ya existe este precio actual para este producto";
    }

    public ArrayList<PrecioProducto> consultarPrecioProducto(String idProducto, int tipo){
        ArrayList<PrecioProducto> precioProd = new ArrayList<PrecioProducto>();
        String[] id = {idProducto};
        Cursor cursor;
        if(tipo == 0) // Devolvemos todos los precios
            cursor = db.rawQuery("SELECT * FROM precioProducto WHERE idProducto = ? ORDER BY esActivo DESC",id);
        else   //Devolvemos solo el precio activo
            cursor = db.query("precioProducto", camposPrecioProducto, "idProducto = ? AND esActivo = 1", id, null, null, null);

        if(cursor.moveToFirst()){
            do{
                PrecioProducto pp = new PrecioProducto();
                pp.setIdPrecioProducto(cursor.getString(0));
                pp.setEsActivo(cursor.getInt(3));
                pp.setPrecioProducto(cursor.getString(2));
                pp.setFechaPrecio(cursor.getString(4));
                precioProd.add(pp);
            }while(cursor.moveToNext());
        }
        return precioProd;
    }

    public ArrayList<String>  getAllProductos(String id){ // PM11074 =========
        ArrayList<String> prods = new ArrayList<String>();
        Cursor cursor;

        if(id == "")
            cursor = db.query("producto", camposProducto, null, null, null, null, null);
        else
            cursor = db.query("producto", camposProducto, "idLocal = ?", new String[]{id}, null, null, null);

        prods.add("-- Seleccione Producto -");  //  Primer elemento de la lsita siempre será el mensaje que debe elegir el producto
        if(cursor.moveToFirst()){
            prods.add(cursor.getString(0) + " - " + cursor.getString(3) + "");
            while (cursor.moveToNext()){
                prods.add(cursor.getString(0) + " - " + cursor.getString(3));
            }
        }else
            prods.add("- No Hay Productos -");

        return prods;
    }

    public String actualizar(PrecioProducto precioProd){
        if(verificarIntegridad(precioProd, 4)){
            String[] id = {precioProd.getIdPrecioProducto()};
            ContentValues cv = new ContentValues();
            cv.put("idPrecioProducto", precioProd.getIdPrecioProducto());
            cv.put("precioProducto", precioProd.getPrecioProducto());
            db.update("precioProducto", cv, "idPrecioProducto = ?", id);
            return "Registro Actualizado Correctamente";
        }else{
            return "Registro con Id " + precioProd.getIdPrecioProducto() + " no existe";
        }
    }

    public String eliminar(PrecioProducto pp){
        String regAfectados="El registro no existe";
        int contador = 0;

        if (verificarIntegridad(pp,4)) {
            contador += db.delete("precioProducto", "idPrecioProducto ='" + pp.getIdPrecioProducto() + "'", null);
            regAfectados = "filas afectadas = " + contador;
        }
        return regAfectados;
    }

// TIPOPRODUCTO =========================
    public String insertar(TipoProducto tipoProducto){ // PM11074 =========
        String regInsertados="Registro Insertado Nº= ";
        long contador = 0;

        ContentValues tp = new ContentValues();
        tp.put("tipo", tipoProducto.getTipo());

        if(verificarIntegridad(tipoProducto, 8)) {
            contador = db.insert("tipoProducto", null, tp);
            regInsertados = regInsertados + contador;

            if (contador == -1 || contador == 0) {
                regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
            }

            return regInsertados;
        }
        return "Ya existe un tipo de producto con el nombre " + tipoProducto.getTipo();
    }

    public TipoProducto consultarTipoProducto(String idTipo){ // PM11074 =========
        String[] id = {idTipo};
        Cursor cursor = db.query("tipoProducto", new String[]{"idTipoProducto","tipo"}, "idTipoProducto = ?", id, null, null, null);
        if(cursor.moveToFirst()){
            TipoProducto tipo = new TipoProducto();
            tipo.setIdTipoProducto(cursor.getString(0));
            tipo.setTipo(cursor.getString(1));

            return tipo;
        }else{
            return null;
        }
    }

    public String actualizar(TipoProducto tipo){  // PM11074 =========
        if(verificarIntegridad(tipo, 9)){
            String[] id = {tipo.getIdTipoProducto()};
            ContentValues cv = new ContentValues();
            cv.put("tipo", tipo.getTipo());
            db.update("tipoProducto", cv, "idTipoProducto = ?", id);
            return "Registro Actualizado Correctamente";
        }else{
            return "Tipo de Producto con Id: " + tipo.getIdTipoProducto() + " no existe";
        }
    }

    public String eliminar(TipoProducto tipo){  // PM11074 =========
        String regAfectados="El registro no existe";
        int contador=0;

        if (verificarIntegridad(tipo,9)) {
            contador += db.delete("tipoProducto", "idTipoProducto ='" + tipo.getIdTipoProducto() + "'", null);
            regAfectados = "filas afectadas = " + contador;
        }
        return regAfectados;
    }

//COMBO ==========
    public String actualizar(Combo combo){ // PM11074 =========

        ContentValues com = new ContentValues();
        com.put("idLocal", combo.getIdLocal());
        com.put("nomCombo", combo.getNomCombo());
        com.put("precioCombo", combo.getPrecioCombo());
        com.put("disponible", combo.getDisponible());


        String[] id = {combo.getIdCombo()};

        db.update("combo", com, "idCombo = ?", id);

        db.delete("comboProducto", "idCombo ='" + combo.getIdCombo() + "'", null);
        ArrayList<String> prodsIds = combo.getProdComboIds();
        for (String pi : prodsIds) {
            ContentValues comboProd = new ContentValues();
            comboProd.put("idCombo", combo.getIdCombo());
            comboProd.put("idProducto", pi);
            db.insert("comboProducto", null, comboProd);
        }
        return "Registro Actualizado Correctamente";
    }

    public Combo consultarCombo(String idCombo){ // PM11074 =========
        String[] id = {idCombo};
        Cursor cursor = db.query("combo", camposCombo, "idCombo = ?", id, null, null, null);
        if(cursor.moveToFirst()){
            Combo combo = new Combo();
            combo.setIdCombo(cursor.getString(0));
            combo.setIdLocal(cursor.getString(1));
            combo.setNomCombo(cursor.getString(2));
            combo.setPrecioCombo(cursor.getString(3));
            combo.setDisponible(cursor.getInt(4));

            Cursor cursor2 = db.query("comboProducto", new String[]{"idCombo","idProducto"}, "idCombo = ?", id, null, null, null);
            ArrayList<String> ids = new ArrayList<String>();
            if(cursor2.moveToFirst()){
                ids.add(cursor2.getString(1));
                while (cursor2.moveToNext()){
                    ids.add(cursor2.getString(1));
                }
            }
            combo.setProdComboIds(ids);

            ArrayList<String> productos = new ArrayList<String>();
            Cursor cursor3;
            for (String prodId : ids) {
                cursor3 = db.query("producto", camposProducto, "idProducto = ?", new String[]{prodId}, null, null, null);
                if(cursor3.moveToFirst()){
                    productos.add(cursor3.getString(3));
                }
            }
            combo.setProdComboNom(productos);

            return combo;
        }else{
            return null;
        }
    }

    public ArrayList<String>  getAllCombos(String id){ // PM11074 =========
        ArrayList<String> combos = new ArrayList<String>();
        Cursor cursor;

        if(id == "")
            cursor = db.query("combo", camposCombo, null, null, null, null, null);
        else
            cursor = db.query("combo", camposCombo, "idLocal = ?", new String[]{id}, null, null, null);

        if(cursor.moveToFirst()){
            combos.add("-- Seleccione Combo --");  //  Primer elemento de la lsita siempre será el mensaje que debe elegir el producto
            combos.add(cursor.getString(0) + " - " + cursor.getString(2));
            while (cursor.moveToNext()){
                combos.add(cursor.getString(0) + " - " + cursor.getString(2));
            }
        }else
            combos.add("-- No Hay Combos --");

        return combos;
    }

    public String insertar(Combo combo){ // PM11074 =========
        String regInsertados = "Registro Insertado Nº= ";
        long contador = 0;

        ContentValues com = new ContentValues();
        com.put("idLocal", combo.getIdLocal());
        com.put("nomCombo", combo.getNomCombo());
        com.put("precioCombo", combo.getPrecioCombo());
        com.put("disponible", 1);

        if(verificarIntegridad(combo, 10)) {
            contador = db.insert("combo", null, com);

            if (contador == -1 || contador == 0) {
                regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
            } else {
                regInsertados = regInsertados + contador; // Si se creo el combo le agregamos los productos
                ArrayList<String> prodsIds = combo.getProdComboIds();
                for (String pi : prodsIds) {
                    ContentValues comboProd = new ContentValues();
                    comboProd.put("idCombo", contador);
                    comboProd.put("idProducto", pi);
                    db.insert("comboProducto", null, comboProd);
                }
            }
            return regInsertados;
        }else
            return "Ya existe este combo en este local";
    }

    public String eliminar(Combo combo){
        String regAfectados = "El registro no existe";
        int contador = 0;

        if (verificarIntegridad(combo,11)) {
            contador += db.delete("comboProducto", "idCombo ='" + combo.getIdCombo() + "'", null);
            contador += db.delete("combo", "idCombo ='" + combo.getIdCombo() + "'", null);
            regAfectados = "filas afectadas = " + contador;
        }
        return regAfectados;
    }

//PEDIDO =========================

    public String insertar(Pedido pedido){ // PM11074 =========
        String regInsertados = "Pedido guardado, cosultelo con el numero de pedido: ";
        long contador = 0;

        ContentValues ped = new ContentValues();
        ped.put("idUsuario", pedido.getIdUsuario());
        ped.put("idLocal", pedido.getIdLocal());
        ped.put("idTipoPedido", pedido.getIdTipoPedido());
        ped.put("idTipoPago", pedido.getIdTipoPago());
        ped.put("monto", pedido.getMonto());
        ped.put("pagado", 0);

        if(verificarIntegridad(pedido, 12)) {
            contador = db.insert("pedido", null, ped);

            if (contador == -1 || contador == 0) {
                regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
            } else {
                regInsertados = regInsertados + contador; // Si se creo el pedido le agregamos los productos y combos
                ArrayList<String> prodsIds = pedido.getProdIds();
                ArrayList<Integer> prodsCant = pedido.getProdCant();
                for (int i = 0; i < prodsIds.size(); i++) {
                    ContentValues p = new ContentValues();
                    p.put("idPedido", contador);
                    p.put("idLocal", pedido.getIdLocal());
                    p.put("idProducto", prodsIds.get(i));
                    p.put("cantidadProducto", prodsCant.get(i));
                    db.insert("detallePedido", null, p);
                }

                ArrayList<String> comboIds = pedido.getComboIds();
                ArrayList<Integer> comboCant = pedido.getComboCant();
                for (int i = 0; i < comboIds.size(); i++) {
                    ContentValues p = new ContentValues();
                    p.put("idPedido", contador);
                    p.put("idLocal", pedido.getIdLocal());
                    p.put("idCombo", comboIds.get(i));
                    p.put("cantidadCombo", comboCant.get(i));
                    db.insert("detallePedido", null, p);
                }
            }
            return regInsertados;
        }
        return "Ya existe este pedido";
    }

    public Pedido consultarPedido(String idPedido) { // PM11074 =========
        String[] id = {idPedido};
        Cursor cursor = db.query("pedido", camposPedido, "idPedido = ?", id, null, null, null);


        ArrayList<String> listado = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            Pedido pedido = new Pedido();
            pedido.setIdPedido(cursor.getString(0));
            pedido.setMonto("$ "+cursor.getString(5));
            pedido.setFechaPedido(cursor.getString(6));
            pedido.setPagado(cursor.getInt(7));

            Cursor local = db.rawQuery("select nomLocal from local where idLocal = '" + cursor.getString(2) + "'", null);
            if (local.moveToFirst())
                pedido.setIdLocal(local.getString(0));

            Cursor tipoPago = db.rawQuery("select nomTipoPago from tipoPago where idTipoPago = '" + cursor.getString(3) + "'", null);
            if (tipoPago.moveToFirst())
                pedido.setIdTipoPago(tipoPago.getString(0));

            Cursor tipoPedido = db.rawQuery("select nomTipoPedido from tipoPedido where idTipoPedido = '" + cursor.getString(4) + "'", null);
            if (tipoPedido.moveToFirst())
                pedido.setIdTipoPedido(tipoPedido.getString(0));

            Cursor rawProductos = db.rawQuery("select p.idProducto, p.nomProducto, d.cantidadProducto from producto p inner join detallePedido d on p.idProducto= d.idProducto and d.idPedido = '"+idPedido+"'", null);
            if(rawProductos.moveToFirst()){
                listado.add(rawProductos.getString(1) + " -     X" + rawProductos.getString(2));
                while (rawProductos.moveToNext()){
                    listado.add(rawProductos.getString(1) + " -     X" + rawProductos.getString(2));
                }
            }
            pedido.setProdNames(listado);

            ArrayList<String> contenido = new ArrayList<>();
            Cursor rawCombos = db.rawQuery("select p.idCombo, p.nomCombo, d.cantidadCombo from combo p inner join detallePedido d on p.idCombo = d.idCombo and d.idPedido = '"+idPedido+"'", null);
            if(rawCombos.moveToFirst()){
                contenido.add(rawCombos.getString(1) + " -     X" + rawCombos.getString(2));
                while (rawCombos.moveToNext()){
                    contenido.add(rawCombos.getString(1) + " -     X" + rawCombos.getString(2));
                }
            }

            pedido.setComboNames(contenido);

            return pedido;
        } else
            return null;

    }

    public String eliminar(Pedido pedido){
        String regAfectados = "El registro no existe";
        int contador = 0;

        if (verificarIntegridad(pedido,13)) {
            contador += db.delete("detallePedido", "idPedido ='" + pedido.getIdPedido() + "'", null);
            contador += db.delete("pedido", "idPedido ='" + pedido.getIdPedido() + "'", null);
            regAfectados = "filas afectadas = " + contador;
        }
        return regAfectados;
    }



    //MONTO==========
    public ArrayList<String>  getMontos(String idLocal){ // PM11074 =========
        ArrayList<String> montos = new ArrayList<String>();
        Cursor cursor = db.query("monto", new String[]{"idLocal","max","min"}, "idLocal = ?", new String[]{idLocal}, null, null, null);

        if(cursor.moveToFirst()){
            montos.add(cursor.getString(1));
            montos.add(cursor.getString(2));
        }
        return montos;
    }


    public ArrayList<String>  getAllTipoPedido(){ // PM11074 =========
        ArrayList<String> tipoPedido = new ArrayList<String>();
        Cursor cursor = db.query("tipoPedido", new String[]{"idTipoPedido","nomTipoPedido"}, null, null, null, null, null);

        if(cursor.moveToFirst()){
            tipoPedido.add("- Tipo de Pedido -");  //  Primer elemento de la lsita siempre será el mensaje que debe elegir el producto
            tipoPedido.add(cursor.getString(0) + " - " + cursor.getString(1));
            while (cursor.moveToNext()){
                tipoPedido.add(cursor.getString(0) + " - " + cursor.getString(1));
            }
        }else
            tipoPedido.add("- No Hay Tipos de pedido -");

        return tipoPedido;
    }

    public ArrayList<String>  getAllTipoPago(){ // PM11074 =========
        ArrayList<String> tipoPago = new ArrayList<String>();
        Cursor cursor = db.query("tipoPago", new String[]{"idTipoPago","nomTipoPago"}, null, null, null, null, null);

        if(cursor.moveToFirst()){
            tipoPago.add(" Tipo de Pago -");  //  Primer elemento de la lsita siempre será el mensaje que debe elegir el producto
            tipoPago.add(cursor.getString(0) +  " - " + cursor.getString(1));
            while (cursor.moveToNext()){
                tipoPago.add(cursor.getString(0) +  " - " + cursor.getString(1));
            }
        }else
            tipoPago.add(" No Hay Tipos de Pago -");

        return tipoPago;
    }

    public ArrayList<Producto>  allProductos(String id){ // PM11074 =========
        ArrayList<Producto> productos = new ArrayList<Producto>();
        Cursor cursor = db.query("producto", camposProducto, "idLocal = ?", new String[]{id}, null, null, null);

        if(cursor.moveToFirst()){
            Producto pr = new Producto();
            pr.setNomProducto(" Seleccione Producto -");
            productos.add(pr);
            do{
                Producto c = new Producto();
                c.setIdProducto(cursor.getString(0));
                c.setIdLocal(cursor.getString(1));
                c.setIdTipoProducto(cursor.getString(2));
                c.setNomProducto(cursor.getString(3));
                c.setDescripcion(cursor.getString(4));
                //Obtenenmos el precio de este producto
                Cursor precio = db.query("precioProducto", camposPrecioProducto, "idProducto = ? AND esActivo = 1", new String[]{cursor.getString(0)}, null, null, null);
                String prec = ""; // Si no lo tiene
                if(precio.moveToFirst())
                    prec = precio.getString(2);
                c.setPrecio(prec);
                if(prec != null)
                    productos.add(c);
            }while(cursor.moveToNext());
        }else{
            Producto c = new Producto();
            c.setNomProducto(" No Hay Productos -");
            productos.add(c);
        }

        return productos;
    }

    public ArrayList<Combo>  allCombos(String id){ // PM11074 =========
        ArrayList<Combo> combos = new ArrayList<Combo>();
        Cursor cursor;

        if(id == "")
            cursor = db.query("combo", camposCombo, null, null, null, null, null);
        else
            cursor = db.query("combo", camposCombo, "idLocal = ? AND disponible = 1", new String[]{id}, null, null, null);

        if(cursor.moveToFirst()){
            Combo ci = new Combo();
            ci.setNomCombo("- Seleccione Combo -");
            combos.add(ci);
            do{
                Combo c = new Combo();
                c.setIdCombo(cursor.getString(0));
                c.setIdLocal(cursor.getString(1));
                c.setNomCombo(cursor.getString(2));
                c.setPrecioCombo(cursor.getString(3));
                c.setDisponible(cursor.getInt(4));
                combos.add(c);
            }while(cursor.moveToNext());
        }else{
            Combo c = new Combo();
            c.setNomCombo("- No Hay Combos -");
            combos.add(c);
        }

        return combos;
    }





    public String validarUsuario(String user, String clave){
        String[] id = {user, clave};
        Cursor cursor = db.query("usuario", camposUsuario, "nomUsuario = ? AND clave = ?", id, null, null, null);

        if(cursor.moveToFirst()){
            return cursor.getString(0);
        }
        else
            return "false";
    }

    public String consultarPermisosUsuario(String idUser){
        String[] id = {idUser};
        Cursor cursor = db.query("accesoUsuario", camposAccesoUsuario, "idUsuario = ?", id, null, null, "idUsuario ASC");
        String res = "";
        cursor.moveToFirst();
        res += cursor.getString(1);
        while (cursor.moveToNext()){
            res += "," + cursor.getString(1);
        }
        return res;
    }

    public static ArrayList<String> retornarArrayPermisos(String cadena){
        ArrayList<String> res = new ArrayList<String>();
        String[] sbstr = cadena.split(",");
        for (String e: sbstr) {
            res.add(e);
        }
        return res;
    }




    // VERIFICACION DE INTEGRIDAD ============================================================================================
    private boolean verificarIntegridad(Object dato, int relacion) throws SQLException{
        switch(relacion){
            case 1:
            {        //verificar que exista Encargado
                Encargado encargado = (Encargado) dato;
                String[] idm = {encargado.getIdEncargado()};
                abrir();
                Cursor cm = db.query("encargado", null, "idEncargado = ?", idm, null, null, null);
                if(cm.moveToFirst()){
                    return true;
                }
                return false;
            }
            case 2:
            {        //verificar que exista Local
                Local loc = (Local) dato;
                String[] idm = {loc.getIdLocal()};
                abrir();
                Cursor cm = db.query("local", null, "idLocal = ?", idm, null, null, null);
                if(cm.moveToFirst()){
                    return true;
                }
                return false;
            }
            case 3:
            {        //verificar que exista Facultad
                Facultad fac = (Facultad) dato;
                String[] idm = {fac.getIdFacultad()};
                abrir();
                Cursor cm = db.query("facultad", null, "idFacultad = ?", idm, null, null, null);
                if(cm.moveToFirst()){
                    return true;
                }
                return false;
            }
            case 4:
            {        //verificar  exista el precio
                PrecioProducto pp = (PrecioProducto) dato;
                String[] idm = {pp.getIdPrecioProducto()};
                abrir();
                Cursor cm = db.query("precioProducto", null, "idPrecioProducto = ?", idm, null, null, null);
                if(cm.moveToFirst()){
                    return true;
                }
                return false;
            }
            case 5:
            {        //verificar no exista mismo producto con el  mismo precio en el mismo local
                Producto p = (Producto) dato;
                String[] ids = {p.getNomProducto(), p.getIdLocal(), p.getIdTipoProducto()};
                abrir();
                Cursor cp = db.query("producto", camposProducto, "nomProducto = ? AND idLocal = ? AND idTipoProducto = ?", ids, null, null, null);

                if(cp.moveToFirst()){
                    String[] idPrecio = {cp.getString(0), p.getPrecio()};
                    Cursor cpp = db.query("precioProducto", null, "idProducto = ? AND precioProducto = ?", idPrecio, null, null, null);
                    if(cpp.moveToFirst())
                        return true;
                }
                return false;
            }
            case 6:
            {   //Verificar que el nuevo precioProducto no sea el miso que el precio activo
                PrecioProducto pp = (PrecioProducto) dato;
                String[] values = {pp.getIdProducto(), pp.getPrecioProducto()};
                Cursor precio = db.query("precioProducto", null, "idProducto = ? AND precioProducto = ? AND esActivo = 1", values, null, null, null);
                if(precio.moveToFirst()){
                    return false;
                }
                return true;
            }
            case 7:
            {        //verificar  exista el Producto
                Producto producto = (Producto) dato;
                String[] idm = {producto.getIdProducto()};
                abrir();
                Cursor cm = db.query("producto", null, "idProducto = ?", idm, null, null, null);
                if(cm.moveToFirst()){
                    return true;
                }
                return false;
            }
            case 8:
            {        //verificar NO exista un TipoProducto con nombre duplicado
                TipoProducto tp = (TipoProducto) dato;
                String[] t = {tp.getTipo()};
                abrir();
                Cursor cm = db.query("tipoProducto", null, "tipo = ?", t, null, null, null);
                if(cm.moveToFirst()){
                    return false;
                }
                return true;
            }
            case 9:
            {        //verificar que exista el TipoProducto
                TipoProducto tp = (TipoProducto) dato;
                String[] t = {tp.getIdTipoProducto()};
                abrir();
                Cursor cm = db.query("tipoProducto", null, "idTipoProducto = ?", t, null, null, null);
                if(cm.moveToFirst()){
                    return true;
                }
                return false;
            }
            case 10:
            {        //verificar que NO exista el mismo combo en el miso local
                Combo c = (Combo) dato;
                String[] t = {c.getNomCombo(),c.getIdLocal()};
                abrir();
                Cursor cm = db.query("combo", null, "nomCombo = ? AND idLocal = ?", t, null, null, null);
                if(cm.moveToFirst()){
                    return false;
                }
                return true;
            }
            case 11:
            {        //verificar que exista el Combo
                Combo combo = (Combo) dato;
                String[] t = {combo.getIdCombo()};
                abrir();
                Cursor cm = db.query("combo", null, "idCombo = ?", t, null, null, null);
                if(cm.moveToFirst()){
                    return true;
                }
                return false;
            }
            case 12:
            {        //verificar que no se repita el pedido
                Pedido pedido = (Pedido) dato;
                String[] t = {pedido.getIdLocal(), pedido.getIdUsuario(),pedido.getMonto()};
                abrir();

                Cursor cm = db.query("pedido", null, "idLocal = ? AND idUsuario = ? AND monto = ?", t, null, null, null);
                if(cm.moveToFirst()){
                    return false;
                }
                return true;
            }
            case 13:
            {        //verificar que exista el Pedido
                Pedido p = (Pedido) dato;
                String[] t = {p.getIdPedido()};
                abrir();
                Cursor cm = db.query("pedido", null, "idPedido = ?", t, null, null, null);
                if(cm.moveToFirst()){
                    return true;
                }
                return false;
            }
            default:
                return false;
        }
    }

}
