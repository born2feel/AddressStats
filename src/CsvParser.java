import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class CsvParser implements AddressParser {

    //формат строки: "Барнаул";"Дальняя улица";56;2
    @Override
    public void parse(String filePath, Statistics statistics) throws Exception {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {

            reader.readLine(); //первая строка - заголовок, пропускаем
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split(";");
                if (parts.length != 4) {
                    throw new Exception("Неверная строка в файле: " + line);
                }
                String city = parts[0].replace("\"", "");
                String street = parts[1].replace("\"", "");
                int house = Integer.parseInt(parts[2]);
                int floor = Integer.parseInt(parts[3]);
                statistics.add(new Address(city, street, house, floor));
            }
        }
    }
}
