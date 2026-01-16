import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RiotGetAPI {
    public static void main(String[] args) {
        System.out.println("---GrayScreenTherapy Starting");
        System.out.println("Trying to find The LoL Client...");

        HttpClient client = RiotGetAPI.createUnsafeClient(); //will be added

        while(true){
            try{
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://127.0.0.1:2999/liveclientdata/allgamedata"))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                String responseBody = response.body();

                JsonObject root = JsonParser.parseString(responseBody).getAsJsonObject();
                JsonObject activePlayer = root.getAsJsonObject("activePlayer");
                JsonObject championStats = activePlayer.getAsJsonObject("championStats");
                double currentHealth = championStats.get("currentHealth").getAsDouble();

                if (currentHealth <= 0) {
                    System.out.println("DEAD -> Opening YouTube Shorts...");
                    // Browser automation code will be added here
                } else {
                    System.out.println("ALIVE (Health: " + currentHealth + ")");
                }

                Thread.sleep(1000);
            }catch (Exception e){
                System.out.println("❌ Could not connect to LoL. Is the game running? ❌");
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
            }
        }
    }
}