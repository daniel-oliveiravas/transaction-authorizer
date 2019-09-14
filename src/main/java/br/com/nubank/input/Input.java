package br.com.nubank.input;

import br.com.nubank.core.BasicOperationProcessor;
import br.com.nubank.core.interfaces.OperationProcessor;
import br.com.nubank.models.Operation;
import br.com.nubank.serialization.LocalDateTimeAdapter;
import br.com.nubank.serialization.OperationAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Input {

    private static String firstLine = "{ \"account\": { \"activeCard\": true, \"availableLimit\": 100 } }";
    private static String secondLine = "{ \"transaction\": { \"merchant\": \"Burger King\", \"amount\": 20, \"time\": \"2019-02-13T10:00:00.000Z\" } }";
    private static String thirdLine = "{ \"transaction\": { \"merchant\": \"Habbib's\", \"amount\": 90, \"time\": \"2019-02-13T11:00:00.000Z\" } }";

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Operation.class, new OperationAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    private static OperationProcessor operationProcessor = new BasicOperationProcessor();

    public static void main(String[] args) throws Exception {
        List<String> operations = Arrays.asList(firstLine, secondLine, thirdLine);

        for (String operation : operations) {
            Operation object = gson.fromJson(operation, Operation.class);
            operationProcessor.process(object);
        }
    }
}
