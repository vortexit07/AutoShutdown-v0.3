package za.co.vortexit.pkg3.v0.autoshutdown;

/**
 *
 * @author Connor Lewis
 */
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

    public JSONManager() {
    }

    public JSONManager(JsonNode jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONManager(File file) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            this.file = file;

            ObjectMapper mapper = new ObjectMapper();
            this.jsonObject = mapper.readTree(file);
        } catch (IOException ex) {
            Logger.getLogger(JSONManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public JSONManager(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.jsonObject = mapper.readTree(jsonString);
        } catch (IOException ex) {
            Logger.getLogger(JSONManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getString(String key) {
        return jsonObject.get(key).isNull() ? null : jsonObject.get(key).asText();
    }
    
    public JsonNode get(String key){
        return jsonObject.get(key).isNull() ? null : jsonObject.get(key);
    }

    public int getInt(String key) {
        return jsonObject.get(key).isNull() ? null : jsonObject.get(key).asInt();
    }

    public boolean getBool(String key) {
        return jsonObject.get(key).isNull() ? null : jsonObject.get(key).asBoolean();
    }

    public void setJsonObject(JsonNode jsonObject) {
        this.jsonObject = jsonObject;
    }

    public void setFile(File file) {
        this.file = file;

    }

    public void setJsonObject(String jsonString) {
        this.jsonObject = toJsonObject(jsonString);
    }
    
    public boolean has(String key){
        return jsonObject.has(key);
    }

    @Override
    public String toString() {
        return "JSONManager{" + "jsonObject=" + jsonObject + ", file=" + file + '}';
    }

    private JsonNode toJsonObject(File file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(file);
        } catch (IOException ex) {
            Logger.getLogger(JSONManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private JsonNode toJsonObject(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(jsonString);
        } catch (IOException ex) {
            Logger.getLogger(JSONManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void writeFile(String key, String value) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter();
            ObjectNode jsonNode = mapper.createObjectNode();
            jsonNode.put(key, value);

            writer.write("" + jsonNode);
            writer.close();

            jsonObject = jsonNode;

        } catch (IOException ex) {
            Logger.getLogger(JSONManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void appendFile(String key, String value) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter();

            // Read the existing content of the file into a JsonNode
            JsonNode rootNode;
            if (file.exists() && file.length() > 0) {
                rootNode = mapper.readTree(file);
            } else {
                // If file does not exist or is empty, initialize rootNode as an empty ObjectNode
                rootNode = mapper.createObjectNode();
            }

            // Check if rootNode is indeed an ObjectNode to safely cast and use it
            if (rootNode instanceof ObjectNode) {
                ObjectNode objectNode = (ObjectNode) rootNode;
                objectNode.put(key, value); // Update the JSON content with the new key-value pair

                // Write the updated JSON content back to the file
                FileWriter writer = new FileWriter(file, false); // 'false' to overwrite the file
                writer.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode));
                writer.close();

                jsonObject = toJsonObject(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode));

            } else {
                // Handle the case where the file content is not a JSON object (i.e., not an instance of ObjectNode)
                Logger.getLogger(JSONManager.class.getName()).log(Level.SEVERE, "File content is not a JSON Object");
            }
        } catch (IOException ex) {
            Logger.getLogger(JSONManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public JsonNode getJsonArray(String key) {
        JsonNode node = jsonObject.get(key);
        if (node != null && node.isArray()) {
            return node;
        }
        return null;
    }

}
