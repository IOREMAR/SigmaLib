package sigma.Ticket;


import com.pagatodo.sigmamanager.Instance.ApiInstance;

import org.apache.commons.codec.binary.Hex;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import sigma.manager.DecodeData;

import static sigma.manager.AppLogger.LOGGER;

@SuppressWarnings("PMD.GodClass")
public final class CamposEMVData {


    private static final String TAG = CamposEMVData.class.getSimpleName();
    private static CamposEMVData instance;
    private DecodeData decodeTarjeta;
    private static final String[] TAGSEMV = {"4F", "5F20", "9F12", "5A", "9F27", "9F26", "95", "9B", "5F28", "9F07"};
    private  int maxCuota;

    private static Map<String, String> mapTags = new HashMap<>();

    public int getMaxCuota() {
        return maxCuota;
    }

    public void setMaxCuota(final int maxCuota) {
        this.maxCuota = maxCuota;
    }



    /**
     * Datos de la tarjeta cuando se hace una operacion.
     */
    public void setDataTarjeta(final DecodeData dataOpTarjeta) {
        decodeTarjeta = dataOpTarjeta;
    }


    public String getMaskPan ( ){
        return decodeTarjeta.getMaskedPAN();
    }

    /**
     * Regresa el Nombre del Titular de la tarjeta
     *
     * @return
     */
    private String getCardHolderName() {

        try {
            if (!mapTags.isEmpty()) {
                return getTAGICCResponce("5F20");
            } else {
                return "";
            }
        } catch (RuntimeException exe) {
           LOGGER.throwing(TAG, 1, exe, "Error al recibir el nombre de la tarjeta");
        }

        if (decodeTarjeta != null) {
            final String cardholderName = decodeTarjeta.getCardholderName();

            return cardholderName != null ? cardholderName : "";
        } else {
            return "";
        }
    }

    /**
     * Regresa el Masked PAN de la tarjeta.
     *
     * @return
     */
    private String getMaskedPan() {

        try {
            if (!mapTags.isEmpty()) {
                return "****" + getTAGICCResponce("5A").substring(getTAGICCResponce("5A").length() - 4);
            } else {
                return "";
            }

        } catch (RuntimeException exe) {
            LOGGER.throwing(TAG, 1, exe, exe.getCause().toString());
            if (decodeTarjeta != null) {
                final String pan = decodeTarjeta.getMaskedPAN();
                return pan != null ? "****" + pan.substring(pan.length() - 4) : "";
            } else {
                return "";
            }
        }
    }

    /**
     * Regresa el nombre de la Aplicacion de la operacion
     *
     * @return
     */
    private String getAID() {

        if (!mapTags.isEmpty()) {
            return getTAGICCResponce("4F");
        } else {
            return "";
        }
    }


    /**
     * Nos regresa el ARQC de la TRX donde usarlo.
     *
     * @return
     */
    private String getARQC() {

        if (!mapTags.isEmpty()) {
            return getTAGICCResponce("9F26");
        } else {
            return "";
        }
    }

    /**
     * Nos regresa el nombre de la aplicacion usada en al TRX
     */
    private String getNombreAplicacion() {

        if (!mapTags.isEmpty()) {
            return getTAGICCResponce("9F12");
        } else {
            return "";
        }
    }

    /**
     * Resultados de verificación del terminal TVR
     *
     * @return
     */
    private String getTerminalVerification() {

        if (!mapTags.isEmpty()) {

            return getTAGICCResponce("95");
        } else {
            return "";
        }
    }

    /**
     * Versión del EmvModule
     *
     * @return
     */
    private String getVersionEMV() {

        return ApiInstance.getInstance().getVersionName();
    }

    /**
     * Imprime el tipo de criptograma yAxis las acciones del terminal
     *
     * @return
     */
    private String getCRYInfo() {
        if (!mapTags.isEmpty()) {
            return getTAGICCResponce("9F27");
        } else {
            return "";
        }
    }


    private String getTransactionStatus() {

        if (!mapTags.isEmpty()) {
            return getTAGICCResponce("9B");
        } else {
            return "";
        }

    }

    /**
     * Recupera los datos de la tarjeta por el digito en el formato del ticket
     *
     * @param valor
     * @return
     */
    public String getCamposbydigit(final int valor) {
        final CamposEMV[] emvvals = CamposEMV.values();
        try {
            switch (emvvals[valor]) {

                case APP_IDENTIFIER:
                    return getAID();


                case APP_PREFERRED_NAME:
                    return getNombreAplicacion();


                case APP_NAME:
                    return getNombreAplicacion();


                case CARD_HOLDER_NAME:
                    return getCardHolderName();


                case PAN:
                    return getMaskedPan();


                case CRYPTO_INFO_DATA:
                    return getCRYInfo();


                case APP_CRYPTOGRAM:
                    return getARQC();


                case TERMINAL_VERIFICATION_RESULTS:
                    return getTerminalVerification();


                case TRANSACCION_STATUS_INFORMATION:
                    return getTransactionStatus();


                case EMV_MODULE_VERSION:
                    return getVersionEMV();


                default:
                    return "";

            }

        } catch (Exception exe) {

          LOGGER.throwing(TAG, 1, exe, "Error al Recibir los datos TLV");
            return "";
        }
    }

    public enum CamposEMV {

        APP_IDENTIFIER(0),
        APP_PREFERRED_NAME(1),
        APP_NAME(2),
        CARD_HOLDER_NAME(3),
        PAN(4),
        CRYPTO_INFO_DATA(5),
        APP_CRYPTOGRAM(6),
        TERMINAL_VERIFICATION_RESULTS(7),
        TRANSACCION_STATUS_INFORMATION(8),
        EMV_MODULE_VERSION(9);

        private final int valornumer;
        private static Map<Integer, CamposEMV> map = new HashMap<>();

        CamposEMV(final int number) {
            valornumer = number;
        }

        static {

            for (final CamposEMV pageType : EnumSet.allOf(CamposEMV.class)) {

                map.put(pageType.getNumber(), pageType);
            }
        }

        public static CamposEMV valuesOf(final int pageType) {
            return map.get(pageType);
        }

        public int getNumber() {
            return valornumer;
        }
    }

    /**
     * Eliminar datos de session
     */
    public void kill() {

        if (instance != null) {
            mapTags.clear();
        }
    }

    public static String getTAGICCResponce(final String TAGEMV) {
        try {
            final String TagValue = mapTags.get(TAGEMV);

            final byte[] bytes;
            if ("5F20".equals(TAGEMV) || "9F12".equals(TAGEMV)) {
                bytes = Hex.decodeHex(TagValue.toCharArray());//NOSONAR
            } else {
                bytes = TagValue.getBytes();
            }

            return new String(bytes, "UTF-8");
        } catch (Exception exe) {//NOSONAR
          LOGGER.throwing(TAG, 1, exe, exe.getMessage());
            return "";
        }
    }


    public void getEMVTags( Map<String, String>  mapTags ) {

        final StringBuilder sBuilder = new StringBuilder();
        StringBuilder tlvresponcebuilder;

        for (final String tag : TAGSEMV) {
            sBuilder.append(tag);
        }

        final Integer emvLength = Integer.valueOf(TAGSEMV.length);
        //TODO Usar Libreria De Qpos

        final Map<String, String> decodeData = mapTags;

        String tlvresponce;

        if (decodeData.get("tlv") != null && !decodeData.get("tlv").equals("")) {
            tlvresponce = decodeData.get("tlv");
            tlvresponcebuilder = new StringBuilder(tlvresponce);
        } else {
            return;
        }

        for (final String tag : TAGSEMV) {

            tlvresponcebuilder = splitTAG(tlvresponcebuilder, tag);
        }
    }

    /**
     * Metodo para filtar los tags,tomando el TLV
     */
    private StringBuilder splitTAG(final StringBuilder tlvstringresponce, final String tag) {
        final StringBuilder tlvmaterial = new StringBuilder(tlvstringresponce);

        int hexalength = 0;
        if (tlvstringresponce.toString().startsWith(tag)) {
            hexalength = (int) Long.parseLong(tlvmaterial.substring(tag.length(), tag.length() + 2), 16);
        }

        mapTags.put(tag, tlvmaterial.substring(tag.length() + 2, tag.length() + 2 + hexalength * 2));

        return new StringBuilder(tlvmaterial.substring(tag.length() + 2 + hexalength * 2));
    }


    public String getTLVBYTAG(final String tag) {

        if (mapTags.isEmpty()) {
            return "";
        } else {
            try {
                return getTAGICCResponce(tag);
            } catch (Exception exe) {
                LOGGER.throwing(tag, 1, exe, "Erro al generar el TLV");

                return "";
            }
        }
    }
}
