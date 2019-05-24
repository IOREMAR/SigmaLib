package sigma.manager;




import android.content.Context;
import android.content.SharedPreferences;

import com.pagatodo.sigmamanager.Instance.ApiInstance;

import net.fullcarga.android.api.bd.sigma.manager.BdEmvSigmaManager;
import net.fullcarga.android.api.bd.sigma.manager.BdSigmaManager;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import static sigma.utils.Constantes.PREFERENCE_SETTINGS;

public final class StorageUtility {

    private static final String NOMEDIA = ".nomedia";
    public static final String SQL_CONNECTOR = "jdbc:sqlite3://";
    private   static String storagePath =  ApiInstance.getInstance().getSigmaPath();

    private StorageUtility() {
    }

    public static boolean validarArchivo(final String fileName) {
        if (fileName != null && !fileName.isEmpty()) {
            final File futureFile = new File(fileName);
            if (futureFile.exists() && futureFile.length() > 1024) {
                AppLogger.LOGGER.info(StorageUtility.class.getSimpleName(), "Validando Archivo  " + fileName + " ? " + futureFile.exists() + " contentlenght " + futureFile.length());
                return futureFile.exists();
            }
        }
        return false;
    }

    public static boolean deleteIfExistFile(final String fileName) {

        final File futureFile = new File(fileName);

        if (futureFile.isDirectory()) {

            return deleteFolder(futureFile);
        }

        return futureFile.exists() && futureFile.delete(); //NOPMD //NOSONAR no se puede usar Files.delete(path) porque no esta soportado en el nivel de api android
    }

    private static boolean deleteFolder(final File folder) {

        final File[] files = folder.listFiles();

        if (files != null) {

            for (final File file : files) {

                boolean borrado = file.isDirectory() ? deleteFolder(file) : file.delete(); //NOPMD //NOSONAR no se puede usar Files.delete(path) porque no esta soportado en el nivel de api android

                if (!borrado) {

                    return false;
                }
            }
        }

        return folder.delete();//NOSONAR no se puede usar Files.delete(path) porque no esta soportado en el nivel de api android

    }

    public static String getStoragePath() {


        //crea la carpeta .nomedia para que no aparezcan los iconos en la galeria del telefono
        final File file = new File(storagePath, NOMEDIA);
        if (!file.exists()) {
            file.mkdir();
        }
        return storagePath;
    }


    public static String  getSigmaDbPath(){
        SharedPreferences preferencesdbname = ApiInstance.getInstance().getAppcontext().getSharedPreferences(PREFERENCE_SETTINGS, Context.MODE_PRIVATE);
      return   storagePath + preferencesdbname.getString(sigma.utils.Constantes.Preferencia.DB_NAME.name(), ".db");

    }




    public static BdSigmaManager crearConexionSigmaManager() throws SQLException {
        return new BdSigmaManager("", "", SQL_CONNECTOR + getSigmaDbPath());
    }

    public static BdEmvSigmaManager crearConexionEMVSigmaManager() throws SQLException {
        return new BdEmvSigmaManager(SQL_CONNECTOR + getStoragePath() + ApiInstance.getInstance().getSigmaDBName(), "", "");
    }

    public static byte[] leerArchivo(final File file) throws IOException {
        try (final DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            final byte[] fileBytes = new byte[(int) file.length()];
            dis.readFully(fileBytes);

            return fileBytes;
        }
    }


}
