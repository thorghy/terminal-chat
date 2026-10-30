import java.util.ArrayList;

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

    public boolean unirMiembro(String miembro) {
        if (Miembros.indexOf(miembro) != -1) {
            // miembro ya está en la Sala
            return false;
        } else {
            Miembros.add(miembro);
            return true;
        }
    }

    public boolean expulsarMiembro(String miembro) {
        int encontrado;
        if ((encontrado = Miembros.indexOf(miembro)) == -1) {
            // miembro aún no está en la Sala
            return false;
        } else {
            Miembros.remove(encontrado);
            return true;
        }
    }
}
