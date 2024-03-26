package com.ar.vgmsistemas.exclusionmutua;

public class Monitor {

	private static boolean release = true;
	
	public static synchronized void lock(){
		release = false;
	}
	
	public static synchronized void unlock(){
		release = true;
	}
	
	public static boolean isRelease(){
		return release;
	}
	
}
