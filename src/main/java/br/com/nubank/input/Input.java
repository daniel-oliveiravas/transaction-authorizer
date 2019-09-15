package br.com.nubank.input;

import br.com.nubank.core.BasicOperationProcessor;
import br.com.nubank.core.exceptions.InvalidOperationTypeException;
import br.com.nubank.core.interfaces.OperationProcessor;
import br.com.nubank.models.Operation;
import br.com.nubank.serialization.LocalDateTimeAdapter;
import br.com.nubank.serialization.OperationAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.stream.Stream;

public class Input {

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Operation.class, new OperationAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    private static OperationProcessor operationProcessor = new BasicOperationProcessor();

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Usage: java -jar <jar-path> <operation-filepath>");
        } else {
            String filepath = args[0];
            try (Stream<String> stream = Files.lines(Paths.get(filepath))) {
                stream.forEach(line -> {
                    try {
                        operationProcessor.process(gson.fromJson(line, Operation.class));
                    } catch (InvalidOperationTypeException e) {
                        System.out.println("Invalid operation");
                    } catch (JsonSyntaxException e) {
                        System.out.println("File content malformed");
                    }
                });
            } catch (IOException e) {
                System.out.println("Problem while trying to read file");
            }
        }
    }
}
