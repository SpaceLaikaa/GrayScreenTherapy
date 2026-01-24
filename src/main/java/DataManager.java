import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DataManager{
    private static final String FILE_NAME = "lifetime_stats.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
public static Stats loadStats() {
    try {
        Path path = Paths.get(FILE_NAME);
        if (!Files.exists(path)) {
            return new Stats();
        }
        String json = Files.readString(path);
        return gson.fromJson(json, Stats.class);
    } catch (Exception e) {
        System.err.println("Data Error: " + e.getMessage());
        return new Stats();
    }
}
    public static void saveStats(Stats stats) {
        try {
            String json = gson.toJson(stats);
            Files.writeString(Paths.get(FILE_NAME), json);
        } catch (Exception e) {
            System.err.println("Data Error: " + e.getMessage());
        }
    }
}
