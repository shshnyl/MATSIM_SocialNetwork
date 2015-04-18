/* *********************************************************************** *
 * project: org.matsim.*
 * Person.java
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2007, 2008 by the members listed in the COPYING,  *
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

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.core.replanning.selectors.RandomPlanSelector;
import org.matsim.population.Desires;
import org.matsim.utils.customize.Customizable;
import org.matsim.utils.customize.CustomizableImpl;
/**
 * Default implementation of {@link Person} interface.
 */
public class PersonImpl implements Person {

	private final static Logger log = Logger.getLogger(PersonImpl.class);

	private static final double CharyparNagelLegScoring = 0;

	private TreeSet<String> travelcards = null;
	protected Desires desires = null;

	private Plan selectedPlan = null;

	private Customizable customizableDelegate;
	protected List<Plan> plans = new ArrayList<Plan>(6);
	protected Id<Person> id;
	private String sex;
	private int age = Integer.MIN_VALUE;
	private String hasLicense;
	private String carAvail;
	private Boolean isEmployed = false;
	// by HaominHU, Jan 24 2015, adding new variables 
	private String bikeAvail;
	private double beta_phi;
	private double beta_theta;
	private double lambda_new;
	private double lambda_min;
	private double lambda_max;
	private double tau;
	private double delta_memory;
	private double delta_social;
	private double delta_merge;
	
	private int clubId;
	private int race;
	private String houseHoldId;
	private int houseHoldIncome;
	private int houseHoldSize;
	
	
	// for mode attitude
	public double modeAtt[] = {0, 0, 0, 0};
	// for mode memory value
	public double modeAttMemory[] = {0, 0, 0, 0};
	public double modeMemory[] = {0, 0, 0, 0};
	public double totalMemScore[] = {0, 0, 0, 0};
	public double totalMemDist[] = {0, 0, 0, 0};
	// for mode social influence
	public double modeAttSNS[] = {0, 0, 0, 0};
	public double modeSNSImpact[] = {0, 0, 0, 0};


	// by Haomin Hu, 20 Mar
	private double delta_decay;
	public ArrayList<PersonImpl> friends = new ArrayList<PersonImpl>();
	public ArrayList<Double> relations = new ArrayList<Double>();

	// by Haomin Hu, 6 Feb, adding function to update new memory and resulted mode att changes
	public void modifyMemory() {
		// update the memories and mode atts
		for (int mode = 0; mode < this.modeAtt.length; mode++) {				
			double phi = 0;
			if (totalMemDist[mode] != 0.0) 
				phi = totalMemScore[mode] / totalMemDist[mode]; 
			modeMemory[mode] =  beta_theta * modeMemory[mode] + (1 - beta_theta) * phi; 
				
			double coff = 1.0;
			if (this.modeMemory[mode] != 0.0) 
				coff = delta_memory +  (1 - delta_memory) * phi / modeMemory[mode];
			modeAttMemory[mode] = coff * modeAtt[mode];
				
			totalMemDist[mode] = 0;
			totalMemScore[mode] = 0;
		}
	}
	
	// by Haomin Hu, 20 Mar, adding function to update sns strength
	public void updateSNS() {
		for (int i = 0; i < friends.size(); i++) {
			double relation = relations.get(i);
			// update relation
			if (hasCommonAct(friends.get(i).getSelectedPlan())) 
				relation *= (1 + 0.5 / Math.pow(relation, 3));
			else 
				relation *= delta_decay;
			// check the lower bound
			if (hasCommonGroup(friends.get(i))) { // if in same group, 
				relation = Math.max(relation, this.getLambdaMin());
				relation = Math.max(relation, friends.get(i).getLambdaMin());
			}
			relations.set(i, relation);
		}
	}
	
	// by Haomin Hu, 27 Mar, determine whether there is common acts or not
	private boolean hasCommonAct(Plan friendPlan) {
		List<PlanElement> selfActs = selectedPlan.getPlanElements();
		List<PlanElement> friendActs = friendPlan.getPlanElements();
		for (int i = 0; i < selfActs.size(); i += 2) { // even ones are activity 
			for (int j = 0; j < friendActs.size(); j += 2) { // even ones are activity
				if (isCommonAct((ActivityImpl) selfActs.get(i), (ActivityImpl) friendActs.get(j)))
					return true;
			}
		}
		return false;
	}
	
	// by Haomin Hu, 27 Mar, 
	private boolean isCommonAct(ActivityImpl act1, ActivityImpl act2) {
		if (act1.getFacilityId() == act2.getFacilityId()) {
			if (act1.getStartTime() <= act2.getEndTime() && act2.getStartTime() <= act1.getEndTime()) {
				if (act1.getType() == act2.getType())
					return true;
			}
		}
		return false;
	}
	
	// by Haomin Hu, 27 Mar, determine whether they are in same group
	private boolean hasCommonGroup(PersonImpl friend) {
		if (friend.getClubId() == this.getClubId() || friend.getHHId() == this.getHHId())
			return true;
		else
			return false;
	}
	
	// by Haomin Hu, 26 Feb, adding function to modify SNS relationships and resulted mode changes
	public void modifySNS() {
		calcSNSModeImpact();
		for (int mode = 0; mode < modeAtt.length; mode++) {
			double coff = 1.0;
			if (modeSNSImpact[mode] != 0.0)
				coff = delta_social + (1 - delta_social) * (modeAtt[mode] / modeSNSImpact[mode]);
			modeAttSNS[mode] = coff * modeAtt[mode];
		}
	}

	// by Haomin Hu, 21 Mar, adding function to calculate sns influence
	public void calcSNSModeImpact() {
		for (int mode = 0; mode < modeAtt.length; mode++) {
			double totalWeightedSum = 0.0, totalWeights = 0.0;
			for (int i = 0; i < friends.size(); i++) {
				totalWeightedSum += (relations.get(i)) * friends.get(i).modeAtt[mode];
				totalWeights += (relations.get(i));
			}
			
			if (totalWeights == 0.0) 
				modeSNSImpact[mode] = 0.00;
			else 
				modeSNSImpact[mode] = (totalWeightedSum / totalWeights);
		}
	}
	
	public void mergeMemSNS() {
		for (int mode = 0; mode < modeAtt.length; mode++) {
			modeAtt[mode] = 
					delta_merge * modeAttMemory[mode] + 
					(1 - delta_merge) * modeAttSNS[mode];
		}
	}
	
	@Deprecated // please try to use the factory: pop.getFactory().create...
	public PersonImpl(final Id<Person> id) {
		this.id = id;
	}

	@Override
	public final Plan getSelectedPlan() {
		return this.selectedPlan;
	}

	@Override
	public boolean addPlan(final Plan plan) {
		plan.setPerson(this);
		// Make sure there is a selected plan if there is at least one plan
		if (this.selectedPlan == null) this.selectedPlan = plan;
		return this.plans.add(plan);
	}

	@Deprecated // use methods of interface Person
	public PlanImpl createAndAddPlan(final boolean selected) {
		PlanImpl p = new PlanImpl(this);
		this.addPlan(p);
		if (selected) {
			this.setSelectedPlan(p);
		}
		return p;
	}

	@Override
	public final void setSelectedPlan(final Plan selectedPlan) {
		if (selectedPlan != null && !plans.contains( selectedPlan )) {
			throw new IllegalStateException("The plan to be set as selected is not null nor stored in the person's plans");
		}
		this.selectedPlan = selectedPlan;
	}

	public void removeUnselectedPlans() {
		for (Iterator<Plan> iter = this.getPlans().iterator(); iter.hasNext(); ) {
			Plan plan = iter.next();
			if (!plan.isSelected()) {
				iter.remove();
			}
		}
	}

	@Override
	public Plan createCopyOfSelectedPlanAndMakeSelected() {
		Plan oldPlan = this.getSelectedPlan();
		if (oldPlan == null) {
			return null;
		}
		PlanImpl newPlan = new PlanImpl(oldPlan.getPerson());
		newPlan.copyFrom(oldPlan);
		this.getPlans().add(newPlan);
		this.setSelectedPlan(newPlan);
		return newPlan;
	}

	@Override
	public Id<Person> getId() {
		return this.id;
	}

    // Not on interface. Only to be used for demand generation.
	public void setId(final Id<Person> id) {
		this.id = id;
	}

	@Deprecated // use PersonAttributes
	public final String getSex() {
		return this.sex;
	}

	@Deprecated // use PersonAttributes
	public final int getAge() {
		return this.age;
	}

	@Deprecated // use PersonAttributes
	public final String getLicense() {
		return this.hasLicense;
	}

	@Deprecated // use PersonAttributes
	public final boolean hasLicense() {
		return ("yes".equals(this.hasLicense)) || ("true".equals(this.hasLicense));
	}

	@Deprecated // use PersonAttributes
	public final String getCarAvail() {
		return this.carAvail;
	}

	@Deprecated // use PersonAttributes
	public final Boolean isEmployed() {
		return this.isEmployed;
	}

	@Deprecated // use PersonAttributes
	public void setAge(final int age) {
		if ((age < 0) && (age != Integer.MIN_VALUE)) {
			throw new NumberFormatException("A person's age has to be an integer >= 0.");
		}
		this.age = age;
	}

	@Deprecated // use PersonAttributes
	public final void setSex(final String sex) {
		this.sex = (sex == null) ? null : sex.intern();
	}

	@Deprecated // use PersonAttributes
	public final void setLicence(final String licence) {
		this.hasLicense = (licence == null) ? null : licence.intern();
	}

	@Deprecated // use PersonAttributes
	public final void setCarAvail(final String carAvail) {
		this.carAvail = (carAvail == null) ? null : carAvail.intern();
	}

	@Deprecated // use PersonAttributes
	public final void setEmployed(final Boolean employed) {
		this.isEmployed = employed;
	}

	@Deprecated // use PersonAttributes
	public final Desires createDesires(final String desc) {
		if (this.desires == null) {
			this.desires = new Desires(desc);
		}
		return this.desires;
	}


	@Deprecated // use PersonAttributes
	public final void addTravelcard(final String type) {
		if (this.travelcards == null) {
			this.travelcards = new TreeSet<String>();
		}
		if (this.travelcards.contains(type)) {
			log.info(this + "[type=" + type + " already exists]");
		} else {
			this.travelcards.add(type.intern());
		}
	}


	@Deprecated // use PersonAttributes
	public final TreeSet<String> getTravelcards() {
		return this.travelcards;
	}


	@Deprecated // use PersonAttributes
	public final Desires getDesires() {
		return this.desires;
	}




	@Override
	public final String toString() {
		StringBuilder b = new StringBuilder();
		b.append("[id=").append(this.getId()).append("]");
		b.append("[sex=").append(this.getSex()).append("]");
		b.append("[age=").append(this.getAge()).append("]");
		b.append("[license=").append(this.getLicense()).append("]");
		b.append("[car_avail=").append(this.getCarAvail()).append("]");
		b.append("[employed=").append((isEmployed() == null ? "null" : (isEmployed ? "yes" : "no"))).append("]");
		b.append("[travelcards=").append(this.getTravelcards() == null ? "null" : this.getTravelcards().size()).append("]");
		b.append("[nof_plans=").append(this.getPlans() == null ? "null" : this.getPlans().size()).append("]");
	  return b.toString();
	}

	@Override
	public boolean removePlan(final Plan plan) {
		boolean result = this.getPlans().remove(plan);
		if ((this.getSelectedPlan() == plan) && result) {
			this.setSelectedPlan(new RandomPlanSelector<Plan, Person>().selectPlan(this));
		}
		return result;
	}

	@Override
	public List<Plan> getPlans() {
		return this.plans;
	}


	@Override
	public Map<String, Object> getCustomAttributes() {
		if (this.customizableDelegate == null) {
			this.customizableDelegate = new CustomizableImpl();
		}
		return this.customizableDelegate.getCustomAttributes();
	}
	
	// by HaominHU, Jan 24 2015, adding new methods related to these newly added variables 

	public void setRace(int race) {
		this.race = race;
	}
	public int getRace() {
		return this.race;
	}
	
	public void setBikeAvail(String bikeAvail) {
		this.bikeAvail = bikeAvail;
	}
	public String getBikeAvail() {
		return this.bikeAvail;
	}
	
	public void setBetaPhi(double val) {
		this.beta_phi = val;
	}
	public double getBetaPhi() {
		return this.beta_phi;
	}

	public void setBetaTheta(double val) {
		this.beta_theta = val;
	}
	public double getBetaTheta() {
		return this.beta_theta;
	}
	
	public void setLambdaNew(double val) {
		this.lambda_new = val;
	}
	public double getLambdaNew() {
		return this.lambda_new;
	}
	
	public void setLambdaMin(double val) {
		this.lambda_min = val;
	}
	public double getLambdaMin() {
		return this.lambda_min;
	}
	
	public void setLambdaMax(double val) {
		this.lambda_max = val;
	}
	public double getLambdaMax() {
		return this.lambda_max;
	}
	
	public void setTau(double val) {
		this.tau = val;
	}
	public double getTau() {
		return this.tau;
	}
	
	public void setDeltaMemory(double val) {
		this.delta_memory = val;
	}
	public double getDeltaMemory() {
		return this.delta_memory;
	}
	
	public void setDeltaSocial(double val) {
		this.delta_social = val;
	}
	public double getDeltaSocial() {
		return this.delta_social;
	}
	
	public void setDeltaMerge(double val) {
		this.delta_merge = val;
	}
	public double getDeltaMerge() {
		return this.delta_merge;
	}
	
	public void setHHId(String HHId) {
		this.houseHoldId = HHId;
	}
	public String getHHId() {
		return this.houseHoldId;
	}
	
	public void setHHIncome(int val) {
		this.houseHoldIncome = val;
	}
	public int getHHIncome() {
		return this.houseHoldIncome;
	}
	
	public void setHHSize(int val) {
		this.houseHoldSize = val;
	}
	public int getHHSize() {
		return this.houseHoldSize;
	}
	
	public void setClubId(int val) {
		this.clubId = val;
	}
	public int getClubId() {
		return this.clubId;
	}
	
	public void setDeltaDecay(double val) {
		this.delta_decay = val;
	}
	public double getDeltaDecay() {
		return this.delta_decay;
	}
}
