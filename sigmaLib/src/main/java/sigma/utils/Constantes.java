package sigma.utils;

import android.os.Environment;

public final class Constantes {

    public static final String UPDATE_APK = "UPDATE_APK";
    public static final String ID_APP = "ID_APP";
    public static final int TIEMPO_ENTRE_CLICKS = 1000;
    public static final String VERSION_DATA_BASE = "1";
    public static final String SQL_CONNECTOR = "jdbc:sqlite3://";
    public static final int DECIMALES_INICIALES = 2;

    //---------------SharedPreferences-------------------------------------
    public static final String PREFERENCE_SETTINGS = "PREFERENCE_SETTINGS";

    //---------------Timers ---------------------------------------------//
    public static final int TIEMPO_TRANSICIONES = 300;
    public static final int TIEMPO_CARGA_FRAGMENTS = 600;
    public static final int TIEMPO_INACTIVIDAD_DEFAULT = 180000;

    public static final String REGISTRO_INTERNO = "REGISTRO_INTERNO";
    public static final String REGISTRO_EXTERNO = "REGISTRO_EXTERNO";
    public static final String COLOR_APP_AZUL = "AZUL";

    //----------------------Tipo Operacion--------------------------------
    public static final String VENTA = "V"; //valor de prueba
    public static final String DEVOLUCION = "D"; //valor de prueba
    public static final String CONSULTA_X = "X"; //valor de prueba
    public static final String CONSULTA_Z = "Z"; //valor de prueba
    public static final String SINCRONIZACION  = "J"; //valor de prueba
    public static final String CIERRE = "CIERRE";

    //------------------------Impresora-------------------------------------



    public static final String TICKET_SMALL = "¸";
    public static final String TICKET_NORMAL = "‗";
    public static final String TICKET_MEDIUM = "░";
    public static final String TICKET_LARGE = "▓";


    public static final int NOPAPER = 3;
    public static final int LOWBATTERY = 4;
    public static final int PRINTVERSION = 5;
    public static final int PRINTBARCODE = 6;
    public static final int PRINTQRCODE = 7;
    public static final int PRINTPAPERWALK = 8;
    public static final int PRINTFORMATTEXT = 9;
    public static final int PRINTTEXT = 17;
    public static final int CANCELPROMPT = 10;
    public static final int PRINTERR = 11;
    public static final int OVERHEAT = 12;
    public static final int PRINTPICTURE = 14;
    public static final int NOBLACKBLOCK = 15;
    public static final int PRINTPICTURECONTENT = 16;

    public static final boolean LAUNCHER = true;

    //------------------------Update apk-----------------------
    public static final String PATH_APK = Environment.getExternalStorageDirectory() + "/Download/";
    public static final String NOMBRE_APK = "apk_update.apk";
    public static final String ACTION_UPDATE_END = "com.pagatodoholdings.posandroid.UPDATE_END";

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
