package sigma.utils;

import android.util.Pair;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import sigma.manager.AppLogger;
import sigma.manager.StorageUtility;

public final class UnzipUtility {

    private static final int BUFFER_SIZE = 512;
    private static final Long TOOBIG = 0x6400000L;//100MB
    private static final Long TOOMANY = 1024L;

    private static final String TAG = UnzipUtility.class.getSimpleName();

    private UnzipUtility() {
    }

    private static String validateFilename(final String filename, final String intendedDir)
            throws IOException {

        final String canonicalPath = new File(filename).getCanonicalPath();
        final String canonicalID = new File(intendedDir).getCanonicalPath();

        if (canonicalPath.startsWith(canonicalID)) {
            return canonicalPath;
        } else {
            throw new IllegalStateException("El Archivo está Fuera del Directorio Destino de Extracción.");
        }
    }

    /**
     * Obtiene el tamaño total de los archivos dentro del zip
     */
    private static Pair<Long, Long> getZipSizeAndEntries(final String fileNameZip) throws IOException {
        long fileSizeUnZip = 0;
        long entries = 0;

        final ZipFile zipfile = new ZipFile(fileNameZip);
        final Enumeration zipEnum = zipfile.entries();

        while (zipEnum.hasMoreElements()) {
            final ZipEntry zipEntry = (ZipEntry) zipEnum.nextElement();
            fileSizeUnZip += (int) zipEntry.getSize();
            entries++;
        }
        zipfile.close();
        return new Pair<>(fileSizeUnZip, entries);
    }

    public static void unzipFile(final String fileZipName, final String unZipPath) throws IOException {

        final Pair<Long, Long> pair = getZipSizeAndEntries(fileZipName);
        if (pair.first == 0) {
            throw new IOException("ZIP Incompleto");
        }
        if (pair.first >= TOOBIG) {
            throw new IllegalStateException("File being unzipped is too big.");
        }
        if (pair.second >= TOOMANY) {
            throw new IllegalStateException("File being unzipped is too many entries.");
        }

       AppLogger.LOGGER.info("UNZIP", "Unzipping, file {}, Size Total {}", pair.first, fileZipName);

        try (final ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(new File(fileZipName)))) {
            ZipEntry zipEntry;

            while ((zipEntry = zipInputStream.getNextEntry()) != null) {

                final String name = validateFilename(unZipPath + "/" + zipEntry.getName(), ".");
                AppLogger. LOGGER.info("UNZIP", "Unzipping: {}/{}", unZipPath, zipEntry.getName());

                if (zipEntry.isDirectory()) {
                    AppLogger.LOGGER.info(TAG, "Creating directory {}", name);
                    new File(name).mkdir(); //NOPMD Crear objetos dentro de un bucle
                } else {
                    unzipEntry(zipInputStream, name);
                }

                zipInputStream.closeEntry();
            }
        } catch (ZipException exc) {
            throw new IOException("Error al descomprimir el archvio: " + fileZipName, exc);
        } finally {
            StorageUtility.deleteIfExistFile(fileZipName);
        }
    }

    private static void unzipEntry(final ZipInputStream zipInputStream, final String name) throws IOException {
        final byte[] buffer = new byte[BUFFER_SIZE];

        try (BufferedOutputStream bufout = new BufferedOutputStream(new FileOutputStream(name), BUFFER_SIZE)) {
            int read = 0;
            while ((read = zipInputStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
                bufout.write(buffer, 0, read);
            }

            bufout.flush();
        }
    }
}
