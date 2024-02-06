package xyz.blurple.chatmsglib.list;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonList extends ChatList {
    JsonObject outputJson;
    int indent = 2;


    public JsonList(JsonObject outputList) {
        this.outputJson = outputList;
    }

    @Override
    public ChatList indent(int indent) {
        this.indent = indent;
        return this;
    }

    @Override
    public List<Text> create() {
        return parseJsonObject(outputJson, this.indent);
    }

    private List<Text> parseJsonObject(JsonObject jsonObject, int indent) {
        List<Text> detailsList = new ArrayList<>();

        // Iterate over the entries
        Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            String key = entry.getKey();
            JsonElement value = entry.getValue();

            // indentation
            StringBuilder indentation = new StringBuilder();
            indentation.append("  ".repeat(Math.max(0, indent))); // Two spaces for each level

            // Check if the value is a JsonObject
            if (value.isJsonObject()) {
                // Recursively call the method for nested objects
                List<Text> nestedDetails = parseJsonObject(value.getAsJsonObject(), indent + 1);
                detailsList.add(Text.literal(indentation + key + ": {"));
                detailsList.addAll(nestedDetails);
                detailsList.add(Text.literal(indentation + "}"));
            } else if (value.isJsonArray()) {
                // If the value is a JsonArray, iterate over its elements
                JsonArray jsonArray = value.getAsJsonArray();
                detailsList.add(Text.literal(indentation + key + ": ["));
                for (JsonElement element : jsonArray) {
                    if (element.isJsonObject()) {
                        // Recursively call the method for nested objects within the array
                        List<Text> nestedDetails = parseJsonObject(element.getAsJsonObject(), indent + 1);
                        detailsList.addAll(nestedDetails);
                    } else {
                        // Add the array element to the list
                        detailsList.add(Text.literal(indentation + "  " + element.getAsString()));
                    }
                }
                detailsList.add(Text.literal(indentation + "]"));
            } else {
                detailsList.add(Text.literal(indentation + this.pre + key + ": " + value.getAsString() + this.suf).formatted(this.form));
            }
        }
        return detailsList;
    }
}
