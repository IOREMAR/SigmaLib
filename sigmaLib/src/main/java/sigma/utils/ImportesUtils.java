package sigma.utils;



import java.math.BigDecimal;
import java.math.RoundingMode;

import static sigma.manager.AppLogger.LOGGER;

public final class ImportesUtils {

    private static final String TAG = ImportesUtils.class.getSimpleName();
    private final BigDecimal importeIngresado;
    private final BigDecimal importeMin;
    private final BigDecimal importeMax;
    private final BigDecimal importeIncremento;

    public ImportesUtils(final BigDecimal importeIngresado, final BigDecimal importeMin, final BigDecimal importeMax, final BigDecimal importeIncremento) {
        this.importeIngresado = importeIngresado;
        this.importeMin = importeMin;
        this.importeMax = importeMax;
        this.importeIncremento = importeIncremento;
    }

    public boolean isMayor() {
        return importeIngresado.compareTo(importeMax) > 0;
    }

    public boolean isMenor() {
        return importeIngresado.compareTo(importeMin) < 0;
    }

    public boolean isMultiplo() {
        boolean isMultiplo = true;
        try {
            final Double resultado = importeIngresado.subtract(importeMin).divide(importeIncremento, 2, RoundingMode.HALF_UP).doubleValue();
            if (resultado - Math.round(resultado) != 0) {
                isMultiplo = false;
            }
        } catch (NumberFormatException ex) {
            LOGGER.throwing(TAG, 1, ex, ex.getMessage());
            isMultiplo = false;
        }
        return isMultiplo;
    }
}
