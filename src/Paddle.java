/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;

/**
 * A basic game object displayed as a black square, starting in the upper left
 * corner of the game court.
 * 
 */
public class Paddle extends GameObj {
    public static final int LENGTH = 75;
    public static final int WIDTH = 15;
	public static final int INIT_X = (GameCourt.COURT_LENGTH - LENGTH)/2;
	public static final int INIT_Y = GameCourt.COURT_HEIGHT - 2*WIDTH;
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;

	/**
	 * Note that, because we don't need to do anything special when constructing
	 * a Square, we simply use the superclass constructor called with the
	 * correct parameters
	 */
	public Paddle(int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_X, INIT_Y, LENGTH, WIDTH, courtWidth,
				courtHeight);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(pos_x, pos_y, width, height);
	}

}
