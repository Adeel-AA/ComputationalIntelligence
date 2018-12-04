package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PSO {

    private static AntennaArray antennaArray = new AntennaArray(3, 90);
    // 0.721 1.1193 1.1193 3 90
    // 0.1 1.0 1.0 5 55
    private static double coefficient1 = 0.721;
    private static double coefficient2 = 1.1193;
    private static double coefficient3 = 1.1193;



    private static double[] getRandomValidSolutions(AntennaArray antennaArray) {
//        PSOParticle newValidParticle;
        Random random = new Random();
        double[][] bounds = antennaArray.bounds();


        double[] antennae = new double[antennaArray.getN_antennae()];

        antennae[antennae.length - 1] = antennaArray.getN_antennae() / 2.0;

//
//        antennae[0] = 0.0;
//        antennae[1] = 0.5;
//        antennae[2] = 1.5;

        while (!antennaArray.is_valid(antennae)) {
            for (int i = 0; i < antennae.length - 1; i++) {
                double rangeMin = bounds[i][0];
                double rangeMax = bounds[i][1];

                double randomValue = rangeMin + (rangeMax + rangeMin) * random.nextDouble();
                antennae[i] = randomValue;
//                System.out.println(antennae[i] + " at position: " + i);

                if (antennae[i] - antennae[i + 1] >= 0.25 || antennae[i + 1] - antennae[i] >= 0.25) {

                    antennae[i] = randomValue;
                    System.out.println(antennae[i] + " at position: " + i);


                }


            }

        }


        System.out.println("The last antennae is: " + antennae[antennae.length - 1]);
        System.out.println("Length of antenna is: " + antennae.length);
        System.out.println("Evaluate method, peak SSL: " + antennaArray.evaluate(antennae));
        System.out.println("Is valid method: " + antennaArray.is_valid(antennae));
        System.out.println("The bounds are: " + Arrays.deepToString(bounds));
        System.out.println("Antennae : " + Arrays.toString(antennae));
//        newValidParticle = new PSOParticle(antennae);
        return antennae;
    }

    private static PSOParticle generateValidParticle(AntennaArray antennaArray) {
        Random random = new Random();
        PSOParticle newParticle = null;
        boolean complete = false;

        double[] position = new double[antennaArray.getN_antennae()];
        double[] velocity = new double[antennaArray.getN_antennae()];
        int antennaSize = antennaArray.getN_antennae();
        double lastAntennae = antennaArray.getN_antennae() / 2.0;

        while (!complete) {
            for (int i = 0; i < antennaSize - 1; i++) {
                position[i] = lastAntennae * random.nextDouble();
                velocity[i] = random.nextDouble() - position[i] / 2;
            }

            position[antennaSize - 1] = lastAntennae;
            velocity[antennaSize - 1] = lastAntennae;

            if (antennaArray.is_valid(position) && antennaArray.is_valid(velocity)) {
                newParticle = new PSOParticle(position, velocity);
                newParticle.setPersonalBestPosition(position);
                complete = true;
            }
        }
        return newParticle;
    }

    private static void particleSwarmOptimisation(AntennaArray antennaArray, int noOfParticles, int secondsTimeLimit) {
        long limit = (System.currentTimeMillis() / 1000) + secondsTimeLimit;
        long current = System.currentTimeMillis() / 1000;

        Random random = new Random();
        double globalBest = 0;
        double[] bestPosition = new double[antennaArray.getN_antennae()];
        int antennaSize = antennaArray.getN_antennae();

        double[][] bounds = antennaArray.bounds();
        ArrayList<PSOParticle> psoParticles = new ArrayList<>();

        // Initialise population
        for (int i = 0; i < noOfParticles; i++) {
            PSOParticle particle = generateValidParticle(antennaArray);
            psoParticles.add(particle);

            double evaluateParticle = antennaArray.evaluate(particle.getCurrentPosition());
            // Set global best to lowest of the population
            if (globalBest == 0) {
                globalBest = evaluateParticle;
                bestPosition = particle.getCurrentPosition();
            } else if (evaluateParticle < globalBest) {
                globalBest = evaluateParticle;
                bestPosition = particle.getCurrentPosition();
            }
        }
        int iterations = 30;
//        while (current < limit) {
        for (int i = 0; i < iterations ; i++) {

            for (PSOParticle particle : psoParticles) {
                boolean complete = false;
                while (!complete) {

                    double[] newPosition = particle.setCurrentVelocity(bestPosition, coefficient1, coefficient2, coefficient3, antennaSize);
                    //Check if new position is valid, if it isn't we don't evaluate the new position as it never gets set
                    if (antennaArray.is_valid(newPosition)) {
                        particle.setCurrentPosition(newPosition);
                        complete = true;

                    }

                }

                double newEvaluation = antennaArray.evaluate(particle.getCurrentPosition());

                if (newEvaluation < antennaArray.evaluate(particle.getPersonalBestPosition())) {
                    particle.setPersonalBestPosition(particle.getCurrentPosition());
                }

                if (newEvaluation < globalBest) {
                    globalBest = antennaArray.evaluate(particle.getCurrentPosition());
                    bestPosition = particle.getCurrentPosition();
                }

            }

//            current = System.currentTimeMillis() / 1000;

        }

        System.out.println(globalBest);
        System.out.println(Arrays.toString(bestPosition));
    }


    public static void main(String[] args) {


        particleSwarmOptimisation(antennaArray, 21, 5);

//        double[] design = {0.21232823219548,0.9383773570070979,1.5};
//        System.out.println(antennaArray.is_valid(design));
//        System.out.println(antennaArray.evaluate(design));


//        getRandomValidSolutions(antennaArray);
//        generateValidParticle(antennaArray);

    }


}
