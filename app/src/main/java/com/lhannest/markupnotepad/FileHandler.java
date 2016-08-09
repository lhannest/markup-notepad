package com.lhannest.markupnotepad;

import android.content.Context;
import android.text.Spanned;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileHandler {
    private final Context context;

    public FileHandler(Context context) {
        this.context = context;
    }

    private File getFile(String fileName) {
        return new File(context.getFilesDir(), fileName);
    }

    public String loadFile(String fileName) {
        File file = getFile(fileName);

        if (file.exists()) {
            byte[] bytes = new byte[(int) file.length()];

            FileInputStream inputStream = null;

            try {
                inputStream = new FileInputStream(file);
                inputStream.read(bytes);
                inputStream.close();

                return new String(bytes);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "";
    }

    public void saveFile(String fileName, String text) {
        File file = getFile(fileName);
        FileOutputStream outputStream;

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(text.getBytes());
            outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
