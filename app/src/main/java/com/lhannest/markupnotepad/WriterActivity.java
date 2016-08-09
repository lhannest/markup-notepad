package com.lhannest.markupnotepad;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.widget.EditText;


public class WriterActivity extends ActionBarActivity {
    private String fileName;
    private EditText editText;
    private Formater formater;

    private void setupPrivateFields() {
        fileName = getIntent().getStringExtra("file_name");
        editText = (EditText) findViewById(R.id.editText);
        formater = new Formater(editText.getText());
    }

    private void saveFile() {
        FileHandler fileHandler = new FileHandler(getApplicationContext());
        String text = Html.toHtml(editText.getText());
        fileHandler.saveFile(fileName, text);
    }

    private TextWatcher makeTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                formater.format(editText);
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writer);

        setupPrivateFields();

        FileHandler fileHandler = new FileHandler(getApplicationContext());
        Spanned loadedHtml = Html.fromHtml(fileHandler.loadFile(fileName));

        editText.setText(loadedHtml);
        editText.addTextChangedListener(makeTextWatcher());
    }

    @Override
    public void onPause() {
        saveFile();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        saveFile();
        super.onDestroy();
    }
}