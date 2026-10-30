import java.util.ArrayList;

import codigos.CodigosRespuesta;

public class Sala {
    private ArrayList<Mensaje> Historial;
    private ArrayList<String> Usuarios;

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
        return Usuarios;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public CodigosRespuesta unirUsuario(String miembro) {
        if (usuarioPresente(miembro)) {
            // miembro ya está en la sala
            return CodigosRespuesta.USUARIO_PRESENTE_SALA;
        } else {
            Usuarios.add(miembro);
            return CodigosRespuesta.OK;
        }
    }

    public CodigosRespuesta expulsarUsuario(String miembro) {
        if (!usuarioPresente(miembro)) {
            // miembro aún no está en la sala
            return CodigosRespuesta.USUARIO_NO_PRESENTE_SALA;
        } else {
            Usuarios.remove(miembro);
            return CodigosRespuesta.OK;
        }
    }

    public boolean usuarioPresente(String nombre) {
        return Usuarios.contains(nombre);
    }

    public boolean validarContrasena(String contrasena) {
        return getContrasena().equals(contrasena);
    }
}
