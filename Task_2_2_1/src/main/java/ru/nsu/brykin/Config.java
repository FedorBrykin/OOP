package ru.nsu.brykin;

class Config {
    private int N;
    private int M;
    private int T;
    private int[] bakerSpeeds;
    private int[] courierCapacities;

    public int getN() { return N; }
    public int getM() { return M; }
    public int getT() { return T; }
    public int[] getBakerSpeeds() { return bakerSpeeds; }
    public int[] getCourierCapacities() { return courierCapacities; }
    public int[] getCourierDeliveryTimes() {return courierCapacities; }
}