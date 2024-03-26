package com.ar.vgmsistemas.gps;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class GpsManager implements LocationListener {

	private Location location;	
	
	public void onLocationChanged(Location loc) {
		loc.getLatitude();
		loc.getLongitude();
	}

	public void onProviderDisabled(String provider) {
	}

	public void onProviderEnabled(String provider) {
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Location getLocation() {
		return location;
	}

}