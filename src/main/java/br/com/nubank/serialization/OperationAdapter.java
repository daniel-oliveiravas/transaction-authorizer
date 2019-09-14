package br.com.nubank.serialization;

import br.com.nubank.models.Account;
import br.com.nubank.models.Operation;
import br.com.nubank.models.Transaction;
import com.google.gson.*;

import java.lang.reflect.Type;

public class OperationAdapter implements JsonDeserializer<Operation> {
    @Override
    public Operation deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if(jsonObject.has("account")) {
            return context.deserialize(jsonObject.get("account"), Account.class);
        } else {
            return context.deserialize(jsonObject.get("transaction"), Transaction.class);
        }
    }
}
