package sample.scene.weather;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonSetter;
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
        String path = "http://worldtimeapi.org/api/timezone/Europe/Moscow";
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;

        try {
            String query = "abbreviation=MSK&client_ip=2a00:62c0:4a22:9201:1915:5b40:61a6:51f5&datetime=2024-04-26T00:12:26.226611+03:00";
            URL url = new URL(path + "?" + query);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();

                File file = new File("test.json");

                fileOutputStream = new FileOutputStream(file);

                int byteRead = -1;
                byte[] buffer = new byte[1024];

                while ((byteRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, byteRead);
                }

                ObjectMapper objectMapper = new ObjectMapper();

                try {

                    JsonNode rootNode = objectMapper.readTree(new File("test.json"));

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
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }


}
