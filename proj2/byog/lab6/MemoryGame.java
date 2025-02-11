package byog.lab6;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            //return;
        }

        //int seed = Integer.parseInt(args[0]);
        int seed = Integer.parseInt("1234");
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        rand = new Random(seed);

    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        String result = "";
        for (int i = 0; i < n; i++) {
            int num = rand.nextInt(26);
            int index = num - 1;
            result += Character.toString(CHARACTERS[index]);
        }
        return result;
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        int midWidth = width / 2;
        int midHeight = height / 2;
        StdDraw.clear();
        StdDraw.clear(Color.black);
        /** 显示 */
        Font smallFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(smallFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.textLeft(1, height - 1, "Round " + round);
        StdDraw.textRight(width - 1, height - 1, ENCOURAGEMENT[rand.nextInt(ENCOURAGEMENT.length)]);
        if (!playerTurn) {
            StdDraw.text(midWidth, height - 1, "Watch!");
        } else {
            StdDraw.text(midWidth, height - 1, "Type!");
        }
        StdDraw.line(0, height - 2, width, height - 2);

        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.text(midWidth, midHeight, s);
        StdDraw.show();

        //TODO: If game is not over, display relevant game information at the top of the screen

    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i++) {
            String letter = letters.substring(i, i + 1);
            drawFrame(letter);
            StdDraw.pause(1000);
            drawFrame("");
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String input = "";
        drawFrame(input);

        while (input.length() < n) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char letter = StdDraw.nextKeyTyped();
            input += String.valueOf(letter);
            drawFrame(input);
        }
        StdDraw.pause(500);
        return input;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        round = 1;
        gameOver = false;
        playerTurn = false;

        //TODO: Establish Game loop
        while (!gameOver) {
            playerTurn = false;
            drawFrame("Round " + round);
            int stringLength = round;
            String targetString = generateRandomString(stringLength);
            flashSequence(targetString);
            playerTurn = true;
            String actual = solicitNCharsInput(targetString.length());
            if (targetString.equals(actual)) {
                round += 1;
            } else {
                drawFrame("Game over! You made it to round: " + round);
                break;
            }
        }
    }

}
