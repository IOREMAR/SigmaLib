package sigma.secciones.operaciones.datos;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.pagatodo.sigmamanager.Instance.ApiInstance;


import net.fullcarga.android.api.ApiFullcargaAndroid;
import net.fullcarga.android.api.bd.sigma.manager.BdSigmaManager;
import net.fullcarga.android.api.data.respuesta.RespuestaListArchivos;
import net.fullcarga.android.api.exc.RespuestaException;
import net.fullcarga.android.api.util.HashUtil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import sigma.manager.AppLogger;
import sigma.manager.StorageUtility;
import sigma.utils.Constantes;

import static sigma.utils.Constantes.PREFERENCE_SETTINGS;

public class IconResolver  {
    private static final String TAG = IconResolver.class.getSimpleName();
    private static DatosOperacion operacion;
    private static  Map<String,Long> iconosCRC = Collections.EMPTY_MAP;


    public static RespuestaListArchivos Resolver (final DatosOperacion datosOperacion ){
    operacion = datosOperacion;

        try (final BdSigmaManager bdSigmaManager = StorageUtility.crearConexionSigmaManager()) {
            iconosCRC = getCrcFromIconList();
            for ( final String file : bdSigmaManager.getListIconos() ) {
                if ( !iconosCRC.containsKey(file) ) {
                    iconosCRC.put(file, 0L);
                }
            }
        }catch ( SQLException | IOException exe) {
            AppLogger.LOGGER.throwing(TAG,1,exe,exe.getCause().toString());
        }

        final RespuestaListArchivos resolver = iconList();

        if(resolver != null && resolver.isCorrecta()){
            SharedPreferences preferencesdbname = ApiInstance.getInstance().getAppcontext().getSharedPreferences(PREFERENCE_SETTINGS, Context.MODE_PRIVATE);

            final String oldDb = preferencesdbname.getString(Constantes.Preferencia.ICONZIP_NAME.name(),".zip");
            StorageUtility.deleteIfExistFile(ApiInstance.getInstance().getSigmaPath() + oldDb);

            preferencesdbname.edit().putString(Constantes.Preferencia.ICONZIP_NAME.name(), resolver.getFileName() ).apply();
            ApiInstance.getInstance().setIconosName(resolver.getFileName());
            ApiInstance.getInstance().setFileSize(resolver.getFileSize());
            return resolver;
        }else {
            return null;
        }

    }



    private static RespuestaListArchivos iconList (){
        try {


        return ApiFullcargaAndroid.iconList(
                AppLogger.LOGGER,
                operacion.getIdPeticion(),
                ApiInstance.getInstance().getDatosSesion(),
                new Date(),
                iconosCRC
        );

        }
        catch ( RespuestaException  | NullPointerException exe ){
            AppLogger.LOGGER.throwing(TAG,1,exe,exe.getCause().toString());
            operacion.getListener().onResponse(exe);
            return null;
        }
    }




    private static   Map<String, Long> getCrcFromIconList() throws IOException {
        final String directory;

        directory = StorageUtility.getStoragePath();


        final Map<String, Long> mapFiles = new HashMap<>();
        final File[] listAllFiles = new File(directory).listFiles();

        if (listAllFiles != null && listAllFiles.length > 0) {

            for (final File currentFile : listAllFiles) {

                if (currentFile.getName().endsWith("BMP") || currentFile.getName().endsWith("PNG")) {
                    mapFiles.put(currentFile.getName(), HashUtil.calcularSHAFile(currentFile));
                }
            }
        }
        return mapFiles;
    }





}
