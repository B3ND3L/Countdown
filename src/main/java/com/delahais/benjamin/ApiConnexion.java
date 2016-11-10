package com.delahais.benjamin;

import java.util.TimeZone;

import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.TimeZoneApi;
import com.google.maps.model.LatLng;

public class ApiConnexion {
	
	protected GeoApiContext mapsContext;

	public ApiConnexion(){
		mapsContext = new GeoApiContext().setApiKey("AIzaSyB_btQuhFEXlNgLnz0e9nD0FDvbRRd5Mws");
	}
	
	public void getTime(Double lat, Double lng){
		
		LatLng latlng = new LatLng(lat,lng);
		PendingResult<TimeZone> results = TimeZoneApi.getTimeZone(mapsContext,latlng);
		
	}
}
