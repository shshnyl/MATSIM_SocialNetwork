/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2008 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

package org.matsim.vehicles;


/**
 * deliberately non-public since there is an interface.  kai, nov'11
 * 
 * @author dgrether
 */
public class FreightCapacityImpl implements FreightCapacity {

	private double volume;
	
	/**
	 * deliberately non-public since there is a factory.  kai, nov'11 
	 */
	public FreightCapacityImpl(){}
	
	@Override
	public void setVolume(double cubicMeters) {
		this.volume = cubicMeters;
	}
	
	@Override
	public double getVolume() {
		return this.volume;
	}
	
}
