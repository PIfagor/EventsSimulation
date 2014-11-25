package main.stream;

import java.awt.Color;
import java.util.Random;

import lib.princeton.StdDraw;
import main.stream.Constaints;

public class Particle {
	private double rx, ry; // position
	private double vx, vy; // velocity
	private final double radius; // radius
	private final double mass; // mass
	private int count; // number of collisions
	private Color color;
	private int r, g, b;
	
	// create a new particle with given parameters
	public Particle(double rx, double ry, double vx, double vy, double radius,
			double mass, Color color) {
		this.vx = vx;
		this.vy = vy;
		this.rx = rx;
		this.ry = ry;
		this.radius = radius;
		this.mass = mass;
		
		this.count = 0;
		
		
		Random r = new Random();
		this.r = Math.abs(r.nextInt()) % 255;
        g = Math.abs(r.nextInt()) % 255;
        b = Math.abs(r.nextInt()) % 255;
        this.color = getColor();
	}

	public Particle() {
		this(Math.random(), Math.random(), 0.01 * (Math.random() - 0.5),
				0.01 * (Math.random() - 0.5), Math.random()*0.01, 0.05, Color.GRAY);
	}

	public void move(double dt) {
		rx += vx * dt;
		ry += vy * dt;
	}

	public void draw() {
		StdDraw.setPenColor(color);
		StdDraw.filledCircle(rx, ry, radius);
	}

	// return the number of collisions involving this particle
	public int count() {
		return count;
	}

	public double timeToHit(Particle b) {
		Particle a = this;
		if (a == b)
			return Constaints.INFINITY;
		double dx = b.rx - a.rx;
		double dy = b.ry - a.ry;
		double dvx = b.vx - a.vx;
		double dvy = b.vy - a.vy;
		double dvdr = dx * dvx + dy * dvy;
		if (dvdr > 0)
			return Constaints.INFINITY;
		double dvdv = dvx * dvx + dvy * dvy;
		double drdr = dx * dx + dy * dy;
		double sigma = a.radius + b.radius;
		double d = (dvdr * dvdr) - dvdv * (drdr - sigma * sigma);
		// if (drdr < sigma*sigma) StdOut.println("overlapping particles");
		if (d < 0)
			return Constaints.INFINITY;
		return -(dvdr + Math.sqrt(d)) / dvdv;
	}

	public double timeToHitVerticalWall() {
		if (vx > 0)
			return (1.0 - rx - radius) / vx;
		else if (vx < 0)
			return (radius - rx) / vx;
		else
			return Constaints.INFINITY;
	}

	public double timeToHitHorizontalWall() {
		if (vy > 0)
			return (1.0 - ry - radius) / vy;
		else if (vy < 0)
			return (radius - ry) / vy;
		else
			return Constaints.INFINITY;
	}

	public void bounceOff(Particle that) {
		double dx = that.rx - this.rx, dy = that.ry - this.ry;
		double dvx = that.vx - this.vx, dvy = that.vy - this.vy;
		double dvdr = dx * dvx + dy * dvy;
		double dist = this.radius + that.radius;
		double J = 2 * this.mass * that.mass * dvdr
				/ ((this.mass + that.mass) * dist);
		double Jx = J * dx / dist;
		double Jy = J * dy / dist;
		this.vx += Jx / this.mass;
		this.vy += Jy / this.mass;
		that.vx -= Jx / that.mass;
		that.vy -= Jy / that.mass;
		this.count++;
		that.count++;
	}

	public void bounceOffVerticalWall() {
		vx = -vx;
		count++;
	}

	public void bounceOffHorizontalWall() {
		vy = -vy;
		count++;
	}

	// return kinetic energy associated with this particle
	public double kineticEnergy() {
		return 0.5 * mass * (vx * vx + vy * vy);
	}

	public Color getColor() {
		
		return new Color(r,g,b);
	}

	public void setColor(int rr, int gg, int bb) {
		color = new Color(rr%255,gg%255,bb%255);
		
	}
}
