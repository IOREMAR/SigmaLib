package sigma.manager;




import net.fullcarga.android.api.bd.sigma.generated.tables.pojos.EmvRangoBinCuotas;
import net.fullcarga.android.api.bd.sigma.generated.tables.pojos.PerfilesEmv;
import net.fullcarga.android.api.bd.sigma.manager.BdEmvSigmaManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sigma.general.interfaces.OnFailureListener;


public class EmvManager {

    private static volatile EmvManager instance;

    private static final String TAG = EmvManager.class.getSimpleName();

    public EmvManager() {
        //nothing
    }


    public static  EmvManager getInstance() {
        synchronized (EmvManager.class) {
            if (instance == null) {
                instance = new EmvManager();
            }
        }
        return instance;
    }

    public PerfilEmvApp getFullPerfil(final int perfil, final OnFailureListener sigmaBdInteractorExceptionListener ) {

        try (  final BdEmvSigmaManager emvSigmaManager = StorageUtility.crearConexionEMVSigmaManager()){

                final PerfilesEmv perfilesEmv = emvSigmaManager.getPerfilEmv(perfil);
                 return new PerfilEmvApp(
                         emvSigmaManager.getEmvAids(perfil),
                         null,
                         perfilesEmv,
                         emvSigmaManager.getEmvMoneda(perfilesEmv != null ? perfilesEmv.getIdMoneda() : 0),
                         emvSigmaManager.getEmvMoneda(perfilesEmv != null ? perfilesEmv.getIdMonedaCashback() : -1));
        }
        catch ( SQLException |  RuntimeException exe ){
                AppLogger.LOGGER.throwing(TAG,1,exe,exe.getMessage());
                sigmaBdInteractorExceptionListener.onFailure(new Throwable("Erro Al consultar el perfil"));
            return null;
        }


    }

    public List<EmvRangoBinCuotas> getRangoCuotas(final int idCuota)  {
        try (final BdEmvSigmaManager emvSigmaManager = StorageUtility.crearConexionEMVSigmaManager()){
            return emvSigmaManager.getRangoCuotas(idCuota);
        }
        catch (SQLException exe ){
            AppLogger.LOGGER.throwing(TAG,1, new Throwable(exe) ,exe.getMessage());
            return Collections.emptyList();
        }
    }

    public List<String> getTagsPerfil(final int perfil) {
        try ( final BdEmvSigmaManager emvSigmaManager = StorageUtility.crearConexionEMVSigmaManager()){
            return toStringTags(emvSigmaManager.getPerfilEmv(perfil).getTagsEmv());
        }
        catch (SQLException exe ){
            AppLogger.LOGGER.throwing(TAG,1,exe ,exe.getMessage());
            return Collections.emptyList();
        }
    }

    private static byte[] hexStringToByteArray(final String str) {
        final int len = str.length();
        final byte[] data = new byte[len / 2];
        for (int cont = 0; cont < len; cont += 2) {
            data[cont / 2] = (byte) ((Character.digit(str.charAt(cont), 16) << 4)
                    + Character.digit(str.charAt(cont + 1), 16));
        }
        return data;
    }

    private static String toHexadecimal(final byte[] digest) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (final byte aux : digest) {
            final int val = aux & 0xff;
            if (Integer.toHexString(val).length() == 1) {
                final String value = "0";
                stringBuilder.append(value);
            }
            stringBuilder.append(Integer.toHexString(val));

        }
        return stringBuilder.toString();
    }

    private static List<String> toStringTags(final String strTag) {
        final byte[] tags = hexStringToByteArray(strTag);
        int idx = 0;
        final byte[] nombreByte = new byte[2];
        final String[] tagsSigma = new String[tags.length * 2];
        for (idx = 0; idx < tags.length; idx++) {
            nombreByte[0] = tags[idx];
            nombreByte[1] = 0x00;
            if ((tags[idx++] & 0x1F) == 0x1F) {
                nombreByte[1] = tags[idx++];
            }
            final String mTag = toHexadecimal(nombreByte);
            final String[] parts = mTag.split("00");
            final String part1 = parts[0];
            tagsSigma[idx] = part1;
            idx -= 1;
        }
        final List<String> list = new ArrayList<>();
        for (final String str : tagsSigma) {
            if (str != null && str.length() > 0) {
                list.add(str);
            }
        }
        return list;
    }
}
