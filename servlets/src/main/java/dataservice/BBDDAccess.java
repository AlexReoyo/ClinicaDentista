package dataservice;



import model.Cita;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BBDDAccess {

    //método para obtener los productos. Se conecta y ejecuta el select
    //No sabe se "vive" en una aplicación Java tradicional, en un ExecuteService de Android,
    //en un servlet... Simplemente "pide" a la BBDD la ejecución de SQL y obtiene los datos
    //para convertirlo en objetos del modelo.
    public List<Cita> listarTodos() throws SQLException,ClassNotFoundException {

        Connection conn = null;
        List<Cita> listaResultado = new ArrayList<>();

        conn = ConexionBD.getConnection();

        String sql = "SELECT c.idPaciente idPaciente, c.idCita idCita," +
                " c.idDoctor idDoctor, c.fecha fecha, c.motivo motivo, " +
                "d.nombre nombreDoctor, concat_ws(' ',p.nombre,p.apellido) nombrePaciente " +
                "from citas c join pacientes p on c.idPaciente=p.idPaciente join" +
                " doctores d on d.idDoctor=c.idDoctor";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            listaResultado.add(new Cita(
                    rs.getInt("idPaciente"),
                    rs.getInt("idCita"),
                    rs.getInt("idDoctor"),
                    rs.getString("fecha"),
                    rs.getString("motivo"),
                    rs.getString("nombrePaciente"),
                    rs.getString("nombreDoctor")
            ));
        }


        if (rs!=null) rs.close();
        if (stmt!=null) stmt.close();
        if (conn != null) conn.close();

        return listaResultado;
    }


    //Esta función es la que implementa realmente el acceso y el insert
    //Le pasa lo mismo que al método previo, donde se lleve, funcionará

    public void insertarCita(Map<String,String> cita) throws SQLException,ClassNotFoundException {

        PreparedStatement pstmt = null;
        Connection conn = ConexionBD.getConnection();

        String tabla = "citas";
        String values = " (idPaciente, idDoctor, fecha, motivo) VALUES (?, ?, ?, ?)";
        String sql = "INSERT INTO " + tabla + values;


        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, Integer.parseInt(cita.get("idPaciente")));
        pstmt.setInt(2, Integer.parseInt(cita.get("idDoctor")));
        pstmt.setString(3, cita.get("fecha"));
        pstmt.setString(4, cita.get("motivo"));

        pstmt.executeUpdate();
    }

    public void eliminarCita(int idCita) throws SQLException,ClassNotFoundException {

        PreparedStatement pstmt = null;
        Connection conn = ConexionBD.getConnection();

        String tabla = "citas";
        String sql = "delete from " + tabla + " where idCita="+idCita;


        pstmt = conn.prepareStatement(sql);

        pstmt.executeUpdate();
    }

    //Igual que listarTodos, pero filtrando por código...

    public List<Cita> buscarPorCodigo(String idCita) throws SQLException,ClassNotFoundException {

        Connection conn = null;
        List<Cita> listaResultado = new ArrayList<>();

        conn = ConexionBD.getConnection();
        // Productos normales
        String sql = "SELECT c.idPaciente idPaciente, c.idCita idCita," +
                " c.idDoctor idDoctor, c.fecha fecha, c.motivo motivo, " +
                "d.nombre nombreDoctor, concat_ws(' ',p.nombre,p.apellido) nombrePaciente " +
                "from citas c join pacientes p on c.idPaciente=p.idPaciente join" +
                " doctores d on d.idDoctor=c.idDoctor WHERE idCita LIKE ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1,"%"+idCita+"%");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            listaResultado.add(new Cita(
                    rs.getInt("idPaciente"),
                    rs.getInt("idCita"),
                    rs.getInt("idDoctor"),
                    rs.getString("fecha"),
                    rs.getString("motivo"),
                    rs.getString("nombrePaciente"),
                    rs.getString("nombreDoctor")
            ));
        }
        if (rs!=null) rs.close();
        if (stmt!=null) stmt.close();
        if (conn != null) conn.close();

        return listaResultado;
    }
}