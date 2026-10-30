import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import codigos.CodigosRespuesta;

public class Servidor {
    ArrayList<Sala> salasActivas = new ArrayList<>();

    public void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(6767)) {
            while (true) {
                new HiloSocket(serverSocket.accept(), this).start();;
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
            if (validarContrasenaSala(sala, contrasena)) {
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
                if (sala.getNombre().equals(nombre)) return true;
            }
        }
        return false;
    }

    private Sala findSala(String nombre) {
        for (Sala sala : salasActivas) {
            if (sala.getNombre().equals(nombre)) return sala;
        }
        return null;
    }
    private boolean validarContrasenaSala(Sala sala, String contrasena) {
        return sala.getContrasena().equals(contrasena);
    }

    public CodigosRespuesta unirMiembro(Sala sala, String miembro) {
        if (!salasActivas.contains(sala)) {
            return findSala(miembro).unirMiembro(miembro);
        } else {
            return CodigosRespuesta.SALA_NO_EXISTE;
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
