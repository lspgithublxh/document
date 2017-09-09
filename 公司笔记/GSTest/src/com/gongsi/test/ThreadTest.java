package com.gongsi.test;

import java.util.Date;

public class ThreadTest {

	 static int lock_num = 2;
	
	public static void main(String[] args) throws InterruptedException {
		final Object obj = new Object();
		 
		Thread sub1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("thread1");
				try {
					Thread.currentThread().sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int m = 1 / 0;
				synchronized (obj) {
					lock_num--;
					if(lock_num == 0){
						obj.notify();
					}
				}
			}
		});
		Thread sub2 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("thread2");
				try {
					Thread.currentThread().sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (obj) {
					lock_num--;
					if(lock_num == 0){
						obj.notify();
					}
				}
			}
		});
		sub1.start();
		sub2.start();
		synchronized (obj) {
			System.out.println("wait start:" + new Date());
			obj.wait();
			System.out.println("wait end:" + new Date());
		}
		
	}
	
	
}
