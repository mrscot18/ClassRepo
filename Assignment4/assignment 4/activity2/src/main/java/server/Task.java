package server;

import java.util.Random;

public class Task {

    private taskings tasks[];

    public Task () {
        tasks = new taskings[9];
        for (int i = 0; i < 9; i++) {
            tasks[i] = new taskings();
        }
        tasks[0].t = "What is the color of a stop sign?";
        tasks[0].ans = "red";
        tasks[1].t = "How many years are in a decade?";
        tasks[1].ans = "10";
        tasks[2].t = "What class is this for?";
        tasks[2].ans = "ser 321";
        tasks[3].t = "Type 'coolio'";
        tasks[3].ans = "coolio";
        tasks[4].t = "Spell hewllo correctly";
        tasks[4].ans = "hello";
        tasks[5].t = "whats the 5th number AFTER 0";
        tasks[5].ans = "5";
        tasks[6].t = "Whats the 5th number including 0'";
        tasks[6].ans = "4";
        tasks[7].t = "What school do you go to? (abbreviate and lowercase)";
        tasks[7].ans = "asu";
        tasks[8].t = "What soft drink is Mcdonalds known for making 'spicy' ";
        tasks[8].ans = "Sprite";

    }

    public taskings getTask () {
        Random random = new Random();
        int i = random.nextInt(9);
        return tasks[i];
    }

}

class taskings {
    String t;
    String ans;
}