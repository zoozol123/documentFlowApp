package org.example.Logic;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
// tłumaczenie z: https://whatsmate.github.io/2016-08-18-translate-text-java/

public class NameTranslationProductDecorator extends ProductDecorator {
    String name;
    public NameTranslationProductDecorator(IProduct product, String name) throws IOException {
        super(product);

        String fromLang = "pl";
        String toLang = "en";
        this.name = translate(fromLang, toLang, name);
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String translate(String fromLang, String toLang, String text) throws IOException {
        final String CLIENT_ID = "filiplazew@wp.pl";
        final String CLIENT_SECRET = "5c70d853b557480ea470b2af98f73584";
        final String ENDPOINT = "http://api.whatsmate.net/v1/translation/translate";

        // TODO: Should have used a 3rd party library to make a JSON string from an object
        String jsonPayload = new StringBuilder()
                .append("{")
                .append("\"fromLang\":\"")
                .append(fromLang)
                .append("\",")
                .append("\"toLang\":\"")
                .append(toLang)
                .append("\",")
                .append("\"text\":\"")
                .append(text)
                .append("\"")
                .append("}")
                .toString();

        URL url = new URL(ENDPOINT);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("X-WM-CLIENT-ID", CLIENT_ID);
        conn.setRequestProperty("X-WM-CLIENT-SECRET", CLIENT_SECRET);
        conn.setRequestProperty("Content-Type", "application/json");

        OutputStream os = conn.getOutputStream();
        os.write(jsonPayload.getBytes());
        os.flush();
        os.close();

        int statusCode = conn.getResponseCode();
        System.out.println("Status Code: " + statusCode);
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream()
        ));
        String output;
        while ((output = br.readLine()) != null) {
            System.out.println(output);
            text = output;
        }
        conn.disconnect();

        return text;
    }
}
