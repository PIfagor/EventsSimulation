package main.stream;

import main.stream.Constaints;
public class Particle {
	private double rx, ry; // position
	private double vx, vy; // velocity
	private final double radius; // radius
	private final double mass; // mass
	private int count; // number of collisions

	public Particle() {
	}

	public void move(double dt) {
	}

	public void draw() {
	}

	public double timeToHit(Particle that){
		if (this == that) return Constaints.INFINITY;
		double dx = that.rx - this.rx, dy = that.ry - this.ry;
		double dvx = that.vx - this.vx, dvy = that.vy - this.vy;
		double dvdr = dx*dvx + dy*dvy;
		if( dvdr > 0) return Constaints.INFINITY;
		double dvdv = dvx*dvx + dvy*dvy;
		double drdr = dx*dx + dy*dy;
		double sigma = this.radius + that.radius;
		double d = (dvdr*dvdr) - dvdv * (drdr - sigma*sigma);
		if (d < 0) return Constaints.INFINITY;
		return -(dvdr + Math.sqrt(d)) / dvdv;
		}


	public double timeToHitVerticalWall() {
		return count;
	}

	public double timeToHitHorizontalWall() {
		return count;
	}

	public void bounceOff(Particle that){
		double dx = that.rx - this.rx, dy = that.ry - this.ry;
		double dvx = that.vx - this.vx, dvy = that.vy - this.vy;
		double dvdr = dx*dvx + dy*dvy;
		double dist = this.radius + that.radius;
		double J = 2 * this.mass * that.mass * dvdr / ((this.mass + that.mass) * dist);
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
	}

	public void bounceOffHorizontalWall() {
	}
}
