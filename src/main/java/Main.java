import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {

    public static void main(String[] args) {
        boolean isYouTubeOpen = false;
        System.out.println("---GrayScreenTherapy Starting");
        System.out.println("Trying to find The LoL Client...");

        HttpClient client = RiotGetAPI.createUnsafeClient();

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
                    if (!isYouTubeOpen) {
                        System.out.println("Starting Therapy Session...");
                        String genericShortsUrl = "https://www.youtube.com/shorts";

                        try {
                            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "start", genericShortsUrl);// To go directly to yt shorts
                            pb.start();
                            isYouTubeOpen = true;
                            Thread.sleep(1000);

                        } catch (Exception e) {
                            System.err.println("Error opening browser: " + e.getMessage());
                        }
                    }
                } else {
                    if (isYouTubeOpen) {
                        System.out.println("✅ ALIVE -> Welcome back!");
                        isYouTubeOpen = false;
                    }
                    System.out.println("ALIVE (Health: " + currentHealth + ")");
                }

                Thread.sleep(1000);
            } catch (Exception e){
                System.out.println("❌ Could not connect to LoL. Is the game running? ❌");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}