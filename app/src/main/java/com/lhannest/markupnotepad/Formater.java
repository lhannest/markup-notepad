package com.lhannest.markupnotepad;

import android.text.Editable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.widget.EditText;

public class Formater {
    private FormatHandler handler;

    private boolean ignoreTextChange;
    private int lastLength;

    public Formater(Editable text) {
        this.lastLength = text.length();
        ignoreTextChange = false;
    }

    public void format(EditText editText) {
        if (!ignoreTextChange) {
            onTextChange(editText);
        }
    }

    private void onTextChange(EditText editText) {
        int deltaLength = editText.length() - lastLength;
        int cursorLocation = editText.getSelectionEnd();

        if (deltaLength > 0) {
            int start = cursorLocation - deltaLength;
            int end = cursorLocation;
            String newText = "" + editText.getText().subSequence(start, end);

            handler.clear(start, end);
            handler.bump(end, deltaLength);

            ignoreTextChange = true;
            handleFormatting(editText.getText(), end - 1);
            ignoreTextChange = false;
        }


        lastLength = editText.length();
    }


    private void handleFormatting(Editable text, int cursorLocation) {
        String charJustTyped = "" + text.toString().charAt(cursorLocation);


        for (FormatType type : FormatType.values()) {
            if (charJustTyped.equals(FormatHandler.getSymbol(type))) {
                if (handler.contains(type)) {
                    int start = Math.min(handler.getLocation(type), cursorLocation);
                    int end = Math.max(handler.getLocation(type), cursorLocation);

                    Editable subEditable = new SpannableStringBuilder(text);
                    subEditable.delete(0, start + 1);
                    subEditable.delete(end - start - 1, subEditable.length());

                    String subHtml = Html.toHtml(subEditable);
                    subHtml = subHtml.replace("<p dir=\"ltr\">", "");
                    subHtml = subHtml.replace("</p>", "");
                    subHtml = subHtml.replace("\n", "");
                    subHtml = FormatHandler.openingTag(type) + subHtml +
                            FormatHandler.closingTag(type);

                    ignoreTextChange = true;
                    text.replace(start, end + 1, Html.fromHtml(subHtml));
                    ignoreTextChange = false;

                    handler.remove(type);

                } else {
                    handler.setLocation(type, cursorLocation);
                }
            }
        }
    }
}
