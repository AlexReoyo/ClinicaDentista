package view;

import com.google.gson.Gson;
import controller.Controlador;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/insertar")
public class InsertarCitaServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idPaciente = request.getParameter("idPaciente");
        String idDoctor = request.getParameter("idDoctor");
        String fecha = request.getParameter("fecha");
        String motivo = request.getParameter("motivo");
        //Sacar la info de los parámetros
        //Los metes en un mapa

        Map<String,String> miJsonMap = new HashMap<>();

        miJsonMap.put("idPaciente",idPaciente);
        miJsonMap.put("idDoctor",idDoctor);
        miJsonMap.put("fecha",fecha);
        miJsonMap.put("motivo",motivo);

        Gson gson = new Gson();

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();


        out.println((new Controlador()).insert(miJsonMap));
    }
}