package main.stream;

import lib.princeton.StdDraw;

public class BouncingBalls {
	public static void main(String[] args) {
		StdDraw.setXscale(0, 500);
		StdDraw.setYscale(0, 500);
		
		int N = 10;
		Ball[] balls = new Ball[N];
		for (int i = 0; i < N; i++)
			balls[i] = new Ball(50*i);
		while (true) {
			StdDraw.clear();
			for (int i = 0; i < N; i++) {
				balls[i].move(0.5);
				balls[i].draw();
			}
			StdDraw.show(50);
		}
	}
}
