package tutorial;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup;
import org.matsim.core.controler.Controler;
import org.matsim.core.scenario.ScenarioUtils;

public class MyControler {

	public static void main(String[] args) {
		Config config = ConfigUtils.loadConfig("input/config.xml");
		Controler controler = new Controler(config);
		controler.setOverwriteFiles(true);
		
		
		// setting new function
		Network planCalcScoreNetwork = controler.getScenario().getNetwork();
		PlanCalcScoreConfigGroup planCalcScoreConfigGroup = controler.getConfig().planCalcScore();
		ShiftedScoringFunFactory factory = new ShiftedScoringFunFactory(planCalcScoreConfigGroup, planCalcScoreNetwork);
		controler.setScoringFunctionFactory(factory);
		
		// start simulation
		controler.run();
	}
}
