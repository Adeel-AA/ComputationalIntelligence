package com.company;

import java.util.Random;

public class PSO {

    private static AntennaArray antennaArray = new AntennaArray(3, 90);


    private static void getRandomValidSolutions(AntennaArray antennaArray) {
        Random r = new Random();
        double rangeMin = 0.0;
        double rangeMax = 1.4;

        double randomValue = rangeMin + (rangeMax + rangeMin) * r.nextDouble();

        double[] antennae = new double[antennaArray.getN_antennae()];
        double lastAntennae = antennae.length - 1;

        for (int i = 0; i < antennae.length ; i++) {
           while (antennae[i] )

               if (antennae[i] + 0.25 < )
        }

        while(){

        }

        System.out.println(antennae.length);
        System.out.println(randomValue);
        antennae[0] = 1.5;
        antennae[1] = 2.0;
        antennae[2] = 1.5;


        System.out.println(antennaArray.evaluate(antennae));
        System.out.println(antennaArray.is_valid(antennae));



//        for (int i = 0; i < antennae.length; i++) {
//
//            while (lastAntennae != )
//        }

    }


    public static void main(String[] args) {

        getRandomValidSolutions(antennaArray);

//        double[][] a = antennaArray.bounds();


    }


}
