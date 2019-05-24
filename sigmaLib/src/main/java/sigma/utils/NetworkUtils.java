package sigma.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class NetworkUtils {

    private NetworkUtils() {
    }

    public static boolean isConnected(final Context context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (activeNetwork != null) {
            switch (activeNetwork.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    return activeNetwork.getState() == NetworkInfo.State.CONNECTED;

                case ConnectivityManager.TYPE_ETHERNET:
                    return activeNetwork.getState() == NetworkInfo.State.CONNECTED;

                case ConnectivityManager.TYPE_MOBILE:
                    return activeNetwork.getState() == NetworkInfo.State.CONNECTED;

                default:
                    return activeNetwork.isConnected() && activeNetwork.isAvailable();
            }
        } else {
            return false;
        }

    }
}
