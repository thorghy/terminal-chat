import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import codigos.CodigosRespuesta;
import codigos.CodigosSolicitud;

public class Servidor {
    ArrayList<Sala> salasActivas = new ArrayList<>();
    ArrayList<Usuario> miembros = new ArrayList<>();

    public void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(6767)) {
            while (true) {
                new HiloSocket(serverSocket.accept(), this).start();
                ;
                System.out.println("Un nuevo usuario se ha unido");
            }

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public ArrayList<String> getSalas() {
        ArrayList<String> salas = new ArrayList<>();

        for (Sala sala : salasActivas) {
            salas.add(sala.getNombre());
        }
        return salas;
    }

    public CodigosRespuesta crearSala(String nombre, String contrasena) {
        if (salaExiste(nombre)) {
            // la sala ya existe
            return CodigosRespuesta.SALA_YA_EXISTE;
        } else {
            salasActivas.add(new Sala(nombre, contrasena));
            return CodigosRespuesta.OK;
        }
    }

    public CodigosRespuesta borrarSala(String nombre, String contrasena) {
        Sala sala;
        if (salaExiste(nombre)) {
            sala = findSala(nombre);
            if (sala.validarContrasena(contrasena)) {
                salasActivas.remove(sala);
                return CodigosRespuesta.OK;
            } else {
                return CodigosRespuesta.CONTRASEÑA_INCORRECTA;
            }
        }
        return CodigosRespuesta.SALA_NO_EXISTE;
    }

    private boolean salaExiste(String nombre) {
        if (!salasActivas.isEmpty()) {
            for (Sala sala : salasActivas) {
                if (sala.getNombre().equals(nombre))
                    return true;
            }
        }
        return false;
    }

    private Sala findSala(String nombre) {
        for (Sala sala : salasActivas) {
            if (sala.getNombre().equals(nombre))
                return sala;
        }
        return null;
    }

    private Usuario findUsuario(String nombre) {
        for (Usuario miembro : miembros) {
            if (miembro.getNombre().equals(nombre))
                return miembro;
        }
        return null;
    }

    public CodigosRespuesta unirUsuarioSala(Sala sala, String miembro) {
        if (!salasActivas.contains(sala)) {
            return findSala(miembro).unirUsuario(miembro);
        } else {
            return CodigosRespuesta.SALA_NO_EXISTE;
        }
    }

    public CodigosRespuesta solicitarUnirUsuarioSala(String nombreSala, String contrasena, String miembro) {
        Sala sala = findSala(nombreSala);

        if (sala.validarContrasena(contrasena)) {
            return unirUsuarioSala(sala, miembro);
        } else {
            return CodigosRespuesta.CONTRASEÑA_INCORRECTA;
        }
    }

    public CodigosRespuesta expulsarUsuarioSala(Sala sala, String miembro) {
        if (salasActivas.contains(sala)) {
            return findSala(miembro).expulsarUsuario(miembro);
        } else {
            return CodigosRespuesta.SALA_NO_EXISTE;
        }
    }

    public CodigosRespuesta crearUsuario(String nombre, String contrasena) {
        if (findUsuario(nombre) == null) {
            miembros.add(new Usuario(nombre, contrasena));
            return CodigosRespuesta.OK;
        } else {
            return CodigosRespuesta.USUARIO_YA_EXISTE;
        }
    }

    public CodigosRespuesta eliminarUsuario(Usuario usuario) {
        miembros.remove(usuario);
        return CodigosRespuesta.OK;
    }

    public CodigosRespuesta solicitarEliminarUsuario(String nombre, String contrasena) {
        Usuario usuario = findUsuario(nombre);

        if (usuario != null) {
            if (usuario.validarContrasena(contrasena)) {
                return eliminarUsuario(usuario);
            } else {
                return CodigosRespuesta.CONTRASEÑA_INCORRECTA;
            }
        } else {
            return CodigosRespuesta.USUARIO_YA_EXISTE;
        }
    }
}
