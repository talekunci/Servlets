package ua.goit.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;

public class HandleBodyUtil {

    private static final Gson jsonParser = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    public static <T> Optional<T> getModelFromStream(InputStream in, Class<T> returnType) {
        try (InputStream inputStream = in;
             Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
            String jsonStr = scanner.nextLine();
            return Optional.of(jsonParser.fromJson(jsonStr, returnType));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}