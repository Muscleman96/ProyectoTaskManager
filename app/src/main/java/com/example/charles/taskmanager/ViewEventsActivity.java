package com.example.charles.taskmanager;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ViewEventsActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {


    private SQLiteDatabase bd;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);

        listView = findViewById(R.id.ltvListaEventos);
        listView.setOnItemLongClickListener(this);

        //Mostramos dia seleccionado en el generador de eventos

        Bundle bundle = getIntent().getExtras();
        int dia,mes,anio;
        dia=0;
        mes=0;
        anio=0;

        dia=bundle.getInt("dia");
        mes=bundle.getInt("mes");
        anio=bundle.getInt("anio");

        String cadena= dia + "-" + mes + "-" + anio;

        //Fin obtencion fecha

        BDSQLite bd = new BDSQLite(getApplicationContext(), "Agenda", null,1);
        SQLiteDatabase db = bd.getReadableDatabase();

        String sql = "select * from eventos where fechadesde ='"+cadena+"'";
        Cursor c; // Almacenar registros que nos devuelva la consulta

        String nombre, fechadesde, fechahasta, descripcion, ubicacion , horadesde, horahasta;

        try{
            c=db.rawQuery(sql,null);
            arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1); //Creamos lista
            if (c.moveToFirst()){
                do {
                    nombre=c.getString(1);
                    ubicacion=c.getString(2);
                    fechadesde=c.getString(3);
                    horadesde=c.getString(4);
                    fechahasta=c.getString(5);
                    horahasta=c.getString(6);
                    descripcion=c.getString(7);
                    arrayAdapter.add(nombre+", "+ubicacion+", "+fechadesde+", "+horadesde+", "+fechahasta+","+horahasta+", "+descripcion);
                }while(c.moveToNext());
                // Agregamos array adapter al list view
                listView.setAdapter(arrayAdapter);
                }else{
                this.finish();
            }
        }catch (Exception ex){
            Toast.makeText(getApplication(),"Error: "+ex.getMessage(), Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }

    private void eliminar(String dato){

        String[]datos=dato.split(", ");

        String sql="delete from eventos where nombreEvento = " + "'" + datos[0] + "'";



        try{
            arrayAdapter.remove(dato);
            listView.setAdapter(arrayAdapter);
            //ejecutar instruccion

            BDSQLite bd = new BDSQLite(getApplicationContext(), "Agenda", null,1);
            SQLiteDatabase db = bd.getWritableDatabase();

          db.execSQL(sql);
            Toast.makeText(getApplication(),"Evento eliminado", Toast.LENGTH_SHORT).show();
        }catch(Exception ex){
            Toast.makeText(getApplication(),"Error" + ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence []items = new CharSequence[2];
        items[0]="Eliminar evento";
        items[1]="Cancelar";
        builder.setTitle("Eliminar evento")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) { //eliminar evento
                            eliminar(adapterView.getItemAtPosition(i).toString());

                        }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
        return false;
    }
}
