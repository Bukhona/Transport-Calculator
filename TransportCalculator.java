package com.example.transportapp;

public class TransportCalculator {

    public double calculateDailyCost(double distance, double costPerKm) {
        return distance * costPerKm;
    }

    public double calculateMonthlyCost(double dailyCost, int travelDays) {
        return dailyCost * travelDays;
    }

    public double calculateMonthlyDistance(double distance, int travelDays) {
        return distance * travelDays;
    }
}