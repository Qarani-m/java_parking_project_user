package com.parking.parking.controller.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PythonRunner {
    public static void main(String[] args) throws IOException {
        // Create a ProcessBuilder object for the command
        ProcessBuilder pb = new ProcessBuilder("python3", "f.py");

        // Start the process
        Process process = pb.start();

        // Read the output of the command
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
}
