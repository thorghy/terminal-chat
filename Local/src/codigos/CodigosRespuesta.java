package codigos;

public enum CodigosRespuesta {
    OK(200, "OK"),
    SIN_SALAS(251, "No existen aún salas en este momento."),
    ERROR(400, "ERROR"),
    CONTRASEÑA_INCORRECTA(401, "Contraseña incorrecta."),
    SALA_YA_EXISTE(451, "La sala ya existe."),
    SALA_AUN_NO_EXISTE(452, "La sala aún no existe.");
    
    private final int codigo;
    private final String descripcion;

    private CodigosRespuesta(int codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static CodigosRespuesta getTituloCodigo(int codigo) {
        for (CodigosRespuesta codigoRespuesta : CodigosRespuesta.values()) {
            if (codigoRespuesta.getCodigo() == codigo) {
                return codigoRespuesta;
            }
        }
        throw new IllegalArgumentException("No hay uno código de respuesta para: " + codigo);
    }
}
