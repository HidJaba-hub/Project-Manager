package com.es.projectManager.exception;

public class ProductDefinitionException extends RuntimeException {
    public ProductDefinitionException(String message) {
        super(message);
    }

    public ProductDefinitionException() {
        super();
    }
}
