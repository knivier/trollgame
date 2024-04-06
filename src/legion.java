package src;

public class legion {
    private int soldiers;
    //a legion is a group of soldiers
    
    //creates legion with set num of soldiers
    public legion(int x){
        soldiers = x;
    }
    
    //creates default legion 
    public legion(){
        soldiers = 2;
    }
    
    //returns number of soldiers
    public int getSoldiers(){
        return soldiers;
    }
    
    //adds soldiers to legion
    public void addSoldiers(int x){
        soldiers +=x;
    }
    
    // removes soldiers to legion
    public void removeSoldiers(int x){
        soldiers -=x;
    }
    
    public String toString(){
        return soldiers + " soldiers";
    }
}