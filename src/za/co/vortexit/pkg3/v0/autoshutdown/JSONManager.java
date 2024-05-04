package za.co.vortexit.pkg3.v0.autoshutdown;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JSONManager {

    private JsonNode jsonObject;
    private File file;
    private String jsonString;

    public JSONManager(JsonNode jsonObject) {
        this.jsonObject = jsonObject;
    }

//    Converts a file into a JSON object
    public JSONManager(File file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.jsonObject = mapper.readTree(file);
        } catch (IOException ex) {
            Logger.getLogger(JSONManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    Converts a string to a JSON object
    public JSONManager(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.jsonObject = mapper.readTree(jsonString);
        } catch (IOException ex) {
            Logger.getLogger(JSONManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getString(String key) {
        return jsonObject.get(key).asText();
    }

    public int getInt(String key) {
        return jsonObject.get(key).asInt();
    }

    public boolean getBool(String key) {
        return jsonObject.get(key).asBoolean();
    }

    public void setJsonObject(JsonNode jsonObject) {
        this.jsonObject = jsonObject;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    @Override
    public String toString() {
        return "JSONManager{" + "jsonObject=" + jsonObject + ", file=" + file + ", jsonString=" + jsonString + '}';
    }

    public void writeFile(String key, String value) {
        FileWriter writer = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            writer = new FileWriter(file);
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode jsonNode = mapper.createObjectNode();
            jsonNode.put(key, value);

            writer.write("" + jsonNode);
            
        } catch (IOException ex) {
            Logger.getLogger(JSONManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(JSONManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
