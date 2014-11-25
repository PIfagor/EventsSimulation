package main.stream;

import lib.princeton.StdDraw;

public class Ball {
	private double rx, ry; // position
	private double vx, vy; // velocity
	private final double radius; // radius

	public Ball(int delta) {
		radius = 10;
		rx = 100+delta;
		ry = 100+delta;
		vx = 1;
		vy = 5;
	}
	
	public void move(double dt) {
		if ((rx + vx * dt < radius) || (rx + vx * dt > 1.0 - radius)) {
			vx = -vx;
		}
		if ((ry + vy * dt < radius) || (ry + vy * dt > 1.0 - radius)) {
			vy = -vy;
		}
		rx = rx + vx * dt;
		ry = ry + vy * dt;
	}

	public void draw() {
		StdDraw.filledCircle(rx, ry, radius);
	}
}
