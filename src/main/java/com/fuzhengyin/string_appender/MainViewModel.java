package com.fuzhengyin.string_appender;

import java.io.IOException;
import java.io.RandomAccessFile;

public class MainViewModel {
    ObservableData<String> moonDest;
    ObservableData<String> featureId;
    ObservableData<String> key;

    MainViewModel() {
        moonDest = new ObservableData<>();
        featureId = new ObservableData<>();
        key = new ObservableData<>();
    }

    public void loadLast() {

    }
    public String start() {
        String data = key.getData();
        if (data == null || data.isEmpty()) return "";
        String stringKey = trans(data);
        return start(stringKey);
    }
    public String start(String stringKey) {
        String moon = moonDest.getData();
        if (moon == null || moon.isEmpty()) return "";
        String feature = featureId.getData();
        if (feature == null || feature.isEmpty()) return "";
        String data = key.getData();
        if (data == null || data.isEmpty()) return "";
        String format = String.format("<string name=\"%s_%s\">%s</string>", feature, stringKey, data);
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
        return "R.string."+feature+"_"+ stringKey;
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
                    stringBuilder.append((char) (c - ('A'-'a')));
                } else {
                    stringBuilder.append(c);
                }
            }
        }
        return stringBuilder.toString();
    }

}

class ObservableData<T> {
    T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
