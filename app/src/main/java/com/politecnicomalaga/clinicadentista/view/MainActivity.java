package com.politecnicomalaga.clinicadentista.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.politecnicomalaga.clinicadentista.R;
import com.politecnicomalaga.clinicadentista.controller.Controlador;
import com.politecnicomalaga.clinicadentista.dataservice.BBDDAccess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void crearCita(View v){
        String idPaciente = ((EditText)findViewById(R.id.etIdPaciente)).getText().toString();
        String idDoctor = ((EditText)findViewById(R.id.etIdDoctor)).getText().toString();
        String fecha = ((EditText)findViewById(R.id.etFechaCita)).getText().toString();
        String motivo = ((EditText)findViewById(R.id.etMotivo)).getText().toString();

        boolean res= true;

        Map<String,String> cita = new HashMap<>();
        cita.put("idPaciente",idPaciente);
        cita.put("idDoctor",idDoctor);
        cita.put("fecha",fecha);
        cita.put("motivo",motivo);


        Controlador.getSingleton(this).addCita(cita);

    }

    public void buscarCita(View v){
        String idCita = ((EditText)findViewById(R.id.etIdCita)).getText().toString();
        Controlador.getSingleton(this).searchCita(Integer.parseInt(idCita));
    }

    public void listarTodas(View v){
        Controlador.getSingleton(this).listarTodas();
    }

    public void eliminarCita(View v) {
        String idCita = ((EditText)findViewById(R.id.etIdCita)).getText().toString();
        Controlador.getSingleton(this).deleteCita(Integer.parseInt(idCita));
    }

    public void reaccionar(String error) {

        if (error.isEmpty()) { //Está vacío /no hay
            List<Map<String, String>> datos = Controlador.getSingleton(this).getData();

            //Mostrarlos
            ListView miListaEnPantalla = findViewById(R.id.lvCitas);

            ArrayAdapter<String> miAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
            for (Map<String, String> unaCita : datos) {
                //Formato de la lista
                String resultado="Id Cita: "+unaCita.get("idCita")+"\n";
                resultado+="Nombre Paciente: "+unaCita.get("nombrePaciente")+"\n";
                resultado+="Nombre Doctor: "+unaCita.get("nombreDoctor")+"\n";
                resultado+="Fecha: "+unaCita.get("fecha")+"\n";
                resultado+="Motivo: "+unaCita.get("motivo")+"\n";
                miAdapter.add(resultado);
            }

            miListaEnPantalla.setAdapter(miAdapter);
        } else {
            ListView miListaEnPantalla = findViewById(R.id.lvCitas);

            ArrayAdapter<String> miAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
            miAdapter.add(error);
            miListaEnPantalla.setAdapter(miAdapter);
        }
    }
}