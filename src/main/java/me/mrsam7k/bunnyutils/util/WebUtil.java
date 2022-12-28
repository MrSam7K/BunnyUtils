package me.mrsam7k.bunnyutils.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class WebUtil {

    public static String getString(String urlToRead, Charset charset) throws IOException {
        URL url = new URL(urlToRead);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), charset));
        StringBuilder builder = new StringBuilder();
        String line;

        while ((line = in.readLine()) != null) {
            builder.append("\n").append(line);
        }
        in.close();
        return builder.toString();
    }

    public static String getString(String urlToRead) throws IOException {
        //System.out.println(urlToRead);
        return getString(urlToRead, StandardCharsets.UTF_8);
    }

    public static JsonElement getJson(String url) throws IOException {
        return JsonParser.parseString(getString(url));
    }

    public static JsonObject getObject(String url) {
        try {
            String jsonObject = WebUtil.getString(url);
            return JsonParser.parseString(jsonObject).getAsJsonObject();
        } catch (JsonSyntaxException | IOException ignored) {
        }

        return null;
    }

}
