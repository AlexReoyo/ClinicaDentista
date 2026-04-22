package controller;



import com.google.gson.Gson;
import dataservice.BBDDAccess;
import model.*;

import java.sql.SQLException;
import java.util.*;


public class Controlador implements DataAccess{


    public Controlador() {

    }


    //Ahora, el controlador sólo se preocupa de ser el intermediario entre
    //la vista (servlet) y el acceso a datos, usando clases sencillas del modelo
    //Para que la vista desde el backend sea independiente de formato, usamos JSON
    @Override
    public String listAll() {
        BBDDAccess bbdd = new BBDDAccess();

        try {
            List<Cita> listaCitas = bbdd.listarTodos();

            return (new Gson()).toJson(listaCitas);

        } catch (SQLException se) {
            return "List Citas: " + se.getMessage();
        } catch (ClassNotFoundException c) {
            return "List Citas: " + c.getMessage();
        }

    }

    @Override
    public String findByCode(String code) {
        BBDDAccess bbdd = new BBDDAccess();

        try {
            List<Cita> listaCitas = bbdd.buscarPorCodigo(code);

            return (new Gson()).toJson(listaCitas);

        } catch (SQLException se) {
            return "Find Citas: " + code + " - "  + se.getMessage();
        } catch (ClassNotFoundException c) {
            return "Find Citas: " + code + " - "+ c.getMessage();
        }
    }

    @Override
    public String insert(Map<String,String> jsonCita) {
        Gson gson = new Gson();
        BBDDAccess bbdd = new BBDDAccess();

        try {
            bbdd.insertarCita(jsonCita);

        } catch (SQLException se) {
            String result =  "Insert: "  + " - " + se.getMessage();
            return result;
        } catch (ClassNotFoundException c) {
            return "Insert: "  + " - " + c.getMessage();
        }
        return "Inserción realizada OK!";
    }

    public String delete(int idCita){
        BBDDAccess bbdd = new BBDDAccess();

        try {
            bbdd.eliminarCita(idCita);
        } catch (SQLException se) {
            String result =  "Delete: "  + " - " + se.getMessage();
            return result;
        } catch (ClassNotFoundException c) {
            return "Delete: "  + " - " + c.getMessage();
        }
        return "Eliminarión realizada OK!";
    }
}