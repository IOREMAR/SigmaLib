package sigma.secciones.operaciones.datos;

import android.content.Context;
import android.content.SharedPreferences;

import com.pagatodo.sigmamanager.Instance.ApiInstance;

import net.fullcarga.android.api.ApiFullcargaAndroid;
import net.fullcarga.android.api.data.respuesta.RespuestaUpdate;
import net.fullcarga.android.api.exc.RespuestaException;
import net.fullcarga.android.api.listener.DescargaListener;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import sigma.manager.AppLogger;
import sigma.utils.Constantes;
import sigma.utils.UnzipUtility;

import static sigma.utils.Constantes.PREFERENCE_SETTINGS;

public class InitUpdateResolver  {
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
            descargaSize += bytes.length;
//            final int ratio = (int) (descargaSize * 100 / fileSize);
//        publishProgress(MposApplication.getInstance().getResources().getString(R.string.Descargando_DB) + " " + ratio + "%");
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


    public static RespuestaUpdate Resolver (final DatosOperacion datosOperacion)throws RespuestaException {

        try {
            operacion = datosOperacion;
            fileOutputStream = new FileOutputStream(ApiInstance.getInstance().getSigmaPath() + ApiInstance.getInstance().getSigmaDBName() + ".zip");
            final RespuestaUpdate resolver = getRespuestaUpdate(datosOperacion);

            if ( resolver != null && resolver.isCorrecta() ) {

                try {

                    UnzipUtility.unzipFile(ApiInstance.getInstance().getSigmaPath() + ApiInstance.getInstance().getSigmaDBName() + ".zip", ApiInstance.getInstance().getSigmaPath());
                    SharedPreferences preferencesdbname = ApiInstance.getInstance().getAppcontext().getSharedPreferences(PREFERENCE_SETTINGS, Context.MODE_PRIVATE);
                    preferencesdbname.edit().putString(Constantes.Preferencia.DB_NAME.name(), ApiInstance.getInstance().getSigmaDBName() + ".db").apply();
                    return resolver;
                } catch (  RuntimeException exc ) {
                    AppLogger.LOGGER.throwing(TAG, 1, exc, "Error al Descomprimir");
                    throw  exc;
                }

            } else {
                throw new RespuestaException("Respuesta Incorrecta");
            }

        } catch ( IOException ioexe ){
            AppLogger.LOGGER.throwing(TAG,1,ioexe,ioexe.getCause().toString());
        }

        catch (RespuestaException  exe ){
            AppLogger.LOGGER.throwing(TAG,1,exe,exe.getCause().toString());
            throw  exe;
        }

        throw  new RespuestaException("Error en la Operación");

    }

    private static RespuestaUpdate getRespuestaUpdate(DatosOperacion datosOperacion) throws RespuestaException {
        try {
            return ApiFullcargaAndroid.initUpdate(
                    AppLogger.LOGGER,
                    datosOperacion.getIdPeticion(),
                    ApiInstance.getInstance().getDatosSesion(),
                    new Date(),
                    ApiInstance.getInstance().getSigmaDBName(),
                    descargaListener
            );
        }
        catch ( RespuestaException  exe ){
            AppLogger.LOGGER.throwing(TAG,1,exe,exe.getCause().toString());
            throw  exe;
        }
    }



}
