package main.stream;

import java.awt.Color;

import lib.princeton.StdDraw;
import lib.princeton.StdIn;

public class Runer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StdDraw.setCanvasSize(800, 800);

        // remove the border
        StdDraw.setXscale(1.0/22.0, 21.0/22.0);
        StdDraw.setYscale(1.0/22.0, 21.0/22.0);


        // StdDraw.setCanvasSize(1024, 768);
        // StdDraw.setXscale(1.0/22.0 +.016, +0.013 + 27.6666667/22.0);
        // StdDraw.setYscale(1.0/22.0, 21.0/22.0);

        // turn on animation mode
        StdDraw.show(0);

        // the array of particles
        Particle[] particles;

        // create N random particles
        boolean lol = true;
        if (lol) {
            int N = Integer.parseInt("30");
            particles = new Particle[N];
            for (int i = 0; i < N; i++) particles[i] = new Particle();
        }

        // or read from standard input
        else {
            int N = StdIn.readInt();
            particles = new Particle[N];
            for (int i = 0; i < N; i++) {
                double rx     = StdIn.readDouble();
                double ry     = StdIn.readDouble();
                double vx     = StdIn.readDouble();
                double vy     = StdIn.readDouble();
                double radius = StdIn.readDouble();
                double mass   = StdIn.readDouble();
                int r         = StdIn.readInt();
                int g         = StdIn.readInt();
                int b         = StdIn.readInt();
                Color color   = new Color(r, g, b);
               // particles[i] = new Particle(rx, ry, vx, vy, radius, mass, color);
            }
        }

        // create collision system and simulate
        CollisionSystem system = new CollisionSystem(particles);
        system.simulate();

	}

}
