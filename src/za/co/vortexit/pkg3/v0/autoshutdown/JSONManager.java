package za.co.vortexit.pkg3.v0.autoshutdown;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for managing JSON data.
 * Provides methods to read, write, update, and manipulate JSON objects.
 * This class uses Jackson's JSON library for JSON processing.
 * <p>
 * It supports operations such as:
 * <ul>
 * <li>Parsing JSON data from files, strings, and nodes.</li>
 * <li>Modifying and appending key-value pairs to a JSON object.</li>
 * <li>Serializing JSON objects to files with pretty-print formatting.</li>
 * </ul>
 * </p>
 * 
 * @author Connor Lewis
 */
public class JSONManager {

    private JsonNode jsonObject;
    private File file;

    /**
     * Constructs a JSONManager object with the provided JsonNode.
     * 
     * @param jsonObject the JsonNode to manage.
     */
    public JSONManager(JsonNode jsonObject) {
        this.jsonObject = jsonObject;
    }

    /**
     * Constructs a JSONManager object with the JsonNode parsed from the provided
     * file.
     * 
     * @param file the file containing JSON data.
     */
    public JSONManager(File file) {
        try {
            this.file = file;
            ObjectMapper mapper = new ObjectMapper();
            this.jsonObject = mapper.readTree(file);
        } catch (IOException ex) {
            Logger.getLogger(JSONManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Constructs a JSONManager object and creates the settings file
     * 
     * @param file the file containing JSON data.
     * @param type create settings file
     * @throws IOException
     */
    public JSONManager(File file, boolean createSettings) throws IOException {
        String settingsContent = "{\r\n" +
                "  \"token\": \"\",\r\n" +
                "  \"areaID\": \"\",\r\n" +
                "  \"areaName\": \"Search for Area\",\r\n" +
                "  \"runOnStartup\": false,\r\n" +
                "  \"startMinimized\": false,\r\n" +
                "  \"timeBefore\": 1,\r\n" +
                "  \"stage\": 0,\r\n" +
                "  \"startTime\": \"\",\r\n" +
                "  \"endTime\": \"\",\r\n" +
                "  \"time1\": \"\",\r\n" +
                "  \"time2\": \"\",\r\n" +
                "  \"time3\": \"\",\r\n" +
                "  \"time4\": \"\",\r\n" +
                "  \"atMidnight\": false\r\n" +
                "}";
        ObjectMapper mapper = new ObjectMapper();
        if (createSettings) {
            FileWriter writer = new FileWriter(file);
            writer.write(settingsContent);
            writer.close();
        }
        this.jsonObject = mapper.readTree(file);
        this.file = file;
    }

    /**
     * Writes the specified JSON String to the file.
     * 
     * @param jsonString JSON String to write to file
     */
    public void writeFile(String jsonString) {
        try {
            FileWriter writer = new FileWriter(this.file);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter();
            writer.write("" + mapper.readTree(jsonString));
            writer.close();
            this.jsonObject = mapper.readTree(this.file);
        } catch (IOException ex) {
            Logger.getLogger(JSONManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Constructs a JSONManager object with the JsonNode parsed from the provided
     * JSON string.
     * 
     * @param jsonString the JSON string.
     */
    public JSONManager(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.jsonObject = mapper.readTree(jsonString);
        } catch (IOException ex) {
            Logger.getLogger(JSONManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gets the string value associated with the specified key.
     * 
     * @param key the key whose associated value is to be returned.
     * @return the string value associated with the specified key, or null if the
     *         key is not found.
     */
    public String getString(String key) {
        return jsonObject.has(key) ? jsonObject.get(key).asText() : null;
    }

    /**
     * Gets the JsonNode associated with the specified key.
     * 
     * @param key the key whose associated value is to be returned.
     * @return the JsonNode associated with the specified key, or null if the key is
     *         not found.
     */
    public JsonNode get(String key) {
        return jsonObject.has(key) ? jsonObject.get(key) : null;
    }

    public LocalTime getTime(String key) {
        if (jsonObject.has(key)) {
            String time = jsonObject.get(key).asText();
            if (!time.equals("null") && !time.isBlank()) {
                return LocalTime.parse(time);
            }
            return null;
        } else
            return null;
    }

    /**
     * Gets the integer value associated with the specified key.
     * 
     * @param key the key whose associated value is to be returned.
     * @return the integer value associated with the specified key, or null if the
     *         key is not found.
     */
    public int getInt(String key) {
        return jsonObject.has(key) ? jsonObject.get(key).asInt() : null;
    }

    /**
     * Gets the boolean value associated with the specified key.
     * 
     * @param key the key whose associated value is to be returned.
     * @return the boolean value associated with the specified key, or null if the
     *         key is not found.
     */
    public boolean getBool(String key) {
        return jsonObject.has(key) ? jsonObject.get(key).asBoolean() : null;
    }

    /**
     * Sets the JsonNode to manage.
     * 
     * @param jsonObject the JsonNode to set.
     */
    public void setJsonObject(JsonNode jsonObject) {
        this.jsonObject = jsonObject;
    }

    /**
     * Sets the file containing JSON data.
     * 
     * @param file the file to set.
     */
    public void setFile(File file) {
        try {
            this.file = file;
            ObjectMapper mapper = new ObjectMapper();
            this.jsonObject = mapper.readTree(file);
        } catch (IOException ex) {
            Logger.getLogger(JSONManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sets the JsonNode parsed from the provided JSON string.
     * 
     * @param jsonString the JSON string to set.
     */
    public void setJsonObject(String jsonString) {
        this.jsonObject = toJsonObject(jsonString);
    }

    /**
     * Checks if the specified key is present in the JSON object.
     * 
     * @param key the key to check.
     * @return true if the key is present, otherwise false.
     */
    public boolean has(String key) {
        return this.jsonObject.has(key);
    }

    @Override
    public String toString() {
        return "JSONManager [jsonObject=" + jsonObject + ", file=" + file + "]";
    }

    /**
     * Returns the path of the file.
     * 
     * @return the path of the file.
     */
    public String getFile() {
        return "" + this.file;
    }

    /**
     * Converts the provided JSON string to a JsonNode.
     * 
     * @param jsonString the JSON string to convert.
     * @return the JsonNode parsed from the JSON string.
     */
    private JsonNode toJsonObject(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(jsonString);
        } catch (IOException ex) {
            Logger.getLogger(JSONManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Appends the specified key-value pair to the file.
     * 
     * @param key   the key.
     * @param value the value.
     */
    public void appendFile(String key, String value) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter();

            // Read the existing content of the file into a JsonNode
            JsonNode rootNode;
            if (file.exists() && file.length() > 0) {
                rootNode = mapper.readTree(file);
            } else {
                // If file does not exist or is empty, initialize rootNode as an empty
                // ObjectNode
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
                // Handle the case where the file content is not a JSON object (i.e., not an
                // instance of ObjectNode)
                Logger.getLogger(JSONManager.class.getName()).log(Level.SEVERE, "File content is not a JSON Object");
            }
        } catch (IOException ex) {
            Logger.getLogger(JSONManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Appends the specified key-value pair to the file.
     * 
     * @param key   the key.
     * @param value the value.
     */
    public void appendFile(String key, int value) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter();

            // Read the existing content of the file into a JsonNode
            JsonNode rootNode;
            if (file.exists() && file.length() > 0) {
                rootNode = mapper.readTree(file);
            } else {
                // If file does not exist or is empty, initialize rootNode as an empty
                // ObjectNode
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
                // Handle the case where the file content is not a JSON object (i.e., not an
                // instance of ObjectNode)
                Logger.getLogger(JSONManager.class.getName()).log(Level.SEVERE, "File content is not a JSON Object");
            }
        } catch (IOException ex) {
            Logger.getLogger(JSONManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Appends the specified key-value pair to the file.
     * 
     * @param key   the key.
     * @param value the value.
     */
    public void appendFile(String key, boolean value) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter();

            // Read the existing content of the file into a JsonNode
            JsonNode rootNode;
            if (file.exists() && file.length() > 0) {
                rootNode = mapper.readTree(file);
            } else {
                // If file does not exist or is empty, initialize rootNode as an empty
                // ObjectNode
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
                // Handle the case where the file content is not a JSON object (i.e., not an
                // instance of ObjectNode)
                Logger.getLogger(JSONManager.class.getName()).log(Level.SEVERE, "File content is not a JSON Object");
            }
        } catch (IOException ex) {
            Logger.getLogger(JSONManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Appends the specified key-value pair to the file.
     * 
     * @param key    the key.
     * @param object the value.
     */
    public void appendFile(String key, Object object) {
        String value = object == null ? "" : object.toString();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter();

            // Read the existing content of the file into a JsonNode
            JsonNode rootNode;
            if (file.exists() && file.length() > 0) {
                rootNode = mapper.readTree(file);
            } else {
                // If file does not exist or is empty, initialize rootNode as an empty
                // ObjectNode
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
                // Handle the case where the file content is not a JSON object (i.e., not an
                // instance of ObjectNode)
                Logger.getLogger(JSONManager.class.getName()).log(Level.SEVERE, "File content is not a JSON Object");
            }
        } catch (IOException ex) {
            Logger.getLogger(JSONManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gets the JsonNode at the specified key in the JSON object.
     * 
     * @param key the key whose associated value is to be returned.
     * @return the JsonNode at the specified key, or null if the key is not found.
     */
    public JsonNode getJsonArray(String key) {
        return jsonObject.has(key) ? jsonObject.get(key) : null;
    }

    /**
     * Gets the JsonNode at the specified index in the JSON array.
     * 
     * @param index the index of the JsonNode to be returned.
     * @return the JsonNode at the specified index, or null if the index is out of
     *         bounds.
     */
    public JsonNode getJsonArray(int index) {
        return jsonObject.has(index) ? jsonObject.get(index) : null;
    }
}