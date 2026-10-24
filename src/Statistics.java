import java.util.HashMap;
import java.util.Map;

public class Statistics {
    //адрес -> сколько раз встретился в файле
    private Map<Address, Integer> counts;
    //город -> массив, где floors[i] = количество i-этажных зданий (индексы 1..5)
    private Map<String, int[]> floorsByCity;

    public Statistics() {
        this.counts = new HashMap<>();
        this.floorsByCity = new HashMap<>();
    }

    public void add(Address address) {
        int count = counts.getOrDefault(address, 0) + 1;
        counts.put(address, count);

        //этажность считаем только для первого появления здания, чтобы дубликаты не считались дважды
        if (count == 1) {
            int floor = address.getFloor();
            if (floor >= 1 && floor <= 5) {
                if (!floorsByCity.containsKey(address.getCity())) {
                    floorsByCity.put(address.getCity(), new int[6]);
                }
                floorsByCity.get(address.getCity())[floor]++;
            }
        }
    }

    //возвращает только те адреса, которые встретились больше одного раза
    public Map<Address, Integer> getDuplicates() {
        Map<Address, Integer> duplicates = new HashMap<>();
        for (Map.Entry<Address, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > 1) {
                duplicates.put(entry.getKey(), entry.getValue());
            }
        }
        return duplicates;
    }

    public Map<String, int[]> getFloorsByCity() {
        return floorsByCity;
    }

    public int getUniqueCount() {
        return counts.size();
    }
}
