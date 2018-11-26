package com.company;

public class PSOParticle {
    private double[] currentPosition;
    private double[] currentVelocity;
    private double[] personalBestPosition;

    PSOParticle(double[] currentPosition, double[] currentVelocity, double personalBestPosition[]) {
        this.currentPosition = currentPosition;
        this.currentVelocity = currentVelocity;
        this.personalBestPosition = personalBestPosition;
    }

    public void setCurrentPosition(double[] currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setCurrentVelocity(double[] currentVelocity) {
        this.currentVelocity = currentVelocity;
    }

    public void setPersonalBestPosition(double[] personalBestPosition) {
        this.personalBestPosition = personalBestPosition;
    }

    public double[] getCurrentPosition() {
        return currentPosition;
    }

    public double[] getCurrentVelocity() {
        return currentVelocity;
    }

    public double[] getPersonalBestPosition() {
        return personalBestPosition;
    }
}
