package sample.scene.weather;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

public class YandexWeather {
    private static String apikey = "977b5c3e1d2c43c281b193110241405";
    private static String httpURL = "http://api.weatherapi.com/v1/current.json?key=" + apikey + "&q=" + HelloApplication.getCity() + "&aqi=yes";

    public class Main {
        public static void main(String[] args) {
            try {
                URL url = new URL(httpURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    // Преобразование JSON в Map
                    Map<String, Object> weatherData = objectMapper.readValue(connection.getInputStream(), Map.class);

                    // Вывод полученных данных
                    System.out.println("Location: " + ((Map<String, Object>) weatherData.get("location")).get("name"));
                    System.out.println("Current temperature: " + ((Map<String, Object>) weatherData.get("current")).get("temp_c") + " Celsius");
                    System.out.println("Condition: " + ((Map<String, Object>) ((Map<String, Object>) weatherData.get("current")).get("condition")).get("text"));

                } else {
                    System.out.printf(Integer.toString(responseCode));
                }
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (ProtocolException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
