package tutorial;

import java.io.PrintWriter;

import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.core.population.PersonImpl;
//import org.matsim.core.scoring.ScoringFunctionAccumulator.BasicScoring;
import org.matsim.core.scoring.SumScoringFunction.BasicScoring;
import org.matsim.core.scoring.functions.CharyparNagelLegScoring;
import org.matsim.core.scoring.functions.CharyparNagelScoringParameters;
import org.matsim.pt.transitSchedule.api.TransitSchedule;

public class ShiftedLegScoringFun extends CharyparNagelLegScoring {
	
	private double score = 0;
	private Person person;
	private CharyparNagelScoringParameters params;
	private Network network;
	
	public ShiftedLegScoringFun(Person person, final CharyparNagelScoringParameters params, Network network) {
		super(params, network);
		this.person =  person;
	}
	
	public ShiftedLegScoringFun(Person person, final CharyparNagelScoringParameters params, Network network, TransitSchedule transitSchedule) {
		super(params, network, transitSchedule);
		this.person = person;
	}
	
	protected double calcLegScore(final double departureTime, final double arrivalTime, Leg leg) {
		int mode = -1;
		PersonImpl tmpPerson = (PersonImpl) this.person;
        if (leg.getMode().equals(TransportMode.car)) mode = 0;
        else if (leg.getMode().equals(TransportMode.pt) || leg.getMode().equals(TransportMode.transit_walk)) mode = 1;
        else if (leg.getMode().equals(TransportMode.walk)) mode = 2;
        else if (leg.getMode().equals(TransportMode.bike)) mode = 3;
        else { System.out.println("BY HaominHU: NOSUCHMODE!");System.out.println(leg.getMode());System.exit(-1);}
        // by HHM, done
        double rawScore = super.calcLegScore(departureTime, arrivalTime, leg);
        double rawDist = leg.getRoute().getDistance();
        //System.out.println(tmpPerson.toString());
        //System.out.println("MODE is: " + mode);
        //System.out.println("score is: " + rawScore);
        //System.out.println("dist is: " + rawDist);
       ///System.out.println("travel time is: " + leg.getTravelTime());
        
        tmpPerson.totalMemScore[mode] += 100000 * rawScore;
        tmpPerson.totalMemDist[mode] += rawDist;
		return rawScore + tmpPerson.modeAtt[mode] * tmpPerson.getBetaPhi();
	}
}
