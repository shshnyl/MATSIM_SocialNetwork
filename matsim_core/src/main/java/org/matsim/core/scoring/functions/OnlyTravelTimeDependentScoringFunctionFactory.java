/* *********************************************************************** *
 * project: org.matsim.*
 * OnlyTimeDependentScoringFunctionFactory.java
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

package org.matsim.core.scoring.functions;

import org.matsim.api.core.v01.population.Person;
import org.matsim.core.scoring.ScoringFunction;
import org.matsim.core.scoring.ScoringFunctionFactory;

/**
 * Returns a Scoring Function that only respects the travel time.
 * @author cdobler
 */
public class OnlyTravelTimeDependentScoringFunctionFactory implements ScoringFunctionFactory {
	
	@Override
	public ScoringFunction createNewScoringFunction(Person person) {
		return new OnlyTravelTimeDependentScoringFunction();
	}
}



