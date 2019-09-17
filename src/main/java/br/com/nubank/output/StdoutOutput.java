package br.com.nubank.output;

import br.com.nubank.core.Output;
import br.com.nubank.models.TransactionResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class StdoutOutput implements Output {

    private static final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    @Override
    public void send(TransactionResult transactionResult) {
        System.out.println(gson.toJson(transactionResult));
    }
}
