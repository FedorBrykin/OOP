package ru.nsu.brykin;

/**
 * для json-ов.
 */
class Config {
    private int n1;
    private int m1;
    private int t1;
    private int[] bakerSpeeds;
    private int[] courierCapacities;

    public int getN() {
        return n1;
    }

    public int getM() {
        return m1;
    }

    public int getT() {
        return t1;
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