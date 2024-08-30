import java.util.ArrayList;
import java.util.Random;


public class Chromosome extends ArrayList<Item> implements Comparable<Chromosome>{
//Logan Current, CS-1181L-07, 2/13/2022
    private static Random rng = new Random();//random used for multple methods

    private final ArrayList<Item> itemSet = new ArrayList<Item>();//this is so the program is able to go through every item in every chromosome that is added as well as use the methods from the class 'Item' this made it easier for me to find specific items a chromosome had and made it easier to use the methods from the Item class
    public Chromosome(){//empty
    }

    public Chromosome(ArrayList<Item> items){//makes new chromosome and adds copies of items to it
        for(Item eachItem : items) {//goes through all items
            double fiftyFifty = rng.nextDouble();//50/50 shot of getting the item as true (the chromosome has the item) or false (the chromosome doesn't have the item)
            Item copyOfItem = new Item(eachItem);//copies the item
            if(fiftyFifty<0.5){//has it 
                copyOfItem.setIncluded(true);
            } else if (fiftyFifty > 0.5){//doesn't have it
                copyOfItem.setIncluded(false);
            }
            this.itemSet.add(copyOfItem);//adds the item to this specific chromosome's set of items
        }
    }	

    public Chromosome crossover(Chromosome other){//makes a offspring from 2 chromosomes
        Chromosome newChild = new Chromosome(itemSet);
        for(int i = 0; i<itemSet.size(); i++){//goes through the amount of items the chromosome has (should always be the same number regardless of true or false)
            double fiftyFifty = rng.nextDouble();//50 / 50 shot of either getting an item from parent 1 or parent 2 
            if(fiftyFifty < 0.5){//gets it from parent 1
                Item itemFromParent1 = this.itemSet.get(i);
                newChild.add(new Item(itemFromParent1));
            } else if (fiftyFifty > 0.5){//gets it from parent 2
                Item itemFromParent2 = other.itemSet.get(i);
                newChild.add(new Item(itemFromParent2));
            }
        }
        return newChild;
    }

    public void mutate(){//chance of changng the included values of the items in the chromosome
        double fiftyFifty = rng.nextDouble();
        for (Item eahcitem : itemSet) {
            //50 / 50 shot of changing a T - > F or F - > T
            if(fiftyFifty < 0.5){//changes it or keeps true
                eahcitem.setIncluded(true);
            } else if (fiftyFifty > 0.5){//changes or keeps it false
                eahcitem.setIncluded(false);
            }
        }
    }

    public int getFitness(){
        double sumOfWeight = 0;
        int sumOfValue = 0;
        int fitness = 0;
        for(Item eachItem : itemSet){//goes through the set of items in the chromosome
            if(eachItem.isIncluded()){//only counts the weight items that the chromosome has (the items with a true included value)
                sumOfWeight += eachItem.getWeight();
                sumOfValue += eachItem.getValue();
            }
        }
        if(sumOfWeight > 10.0){//if the set of items is too heavy then it sets fitness to 0
            fitness = 0;
        } else if(sumOfWeight <= 10.0){//else if less than 10 then it will return all the items in the chromosome's items values combined
            fitness = sumOfValue;
        }
        return fitness;
    }
    /*when using the items from the file 'items.txt' I get the desired answer of $3400 as the best possible chromosome. But when I try to use 'more_items.txt' I get a chromosome with all of it's items set as true as the best possible answer even though the fitness is 0. I couldn't figure it out and was stumped on it the longest out of all of this... I believe it boils down to my compareTo() or getFitness() method. I'm not sure how it would be anything else. Insteaad of going off of the fitness numbers it goes off of the amount of items the chromosome has even if the fitness is equal to 0... Can you please explain or help me understand what I did wrong in the feedback? 
    */
    @Override
    public int compareTo(Chromosome other){//sorts through the list of chromosomes from greatest to least fit
        if(this.getFitness() > other.getFitness()){
            return -1;
        } else if(this.getFitness() < other.getFitness()){
            return 1;
        } else {//default 0 b/c that's the only option left if it bypassed the other 2 if statements, means they are equal
            return 0;
        }
    } 
    @Override
    public String toString() {//returns all items in each chromosome's set that is set to true
        int isTrue = 0;
        StringBuilder bld = new StringBuilder();
        for(Item eachItem : this.itemSet){//goes through the set
            if(eachItem.isIncluded()){//checks if true
                bld.append(eachItem.toString() + " <"+eachItem.isIncluded() +">"+" <"+ getFitness()+">\n");//makes a string with all teh stats of the item
                isTrue++;
            } 
        }
        if(isTrue == 0){//I would so often get a chromosome whose all included value for the items were false, so this shows that the chromosome has nothing with it
            return "This chromosome has no items \n";
        }
        return bld.toString();
        
    }
}