package sigma.Ticket;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.TextAppearanceSpan;

import com.google.zxing.BarcodeFormat;

import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.pagatodo.sigmamanager.Instance.ApiInstance;
import com.pagatodo.sigmamanager.R;


import net.fullcarga.android.api.data.respuesta.CamposTicket;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sigma.general.interfaces.OnFailureListener;

//import sigma.manager.CamposEMVData;
import sigma.manager.SigmaBdManager;
import sigma.utils.Constantes;
import sigma.utils.CustomTypefaceSpan;
import sigma.utils.DateUtils;

import static sigma.manager.AppLogger.LOGGER;


/**
 * Created by Daniel Ruiz on 26/09/2017.
 * Modified by Omar Orozco on February.
 * Clase que generar el Charsequence para generar el ticket
 */
@SuppressWarnings("PMD.GodClass")
public final class TicketGenerator {
    private static final java.lang.String TAG = "TicketGenerator";
    private static final int CHARS_PER_LINE_SMALL_SIZE = 42;
    private static final int CHARS_PER_LINE_NORMAL_SIZE = 32;
    private static final int CHARS_PER_LINE_MEDIUM_SIZE = 28;
    private static final int CHARS_PER_LINE_BIG_SIZE = 24;

    private static final String SMALL_SIZE_CODE = "00";
    private static final String NORMAL_SIZE_CODE = "01";
    private static final String MEDIUM_SIZE_CODE = "02";
    private static final String BIG_SIZE_CODE = "03";

    //    public static final int TEXT_SMALL_SIZE = MposApplication.getInstance().getConfigManager().getTextSizeSmall();
//    public static final int TEXT_NORMAL_SIZE = MposApplication.getInstance().getConfigManager().getTextSizeNormal();
//    public static final int TEXT_MEDIUM_SIZE = MposApplication.getInstance().getConfigManager().getTextSizeMedium();
//    public static final int TEXT_LARGE_SIZE = MposApplication.getInstance().getConfigManager().getTextSizeLarge();
    public static final int TEXT_SMALL_SIZE = 12;
    public static final int TEXT_NORMAL_SIZE = 14;
    public static final int TEXT_MEDIUM_SIZE = 16;
    public static final int TEXT_LARGE_SIZE = 18;

    private static final String SIZE_INDICATOR_CODE = "46";

    private static Bitmap bitmapBarcode;
    private static String boleta;

    private static CamposEMVData camposEMVData;

    public TicketGenerator() {
    }


    public void setCamposEMVData(final CamposEMVData camposEMVData) {
        this.camposEMVData = camposEMVData;
    }

    public static CharSequence getTicketContent(final String ticket, final CamposTicket camposTicket, final SigmaBdManager sigmaBdManager) {
        String ticketTemplate;

        try {
            ticketTemplate = addValuesFormat(ticket, sigmaBdManager, camposTicket);
        } catch (final Exception e) {
            ticketTemplate = fromHexString(ticket);
            LOGGER.throwing(TAG, 1, e, e.getMessage());
        }
        try {
            ticketTemplate = repeatCharacter(ticketTemplate);
        } catch (final Exception e) {
            LOGGER.throwing(TAG, 1, e, e.getMessage());
        }
        try {
            ticketTemplate = replaceDate(ticketTemplate);
        } catch (final Exception e) {
            LOGGER.throwing(TAG, 1, e, e.getMessage());
        }
        try {
            ticketTemplate = replaceImport(ticketTemplate, sigmaBdManager.getNumberFormat(new OnFailureListener.BasicOnFailureListener(TAG, "Error al obtener number format")));
        } catch (final Exception e) {
            LOGGER.throwing(TAG, 1, e, e.getMessage());
        }

        try {
            ticketTemplate = replaceHidden(ticketTemplate);
        } catch (final Exception e) {
            LOGGER.throwing(TAG, 1, e, e.getMessage());
        }

        final SpannableStringBuilder buf = new SpannableStringBuilder(ticketTemplate);

        try {
            applyFonts(buf);
        } catch (final Exception e) {
            LOGGER.throwing(TAG, 1, e, e.getMessage());
        }

        try {
            alignText(buf);
        } catch (final Exception e) {
            LOGGER.throwing(TAG, 1, e, e.getMessage());
        }

        try {
            applyStyleBold(buf);
        } catch (final Exception e) {
            LOGGER.throwing(TAG, 1, e, e.getMessage());
        }
        try {
            applyReverse(buf);
        } catch (final Exception e) {
            LOGGER.throwing(TAG, 1, e, e.getMessage());
        }

        return setLineFeedToWholeTicket(buf);

    }

    /***
     * Agrega los valores  de camposTicket a la plantilla Ticket
     * @param ticketHex
     * @param sigmaBdManager
     * @param camposTicket
     * @return
     */
    private static String addValuesFormat(final String ticketHex, final SigmaBdManager sigmaBdManager, final CamposTicket camposTicket) {//NOSONAR //NOPMD
        final char[] array = ticketHex.toCharArray();
        final StringBuilder bufferHex = new StringBuilder();
        final StringBuilder bufferAscii = new StringBuilder();
        final StringBuilder auxHexBuilder = new StringBuilder();
        final int charsPerLine = CHARS_PER_LINE_NORMAL_SIZE;
        int indexStrHex = 0;
        String strAscii = null;
        final List<Integer> sizesAccordingLineIndex = new ArrayList<>();
        while (indexStrHex < array.length) {
            bufferHex.append(array[indexStrHex]);
            indexStrHex++;
            if (bufferHex.length() != 2) {
                continue;
            }
            if (validateEsc(bufferHex)) {
                final char[] auxEsc = Arrays.copyOfRange(array, indexStrHex, indexStrHex + 2);
                auxHexBuilder.delete(0, auxHexBuilder.length());
                for (final char charHex : auxEsc) {
                    auxHexBuilder.append(charHex);
                    if (auxHexBuilder.length() == 2) {

                        if (auxHexBuilder.toString().equalsIgnoreCase(SIZE_INDICATOR_CODE)) {

                            getSizeAccordingCode(ticketHex.substring(indexStrHex + 2, indexStrHex + 4));
                        }

                        if (validateU(auxHexBuilder)) {
                            try {
                                strAscii = getFormatValueU(Arrays.copyOfRange(array, indexStrHex + 2, indexStrHex + 4), camposTicket);
                            } catch (final Exception e) {
                                LOGGER.throwing(TAG, 1, e, e.getMessage());
                            }
                            indexStrHex += 4;// se aumentan 2 caracteres de 55 mas 2 caracteres que se leyeron
                        } else if (validateV(auxHexBuilder)) {
                            try {
                                strAscii = getFormatValueV(Arrays.copyOfRange(array, indexStrHex + 2, indexStrHex + 8), sigmaBdManager);
                            } catch (final Exception e) {
                                LOGGER.throwing(TAG, 1, e, e.getMessage());
                            }
                            indexStrHex += 8; //  se aumentan 2 caracteres de "56"  de la V yAxis los 6 carateres que se leyeron 2 de la tabla yAxis 4 del registro
                        } else if (validateW(auxHexBuilder)) {
                            try {
                                strAscii = getFormatValueW(Arrays.copyOfRange(array, indexStrHex + 2, indexStrHex + 6), camposTicket);
                            } catch (final Exception e) {
                                LOGGER.throwing(TAG, 1, e, e.getMessage());
                            }
                            indexStrHex += 6;//aumentamos en 2 por el "57" de la W aumentamos en 2 por el campos yAxis 2 por el sub campo
                        } else if (validateE(auxHexBuilder)) {
                            try {
                                strAscii = getFormatValueE(Arrays.copyOfRange(array, indexStrHex + 2, indexStrHex + 4));
                            } catch (final Exception e) {
                                LOGGER.throwing(TAG, 1, e, e.getMessage());
                            }
                            indexStrHex += 4;
                        } else if (validateC(auxHexBuilder)) {
                            try {
                                bitmapBarcode = getFormatValueC(Arrays.copyOfRange(array, indexStrHex + 2, indexStrHex + 8), camposTicket);
                                if (bitmapBarcode != null) {
                                    strAscii = Constantes.BCODE_FIELD;
                                }
                            } catch (final Exception e) {
                                LOGGER.throwing(TAG, 1, e, e.getMessage());
                            }
                        } else {
                            strAscii = fromHexString(bufferHex.toString());
                        }
                    }
                }
                bufferHex.delete(0, bufferHex.length()); //limpiamos ESC
            } else {
                if (bufferHex.toString().equalsIgnoreCase("0A")) {
                    sizesAccordingLineIndex.add(charsPerLine);
                }
                strAscii = fromHexString(bufferHex.toString());
                bufferHex.delete(0, bufferHex.length());// limpiamos buffer
            }
            bufferAscii.append(strAscii); // cambio de hex a ascii

        }
        return bufferAscii.toString();
    }

    private static int getSizeAccordingCode(final String sizeCode) {
        switch (sizeCode) {
            case SMALL_SIZE_CODE:
                return CHARS_PER_LINE_SMALL_SIZE;
            case NORMAL_SIZE_CODE:
                return CHARS_PER_LINE_NORMAL_SIZE;
            case MEDIUM_SIZE_CODE:
                return CHARS_PER_LINE_MEDIUM_SIZE;
            case BIG_SIZE_CODE:
                return CHARS_PER_LINE_BIG_SIZE;
            default:
                break;
        }
        return CHARS_PER_LINE_NORMAL_SIZE;
    }

    /**
     * valida si el ticket tiene ESC
     *
     * @param buffer
     * @return
     */
    private static boolean validateEsc(final StringBuilder buffer) {
        return "1B".equalsIgnoreCase(buffer.toString());
    }

    /**
     * valida si se debe imprimir un campode de la trama ISO
     *
     * @param auxHexBuilder
     * @return
     */
    private static boolean validateU(final StringBuilder auxHexBuilder) {
        return "55".equalsIgnoreCase(auxHexBuilder.toString());
    }

    /**
     * valida si debe repetirse un valor
     *
     * @param auxHexBuilder
     * @return
     */
    private static boolean validateV(final StringBuilder auxHexBuilder) {
        return "56".equalsIgnoreCase(auxHexBuilder.toString());
    }

    /**
     * Valida si debe imprimirse un subcampo de la trama ISO
     *
     * @param auxHexBuilder
     * @return
     */
    private static boolean validateW(final StringBuilder auxHexBuilder) {
        return "57".equalsIgnoreCase(auxHexBuilder.toString());
    }

    /**
     * Valida si debe imprimirse un campo EMV
     */
    private static boolean validateE(final StringBuilder auxHexBuilder) {
        return "45".equalsIgnoreCase(auxHexBuilder.toString());
    }

    /**
     * Valida si debe imprimirse un campo Code
     */
    private static boolean validateC(final StringBuilder auxHexBuilder) {
        return "43".equalsIgnoreCase(auxHexBuilder.toString());
    }


    /*ESC U Var Imprime un campo ISO de la trama*/

    /**
     * Imprime el valor de la trama ISO
     *
     * @param array
     * @param camposTicket
     * @return
     */
    private static String getFormatValueU(final char[] array, final CamposTicket camposTicket) {
        final StringBuilder stringHex = new StringBuilder();
        final StringBuilder stringAscii = new StringBuilder();
        for (final char character : array) {
            stringHex.append(character);
            if (stringHex.length() == 2) {
                stringAscii.append(camposTicket.getTicketField(Integer.parseInt(stringHex.toString(), 16)));
            }
        }
        return stringAscii.toString();
    }

    //Formato ESC W Var1 Var2 Imprime un subcampo ISO de la trama
    private static String getFormatValueW(final char[] array, final CamposTicket camposTicket) {
        final StringBuilder stringHex = new StringBuilder();
        final StringBuilder stringAscii = new StringBuilder();
        int field = -1;
        for (final char character : array) {
            stringHex.append(character);
            if (stringHex.length() == 2) {
                field = Integer.parseInt(stringHex.toString(), 16);
            } else if (stringHex.length() == 4 && field != -1) {
                try {
                    stringAscii.append(camposTicket.getTicketSubField(field, Integer.parseInt(stringHex.toString().substring(2, 4), 16)));
                } catch (IndexOutOfBoundsException e) {
                    LOGGER.throwing(TAG, 1, e, e.getMessage());
                }
            }
        }
        return stringAscii.toString();
    }

    private static String getFormatValueE(final char... array) {
        final StringBuilder stringHex = new StringBuilder();
        final StringBuilder stringAscii = new StringBuilder();
        for (final char character : array) {
            stringHex.append(character);
            if (stringHex.length() == 2) {
                stringAscii.append(camposEMVData.getCamposbydigit(Integer.parseInt(String.valueOf(character))));
            }
        }
        return stringAscii.toString();
    }

    /*Formato ESC V Var1 Var2 Imprime un valor de la tabla de inicialización, siendo Var1 el Id de tabla (1byte), yAxis Var2 el Id del registro (2bytes).*/
    private static String getFormatValueV(final char[] array, final SigmaBdManager sigmaBdManager) {
        final StringBuilder stringHex = new StringBuilder();
        final StringBuilder stringAscii = new StringBuilder();
        int tableID = -1;

        final OnFailureListener onLiteralFailureListener = new OnFailureListener.BasicOnFailureListener(TAG, "Error obtener literal ticket");
        final OnFailureListener onParamFailureListener = new OnFailureListener.BasicOnFailureListener(TAG, "Error al obtener parametro fijo");

        for (final char character : array) {

            stringHex.append(character);

            if (stringHex.length() == 2) {

                tableID = Integer.parseInt(stringHex.toString(), 16);
            } else if (stringHex.length() == 6 && tableID == 0) {

                stringAscii.append(sigmaBdManager.getParametroFijo(stringHex.toString().substring(2, 6), onParamFailureListener));
            } else if (stringHex.length() == 6 && tableID == 16) {

                stringAscii.append(sigmaBdManager.getLiteralTicket(Integer.parseInt(stringHex.toString().substring(2, 6), 16), onLiteralFailureListener).getValor());
            }
        }

        return stringAscii.toString();
    }

    private static Bitmap getFormatValueC(final char[] array, final CamposTicket camposTicket) {
        final StringBuilder stringHex = new StringBuilder();
        final StringBuilder stringAscii = new StringBuilder();
        int type = -1;
        int field = -1;
        int subFlied = -1;
        for (final char character : array) {
            stringHex.append(character);
            if (stringHex.length() == 2) {
                type = Integer.parseInt(stringHex.toString().substring(0, 2), 16);
            }
            if (stringHex.length() == 4) {
                field = Integer.parseInt(stringHex.toString().substring(2, 4), 16);
            }
            if (stringHex.length() == 6) {
                subFlied = Integer.parseInt(stringHex.toString().substring(4, 6), 16);
                try {
                    stringAscii.append(camposTicket.getTicketSubField(field, subFlied));
                } catch (IndexOutOfBoundsException e) {
                    LOGGER.throwing(TAG, 1, e, e.getMessage());
                }
            }
        }
        boleta = stringAscii.toString();
        return encodeAsBitmap(stringAscii.toString(), getFormatCode(type));
    }

    private static String replaceImport(final String ticketTemplate, final NumberFormat numberFormat) {
        final StringBuffer buffer = new StringBuffer();
        final String regexScript = "\\eI(-?\\d+)";
        final Pattern pattern = Pattern.compile(regexScript);
        final Matcher matcher = pattern.matcher(ticketTemplate);
        while (matcher.find()) {
            final String ticketImporte = Matcher.quoteReplacement(
                    numberFormat.format(
                            ApiInstance.getInstance().getDatosSesion().getDatosTPV().convertirImporte(matcher.group(1))
                    )
            );
            matcher.appendReplacement(buffer, ticketImporte);
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    private static String replaceHidden(final String ticketTemplate) {
        final StringBuffer buffer = new StringBuffer();
        final String regexScript = "\\eH(\\d*)";
        final Pattern pattern = Pattern.compile(regexScript);
        final Matcher matcher = pattern.matcher(ticketTemplate);
        while (matcher.find()) {
            final String ticketImporte = Matcher.quoteReplacement(
                    matcher.group(1));
            matcher.appendReplacement(buffer, ticketImporte);
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    private static String replaceDate(final String ticketTemplate) {
        return ticketTemplate.replaceAll("\\eD", DateUtils.getDateNow()).replaceAll("\\eT", DateUtils.getNowHour());
    }

    private static String repeatCharacter(final String ticketTemplate) {

        String string = ticketTemplate;

        final String regexScript = "\\eS(.+)";
        final Pattern pattern = Pattern.compile(regexScript);
        final Matcher matcher = pattern.matcher(string);

        final StringBuilder repeatedString = new StringBuilder();

        while (matcher.find()) {

            for (int index = 0; index < matcher.groupCount(); index++) {

                final String formatChar = matcher.group(index).substring(0, 4);
                final String charToRepeat = String.valueOf(formatChar.charAt(2));
                final int timesToRepeat = formatChar.charAt(3);

                repeatedString.delete(0, repeatedString.length());

                for (int i = 0; i < timesToRepeat; i++) {

                    repeatedString.append(charToRepeat);
                }

                string = string.replace(formatChar, repeatedString);
            }
        }
        return string;
    }

    private static void applyStyleBold(final SpannableStringBuilder ticket) {

        SpannableStringBuilder spannableStringBuilder = ticket;

        final String regexScript = "\\eB(.+)";
        final Pattern pattern = Pattern.compile(regexScript);
        final Matcher matcher = pattern.matcher(spannableStringBuilder);

        final TextAppearanceSpan textAppearanceSpanBold = new TextAppearanceSpan(ApiInstance.getInstance().getAppcontext(), R.style.TicketBold);

        while (matcher.find()) {
            for (int index = 0; index < matcher.groupCount(); index++) {
                final String boldFormat = matcher.group(index).substring(0, 2);
                int start = spannableStringBuilder.toString().indexOf(matcher.group(index));
                int end = start + matcher.group().length();
                final String textBold = matcher.group(index).replace(boldFormat, "");
                spannableStringBuilder = spannableStringBuilder.replace(start, end, textBold);
                start = spannableStringBuilder.toString().indexOf(textBold);
                end = start + textBold.length();
                spannableStringBuilder.setSpan(textAppearanceSpanBold, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
    }

    private static void applyReverse(final SpannableStringBuilder ticket) {

        SpannableStringBuilder spannableStringBuilder = ticket;

        final String regexScript = "\\eR(.+)";
        final Pattern pattern = Pattern.compile(regexScript);
        final Matcher matcher = pattern.matcher(spannableStringBuilder);

        final ForegroundColorSpan foregroundColorSpanWhite = new ForegroundColorSpan(Color.WHITE);
        final BackgroundColorSpan backgroundColorSpanBlack = new BackgroundColorSpan(Color.BLACK);

        while (matcher.find()) {
            for (int index = 0; index < matcher.groupCount(); index++) {
                int start = spannableStringBuilder.toString().indexOf(matcher.group(index));
                int end = start + matcher.group().length();
                final String textAlign = matcher.group(index).replace(matcher.group().substring(0, 2), "");
                spannableStringBuilder = spannableStringBuilder.replace(start, end, textAlign);
                start = spannableStringBuilder.toString().indexOf(textAlign);
                end = start + textAlign.length();
                spannableStringBuilder.setSpan(foregroundColorSpanWhite, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableStringBuilder.setSpan(backgroundColorSpanBlack, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    private static void applyFonts(final SpannableStringBuilder buffer) {
        final String regexScript = "\\eF(.+)";
        final Pattern mPattern = Pattern.compile(regexScript);
        final Matcher matcher = mPattern.matcher(buffer);
        final List<Integer> indexes = new ArrayList<>();
        final List<Integer> sizes = new ArrayList<>();
        int index = 0;
        while (matcher.find()) {
            final String pivot = matcher.group();
            final String fontFormatAuxiliar = pivot.substring(0, 3); //traemos el formato de posicion ESC F Var
            indexes.add(buffer.toString().indexOf(pivot));
            sizes.add((int) fontFormatAuxiliar.charAt(fontFormatAuxiliar.length() - 1));

            buffer.replace(indexes.get(index), (indexes.get(index) + pivot.length()), pivot.replace(fontFormatAuxiliar, ""));
            index++;
        }

        if (!indexes.isEmpty()) {
            index = 0;
            if (indexes.get(index) != 0) {
                setFontSize(buffer, 1, 0, indexes.get(index));
            }
            for (index = 0; index < indexes.size(); index++) {
                if (index == indexes.size() - 1) {
                    setFontSize(buffer, sizes.get(index), indexes.get(index), buffer.length());
                } else {
                    setFontSize(buffer, sizes.get(index), indexes.get(index), indexes.get(index + 1));
                }
            }
        } else {
            setFontSize(buffer, 1, 0, buffer.length());
        }
        //Below code was asked as a way for avoiding irregular spacing chars.
        final Typeface monoSpaceFont = Typeface.createFromAsset(ApiInstance.getInstance().getAppcontext().getAssets(), "lucida.otf"); //
        buffer.setSpan(new CustomTypefaceSpan("", monoSpaceFont), 0, buffer.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
    }

    private static void setFontSize(final SpannableStringBuilder buffer, final int fontKind, final int start, final int end) {
        switch (fontKind) {
            case 0:

                buffer.setSpan(new AbsoluteSizeSpan(TEXT_SMALL_SIZE, true), start, end,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                break;
            case 1:
                buffer.setSpan(new AbsoluteSizeSpan(TEXT_NORMAL_SIZE, true), start, end,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                break;
            case 2:
                buffer.setSpan(new AbsoluteSizeSpan(TEXT_MEDIUM_SIZE, true), start, end,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                break;
            case 3:
                buffer.setSpan(new AbsoluteSizeSpan(TEXT_LARGE_SIZE, true), start, end,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                break;
            default:
                buffer.setSpan(new AbsoluteSizeSpan(TEXT_NORMAL_SIZE, true), start, end,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                break;
        }
    }

    private static void alignText(SpannableStringBuilder ticket) {//NOPMD se debe modificar la misma variable que llega
        // Busqueda  ESC A

        final String regexScript = "\\eA(.+)";
        final Pattern pattern = Pattern.compile(regexScript);
        final Matcher matcher = pattern.matcher(ticket);


        final AlignmentSpan.Standard alignmetNormal = new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL);
        final AlignmentSpan.Standard alignmetCenter = new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER);
        final AlignmentSpan.Standard alignmetOpposite = new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE);

        while (matcher.find()) {

            for (int index = 0; index < matcher.groupCount(); index++) {

                final String aling = matcher.group(index).substring(0, 3); //traemos el formato de posicion ESC A Var
                final int alignVar = aling.charAt(aling.length() - 1); //obtenemos var para posicionar
                int start = ticket.toString().indexOf(matcher.group(index));
                int end = start + matcher.group().length();
                final String textAlign = matcher.group(index).replace(aling, "");
                ticket = ticket.replace(start, end, textAlign);
                start = ticket.toString().indexOf(textAlign);
                end = start + textAlign.length();

                switch (alignVar) {
                    case 0:// left
                        ticket.setSpan(alignmetNormal, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        break;
                    case 1://center
                        ticket.setSpan(alignmetCenter, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        break;
                    case 2://rigth
                        ticket.setSpan(alignmetOpposite, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        break;
                    default:
                }
            }
        }
    }

    public static String fromHexString(final String hex) {
        final StringBuilder str = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2) {
            str.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
        }
        return str.toString();
    }

    public static String getBoletaString() {
        return boleta;
    }

    public static void clearBoleta() {
        boleta = null;
        bitmapBarcode = null;
    }

    private static SpannableStringBuilder setLineFeedToWholeTicket(final SpannableStringBuilder spannableBuffer) {

        final String[] lines = spannableBuffer.toString().trim().split("\n");

        for (final String line : lines) {

            if (line.length() > 1) {

                final int start = spannableBuffer.toString().indexOf(line);
                final int end = spannableBuffer.toString().indexOf(line) + line.length();
                spannableBuffer.replace(start, end, lineByTextSize(line, spannableBuffer.getSpans(start, end, AbsoluteSizeSpan.class)));
            }
        }

        if (bitmapBarcode != null) {
            final Drawable drawable = new BitmapDrawable(bitmapBarcode);
            drawable.setBounds(0, 10, (int) (drawable.getIntrinsicWidth() / 1.75), drawable.getIntrinsicHeight());
            final ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
            final int start = spannableBuffer.toString().indexOf(Constantes.BCODE_FIELD);
            spannableBuffer.setSpan(imageSpan, start, start + Constantes.BCODE_FIELD.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        return spannableBuffer;
    }

    /**
     * @param text
     * @param styleSpans Metodo para obtener los tamaños de letra encontrado en cada Linea del Ticket.
     * @return
     */
    private static String lineByTextSize(final String text, final AbsoluteSizeSpan... styleSpans) {

        int lineLimit = CHARS_PER_LINE_NORMAL_SIZE;
        for (final AbsoluteSizeSpan span : styleSpans) {
            if (span.getSize() > 0) {
                if (span.getSize() == TEXT_SMALL_SIZE) {
                    lineLimit = CHARS_PER_LINE_SMALL_SIZE;
                } else if (span.getSize() == TEXT_MEDIUM_SIZE) {
                    lineLimit = CHARS_PER_LINE_MEDIUM_SIZE;
                } else if (span.getSize() == TEXT_LARGE_SIZE) {
                    lineLimit = CHARS_PER_LINE_BIG_SIZE;
                }
                break;
            }
        }
        if (text.length() > lineLimit) {
            return text.substring(0, lineLimit) + "\n" + lineByTextSize(text.substring(lineLimit, text.length()), styleSpans);
        } else {
            return text;
        }
    }

    private static Bitmap encodeAsBitmap(final String contents, final BarcodeFormat format) {
        if (contents.isEmpty() || format == null) {
            return null;
        }

        try {
            Map<EncodeHintType, Object> hints = null;
            hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.MARGIN, 0);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            final MultiFormatWriter writer = new MultiFormatWriter();
            final BitMatrix bitMatrix = writer.encode(contents, format, 1, 1, hints);
            final Bitmap bitmap = Bitmap.createBitmap(bitMatrix.getWidth(), bitMatrix.getHeight(), Bitmap.Config.ARGB_8888);
            for (int i = 0; i < bitMatrix.getWidth(); i++) {
                for (int j = 0; j < bitMatrix.getHeight(); j++) {
                    bitmap.setPixel(i, j, bitMatrix.get(i, j) ? Color.BLACK : Color.WHITE);
                }
            }
            return bitmap;
        } catch (WriterException e) {
            Logger.getLogger(e.getMessage());
            return null;
        }
    }

    private static BarcodeFormat getFormatCode(final int tipo) {
        switch (tipo) {
            case 1:
                //
                return null;
            case 2:
                //
                return null;
            case 3:
                //
                return null;

            case 4:
                return BarcodeFormat.PDF_417;
            default:
                break;
        }
        return null;
    }
}
