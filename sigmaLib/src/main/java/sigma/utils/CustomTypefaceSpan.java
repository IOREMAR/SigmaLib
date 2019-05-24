package sigma.utils;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

/**
 * Created by rvargas on 01/02/2018.
 */

public class CustomTypefaceSpan extends TypefaceSpan {

    private final Typeface newType;

    public CustomTypefaceSpan(final String family, final Typeface type) {
        super(family);
        newType = type;
    }

    @Override
    public void updateDrawState(final TextPaint textPaint) {
        applyCustomTypeFace(textPaint, newType);
    }

    @Override
    public void updateMeasureState(final TextPaint paint) {
        applyCustomTypeFace(paint, newType);
    }

    private static void applyCustomTypeFace(final Paint paint, final Typeface typeface) {
        int oldStyle;
        final Typeface old = paint.getTypeface();
        if (old == null) {
            oldStyle = 0;
        } else {
            oldStyle = old.getStyle();
        }

        final int fake = oldStyle & typeface.getStyle();
        if ((fake & Typeface.BOLD) != 0) {
            paint.setFakeBoldText(true);
        }

        if ((fake & Typeface.ITALIC) != 0) {
            paint.setTextSkewX(-0.25f);
        }

        paint.setTypeface(typeface);
    }
}