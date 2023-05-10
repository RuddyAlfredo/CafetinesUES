package sv.ues.fia.eisi.cafetinesues;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

import sv.ues.fia.eisi.cafetinesues.Modelos.AccesoUsuario;
import sv.ues.fia.eisi.cafetinesues.Modelos.OpcionCRUD;
import sv.ues.fia.eisi.cafetinesues.Modelos.Usuario;
import sv.ues.fia.eisi.cafetinesues.pm11074.Encargado.Encargado;
import sv.ues.fia.eisi.cafetinesues.pm11074.Facultad.Facultad;
import sv.ues.fia.eisi.cafetinesues.pm11074.Local.Local;

public class ControlBD {

//DEFINICIÓN DE CAMPOS PARA LAS TABLAS---------------------------------------------
    private static final String[] camposUsuario = new String[] {"idUsuario","nomUsuario","clave"};
    private static final String[] camposAccesoUsuario = new String[] {"idUsuario","idOpcion"};
    private static final String[] camposOpcionCrud = new String[] {"idOpcion","desOpcion","numCrud"};

    private static final String[] camposEncargado = new String[] {"idEncargado","nomEncargado"};



//---------------------------------------------------------------------------------

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

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
                db.execSQL("CREATE TABLE usuario( idUsuario VARCHAR(2) PRIMARY KEY, nomUsuario VARCHAR(30), clave VARCHAR(5));");
                db.execSQL("CREATE TABLE opcionCrud(idOpcion VARCHAR(3), desOpcion VARCHAR(30), numCrud INTEGER);");
                db.execSQL("CREATE TABLE accesoUsuario( idUsuario VARCHAR(2), idOpcion VARCHAR(3), PRIMARY KEY(idUsuario, idOpcion), FOREIGN KEY(idUsuario) REFERENCES usuario(idUsuario), FOREIGN KEY(idOpcion) REFERENCES opcionCrud(idOpcion));");

                db.execSQL("CREATE TABLE encargado( idEncargado VARCHAR(2) PRIMARY KEY, nomEncargado VARCHAR(30) NOT NULL);");
                db.execSQL("CREATE TABLE local( idLocal VARCHAR(2) PRIMARY KEY, idEncargado VARCHAR(2), nomLocal VARCHAR(30) NOT NULL, FOREIGN KEY(idEncargado) REFERENCES encargado(idEncargado));");
                db.execSQL("CREATE TABLE facultad( idFacultad VARCHAR(2) PRIMARY KEY, nomFacultad VARCHAR(30) NOT NULL);");


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
                "001","002","003","004","005","006", // ID's para opciones de gestionar (se reserva desde el 001 hasta el 020)
                "021","022","023","024", // ID's para las opciones del CRUD de Encargado.
                "025","026","027","028", // ID's para las opciones del CRUD de Local.
                "029","030","031","032", // ID's para las opciones del CRUD de Facultad.
                "033","034","035","036", // ID's para las opciones del CRUD de Pedido.
        };
        final String[] valDesOpcion = {
                "Gestionar Encargados","Gestionar Locales","Gestionar Facultades","Gestionar Pedido","Gestionar Combo","Gestionar Combo/Producto",
                "Insertar Encargado","Consultar Encargado","Borrar Encargado","Actualizar Encargado", //CRUD Encargados.
                "Insertar Local","Consultar Local","Borrar Local","Actualizar Local", //CRUD Locales.
                "Insertar Facultad","Consultar Facultad","Borrar Facultad","Actualizar Facultad", //CRUD ingredientes.
                "Insertar Pedido", "Consultar Pedido","Eliminar Pedido","Actualizar Pedido" // CRUD Pedidos.
        };
        final int[] valNumCrud = {
                0, 0, 0, 0, 0, 0,
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4
        };

    // VALORES PARA ACCESOUSUARIO-------( POR AHORA SON 26)----------------------------
        final String[] valAccesoIdUsuario = {
                "01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01",// ID del Admin (22 veces)
                "02","02","02","02","02",  // ID del cliente1 3 veces pues solo le permitimos 3 opciones
                "03","03","03","03","03"    // ID del encargado1 (para ejemplo solo le permitimos crear y consultar INGREDIENTE)
        };
        final String[] valAccesoIdOpcion = {                // El admin tiene acceso a todo (las 22 opciones hasta ahora)
                "001","002","003","004","005","006", "021","022","023","024","025","026","027","028","029","030","031","032","033","034","035","036",
                "004","033","034","035","036",  // El cliente puede ver el la gestion de Pedido y solo crear/consultar (por eso 3 opciones)
                "002","025","026","027","028"  // El encargado 1 puede ver el la gestion de Productos y solo crear/consultar (por eso 3 opciones)
        };

// TODO Poner las demás tablas


        abrir();
        db.execSQL("DELETE FROM usuario");
        db.execSQL("DELETE FROM opcionCrud");
        db.execSQL("DELETE FROM accesoUsuario");



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




        cerrar();

    }

//   ENCARGADO ======
    public String insertar(Encargado encargado){ // PM11074 =========
        String regInsertados="Registro Insertado Nº= ";
        long contador=0;
        
        ContentValues encar = new ContentValues();
        encar.put("idEncargado", encargado.getIdEncargado());
        encar.put("nomEncargado", encargado.getNomEncargado());

        contador = db.insert("encargado", null, encar);

        if(contador==-1 || contador==0) {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados = regInsertados + contador;
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

    public String eliminar(Encargado encargado){  // PM11074 =========
        String regAfectados="El registro no existe";
        int contador=0;

       /* if (verificarIntegridad(encargado,1)) {
            contador += db.delete("local", "idEncargado='"+encargado.getIdEncargado()+"'", null);
        }*/
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
            cv.put("idEncargado", encargado.getIdEncargado());
            cv.put("nomEncargado", encargado.getNomEncargado());
            db.update("encargado", cv, "idEncargado = ?", id);
            return "Registro Actualizado Correctamente";
        }else{
            return "Registro con carnet " + encargado.getIdEncargado() + " no existe";
        }
    }

// LOCAL ======



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
            default:
                return false;
        }
    }

}
