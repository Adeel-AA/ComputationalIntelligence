package com.company;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.apache.commons.lang3.ArrayUtils;

import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class TSP {

    //    private static Integer[] route = {5, 8, 7, 14, 3, 9, 10, 4, 11, 15, 6, 12, 0, 1, 13, 2};
//    private static Integer[] route = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    private static Integer[] route = {0, 1, 2, 3};


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

    private static void evolutionaryAlgorithm() throws Exception {

        int generations = 3000;
        // Initialise population
        List<Integer[]> population = new ArrayList<>();
        List<Integer[]> bestTour = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            population.add(getRandomRoute(route));
        }

        System.out.println(Arrays.deepToString(population.toArray()));

//        ArrayList<ArrayList<Integer[]>> population = new ArrayList<>();
        for (int i = 0; i < generations; i++) {

            // Select parents
            List<Integer[]> parents = getParents(population);
            System.out.println(Arrays.deepToString(parents.toArray()));

            // 1 order recombination
            List<Integer[]> children = recombination(parents);

            // Swap mutation
            List<Integer[]> mutations = new ArrayList<>();
            for (int j = 0; j < children.size() ; j++) {
                List<Integer[]> mutant = getTwoOptNeighbourhood(children.get(i));
                List<Integer[]> bestRoute = bestNeighbourhoodStep(Arrays.asList(mutant.get(i)));

            }

            // Survivor selection
            population.clear();
            population.addAll(parents);
            for (int m = 0; m < 5; m++) {
                population.add(getRandomRoute(route));
            }
        }
    }

    private static List<Integer[]> recombination(List<Integer[]> parents) {
        List<Integer[]> children = new ArrayList<>();

        // Compare and sort parents
        Collections.sort(parents, new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] o1, Integer[] o2) {
                int i = 0;
                try {
                    i = (int) (getCostOfRoute(o1) - getCostOfRoute(o2));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return i;
            }
        });

        // Check if parents size isn't even
        if (parents.size() % 2 != 0) {
            // If not even then move the last node from parents to the children pool
            children.add(parents.get(parents.size() - 1));
            parents.remove(parents.size() - 1);

        }

        for (int i = 0; i < parents.size(); i += 2) {
            Random random = new Random();

            Integer[] firstParent = parents.get(i);
            Integer[] secondParent = parents.get(i + 1);

            int size = parents.get(i).length;
            int randomPosition = random.nextInt(size); // -1?
            int insertionPosition = randomPosition;

            int amountToSwap = size / 2;

            Integer[] child = new Integer[size];

            int j = 0;
            int k = 0;
            while (j < amountToSwap) {
                int positionToSwap = firstParent[randomPosition];
                child[insertionPosition] = positionToSwap;
                randomPosition++;
                randomPosition = randomPosition % size;
                insertionPosition = randomPosition;
                j++;
            }

            while (k < amountToSwap) {
                int positionToSwap = secondParent[randomPosition];
                boolean childContainsRandomPosition = Arrays.asList(child).contains(positionToSwap);
                if (!childContainsRandomPosition) {
                    child[insertionPosition] = positionToSwap;
                    insertionPosition++;
                    insertionPosition = insertionPosition % size;
                    k++;
                }
                randomPosition++;
                randomPosition = randomPosition % size;
            }

            children.add(child);


        }
        return children;

    }

    // Tournament selection - pick the best as parent
    private static List<Integer[]> getParents(List<Integer[]> population) throws Exception {
        List<Integer[]> parents = new ArrayList<>();

        // Compare and sort population of best survivors
        Collections.sort(population, new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] o1, Integer[] o2) {
                int i = 0;
                try {
                    i = (int) (getCostOfRoute(o1) - getCostOfRoute(o2));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return i;
            }
        });

        // Check if population size isn't even
        if (population.size() % 2 != 0) {
            // If not even then move the last node from population to the parent pool
            parents.add(population.get(population.size() - 1));
            population.remove(population.size() - 1);
        }
        // Add nodes with the best routes from population to the parent pool
        for (int i = 0; i < population.size(); i += 2) {
            double firstCost = getCostOfRoute(population.get(i));
            double secondCost = getCostOfRoute(population.get(i + 1));
            if (firstCost < secondCost) {
                parents.add(population.get(i));
            } else {
                parents.add(population.get(i + 1));
            }
        }
        return parents;
    }

    private static Integer[] getRandomRoute(Integer[] route) {
//        Integer[] routeTour = route.clone();
        Integer[] randomRoute = route.clone();

        Collections.shuffle(Arrays.asList(randomRoute));


        return randomRoute;
    }

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

//        System.out.println(count);
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

            System.out.println(Collections.min(bestRoute));
            System.out.println(Arrays.toString(route));
        }


    }

    private static void localSearch(Integer[] route, int secondsTimeLimit) throws Exception {

        long limit = (System.currentTimeMillis() / 1000) + secondsTimeLimit;
        long current = System.currentTimeMillis() / 1000;
        ArrayList<Double> bestCost = new ArrayList<>();

        while (current < limit) {
            Collections.shuffle(Arrays.asList(route));

            List<Integer[]> twoOptRoutes = getTwoOptNeighbourhood(route);

            for (int i = 0; i < twoOptRoutes.size(); i++) {
                bestCost.add(bestNeighbourhoodStep(twoOptRoutes.get(i)));
            }

            current = System.currentTimeMillis() / 1000;

            System.out.println(Arrays.toString(route));
            System.out.println(Collections.min(bestCost));
        }


        System.out.println("The lowest cost is: " + Collections.min(bestCost) + " \n With the route: " + Arrays.toString(route));

    }

    private static List<Integer[]> getTwoOptNeighbourhood(Integer[] route) {

        List<Integer[]> twoOptNeighbourhood = new ArrayList<Integer[]>();

        Integer swapped[] = route.clone();

        for (int i = 0; i < route.length; i++) {
            for (int j = 0; j < swapped.length; j++) {
                swapped = route.clone();

                if (route[i] != swapped[j]) {

                    int temp = swapped[j];
                    swapped[j] = route[i];
                    swapped[i] = temp;


                    twoOptNeighbourhood.add(swapped);


                }

            }
        }

        for (int i = 0; i < twoOptNeighbourhood.size() - 1; i++) {

            for (int j = 0; j < twoOptNeighbourhood.size(); j++) {
                if (Arrays.equals(twoOptNeighbourhood.get(i), twoOptNeighbourhood.get(j))) {
                    twoOptNeighbourhood.remove(j);
                }
            }

        }
//        twoOptNeighbourhood.add(route);

//        System.out.println(Arrays.deepToString(twoOptNeighbourhood.toArray()));


        return twoOptNeighbourhood;
    }

    private static double bestNeighbourhoodStep(Integer[] route) throws Exception {
        List<Integer[]> neighbourhood = getTwoOptNeighbourhood(route);
        double shortestRoute = 0.0;
        ArrayList<Double> costOfRoutes = new ArrayList<>();
        List<Integer[]> routes = new ArrayList<>();

        for (int i = 0; i < neighbourhood.size(); i++) {
            costOfRoutes.add(getCostOfRoute(neighbourhood.get(i)));
            routes.add(neighbourhood.get(i));
        }

        shortestRoute = Collections.min(costOfRoutes);

        System.out.println(shortestRoute);
        System.out.println(Arrays.toString(routes.get(routes.size() - 1)));
        return shortestRoute;
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


        for (int i = 0; i < results.size() / 3; i++) {

            double xCityA = results.get((3 * i) + 1);
            double yCityA = results.get((3 * i) + 2);

            for (int e = 0; e < results.size() / 3; e++) {
                double xCityNext = results.get((3 * e) + 1);
                double yCityNext = results.get((3 * e) + 2);


                double costOfTwoCities = pythagorasTheorem(xCityA, xCityNext, yCityA, yCityNext);
                costOfCsv.add(costOfTwoCities);

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

        return csvLookUpTable;

    }

    private static double pythagorasTheorem(double xCityA, double xCityB, double yCityA, double yCityB) {
        double x = Math.pow((xCityB - xCityA), 2);
        double y = Math.pow((yCityB - yCityA), 2);

        double xAndY = x + y;


        return Math.sqrt(xAndY);
    }

    public static void main(String[] args) throws Exception {

        evolutionaryAlgorithm();


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
//        getTwoOptNeighbourhood(route);


//        bestNeighbourhoodStep(route);

//        randomSearchCpuTimeBasedTermination(60);
//        localSearch(route, 5);


//        System.out.print(getCostOfRoute(route));
//        getCostOfAllCities();
//        generatePermutationsOfRoutes(0,route);

//        getCostOfRoute(route);

//        System.out.println(pythagorasTheorem(38.24,39.57,20.42,26.15));

    }


}
