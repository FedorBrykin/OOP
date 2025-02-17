package ru.nsu.brykin;

/**
 * для json-ов.
 */
class Config {
    private int n;
    private int m;
    private int t;
    private int[] bakerSpeeds;
    private int[] courierCapacities;

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public int getT() {
        return t;
    }

    public int[] getBakerSpeeds() {
        return bakerSpeeds;
    }

    public int[] getCourierCapacities() {
        return courierCapacities;
    }

    public int[] getCourierDeliveryTimes() {
        return courierCapacities;
    }
}