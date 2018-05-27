package com.jp.morgan.messaging.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Config {
    public static final JsonObject APP_CONFIG;
    private static final String CONFIG_FILE_PATH = "../messaging-engine/src/main/resources/broker_config.json";

    static {
        APP_CONFIG = readConfig();
    }

    public static JsonObject readConfig() {
        return read(CONFIG_FILE_PATH);
    }

    public static JsonObject read(String fileName) {
        // Read from File to String
        JsonObject jsonObject = new JsonObject();

        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(fileName));
            jsonObject = jsonElement.getAsJsonObject();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return jsonObject;
    }
}
