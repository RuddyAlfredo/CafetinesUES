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
import sv.ues.fia.eisi.cafetinesues.pm11074.Zona.Zona;

public class ControlBD {

//DEFINICIÓN DE CAMPOS PARA LAS TABLAS---------------------------------------------
    private static final String[] camposUsuario = new String[] {"idUsuario","nomUsuario","clave"};
    private static final String[] camposAccesoUsuario = new String[] {"idUsuario","idOpcion"};
    private static final String[] camposOpcionCrud = new String[] {"idOpcion","desOpcion","numCrud"};

    private static final String[] camposEncargado = new String[] {"idEncargado","nomEncargado"};
    private static final String[] camposLocal = new String[] {"idLocal","idEncargado","nomLocal","esInterno"};
    private static final String[] camposFacultad = new String[] {"idFacultad","nomFacultad"};

    private static final String[] camposZona = new String[] {"idZona","nomZona"};


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
                db.execSQL("CREATE TABLE usuario( idUsuario VARCHAR(2) PRIMARY KEY, nomUsuario VARCHAR(50), clave VARCHAR(5));");
                db.execSQL("CREATE TABLE opcionCrud(idOpcion VARCHAR(3), desOpcion VARCHAR(50), numCrud INTEGER);");
                db.execSQL("CREATE TABLE accesoUsuario( idUsuario VARCHAR(2), idOpcion VARCHAR(3), PRIMARY KEY(idUsuario, idOpcion),CONSTRAINT fk_usuario FOREIGN KEY(idUsuario) REFERENCES usuario(idUsuario),CONSTRAINT fk_opcion FOREIGN KEY(idOpcion) REFERENCES opcionCrud(idOpcion));");

                db.execSQL("CREATE TABLE encargado( idEncargado INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nomEncargado VARCHAR(50) NOT NULL);");
                db.execSQL("CREATE TABLE local( idLocal INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, idEncargado VARCHAR(2), nomLocal VARCHAR(50) NOT NULL, esInterno INTEGER, CONSTRAINT fk_encargado FOREIGN KEY(idEncargado) REFERENCES encargado(idEncargado) ON DELETE RESTRICT);");
                db.execSQL("CREATE TABLE facultad( idFacultad INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nomFacultad VARCHAR(50) NOT NULL);");

                db.execSQL("CREATE TABLE zona ( idZona INTEGER PRIMARY KEY, nomZona VARCHAR(30) NOT NULL);");
                db.execSQL("CREATE TABLE empleado( idEmpleado VARCHAR(5) PRIMARY KEY, idZona VARCHAR(2), idLocal VARCHAR(2), nomEmpleado VARCHAR(30) NOT NULL, CONSTRAINT fk_zona FOREIGN KEY(idZona) REFERENCES zona(idZona), CONSTRAINT fk_local FOREIGN KEY(idLocal) REFERENCES local(idLocal) ON DELETE RESTRICT);");
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
                "001","002","003","004","005","006","007","008", // ID's para opciones de gestionar (se reserva desde el 001 hasta el 020)
                "021","022","023","024", // ID's para las opciones del CRUD de Encargado.
                "025","026","027","028", // ID's para las opciones del CRUD de Local.
                "029","030","031","032", // ID's para las opciones del CRUD de Facultad.
                "033","034","035","036", // ID's para las opciones del CRUD de Pedido.
                "037","038","039","040", // ID's para las opciones del CRUD de ZONA.
                "041","042","043","044", // ID's para las opciones del CRUD de Producto.
        };
        final String[] valDesOpcion = {
                "Gestionar Encargados","Gestionar Locales","Gestionar Facultades","Gestionar Pedido","Gestionar Combo","Gestionar Combo/Producto","Gestionar Zona","Gestionar Producto",
                "Insertar Encargado","Consultar Encargado","Borrar Encargado","Actualizar Encargado", //CRUD Encargados.
                "Insertar Local","Consultar Local","Borrar Local","Actualizar Local", //CRUD Locales.
                "Insertar Facultad","Consultar Facultad","Borrar Facultad","Actualizar Facultad", //CRUD ingredientes.
                "Insertar Pedido", "Consultar Pedido","Eliminar Pedido","Actualizar Pedido", // CRUD Pedidos.
                "Insertar Zona", "Consultar Zona","Eliminar Zona","Actualizar Zona", // CRUD Zona.
                "Insertar Producto", "Consultar Producto","Eliminar Producto","Actualizar Producto" // CRUD Producto.
        };
        final int[] valNumCrud = {
                0, 0, 0, 0, 0, 0, 0, 0,
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
        };

    // VALORES PARA ACCESOUSUARIO-------( POR AHORA SON 26)----------------------------
        final String[] valAccesoIdUsuario = {
                "01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01","01",// ID del Admin (22 veces)
                "02","02","02","02",  // ID del cliente1 3 veces pues solo le permitimos 3 opciones
                "03","03","03","03","03"    // ID del encargado1 (para ejemplo solo le permitimos crear y consultar INGREDIENTE)
        };
        final String[] valAccesoIdOpcion = {                // El admin tiene acceso a todo (las 22 opciones hasta ahora)
                "001","002","003","004","005","006","007","008","021","022","023","024","025","026","027","028","029","030","031","032","033","034","035","036","037","038","039","040","041","042","043","044",
                "004","033","034","036",  // El cliente puede ver el la gestion de Pedido y solo crear/consultar (por eso 3 opciones)
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

    public String eliminar(Facultad facultad){  // PM11074 =========
        String regAfectados="El registro no existe";
        int contador = 0;

        if (verificarIntegridad(facultad,3)) {
            contador += db.delete("facultad", "idFacultad ='" + facultad.getIdFacultad() + "'", null);
            regAfectados = "filas afectadas = " + contador;
        }
        return regAfectados;
    }

    public String actualizar(Facultad facultad){  // PM11074 =========
        if(verificarIntegridad(facultad, 3)){
            String[] id = {facultad.getIdFacultad()};
            ContentValues cv = new ContentValues();
            cv.put("idFacultad", facultad.getIdFacultad());
            cv.put("nomFacultad", facultad.getNomFacultad());
            db.update("facultad", cv, "idFacultad = ?", id);
            return "Registro Actualizado Correctamente";
        }else{
            return "Registro con carnet " + facultad.getIdFacultad() + " no existe";
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
