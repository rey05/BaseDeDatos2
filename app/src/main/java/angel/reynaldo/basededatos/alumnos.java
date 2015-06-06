package angel.reynaldo.basededatos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class alumnos extends ActionBarActivity {
    //Se declaran los atributos que se van a utilizar
    EditText nocontrol, nombre, apellidos, email, carrera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumnos);

        //Enlazamos la parte grafica con el codigo
        nocontrol=(EditText) findViewById(R.id.nocontrol);
        nombre = (EditText) findViewById(R.id.nombre);
        apellidos= (EditText) findViewById(R.id.apellidos);
        email = (EditText) findViewById(R.id.email);
        carrera = (EditText) findViewById(R.id.carrera);
    }

    //Metodo que nos permite registrar o dar de alta a los alumnos en la base de datos
    public void alta(View v){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "alumnos", null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String Nocontrol= nocontrol.getText().toString();
        String Nombre = nombre.getText().toString();
        String Apellidos = apellidos.getText().toString();
        String Email = email.getText().toString();
        String Carrera = carrera.getText().toString();



        Cursor fila = bd.rawQuery("select nombre, apellidos, email, carrera  from alumnos where nocontrol=" + Nocontrol, null);
        if (fila.getCount()>=1){

            Toast.makeText(this, "No se puede agregar, el Numero de control ya existe", Toast.LENGTH_SHORT).show();

        }else {
            ContentValues registro = new ContentValues();


            registro.put("nocontrol", Nocontrol);
            registro.put("nombre", Nombre);
            registro.put("apellidos", Apellidos);
            registro.put("email", Email)    ;
            registro.put("carrera", Carrera);


            bd.insert("alumnos", null, registro);
            bd.close();

            nocontrol.setText("");
            nombre.setText("");
            apellidos.setText("");
            email.setText("");
            carrera.setText("");


            Toast.makeText(this, "Se agrego un nuevo alumno", Toast.LENGTH_SHORT).show();
        }
    }
    //Metodo que nos permite consultar los alumnos en la base de datos
    public void consulta (View v) {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "alumnos", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String Nocontrol = nocontrol.getText().toString();

    //se hace una consulta para ver el regstro mediante el numero de control
        Cursor fila = bd.rawQuery("select nombre, apellidos, email, carrera from alumnos where nocontrol=" + Nocontrol, null);
        if (fila.moveToFirst()){

            nombre.setText(fila.getString(0));
            apellidos.setText(fila.getString(1));
            email.setText(fila.getString(2));
            carrera.setText(fila.getString(3));

        }else {
            Toast.makeText(this, "No existe el registro", Toast.LENGTH_SHORT).show();
        }bd.close();
    }


    //Metodo que nos permite elinar a los alumnos en la base de datos
    public void baja (View v) {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "alumnos", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String Nocontrol = nocontrol.getText().toString();
        int cant = bd.delete("alumnos", "nocontrol=" + Nocontrol, null);
        bd.close();

        nocontrol.setText("");
        nombre.setText("");
        apellidos.setText("");
        email.setText("");
        carrera.setText("");


        if (cant==1) {
            Toast.makeText(this, "Se elimino el registro", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "No existe el registro", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo que nos permite modificar y actualizar a los alumnos en la base de datos
    public void modificacion (View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "alumnos", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String Nocontrol = nocontrol.getText().toString();
        String Nombre = nombre.getText().toString();
        String Apellidos = apellidos.getText().toString();
        String Email = email.getText().toString();
        String Carrera = carrera.getText().toString();

        ContentValues registro= new ContentValues();

        registro.put("nocontrol", Nocontrol);
        registro.put("nombre", Nombre);
        registro.put("apellidos", Apellidos);
        registro.put("email", Email);
        registro.put("carrera", Carrera);



        int cant = bd.update("alumnos", registro, "nocontrol=" +  Nocontrol, null);
        bd.close();

        if (cant==1){
            Toast.makeText(this, "Se modifico el registro de la persona", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No existe el registro de la persona", Toast.LENGTH_SHORT).show();
        }
    }


    //Metodod que nos permite limpiar los EditText
    public void limpia (View v) {

        nocontrol.setText("");
        nombre.setText("");
        apellidos.setText("");
        email.setText("");
        carrera.setText("");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alumnos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
