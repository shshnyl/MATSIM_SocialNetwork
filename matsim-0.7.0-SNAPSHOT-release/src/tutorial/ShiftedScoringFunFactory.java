package tutorial;

import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup;
import org.matsim.core.scoring.ScoringFunction;
import org.matsim.core.scoring.SumScoringFunction;
import org.matsim.core.scoring.SumScoringFunction.BasicScoring;
import org.matsim.core.scoring.functions.CharyparNagelActivityScoring;
import org.matsim.core.scoring.functions.CharyparNagelAgentStuckScoring;
import org.matsim.core.scoring.functions.CharyparNagelLegScoring;
import org.matsim.core.scoring.functions.CharyparNagelMoneyScoring;
import org.matsim.core.scoring.functions.CharyparNagelScoringFunctionFactory;
import org.matsim.core.scoring.functions.CharyparNagelScoringParameters;

public class ShiftedScoringFunFactory extends CharyparNagelScoringFunctionFactory {
	protected Network network;
	private final PlanCalcScoreConfigGroup config;
	private CharyparNagelScoringParameters params = null;
	
	public ShiftedScoringFunFactory(PlanCalcScoreConfigGroup config,
			Network network) {
		super(config, network);
		this.config = config;
		this.network = network;
	}
	
	
	/*
	public ScoringFunction createNewScoringFunction(Person person) {
		SumScoringFunction scoringFunctionSum = (SumScoringFunction) super.createNewScoringFunction(person);
		scoringFunctionSum.addScoringFunction((BasicScoring) new ShiftedScoringFun(person));
		return scoringFunctionSum;
	}
	/*
	public ScoringFunction createNewScoringFunction(Plan plan) {
		SumScoringFunction scoringFunctionSum = (SumScoringFunction) super.createNewScoringFunction(plan.getPerson());
		scoringFunctionSum.addScoringFunction((BasicScoring) new ShiftedScoringFun(plan));
		return scoringFunctionSum;
	}
	*/
	public ScoringFunction createNewScoringFunction(Person person) {
	
		if (this.params == null) {
			this.params = new CharyparNagelScoringParameters(this.config);
		}
		SumScoringFunction sumScoringFunction = new SumScoringFunction();
		sumScoringFunction.addScoringFunction(new CharyparNagelActivityScoring(this.params));
		sumScoringFunction.addScoringFunction(new ShiftedLegScoringFun(person, this.params, this.network));
		sumScoringFunction.addScoringFunction(new CharyparNagelMoneyScoring(this.params));
		sumScoringFunction.addScoringFunction(new CharyparNagelAgentStuckScoring(this.params));
		return sumScoringFunction;
	}
}
