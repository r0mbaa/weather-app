package sample.scene.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Controller {

    @FXML
    private Button StartButton;

    @FXML
    private TextField City;

    @FXML
    private TextField Date;

    @FXML
    private Button NextDayButton;

    @FXML
    private TextField YaWind;

    @FXML
    private TextField YaOnStreet;

    @FXML
    private TextField YaOs;

    @FXML
    private TextField YaPressure;

    @FXML
    private TextField YaTemp;

    @FXML
    private ImageView image;

    @FXML
    public void handleButtonActionStart(ActionEvent event) {
        MainApplication.setCity(City.getText());
        YaWeather.main();
        System.out.println("Кнопка рассчета была нажата!");
        updateWeatherData(); // Вызываем метод для обновления данных
    }

    private void updateWeatherData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> weatherData = objectMapper.readValue(new File("weather_data.json"), Map.class);

            // Извлекаем данные из weatherData
            YaTemp.setText(((Map<String, Object>) weatherData.get("current")).get("temp_c").toString() + "°C");
            YaOs.setText(((Map<String, Object>) weatherData.get("current")).get("feelslike_c").toString() + "°C");
            YaWind.setText(((Map<String, Object>) weatherData.get("current")).get("wind_kph").toString() + "км/ч"); // Пример, уточните ключи в weather_data.json
            YaOnStreet.setText(((Map<String, Object>) ((Map<String, Object>) weatherData.get("current")).get("condition")).get("text").toString());
            YaPressure.setText(((Map<String, Object>) weatherData.get("current")).get("pressure_mb").toString() + " гПа");

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла weather_data.json: " + e.getMessage());
        }
    }

    @FXML
    public void initialize() {
        Date.setText(ConnectionWorldTime.getDateInfoFromJson().getYear()+"."+ConnectionWorldTime.getDateInfoFromJson().getMonth()+"."+ConnectionWorldTime.getDateInfoFromJson().getDay());
    }
}