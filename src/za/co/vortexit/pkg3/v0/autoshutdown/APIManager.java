package za.co.vortexit.pkg3.v0.autoshutdown;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import kong.unirest.*;

/**
 *
 * @author Connor Lewis
 */
public class APIManager {

    private final String BASE_URL = "https://developer.sepush.co.za/business/2.0/";
    private String TOKEN;
    private String areaName;
    private String areaID;

    public APIManager() {
        this.TOKEN = getTokenFromSettings();
        this.areaName = getAreaName();
        this.areaID = getAreaID();
    }

    public void setToken(String TOKEN) {
        this.TOKEN = TOKEN;
    }

    public String getToken() {
        return TOKEN;
    }

    public int getStatus() {
        HttpResponse<String> response = Unirest.get("https://developer.sepush.co.za/business/2.0/area?id=jhbcitypower3-7-bruma&test=current")
                .header("Token", TOKEN)
                .asString();
        return response.getStatus();
    }

    public String getAreaName(String search) throws UnsupportedEncodingException {

        search = URLEncoder.encode(search, StandardCharsets.UTF_8.toString());

        HttpResponse<String> responseString = Unirest.get(BASE_URL + "areas_search?text=" + search)
                .header("Token", TOKEN)
                .asString();

        if (responseString.getStatus() == 200) {

            System.out.println(responseString.getBody());

            JSONManager jsonMngr = new JSONManager(responseString.getBody());

            String areaName = jsonMngr.getJsonArray("areas").get(0).get("name").asText();

            return areaName;

        } else {
            return "" + responseString.getStatus();
        }

    }

    public String getAreaID(String search) throws UnsupportedEncodingException {

        search = URLEncoder.encode(search, StandardCharsets.UTF_8.toString());

        HttpResponse<String> responseString = Unirest.get(BASE_URL + "areas_search?text=" + search)
                .header("Token", TOKEN)
                .asString();

        JSONManager jsonMngr = new JSONManager(responseString.getBody());

        String areaID = jsonMngr.getJsonArray("areas").get(0).get("id").asText();

        return areaID;

    }

    private String getTokenFromSettings() {
        JSONManager settingsManager = new JSONManager(new File("settings.json"));

        TOKEN = settingsManager.getString("token") == null ? null : settingsManager.getString("token");

        return TOKEN;
    }

    private String getAreaID() {
        JSONManager settingsManager = new JSONManager(new File("settings.json"));

        areaID = settingsManager.getString("areaID") == null ? null : settingsManager.getString("areaID");

        return areaID;
    }

    private String getAreaName() {
        JSONManager settingsManager = new JSONManager(new File("settings.json"));

        areaName = settingsManager.getString("areaName") == null ? null : settingsManager.getString("areaName");

        return areaName;
    }

}
