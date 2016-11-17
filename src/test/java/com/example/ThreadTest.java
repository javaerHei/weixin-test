package com.example;

import org.junit.Test;

public class ThreadTest {

	@Test
	public void test1() {

		final Business business = new Business();
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <= 50; i++) {
					business.sub(i);
				}
			}
		}).start();

		//
		for (int i = 1; i <= 50; i++) {
			business.main(i);
		}

	}

	class Business {
		boolean subRun = true;

		public synchronized void sub(int loop) {
			while (!subRun) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for (int i = 1; i <= 10; i++) {
				System.out.println("sub:" + i + ",loop:" + loop);
			}
			subRun = false;
			this.notify();
		}

		public synchronized void main(int loop) {
			while (subRun) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for (int i = 1; i <= 20; i++) {
				System.out.println("main:" + i + ",loop:" + loop);
			}
			subRun = true;
			this.notify();
		}
	}
}
