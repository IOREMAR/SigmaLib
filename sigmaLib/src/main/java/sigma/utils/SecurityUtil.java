package sigma.utils;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static sigma.utils.Base64Util.b64decode;
import static sigma.utils.Base64Util.b64encode;

/**
 * @author dsoria
 */
public final class SecurityUtil {

    private static final String ALGORITMO = "DESede";

    private SecurityUtil() {
    }

    public static String encrypt(final String value, final String key) throws GeneralSecurityException {
        final Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(Cipher.ENCRYPT_MODE, getKey(key));

        return b64encode(cipher.doFinal(value.getBytes(ISO_8859_1)));
    }

    public static String decrypt(final String encryptValue, final String key) throws GeneralSecurityException {
        final Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(Cipher.DECRYPT_MODE, getKey(key));

        return new String(cipher.doFinal(b64decode(encryptValue)), ISO_8859_1);
    }

    static SecretKey getKey(final String key) throws NoSuchAlgorithmException {
        return new SecretKeySpec(
                Arrays.copyOfRange(
                        MessageDigest.getInstance("SHA-1").digest(key.getBytes(ISO_8859_1)),
                        0, 24),
                ALGORITMO);
    }
}
