import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 * 
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

    // the state of the game logic
    private Paddle paddle; // the Paddle, keyboard control
    private Ball ball; // the Golden Snitch, bounces
    private Brick[][] bricks = new Brick[15][10];

    public boolean playing = false; // whether the ball has been released
    private static boolean hasLost = false;
    private JLabel status; // Current status text (i.e. Level)
    private int level = 1;

    // Game constants
    public static final int COURT_LENGTH = 10*Brick.WIDTH;
    public static final int COURT_HEIGHT = 5*Paddle.WIDTH + 20*Brick.HEIGHT;
    public static final int PADDLE_VELOCITY = 4;
    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 15;

    public GameCourt(JLabel status) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // The timer is an object which triggers an action periodically
        // with the given INTERVAL. One registers an ActionListener with
        // this timer, whose actionPerformed() method will be called
        // each time the timer triggers. We define a helper method
        // called tick() that actually does everything that should
        // be done in a single timestep.
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start(); // MAKE SURE TO START THE TIMER!

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key
        // events will be handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long
        // as an arrow key is pressed, by changing the square's
        // velocity accordingly. (The tick method below actually
        // moves the square.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT)
                    paddle.v_x = -PADDLE_VELOCITY;
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                    paddle.v_x = PADDLE_VELOCITY;
                else if (e.getKeyCode() == KeyEvent.VK_SPACE && !playing && !hasLost){
                    ball.pos_x += ball.v_x;
                    ball.v_y = - Math.abs(ball.v_y);
                    ball.pos_y += ball.v_y;
                    playing = true;
                }
            }

            public void keyReleased(KeyEvent e) {
                paddle.v_x = 0;
            }
        });

        this.status = status;
        
        bricks = LevelReader.getBricks(1);
        //newBricks();
    }
    
    /**
    public void newBricks(){
        //Brick[][] br = LevelReader.getBricks(1);
        for(int r = 0; r < 15; r++){
            for(int c = 0; c < 10; c++){
                //bricks[r][c] = br[r][c];
                bricks[r][c] = new Brick(c*Brick.WIDTH, r*Brick.HEIGHT, 3);
            }
        }
    }
    **/

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {

        paddle = new Paddle(COURT_LENGTH, COURT_HEIGHT);
        ball = new Ball(COURT_LENGTH, COURT_HEIGHT);

        playing = false;
        hasLost = false;
        status.setText("Level " + level);
        level = 1;
        bricks = LevelReader.getBricks(6);

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    public static void lost(){ // called in GameObj
        hasLost = true;
    }
    
    public boolean canAdvance(){
        for(int r = 0; r < 15; r++){
            for(int c = 0; c < 10; c++){
                if(bricks[r][c] != null){
                    return false;
                }
            }
        }
        return true;
    }
    
    public void nextLevel(){
        paddle = new Paddle(COURT_LENGTH, COURT_HEIGHT);
        ball = new Ball(COURT_LENGTH, COURT_HEIGHT);
        playing = false;
        hasLost = false;
        status.setText("Level " + level);
        level++;
        bricks = LevelReader.getBricks(level);
        if(canAdvance()){
            status.setText("YOU WON!");
        }
    }
    
    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     */
    void tick() {
        if (playing && !hasLost) {
            // advance the square and snitch in their
            // current direction.
            paddle.move();
            ball.move();

            // make the snitch bounce off walls...
            ball.bounce(ball.hitWall());

            // check for the game end conditions
            if (paddle.intersects(ball)) {
                ball.bounce(ball.hitObj(paddle));
                ball.v_y = -Math.abs(ball.v_y);
                //playing = false;
                //status.setText("You win!");
            }
            
            for(int r = 0; r < 15; r++){
                for(int c = 0; c < 10; c++){
                    if(bricks[r][c] != null && bricks[r][c].intersects(ball)){
                        int tempy = ball.v_y;
                        int tempx = ball.v_x;
                        ball.bounce(ball.hitObj(bricks[r][c]));
                        if(tempy == ball.v_y && tempx == ball.v_x){
                            ball.v_y = - ball.v_y;
                        }
                        if(bricks[r][c].getHits() == 1){
                            bricks[r][c] = null;
                        } else {
                            bricks[r][c].decr();
                        }
                       if(canAdvance()){
                            nextLevel();
                        }
                       ball.move();
                       break; // only 1 brick at a time
                    }
                }
            }

        } else if (!hasLost){
            paddle.move();
            ball.setPos(paddle.pos_x + (Paddle.LENGTH - Ball.SIZE)/2,
                        paddle.pos_y - Paddle.WIDTH);
            requestFocusInWindow();
            
        } else {
            playing = false;
            status.setText("Game Over!");
        }
        // update the display
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paddle.draw(g);
        ball.draw(g);
        for(int r = 0; r < 15; r++){
            for(int c = 0; c < 10; c++){
                if(bricks[r][c] != null){
                    bricks[r][c].draw(g);
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_LENGTH, COURT_HEIGHT);
    }
}
