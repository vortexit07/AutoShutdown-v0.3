package za.co.vortexit.pkg3.v0.autoshutdown;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.http.HttpTimeoutException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

/**
 * Manages API interactions with the SePush service for retrieving schedule
 * information and area details.
 * <p>
 * This class handles authentication, area lookup, and fetching schedules for
 * load shedding stages.
 * </p>
 * 
 * @author Connor Lewis
 */
public class APIManager {

    private final String BASE_URL = "https://developer.sepush.co.za/business/2.0/";
    private String TOKEN;
    private String areaName;
    private String areaID;

    /**
     * Constructs an APIManager and initializes the token and area details from
     * settings.
     */
    public APIManager() {
        this.TOKEN = getTokenFromSettings();
        this.areaName = getAreaName();
        this.areaID = getAreaID();
    }

    /**
     * Sets the API token.
     * 
     * @param TOKEN the API token
     */
    public void setToken(String TOKEN) {
        this.TOKEN = TOKEN;
    }

    /**
     * Gets the API token from the settings file.
     * 
     * @return the API token
     */
    public String getToken() {
        this.TOKEN = getTokenFromSettings();
        return TOKEN;
    }

    /**
     * Gets the HTTP status code of a test request to the API.
     * 
     * @return HTTP status code
     */
    public int getStatus() throws HttpTimeoutException {
        HttpResponse<String> response = Unirest.get(
                "https://developer.sepush.co.za/business/2.0/area?id=eskde-6-onrusoverstrandwesterncape&test=current")
                .header("Token", TOKEN)
                .asString();
        return response.getStatus();
    }

    /**
     * Retrieves the schedule information for the configured area.
     * 
     * @return JsonNode containing the schedule information, or an error code
     * 
     * @throws JsonMappingException    if there is an error mapping the JSON
     *                                 response
     * @throws JsonProcessingException if there is an error processing the JSON
     *                                 response
     */
    public JsonNode getSchedule() throws JsonMappingException, JsonProcessingException, HttpTimeoutException {
        if (this.areaID != null && this.areaID.length() > 0 && !this.areaID.isBlank()) {

            String url = "https://developer.sepush.co.za/business/2.0/area?id=" + this.areaID;

            // TODO: Uncomment the line below for testing purposes
            // url += "&test=current";

            HttpResponse<String> response = Unirest.get(
                    url)
                    .header("Token", TOKEN)
                    .asString();

            if (response.getStatus() == 200) {
                JSONManager responseJSON = new JSONManager(response.getBody());

                JsonNode events = responseJSON.getJsonArray("events");

                if (!events.isEmpty() && events.get(0).has("start")) {
                    String startTime, endTime, time1 = "", time2 = "", time3 = "", time4 = "";
                    boolean atMidnight = false;
                    int stageArrayIndex;
                    LocalDate today = LocalDate.now();
                    LocalDate tomorrow = today.plusDays(1);

                    int stage = Character
                            .getNumericValue(responseJSON.getJsonArray("events").get(0).get("note").asText().charAt(6));
                    stageArrayIndex = stage - 1;

                    startTime = responseJSON.getJsonArray("events").get(0).get("start").asText().substring(11, 16);
                    endTime = responseJSON.getJsonArray("events").get(0).get("end").asText().substring(11, 16);

                    JsonNode scheduleDays = responseJSON.getJsonArray("schedule").get("days");
                    int i = 0;
                    for (JsonNode day : scheduleDays) {
                        if (day.get("date").asText().equals(today.toString())) {
                            JsonNode stagesArray = day.get("stages");

                            if (stagesArray.has(stageArrayIndex)) {
                                JsonNode stageTimes = stagesArray.get(stageArrayIndex);
                                time1 = stageTimes.has(0) ? stageTimes.get(0).asText().substring(0, 5) : "";
                                time2 = stageTimes.has(1) ? stageTimes.get(1).asText().substring(0, 5) : "";
                                time3 = stageTimes.has(2) ? stageTimes.get(2).asText().substring(0, 5) : "";
                                time4 = stageTimes.has(3) ? stageTimes.get(3).asText().substring(0, 5) : "";
                            }
                        }

                        if (day.get("date").asText().equals(tomorrow.toString())) {
                            JsonNode stagesArray = day.get("stages");
                            if (stagesArray.has(stageArrayIndex) && stagesArray.get(stageArrayIndex).has(0)) {
                                String firstTime = stagesArray.get(stageArrayIndex).get(0).asText().substring(0, 5);
                                if (firstTime.equals("00:00")) {
                                    atMidnight = true;
                                }
                            }
                        }

                        i++;
                        if (i > 1)
                            break; // Only need to check today and tomorrow
                    }

                    String jsonString = String.format(
                            "{\"stage\": %d, \"startTime\": \"%s\", \"endTime\": \"%s\", \"time1\": \"%s\", \"time2\": \"%s\", \"time3\": \"%s\", \"time4\": \"%s\", \"atMidnight\": %b}",
                            stage, startTime, endTime, time1, time2, time3, time4, atMidnight);

                    return new ObjectMapper().readTree(jsonString);
                } else {
                    return new ObjectMapper().readTree("{\"stage\" : 0}");
                }
            } else {
                return new ObjectMapper().readTree("{\"error\" : \"" + response.getStatus() + "\"}");
            }
        } else {
            return new ObjectMapper().readTree("{\"error\" : -1}");
        }
    }

    public static boolean isTokenValid(String token) throws IOException {
        HttpResponse<String> response = Unirest.get(
                "https://developer.sepush.co.za/business/2.0/area?id=eskde-6-onrusoverstrandwesterncape&test=current")
                .header("Token", token)
                .asString();

        int statusCode = response.getStatus();

        switch (statusCode) {
            case 200:
                return true;

            case 429:
                return true;

            default:
                return false;
        }

    }

    /**
     * Retrieves information about an area based on a search query.
     * 
     * @param search the search query
     * @return a JSON string containing area name and ID or an error message
     * @throws IOException if there is an error with encoding the search query
     */
    public String getAreaInfo(String search) throws IOException {
        search = URLEncoder.encode(search, StandardCharsets.UTF_8.toString());

        HttpResponse<String> responseString = Unirest.get(BASE_URL + "areas_search?text=" + search)
                .header("Token", TOKEN)
                .asString();

        if (responseString.getStatus() == 200) {
            JSONManager jsonMngr = new JSONManager(responseString.getBody());
            JsonNode areasArray = jsonMngr.getJsonArray("areas");

            if (areasArray != null && areasArray.isArray() && areasArray.size() > 0) {
                JsonNode firstArea = areasArray.get(0);
                String name = firstArea.has("name") ? firstArea.get("name").asText() : null;
                String id = firstArea.has("id") ? firstArea.get("id").asText() : null;
                return String.format("{\"areaName\": \"%s\", \"areaID\": \"%s\"}", name, id);
            } else {
                return "{\"error\": \"404\"}";
            }
        } else {
            return String.format("{\"error\": \"%s\"}", responseString.getStatus());
        }
    }

    /**
     * Retrieves the area name based on a search query.
     * 
     * @param search the search query
     * @return the area name or an error message
     * @throws UnsupportedEncodingException if there is an error with encoding the
     *                                      search query
     */
    public String getAreaName(String search) throws UnsupportedEncodingException {
        search = URLEncoder.encode(search, StandardCharsets.UTF_8.toString());

        HttpResponse<String> responseString = Unirest.get(BASE_URL + "areas_search?text=" + search)
                .header("Token", TOKEN)
                .asString();

        if (responseString.getStatus() == 200) {
            JSONManager jsonMngr = new JSONManager(responseString.getBody());
            return jsonMngr.getJsonArray("areas").get(0).get("name").asText();
        } else {
            return "" + responseString.getStatus();
        }
    }

    /**
     * Retrieves the area ID based on a search query.
     * 
     * @param search the search query
     * @return the area ID
     * @throws UnsupportedEncodingException if there is an error with encoding the
     *                                      search query
     */
    public String getAreaID(String search) throws UnsupportedEncodingException {
        search = URLEncoder.encode(search, StandardCharsets.UTF_8.toString());

        HttpResponse<String> responseString = Unirest.get(BASE_URL + "areas_search?text=" + search)
                .header("Token", TOKEN)
                .asString();

        JSONManager jsonMngr = new JSONManager(responseString.getBody());
        return jsonMngr.getJsonArray("areas").get(0).get("id").asText();
    }

    /**
     * Retrieves the API token from the settings file.
     * 
     * @return the API token
     */
    private String getTokenFromSettings() {
        JSONManager settingsManager = new JSONManager(new File("settings.json"));
        this.TOKEN = settingsManager.has("token") ? settingsManager.getString("token") : null;
        return this.TOKEN;
    }

    /**
     * Retrieves the area ID from the settings file.
     * 
     * @return the area ID
     */
    private String getAreaID() {
        JSONManager settingsManager = new JSONManager(new File("settings.json"));
        areaID = settingsManager.has("areaID") ? settingsManager.getString("areaID") : null;
        return areaID;
    }

    /**
     * Retrieves the area name from the settings file.
     * 
     * @return the area name
     */
    private String getAreaName() {
        JSONManager settingsManager = new JSONManager(new File("settings.json"));
        areaName = settingsManager.has("areaName") ? settingsManager.getString("areaName") : null;
        return areaName;
    }

    // Purely for testing purposes
    @SuppressWarnings("unused")
    private String customAPIString = "{\"events\":[],\"info\":{\"name\":\"Onrus (6)\",\"region\":\"Eskom Direct, Overstrand, Western Cape\"},\"schedule\":{\"days\":[{\"date\":\"2025-02-02\",\"name\":\"Sunday\",\"stages\":[[\"18:00-20:30\"],[\"10:00-12:30\",\"18:00-20:30\"],[\"02:00-04:30\",\"10:00-12:30\",\"18:00-20:30\"],[\"02:00-04:30\",\"10:00-12:30\",\"18:00-20:30\"],[\"02:00-04:30\",\"10:00-12:30\",\"18:00-22:30\"],[\"02:00-04:30\",\"10:00-14:30\",\"18:00-22:30\"],[\"02:00-06:30\",\"10:00-14:30\",\"18:00-22:30\"],[\"02:00-06:30\",\"10:00-14:30\",\"18:00-22:30\"]]},{\"date\":\"2025-02-03\",\"name\":\"Monday\",\"stages\":[[],[\"18:00-20:30\"],[\"10:00-12:30\",\"18:00-20:30\"],[\"02:00-04:30\",\"10:00-12:30\",\"18:00-20:30\"],[\"02:00-04:30\",\"10:00-12:30\",\"18:00-20:30\"],[\"02:00-04:30\",\"10:00-12:30\",\"18:00-22:30\"],[\"02:00-04:30\",\"10:00-14:30\",\"18:00-22:30\"],[\"02:00-06:30\",\"10:00-14:30\",\"18:00-22:30\"]]},{\"date\":\"2025-02-04\",\"name\":\"Tuesday\",\"stages\":[[\"02:00-04:30\"],[\"02:00-04:30\"],[\"02:00-04:30\",\"18:00-20:30\"],[\"02:00-04:30\",\"10:00-12:30\",\"18:00-20:30\"],[\"02:00-06:30\",\"10:00-12:30\",\"18:00-20:30\"],[\"02:00-06:30\",\"10:00-12:30\",\"18:00-20:30\"],[\"02:00-06:30\",\"10:00-12:30\",\"18:00-22:30\"],[\"02:00-06:30\",\"10:00-14:30\",\"18:00-22:30\"]]},{\"date\":\"2025-02-05\",\"name\":\"Wednesday\",\"stages\":[[\"08:00-10:30\"],[\"00:00-02:30\",\"08:00-10:30\"],[\"00:00-02:30\",\"08:00-10:30\"],[\"00:00-02:30\",\"08:00-10:30\",\"16:00-18:30\"],[\"00:00-02:30\",\"08:00-12:30\",\"16:00-18:30\"],[\"00:00-04:30\",\"08:00-12:30\",\"16:00-18:30\"],[\"00:00-04:30\",\"08:00-12:30\",\"16:00-18:30\"],[\"00:00-04:30\",\"08:00-12:30\",\"16:00-20:30\"]]},{\"date\":\"2025-02-06\",\"name\":\"Thursday\",\"stages\":[[\"16:00-18:30\"],[\"08:00-10:30\",\"16:00-18:30\"],[\"00:00-02:30\",\"08:00-10:30\",\"16:00-18:30\"],[\"00:00-02:30\",\"08:00-10:30\",\"16:00-18:30\"],[\"00:00-02:30\",\"08:00-10:30\",\"16:00-20:30\"],[\"00:00-02:30\",\"08:00-12:30\",\"16:00-20:30\"],[\"00:00-04:30\",\"08:00-12:30\",\"16:00-20:30\"],[\"00:00-04:30\",\"08:00-12:30\",\"16:00-20:30\"]]},{\"date\":\"2025-02-07\",\"name\":\"Friday\",\"stages\":[[],[\"16:00-18:30\"],[\"08:00-10:30\",\"16:00-18:30\"],[\"00:00-02:30\",\"08:00-10:30\",\"16:00-18:30\"],[\"00:00-02:30\",\"08:00-10:30\",\"16:00-18:30\"],[\"00:00-02:30\",\"08:00-10:30\",\"16:00-20:30\"],[\"00:00-02:30\",\"08:00-12:30\",\"16:00-20:30\"],[\"00:00-04:30\",\"08:00-12:30\",\"16:00-20:30\"]]},{\"date\":\"2025-02-08\",\"name\":\"Saturday\",\"stages\":[[\"00:00-02:30\"],[\"00:00-02:30\"],[\"00:00-02:30\",\"16:00-18:30\"],[\"00:00-02:30\",\"08:00-10:30\",\"16:00-18:30\"],[\"00:00-04:30\",\"08:00-10:30\",\"16:00-18:30\"],[\"00:00-04:30\",\"08:00-10:30\",\"16:00-18:30\"],[\"00:00-04:30\",\"08:00-10:30\",\"16:00-20:30\"],[\"00:00-04:30\",\"08:00-12:30\",\"16:00-20:30\"]]}],\"source\":\"https://loadshedding.eskom.co.za/\"}}";

}