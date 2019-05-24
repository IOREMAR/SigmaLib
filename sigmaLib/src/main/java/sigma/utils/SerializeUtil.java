package sigma.utils;

import net.fullcarga.android.api.util.HexUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author dsoria
 */
public final class SerializeUtil {

    private SerializeUtil() {
    }

    public static <S extends Serializable> String serialize(final S obj) {

        if (obj == null) {
            throw new IllegalArgumentException("Intentando Serializar Objeto Null");
        }
        try (
                ByteArrayOutputStream arrayStream = new ByteArrayOutputStream();
                ObjectOutputStream objStream = new ObjectOutputStream(arrayStream);
        ) {

            objStream.writeObject(obj);
            return HexUtil.hexStringFromBytes(arrayStream.toByteArray());

        } catch (IOException exc) {
            throw new IllegalArgumentException(exc);
        }
    }

    public static <S extends Serializable> S deserialize(final String str) {

        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("Intentando Deserializar Cadena Vacía");
        }
        try (
                final ByteArrayInputStream serialObj = new ByteArrayInputStream(HexUtil.hex2byte(str, UTF_8));
                final ObjectInputStream objStream = new ObjectInputStream(serialObj);
        ) {

            return (S) objStream.readObject();

        } catch (IOException | ClassNotFoundException exc) {
            throw new IllegalStateException(exc);
        }
    }
}
