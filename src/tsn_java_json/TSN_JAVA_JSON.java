package tsn_java_json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class TSN_JAVA_JSON {

    public static void main(String[] args) {
        String url = "https://api.coindesk.com/v1/bpi/currentprice.json"; // Адрес получения JSON - данных
        String json = getHTMLData(url);
        StringBuilder txt = new StringBuilder("Курс BTC:\n");
        if (json != null) {
            JSONObject _root = null;
            try {
                _root = new JSONObject(json);
                JSONObject bpi = _root.getJSONObject("bpi");
                JSONObject USD = bpi.getJSONObject("USD");
                String rate = USD.getString("rate");
                String code = USD.getString("code");
                JSONObject EUR = bpi.getJSONObject("EUR");
                String rate1 = EUR.getString("rate");
                String code1 = EUR.getString("code");
                txt.append("1 BTC = " + rate + " " + code);
                txt.append("\n");
                txt.append("1 BTC = " + rate1 + " " + code1);
                System.out.println(txt.toString());
            } catch (Exception e) {
                System.out.println("Error!");
            }
        }
    }

    // Метод чтения данных с сети по протоколу HTTP
    public static String getHTMLData(String url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            int response = connection.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                StringBuilder data = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        data.append(line);
                    }
                } catch (IOException e) {
                }
                return data.toString();
            } else {
                return null;
            }
        } catch (IOException ignored) {
        } finally {
            connection.disconnect();
        }
        return null;
    }

}
