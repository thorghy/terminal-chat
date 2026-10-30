import java.util.ArrayList;

import codigos.CodigosRespuesta;

public class Sala {
    private ArrayList<Mensaje> Historial;
    private ArrayList<String> Miembros;

    private String Nombre;
    private String Contrasena;

    public Sala(String nombre, String contrasena) {
        this.Nombre = nombre;
        this.Contrasena = contrasena;
    }

    public ArrayList<Mensaje> getHistorial() {
        return Historial;
    }

    public ArrayList<String> getMiembros() {
        return Miembros;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public CodigosRespuesta unirMiembro(String miembro) {
        if (miembroPresente(miembro)) {
            // miembro ya está en la sala
            return CodigosRespuesta.MIEMBRO_PRESENTE_SALA;
        } else {
            Miembros.add(miembro);
            return CodigosRespuesta.OK;
        }
    }

    public CodigosRespuesta expulsarMiembro(String miembro) {
        if (!miembroPresente(miembro)) {
            // miembro aún no está en la sala
            return CodigosRespuesta.MIEMBRO_NO_PRESENTE_SALA;
        } else {
            Miembros.remove(miembro);
            return CodigosRespuesta.OK;
        }
    }

    public boolean miembroPresente(String nombre) {
        return Miembros.contains(nombre);
    }
}
