import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import codigos.CodigosRespuesta;
import codigos.CodigosSolicitud;

public class Servidor {
    ArrayList<Sala> salasActivas = new ArrayList<>();
    ArrayList<Miembro> miembros = new ArrayList<>();

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
            if (sala.validarContrasenaSala(contrasena)) {
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

    private Miembro findMiembro(String nombre) {
        for (Miembro miembro : miembros) {
            if (miembro.getNombre().equals(nombre)) 
                return miembro;
        }
        return null;
    }

    public CodigosRespuesta unirMiembro(Sala sala, String miembro) {
        if (!salasActivas.contains(sala)) {
            return findSala(miembro).unirMiembro(miembro);
        } else {
            return CodigosRespuesta.SALA_NO_EXISTE;
        }
    }

    public CodigosRespuesta solicitarUnirMiembro(String nombreSala, String contrasena, String miembro) {
        Sala sala = findSala(nombreSala);

        if (sala.validarContrasenaSala(contrasena)) {
            return unirMiembro(sala, miembro);
        } else {
            return CodigosRespuesta.CONTRASEÑA_INCORRECTA;
        }
    }

    public CodigosRespuesta expulsarMiembro(Sala sala, String miembro) {
        if (salasActivas.contains(sala)) {
            return findSala(miembro).expulsarMiembro(miembro);
        } else {
            return CodigosRespuesta.SALA_NO_EXISTE;
        }
    }

    
}
