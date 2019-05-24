package sigma.manager;



import net.fullcarga.android.api.log.FullLogger;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.logging.Level;

import static sigma.manager.AppLogger.Loggers.LOGGER_PRODUCCION;
import static sigma.manager.AppLogger.Loggers.LOGGER_QA;
import static net.fullcarga.android.api.util.StringUtil.parseParams;

public class AppLogger {

    private static final String TAG = AppLogger.class.getSimpleName();

    public static final FullLogger LOGGER = getInstancia();

    private static LoggerApi loggerApi;

    public static FullLogger getInstancia() {

        if ("QA".equals("QA")) {
            return LOGGER_QA;
        } else {
            return LOGGER_PRODUCCION;
        }
    }

    public static void setlogger(final LoggerApi logger ){
        loggerApi = logger;
    }





    enum Loggers implements FullLogger {
        LOGGER_QA() {
            @Override
            public void fine(final String TAG, final String mensaje) {
                loggerApi.fine(TAG, mensaje);
            }

            @Override
            public void fine(final String TAG, final String mensaje, final Object... params) {
                loggerApi.fine(TAG, parseParams(mensaje, params));
            }

            @Override
            public void fine(final String TAG, final int error, final String mensaje) {
                loggerApi.fine(TAG, mensaje + "{" + error + "}");
            }

            @Override
            public void fine(final String TAG, final int error, final String mensaje, final Object... params) {
                loggerApi.fine(TAG, parseParams(mensaje, params) + "{" + error + "}");
            }

            @Override
            public void finest(final String TAG, final String mensaje) {
                loggerApi.fine(TAG, mensaje);
            }

            @Override
            public void finest(final String TAG, final String mensaje, final Object... params) {
                loggerApi.fine(TAG, parseParams(mensaje, params));
            }

            @Override
            public void finest(final String TAG, final int error, final String mensaje) {
                loggerApi.fine(TAG, mensaje + "{" + error + "}");
            }

            @Override
            public void finest(final String TAG, final int error, final String mensaje, final Object... params) {
                loggerApi.fine(TAG, parseParams(mensaje, params) + "{" + error + "}");
            }

            @Override
            public void info(final String TAG, final String mensaje) {
                loggerApi.info(TAG, mensaje);

            }

            @Override
            public void info(final String TAG, final String mensaje, final Object... params) {
                loggerApi.fine(TAG, parseParams(mensaje, params));

            }

            @Override
            public void info(final String TAG, final int error, final String mensaje) {
                loggerApi.info(TAG, mensaje + "{" + error + "}");

            }

            @Override
            public void info(final String TAG, final int error, final String mensaje, final Object... params) {
                loggerApi.info(TAG, parseParams(mensaje, params) + "{" + error + "}");

            }

            @Override
            public void setLevel(final Level level) {
                loggerApi.info(TAG, "setLevel ->" + level);
            }

            @Override
            public void setLimite(final long limit) {
                loggerApi.info(TAG, "setLimite ->" + limit);
            }

            @Override
            public void sql(final String TAG, final String mensaje, final Object... params) {
                loggerApi.fine(TAG, parseParams(mensaje, params));

            }

            @Override
            public void throwing(final String TAG, final int error, final Throwable thrwbl, final String mensaje) {

                loggerApi.throwing(TAG, mensaje, thrwbl);
            }

            @Override
            public void throwing(final String TAG, final int error, final Throwable thrwbl, final String mensaje, final Object... params) {

                loggerApi.throwing(TAG, parseParams(mensaje, params) + "{" + error + "}", thrwbl);
            }

            @Override
            public void throwing(final String TAG, final int error, final SQLException sqle, final String mensaje) {

                loggerApi.throwing(TAG, mensaje + "{" + error + "}", sqle);
            }

            @Override
            public void throwing(final String TAG, final int error, final SQLException sqle, final String mensaje, final Object... params) {

                loggerApi.throwing(TAG, parseParams(mensaje, params) + "{" + error + "}", sqle);
            }

            @Override
            public void throwing(final String TAG, final int error, final SocketException socketException, final String mensaje) {

                loggerApi.throwing(TAG, mensaje + "{" + error + "}", socketException);
            }

            @Override
            public void throwing(final String TAG, final int error, final SocketException socketException, final String mensaje, final Object... params) {

                loggerApi.throwing(TAG, parseParams(mensaje, params) + "{" + error + "}", socketException);
            }

            @Override
            public void throwing(final String TAG, final int error, final SocketTimeoutException sockettimeoutexception, final String mensaje) {

                loggerApi.throwing(TAG, mensaje + "{" + error + "}", sockettimeoutexception);
            }

            @Override
            public void throwing(final String TAG, final int error, final SocketTimeoutException sockettimeoutexception, final String mensaje, final Object... params) {

                loggerApi.throwing(TAG, parseParams(mensaje, params) + "{" + error + "}", sockettimeoutexception);
            }

            @Override
            public void warning(final String TAG, final int error, final String mensaje) {
                loggerApi.info(TAG, mensaje + "{" + error + "}");

            }

            @Override
            public void warning(final String TAG, final int error, final String mensaje, final Object... params) {
                loggerApi.info(TAG, parseParams(mensaje, params) + "{" + error + "}");

            }
        },
        LOGGER_PRODUCCION() {
            @Override
            public void fine(final String TAG, final String mensaje) {
// EN PRODUCCION NO GENERA LOG
            }

            @Override
            public void fine(final String TAG, final String mensaje, final Object... params) {
// EN PRODUCCION NO GENERA LOG
            }

            @Override
            public void fine(final String TAG, final int error, final String mensaje) {
// EN PRODUCCION NO GENERA LOG
            }

            @Override
            public void fine(final String TAG, final int error, final String mensaje, final Object... params) {
// EN PRODUCCION NO GENERA LOG
            }

            @Override
            public void finest(final String TAG, final String mensaje) {
// EN PRODUCCION NO GENERA LOG
            }

            @Override
            public void finest(final String TAG, final String mensaje, final Object... params) {
// EN PRODUCCION NO GENERA LOG
            }

            @Override
            public void finest(final String TAG, final int error, final String mensaje) {
// EN PRODUCCION NO GENERA LOG
            }

            @Override
            public void finest(final String TAG, final int error, final String mensaje, final Object... params) {
// EN PRODUCCION NO GENERA LOG
            }

            @Override
            public void info(final String TAG, final String mensaje) {
// EN PRODUCCION NO GENERA LOG

            }

            @Override
            public void info(final String TAG, final String mensaje, final Object... params) {
// EN PRODUCCION NO GENERA LOG

            }

            @Override
            public void info(final String TAG, final int error, final String mensaje) {
// EN PRODUCCION NO GENERA LOG

            }

            @Override
            public void info(final String TAG, final int error, final String mensaje, final Object... params) {
// EN PRODUCCION NO GENERA LOG

            }

            @Override
            public void setLevel(final Level level) {
// EN PRODUCCION NO GENERA LOG
            }

            @Override
            public void setLimite(final long limit) {
// EN PRODUCCION NO GENERA LOG
            }

            @Override
            public void sql(final String TAG, final String mensaje, final Object... params) {
// EN PRODUCCION NO GENERA LOG

            }

            @Override
            public void throwing(final String TAG, final int error, final Throwable thrwbl, final String mensaje) {
                loggerApi.throwing(TAG, mensaje + "{" + error + "}", thrwbl);
// EN PRODUCCION NO GENERA LOG
            }

            @Override
            public void throwing(final String TAG, final int error, final Throwable thrwbl, final String mensaje, final Object... params) {
                loggerApi.throwing(TAG, mensaje + "{" + error + "}", thrwbl);
// EN PRODUCCION NO GENERA LOG
            }

            @Override
            public void throwing(final String TAG, final int error, final SQLException sqle, final String mensaje) {
                loggerApi.throwing(TAG, mensaje + "{" + error + "}", sqle);
// EN PRODUCCION NO GENERA LOG
            }

            @Override
            public void throwing(final String TAG, final int error, final SQLException sqle, final String mensaje, final Object... params) {
                loggerApi.throwing(TAG, mensaje + "{" + error + "}", sqle);
// EN PRODUCCION NO GENERA LOG
            }

            @Override
            public void throwing(final String TAG, final int error, final SocketException socketException, final String mensaje) {
                loggerApi.throwing(TAG, mensaje + "{" + error + "}", socketException);
// EN PRODUCCION NO GENERA LOG
            }

            @Override
            public void throwing(final String TAG, final int error, final SocketException socketException, final String mensaje, final Object... params) {
                loggerApi.throwing(TAG, mensaje + "{" + error + "}", socketException);
// EN PRODUCCION NO GENERA LOG
            }

            @Override
            public void throwing(final String TAG, final int error, final SocketTimeoutException sockettimeoutexception, final String mensaje) {
                loggerApi.throwing(TAG, mensaje + "{" + error + "}", sockettimeoutexception);
// EN PRODUCCION NO GENERA LOG
            }

            @Override
            public void throwing(final String TAG, final int error, final SocketTimeoutException sockettimeoutexception, final String mensaje, final Object... params) {
                loggerApi.throwing(TAG, mensaje + "{" + error + "}", sockettimeoutexception);
// EN PRODUCCION NO GENERA LOG
            }

            @Override
            public void warning(final String TAG, final int error, final String mensaje) {
// EN PRODUCCION NO GENERA LOG

            }

            @Override
            public void warning(final String TAG, final int error, final String mensaje, final Object... params) {
// EN PRODUCCION NO GENERA LOG
            }
        };
    }

    public interface LoggerApi{

        void info(String TAG, String mensaje);
        void fine(String TAG, String mensaje);
        void throwing(final String TAG, final String mensaje, final Throwable thrwbl);
    }
}