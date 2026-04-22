package com.politecnicomalaga.clinicadentista.controller;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.politecnicomalaga.clinicadentista.dataservice.BBDDAccess;
import com.politecnicomalaga.clinicadentista.model.Cita;
import com.politecnicomalaga.clinicadentista.view.MainActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controlador {
    private MainActivity miPantalla;
    private static Controlador singleton;
    private List<Cita> citas;

    private Controlador(MainActivity miPantalla) throws Exception
    {
        this.miPantalla=miPantalla;
        citas = new ArrayList<>();
    }

    public static Controlador getSingleton(MainActivity miPantalla)
    {
        try{
            if (singleton == null) singleton = new Controlador(miPantalla);

            return singleton;
        } catch (Exception e ){
            throw new RuntimeException("Error de inicialización del controlador");
        }

    }

    public void addCita(Map<String,String> cita){
        BBDDAccess miBBDD = new BBDDAccess(this);
        miBBDD.insertarCita(Integer.parseInt(cita.get("idPaciente")),Integer.parseInt(cita.get("idDoctor")),cita.get("fecha"),cita.get("motivo"));

        listarTodas();
    }

    public void deleteCita(int idCita){
        BBDDAccess miBBDD = new BBDDAccess(this);
        miBBDD.deleteCita(idCita);
    }

    public void listarTodas(){
        BBDDAccess miBBDD = new BBDDAccess(this);
        miBBDD.listarTodos();

    }

    public void searchCita(int idCita){
        BBDDAccess miBBDD= new BBDDAccess(this);
        miBBDD.searchCita(idCita);
    }


    //Método de okHTTP
    public void setData(String jsonData, boolean error) {

        try {
            JsonParser.parseString(jsonData);
            //si estamos aquí, es un json
        } catch (JsonSyntaxException e) {
            error = true; //se que no tengo un json
        }

        if (!error) {
            citas.clear();
            Type tipoListaCitas = new TypeToken<List<Cita>>() {
            }.getType();
            citas = (new Gson().fromJson(jsonData, tipoListaCitas));
            this.miPantalla.reaccionar("");
        } else {
            if (!jsonData.isEmpty()){
                this.miPantalla.reaccionar(""+jsonData);
            } else {
                this.miPantalla.reaccionar("Error de acceso a Backend " + jsonData);
            }
        }

    }
    public List<Map<String,String>> getData() {

        List<Map<String,String>> resultado = new ArrayList<>();

        //Cambiar del List<Producto> y List<ProductoPerecedero> a
        //List de maps

        for(Cita c: citas) {
            Map<String,String> citaMapeada = new HashMap<>();
            citaMapeada.put("idPaciente",""+c.getIdPaciente());
            citaMapeada.put("idDoctor",""+c.getIdDoctor());
            citaMapeada.put("fecha",c.getFecha());
            citaMapeada.put("motivo",c.getMotivo());
            citaMapeada.put("idCita",""+c.getIdCita());
            citaMapeada.put("nombrePaciente",c.getNombrePaciente());
            citaMapeada.put("nombreDoctor",c.getNombreDoctor());
            resultado.add(citaMapeada);
        }

        return resultado;
    }



}
