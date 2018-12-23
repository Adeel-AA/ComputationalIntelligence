package com.company;

import java.util.Random;

public class MarketPricingPSOParticle {
    private double[] currentPosition;
    private double[] currentVelocity;
    private double[] personalBestPosition;

    MarketPricingPSOParticle(double[] currentPosition, double[] currentVelocity) {
        this.currentPosition = currentPosition;
        this.currentVelocity = currentVelocity;
//        this.personalBestPosition = personalBestPosition;
    }

    public void setCurrentVelocity(double[] bestPosition, double coefficient1, double coefficient2, double coefficient3, int antennaSize) {
        Random random = new Random();
        double lastAntennae = antennaSize / 2.0;
        double[] newVelocity = new double[antennaSize];

        double randomVector1 = random.nextDouble();
        double randomVector2 = random.nextDouble();

        // Last value of vector being assigned with exactly half the number of antennae

        for (int i = 0; i < bestPosition.length - 1; i++) {
            newVelocity[i] = (coefficient1 * currentVelocity[i]) +
                    (coefficient2 * randomVector1 * (personalBestPosition[i] - currentPosition[i])) +
                    (coefficient3 * randomVector2 * (bestPosition[i] - currentPosition[i]));
//            newVelocity[i] = currentPosition[i] + currentVelocity[i];
        }

        this.currentVelocity = newVelocity;

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
