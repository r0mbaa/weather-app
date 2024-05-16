package sample.scene.weather;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

public class YaWeather {
    private static String apikey = "977b5c3e1d2c43c281b193110241405";

    public static void main() {
        try {
            String httpURL = "http://api.weatherapi.com/v1/current.json?key=" + apikey + "&q=" + MainApplication.getCity() + "&aqi=yes";
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
                System.out.println((weatherData.get("current")));

                String jsonOutput = objectMapper.writeValueAsString(weatherData);
                File file = new File("weather_data.json"); // Имя файла
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(jsonOutput.getBytes());
                outputStream.close();
                System.out.println(" JSON-файл сохранен ");

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
