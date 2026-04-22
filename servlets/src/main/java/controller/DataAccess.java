package controller;


import java.util.Map;

public interface DataAccess {

    public String listAll();
    public String findByCode(String code);
    public String insert(Map <String,String> jsonCita);

}
