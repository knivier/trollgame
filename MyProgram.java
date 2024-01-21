/**
 * The Troll Game
 * This is the main class that controls the game
 * All game functions are controlled here
 * CompSci MidTerm Project
 * Date 01/19/2024
 * @author Agniva
 * @version 1.0
*/
import java.util.Scanner;
public class MyProgram {
    public static void main(String[] args) throws InterruptedException{ //throw for delay
    Scanner input = new Scanner(System.in);
    
    
    //start of directions screen
    Thread.sleep(1000);
    boolean nextStep = false;
    System.out.println("Welcome to the troll game!");
    System.out.print("Would you like directions? Enter 'yes' or 'no': ");
    do{
        String directions = input.nextLine();
        if(directions.equalsIgnoreCase("yes")) {
            fetchDirections();
            nextStep = true;
        }
        else if(directions.equalsIgnoreCase("no")){
            nextStep = true;
        }
        else{
            System.out.println("Invalid entry. Please try again: ");
        }
    }
    while(nextStep == false);
    gap(1);
    //end of directions screen
    
    
    //number of days selection
    System.out.print("Please enter the number of days you want to play for:  ");
    int days = input.nextInt();
    int tempDays = days;
    while((days <=3) || (days > 60)){
        System.out.print("Your number of days must be greater than 3 days and less than 60. Please enter a valid number again");
        days = input.nextInt();
    }
    Thread.sleep(1000);
    gap(1);
    //end number of days selection
    

    //start of difficulty + directions screen
    System.out.println("WARNING: It is HIGHLY reccomended you master level 1 before going to any other level.");
    Thread.sleep(500); //Bigger delay so user can read the warning.
    System.out.println("[1] You win most of the time, it's difficult to screw up");
    Thread.sleep(500);
    System.out.println("[2] Max difficulty reccomended, you'll suffer many losses");
    Thread.sleep(500);
    System.out.println("[3] You'll have mental breakdowns, the odds are now against you");
    Thread.sleep(500);
    System.out.println("[4] We won't pay for your therapist");
    Thread.sleep(500);
    System.out.println("[5] Cry");
    Thread.sleep(500);
    System.out.print("What difficulty would you like? Enter the corresponding number: ");
    int difficulty = input.nextInt();
    switch (difficulty) {
        case 1:
            System.out.println("You have choosen level 1, the easiest level");
            break;
        case 2:
            System.out.println("You have choosen level 2");
            break;
        case 3:
            System.out.println("You have choosen level 3");
            break;
        case 4:
            System.out.println("You have choosen level 4");
            break;
        case 5:
            System.out.println("You have choosen level 5.");
            System.out.println("The creators of the game wish you luck");
            break;
    }
    //end of difficulty screen
    
    
    //start of first battle and shop experience.    
    bank playerBank = new bank();
    legion playerLegion = new legion(2);
    //first battle
    System.out.println("Welcome to your first battle! A new day starts.");
    gap(2);
    days--; //decrement days left that user initially selected.
    System.out.println("You encounter Archie.");
    System.out.println("You have " + days + " days left to fight him.");
    System.out.println("You send your trolls to fight him.");
    gap(2);
    Thread.sleep(5000);
    dragon archie = new dragon();
    if(playerLegion.getSoldiers() > archie.getHealth()){
        System.out.println("You won that battle!"); //always win first battle
        gap(2);
        playerBank.addMoney(calculateWinnings(playerBank, archie));
    }
    Thread.sleep(5000);
    gap(2);
    openShop(playerBank, archie, playerLegion, input);
    Thread.sleep(5000);
    //end of first battle and first shop experience
    
    
    //start of all battles and shop
    System.out.println("It'll be repetitive from here. You now have " + days + " days left to finish this");
    //repeating battle screen
    for(int loop = days; loop !=0; loop--){
        gap(2);
        System.out.println("A new day starts once more. You have " + loop + " days left. $10 has been deposited.");
        playerBank.addMoney(10);
        Thread.sleep(2000);
        gap(2);
        openBattle(playerBank, archie, playerLegion, difficulty);
        Thread.sleep(7500);
        gap(2);
        openShop(playerBank, archie, playerLegion, input);
        Thread.sleep(5000);
    }
    openBattle(playerBank, archie, playerLegion, difficulty); //the final battle
    //end of final battles
    
    //displaying final results and thank you message
    System.out.println("You have made it to the end. Your trolls have fought the dragon, now it's time for others to try.");
    displayResults(playerBank, playerLegion, difficulty, tempDays);
    thankYouMessage();
  }//end of main method    
    
    
    public static void openBattle(bank playerBank, dragon archie, legion playerLegion, int difficulty){
        int randomNum = (int)(Math.random() * playerLegion.getSoldiers() + difficulty);
        System.out.println("Archie has respawned at " + randomNum + " health.");
        System.out.println("You have " + playerLegion.getSoldiers() + " soldiers.");
        archie.setHealth(randomNum);
        if(archie.getHealth() > playerLegion.getSoldiers()){
            System.out.println("You loose!");
            playerBank.removeMoney(playerLegion.getSoldiers()*10);
            System.out.println("You now have $" + playerBank.getMoney());
            //remove 1 or 2 soldiers
        }
        if(archie.getHealth() == playerLegion.getSoldiers()){
            System.out.println("It's up to your soldiers for this one!");
            int winChance = (int)(Math.random()*2);
            if(winChance == 0){
                System.out.println("You loose!");
                playerBank.removeMoney(playerLegion.getSoldiers()*10);
                System.out.println("You now have $" + playerBank.getMoney());
            }
            if(winChance == 1){
                System.out.println("You win!");
                playerBank.addMoney(calculateWinnings(playerBank, archie));
                System.out.println("You now have $" + playerBank.getMoney());
            }
        }
            if(archie.getHealth() < playerLegion.getSoldiers()){
                System.out.println("You win with no losses!");
                playerBank.addMoney(calculateWinnings(playerBank, archie));
                System.out.println("You now have $" + playerBank.getMoney());
            }
    }
    
    
    //calculates your total winnings with bonus included    
    public static double calculateWinnings(bank playerBank, dragon archie){
        double winnings = archie.getHealth() * 50;
        System.out.println("You got $" + winnings + " from that battle.");
        winnings *=playerBank.getWinBonus();
        System.out.println("Your win bonus makes that $" + Math.round(winnings));
        return winnings;
    }
    
    
    //gaps your terminal
    public static void gap(int x){
        for(int i=0; i!=x; i++){
           System.out.println();
        }
    }
    
    
    //shop method to buy trolls
    public static void openShop(bank playerBank, dragon archie, legion playerLegion, Scanner input){
        if(playerBank.getMoney() > 100){
            System.out.println("You may now purchase trolls. Enter 1 for banker or 2 for fighter. Enter any other number to skip. Prices:");
            System.out.println("[1] Banker Troll: $110");
            System.out.println("[2] Fighter Troll: $100");
            System.out.print("What would you like to buy? ");
            int choice = input.nextInt();
            if((choice != 1) && (choice !=2)){
                System.out.println("Exiting shop!");
            }
            else{
                
            System.out.print("How many would you like to buy? ");
            int quantity = input.nextInt();
            //if troll is selected
            if(choice == 1){
                int total = quantity * 110;
                if(total > playerBank.getMoney()){
                    System.out.println("You have attempted buying something that exceeds your wallet. The bank has forclosed your account and the shop is now shut down. Proceeding to next battle...");
                } 
                else{
                    playerBank.removeMoney(total);
                    playerBank.addWorkers(quantity);
                    System.out.println("You have purchased " + quantity + " banker trolls. Your balance is now $" + playerBank.getMoney());
                    System.out.println("You now have " + playerBank.getWorkers() + " banking trolls and " + playerLegion.getSoldiers() + " fighting trolls.");
                }
            }
            else if(choice == 2){
                int total = quantity * 100;
                if(total > playerBank.getMoney()){
                    System.out.println("You have attempted buying something that exceeds your wallet. The bank has forclosed your account and the shop is now shut down. Proceeding to next battle...");
                }
                else{
                    playerBank.removeMoney(total);
                    playerLegion.addSoldiers(quantity);
                    System.out.println("You have purchased " + quantity + " soldier trolls. Your balance is now $" + playerBank.getMoney());
                    System.out.println("You now have " + playerBank.getWorkers() + " banking trolls and " + playerLegion.getSoldiers() + " fighting trolls.");
                }
            }
        }
    }
        else{
            System.out.println("You aren't eligible for the shop. Moving onto the next battle.");
        }
    }
    
    
    //prints directions if player wants
    public static void fetchDirections() throws InterruptedException{
        System.out.println("------------------------");
        System.out.println("        DIRECTIONS      ");
        gap(2);
        System.out.println("The directions for the troll game are simple: ");
        System.out.println("Gain as much money as you can.");
        Thread.sleep(1000);
        gap(1);
        System.out.println("The actual task is more difficult. You are the leader of trolls. ");
        System.out.println("You start with $200 and some battle and banker trolls. ");
        Thread.sleep(2000);
        gap(1);
        System.out.println("Banker trolls give you money everytime you battle.");
        System.out.println("The more banker trolls you have, the higher your bonus is.");
        System.out.println("This bonus is a multiplier that is put onto your total amount of money, everytime you win!");
        Thread.sleep(5000);
        gap(1);
        System.out.println("Battle trolls are part of your legion.");
        System.out.println("The more battle trolls you have, the more difficult opponents you face.");
        System.out.println("But the more difficult your opponent is, the more money you also earn!");
        Thread.sleep(5000);
        gap(2);
        System.out.println("Your opponent is an immortal dragon named 'Archie'. ");
        System.out.println("If you have more trolls than Archie's health, you win!");
        System.out.println("If you have less trolls, you loose.");
        System.out.println("If you have the same amount of health, it's a 50/50 shot!");
        Thread.sleep(5000);
        gap(2);
        System.out.println("Archie's health is random, but the higher difficulty select later, the more difficult he is.");
        System.out.println("You are reccomended to start at Level 1 for your first match.");
        gap(1);
        System.out.println("REMEMBER, THE GOAL IS TO EARN AS MUCH MONEY AS POSSIBLE!");
        System.out.println("Good luck!");
        gap(2);
        System.out.println("      DIRECTIONS END    ");
        System.out.println("------------------------");

        
        
    }
    //directions screen method
   
   public static void displayResults(bank playerBank, legion playerLegion, int difficulty, int tempDays) throws InterruptedException{
       System.out.println("The results have been called! ");
       System.out.println("The goal was to earn as much money as you can in less time.");
       System.out.println("Let's see if you got a nice medal!");
       System.out.println("You can get a bronze, silver, gold, diamond, imperial diamond, obsidian and imperial obsidian reward.");
       gap(3);
       System.out.print("Your choosen difficulty was: ");
       Thread.sleep(2000);
       System.out.println(difficulty);
       gap(2);
       System.out.print("Your choosen days to battle was: ");
       Thread.sleep(2000);
       System.out.println(tempDays);
       gap(2);
       System.out.print("You earned a total of: $");
       Thread.sleep(2000);
       System.out.println(playerBank.getMoney());
       gap(2);
       System.out.println("Now, it's time for your money earned per day average.");
       System.out.println("You earned: $" + playerBank.getMoney() + " in " + tempDays + " days. Your average $/day was...");
       Thread.sleep(2000);
       double dailyAverage = playerBank.getMoney() / tempDays;
       System.out.println("$" + dailyAverage + " per day average!");
       if(dailyAverage < 100){
           System.out.println("You earned a BRONZE award! Rank 1/6");
       }
       if((dailyAverage > 100) && (dailyAverage < 200)){
           System.out.println("You earned a SILVER award! Rank 2/6");
       }
       if((dailyAverage > 200) && (dailyAverage < 500)){
           System.out.println("You earned a DIAMOND award! Rank 3/6");
       }
       if((dailyAverage > 500) && (dailyAverage < 1000)){
           System.out.println("You earned an IMPERIAL DIAMOND award! Rank 4/6");
       }
       if((dailyAverage > 1000) && (dailyAverage < 2000)){
           System.out.println("You earned an OBSIDIAN award! Rank 5/6 ");
       }
       if(dailyAverage > 2000){
            System.out.println("You earned an IMPERIAL OBSIDIAN award! You have earned the highest level possible! Rank 6/6!");
            System.out.println(" CONGRATULATIONS ");
            System.out.println(" _______________ ");
            System.out.println("|@@@@|  A  |####|");
            System.out.println("|@@@@|  W  |####|");
            System.out.println("|@@@@|  A  |####|");
            System.out.println("|@@@@|  R  |####|");
            System.out.println(" |@@@|  D  |###| ");
            System.out.println("  `@@|_____|##'");    
            System.out.println("       (O)");
            System.out.println("   .-'''''-.");
            System.out.println("  .'  * * *  `.");
            System.out.println(" :  *       *  :");
            System.out.println(": ~ IMPERIAL ~ :");
            System.out.println(": ~ OBSIDIAN ~ :");
            System.out.println(" :  *       *  :");
            System.out.println("  `.  * * *  .'");
            System.out.println("    `-.....-'");
       }
       
   }
   
   public static void thankYouMessage(){
        System.out.println("╭━━━━┳╮╱╱╱╱╱╱╱╭╮╱╱╱╱╱╱╱╱╱╱╱╱╱╱╭━╮╱╱╱╱╱╱╱╱╭╮╱╱╱╱╱╱╱╱╱╱╱╱╱╱╭╮");
        System.out.println("┃╭╮╭╮┃┃╱╱╱╱╱╱╱┃┃╱╱╱╱╱╱╱╱╱╱╱╱╱╱┃╭╯╱╱╱╱╱╱╱╱┃┃╱╱╱╱╱╱╱╱╱╱╱╱╱╱┃┃");
        System.out.println("╰╯┃┃╰┫╰━┳━━┳━╮┃┃╭╮╭╮╱╭┳━━┳╮╭╮╭╯╰┳━━┳━╮╭━━┫┃╭━━┳╮╱╭┳┳━╮╭━━┫┃");
        System.out.println("╱╱┃┃╱┃╭╮┃╭╮┃╭╮┫╰╯╯┃┃╱┃┃╭╮┃┃┃┃╰╮╭┫╭╮┃╭╯┃╭╮┃┃┃╭╮┃┃╱┃┣┫╭╮┫╭╮┣╯");
        System.out.println("╱╱┃┃╱┃┃┃┃╭╮┃┃┃┃╭╮╮┃╰━╯┃╰╯┃╰╯┃╱┃┃┃╰╯┃┃╱┃╰╯┃╰┫╭╮┃╰━╯┃┃┃┃┃╰╯┣╮");
        System.out.println("╱╱╰╯╱╰╯╰┻╯╰┻╯╰┻╯╰╯╰━╮╭┻━━┻━━╯╱╰╯╰━━┻╯╱┃╭━┻━┻╯╰┻━╮╭┻┻╯╰┻━╮┣╯");
        System.out.println("╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╭━╯┃╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱┃┃╱╱╱╱╱╱╭━╯┃╱╱╱╱╭━╯┃");
        System.out.println("╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╰━━╯╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╰╯╱╱╱╱╱╱╰━━╯╱╱╱╱╰━━╯");
   }
}