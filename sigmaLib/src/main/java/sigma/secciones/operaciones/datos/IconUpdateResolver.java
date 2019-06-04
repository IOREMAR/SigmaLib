package sigma.secciones.operaciones.datos;

import android.content.Context;
import android.content.SharedPreferences;

import com.pagatodo.sigmamanager.Instance.ApiInstance;

import net.fullcarga.android.api.ApiFullcargaAndroid;
import net.fullcarga.android.api.data.respuesta.RespuestaListArchivos;
import net.fullcarga.android.api.data.respuesta.RespuestaUpdate;
import net.fullcarga.android.api.exc.RespuestaException;
import net.fullcarga.android.api.listener.DescargaListener;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import sigma.manager.AppLogger;
import sigma.utils.Constantes;
import sigma.utils.UnzipUtility;

import static sigma.utils.Constantes.PREFERENCE_SETTINGS;

public class IconUpdateResolver {


    private static final String TAG = InitUpdateResolver.class.getSimpleName();
    private String directory;
    private static FileOutputStream fileOutputStream;
    private String zipName;
    private static long descargaSize;
    private static long fileSize;
    private  static DatosOperacion operacion;

    private static DescargaListener descargaListener =  new DescargaListener() {
        @Override
        public void recibir(byte[] bytes) {
//            descargaSize += bytes.length;
//            final int ratio = (int) (descargaSize * 100 / fileSize);
//            String msg = "Descargando Base de Datos " + ratio + "%";
//            operacion.getListener().onResponse(msg);
            try {
                fileOutputStream.write(bytes);
                fileOutputStream.flush();
            } catch ( IOException exc) {
                AppLogger.LOGGER.throwing(TAG,1,exc,exc.getCause().toString());
                operacion.getListener().onResponse(new Throwable("Error en al Descarga"));
            }
        }

        @Override
        public void fin() {
            try {
                fileOutputStream.close();
                AppLogger.LOGGER.info(TAG, "Cerrando archivo");
            } catch (IOException exc) {
                AppLogger.LOGGER.throwing(TAG, 1, exc, "Error en  fin () ");
                operacion.getListener().onResponse(new Throwable("Error en al Descarga"));
            }
        }
    };


    public static RespuestaUpdate Resolver (final DatosOperacion datosOperacion) throws Exception{
             operacion = datosOperacion;
             fileSize = ApiInstance.getInstance().getFileSize();
             fileOutputStream = new FileOutputStream(ApiInstance.getInstance().getSigmaPath() + ApiInstance.getInstance().getIconosName() + ".zip");

             final RespuestaUpdate resolver = getRespuestaUpdate(datosOperacion);
             UnzipUtility.unzipFile(ApiInstance.getInstance().getSigmaPath() + ApiInstance.getInstance().getIconosName() + ".zip", ApiInstance.getInstance().getSigmaPath());
        SharedPreferences preferencesdbname = ApiInstance.getInstance().getAppcontext().getSharedPreferences(PREFERENCE_SETTINGS, Context.MODE_PRIVATE);
        preferencesdbname.edit().putString(Constantes.Preferencia.ICONZIP_NAME.name(), ApiInstance.getInstance().getIconosName() ).apply();
             return resolver;
    }

    private static RespuestaUpdate getRespuestaUpdate(DatosOperacion datosOperacion) throws RespuestaException {
        return ApiFullcargaAndroid.iconUpdate(
                AppLogger.LOGGER,
                datosOperacion.getIdPeticion(),
                ApiInstance.getInstance().getDatosSesion(),
                new Date(),
                ApiInstance.getInstance().getIconosName(),
                descargaListener
        );
    }







}
