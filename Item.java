public class Item {//Logan Current, CS-1181L-07, 2/13/2022
    private final String name;
    
    private final double weight;

    private final int value;

    private boolean included;
    
    public Item(String name, double weight, int value){ //creates a new item
    this.name = name;
    this.weight = weight;
    this.value = value;
    this.included = false;
    }
    
    public Item(Item other) { //copies an item 
        this.name = other.name;
        this.weight = other.weight;
        this.value = other.value;
        this.included = other.included;
    }

    public double getWeight(){//returns the weight of the item
        return weight;
    }

    public int getValue(){//returns the value of the item
        return value;
    }

    public boolean isIncluded(){ //returns field of item (T / F)
        return included;
    } 
    
    
    public void setIncluded(boolean included){ //changes the included field to whatever it is set to
    this.included = included;
    }
    
    public String toString() { //returns all the fields for the item
        return "<"+name+">"+"(<"+weight+"> lbs, $<"+value+">)";
        
    }
}