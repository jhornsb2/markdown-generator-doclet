package com.documentation.generator.java;

/**
 * This is a simple class that returns a greeting message. This is meant for
 * testing a basic Java class.
 */
public class App {
    /**
     * Returns a greeting message.
     * 
     * @return a greeting message
     */
    public String getGreeting() {
        return "Hello World!";
    }

    /**
     * Main method to run the application.
     * 
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
    }
}
