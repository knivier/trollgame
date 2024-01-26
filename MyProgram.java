/**
 *The Troll Game
 *Created by Knivier/AG
 *Read the docs for more information on Github
 */
import java.util.Scanner;
public class MyProgram {
    public static void main(String[] args) throws InterruptedException{ //throw for delay
        Scanner input = new Scanner(System.in);
        //start of directions screen
        Thread.sleep(1000);
        boolean nextStep = false;
        boolean devMode = false;
        System.out.println("Welcome to the troll game!");
        System.out.print("Would you like directions? Enter 'yes' or 'no': ");
        do{
            String directions = input.nextLine();
            if(directions.equalsIgnoreCase("yes")) {
                fetchDirections(devMode);
                nextStep = true;
            }
            else if(directions.equalsIgnoreCase("no")){
                nextStep = true;
            }
            else if(directions.equalsIgnoreCase("dev")){
                System.out.println("You have entered developer mode. Fastmode enabled.");
                nextStep = true;
                devMode = true;
            }
            else{
                System.out.print("Invalid entry. Please try again: ");
            }
        }
        while(nextStep == false);
        gap(1);
        //end of directions screen

        //number of days selection
        System.out.println("Now for how many in-game days you want to play for. The more days you have, the more money you can earn.");
        System.out.print("Please enter the number of days you want to play for:  ");
        int days;
        while (true) {
            try {
                days = Integer.parseInt(input.nextLine());
                if (days > 3 && days <= 60) {
                    break;
                } else {
                    System.out.print("Your number of days must be greater than 3 days and less than or equal to 60. Please enter a valid number again: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
        int tempDays = days;
        //end number of days selection

        //start of difficulty + directions screen
        System.out.println("WARNING: It is HIGHLY reccomended you master level 1 before going to any other level ");
        delay(devMode, 500); //Bigger delay so user can read the warning.
        System.out.println("[1] You win most of the time, it's difficult to screw up");
        delay(devMode, 500);
        System.out.println("[2] Max difficulty reccomended, you'll suffer many losses");
        delay(devMode, 500);
        System.out.println("[3] You'll have mental breakdowns, the odds are now against you");
        delay(devMode, 500);
        System.out.println("[4] We won't pay for your therapist");
        delay(devMode, 500);
        System.out.println("[5] Cry");
        delay(devMode, 500);
        System.out.print("What difficulty would you like? Enter the corresponding number: ");
        int difficulty;
        while (true) {
            try {
                difficulty = Integer.parseInt(input.nextLine());
                if (difficulty <=6 && difficulty > 0) {
                    break;
                } else {
                    System.out.println("Invalid selection. Please try again!");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }

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
        delay(devMode, 2000);
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
        delay(devMode, 5000);
        dragon archie = new dragon();
        if(playerLegion.getSoldiers() > archie.getHealth()){
            System.out.println("You won that battle!"); //always win first battle
            gap(2);
            playerBank.addMoney(calculateWinnings(playerBank, archie));
        }
        delay(devMode, 5000);
        gap(2);
        openShop(playerBank, archie, playerLegion, input);
        delay(devMode, 5000);
        //end of first battle and first shop experience

        //start of all battles and shop
        System.out.println("It'll be repetitive from here. You now have " + days + " days left to finish this");
        //repeating battle screen
        int streak=0;
        for(int loop = days; loop !=0; loop--){
            gap(2);
            System.out.println("A new day starts once more. You have " + loop + " days left. $10 has been deposited.");
            playerBank.addMoney(10);
            delay(devMode, 2000);
            gap(2);
            streak += openBattle(playerBank, archie, playerLegion, difficulty, streak);
            delay(devMode, 7500);
            gap(2);
            openShop(playerBank, archie, playerLegion, input);
            delay(devMode, 2000);
            if(streak > 4){
                bounty death = new bounty();
                openBounty(playerBank, playerLegion, difficulty, death, input);
                openShop(playerBank, archie, playerLegion, input);
                streak = 0;
                //check streak and initiate hunt     
        }
        openBattle(playerBank, archie, playerLegion, difficulty, streak); //the final battle
        //end of final battles

        //displaying final results and thank you message
        System.out.println("You have made it to the end. Your trolls have fought the dragon, now it's time for others to try.");
        displayResults(playerBank, playerLegion, difficulty, tempDays, devMode);
        thankYouMessage();
    }
    }//end of main method    

    public static void openBounty(bank playerBank, legion playerLegion, int difficulty, bounty death, Scanner input){
        System.out.println("A bounty has started! ");
        death.calcHoundHealth(playerLegion.getSoldiers());
        System.out.println("A hellhound spawns, towering over any previous dragon you've ever met.");
        System.out.println("You can flee but it's risky. You can also fight him for a massive bonus. If you die, it's game over. What will you do? ");
        System.out.println("If you choose to flee, enter 'flee'. If you choose to fight, enter 'fight': ");
        boolean choiceMade = false;        
        do{
            String choice = input.nextLine();
            if(choice.equalsIgnoreCase("flee")) {
                choiceMade = true;
                int random = (int) (Math.random()*1) + 2;
                if(random == 1){
                    System.out.println("You get away with no losses!");
                }
                else if(random ==2){
                    System.out.println("The hellhound catches you anyways, but you only loose some soldiers and money.");
                    playerBank.removeMoney(1000);
                    int lost = (int) Math.random()*2 + playerLegion.getSoldiers()-1;
                    playerLegion.removeSoldiers(lost);
                }
            }
            else if(choice.equalsIgnoreCase("fight")){
                choiceMade = true;
                System.out.println("You choose to fight, it will take you and your trolls.");
                int random = (int) Math.random()*1 + 2;
                if(random == 1){
                    System.out.println("You win with a massive bonus!");
                    playerBank.addMoney(1000 * playerLegion.getSoldiers());
                    System.out.println("You now have $" + playerBank.getMoney());
                }
                else if(random == 2){
                    System.out.println("GAME OVER. YOU LOST.");
                    System.out.println("All your money ever earned was burned, and you were executed by the hunters.");
                    System.exit(0);
                }
            }
            else{
                System.out.print("Invalid entry. Please try again: ");
            }
        }
        while(!choiceMade);

    }


    /*
     * @param datatype Requires the correct datatype inputs
     * This method opens a new battle with the player and dragon
     * Difficulty is randomly generated
     */
    public static int openBattle(bank playerBank, dragon archie, legion playerLegion, int difficulty, int streak){
        int randomNum = (int)(Math.random() * playerLegion.getSoldiers() + difficulty);
        int winNum= 0;
        System.out.println("Archie has respawned at " + randomNum + " health.");
        System.out.println("You have " + playerLegion.getSoldiers() + " soldiers.");
        archie.setHealth(randomNum);
        if(archie.getHealth() > playerLegion.getSoldiers()){
            System.out.println("You loose!");
            playerBank.removeMoney(playerLegion.getSoldiers()*10);
            System.out.println("You now have $" + playerBank.getMoney());
            winNum = streak-0;
            if(difficulty >= 3){
                System.out.println("Since your difficulty is 3+, soldiers will now be removed!");
                if(difficulty == 5){
                    System.out.println("Since you're level 5, you will also loose bankers!");
                      playerLegion.removeSoldiers(randomNum-1);
                      playerBank.removeWorkers(1);
                }
                else{
                    playerLegion.removeSoldiers(1);
                }
                
            }
        }

        if(archie.getHealth() == playerLegion.getSoldiers()){
            System.out.println("It's up to your soldiers for this one!");
            int winChance = (int)(Math.random()*2);
            if(winChance == 0){
                System.out.println("You loose!");
                playerBank.removeMoney(playerLegion.getSoldiers()*10);
                System.out.println("You now have $" + playerBank.getMoney());
                winNum = streak-0;
            }
            if(winChance == 1){
                System.out.println("You win!");
                playerBank.addMoney(calculateWinnings(playerBank, archie));
                System.out.println("You now have $" + playerBank.getMoney());
                winNum=1;
            }
        }
        if(archie.getHealth() < playerLegion.getSoldiers()){
            System.out.println("You win with no losses!");
            playerBank.addMoney(calculateWinnings(playerBank, archie));
            System.out.println("You now have $" + playerBank.getMoney());
            winNum=2; //bonus streak because battle was easy
        }
        return winNum;
    }
    
    //calculates total winnings with bonus
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
    
    //delays code
    public static void delay(boolean condition, int x) throws InterruptedException{
        if(condition){
            Thread.sleep(0);
        }
        else{
            Thread.sleep(x);
        }
    }

    //shop method to buy trolls
    public static void openShop(bank playerBank, dragon archie, legion playerLegion, Scanner input){
        boolean shopStat=false;
        System.out.println("The shop has opened! ");
        do{
            System.out.print("Would you like to stay inside the shop? ");
            String nextWait = input.nextLine();
            if(nextWait.equalsIgnoreCase("yes")) {
                System.out.println("You may now purchase trolls. Enter 1 for banker or 2 for fighter. Prices:");
                System.out.println("[1] Banker Troll: $110");
                System.out.println("[2] Fighter Troll: $100");
                System.out.println("Your balance: $" + playerBank.getMoney());
                System.out.print("What would you like to buy? ");
                int choice = input.nextInt();
                input.nextLine();
                System.out.print("How many would you like to buy? ");
                int quantity = input.nextInt();
                input.nextLine();
                if(choice==1){
                    int total = quantity * 110;
                    if(total > playerBank.getMoney()){
                        System.out.println("You have been fined $50 for attempting an overdraft. The bank has temporarily closed your account and the shop is now shut down. Proceeding to next battle...");
                        playerBank.removeMoney(50);
                        shopStat = true;
                    } 
                    else{
                        playerBank.removeMoney(total);
                        playerBank.addWorkers(quantity);
                        System.out.println("You have purchased " + quantity + " banker trolls. Your balance is now $" + playerBank.getMoney());
                    }
                }
                if(choice == 2){
                    int total = quantity * 100;
                    if(total > playerBank.getMoney()){
                        System.out.println("You have been fined $50 for attempting an overdraft. The bank has temporarily closed your account and the shop is now shut down. Proceeding to next battle...");
                        playerBank.removeMoney(50);
                        shopStat = true;
                    } 
                    else{
                        playerBank.removeMoney(total);
                        playerLegion.addSoldiers(quantity);
                        System.out.println("You have purchased " + quantity + " fighter trolls. Your balance is now $" + playerBank.getMoney());
                        
                    }
                }
    
            }
            else if(nextWait.equalsIgnoreCase("no")){
                shopStat = true;
            }
            else{
                System.out.println("Error, try again. ");
            }
        }
        while(!shopStat);//if false, keep shop open
    }

    //prints directions if player wants
    public static void fetchDirections(boolean devMode) throws InterruptedException{
        System.out.println("------------------------");
        System.out.println("        DIRECTIONS      ");
        gap(2);
        System.out.println("The directions for the troll game are simple: ");
        System.out.println("Gain as much money as you can.");
        delay(devMode, 2000);
        gap(1);
        System.out.println("The actual task is more difficult. You are the leader of trolls. ");
        System.out.println("You start with $200 and some battle and banker trolls. ");
        delay(devMode, 2000);
        gap(1);
        System.out.println("Banker trolls give you money everytime you battle.");
        System.out.println("The more banker trolls you have, the higher your bonus is.");
        System.out.println("This bonus is a multiplier that is put onto your total amount of money, everytime you win!");
        delay(devMode, 2000);
        gap(1);
        System.out.println("Battle trolls are part of your legion.");
        System.out.println("The more battle trolls you have, the more difficult opponents you face.");
        System.out.println("But the more difficult your opponent is, the more money you also earn!");
        delay(devMode, 2000);
        gap(2);
        System.out.println("Your opponent is an immortal dragon named 'Archie'. ");
        System.out.println("If you have more trolls than Archie's health, you win!");
        System.out.println("If you have less trolls, you loose.");
        System.out.println("If you have the same amount of health, it's a 50/50 shot!");
        delay(devMode, 2000);
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

    public static void displayResults(bank playerBank, legion playerLegion, int difficulty, int tempDays, boolean devMode) throws InterruptedException{
        System.out.println("The results have been called! ");
        System.out.println("The goal was to earn as much money as you can in less time.");
        System.out.println("Let's see if you got a nice medal!");
        System.out.println("You can get a bronze, silver, gold, diamond, imperial diamond, obsidian and imperial obsidian reward.");
        gap(3);
        System.out.print("Your choosen difficulty was: ");
        delay(devMode, 2000);
        System.out.println(difficulty);
        gap(2);
        System.out.print("Your choosen days to battle was: ");
        delay(devMode, 2000);
        System.out.println(tempDays);
        gap(2);
        System.out.print("You earned a total of: $");
        delay(devMode, 2000);
        System.out.println(playerBank.getMoney());
        gap(2);
        System.out.println("Now, it's time for your money earned per day average.");
        System.out.println("You earned: $" + playerBank.getMoney() + " in " + tempDays + " days. Your average $/day was...");
        delay(devMode, 2000);
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
        System.out.println("╭━━━━┳╮ ╱╱╱╱╱╱╭╮╱╱╱╱╱╱╱╱╱╱╱╱╱╱╭━╮╱╱╱╱╱╱╱╱╭╮╱╱╱╱╱╱╱╱╱╱╱╱╱╱╭╮");
        System.out.println("┃╭╮╭╮┃┃╱╱╱╱╱╱╱┃┃╱╱╱╱╱╱╱╱╱╱╱╱╱╱┃╭╯╱╱╱╱╱╱╱╱┃┃╱╱╱╱╱╱╱╱╱╱╱╱╱╱┃┃");
        System.out.println("╰╯┃┃╰┫╰━┳━━┳━╮┃┃╭╮╭╮╱╭┳━━┳╮╭╮╭╯╰┳━━┳━╮╭━━┫┃╭━━┳╮╱╭┳┳━╮╭━━┫┃");
        System.out.println("╱╱┃┃╱┃╭╮┃╭╮┃╭╮┫╰╯╯┃┃╱┃┃╭╮┃┃┃┃╰╮╭┫╭╮┃╭╯┃╭╮┃┃┃╭╮┃┃╱┃┣┫╭╮┫╭╮┣╯");
        System.out.println("╱╱┃┃╱┃┃┃┃╭╮┃┃┃┃╭╮╮┃╰━╯┃╰╯┃╰╯┃╱┃┃┃╰╯┃┃╱┃╰╯┃╰┫╭╮┃╰━╯┃┃┃┃┃╰╯┣╮");
        System.out.println("╱╱╰╯╱╰╯╰┻╯╰┻╯╰┻╯╰╯╰━╮╭┻━━┻━━╯╱╰╯╰━━┻╯╱┃╭━┻━┻╯╰┻━╮╭┻┻╯╰┻━╮┣╯");
        System.out.println("╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╭━╯┃╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱┃┃╱╱╱╱╱╱╭━╯┃╱╱╱╱╭━╯┃");
        System.out.println("╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╰━━╯╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╱╰╯╱╱╱╱╱╱╰━━╯╱╱╱╱╰━━╯");
    }
}