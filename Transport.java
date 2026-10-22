package com.example.transportapp;

public class Transport {

    private String transportType;
    private double distance;
    private double costPerKm;
    private int travelDays;

    public Transport(String transportType, double distance,
                     double costPerKm, int travelDays) {

        this.transportType = transportType;
        this.distance = distance;
        this.costPerKm = costPerKm;
        this.travelDays = travelDays;
    }

    public String getTransportType()
    {
        return transportType;
    }

    public double getDistance()
    {
        return distance;
    }

    public double getCostPerKm()
    {
        return costPerKm;
    }

    public int getTravelDays()
    {
        return travelDays;
    }
}