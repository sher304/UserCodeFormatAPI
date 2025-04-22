package org.example.codeformater.Service;


import com.google.googlejavaformat.java.Formatter;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;

@Service
public class CodeSerialization {

    public final Formatter formatter;

    @Autowired
    public CodeSerialization(Formatter formatter) {
        this.formatter = formatter;
    }

    public void saveUserCode(String codeId, String userCode, long expirySeconds) {
        try {
            JSONObject json = new JSONObject();
            json.put("codeID", codeId);
            json.put("formattedCode", userCode);
            json.put("expiresAt", Instant.now().plusSeconds(expirySeconds).toString());
            try (FileWriter file = new FileWriter("user_code.json")) {
                file.write(json.toString(4));
                file.flush();
            }
            System.out.println("Code saved to: user_code.json");
        } catch (JSONException | IOException e) {
            throw new RuntimeException("Failed to save code: " + e.getMessage(), e);
        }
    }


    public String loadFormattedCode(String codeId) {
        if (!Files.exists(Paths.get("user_code.json"))) {
            return "Code with ID '" + codeId + "' not found.";
        }

        try {
            String content = Files.readString(Paths.get("user_code.json"));
            JSONObject json = new JSONObject(content);

            Instant expiresAt = Instant.parse(json.getString("expiresAt"));
            if (Instant.now().isAfter(expiresAt)) {
                Files.delete(Paths.get("user_code.json"));
                return "This code has expired and was deleted.";
            }

            String res = json.getString("formattedCode");
            return formatter.formatSource(res);

        } catch (Exception e) {
            e.printStackTrace();
            return "Error loading saved code.";
        }
    }

}
