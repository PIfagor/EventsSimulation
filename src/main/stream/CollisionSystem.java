package main.stream;



import java.awt.Color;

import lib.lection.MinPQ;
import lib.princeton.StdDraw;



public class CollisionSystem {
	
	private double limit = 10000;

	class Event implements Comparable<Event> {
		private double time; // time of event
		private Particle a, b; // particles involved in event
		private int countA, countB; // collision counts for a and b

		public Event(double t, Particle a, Particle b) {
			time = t;
			this.a = a;
			this.b = b;
			
			if (a != null) countA = a.count();
            else           countA = -1;
            if (b != null) countB = b.count();
            else           countB = -1;

		}

		public int compareTo(Event that) {
			return Double.compare(this.time, that.time);
		}

		public boolean isValid() {
			if (a != null && a.count() != countA) return false;
            if (b != null && b.count() != countB) return false;
            return true;
		}
	}

	private MinPQ<Event> pq; // the priority queue
	private double t = 0.0; // simulation clock time
	private Particle[] particles; // the array of particles

	public CollisionSystem(Particle[] particles) {
		this.particles = particles;
	}

	private void predict(Particle a) {
		if (a == null)
			return;
		for (int i = 0; i < particles.length; i++) {
			double dt = a.timeToHit(particles[i]);
			if (t + dt < limit)
				pq.insert(new Event(t + dt, a, particles[i]));
		}
		double dtx = a.timeToHitVerticalWall();
		double dty = a.timeToHitHorizontalWall();
		if (t + dtx < limit)
			pq.insert(new Event(t + dtx, a, null));
		
		if (t + dty < limit)
			pq.insert(new Event(t + dty, null, a));
	}

	private void redraw() {
		StdDraw.clear();
		
		for (int i = 0; i < particles.length; ++i) {
			particles[i].draw();
		}
		StdDraw.show(50);
		if (t < limit) {
            pq.insert(new Event(t + 1.0 / 0.5, null, null));
        }
	}

	public void simulate() {
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
	
	public static void main(String[] args) {
		
//		StdDraw.setXscale(0, 1);
//	    StdDraw.setYscale(0, 1);
		StdDraw.setXscale(1.0/22.0, 21.0/22.0);
        StdDraw.setYscale(1.0/22.0, 21.0/22.0);
	    StdDraw.show(0);
		int N = 100;
		Particle[] balls = new Particle[N];
		for (int i = 0; i < N; i++)
			balls[i] = new Particle();
		CollisionSystem s = new CollisionSystem(balls);
		
		s.simulate();
		//while (true) {
			
			
			
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			StdDraw.clear();
//			for (int i = 0; i < N; i++) {
//				balls[i].move(0.5);
//				balls[i].draw();
//			}
//			StdDraw.show(50);
		//}
	}
}
