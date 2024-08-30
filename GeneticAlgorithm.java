import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GeneticAlgorithm {//Logan Current, CS-1181L-07, 2/13/2022
    public static ArrayList<Item> readData(String filename) throws FileNotFoundException{//reads through the file and makes an arraylist for all the items in the file
        File file = new File(filename);
        Scanner fileScan = new Scanner(file);
        ArrayList<Item> itemArayList = new ArrayList<Item>();
        while(fileScan.hasNextLine()){//scans the file
            String line = fileScan.nextLine();
            String[] tokens = line.split(", ");
            //splits it all by the space and comma separating it all
            String name = tokens[0];//takes the name from the string aray
            double weight = Double.parseDouble(tokens[1]);//takes the weight from string array
            int value = Integer.parseInt(tokens[2]);//takes the value from the string array
            Item item = new Item(name, weight, value);//makes the corrosponding item
            itemArayList.add(item);//adds it to the list
        }
        fileScan.close();
        return itemArayList;
    }
    public static ArrayList<Chromosome> initializePopulation(ArrayList<Item> items, int populationSize) {
        //makes the population of chromosomes and adds them to the population, as well as gives them items (see constructor in Chromosome class)
        ArrayList<Chromosome> chromosomeArrayList = new ArrayList<Chromosome>();
        for(int i = 0; i < populationSize; i++){
            Chromosome chromosome = new Chromosome(items);//makes a new person with random items from item list
            chromosomeArrayList.add(chromosome);//adds to population
        }
        return chromosomeArrayList;
    }
    public static void main(String[] args) throws 
    FileNotFoundException {
        ArrayList<Chromosome> topTenOfThem = new ArrayList<Chromosome>();//saves the top ten then adds it back, eventually it will save the top ten overall after the 20 times the program loops then print the top 10
        String filename = "items.txt";
        ArrayList<Item> items = readData(filename);
        for (int x = 0; x<1; x++){
            ArrayList<Chromosome> chromosome = initializePopulation(items, 10);//population is 10
            for(Chromosome topTen : topTenOfThem){//adds the top 10 individuals back to the last population
                chromosome.add(topTen);
            }
            topTenOfThem.clear();//clears the list to add the top ten of this generation back to the next one. So should end with 10 individuals just like what I started with
            Collections.shuffle(chromosome);//randomizes the population so it's different people making different kids each time
            int parent1 = 0;//2,4,6,8,10, etc
            int parent2 = 1;//3,5,7,9,11, etc
            int originalSize = chromosome.size();//checks the size of the population so it knows when to stop making kids
            int onlyParents = (chromosome.size() /2); //only makes kids with the orignial parents in the population(so kids don't make other kids)
            for(int i = 0; i< onlyParents; i++){//goes through only parents (in pairs)
                    if (parent1 == originalSize) {//if it tries to make kids with kids it breaks the loop and continues on
                        break;
                    }
                    Chromosome childChromosome = chromosome.get(parent1).crossover(chromosome.get(parent2));//makes the kid
                    parent1 = parent1 + 2;//makes kids in pairs (none can cheat on the other b/c that's bad)
                    parent2 = parent2 + 2;//makes kids in pairs (none can cheat on the other b/c that's bad)
                    chromosome.add(childChromosome);//adds to population
            }
            Collections.shuffle(chromosome);//randomizes the population to mutate
            int percent = ((10*chromosome.size())/100);//10 percent of the population
            for(int i = 0; i < percent; i++){//only mutates 10 percent
                chromosome.get(i).mutate();
            }
            Collections.sort(chromosome);//sorts the population based on fitness of each individual
            for(int i = 0; i < 10; i++){//takes only the top 10 individuals (since the Collections.sort() sorts it by making the fittest to least fit, you only need to take the chromosomes from index 0-9)
                topTenOfThem.add(chromosome.get(i));//adds it to top 10 for future generation
            }
        }
        Collections.sort(topTenOfThem);//sorts based on fitness
        System.out.println("This is the fittest individual: ");
            // System.out.println(topTenOfThem.get(0));//sorts from fit(index 0) to least fit(index 9) so returns index 0
        System.out.println(topTenOfThem.get(0));
    }
}
