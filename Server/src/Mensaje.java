public class Mensaje {
    private String Texto;
    private String Emisor;

    public Mensaje(String texto, String emisor) {
        this.Texto = texto;
        this.Emisor = emisor;
    }

    public String getEmisor() {
        return Emisor;
    }

    public String getTexto() {
        return Texto;
    }
}
