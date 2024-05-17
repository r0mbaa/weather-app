package sample.scene.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Controller {

    @FXML
    private TextField City;

    @FXML
    private TextField Date;

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
        MainApplication.setCity(City.getText()); // сморим какой город написал пользователь
        YaWeather.main(); // тырит джсон у какого то погодника
        updateWeatherData(); // метод для обновления данных
    }

    private void updateWeatherData() {
        try {
            File file = new File("weather_data.json"); // открыл папку которую стырил япогода
            ObjectMapper objectMapper = new ObjectMapper(); // че то из библиотеки джексона
            Map<String, Object> weatherData = objectMapper.readValue(file, Map.class); // преобразовал через мап чтоб не делать класс и его экземпляр

            // данные из weatherData
            YaTemp.setText(((Map<String, Object>) weatherData.get("current")).get("temp_c").toString() + " °C");
            YaOs.setText(((Map<String, Object>) weatherData.get("current")).get("feelslike_c").toString() + " °C");
            YaWind.setText(((Map<String, Object>) weatherData.get("current")).get("wind_kph").toString() + " km/h");
            YaOnStreet.setText(((Map<String, Object>) ((Map<String, Object>) weatherData.get("current")).get("condition")).get("text").toString());
            YaPressure.setText(((Map<String, Object>) weatherData.get("current")).get("pressure_mb").toString() + " hPa");

            String iconUrl ="http:" + ((Map<String, Object>) ((Map<String, Object>) weatherData.get("current")).get("condition")).get("icon").toString(); // иконка сверху

            image.setImage(new Image(iconUrl));

            file.delete(); // удаляем джсон чтобы не оставлять улик

        } catch (IOException e) {
            // если города нет то чистим гамно из полей
            YaTemp.setText("");
            YaOs.setText("");
            YaWind.setText("");
            YaOnStreet.setText("");
            YaPressure.setText("");
            image.setImage(null);
        }
    }

    @FXML
    public void initialize() {
        // ставим дату ( она кста по мск там utc+3)
        Date.setText(ConnectionWorldTime.getDateInfoFromJson().getYear()+"."+ConnectionWorldTime.getDateInfoFromJson().getMonth()+"."+ConnectionWorldTime.getDateInfoFromJson().getDay());
        File file = new File("time.json"); // ищим джсон со временем
        file.delete(); // удаляем джсон чтобы не оставлять улик
    }
}