import java.io.File;
import java.util.Scanner;

public class ConsoleApp {
    private Scanner scanner;
    private StatisticsPrinter printer;

    public ConsoleApp() {
        this.scanner = new Scanner(System.in);
        this.printer = new StatisticsPrinter();
    }

    public void start() {
        System.out.println("--- Статистика по справочнику адресов ---");

        while (true) {
            System.out.print("\nВведите путь к файлу (.csv или .xml) или exit для выхода: ");
            if (!scanner.hasNextLine()) {
                break; //ctrl+d / ctrl+z
            }
            String path = scanner.nextLine().trim().replace("\"", "");

            if (path.equalsIgnoreCase("exit")) {
                break;
            }
            if (path.isEmpty()) {
                System.out.println("Ошибка: путь не может быть пустым!");
                continue;
            }
            processFile(path);
        }
        System.out.println("Завершение работы программы.");
    }

    private void processFile(String path) {
        if (!new File(path).isFile()) {
            System.out.println("Ошибка: файл не найден!");
            return;
        }

        AddressParser parser = createParser(path);
        if (parser == null) {
            System.out.println("Ошибка: поддерживаются только файлы .csv и .xml");
            return;
        }

        System.out.println("Обработка файла...");
        Statistics statistics = new Statistics();
        long start = System.currentTimeMillis();
        try {
            parser.parse(path, statistics);
        } catch (Exception e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
            return;
        }
        long time = System.currentTimeMillis() - start;

        printer.print(statistics, time);
    }

    //выбираем парсер по расширению файла
    private AddressParser createParser(String path) {
        String lower = path.toLowerCase();
        if (lower.endsWith(".csv")) {
            return new CsvParser();
        } else if (lower.endsWith(".xml")) {
            return new XmlParser();
        }
        return null;
    }
}
