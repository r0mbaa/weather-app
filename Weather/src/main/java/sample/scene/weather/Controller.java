package sample.scene.weather;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private Button BeforeDayButton;

    @FXML
    private TextField City;

    @FXML
    private TextField Date;

    @FXML
    private TextField GoMax;

    @FXML
    private TextField GoMin;

    @FXML
    private TextField GoOs;

    @FXML
    private TextField GoPressure;

    @FXML
    private Button NextDayButton;

    @FXML
    private TextField YaMax;

    @FXML
    private TextField YaMin;

    @FXML
    private TextField YaOs;

    @FXML
    private TextField YaPressure;

    @FXML
    private TextField YaTemp;

    @FXML
    private TextField gisMax;

    @FXML
    private TextField gisMin;

    @FXML
    private TextField gisOs;

    @FXML
    private TextField gisPressure;

    @FXML
    private TextField gisTemp;

    @FXML
    public void handleButtonActionOnNextDay(ActionEvent event) {
        System.out.println("Кнопка вперед была нажата!");
    }

    @FXML
    public void handleButtonActionOnDayBefore(ActionEvent event) {
        HelloApplication.setCity(City.getText());
        YandexWeather.Main.main(null);
        System.out.println("Кнопка назад была нажата!");
    }

    @FXML
    public void initialize() {

        Date.setText(ConnectionWorldTime.getDateInfoFromJson().getYear()+"."+ConnectionWorldTime.getDateInfoFromJson().getMonth()+"."+ConnectionWorldTime.getDateInfoFromJson().getDay());
    }

}
