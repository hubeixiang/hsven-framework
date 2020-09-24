import java.util.HashMap;
import java.util.Map;

public class TestMain {
    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("a", new Object());
        map.put("b", "b");

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            map.put(entry.getKey(),null);
            System.out.println("00000000000000000");
        }
        System.out.println(map.size());
        map.clear();
        System.out.println(map.size());
    }
}
