package org.example.testUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

public class BrowserStackAPI {

    public static String getLatestBuildId() {
        String buildId = null;
        try {
            String username = "akshayreddy_EiAhls";
            String accessKey = "qMcCkyzMpLkP5uVDEd8A";
            String apiUrl = "https://api-cloud.browserstack.com/app-automate/builds.json";

            // Encode credentials
            String auth = username + ":" + accessKey;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

            // Setup connection
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Basic " + encodedAuth);

            // Handle response
            int responseCode = connection.getResponseCode();
            System.out.println(">>> Response Code: " + responseCode);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse and extract buildId
            JSONArray buildsArray = new JSONArray(response.toString());
            if (buildsArray.length() > 0) {
                JSONObject firstBuild = buildsArray.getJSONObject(0).getJSONObject("automation_build");
                buildId = firstBuild.getString("hashed_id");

            } else {
                System.out.println("No builds found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return buildId;
    }
}
