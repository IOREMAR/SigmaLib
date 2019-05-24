package sigma.utils;

import android.annotation.SuppressLint;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static sigma.manager.AppLogger.LOGGER;

/**
 * Created by Daniel Ruiz on 03/10/2017.
 */

public final class DateUtils {

    private DateUtils() {
    }

    public static String getDateNow() {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", getLocale(7));
        return sdf.format(new Date(System.currentTimeMillis()));

    }

    public static String getNowHour() {
        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", getLocale(7));
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    public static DateFormatSymbols cambioFormatoMeses() {
        final DateFormatSymbols fechasActual = new DateFormatSymbols(getLocale(7));
        final String[] anteriorMeses = fechasActual.getShortMonths();
        final String[] actualMeses = new String[anteriorMeses.length];
        for (int i = 0; i < 12; i++) {
            if (anteriorMeses.length > 0) {
                actualMeses[i] = Character.toUpperCase(anteriorMeses[i].charAt(0)) + anteriorMeses[i].substring(1).replace(".", "");
            } else {
                actualMeses[i] = "";
            }
        }
        fechasActual.setShortMonths(actualMeses);
        return fechasActual;
    }

    @SuppressLint("SimpleDateFormat")
    public static Date cambiarFormatoFechaDate(final String fecha) {
        final DateFormatSymbols mesesFormato = cambioFormatoMeses();
        final String input = "yyyy/MM/dd kk:mm:ss";
        final String output = "dd/MMM/yy kk:mm";
        final SimpleDateFormat formatoEntrada = new SimpleDateFormat(input, getLocale(7));
        final SimpleDateFormat formatoSalida = new SimpleDateFormat(output, mesesFormato);

        Date date = null;
        String fechaFormateada = null;

        try {
            date = formatoEntrada.parse(fecha);
            fechaFormateada = formatoSalida.format(date);
            return formatoSalida.parse(fechaFormateada);
        } catch (ParseException e) {
            LOGGER.throwing("DateUtil", 1, e, e.getMessage());
        }
        return null;
    }

    private static Locale getLocale(final int idPais) {
        final Locale locale;
        if (idPais == 7) {   // Peru
            locale = new Locale("es", "PE");
        } else if (idPais == 6) { // EU
            locale = new Locale("es", "US");
        } else if (idPais == 5) { // Ecuador
            locale = new Locale("es", "EC");
        } else if (idPais == 4) { // Chile
            locale = new Locale("es", "CL");
        } else if (idPais == 3) { // Colombia
            locale = new Locale("es", "CO");
        } else if (idPais == 2) { // España
            locale = new Locale("es", "ES");
        } else if (idPais == 1) { // Argentina
            locale = new Locale("es", "AR");
        } else {
            locale = new Locale("es", "MX");
        }
        return locale;
    }
}
