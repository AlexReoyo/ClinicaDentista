package model;

public class Cita {

    private int idCita;
    private int idPaciente;
    private int idDoctor;
    private String fecha;
    private String motivo;
    private String nombrePaciente;
    private String nombreDoctor;

    public Cita(int idPaciente, int idCita, int idDoctor, String fecha, String motivo, String nombrePaciente, String nombreDoctor) {
        this.idPaciente = idPaciente;
        this.idCita = idCita;
        this.idDoctor = idDoctor;
        this.fecha = fecha;
        this.motivo = motivo;
        this.nombreDoctor=nombreDoctor;
        this.nombrePaciente=nombrePaciente;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getNombreDoctor() {
        return nombreDoctor;
    }

    public void setNombreDoctor(String nombreDoctor) {
        this.nombreDoctor = nombreDoctor;
    }
}
