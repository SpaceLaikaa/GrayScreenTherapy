import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    static TherapyGUI gui;

    static boolean isYouTubeOpen = false;
    static double totalDeadTime = 0;
    public static void focusLeagueOfLegends() {
        String windowName = "League of Legends (TM) Client";

        HWND hwnd = User32.INSTANCE.FindWindow(null, windowName);

        if (hwnd != null) {
            User32.INSTANCE.ShowWindow(hwnd, 9); // 9 = SW_RESTORE
            User32.INSTANCE.SetForegroundWindow(hwnd);
            System.out.println("🎯 LoL client!");
        } else {
            System.out.println("⚠️ LoL client not found. Make sure the game is running.");
        }
    }

    private static void closeAllPossibleBrowsers(){
        String[] browsers = {"msedge.exe", "chrome.exe", "opera.exe", "brave.exe", "firefox.exe"};
        for (String browser : browsers){
            try{
                Runtime.getRuntime().exec("taskkill /F /IM " + browser + " /T");
            } catch (Exception ignored){}
        }
    }

    public static void aliveLogic(double currentHealth){
        if (isYouTubeOpen) {
            System.out.println("✅ ALIVE -> Welcome back!");

            try{
                Robot robot = new Robot();
                robot.keyPress(java.awt.event.KeyEvent.VK_CONTROL);
                robot.keyPress(java.awt.event.KeyEvent.VK_W);
                robot.keyRelease(java.awt.event.KeyEvent.VK_W);
                robot.keyRelease(java.awt.event.KeyEvent.VK_CONTROL);

                Thread.sleep(100);
                focusLeagueOfLegends();

                isYouTubeOpen = false;

            } catch (Exception e){
                System.err.println("Couldn't close the browser: " + e.getMessage());
                closeAllPossibleBrowsers();//Will try popular browsers to close it.
            }
        }
        System.out.println("ALIVE (Health: " + currentHealth + ")");
    }

    public static void deathLogic(double respawnTimer){
        totalDeadTime++;
        if (!isYouTubeOpen && respawnTimer > 10) {
            System.out.println("Starting Therapy Session...");
            String genericShortsUrl = "https://www.youtube.com/shorts";

            try {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "start", genericShortsUrl);
                pb.start();
                isYouTubeOpen = true;
                Thread.sleep(1000);

            } catch (Exception e) {
                System.err.println("Error opening browser: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        gui = new TherapyGUI();
        gui.update(totalDeadTime, "Alive in The Rift");

        System.out.println("---GrayScreenTherapy Starting---");
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
                if(!root.has("activePlayer") || root.get("activePlayer").isJsonNull()){
                    System.out.println("⚠️Waiting for data (ActivePlayer)...");
                    Thread.sleep(1000);
                    continue;
                }
                JsonObject activePlayer = root.getAsJsonObject("activePlayer");

                if (!activePlayer.has("championStats")) {
                    System.out.println("⚠️Waiting for data (Stats)...");
                    Thread.sleep(1000);
                    continue;
                }

                JsonObject championStats = activePlayer.getAsJsonObject("championStats");
                double currentHealth = championStats.get("currentHealth").getAsDouble();

                double respawnTimer = 0;
                if (root.has("allPlayers")) {
                    JsonArray allPlayers = root.getAsJsonArray("allPlayers");
                    String myName = activePlayer.get("summonerName").getAsString();

                    for (JsonElement playerElement : allPlayers) {
                        JsonObject playerObj = playerElement.getAsJsonObject();
                        if (playerObj.get("summonerName").getAsString().equals(myName)) {
                            respawnTimer = playerObj.get("respawnTimer").getAsDouble();
                            break;
                        }
                    }
                }

                if (currentHealth <= 0) {deathLogic(respawnTimer);}
                else {aliveLogic(currentHealth);}

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