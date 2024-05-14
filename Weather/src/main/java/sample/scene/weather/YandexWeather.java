package sample.scene.weather;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class YandexWeather {
    //"time":"2024-05-11T00:00
    //https://yandex.ru/_next/data/weather-front2-420/pogoda/ru-RU/details/watersports/today.json?lat=56.129057&lon=40.406635&lang=ru&via=prsw&serviceNameRoute=pogoda&localeRoute=ru-RU&daysRoute=watersports&daysRoute=today


    public class Main {
        public static void main(String[] args) {
            try {
                URL url = new URL("https://yandex.ru/_next/data/weather-front2-420/pogoda/ru-RU/details/watersports/today.json?lat=56.129057&lon=40.406635&lang=ru&via=prsw&serviceNameRoute=pogoda&localeRoute=ru-RU&daysRoute=watersports&daysRoute=today");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Scanner scanner = new Scanner(url.openStream());
                    StringBuilder response = new StringBuilder();
                    while (scanner.hasNext()) {
                        response.append(scanner.nextLine());
                    }
                    scanner.close();

                    String jsonResponse = response.toString();
                    System.out.println(jsonResponse);
                } else {
                    System.out.println("Ошибка при получении данных: " + responseCode);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
