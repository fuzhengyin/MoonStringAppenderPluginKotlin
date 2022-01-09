package com.fuzhengyin.string_appender;

import java.io.IOException;
import java.io.RandomAccessFile;

public class MainViewModel {
    ObservableData<String> moonDest;
    ObservableData<String> prefix;
    ObservableData<String> suffix;
    ObservableData<String> key;

    MainViewModel() {
        moonDest = new ObservableData<>();
        prefix = new ObservableData<>();
        key = new ObservableData<>();
        suffix = new ObservableData<>();
    }

    public void loadLast() {

    }

    public String start() {
        String data = key.getValue();
        if (data == null || data.isEmpty()) return "";
        String stringKey = trans(data);
        return start(stringKey);
    }

    public String start(String stringKey) {
        String moon = moonDest.getValue();
        if (moon == null || moon.isEmpty()) return "";
        String prefix = this.prefix.getValue();
        if (prefix == null || prefix.isEmpty()) return "";
        String suffix = this.suffix.getValue();
        if (suffix != null && !suffix.trim().isEmpty()) {
            suffix = "_" + suffix;
        } else {
            suffix = "";
        }
        String data = key.getValue();
        if (data == null || data.isEmpty()) return "";
        if (data.contains("\n")) data = "\"" + data + "\"";
        String format = String.format("<string name=\"%s_%s%s\">%s</string>", prefix, stringKey, suffix, data);
        System.out.println(format);
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(moon, "rw");
            randomAccessFile.seek(randomAccessFile.length() - 12);
            String s = "\t" + format + "\n</resources>";
            //todo code improvement
            randomAccessFile.writeBytes(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "R.string." + prefix + "_" + stringKey;
    }

    public String trans(String origin) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < origin.length(); i++) {
            char c = origin.charAt(i);
            if (c == ' ') {
                stringBuilder.append("_");
            } else {
                if (c >= 33 && c <= 47 || c >= 58 && c <= 64 || c >= 91 && c <= 96 || c >= 121 && c <= 126) {
                    continue;
                }
                if (c >= 'A' && c <= 'Z') {
                    stringBuilder.append((char) (c - ('A' - 'a')));
                } else {
                    stringBuilder.append(c);
                }
            }
        }
        return stringBuilder.toString();
    }

}

class ObservableData<T> {
    T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
