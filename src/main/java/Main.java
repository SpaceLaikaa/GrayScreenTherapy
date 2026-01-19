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
import com.sun.jna.Native;
//------------------------------------------------
public class Main {
    static TherapyGUI gui;
    static boolean isYouTubeOpen = false;
    static double totalDeadTime = 0;

    public static void focusTherapyTab() {//After respawning focus on youtube shorts to close it
        User32.INSTANCE.EnumWindows((hwnd, pointer) -> {
            char[] windowText = new char[512];
            User32.INSTANCE.GetWindowText(hwnd, windowText, 512);
            String title = Native.toString(windowText);

            if (title.contains("- YouTube")|| title.contains("Instagram") || title.contains("TikTok")) { //Probably will change this condition
                User32.INSTANCE.ShowWindow(hwnd, 3);//fullscreen
                User32.INSTANCE.SetForegroundWindow(hwnd);
                System.out.println("🎯 Therapy tab found and focused!");// console feedbacks
                return false;
            }
            return true;
        }, null);
    }

    public static void focusLeagueOfLegends() {//Literally focuses LoL
        String windowName = "League of Legends (TM) Client";

        HWND hwnd = User32.INSTANCE.FindWindow(null, windowName);

        if (hwnd != null) {
            User32.INSTANCE.ShowWindow(hwnd, 9); // 9 = SW_RESTORE
            User32.INSTANCE.SetForegroundWindow(hwnd);
            System.out.println("🎯 LoL client!");// console feedbacks
        } else {
            System.out.println("⚠️ LoL client not found. Make sure the game is running.");// console feedbacks
        }
    }

    private static void closeAllPossibleBrowsers(){//This method tries to find the users browser to access
        String[] browsers = {"msedge.exe", "chrome.exe", "opera.exe", "brave.exe", "firefox.exe"};
        for (String browser : browsers){
            try{
                Runtime.getRuntime().exec("taskkill /F /IM " + browser + " /T");//close browser
            } catch (Exception ignored){}
        }
    }

    public static void aliveLogic(double currentHealth){
        if (isYouTubeOpen) {
            System.out.println("✅ ALIVE -> Welcome back!");

            try{
                focusTherapyTab();
                Thread.sleep(250);


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
        gui.update(totalDeadTime, "Alive...", false);
        System.out.println("ALIVE (Health: " + currentHealth + ")");
    }

    public static void deathLogic(double respawnTimer) {
        totalDeadTime++;

        if (gui.isTherapyEnabled() && !isYouTubeOpen && respawnTimer > 10) {
            System.out.println("Starting Therapy Session...");
            String genericShortsUrl = gui.getSelectedPlatformUrl();

            try {
                gui.moveToCorner();
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "start", genericShortsUrl);
                pb.start();
                isYouTubeOpen = true;

                Thread.sleep(1500);
                focusTherapyTab();
            } catch (Exception e) {
                System.err.println("Error opening browser: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        gui = new TherapyGUI();

        System.out.println("---GrayScreenTherapy Starting---");//Does not affect GUI only for console
        System.out.println("Trying to find The LoL Client...");// ``

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

                if (currentHealth <= 0) {
                    deathLogic(respawnTimer);
                    boolean shouldForceFront = (respawnTimer>10);

                    gui.update(totalDeadTime, "Therapy Session has started...", shouldForceFront);
                }
                else {aliveLogic(currentHealth); gui.update(totalDeadTime, "Alive...", false);}

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