public class dragon {
    //how many trolls it can take out, if random number generates 0 for loss
    private int health;
    //how many trolls needed to kill it. for example, a health of 2 means 2 trolls can kill it
    private boolean lifeStat;
    
    //creates the default dragon
    public dragon(){
        health = 1;
        lifeStat = true;
    }
    
    //creates dragon with set health
    public dragon(int y){
        health = y;
    }
    
    //gets life stat (not currently in use)
    public boolean getLifeStat(){
        //returns true if alive, false if not. 
        return lifeStat;
    }
    
    //returns health of dragon
    public int getHealth(){
        return health;
    }
    
    //sets dragon health
    public void setHealth(int x){
        health = x;
    }
    
    public String toString(){
        return health + " health";
    }
}