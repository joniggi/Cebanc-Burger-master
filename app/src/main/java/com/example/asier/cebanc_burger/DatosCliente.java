package com.example.asier.cebanc_burger;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class DatosCliente extends MainActivity{
    private ImageView siguiente;
    private ImageView salir;

    private EditText nombre, apellidos, direccion, telefono;
    private boolean verificar = false;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datos_cliente);

        siguiente = (ImageView) findViewById(R.id.buttonSiguiente);
        salir = (ImageView) findViewById(R.id.buttonSalir);
        nombre = (EditText) findViewById(R.id.editTextNombre);
        apellidos = (EditText) findViewById(R.id.editTextApellido);
        direccion = (EditText) findViewById(R.id.editTextDireccion);
        telefono = (EditText) findViewById(R.id.editTextTelefono);


        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificar = comprobacion();
                lanzarPedido(verificar);
            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });


    }

    public void lanzarPedido(boolean v) {
        if (v == true) {
            Intent i = new Intent(DatosCliente.this, AnadirHamburguesas.class);
            i.putExtra("nombre", nombre.getText().toString());
            i.putExtra("apellidos", apellidos.getText().toString());
            i.putExtra("direccion", direccion.getText().toString());
            i.putExtra("telefono", telefono.getText().toString());
            startActivityForResult(i, 1234);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
        }
    }

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

}
