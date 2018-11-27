package com.company;

import java.util.Random;

public class PSOParticle {
    private double[] currentPosition;
    private double[] currentVelocity;
    private double[] personalBestPosition;

    PSOParticle(double[] currentPosition, double[] currentVelocity) {
        this.currentPosition = currentPosition;
        this.currentVelocity = currentVelocity;
//        this.personalBestPosition = personalBestPosition;
    }

    public double[] setCurrentVelocity(double[] bestPosition, double coefficient1, double coefficient2, double coefficient3, int antennaSize) {
        Random random = new Random();
        double lastAntennae = antennaSize / 2.0;
        double[] newVelocity = new double[antennaSize];

        double[] randomVector1 = new double[antennaSize];
        double[] randomVector2 = new double[antennaSize];
        // Initialise all elements of vector with random doubles apart from the last one
        for (int i = 0; i < antennaSize - 1; i++) {
            randomVector1[i] = random.nextDouble();
            randomVector2[i] = random.nextDouble();
        }
        // Last value of vector being assigned with exactly half the number of antennae
        randomVector1[antennaSize - 1] = lastAntennae;
        randomVector2[antennaSize - 1] = lastAntennae;

        for (int i = 0; i < bestPosition.length - 1; i++) {
            currentVelocity[i] = (coefficient1 * currentVelocity[i]) +
                    (coefficient2 * randomVector1[i] * (personalBestPosition[i] - currentPosition[i])) +
                    (coefficient3 * randomVector2[i] * (bestPosition[i] - currentPosition[i]));
            newVelocity[i] = currentPosition[i] + currentVelocity[i];
        }
        newVelocity[antennaSize - 1] = lastAntennae;
        return newVelocity;

    }

    public double[] getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(double[] currentPosition) {
        this.currentPosition = currentPosition;
    }

    public double[] getCurrentVelocity() {
        return currentVelocity;
    }

    public double[] getPersonalBestPosition() {
        return personalBestPosition;
    }

    public void setPersonalBestPosition(double[] personalBestPosition) {
        this.personalBestPosition = personalBestPosition;
    }
}
