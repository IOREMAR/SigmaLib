package sigma.secciones.operaciones.datos;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.pagatodo.sigmamanager.Instance.ApiInstance;

import net.fullcarga.android.api.ApiFullcargaAndroid;
import net.fullcarga.android.api.data.respuesta.RespuestaListArchivos;
import net.fullcarga.android.api.exc.RespuestaException;

import java.util.Date;

import sigma.manager.AppLogger;
import sigma.manager.StorageUtility;
import sigma.utils.Constantes.Preferencia;

import static sigma.utils.Constantes.PREFERENCE_SETTINGS;

public class InitResolver {

    public static RespuestaListArchivos Resolver(final DatosOperacion datosOperacion) {

      final RespuestaListArchivos  respuestaArchivos = getRespuestaArchivos(datosOperacion);

      if(respuestaArchivos !=  null) {

          SharedPreferences preferencesdbname = ApiInstance.getInstance().getAppcontext().getSharedPreferences(PREFERENCE_SETTINGS, Context.MODE_PRIVATE);

          final String oldDb = preferencesdbname.getString(Preferencia.DB_NAME.name(), ".db");
          StorageUtility.deleteIfExistFile(ApiInstance.getInstance().getSigmaPath() + oldDb);

          preferencesdbname.edit().putString(Preferencia.DB_NAME.name(), respuestaArchivos.getFileName() + ".db").apply();
          ApiInstance.getInstance().setSigmaDBName(respuestaArchivos.getFileName());
          return respuestaArchivos;

      }else {
          return null;
      }

    }


    private static RespuestaListArchivos getRespuestaArchivos(final DatosOperacion  datosOperacion){
        try {
            return ApiFullcargaAndroid.initList(
                    AppLogger.LOGGER,
                    datosOperacion.getIdPeticion(),
                    ApiInstance.getInstance().getDatosSesion(),
                    new Date()
            );
        }catch (RespuestaException exe){
            AppLogger.LOGGER.throwing(InitResolver.class.getSimpleName(),1,exe,exe.getCause().toString());
            datosOperacion.getListener().onResponse(exe);
            return  null;
        }

    }

 }
