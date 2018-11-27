package com.noblapp.model.cmd;

import com.noblapp.model.support.AbstractCmd;

public class LocationCmd extends AbstractCmd {
	private double lat_x;
	private double long_y;

	public double getLat_x() {
		return lat_x;
	}
	public void setLat_x(double lat_x) {
		this.lat_x = lat_x;
	}
	public double getLong_y() {
		return long_y;
	}
	public void setLong_y(double long_y) {
		this.long_y = long_y;
	}
}
