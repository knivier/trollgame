public class bank{
    private double money;
    private int workers;
    private double winBonus;
    
    //creates bank class with input variables
    public bank(double amoney, int aworkers){
        money = amoney;
        workers = aworkers;
        winBonus = 0.001+workers%100;
    }
    
    //creates bank class by default money
    public bank(){
        money = 100;
        workers = 2;
        winBonus = 0.001+workers%100;
    }
    
    //returns money in bank
    public double getMoney(){
        money = Math.round(money);
        return money;
    }
    
    //returns number of workers
    public int getWorkers(){
        return workers;
    }
    
    //adds money to bank
    public void addMoney(double x){
        money +=x;
    }
    
    //removes money to bank
    public void removeMoney(double x){
        money -=x;
    }
    
    //returns the win bonus
    public double getWinBonus(){
        return winBonus;
    }
    
    //when adding workers, recaclulate the win bonus aswell
    public void addWorkers(int y){
        workers +=y;
        winBonus = 0.001+workers%100;
        //recalculate winbonus everytime workers are added
    }
    
    //when removing workers, win bonus recalculated again
    public void removeWorkers(int z){
        workers -=z;
        winBonus = 0.001+workers%100;
        //should only be removed when loosing
    }

    //returns default format for toString implementation
    public String toString(){
        return money + " money " + workers + " workers " + winBonus + " winBonus";
    }
}