package com.ar.vgmsistemas.utils;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.widget.TextView;

import java.util.Locale;

public class TextViewUtils {
    public static void highlightText(TextView textView, String originalString, String filterString) {
        int startPos = originalString.toLowerCase(Locale.US).indexOf(filterString.toLowerCase(Locale.US));
        int endPos = startPos + filterString.length();

        if (startPos != -1 && filterString.length() > 0) // This should always be true, just a sanity check
        {
            Spannable spannable = new SpannableString(originalString);

            ColorStateList color = new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.rgb(51, 181, 229)});
            TextAppearanceSpan highlightSpan = new TextAppearanceSpan(null, Typeface.BOLD, -1, color, null);

            spannable.setSpan(highlightSpan, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(spannable);
        } else {
            textView.setText(originalString);
        }
    }

}
