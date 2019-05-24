package sigma.utils;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

public final class NotificationsUtils {

    private NotificationsUtils() {
        //no utilizado
    }

    public static void construirNotificacion
            (final Context paramContext, final int paramIcon, final String paramTitulo, final String paramMsg) {

        final Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final NotificationManager administradorNotificaciones = (NotificationManager) paramContext.getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationCompat.Builder contructorNotificaciones =
                new NotificationCompat.Builder(paramContext, "chanelid")
                        .setSmallIcon(paramIcon)
                        .setContentTitle(paramTitulo)
                        .setContentText(paramMsg)
                        .setSound(alarmSound)
                        .setVibrate(new long[]{500, 500})
                        .setLights(Color.WHITE, 3000, 3000)
                        .setAutoCancel(true);

        assert administradorNotificaciones != null;
        administradorNotificaciones.notify(112, contructorNotificaciones.build());

    }
}