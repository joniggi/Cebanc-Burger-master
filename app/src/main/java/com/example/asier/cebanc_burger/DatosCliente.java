package com.example.asier.cebanc_burger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class DatosCliente extends MainActivity{

    //definimos las varible que vamos a utilizar
    private ImageView siguiente;
    private ImageView salir;
    private Button comprobar;
    private EditText nombre, apellidos, direccion, telefono;
    private boolean verificar = false;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datos_cliente);

       //identificamos los objetos del xml
        siguiente = (ImageView) findViewById(R.id.buttonSiguiente);
        salir = (ImageView) findViewById(R.id.buttonSalir);
        apellidos = (EditText) findViewById(R.id.editTextApellido);
        comprobar =(Button)findViewById(R.id.comprobar);

        nombre = (EditText) findViewById(R.id.editTextNombre);
        direccion = (EditText) findViewById(R.id.editTextDireccion);
        telefono = (EditText) findViewById(R.id.editTextTelefono);


        /*direccion.setEnabled(false);
        telefono.setEnabled(false);*/


        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = nombre.getText().toString();
                String a = apellidos.getText().toString();
                String d = direccion.getText().toString();
                String t = telefono.getText().toString();


                if(nombreExiste(n)){
                    Toast toastBienvenido = Toast.makeText(getApplicationContext(),
                            "Hola, " + n + " bienvenido", Toast.LENGTH_SHORT);
                    toastBienvenido.show();
                    lanzarActividad();

                }else{
                    Boolean nombreCorrecto = validarNombre(n);
                    Boolean apellidoCorrecto = validarApellidos(a);
                    Boolean direccionCorrecto = validarDireccion(d);
                    Boolean telefonoCorrecto = validarTelefono(t);

                    if(!nombreCorrecto){
                        nombre.setError("Debes introducir el nombre");
                    }
                    if(!apellidoCorrecto){
                        apellidos.setError("Debes introducir el apellido");
                    }
                    if(!direccionCorrecto){
                        direccion.setError("Debes introducir el direccion");
                    }
                    if(!telefonoCorrecto){
                        telefono.setError("Debes introducir el telefono");
                    }

                    if(!nombreCorrecto || !direccionCorrecto || !telefonoCorrecto){
                        Toast toastError = Toast.makeText(getApplicationContext(),
                                "Debes introducir los datos", Toast.LENGTH_SHORT);
                        toastError.show();
                    }else{
                        insertarNombre(n, a, d, t);
                        Toast toastBienvenido = Toast.makeText(getApplicationContext(),
                                "Hola, " + n + " bienvenido", Toast.LENGTH_SHORT);
                        toastBienvenido.show();
                        lanzarActividad();

                    }
                }
            }
        });
        comprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             comprobar2();
            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });

//aqui queriamos hacer un metodo pero sino no funcionaba
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs4);
        tabs.addTab(tabs.newTab().setText("DATOS").setIcon(R.drawable.logod));


    }



    public void insertarNombre(String nombre, String apellidos, String direccion, String telefono){
        HamburSQLiteHelper burguer_dbh =
                new HamburSQLiteHelper(this, "DBBurguer", null, 2);
        SQLiteDatabase db = burguer_dbh.getWritableDatabase();

        db.execSQL("INSERT INTO USUARIOS (USUARIO, NOMBRE_USU, APELLIDOS, DIRECCION, TELF) VALUES ( "
                + null + ", '"+ nombre +"', '"+ apellidos +"',  '" + direccion + "', '" + telefono + "' )");

        Toast toastCarrito = Toast.makeText(getApplicationContext(),
                "¡El usuario se ha registrado en la base de datos!", Toast.LENGTH_SHORT);
        toastCarrito.show();

        db.close();
    }

    public boolean nombreExiste(String nombre){
        HamburSQLiteHelper burguer_dbh =
                new HamburSQLiteHelper(this, "DBBurguer", null, 2);
        SQLiteDatabase db = burguer_dbh.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT NOMBRE_USU FROM USUARIOS WHERE NOMBRE_USU = '" + nombre + "'", null);

        if(c.getCount() > 0){
            c.close();
            db.close();
            return true;
        }
        c.close();
        db.close();
        return false;

    }

    public boolean validarNombre(String n) {
        String nombre = n;

        if(nombre.length()!=0){
            return true;
        }
        return false;

    }
    public boolean validarApellidos(String a) {
        String apellidos = a;

        if(apellidos.length()!=0){
            return true;
        }
        return false;

    }

    public boolean validarDireccion(String d) {
        String direccion = d;

        if(direccion.length()!=0){
            return true;
        }
        return false;

    }

    public boolean validarTelefono(String t) {
        String telefono = t;

        if(telefono.length()==9 && telefono.matches("\\d*")){
            return true;
        }
        return false;

    }




public void comprobar2(){
    String n = nombre.getText().toString();

    if(nombreExiste(n)){
        apellidos.setEnabled(false);
        direccion.setEnabled(false);
        telefono.setEnabled(false);

        HamburSQLiteHelper burguer_dbh =
                new HamburSQLiteHelper(this, "DBBurguer", null, 2);
        SQLiteDatabase db = burguer_dbh.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT APELLIDOS, DIRECCION, TELF FROM USUARIOS WHERE NOMBRE_USU = '" + n + "'", null);

        String apellidosDB = "";
        String direccionDB = "";
        String telefonoDB = "";
        try{
            if(c.moveToFirst()){
                do{
                    apellidosDB = c.getString(0);
                    direccionDB = c.getString(1);
                    telefonoDB = c.getString(2);
                }while(c.moveToNext());
            }
        }catch(Exception ex){

        }
        c.close();
        db.close();


        Toast toastUsuarioExiste = Toast.makeText(getApplicationContext(),
                "¡El usuario introducido ya existe!", Toast.LENGTH_SHORT);
        toastUsuarioExiste.show();

        apellidos.setText(apellidosDB);
        direccion.setText(direccionDB);
        telefono.setText(telefonoDB);



    }else{
        apellidos.setEnabled(true);
        direccion.setEnabled(true);
        telefono.setEnabled(true);

        Toast toastUsuarioNoExiste = Toast.makeText(getApplicationContext(),
                "¡El usuario introducido no existe, introduce tus datos!", Toast.LENGTH_SHORT);
        toastUsuarioNoExiste.show();


    }
}


    //este metodo lanza una ventana emergente que termina la actividad
    public void salir() {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Salir");
        dialogo1.setMessage("¿Estas seguro de que quieres salir?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                finish();
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });
        dialogo1.show();

    }

















    //este metodo recibe un booleano y en caso de que sea true lanza la actividad
    public void lanzarPedido(boolean v) {
        String n=nombre.getText().toString();
        String d=direccion.getText().toString();
        String t=telefono.getText().toString();
        if (v == true && usuarioExiste(n) ) {
            Toast toastBienvenido = Toast.makeText(getApplicationContext(),
                    "¡Bienvenido, " + n + " !", Toast.LENGTH_SHORT);
            toastBienvenido.show();
            lanzarActividad();
        }else if(v == true && usuarioExiste(n)==false){
            insertarUsuario(n,d,t);
            Toast toastInsertado = Toast.makeText(getApplicationContext(),
                    "¡Usuario, " + n + " insertado", Toast.LENGTH_SHORT);
            toastInsertado.show();
            lanzarActividad();
        }
    }

    //este metodo verifica que esten los datos introducidos y en dicho caso devuelve un booleano = true
    public boolean comprobacion() {
        boolean verificar = false;
        if (nombre.getText().toString().equals("") && apellidos.getText().toString().equals("") && direccion.getText().toString().equals("") && telefono.getText().toString().equals("")) {
            Toast.makeText(this, "¡ Deberías introducir los datos para continuar !", Toast.LENGTH_SHORT).show();
            verificar = false;
        } else if (nombre.getText().toString().equals("") || apellidos.getText().toString().equals("") || direccion.getText().toString().equals("")|| telefono.getText().toString().equals("") || telefono.getText().toString().length()<9) {
            if (nombre.getText().toString().equals("")) {
                nombre.setError("Introduce el nombre");
                verificar = false;
            }
            if (apellidos.getText().toString().equals("")) {
                apellidos.setError("Introduce el apellido ");
                verificar = false;
            }
            if (direccion.getText().toString().equals("")) {
                direccion.setError("Introduce la direccion");
                verificar = false;
            }
            if (telefono.getText().toString().equals("")) {
                telefono.setError("Introduce el telefono");
                verificar = false;
            }else if(telefono.getText().toString().length()<9){
                telefono.setError("El teléfono no es válido");
            }
        } else {
            verificar = true;
        }
        return verificar;
    }



    public void lanzarActividad(){
        Intent i = new Intent(DatosCliente.this, AnadirHamburguesas.class);
        i.putExtra("nombre", nombre.getText().toString());
        i.putExtra("apellidos", apellidos.getText().toString());
        i.putExtra("direccion", direccion.getText().toString());
        i.putExtra("telefono", telefono.getText().toString());
        startActivityForResult(i, 1234);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    public boolean usuarioExiste(String nombre){
        HamburSQLiteHelper burguer_dbh =
                new HamburSQLiteHelper(this, "DBBurguer", null, 2);
        SQLiteDatabase db = burguer_dbh.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT NOMBRE_USU, DIRECCION, TELF FROM USUARIOS WHERE NOMBRE_USU = '" + nombre + "'", null);

        if(c.getString(0).length() > 0 && c.getString(1).length() > 0 && c.getString(2).length() > 0 ){
            c.close();
            db.close();
            return true;
        }
        c.close();
        db.close();
        return false;

    }

    public void insertarUsuario(String nombre, String direccion, String telefono){
        HamburSQLiteHelper burguer_dbh =
                new HamburSQLiteHelper(this, "DBBurguer", null, 2);
        SQLiteDatabase db = burguer_dbh.getWritableDatabase();

        db.execSQL("INSERT INTO USUARIOS (USUARIO, NOMBRE_USU, DIRECCION, TELF) VALUES ( "
                + null + ", '"+ nombre +"', '" + direccion + "', '" + telefono + "' )");

        /*Toast toastCarrito = Toast.makeText(getApplicationContext(),
                "¡El usuario se ha registrado en la base de datos!", Toast.LENGTH_SHORT);
        toastCarrito.show();*/

        db.close();
    }

    public void comprobar(){
        String n = nombre.getText().toString();

        if(usuarioExiste(n)){
            direccion.setEnabled(false);
            telefono.setEnabled(false);

            HamburSQLiteHelper burguer_dbh =
                    new HamburSQLiteHelper(this, "DBBurguer", null, 2);
            SQLiteDatabase db = burguer_dbh.getWritableDatabase();

            Cursor c = db.rawQuery("SELECT DIRECCION, TELF FROM USUARIOS WHERE NOMBRE_USU = '" + n + "'", null);

            String direccionDB = "";
            String telefonoDB = "";
            try{
                if(c.moveToFirst()){
                    do{
                        direccionDB = c.getString(0);
                        telefonoDB = c.getString(1);
                    }while(c.moveToNext());
                }
            }catch(Exception ex){

            }
            c.close();
            db.close();


            Toast toastUsuarioExiste = Toast.makeText(getApplicationContext(),
                    "¡El usuario introducido ya existe!", Toast.LENGTH_SHORT);
            toastUsuarioExiste.show();

            direccion.setText(direccionDB);
            telefono.setText(telefonoDB);



        }else{
            direccion.setEnabled(true);
            telefono.setEnabled(true);

            Toast toastUsuarioNoExiste = Toast.makeText(getApplicationContext(),
                    "¡El usuario introducido no existe, introduce tus datos!", Toast.LENGTH_SHORT);
            toastUsuarioNoExiste.show();


        }
    }



}
