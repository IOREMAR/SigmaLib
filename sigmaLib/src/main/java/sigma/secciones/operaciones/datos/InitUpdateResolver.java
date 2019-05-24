package sigma.secciones.operaciones.datos;

import com.pagatodo.sigmamanager.Instance.ApiInstance;

import net.fullcarga.android.api.ApiFullcargaAndroid;
import net.fullcarga.android.api.data.respuesta.RespuestaUpdate;
import net.fullcarga.android.api.exc.RespuestaException;
import net.fullcarga.android.api.listener.DescargaListener;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import sigma.manager.AppLogger;
import sigma.utils.UnzipUtility;

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


    public static RespuestaUpdate Resolver (final DatosOperacion datosOperacion){

        try {
            operacion = datosOperacion;
            fileOutputStream = new FileOutputStream(ApiInstance.getInstance().getSigmaPath() + ApiInstance.getInstance().getSigmaDBName() + ".zip");
            final RespuestaUpdate resolver = getRespuestaUpdate(datosOperacion);

            if ( resolver != null && resolver.isCorrecta() ) {

                try {
                    UnzipUtility.unzipFile(ApiInstance.getInstance().getSigmaPath() + ApiInstance.getInstance().getSigmaDBName() + ".zip", ApiInstance.getInstance().getSigmaPath());
                    return resolver;
                } catch ( IOException | RuntimeException exc ) {
                    AppLogger.LOGGER.throwing(TAG, 1, exc, "Error al Descomprimir");
                    return null;
                }

            } else {
                return null;
            }

        }catch (Throwable exe ){
            AppLogger.LOGGER.throwing(TAG,1,exe,exe.getCause().toString());
            return null;
        }


    }

    private static RespuestaUpdate getRespuestaUpdate(DatosOperacion datosOperacion) {
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
            return null;
        }
    }



}
