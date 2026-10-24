import java.util.Map;
import java.util.TreeMap;

public class StatisticsPrinter {

    public void print(Statistics statistics, long timeMs) {
        printDuplicates(statistics.getDuplicates());
        printFloors(statistics.getFloorsByCity());
        System.out.println("\nУникальных записей: " + statistics.getUniqueCount());
        System.out.println("Время обработки файла: " + timeMs + " мс");
    }

    private void printDuplicates(Map<Address, Integer> duplicates) {
        System.out.println("\n--- Дублирующиеся записи ---");
        if (duplicates.isEmpty()) {
            System.out.println("Дубликатов нет.");
            return;
        }
        for (Map.Entry<Address, Integer> entry : duplicates.entrySet()) {
            System.out.println(entry.getKey() + " | повторений: " + entry.getValue());
        }
    }

    private void printFloors(Map<String, int[]> floorsByCity) {
        System.out.println("\n--- Количество зданий по этажности ---");
        System.out.printf("%-25s %7s %7s %7s %7s %7s%n", "Город", "1 эт.", "2 эт.", "3 эт.", "4 эт.", "5 эт.");
        //TreeMap чтобы города шли по алфавиту
        for (Map.Entry<String, int[]> entry : new TreeMap<>(floorsByCity).entrySet()) {
            int[] f = entry.getValue();
            System.out.printf("%-25s %7d %7d %7d %7d %7d%n", entry.getKey(), f[1], f[2], f[3], f[4], f[5]);
        }
    }
}
