package com.example.charles.taskmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.preference.DialogPreference;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

public class TaskManager extends AppCompatActivity implements CalendarView.OnDateChangeListener{

    private CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(this);

    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence []items = new CharSequence[3];
        items[0]="Agregar Eventos";
        items[1]="Ver Eventos";
        items[2]="Cancelar";

        final int dia,mes,anio;
        dia=i;
        mes=i1+1;
        anio=i2;

        builder.setTitle("Selecciona una tarea")
                .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i==0) {
                                    //Actividad Agregar Eventos
                                    Intent intent =new Intent(getApplication(), AddActivity.class);

                                    Bundle bundle = new Bundle();
                                    bundle.putInt("dia",dia);
                                    bundle.putInt("mes",mes);
                                    bundle.putInt("anio",anio);
                                    intent.putExtras(bundle);
                                    startActivity(intent);

                            } else if(i==1){
                                    //Ver Eventos
                                    Intent intent =new Intent(getApplication(), ViewEventsActivity.class);

                                    Bundle bundle = new Bundle();
                                    bundle.putInt("dia",dia);
                                    bundle.putInt("mes",mes);
                                    bundle.putInt("anio",anio);
                                    intent.putExtras(bundle);
                                    startActivity(intent);

                            } else {
                                return;
                            }
                        }
    });
        AlertDialog dialog = builder.create();
        dialog.show();

}}
