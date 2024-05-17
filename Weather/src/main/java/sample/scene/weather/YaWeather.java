package sample.scene.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class YaWeather {
    public static void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private static String apikey = "977b5c3e1d2c43c281b193110241405"; // Если не работает - меняйте на свой
    public static void main() {
        try {
            String httpURL = "http://api.weatherapi.com/v1/current.json?key=" + apikey + "&q=" + MainApplication.getCity() + "&aqi=no";
            URL url = new URL(httpURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                ObjectMapper objectMapper = new ObjectMapper();

                //  джесон в Map
                Map<String, Object> weatherData = objectMapper.readValue(connection.getInputStream(), Map.class);

                String jsonOutput = objectMapper.writeValueAsString(weatherData);
                File file = new File("weather_data.json");
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(jsonOutput.getBytes());
                outputStream.close();

            }
            else if(responseCode == HttpsURLConnection.HTTP_BAD_REQUEST)
            {
                showErrorAlert("An error has occurred!", "Your city was not found!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
