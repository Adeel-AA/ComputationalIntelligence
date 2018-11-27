package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PSO {

    private static AntennaArray antennaArray = new AntennaArray(3, 90);


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

    public static PSOParticle generateValidParticle(AntennaArray antennaArray) {
        Random random = new Random();
        PSOParticle newParticle = null;
        boolean complete = false;

        double[] position = new double[antennaArray.getN_antennae()];
        double[] velocity = new double[antennaArray.getN_antennae()];
        int antennaSize = antennaArray.getN_antennae();
        double lastAntennae = antennaArray.getN_antennae() / 2.0;

        while (!complete) {
            for (int i = 0; i < antennaSize; i++) {
                position[i] = lastAntennae * random.nextDouble();
                velocity[i] = Math.abs(random.nextDouble() - position[i] / 2);
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

        double[][] bounds = antennaArray.bounds();
        ArrayList<PSOParticle> psoParticles = new ArrayList<>();
//            double[] secondFeasiblePosition = getRandomValidSolutions(antennaArray);

        for (int i = 0; i < noOfParticles; i++) {
            PSOParticle particle = generateValidParticle(antennaArray);
            psoParticles.add(particle);

            double evaluateParticle = antennaArray.evaluate(particle.getCurrentPosition());
            if(globalBest == 0){

            }
        }
//            particles[i][1] = secondFeasiblePosition[i] - particles[i][0] / 2;


        // Set velocity

        // Initialise population

////        double[][] particles = new double[noOfParticles][3];
//        for (int i = 0; i < particles.length; i++) {
//
//            for (int j = 0; j < particles.length; j++) {
//                double[] validParticles = getRandomValidSolutions(antennaArray);
//                particles[i][0] = validParticles[j];
////            particles[particles.length - 1][0] = antennaArray.getN_antennae() / 2.0;
//
//            }


//            // Set personal best
//            particles[i][2] = particles[i][0];
//        }

//            if ((particles[i][0] - particles[i + 1][0] >= 0.25 || particles[i + 1][0] - particles[i][0] >= 0.25) && antennaArray.is_valid(particles[i])) {
//
//                particles[i][0] = randomValue;
//
//            }
//        globalBest[particles.length - 1] = antennaArray.getN_antennae() / 2.0;

//        while (current < limit) {
//            for (int j = 0; j < particles.length - 1; j++) {
//                if (particles[j][2] >= particles[j + 1][2]) {
//                    globalBest = particles[j][2];
//                }
//
//                for (int i = 0; i < particles.length - 1; i++) {
//                    // Update global best
//
//                    double rangeMin = bounds[i][0];
//                    double rangeMax = bounds[i][1];
//                    double randomValue = rangeMin + (rangeMax + rangeMin) * r.nextDouble();
//
//
//                    particles[i][0] = particles[i][1];
//
//                    if (antennaArray.evaluate(particles[i]) > globalBest) {
//                        particles[i][2] = antennaArray.evaluate(particles[i]);
//                    }
//
//                    if (antennaArray.evaluate(particles[i]) > particles[i][2]) {
//                        particles[i][2] = antennaArray.evaluate(particles[i]);
//                    }
//
//                    if ((particles[i][0] - particles[i + 1][0] >= 0.25 || particles[i + 1][0] - particles[i][0] >= 0.25) && antennaArray.is_valid(particles[i])) {
////
//                        if (particles[i][0] < particles[i][2]) {
//                            particles[i][2] = particles[i][2];
//                        }
////                particles[i][0] = randomValue;
//                        System.out.println("Is valid method: " + antennaArray.is_valid(particles[i]));
//
//                    }
//
//                }
//
//            }
//            current = System.currentTimeMillis() / 1000;
//
//        }

//        while (current < limit) {
//            for (int i = 0; i < particles.length - 1; i++) {
//
//                particles[i][0] = randomValue;
//
//
//                for (int j = 0; j < particles.length - 1; j++) {
//
//                    if ((particles[j][0] - particles[j + 1][0] >= 0.25 || particles[j + 1][0] - particles[j][0] >= 0.25) && antennaArray.is_valid(particles[j])) {
//
//                        System.out.println(particles[j] + " at position: " + j);
//
////                        if (antennaArray.evaluate(particles[j]) >= particlesPersonalBest[j]) {
////                            particlesPersonalBest[j] = antennaArray.evaluate(particles);
//////                            if (antennaArray.evaluate(particles) > globalBest) {
//////                                globalBest = antennaArray.evaluate(particles);
//////                                System.out.println("Global best updated: " + globalBest);
//////                            }
////                        }
//
//                    }
//
//
//                }
//
//            }
//            current = System.currentTimeMillis() / 1000;
//
//        }

//        System.out.println("Global best is: " + Arrays.toString(globalBest));
//        Arrays.sort(particlesPersonalBest);
//        System.out.println("Personal best is : " + particlesPersonalBest[0]);


//        System.out.println("The last antennae is: " + Arrays.toString(particles[particles.length - 1]));
//        System.out.println("Length of antenna is: " + particles.length);
//        System.out.println("The global best peak SSL is: " + antennaArray.evaluate(particles));
//        System.out.println("The bounds are: " + Arrays.deepToString(bounds));
//        System.out.println("Antennae : " + Arrays.deepToString(particles));
    }


    public static void main(String[] args) {

//        getRandomValidSolutions(antennaArray);

//        particleSwarmOptimisation(antennaArray, 10, 20);
//        double[][] a = antennaArray.bounds();

        generateValidParticle(antennaArray);

    }


}
