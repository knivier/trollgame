package src;
/**
 *The Troll Game
 *Created by Knivier/AG
 *Read the docs for more information on GitHub
 */
import java.util.Scanner;
public class MyProgram {
    public static int difficulty;
    public static boolean devMode;

    public static void main(String[] args) throws InterruptedException{ //throw for delay
        Scanner input = new Scanner(System.in);
        //start of directions screen
        Thread.sleep(1000);
        boolean nextStep = false;
        boolean rankedMode = false;
        System.out.println("Welcome to the troll game!");
        do{
            System.out.print("Which mode would you prefer? Ranked or Adventure mode? Enter 'ranked' or 'adventure': ");
            String mode = input.nextLine();
            if(mode.equals("ranked")){
                rankedMode = true;
                break;
            }
            else if(mode.equals("adventure")){
                break; //keeps ranked off
            }
            else{
                System.out.println("We're sorry, but your input doesn't match the possible selections. Please try again: ");
            }
        }
        while(true);
        //rank mode setup complete


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
            else if(directions.equalsIgnoreCase("dev")){
                System.out.println("You have entered developer mode. Fast mode enabled.");
                nextStep = true;
                devMode = true;
            }
            else{
                System.out.print("Invalid entry. Please try again: ");
            }
        }
        while(!nextStep);
        gap(1);
        //end of directions screen

        //number of days selection
        System.out.println("Now for how many in-game days you want to play for. The more days you have, the more money you can earn.");
        if(!rankedMode){
            System.out.println("Your minimum days are 10, since you've entered adventure mode. ");
        }
        System.out.print("Please enter the number of days you want to play for:  ");
        int days;
        while (true) {
            try {
                days = Integer.parseInt(input.nextLine());
                if(rankedMode) {
                    if (days > 3 && days <= 20) {
                        System.out.println("You've selected " + days + " days successfully");
                        break;
                    } else {
                        System.out.print("Your number of days must be greater than 3 days and less than or equal to 20. Please enter a valid number again: ");
                    }
                }
                else{
                    if(days>10 && days <=45){
                        System.out.println("You've selected " + days + " days successfully");
                        break;
                    }
                    else{
                        System.out.print("Your number of days must be greater than 10 days and less than or equal to 45. Please enter a valid number again: ");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
        int tempDays = days;
        //end number of days selection

        //start of difficulty + directions screen
        System.out.println("WARNING: It is HIGHLY recommended you master level 1 before going to any other level ");
        delay(500); //Bigger delay so user can read the warning.
        System.out.println("[1] You win most of the time, it's difficult to screw up");
        delay(500);
        System.out.println("[2] Max difficulty recommended, you'll suffer many losses");
        delay(500);
        System.out.println("[3] You'll have mental breakdowns, the odds are now against you");
        delay(500);
        System.out.println("[4] We won't pay for your therapist");
        delay(500);
        System.out.println("[5] Cry");
        delay(500);
        System.out.print("What difficulty would you like? Enter the corresponding number: ");
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
                System.out.println("You have chosen level 1, the easiest level");
                break;
            case 2:
                System.out.println("You have chosen level 2");
                break;
            case 3:
                System.out.println("You have chosen level 3");
                break;
            case 4:
                System.out.println("You have chosen level 4");
                break;
            case 5:
                System.out.println("You have chosen level 5.");
                System.out.println("The creators of the game wish you luck");
                break;
        }
        delay(2000);
        //end of difficulty screen

        //start of first battle and shop experience.    
        bank playerBank = new bank();
        legion playerLegion = new legion(2);
        //first battle
        System.out.println("Welcome to your first battle! A new day starts.");
        gap(2);
        dragon archie = new dragon();
        if(difficulty==1){ //beginner difficulty
            days--; //decrement days left that user initially selected.
            System.out.println("You encounter Archie.");
            System.out.println("You have " + days + " days left to fight him.");
            System.out.println("You send your trolls to fight him.");
            gap(2);
            delay(5000);

            if(playerLegion.getSoldiers() > archie.getHealth()){
                System.out.println("You won that battle!"); //always win first battle
                gap(2);
                playerBank.addMoney(calculateWinnings(playerBank, archie));
            }
            delay( 5000);
            gap(2);
            openShop(playerBank, archie, playerLegion, input);
            delay(5000);
            //end of first battle and first shop experience

            //start of all battles and shop
            System.out.println("It'll be repetitive from here. You now have " + days + " days left to finish this");
        }

        //repeating battle screen
        int streak=0;
        for(int loop = days; loop !=0; loop--){
            gap(2);
            if(difficulty>=3){
                System.out.println("A new day starts. You have " + loop + " days left. $35 has been deposited.");
                playerBank.addMoney(35);
            }
            else{
                System.out.println("A new day starts. You have " + loop + " days left. $10 has been deposited.");
                playerBank.addMoney(10);
            }
            delay(2000);
            gap(2);
            streak += openBattle(playerBank, archie, playerLegion, streak);
            delay(7500);
            gap(2);
            openShop(playerBank, archie, playerLegion, input);
            delay(2000);
            if(rankedMode) { //if ranked, make bounties rarer
                if (streak > 8) {
                    bounty death = new bounty();
                    openBounty(playerBank, playerLegion, death, input);

                    openShop(playerBank, archie, playerLegion, input);
                    streak = 0;
                    //check streak and initiate hunt
                }
            }
            else if(streak > 4) {
                bounty death = new bounty();
                openBounty(playerBank, playerLegion, death, input);
                openShop(playerBank, archie, playerLegion, input);
                streak = 0;
                //check streak and initiate hunt
            }
            //displaying final results and thank you message
        }
        System.out.println("You have made it to the end. Your trolls have fought the dragon, now it's time for others to try.");
        displayResults(playerBank, playerLegion, tempDays);
        thankYouMessage();
    }//end of main method

    public static void openBounty(bank playerBank, legion playerLegion, bounty death, Scanner input){
        System.out.println("A bounty has started! ");
        death.calcHoundHealth(playerLegion.getSoldiers());
        System.out.println("A hellhound spawns, towering over any previous dragon you've ever met.");
        System.out.println("You can flee but it's risky. You can also fight him for a massive bonus. If you die, it's game over. What will you do? ");
        System.out.print("If you choose to flee, enter 'flee'. If you choose to fight, enter 'fight': ");
        boolean choiceMade = false;
        do{
            String choice = input.nextLine();
            if(choice.equalsIgnoreCase("flee")) {
                choiceMade = true;
                double random1 = Math.random();
                int random = (int) (random1 * 2) + 1;
                if(random == 1){
                    System.out.println("You get away with no losses!");
                }
                else if(random ==2){
                    System.out.println("The hellhound catches you anyways, but you only loose some soldiers and money.");
                    System.out.println("You lost $" + difficulty*1000);
                    playerBank.removeMoney(1000*difficulty);
                    int lost = (int) (Math.random() * difficulty) + playerLegion.getSoldiers()-1;
                    playerLegion.removeSoldiers(lost);
                }
            }
            else if(choice.equalsIgnoreCase("fight")){
                choiceMade = true;
                System.out.println("You choose to fight. Good luck!");
                double random1 = Math.random();
                int random = (int) (random1 * 2) + 1;
                if(random == 1){
                    System.out.println("You win with a massive bonus!");
                    playerBank.addMoney(1000 * playerLegion.getSoldiers() +100*difficulty); //difficulty based bonus
                    System.out.println("You now have $" + playerBank.getMoney());
                }
                else if(random == 2){
                    if(difficulty>=3){
                        System.out.println("You lost, and since your difficulty was " + difficulty + ", the game is over.");
                        System.out.println("You were executed, and all your trolls were sent to prison. GAME OVER");
                        System.exit(0);
                    }
                    else{
                        int moneyLost = (int) (Math.random()*playerBank.getMoney())+100; //min 100 max 1k lost
                        System.out.println("You lost $" + moneyLost+(difficulty*100));
                        playerBank.removeMoney(moneyLost+(difficulty*100));
                        break;
                    }
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
    public static int openBattle(bank playerBank, dragon archie, legion playerLegion, int streak){
        int randomNum = (int)(Math.random() * playerLegion.getSoldiers() + difficulty);
        int winNum= 0;
        System.out.println("Archie has respawned at " + randomNum + " health.");
        System.out.println("You have " + playerLegion.getSoldiers() + " soldiers.");
        archie.setHealth(randomNum);
        if(archie.getHealth() > playerLegion.getSoldiers()){
            System.out.println("You loose!");
            playerBank.removeMoney(playerLegion.getSoldiers()*10);
            System.out.println("You now have $" + playerBank.getMoney());
            winNum = streak;
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
                winNum = streak;
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
    public static void delay(int x) throws InterruptedException{
        if(devMode){
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
        int errorCount = 0;
        do {
            try {
                if (errorCount > 10) {
                    System.out.println("Your inputs haven't matched cases for more than 10 attempts.");
                    System.out.println("This may be a result of a development error.");
                    System.out.println("If you are a user and this occurs, please inform a developer on GitHub by opening an issue");
                    System.out.println("Error type: Scanner issue detected. Exiting program, goodbye!");
                    System.exit(0);
                }
                if(playerBank.getMoney() < 100){
                    System.out.println("Your balance is lower than $100. Proceeding to the next battle...");
                    break;

                }

                System.out.println("[1] Buy = Enter the shop to purchase something");
                System.out.println("[2] Balance = retrieves your current valance");
                System.out.println("[3] Exit = Exit the shop");
                System.out.print("Enter the corresponding word or number: ");
                String nextWait = input.nextLine();
                //if nextWait == purchase something
                if (nextWait.equalsIgnoreCase("buy")  || (Integer.parseInt(nextWait) == 1)) {
                    System.out.println("You may now purchase trolls. Enter 1 for banker or 2 for fighter. Prices:");
                    System.out.println("[1] Banker Troll: $110");
                    System.out.println("[2] Fighter Troll: $100");
                    System.out.println("Your balance: $" + playerBank.getMoney());
                    System.out.print("What would you like to buy? ");
                    int choice = input.nextInt();
                    input.nextLine();
                    if (choice > 2 || choice <= 0) {
                        System.out.println("Error, you can't purchase that!");
                        errorCount++;
                        continue;
                    } else {
                        System.out.print("How many would you like to buy? ");
                        int quantity = input.nextInt();
                        input.nextLine();
                        if (choice == 1) {
                            int total = quantity * 110;
                            if (total > playerBank.getMoney()) {
                                System.out.println("You have been fined $50 for attempting an overdraft. The bank has temporarily closed your account and the shop is now shut down. Proceeding to next battle...");
                                playerBank.removeMoney(50);
                                shopStat = true;
                            } else {
                                playerBank.removeMoney(total);
                                playerBank.addWorkers(quantity);
                                System.out.println("You have purchased " + quantity + " banker trolls. Your balance is now $" + playerBank.getMoney());
                            }
                        }
                        if (choice == 2) {
                            int total = quantity * 100;
                            if (total > playerBank.getMoney()) {
                                System.out.println("You have been fined $50 for attempting an overdraft. The bank has temporarily closed your account and the shop is now shut down. Proceeding to next battle...");
                                playerBank.removeMoney(50);
                                shopStat = true;
                            } else {
                                playerBank.removeMoney(total);
                                playerLegion.addSoldiers(quantity);
                                System.out.println("You have purchased " + quantity + " fighter trolls. Your balance is now $" + playerBank.getMoney());

                            }
                        }
                    }
                } // if exit shop
                else if(Integer.parseInt(nextWait) == 3) {
                    shopStat = true;
                    System.out.println("You've exited the shop");
                } //if return bal
                else if (Integer.parseInt(nextWait) == 2) {
                    System.out.println("You currently have $" + playerBank.getMoney());
                } else {
                    System.out.println("Invalid input, please try again! ");
                    errorCount++;
                }

            } catch(NumberFormatException e){
                System.out.println("Invalid input, please try again! ");
                errorCount++;
            }
        }
        while(!shopStat);//if false, keep shop open
    }

    //prints directions if player wants
    public static void fetchDirections() throws InterruptedException{
        System.out.println("------------------------");
        System.out.println("        DIRECTIONS      ");
        gap(2);
        System.out.println("The directions for the troll game are simple: ");
        System.out.println("Gain as much money as you can.");
        delay(2000);
        gap(1);
        System.out.println("The actual task is more difficult. You are the leader of trolls. ");
        System.out.println("You start with $200 and some battle and banker trolls. ");
        delay(2000);
        gap(1);
        System.out.println("Banker trolls give you money everytime you battle.");
        System.out.println("The more banker trolls you have, the higher your bonus is.");
        System.out.println("This bonus is a multiplier that is put onto your total amount of money, everytime you win!");
        delay(2000);
        gap(1);
        System.out.println("Battle trolls are part of your legion.");
        System.out.println("The more battle trolls you have, the more difficult opponents you face.");
        System.out.println("But the more difficult your opponent is, the more money you also earn!");
        delay(2000);
        gap(2);
        System.out.println("Your opponent is an immortal dragon named 'Archie'. ");
        System.out.println("If you have more trolls than Archie's health, you win!");
        System.out.println("If you have less trolls, you loose.");
        System.out.println("If you have the same amount of health, it's a 50/50 shot!");
        delay(2000);
        gap(2);
        System.out.println("Archie's health is random, but the higher difficulty select later, the more difficult he is.");
        System.out.println("You are recommended to start at Level 1 for your first match.");
        gap(1);
        System.out.println("If You are in adventure mode, bounty's are much more often and much more difficult to defeat");
        System.out.println("REMEMBER, THE GOAL IS TO EARN AS MUCH MONEY AS POSSIBLE!");
        System.out.println("Good luck!");
        gap(2);
        System.out.println("      DIRECTIONS END    ");
        System.out.println("------------------------");

    }
    //directions screen method

    /*
    No additional constraints
    Display results given player-bank, legion, difficulty, tempdays for bool and devmode
    throws interrupted exception for delay and organized

     */
    public static void displayResults(bank playerBank, legion playerLegion, int tempDays) throws InterruptedException{
        System.out.println("The results have been called! ");
        System.out.println("The goal was to earn as much money as you can in less time.");
        System.out.println("Let's see if you got a nice medal!");
        System.out.println("You can get a bronze, silver, gold, diamond, imperial diamond, obsidian and imperial obsidian reward.");
        gap(3);
        System.out.print("Your chosen difficulty was: ");
        delay(2000);
        System.out.println(difficulty);
        gap(2);
        System.out.print("Your chosen days to battle was: ");
        delay(2000);
        System.out.println(tempDays);
        gap(2);
        System.out.print("You earned a total of: $");
        delay(2000);
        System.out.println(playerBank.getMoney());
        gap(2);
        System.out.println("Now, it's time for your money earned per day average.");
        System.out.println("You earned: $" + playerBank.getMoney() + " in " + tempDays + " days. Your average $/day was...");
        delay(2000);
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
    /*
    Method only activates at end of run
    No additional constraints -_-
     */
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