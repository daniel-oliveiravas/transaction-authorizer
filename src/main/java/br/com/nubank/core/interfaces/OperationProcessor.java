package br.com.nubank.core.interfaces;

import br.com.nubank.core.exceptions.InvalidOperationTypeException;
import br.com.nubank.models.Operation;


public interface OperationProcessor {

    void process(Operation operation) throws InvalidOperationTypeException;
}
