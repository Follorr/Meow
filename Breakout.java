package com.shpp.p2p.cs.aesaulenko.assignment4;

import acm.graphics.*;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

public class Breakout extends WindowProgram {
    // Global constants
    /** Width and height of application window in pixels */
    public static final int APPLICATION_WIDTH = 1400;
    public static final int APPLICATION_HEIGHT = 1000;

    /** Text that will show up if you win */
    private static final String WIN_TEXT = "You win!";

    /** Text that will show up if you lose */
    private static final String LOSE_TEXT = "You lose!";

    /** Text that will show up with your score */
    private static final String SCORE_TEXT = "Your score is ";

    /** Text that will show up before new game starts (after already ended game) */
    private static final String NEW_GAME_WARNING = "Starting new game";

    /** Font size of a win label */
    private static final int BIG_FONT_SIZE = 100;

    /** Font size for a score label */
    private static final int MEDIUM_FONT_SIZE = 40;

    /** Dimensions of the paddle */
    private static final int RACKET_WIDTH = 100;
    private static final int RACKET_HEIGHT = 10;

    /** Offset of the paddle up from the bottom */
    private static final int RACKET_Y_OFFSET = 30;

    /** Offset of the pointer, needed to center pointer on racket */
    private static final int POINTER_OFFSET = 25;

    /** Number of bricks per row */
    private static final int BRICKS_PER_ROW_AMOUNT = 10;

    /** Number of rows of bricks */
    private static final int BRICK_ROWS_AMOUNT = 10;

    /** Separation between bricks */
    private static final int BRICK_SEP = 3;

    /** Width of a brick <p>
     * Technically not a constants, but treated as one after adjustment for window size */
    private static double BRICK_WIDTH = ((double) APPLICATION_WIDTH - (BRICKS_PER_ROW_AMOUNT - 1) * BRICK_SEP) / BRICKS_PER_ROW_AMOUNT;

    /** Height of a brick */
    private static final int BRICK_HEIGHT = 8;

    /** Radius of the ball in pixels */
    private static final int BALL_RADIUS = 10;

    /** Thickness of an outside wall */
    private static final int WALL_THICKNESS = 10;

    /** Offset of the top brick row from the top */
    private static final int BRICK_Y_OFFSET = 70;

    /** Basic score given for one brick without multiplier for a row*/
    private static final int BASIC_BRICK_SCORE = 10;

    /** Number of lives */
    private static final int LIVES_AMOUNT = 3;

    /** How much will change speed in interactions with racket */
    private static final double SPEED_MULTIPLIER = 0.1;

    /** Coordinates of a ball <p>
     * Technically not a constants, but treated as one after adjustment for window size */
    private static double BALL_START_POS_X = (double) APPLICATION_WIDTH / 2;
    private static double BALL_START_POS_Y = (double) APPLICATION_HEIGHT / 2;

    /** How many times will ball bleep when spawn */
    private static final int BALL_BLEEP_AMOUNT = 3;

    /** For how much will last one bleep */
    private static final int BALL_BLEEP_PAUSE = 200;

    /** How long it will take to make every brick row at the start */
    private static final int BRICK_ROW_CREATE_PAUSE = 100;

    /** How long it will take to remove every brick row at the end */
    private static final int BRICK_ROW_REMOVE_PAUSE = 200;

    /** Pause before win animation is shown*/
    private static final int WIN_PAUSE = 600;

    /** Pause before lose animation is shown*/
    private static final int LOSE_PAUSE = 200;

    /** Pause before starting new game */
    private static final int NEW_GAME_PAUSE = 3000;

    /** Warns of new game starting */
    private static final int NEW_GAME_WARNING_PAUSE = 1000;

    /** Pause for newGame label animation */
    private static final int MID_NEW_GAME_WARNING_PAUSE = 200;

    /** Dots in newGame label animation */
    private static final int NEW_GAME_WARNING_DOTS_AMOUNT = 3;

    /** How many times will score bleep */
    private static final int SCORE_BLEEP_AMOUNT = 3;

    /** For how much will last one bleep */
    private static final int SCORE_BLEEP_PAUSE = 300;

    /** Start counting points faster after this amount of counted points */
    private static final int SCORE_SPEEDUP_THRESHOLD = 500;

    /** How much to speedup */
    private static final int SCORE_SPEEDUP_TIMES = 2;

    /** Gaps between lives visualisation */
    private static final int VISUAL_LIVES_OFFSET = 10;

    /** Limits speed from crazy values (for X) */
    private static final double SPEED_LIMIT_X = 2;

    /** Window height at which Y speed to window dependency modificator will be 1 */
    private static final int REFERENCE_SCREEN_HEIGHT = 600;

    /** How much Y speed depended on X speed */
    private static final double Y_SPEED_TO_X_SPEED_DEPENDENCY = 0.4;

    /** Modify Y speed depending on window height <p>
     * Technically not a constant, but treated as one after adjustment for window size */
    private static double Y_SPEED_TO_WINDOW_HEIGHT_DEPENDENCY = (double) APPLICATION_HEIGHT / REFERENCE_SCREEN_HEIGHT;

    /** Limits speed from crazy values (for Y) <p>
     * Technically not a constant, but treated as one after adjustment for window size */
    private static double SPEED_LIMIT_Y = 1 * Y_SPEED_TO_WINDOW_HEIGHT_DEPENDENCY;

    /** Basic speed of a ball on a Y axis */
    private static final double BALL_BASE_SPEED_Y = 0.3;

    /** Sets a color for a background */
    private static final Color BACKGROUND_COLOR = new Color(0, 0, 0);

    /** Sets a color for the ball */
    private static final Color BALL_COLOR = new Color(255, 255, 255);

    /** Sets a color for the racket */
    private static final Color RACKET_COLOR = new Color(255, 255, 255);

    /** Sets a color for all text in a game */
    private static final Color TEXT_COLOR = new Color(255, 255, 255);

    /** Color of the bricks
     * Put a number of a current row of that brick */
    private static final Color[] BRICK_COLOR = new Color[BRICK_ROWS_AMOUNT];


    // Global variables

    /** Game score */
    private static int score = 0;

    /** Bricks left in a game */
    private static int bricksLeft = BRICK_ROWS_AMOUNT * BRICKS_PER_ROW_AMOUNT;

    /** Number of turns left */
    private static int livesLeft = LIVES_AMOUNT;

    /** Sets a window size */
    private static double screenWidth;
    private static double screenHeight;

    /** If false, main part of a game will skip */
    private static boolean isGameGoing = true;

    /** Coordinates of a racket */
    private static double racketPosX = 0;
    private static double racketPosY = 0;

    /** Speed of a racket */
    private static double racketSpeed = 0;

    /** Counts time that ball were in a position, in which he might be stuck */
    private static int mightBeStuckTime = 0;

    /** Speed of a ball on X axis */
    private static double ballSpeedX;

    /** Speed of a ball on a Y axis <p>
     * Y speed is depended on X speed and window height */
    private static double ballSpeedY;


    // Global objects

    /** Array of bricks <p>
     * <b>First value</b> - bricks in a column <p>
     * <b>Second value</b> - bricks in a row */
    private static final GRect[][] BRICK_WALL = new GRect[BRICK_ROWS_AMOUNT][BRICKS_PER_ROW_AMOUNT];

    /** Background */
    private static final GCompound BACK_GROUND = new GCompound();

    /** Background color */
    private static final GRect BACK_GROUND_COLOR = new GRect(0,0);

    /** Visual representation of amount of lives */
    private static final GOval[] LIVES_VISUAL = new GOval[LIVES_AMOUNT];

    /** Racket */
    private static final GRect RACKET = new GRect(0,0);

    /** Ball */
    private static final GOval BALL = new GOval(0,0);

    /** Game walls that go outside of window (in case window gets changed while game is on) <p>
     * 4 is for number of sides of a window */
    private static final GRect[] WALL = new GRect[4];


    /** Run a game */
    public void run(){
        screenWidth = getWidth();
        screenHeight = getHeight();
        breakout();
    }

    /** Activates the game */
    public void breakout() {
        setGame();
        gameAction();
    }

    /** All that changes in a game, changes in that cycle*/
    public void gameAction(){
        while(isGameGoing) {
            racketMove();
            ballMove();
            checkForWin();
            pause(1);
        }
    }

    /** Updates balls coordinates */
    public void ballMove() {
        ballCollisionsCheck(setHitBox(BALL));
        BALL.move(ballSpeedX, ballSpeedY);
    }

    /** Check for collisions at the moment */
    public void ballCollisionsCheck(GPoint[][] hitBox) {
        // Tries to fix occurring bugs
        bugFinder(BALL);

        // Collisions
        for(int i = 0; i < 4; i++) {
            if(getElementAt(hitBox[i][0]) != BACK_GROUND) {
                // Check for side of a corner (left/right)
                if(getElementAt(hitBox[i][1]) != BACK_GROUND) {
                    checkForInteractionsX(hitBox[i][1]);
                    changeBallXDirection();
                    break;
                }

                // Check for side of a corner (top/bottom)
                else if(getElementAt(hitBox[i][2]) != BACK_GROUND) {
                    checkForInteractionsY(hitBox[i][2]);
                    changeBallYDirection();
                    break;
                }
            }
        }
    }

    /** Checks for collisions that make interactions on X axis */
    public void checkForInteractionsX(GPoint point) {
        racketInteraction(point);
        brickInteraction(point);
    }

    /** Checks for collisions that make interactions on Y axis */
    public void checkForInteractionsY(GPoint point) {
        racketInteraction(point);
        brickInteraction(point);
        bottomInteraction(point);

    }

    /** Makes ball-racket interaction */
    public void racketInteraction(GPoint point) {
        // If hit with certain speed, increases or decreases ball speed on X axis
        if(getElementAt(point) == RACKET) {
            changeBallSpeedX(ballSpeedX + racketSpeed * SPEED_MULTIPLIER);
        }
    }

    /** Makes a ball-brick interaction */
    public void brickInteraction(GPoint point) {
        for(int i = 0; i < BRICK_ROWS_AMOUNT; i++) {
            for(int j = 0; j < BRICKS_PER_ROW_AMOUNT; j++) {
                if(getElementAt(point) == BRICK_WALL[i][j]) {
                    remove(BRICK_WALL[i][j]);
                    bricksLeft--;
                    score += (i + 1) * BASIC_BRICK_SCORE;
                    break;
                }
            }
        }
    }

    /** Makes a bottom wall-ball interaction */
    public void bottomInteraction(GPoint point) {
        if(getElementAt(point) == WALL[3]) {
            loseLife();
            setLivesVisual();
            // Won't activate if game is lost (livesLeft is equal to LIVES_AMOUNT after one life is lost only if game restarted via lose)
            if(livesLeft != LIVES_AMOUNT) {
                respawnBall();
            }
            changeBallYDirection();
        }
    }

    /** Changes balls speed on X axis while updating speed on Y axis*/
    public void changeBallSpeedX(double newBallSpeedX) {
        // Changes X speed
        ballSpeedX = newBallSpeedX;
        speedLimitX();

        // Checks for current Y direction
        int currentDirectionY = 1;
        if (ballSpeedY < 0) {
            currentDirectionY = -1;
        }

        // Updates Y speed
        changeBallSpeedY((BALL_BASE_SPEED_Y + Math.abs(ballSpeedX) * Y_SPEED_TO_X_SPEED_DEPENDENCY) * Y_SPEED_TO_WINDOW_HEIGHT_DEPENDENCY * currentDirectionY);
    }

    public void changeBallSpeedY(double newBallSpeedY) {
        ballSpeedY = newBallSpeedY;
        speedLimitY();
    }

    /** Activates all bug finding methods */
    public void bugFinder(GObject obj) {
        stuckOut(obj);
        glitchOut(obj);
        outOfBounds(obj);
        speedLimitX();
        speedLimitY();
    }

    /** If object is stuck at on position for too long, pull it down a little */
    public void stuckOut(GObject obj) {
        // Checks where ball is (needed for unstucking)
        GPoint monitorBall = new GPoint(0,0);

        // Update ball center once every 100 ms
        if(System.currentTimeMillis()%100 == 0) {
            monitorBall = calcCenterPoint(obj);
        }
        if (    monitorBall.getX() > obj.getX() &&
                monitorBall.getX() < obj.getX() + obj.getWidth() &&
                monitorBall.getY() > obj.getY() &&
                monitorBall.getY() < obj.getY() + obj.getHeight()
        ) {
            mightBeStuckTime++;
        } else {
            mightBeStuckTime = 0;
        }
        if(mightBeStuckTime == 50) {
            obj.move(0,10);
            changeBallYDirection();
        }
    }

    /** If object is stuck to the center point, it will tp out to the top until it's no longer stuck */
    public void glitchOut(GObject obj) {
        GPoint objCenter = calcCenterPoint(obj);
        if(getElementAt(objCenter) != obj) {
            obj.move(0,-obj.getHeight()/2);
            glitchOut(obj);
        }
    }

    /** If object out of a window, this puts it back in */
    public void outOfBounds(GObject obj) {
        // So the object first tries to check for wall collision before teleporting
        double offset = 1;

        // Checks if objects X coordinate is out of a window
        if(obj.getX() < -offset) {
            obj.setLocation(offset,obj.getY());
        } else if(obj.getX() + obj.getWidth() > screenWidth + offset) {
            obj.setLocation(screenWidth - obj.getWidth() - offset,obj.getY());
        }

        // Checks if objects Y coordinate is out of a window
        if(obj.getY() < -offset) {
            obj.setLocation(obj.getX(),offset);
        } else if(obj.getY() + obj.getHeight() > screenHeight + offset) {
            obj.setLocation(obj.getX(),screenHeight - obj.getHeight() - offset);
        }
    }

    /** So the ball wouldn't fly like crazy, X axis*/
    public void speedLimitX() {
        if(ballSpeedX > SPEED_LIMIT_X) {
            changeBallSpeedX(SPEED_LIMIT_X);
        } else if(ballSpeedX < -SPEED_LIMIT_X) {
            changeBallSpeedX(-SPEED_LIMIT_X);
        }
    }

    /** So the ball wouldn't fly like crazy, Y axis*/
    public void speedLimitY() {
        if(ballSpeedY > SPEED_LIMIT_Y) {
            ballSpeedY = SPEED_LIMIT_Y;
        } else if(ballSpeedY < -SPEED_LIMIT_Y) {
            ballSpeedY = -SPEED_LIMIT_Y;
        }
    }

    /** Changes speed on an X axis to the opposite */
    public void changeBallXDirection() {
        ballSpeedX *= -1;
    }

    /** Changes speed on an Y axis to the opposite */
    public void changeBallYDirection() {
        ballSpeedY *= -1;
    }

    /** Updates rackets X coordinate */
    public void racketMove() {

        // Sets a value for a potential new location of a racket
        double racketNewPosX = MouseInfo.getPointerInfo().getLocation().getX() - RACKET.getWidth()/2 - POINTER_OFFSET;
        double racketNewRightX = racketNewPosX + RACKET.getWidth();

        // Remember old location
        double racketOldPosX = racketPosX;

        // Checks if any points of racket are connecting to a wall
        if(racketNewPosX >= WALL[0].getX() && racketNewRightX <= WALL[1].getX() + WALL[1].getWidth()) {
            racketPosX = racketNewPosX;
        }
        // If collision with the left wall
        else if(racketNewPosX < WALL[0].getX()) {
            racketPosX = 0;
        }
        // If collision with the right wall
        else if(racketNewRightX > WALL[1].getX() + WALL[1].getWidth()) {
            racketPosX = screenWidth - RACKET.getWidth();
        }
        // Send updated location
        RACKET.setLocation(racketPosX,racketPosY);
        racketSpeed = racketPosX - racketOldPosX;
    }

    /** Set all objects in position to start a game */
    public void setGame() {
        // Matters only after first game
        score = 0;
        livesLeft = LIVES_AMOUNT;
        bricksLeft = BRICK_ROWS_AMOUNT * BRICKS_PER_ROW_AMOUNT;

        // Update all screen size depended global variables
        updateGlobalConstants();

        // Set all objects

        // Sets an array for brick colors
        // That method located in Settings.java
        setColorArray();

        // Background
        setBackGround();

        // Set brick wall object created in settings
        setBrickWall();

        // Ball
        setBall();

        // Racket
        setRacket();

        // Walls
        setAllWalls();

        // Lives amount
        setLivesVisual();
    }

    /** Update all screen size depended global variables */
    public void updateGlobalConstants() {
        // Corrects a brick width to a window width
        BRICK_WIDTH = (screenWidth - (BRICKS_PER_ROW_AMOUNT - 1) * BRICK_SEP) / BRICKS_PER_ROW_AMOUNT;

        // Adjust Y speed to window height, so a game won't feel too rushed or too boring
        Y_SPEED_TO_WINDOW_HEIGHT_DEPENDENCY = screenHeight / REFERENCE_SCREEN_HEIGHT;

        // Adjust Y speed limit to window height dependency
        SPEED_LIMIT_Y *= Y_SPEED_TO_WINDOW_HEIGHT_DEPENDENCY;

        // Sets a Y coordinate for a racket
        racketPosY = screenHeight - RACKET_Y_OFFSET;

        // Sets start coordinates for a ball
        BALL_START_POS_X = screenWidth / 2;
        BALL_START_POS_Y = screenHeight / 2;
    }

    /** Set a brick wall */
    public void setBrickWall() {
        for(int i = 0; i < BRICK_ROWS_AMOUNT; i++) {
            pause(BRICK_ROW_CREATE_PAUSE);
            for(int j = 0; j < BRICKS_PER_ROW_AMOUNT; j++) {
                //Calculate pos for current brick
                double brickPosX = (BRICK_WIDTH + BRICK_SEP) * j;
                double brickPosY = BRICK_Y_OFFSET + (BRICK_HEIGHT + BRICK_SEP) * i;
                //Set brick with these position and color of current row
                BRICK_WALL[i][j] = makeBrick(brickPosX,brickPosY, BRICK_COLOR[i]);
                add(BRICK_WALL[i][j]);
            }
        }
    }

    /** Makes a brick */
    public GRect makeBrick(double posX, double posY, Color color) {
        GRect brick = new GRect(posX,posY,BRICK_WIDTH,BRICK_HEIGHT);
        brick.setFilled(true);
        brick.setColor(color);
        return brick;
    }

    /** Set values for a ball */
    public void setBall() {
        BALL.setFilled(true);
        BALL.setColor(BALL_COLOR);
        BALL.setSize(BALL_RADIUS,BALL_RADIUS);
        add(BALL);
        respawnBall();
    }

    /** Set values for a racket\*/
    public void setRacket() {
        RACKET.setFilled(true);
        RACKET.setColor(RACKET_COLOR);
        RACKET.setSize(RACKET_WIDTH, RACKET_HEIGHT);
        RACKET.setLocation(racketPosX,racketPosY);
        add(RACKET);
    }

    /** Sets all 4 walls around a game window */
    public void setAllWalls() {
        // Left wall
        setWall(0,WALL_THICKNESS,screenHeight + WALL_THICKNESS,-WALL_THICKNESS,0);
        // Right wall
        setWall(1,WALL_THICKNESS,screenHeight + WALL_THICKNESS,screenWidth,0);
        // Top wall
        setWall(2,screenWidth + WALL_THICKNESS,WALL_THICKNESS,0,-WALL_THICKNESS);
        // Bottom wall
        setWall(3,screenWidth + WALL_THICKNESS,WALL_THICKNESS,0,screenHeight);
    }

    /** Sets an outside wall around a game window */
    public void setWall(int wallNum, double width, double height, double posX, double posY) {
        WALL[wallNum] = new GRect(posX,posY,width,height);
        WALL[wallNum].setColor(Color.BLACK);
        WALL[wallNum].setFilled(true);
        WALL[wallNum].setFillColor(Color.LIGHT_GRAY);
        add(WALL[wallNum]);
    }

    /** Sets a background */
    public void setBackGround() {
        setBackGroundColor();
        setLivesVisual();
        add(BACK_GROUND);
    }

    /** Sets a background color*/
    public void setBackGroundColor() {
        BACK_GROUND_COLOR.setSize(screenWidth,screenHeight);
        BACK_GROUND_COLOR.setFilled(true);
        BACK_GROUND_COLOR.setColor(BACKGROUND_COLOR);
        BACK_GROUND.add(BACK_GROUND_COLOR);
    }

    /** Sets a hitbox for an object */
    public GPoint[][] setHitBox(GObject obj) {
        // Sets array of points at balls hitbox corners
        GPoint[][] hitBox = new GPoint[4][3];

        // Initialise GPoints in array
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                hitBox[i][j] = new GPoint(0, 0);
            }
        }

        // First value is a corner of an object
        // Second value is a side of a corner

        // Left top
        hitBox[0][0].setLocation(obj.getX(), obj.getY());
        // Left side
        hitBox[0][1].setLocation(obj.getX(), obj.getY() + 1);
        // Top side
        hitBox[0][2].setLocation(obj.getX() + 1, obj.getY());

        // Right top
        hitBox[1][0].setLocation((int) (obj.getX() + obj.getWidth()), (int) obj.getY());
        // Right side
        hitBox[1][1].setLocation((int) (obj.getX() + obj.getWidth()), (int) obj.getY() + 1);
        // Top side
        hitBox[1][2].setLocation((int) (obj.getX() + obj.getWidth()) - 1, (int) obj.getY());

        // Left bottom
        hitBox[2][0].setLocation((int) (obj.getX()), (int) (obj.getY() + obj.getHeight()));
        // Left side
        hitBox[2][1].setLocation((int) (obj.getX()), (int) (obj.getY() + obj.getHeight() - 1));
        // Bottom side
        hitBox[2][2].setLocation((int) (obj.getX()) + 1, (int) (obj.getY() + obj.getHeight()));

        // Right bottom
        hitBox[3][0].setLocation((int) (obj.getX() + obj.getWidth()), (int) (obj.getY() + obj.getHeight()));
        // Right side
        hitBox[3][1].setLocation((int) (obj.getX() + obj.getWidth()), (int) (obj.getY() + obj.getHeight()) - 1);
        // Bottom side
        hitBox[3][2].setLocation((int) (obj.getX() + obj.getWidth()) - 1, (int) (obj.getY() + obj.getHeight()));

        return hitBox;
    }

    /** Sets a label*/
    public GLabel setLabel(String text, int fontSize, double posX, double posY) {
        GLabel label = new GLabel(text,posX,posY);
        label.setFont("MONOSPACED-" + fontSize);
        label.setColor(TEXT_COLOR);
        label.setLocation(posX - (label.getWidth() / 2),posY - (label.getHeight() / 2));
        return label;
    }

    /** Set lives visualisation */
    public void setLivesVisual() {
        // Remove previous amount
        for(int i = 0; i < LIVES_VISUAL.length; i++) {
            if(LIVES_VISUAL[i] != null) {
                BACK_GROUND.remove(LIVES_VISUAL[(LIVES_VISUAL.length - 1) - i]);
            }
        }

        // Set new amount
        for(int i = 0; i < livesLeft; i++) {
            double visualLifePosX = VISUAL_LIVES_OFFSET + (BALL_RADIUS + VISUAL_LIVES_OFFSET)*i;
            double visualLifePosY = screenHeight - BALL_RADIUS - VISUAL_LIVES_OFFSET;
            LIVES_VISUAL[i] = new GOval(visualLifePosX,visualLifePosY,BALL_RADIUS,BALL_RADIUS);
            LIVES_VISUAL[i].setFilled(true);
            LIVES_VISUAL[i].setColor(BALL_COLOR);
            BACK_GROUND.add(LIVES_VISUAL[i]);
        }
    }

    /** Creates set of colors that used to paint bricks and gives those values to brickColor[]
     * Every color paints two rows of bricks
     * If all colors are already used, then colors go from start */
    public static void setColorArray(){
        // Sets all possible colors for bricks
        Color[] colorSet = new Color[7];
        colorSet[0] = Color.RED;
        colorSet[1] = Color.ORANGE;
        colorSet[2] = Color.YELLOW;
        colorSet[3] = Color.GREEN;
        colorSet[4] = Color.CYAN;
        colorSet[5] = Color.BLUE;
        colorSet[6] = Color.MAGENTA;

        // Writes colors from colorSet[] to brickColor[] for needed amount of rows
        int oddRow = BRICK_COLOR.length % 2;
        for(int i = 0; i < BRICK_COLOR.length/2 + oddRow; i++) {
            BRICK_COLOR[i*2] = colorSet[i%colorSet.length];
            // If number of rows is odd, don't try to paint second row in color pair
            if(i < BRICK_COLOR.length/2) {
                BRICK_COLOR[i*2+1] = colorSet[i%colorSet.length];
            }
        }
    }

    /** Calculates objects center point */
    public GPoint calcCenterPoint(GObject obj) {
        double centerPointX = obj.getX() + obj.getWidth()/2;
        double centerPointY = obj.getY() + obj.getHeight()/2;
        return new GPoint(centerPointX,centerPointY);
    }

    /** Returns 1 or -1 */
    public int randDirection() {
        double coin = Math.random();
        if(coin<0.5) {
            return -1;
        } else {
            return 1;
        }
    }

    /** Loses 1 life, checks if conditions for lose is met */
    public void loseLife() {
        livesLeft--;
        if(livesLeft == 0) {
            // Remove last life
            setLivesVisual();

            // Play lose animation
            isGameGoing = false;
            pause(LOSE_PAUSE);
            loseAnimation();
        }
    }

    /** Respawn ball at start position with random speed */
    public void respawnBall() {
        // Set ball at center of a window
        BALL.setLocation(BALL_START_POS_X, BALL_START_POS_Y);

        // Spawn animation
        for(int i = 0; i < BALL_BLEEP_AMOUNT; i++) {
           pause(BALL_BLEEP_PAUSE);
           remove(BALL);
           pause(BALL_BLEEP_PAUSE);
           add(BALL);
        }

        // Speed change
        changeBallSpeedX(Math.random() * randDirection());
        changeBallSpeedY((BALL_BASE_SPEED_Y + Math.abs(ballSpeedX) * Y_SPEED_TO_X_SPEED_DEPENDENCY) * Y_SPEED_TO_WINDOW_HEIGHT_DEPENDENCY);
    }

    /** Checks if conditions for win is met */
    public void checkForWin() {
        if(bricksLeft == 0) {
            isGameGoing = false;
            pause(WIN_PAUSE);
            winAnimation();
        }
    }

    /** Plays win animation */
    public void winAnimation() {
        // Ball is not removed intentionally for effect
        remove(RACKET);

        endGameAnimation(WIN_TEXT);

    }

    /** Plays lose animation */
    public void loseAnimation() {
        // Wall removing
        // "BRICK_ROWS_AMOUNT - 1" needed instead of just BRICK_ROWS_AMOUNT
        // because array ends on number BRICK_ROWS_AMOUNT - 1
        for(int i = BRICK_ROWS_AMOUNT - 1; i >= 0; i--) {
            int bricksInRowLeft = 0;
            for(int j = 0; j < BRICKS_PER_ROW_AMOUNT; j++) {
                if(BRICK_WALL[i][j].isVisible()) {
                    bricksInRowLeft++;
                    remove(BRICK_WALL[i][j]);
                }
            }
            // If there's no bricks in a row, pause will not be made
            if(bricksInRowLeft != 0) {
                pause(BRICK_ROW_REMOVE_PAUSE);
            }
        }

        remove(RACKET);
        remove(BALL);

        endGameAnimation(LOSE_TEXT);
    }

    /** Sets a text if you win or lose */
    public void endGameAnimation(String text) {
        // Add texts
        GLabel bigLabel = setLabel(text,BIG_FONT_SIZE,screenWidth/2,screenHeight/2);
        add(bigLabel);

        GLabel finalScoreLabel = setLabel(SCORE_TEXT + score, MEDIUM_FONT_SIZE,screenWidth/2,(screenHeight + bigLabel.getHeight())/2);
        add(finalScoreLabel);

        // Animate score
        scoreAnimation(finalScoreLabel);

        // Animate warning for a new game
        startNewGameWarningAnimation(bigLabel.getHeight(),finalScoreLabel.getHeight());

        // Remove all texts
        remove(bigLabel);
        remove(finalScoreLabel);

        // Starts new game
        isGameGoing = true;
        setGame();
    }

    /** Zero to current score animation */
    public void scoreAnimation(GLabel label) {
        for(int i = 0; i <= score; i++) {
            label.setLabel(SCORE_TEXT + i);

            //After first X amount of points speed up Y times
            if(i < SCORE_SPEEDUP_THRESHOLD || i % SCORE_SPEEDUP_TIMES == 0) {
                pause(1);
            }
        }
        for(int i = 0; i < SCORE_BLEEP_AMOUNT; i++){
            pause(SCORE_BLEEP_PAUSE);
            label.setLabel(SCORE_TEXT);
            pause(SCORE_BLEEP_PAUSE);
            label.setLabel(SCORE_TEXT + score);
        }

    }

    /** Start new game */
    public void startNewGameWarningAnimation(double mainLabelOffset, double scoreLabelOffset) {
        pause(NEW_GAME_PAUSE);

        // New game warning
        GLabel newGame = setLabel(NEW_GAME_WARNING,MEDIUM_FONT_SIZE,screenWidth/2,(screenHeight + mainLabelOffset) / 2 + scoreLabelOffset);
        add(newGame);

        // Animate dots
        for(int i = 0; i < NEW_GAME_WARNING_DOTS_AMOUNT; i++){
            pause(MID_NEW_GAME_WARNING_PAUSE);
            newGame.setLabel(newGame.getLabel() + ".");
        }
        pause(NEW_GAME_WARNING_PAUSE);

        // Remove text in prep for new game
        remove(newGame);
    }
}