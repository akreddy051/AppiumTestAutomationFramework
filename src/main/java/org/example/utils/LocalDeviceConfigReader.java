package org.example.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;
import java.util.Map;

public class LocalDeviceConfigReader {
    public static List<Map<String, String>> getLocalDeviceConfigs() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(
                    new File("src/main/java/org/example/resources/localDevices.json"),
                    new TypeReference<List<Map<String, String>>>() {}
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to read local device config", e);
        }
    }
}
