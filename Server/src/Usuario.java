import java.util.ArrayList;

public class Usuario {
    private String nombre;
    private String contrasena;
    private ArrayList<String> salas;

    public Usuario(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;

        salas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public boolean validarContrasena(String contrasena) {
        return this.contrasena.equals(contrasena);
    }
}
