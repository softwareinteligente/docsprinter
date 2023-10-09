package test;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;

public class CreateAndWriteJsonFileExample {
    public static void main(String[] args) {
        // Create a JSON object with subfields
        JsonObject personJsonObject = new JsonObject();
        personJsonObject.addProperty("name", "John Doe");
        personJsonObject.addProperty("age", 30);

        // Create a JSON object for address
        JsonObject addressJsonObject = new JsonObject();
        addressJsonObject.addProperty("street", "123 Main St");
        addressJsonObject.addProperty("city", "New York");
        addressJsonObject.addProperty("zip", "10001");

        // Add the address JSON object as a subfield
        personJsonObject.add("address", addressJsonObject);

        // Create a Gson object with pretty printing
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Convert the JSON object to a JSON string
        String jsonString = gson.toJson(personJsonObject);

        // Write the JSON string to a file
        try (FileWriter writer = new FileWriter("person.json")) {
            writer.write(jsonString);
            System.out.println("JSON data written to person.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

