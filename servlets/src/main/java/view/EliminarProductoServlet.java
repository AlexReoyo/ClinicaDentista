package view;



import controller.Controlador;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/eliminar")
public class EliminarProductoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idCita = request.getParameter("idCita");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        out.println((new Controlador()).delete(Integer.parseInt(idCita)));
    }
}