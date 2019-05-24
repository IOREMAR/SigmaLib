package sigma.utils;

import android.util.Base64;

import static java.nio.charset.StandardCharsets.ISO_8859_1;

public final class Base64Util {

    private Base64Util() {

    }

    public static String b64encode(final byte[] bytes) {
        return new String(Base64.encode(bytes, Base64.NO_WRAP), ISO_8859_1);
    }

    public static byte[] b64decode(final String str) {
        return Base64.decode(str, Base64.NO_WRAP);
    }
}
