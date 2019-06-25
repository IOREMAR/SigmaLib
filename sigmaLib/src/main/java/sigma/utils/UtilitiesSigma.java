package sigma.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.inputmethod.InputMethodManager;

//import com.crashlytics.android.Crashlytics;
//import com.google.firebase.analytics.FirebaseAnalytics;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
//import com.pagatodoholdings.posandroid.MposApplication;
//import com.pagatodoholdings.posandroid.R;
//import com.pagatodoholdings.posandroid.analytics.Event;
//import com.pagatodoholdings.posandroid.general.interfaces.ConfigManager;
//import com.pagatodoholdings.posandroid.general.interfaces.PreferenceManager;
//import com.pagatodoholdings.posandroid.secciones.home.HomeActivity;
//
//import net.fullcarga.android.api.bd.sigma.generated.tables.pojos.Menu;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.text.NumberFormat;
import java.util.List;

import sigma.manager.AppLogger;
import sigma.manager.NivelMenu;
import sigma.manager.StorageUtility;
import sigma.utils.Constantes;
import sigma.utils.Constantes.Preferencia;

public class UtilitiesSigma {

    private static boolean sIsTablet = false;
    private static int sDecimalesPais = 0;
    private static String sNumeroSerie = "";

    private UtilitiesSigma(){
    }

    public static boolean isTablet() {
        return sIsTablet;
    }

    public static void setIsTablet(boolean isTablet) {
        sIsTablet = isTablet;
    }

    public static int getDecimalesPais() {
        return sDecimalesPais;
    }

    public static void setDecimalesPais(int decimalesPais) {
        sDecimalesPais = decimalesPais;
    }

    public static void setNumeroSerie(String serie) {
        sNumeroSerie = serie;
    }

    public static String getNumeroSerie() {
        return sNumeroSerie;
    }

//    public static int getAttributeReference(final int attr, Context context) {
//        final TypedArray typedArray = context.obtainStyledAttributes(
//                Constantes.COLOR_APP_AZUL.equals(MposApplication.getInstance().getConfigManager().getColorAplication()) ? R.style.AppThemeBlue : R.style.AppThemeRed,
//                new int[]{attr});
//        return typedArray.getResourceId(0, 1);
//    }

    public static byte[] getLogoArray(final String pathIcono) {

        if (!("").equals(pathIcono)) {
            final BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            final Bitmap bitmap = BitmapFactory.decodeFile(StorageUtility.getStoragePath() + pathIcono, bmOptions);
            if (bitmap != null) {
                return getByteArray(bitmap);
            } else {
                return new byte[0];
            }
        }
        return new byte[0];
    }

    public static void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }

        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
    }

    public static byte[] getLogoArraImpress(final String pathIcono) {

        if (!("").equals(pathIcono)) {
            final BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            final Bitmap bitmap = BitmapFactory.decodeFile(StorageUtility.getStoragePath() + pathIcono, bmOptions);
            if (bitmap != null) {
                final UtilsPrinter utilsPrinter = new UtilsPrinter();
                final Bitmap imageRezized = rezizeImage(bitmap);
                return utilsPrinter.decodeBitmap(padCenter(imageRezized, 0, 50));
            } else {
                return new byte[0];
            }
        }
        return new byte[0];
    }

    private static Bitmap padCenter(final Bitmap src, final int padding_x, final int padding_y) {
        final Bitmap outputimage = Bitmap.createBitmap(src.getWidth() + padding_x, src.getHeight() + padding_y, Bitmap.Config.ARGB_8888);
        final Canvas can = new Canvas(outputimage);
        can.drawARGB(1, 255, 255, 255); //This represents White color
        can.drawBitmap(src, padding_x, 0, null);
        return outputimage;
    }

    private static Bitmap rezizeImage(final Bitmap bitmap) {

        final int Height = bitmap.getHeight();
        final int Width = bitmap.getWidth();
        final int newHeight = 80;  // estas medidas son las maximas aceptadas para la impresora de peru
        final int newWidth = 380;
        final float scaleWidth = ((float) newWidth) / Width;
        final float scaleHeight = ((float) newHeight) / Height;
        final Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, Width, Height, matrix, true);
    }

//    public static Drawable getIcono(final String pathIcono) {
//        Drawable icono = ResourcesCompat.getDrawable(MposApplication.getInstance().getResources(), getAttributeReference(R.attr.fullcarga), null);
//        if (!("").equals(pathIcono)) {
//            icono = Drawable.createFromPath(StorageUtility.getStoragePath() + pathIcono);
//        }
//        return icono;
//    }

    public static NivelMenu getNivelMenuDeInt(final int numeroNivel) {
        switch (numeroNivel) {
            case 1:
                return NivelMenu.SEGUNDO_NIVEL;
            case 2:
                return NivelMenu.TERCER_NIVEL;
            case 3:
                return NivelMenu.CUARTO_NIVEL;
            default:
                return NivelMenu.SEGUNDO_NIVEL;
        }
    }

    public static BigDecimal cleanImporteInput(final String importe, final NumberFormat numberFormat) {

        final String cleanString = numbers(importe.replaceAll(numberFormat.getCurrency().getSymbol(), "").replace(" ", ""));
        if (cleanString.length() > 0) {
            if (numberFormat.getMaximumFractionDigits() != 0) {
                return new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
            } else {
                return new BigDecimal(cleanString).setScale(0, BigDecimal.ROUND_DOWN);
            }
        }

        return BigDecimal.valueOf(0.00);
    }

    public static String numbers(final String importe) {
        final StringBuilder numbers = new StringBuilder();
        for (short indice = 0; indice < importe.length(); indice++) {
            final char caracter = importe.charAt(indice);
            if (isNumeric(caracter)) {
                numbers.append(caracter);
            }
        }
        return numbers.toString();
    }

    private static boolean isNumeric(final char caracter) {
        try {
            Integer.parseInt(String.valueOf(caracter));
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Metodo para crear el pdf contenedor del ticket
     *
     * @param nombrePDF El parametro es el nombre del archivo PDF
     * @param textoPDF  Este parametro contiene el Texto que sera enviado por PDF.
     * @return = El archivo completo o un nulo si no se a podido crear.
     */
    public static File createFilePDF(final String nombrePDF, final String textoPDF, final com.itextpdf.text.Image imagePDF, final com.itextpdf.text.Image imageLogoPDF) {
        boolean isCreated;
        final File pdfFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!pdfFolder.exists()) {
            isCreated = pdfFolder.mkdir();
        } else {
            isCreated = true;
        }
        final File myFile = new File(pdfFolder + File.separator + nombrePDF + ".pdf");
        if (isCreated) {
            createPDF(myFile, textoPDF, imagePDF, imageLogoPDF);
        } else {
            return null;
        }
        return myFile;
    }

    /**
     * Genera un archivo PDF Generico.
     *
     * @param file Archivo para escrbir.
     * @param data Datos a escribir.
     */
    public static void createPDF(final File file, final String data, final com.itextpdf.text.Image imagePDF, final Image imageLogoPDF) {
        try {
            final Document document = new Document();
            final PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            document.setPageSize(PageSize.A4);
            document.setMargins(20, 20, 10, 10);
            document.addAuthor("MposApp");
            document.addCreator("MposApp");

            if (imageLogoPDF != null) {
                final Paragraph paragraph1 = new Paragraph();
                paragraph1.setFont(FontFactory.getFont(FontFactory.COURIER, 10, Font.NORMAL));
                paragraph1.add(imageLogoPDF);
                document.add(paragraph1);
            }

            final Paragraph paragraph2 = new Paragraph();
            paragraph2.setFont(FontFactory.getFont(FontFactory.COURIER, 10, Font.NORMAL));
            paragraph2.add(data);
            paragraph2.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph2);

            if (imagePDF != null) {
                final Paragraph paragraph3 = new Paragraph();
                paragraph3.setFont(FontFactory.getFont(FontFactory.COURIER, 10, Font.NORMAL));
                paragraph3.add(imagePDF);
                document.add(paragraph3);
            }

            document.close();
            pdfWriter.close();
        } catch (DocumentException | FileNotFoundException documentException) {
            AppLogger.LOGGER.throwing(file.getName(), 1, documentException, documentException.getMessage());
        }
    }

    public static void hideSoftKeyboard(final Activity activity) {
        final InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    private static byte[] getByteArray(final Bitmap bitmap) {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        return bos.toByteArray();
    }

//    public static void analyticsLogEventVenta(final Activity activity, final FirebaseAnalytics firebaseAnalytics) {
//        if (activity instanceof HomeActivity) {
//            final List<Menu> menus = ((HomeActivity) activity).getListaMenus();
//            if (menus != null) {
//                final StringBuilder stringBuilder = new StringBuilder();
//                stringBuilder.append(Event.EVENT_VENTA);
//                for (final Menu menu : menus) {
//                    stringBuilder.append(ANALYTICS_SEPARADOR + menu.getTexto());
//                    if (menus.indexOf(menu) == 0) {
//                        firebaseAnalytics.logEvent(stringBuilder.toString(), null);
//                    }
//                }
//                firebaseAnalytics.logEvent(stringBuilder.toString(), null);
//            }
//        }
//    }

//    public static void appendDeviceInfo() {
//
//        final ConfigManager configManager = MposApplication.getInstance().getConfigManager();
//        final PreferenceManager preferenceManager = MposApplication.getInstance().getPreferenceManager();
//
//        try {
//            Crashlytics.setString("ConfigManager.getConfigBuildType", configManager.getConfigBuildType());
//            Crashlytics.setString("ConfigManager.getConfigClaveTPV", preferenceManager.isExiste(Preferencia.DATOS_SESION) ? preferenceManager.getDatosSesion().getDatosTPV().getTpvcod() : "Empty");
//            Crashlytics.setString("ConfigManager.getConfigSerie", configManager.getConfigSerie());
//            Crashlytics.setString("ConfigManager.getConfigTipoApk", configManager.getConfigTipoApk());
//            Crashlytics.setInt("ConfigManager.getVersionCode", configManager.getVersionCode());
//            Crashlytics.setString("ConfigManager.getVersionName", configManager.getVersionName());
//            Crashlytics.setInt("PreferenceManager.getValue(PreferenceManager.Preferencia.TIEMPO_INACTIVIDAD, \"\")", preferenceManager.getValue(sigma.utils.Constantes.Preferencia.TIEMPO_INACTIVIDAD));
//        } catch (Exception exe) {
//            AppLogger.LOGGER.throwing("appendTransactionSigmaInfo", 1, exe, exe.getMessage());
//        }
//    }

    public static byte[] readLine(String Filename, Context context) {

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            ContextWrapper contextWrapper = new ContextWrapper(context);
            AssetManager assetManager = contextWrapper.getAssets();
            InputStream inputStream = assetManager.open(Filename);
            byte[] data = new byte[50];
            int current;
            while ((current = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, current);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buffer.toByteArray();
    }

    public static byte[] getBarcodeArrayPrint(final Bitmap bitmap) {
        if (bitmap != null) {
            final UtilsPrinter utilsPrinter = new UtilsPrinter();
            return utilsPrinter.decodeBitmap(Bitmap.createScaledBitmap(bitmap, 380, 180, false));
        }
        return new byte[0];
    }

    public static String toCamelCase(String str) {
        if (str == null) {
            return null;
        }

        boolean space = true;
        StringBuilder builder = new StringBuilder(str);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }
}
