package com.company;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

public class TSP {

    //    private static Integer[] route = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    private static Integer[] route = {1, 2, 3, 4};

    private static final int A = 0;
    private static final int B = 1;
    private static final int C = 2;
    private static final int D = 3;


    private static double[][] graph = {
            {0, 20, 42, 35},
            {20, 0, 30, 34},
            {42, 30, 0, 12},
            {35, 34, 12, 0}
    };

    private static double getCostOfRoute(Integer[] route) throws Exception {

        double[][] csvLookUpTable = getCostOfAllCities();

        double count = 0;
        int original = route[0];
        int last = route[route.length - 1];

        for (int i = 0; i < route.length - 1; i++) {
            int current = route[i];
            int nextE = route[i + 1];

            count += csvLookUpTable[current][nextE];

        }
        count += csvLookUpTable[last][original];


        return count;
    }

    private static int[] generatePermutationsOfRoutes(int start, int[] input) {
        int[] permutations = new int[input.length];

        if (start == input.length) {
            for (int x : input) {
                System.out.print(x);
                permutations[x] = x;
            }
            System.out.println("");
            return permutations;

        }
        for (int i = start; i < input.length; i++) {
            // swap
            int temp = input[i];
            input[i] = input[start];
            input[start] = temp;


            generatePermutationsOfRoutes(start + 1, input);

            int temp2 = input[i];
            input[i] = input[start];
            input[start] = temp2;

        }

        return permutations;
    }


    private static double getCostOfRandomRoutes() throws Exception {

        Collections.shuffle(Arrays.asList(route));

        return getCostOfRoute(route);
    }

    private static void randomSearchCpuTimeBasedTermination(int secondsTimeLimit) throws Exception {

        long limit = (System.currentTimeMillis() / 1000) + secondsTimeLimit;
        long current = System.currentTimeMillis() / 1000;

        ArrayList<Double> bestRoute = new ArrayList<Double>();

        while (current < limit) {
            Collections.shuffle(Arrays.asList(route));
            double firstRoute = getCostOfRoute(route);


//            double secondRoute = getCostOfRoute(route);

            bestRoute.add(firstRoute);
//            if (firstRoute < secondRoute) {
//                bestRoute.add(firstRoute);
//            }
//            System.out.println(firstRoute);


            current = System.currentTimeMillis() / 1000;
//        System.out.println(bestRoute);
            System.out.println(Collections.min(bestRoute));
            System.out.println(Arrays.toString(route));
        }


    }

    private static List<Integer[]> getTwoOptNeighbourhood(Integer[] route) {

        List<Integer[]> twoOptNeighbourhood = new ArrayList<Integer[]>();

        Integer swapped[] = route.clone();

        for (int i = 0; i < route.length; i++)
            for (int j = 0; j < swapped.length; j++) {
                swapped = route.clone();

                if (route[i] != swapped[j]) {

                    int temp = swapped[j];
                    swapped[j] = route[i];
                    swapped[i] = temp;

                    twoOptNeighbourhood.add(swapped);

                }

            }
        System.out.println(Arrays.deepToString(twoOptNeighbourhood.toArray()));
        return twoOptNeighbourhood;
    }

    private static ArrayList<Double> csvProblemInstance() throws Exception {

        String CSV_FILE_PATH = "ulysses16.csv";
        ArrayList<Double> results = new ArrayList<Double>();
//        double[][] results = new double[16][2];
//        double results[] = new double[0];
//        List<String[]> list;

        FileReader fileReader = new FileReader(CSV_FILE_PATH);


        CSVReader csvReader = new CSVReaderBuilder(fileReader)
                .withSkipLines(3)
                .build();

        List<String[]> allData = csvReader.readAll();

        for (String[] row : allData) {
//            results.add(Double.parseDouble(row[0]));
//            for (int i = 0; i < row.length; i++) {
//                results[e][0] = Double.parseDouble(row[1]);
//                results[i][1] = Double.parseDouble(row[2]);
//
//            }
//            results = row[];
//            results.add(Double.parseDouble(row[2]));
            for (String cell : row) {
//                results[i][0] = Double.parseDouble(cell);
                results.add(Double.parseDouble(cell));

//                System.out.println(cell + "\t");
//                System.out.println(cell);
//                System.out.println(row);
//                i++;
            }
//                System.out.println(results[0][0]);
        }
        csvReader.close();

//        System.out.println(results);
        return results;
    }

    private static double[][] getCostOfAllCities() throws Exception {
        ArrayList<Double> results = csvProblemInstance();
        ArrayList<Double> costOfCsv = new ArrayList<Double>();
        double[][] csvLookUpTable = new double[16][16];

//        int counter = 1;
//        pythagorasTheorem();
//        int indexOfLastCity = results.size() / 2;

        for (int i = 0; i < results.size() / 3; i++) {

            double xCityA = results.get((3 * i) + 1);
            double yCityA = results.get((3 * i) + 2);

            for (int e = 0; e < results.size() / 3; e++) {
                double xCityNext = results.get((3 * e) + 1);
                double yCityNext = results.get((3 * e) + 2);

//                counter += 1;

//
//                if (counter == 16) {
////              System.out.println(costOfCsv);
//                    break;
//                }
                double costOfTwoCities = pythagorasTheorem(xCityA, xCityNext, yCityA, yCityNext);
                costOfCsv.add(costOfTwoCities);


//
            }

        }

        int row = 0;
        int counter = 0;
        for (int i = 0; i < costOfCsv.size() / 16; i++) {
//            csvLookUpTable[row][i] = costOfCsv.get(i+1);


            for (int e = 0; e < costOfCsv.size() / 16; e++) {
                csvLookUpTable[i][e] = costOfCsv.get(counter + e);
                row++;


            }
            counter += 16;


        }


        // Seed new data structure with cost of city tours.

//        for (int i = 0; i < costOfCsv.size(); i++) {
////            csvLookUpTable[0][i + 1] = costOfCsv.get(i);
////            csvLookUpTable[i + 1][0] = costOfCsv.get(i);
////            csvLookUpTable[i][i + 1] = costOfCsv.get(i);
////            csvLookUpTable[i][i + 1] = costOfCsv.get(i);
//            for (int e = 0; e < csvLookUpTable.length; e++) {
//                csvLookUpTable[i + 1][i] = costOfCsv.get(i);
////                csvLookUpTable[i + 1][e] = costOfCsv.get(i);
//                for (int o = 0; o < csvLookUpTable.length; o++) {
//                    csvLookUpTable[o][o] = 0;
//                }
//            }
//        }
        // Getting the cost of one particular route where [0][1] being
        // from City A to City B
//        System.out.println(csvLookUpTable[1][1]);

//        System.out.println("The cost of travelling each individual city and then returning home: \n" + costOfCsv);
        return csvLookUpTable;

    }

    private static double pythagorasTheorem(double xCityA, double xCityB, double yCityA, double yCityB) {
        double x = Math.pow((xCityB - xCityA), 2);
        double y = Math.pow((yCityB - yCityA), 2);

        double xAndY = x + y;


        return Math.sqrt(xAndY);
    }

    public static void main(String[] args) throws Exception {

//        System.out.println(getCostOfRandomRoutes());
//         Integer[] route = {0,1,2,3};
//        System.out.println(getCostOfRoute(route));
//        System.out.println(Arrays.toString(route));

//        System.out.println(csvProblemInstance());

//        getCostOfAllCities();
//        double[][] test = getCostOfAllCities();
//        System.out.println(test[3][10]);
//        Integer[] route = {0, 1, 2, 3};
//        System.out.println(getCostOfRandomRoutes());
//        randomSearchCpuTimeBasedTermination(1000);
        getTwoOptNeighbourhood(route);

//        System.out.print(getCostOfRoute(route));
//        getCostOfAllCities();
//        generatePermutationsOfRoutes(0,route);

//        getCostOfRoute();

//        System.out.println(pythagorasTheorem(38.24,39.57,20.42,26.15));

    }


}
