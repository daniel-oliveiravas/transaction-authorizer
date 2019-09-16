package br.com.nubank.core;

import br.com.nubank.core.exceptions.AccountNotInitializedException;
import br.com.nubank.core.exceptions.InvalidOperationTypeException;
import br.com.nubank.models.Operation;
import br.com.nubank.serialization.LocalDateTimeAdapter;
import br.com.nubank.serialization.OperationAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class BasicOperationProcessorIT {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Operation.class, new OperationAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private BasicOperationProcessor basicOperationProcessor;

    @Before
    public void setUpStreams() {
        basicOperationProcessor = new BasicOperationProcessor();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void shouldProcessBasicOperationsSuccessfully() throws InvalidOperationTypeException {
        List<Operation> operations = readOperationsFromFile("basic-operations");
        for (Operation operation : operations) {
            basicOperationProcessor.process(operation);
        }

        Assert.assertEquals("{\"account\":{\"availableLimit\":100,\"activeCard\":true},\"violations\":[]}\n" +
                "{\"account\":{\"availableLimit\":80,\"activeCard\":true},\"violations\":[]}\n" +
                "{\"account\":{\"availableLimit\":80,\"activeCard\":true},\"violations\":[\"insufficient-limit\"]}\n", outContent.toString());
    }

    @Test
    public void shouldValidateAccountAlreadyCreated() throws InvalidOperationTypeException {
        List<Operation> operations = readOperationsFromFile("account-already-created");
        for (Operation operation : operations) {
            basicOperationProcessor.process(operation);
        }

        Assert.assertEquals("{\"account\":{\"availableLimit\":120,\"activeCard\":true},\"violations\":[]}\n" +
                "{\"account\":{\"availableLimit\":100,\"activeCard\":true},\"violations\":[]}\n" +
                "{\"account\":{\"availableLimit\":100,\"activeCard\":true},\"violations\":[\"account-already-initialized\"]}\n", outContent.toString());
    }

    @Test
    public void shouldValidateInactiveCard() throws InvalidOperationTypeException {
        List<Operation> operations = readOperationsFromFile("inactive-card");
        for (Operation operation : operations) {
            basicOperationProcessor.process(operation);
        }

        Assert.assertEquals("{\"account\":{\"availableLimit\":120,\"activeCard\":false},\"violations\":[]}\n" +
                "{\"account\":{\"availableLimit\":120,\"activeCard\":false},\"violations\":[\"card-not-active\"]}\n", outContent.toString());
    }

    @Test
    public void shouldValidateDuplicatedTransaction() throws InvalidOperationTypeException {
        List<Operation> operations = readOperationsFromFile("duplicated-transaction");
        for (Operation operation : operations) {
            basicOperationProcessor.process(operation);
        }

        Assert.assertEquals("{\"account\":{\"availableLimit\":120,\"activeCard\":true},\"violations\":[]}\n" +
                "{\"account\":{\"availableLimit\":100,\"activeCard\":true},\"violations\":[]}\n" +
                "{\"account\":{\"availableLimit\":100,\"activeCard\":true},\"violations\":[\"doubled-transaction\"]}\n", outContent.toString());
    }

    @Test
    public void shouldValidateHighFrequencyTransactions() throws InvalidOperationTypeException {
        List<Operation> operations = readOperationsFromFile("transactions-frequency");
        for (Operation operation : operations) {
            basicOperationProcessor.process(operation);
        }

        Assert.assertEquals("{\"account\":{\"availableLimit\":120,\"activeCard\":true},\"violations\":[]}\n" +
                "{\"account\":{\"availableLimit\":100,\"activeCard\":true},\"violations\":[]}\n" +
                "{\"account\":{\"availableLimit\":80,\"activeCard\":true},\"violations\":[]}\n" +
                "{\"account\":{\"availableLimit\":60,\"activeCard\":true},\"violations\":[]}\n" +
                "{\"account\":{\"availableLimit\":60,\"activeCard\":true},\"violations\":[\"high-frequency-small-interval\"]}\n", outContent.toString());
    }

    @Test
    public void shouldValidateInsufficientLimit() throws InvalidOperationTypeException {
        List<Operation> operations = readOperationsFromFile("insufficient-limit");
        for (Operation operation : operations) {
            basicOperationProcessor.process(operation);
        }

        Assert.assertEquals("{\"account\":{\"availableLimit\":20,\"activeCard\":true},\"violations\":[]}\n" +
                "{\"account\":{\"availableLimit\":20,\"activeCard\":true},\"violations\":[\"insufficient-limit\"]}\n", outContent.toString());
    }

    @Test(expected = AccountNotInitializedException.class)
    public void shouldThrowExceptionWhenExecutingOperationWithoutAccount() throws InvalidOperationTypeException {
        List<Operation> operations = readOperationsFromFile("without-account");
        for (Operation operation : operations) {
            basicOperationProcessor.process(operation);
        }
    }

    private List<Operation> readOperationsFromFile(String filepath) {
        String path = Thread.currentThread().getContextClassLoader().getResource(filepath).getPath();
        List<Operation> operations = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            stream.forEach(line -> {
                try {
                    operations.add(gson.fromJson(line, Operation.class));
                } catch (JsonSyntaxException e) {
                    System.out.println("File content malformed");
                }
            });
        } catch (IOException e) {
            System.out.println("Problem while trying to read file");
        }

        return operations;
    }
}