package sample.scene.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
public class ConnectionWorldTime {
    public static class DateInfo {
        private String year;
        private String month;
        private String day;

        public DateInfo(String year, String month, String day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        public String getYear() {
            return year;
        }

        public String getMonth() {
            return month;
        }

        public String getDay() {
            return day;
        }
    }

    public static DateInfo getDateInfoFromJson() {
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;

        try {
            URL url = new URL("http://worldtimeapi.org/api/timezone/Europe/Moscow");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();

                File file = new File("time.json");

                fileOutputStream = new FileOutputStream(file);

                int byteRead = -1;
                byte[] buffer = new byte[1024];

                while ((byteRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, byteRead);
                }

                ObjectMapper objectMapper = new ObjectMapper();

                try {

                    JsonNode rootNode = objectMapper.readTree(new File("time.json"));

                    String utcDatetime = rootNode.get("datetime").asText();

                    String year = utcDatetime.substring(0, 4);
                    String month = utcDatetime.substring(5, 7);
                    String day = utcDatetime.substring(8, 10);

                    return new DateInfo(year, month, day);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.printf(Integer.toString(responseCode));
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return null;
    }


}
