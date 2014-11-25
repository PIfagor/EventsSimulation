package main.stream;

import java.awt.Color;

import lib.lection.MinPQ;
import lib.princeton.StdDraw;

public class CollisionSystem {
	private MinPQ<Event> pq; // the priority queue
	private double t = 0.0; // simulation clock time
	private Particle[] particles; // the array of particles
	private int N = 0;
	 private double hz = 0.5;        // number of redraw events per clock tick
	 
	public CollisionSystem(Particle[] particles) {
		 this.particles = particles.clone();   // defensive copy
	}

	private void predict(Particle a)
	{
		predict(a, 10000);
	}
	private void predict(Particle a,double limit) {
		if (a == null)
			return;
		for (int i = 0; i < N; i++) {
			double dt = a.timeToHit(particles[i]);
			if (t + dt <= limit)
                pq.insert(new Event(t + dt, a, particles[i]));
			//pq.insert(new Event(t + dt, a, particles[i]));
		}
		 // particle-wall collisions
        double dtX = a.timeToHitVerticalWall();
        double dtY = a.timeToHitHorizontalWall();
        if (t + dtX <= limit) pq.insert(new Event(t + dtX, a, null));
        if (t + dtY <= limit) pq.insert(new Event(t + dtY, null, a));
	}

	private void redraw ()
	{
		redraw(10000);
	}
	private void redraw(double limit) {
		 StdDraw.clear();
	        for (int i = 0; i < particles.length; i++) {
	            particles[i].draw();
	        }
	        StdDraw.show(20);
	        if (t < limit) {
	            pq.insert(new Event(t + 1.0 / hz, null, null));
	        }
	}
	
	public void simulate()
	{
		simulate(100000);
	}
	
	public void simulate(double limit) {
		pq = new MinPQ<Event>();
		for (int i = 0; i < particles.length; i++)
			predict(particles[i]);
		pq.insert(new Event(0, null, null));
		
		
		while (!pq.isEmpty()) {
			Event event = pq.delMin();
			if (!event.isValid())
				continue;
			Particle a = event.a;
			Particle b = event.b;
			for (int i = 0; i < particles.length; i++)
				particles[i].move(event.time - t);
			t = event.time;
			if (a != null && b != null) {
				a.bounceOff(b);
				
				Color c = a.getColor();
				int rr = c.getRed() + 20;
				int gg = c.getGreen() + 15;
				int bb = c.getBlue() + 10;

				a.setColor(rr, gg, bb);
				b.setColor(rr, gg, bb);
			}
			else if (a != null && b == null)
				a.bounceOffVerticalWall();
			else if (a == null && b != null)
				b.bounceOffHorizontalWall();
			else if (a == null && b == null) {
				redraw();
			}
				
			predict(a);
			predict(b);
		}
	}

	private class Event implements Comparable<Event> {
		private double time; // time of event
		private Particle a, b; // particles involved in event
		private int countA, countB; // collision counts for a and b

		public Event(double t, Particle a, Particle b) {
			this.time = t;
            this.a    = a;
            this.b    = b;
            if (a != null) countA = a.count();
            else           countA = -1;
            if (b != null) countB = b.count();
            else           countB = -1;
		}

		public int compareTo(Event that) {
			if      (this.time < that.time) return -1;
            else if (this.time > that.time) return +1;
            else                            return  0;
		}

		public boolean isValid() {
			if (a != null && a.count() != countA) return false;
            if (b != null && b.count() != countB) return false;
            return true;
		}
	}


}
