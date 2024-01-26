public class bounty {
    private boolean bountyActive;
    private int houndHealth;

    public bounty(){
        bountyActive = true;        
        houndHealth = 0;
    }

    public void calcHoundHealth(int soldiers){
        houndHealth = (int) Math.random() * soldiers + 5;
    }

    public int getHoundHealth(){
        return houndHealth;
    }

    public boolean getBountyStatus(){
        return bountyActive;
    }
}
