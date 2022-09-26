import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class WeatherParser {
    private static String dataFile = "mlb-dbd-2021.csv";

    public static void main(String[] args) throws IOException {
        String path = "/Users/owen/Desktop/" + dataFile;
        BufferedReader csvReader = new BufferedReader(new FileReader(path));

        ArrayList<String> conditions = new ArrayList<>();
        String day = csvReader.readLine();

        int counter[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        for (int i = 0; i < 29735; i++) {
            day = csvReader.readLine();
            String[] data = day.split(",");

            String tp = data[17];
            String condition = data[18];
            String ws = data[19];

            /*
            //Wind
            if (!(ws.equals("NA"))) {
                int windSpeed = Integer.parseInt(ws);
                if (windSpeed >= 25) {
                    j++;
                } else if (windSpeed >= 20) {
                    k++;
                } else if (windSpeed >= 15) {
                    l++;
                }
            }
            */

            //Temp
            if (!(tp.equals("NA"))) {
                int temp = Integer.parseInt(tp);
                if (temp >= 110) {
                    counter[0] += 1;
                } else if (temp >= 100) {
                    counter[2] += 1;
                } else if (temp >= 95) {
                    counter[3] += 1;
                } else if (temp >= 90) {
                    counter[4] += 1;
                } else if (temp >= 85) {
                    counter[5] += 1;
                } else if (temp >= 80) {
                    counter[6] += 1;
                } else if (temp >= 75) {
                    counter[7] += 1;
                } else if (temp >= 70) {
                    counter[8] += 1;
                } else if (temp >= 65) {
                    counter[9] += 1;
                } else if (temp >= 60) {
                    counter[10] += 1;
                } else if (temp >= 55) {
                    counter[11] += 1;
                } else if (temp >= 50) {
                    counter[12] += 1;
                } else if (temp >= 45) {
                    counter[13] += 1;
                } else if (temp >= 40) {
                    counter[14] += 1;
                } else if (temp >= 35) {
                    counter[15] += 1;
                } else if (temp >= 30) {
                    counter[16] += 1;
                } else {
                    counter[17] += 1;
                }
            }
        }

        for (int i = 0; i < counter.length; i++) {
            System.out.println(i + " count " + counter[i]);
        }
    }
}
