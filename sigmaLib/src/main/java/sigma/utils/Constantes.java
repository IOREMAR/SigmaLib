package sigma.utils;

public final class Constantes {

    //---------------SharedPreferences-------------------------------------
    public static final String PREFERENCE_SETTINGS = "PREFERENCE_SETTINGS";

    public static final String BCODE_FIELD = "C<";

    public enum Preferencia {
        ESTADO_REGISTRO,
        NUMERO_SERIE,
        CLAVE_TPV,
        DATOS_SESION,
        DATOS_SESION_PCI,
        DB_NAME,
        VERSION_DB_SIGMA,
        DB_NAME_SCANNER,
        ICONZIP_NAME,
        LOGOZIP_NAME,
        STAN,
        TURNO,
        FECHA_ULTIMO_CIERRE_TURNO,
        TIEMPO_INACTIVIDAD,
        TIEMPO_NOTIFICACION,
        PREFERENCE_POS_KEY,
        PREFERENCE_USER_KEY,
        PREFERENCE_INSTALLATION_KEY,
        PREFERENCE_SERIAL_NUMBER,
        ISUPDATING,
        PRINTER_SELECTED,
        PRINTER_AVAIBLE,
        CAPK_UPDATED,
        NOTIFICACIONES_ARRAY,
        NOTIFICACIONES_LEIDAS,
        LISTA_NOTIFICACIONE_LEIDAS,
        COLOR_TEMA,
        NOMBRE,
        EMAIL,
        GENERO,
        TELEFONO,
        DONGLE_VINCULADO,
        VISTA_MENU_PRODUCTOS,
        DONGLE_MAC_ADDRESS,
        AUTH_FINGERPRINT,
        PAIS
    }

    public enum EstadosRegistro {
        PENDIENTE, // Este estado se usa para devolver un estado por defecto cuando no se ha guardado aun un estado en preferencias
        ENVIADO,
        REGISTRADO
    }

    private Constantes() {
    }
}
