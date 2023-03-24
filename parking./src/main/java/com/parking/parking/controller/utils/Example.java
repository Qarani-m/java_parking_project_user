package com.parking.parking.controller.utils;
public class Example {
    public static void main(String[] args) {
        String arr = "[[0, 0, 11]]";
        String inner = arr.substring(1, arr.length() - 1);
        inner = inner.replace("[", "").replace("]", "");
        String[] parts = inner.split(", ");
        int[] values = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            values[i] = Integer.parseInt(parts[i]);
        }
        for (int i = 0; i < values.length; i++) {
            System.out.print(values[i] + " ");
        }
    }
}
