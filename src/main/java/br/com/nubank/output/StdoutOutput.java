package br.com.nubank.output;

import br.com.nubank.models.TransactionResult;
import com.google.gson.Gson;

public class StdoutOutput implements Output {

    private static final Gson gson = new Gson();

    @Override
    public void send(TransactionResult transactionResult) {
        System.out.println(gson.toJson(transactionResult));
    }
}
