/* *********************************************************************** *
 * project: org.matsim.*
 * ParallelPopulationReaderMatsimV4Runner.java
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2012 by the members listed in the COPYING,        *
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

package org.matsim.core.population;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.population.ParallelPopulationReaderMatsimV4.EndProcessingTag;
import org.matsim.core.population.ParallelPopulationReaderMatsimV4.EndTag;
import org.matsim.core.population.ParallelPopulationReaderMatsimV4.PersonTag;
import org.matsim.core.population.ParallelPopulationReaderMatsimV4.StartTag;
import org.matsim.core.population.ParallelPopulationReaderMatsimV4.Tag;
import org.xml.sax.Attributes;

/**
 * Runnable used by ParallelPopulationReaderMatsimV4.
 * Processes xml data taken from a BlockingQueue which is filled
 * in the main class.
 * 
 * @author cdobler
 */
public class ParallelPopulationReaderMatsimV4Runner extends PopulationReaderMatsimV4 implements Runnable {
	
	private final BlockingQueue<List<Tag>> queue;
	
	public ParallelPopulationReaderMatsimV4Runner(Scenario scenario, BlockingQueue<List<Tag>> queue) {
		super(scenario);
		this.queue = queue;
	}
	
	@Override
	public void run() {
		/*
		 * The thread will go on with the parsing until an EndProcessingTag is found,
		 * which calls "return".
		 */
		while (true) {
			try {
				List<Tag> tags;
				tags = queue.take();
				
				for (Tag tag : tags) {
					if (tag instanceof PersonTag) {
						this.currperson = ((PersonTag) tag).person;
					} else if (tag instanceof StartTag) {
						// if its is a person tag, we use the startPerson method from this class
						if (PERSON.equals(tag.name)) {
							startPerson(((StartTag) tag).atts);
						}
						// otherwise hand the tag over to the super class
						else {
							this.startTag(tag.name, ((StartTag) tag).atts, tag.context);
						}
					} else if (tag instanceof EndTag) {
						/* 
						 * If its is a person tag, we reset the current person. We do not hand the
						 * tag over to the superclass because the person has already been added
						 * to the population.
						 */
						if (PERSON.equals(tag.name)) {
							this.currperson = null;
						}
						// otherwise hand the tag over to the super class
						else {
							this.endTag(tag.name, ((EndTag) tag).content, tag.context);
						}
					} else if (tag instanceof EndProcessingTag) {
						return;
					}
				}
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	private void startPerson(final Attributes atts) {
		String ageString = atts.getValue("age");
		int age = Integer.MIN_VALUE;
		if (ageString != null) age = Integer.parseInt(ageString);
		this.currperson.setAge(age);
		// by Haomin Hu 26 Jan, this.currperson.setSex(atts.getValue("sex"));
		this.currperson.setSex(atts.getValue("gend"));
		this.currperson.setLicence(atts.getValue("license"));
		this.currperson.setCarAvail(atts.getValue("car_avail"));
		String employed = atts.getValue("employed");
		if (employed == null) {
			this.currperson.setEmployed(null);
		} else {
			this.currperson.setEmployed("yes".equals(employed));
		}
		
		// by Haomin Hu here, Jan 26
		// mode att;
		this.currperson.modeAtt[0] = Double.parseDouble(atts.getValue("att_car"));
		this.currperson.modeAtt[1] = Double.parseDouble(atts.getValue("att_pt"));
		this.currperson.modeAtt[2] = Double.parseDouble(atts.getValue("att_walk"));
		this.currperson.modeAtt[3] = Double.parseDouble(atts.getValue("att_bike"));
		// mode memory
		this.currperson.modeMemory[0] = Double.parseDouble(atts.getValue("mem_car"));
		this.currperson.modeMemory[1] = Double.parseDouble(atts.getValue("mem_pt"));
		this.currperson.modeMemory[2] = Double.parseDouble(atts.getValue("mem_walk"));
		this.currperson.modeMemory[3] = Double.parseDouble(atts.getValue("mem_bike"));
		// doubles
		this.currperson.setBetaPhi(Double.parseDouble(atts.getValue("beta_phi")));
		this.currperson.setBetaTheta(Double.parseDouble(atts.getValue("beta_theta")));
		this.currperson.setLambdaNew(Double.parseDouble(atts.getValue("lambda_new")));
		this.currperson.setLambdaMin(Double.parseDouble(atts.getValue("lambda_min")));
		this.currperson.setLambdaMax(Double.parseDouble(atts.getValue("lambda_max")));
		this.currperson.setTau(Double.parseDouble(atts.getValue("tau")));
		this.currperson.setDeltaMemory(Double.parseDouble(atts.getValue("delta_memory")));
		this.currperson.setDeltaSocial(Double.parseDouble(atts.getValue("delta_social")));
		this.currperson.setDeltaMerge(Double.parseDouble(atts.getValue("delta_merge")));
		this.currperson.setDeltaDecay(Double.parseDouble(atts.getValue("delta_decay")));
		// strings
		this.currperson.setBikeAvail(atts.getValue("bike_avail"));
		this.currperson.setHHId(atts.getValue("hh"));
		// ints
		this.currperson.setClubId(Integer.parseInt(atts.getValue("club")));
		this.currperson.setHHIncome(Integer.parseInt(atts.getValue("hhinc")));
		this.currperson.setHHSize(Integer.parseInt(atts.getValue("hhsiz")));
		// log
		
		// end by Haomin Hu 26 Jan
	}
}
