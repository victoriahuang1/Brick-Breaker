/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;

/**
 * A basic game object displayed as a yellow circle, starting in the upper left
 * corner of the game court.
 * 
 */
public class Ball extends GameObj {

	public static final int SIZE = 15;
	public static final int INIT_POS_X = Paddle.INIT_X + (Paddle.LENGTH - SIZE)/2;
	public static final int INIT_POS_Y = Paddle.INIT_Y - Paddle.WIDTH;
	public static final int INIT_VEL_X = 2;
	public static final int INIT_VEL_Y = 3;

	public Ball(int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE,
				courtWidth, courtHeight);
	}
	
	public void setPos(int x, int y){
	    pos_x = x;
	    pos_y = y;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillOval(pos_x, pos_y, width, height);
	}

}
