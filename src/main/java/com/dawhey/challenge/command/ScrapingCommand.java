package com.dawhey.challenge.command;

public abstract class ScrapingCommand<T,R> {

    public R execute(T t) throws Exception {
        try {
            System.out.println("Starting: " + name());
            return executeLogic(t);
        } catch (Exception e) {
            System.out.println(errorMessage());
            e.printStackTrace();
            throw e;
        }
    }

    protected abstract R executeLogic(T t) throws Exception;

    protected abstract String name();

    private String errorMessage() {
        return "An error occurred during processing " + name();
    }
}
