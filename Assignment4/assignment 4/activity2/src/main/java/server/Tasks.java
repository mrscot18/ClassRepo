package server;

import java.util.Random;

public class Tasks {

    private taskings tasks[];

    public Tasks () {
        tasks = new taskings[9];
        for (int i = 0; i < 9; i++) {
            tasks[i] = new taskings();
        }
        tasks[0].tasks = "What is the color of a stop sign?";
        tasks[0].ans = "red";
        tasks[1].tasks = "How many years are in a decade?";
        tasks[1].ans = "10";
        tasks[2].tasks = "What class is this for?";
        tasks[2].ans = "ser 321";
        tasks[3].tasks = "Type 'coolio in all upper case'";
        tasks[3].ans = "COOLIO";
        tasks[4].tasks = "Spell hewllo correctly";
        tasks[4].ans = "hello";
        tasks[5].tasks = "whats the 5th number AFTER 0";
        tasks[5].ans = "5";
        tasks[6].tasks = "Whats the 5th number including 0'";
        tasks[6].ans = "4";
        tasks[7].tasks = "What school do you go to? (abbreviate and lowercase)";
        tasks[7].ans = "asu";
        tasks[8].tasks = "What soft drink is Mcdonalds known for making 'spicy' (use an uppercase for the first letter)? ";
        tasks[8].ans = "Sprite";

    }

    public taskings getTasks () {
        Random random = new Random();
        int i = random.nextInt(9);
        return tasks[i];
    }

}

class taskings {
    String tasks;
    String ans;
}