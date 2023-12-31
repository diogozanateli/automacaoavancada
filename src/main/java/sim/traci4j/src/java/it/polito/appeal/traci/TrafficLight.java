

/*
    THIS FILE IS GENERATED AUTOMATICALLY. DO NOT EDIT: CHANGES WILL BE OVERWRITTEN.
    File generated by traciObject.xslt.
*/

/*   
    Copyright (C) 2013 ApPeAL Group, Politecnico di Torino

    This file is part of TraCI4J.

    TraCI4J is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    TraCI4J is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with TraCI4J.  If not, see <http://www.gnu.org/licenses/>.
*/


package sim.traci4j.src.java.it.polito.appeal.traci;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**

	Representation of a traffic light in the SUMO environment.
	@see <a href="http://sumo.dlr.de/wiki/Simulation/Traffic_Lights">SUMO documentation</a>
	@see <a href="http://sumo.dlr.de/wiki/TraCI/Traffic_Lights_Value_Retrieval">TraCI documentation</a>
	@author Enrico Gueli &lt;enrico.gueli@polito.it&gt;
	
*/
public class TrafficLight 
extends TraciObject<TrafficLight.Variable>
implements StepAdvanceListener
{

	/**
	 * Enumerates all the read queries. Each value can be used as an argument
	 * for {@link TraciObject#getReadQuery(Enum)}.
	 * 
	 * @author Enrico Gueli &lt;enrico.gueli@polito.it&gt;
	 */	
	public static enum Variable {
		
		/** 
		 * Query "ReadState"
		 * @see #queryReadState
		 */
		STATE,
		
		/** 
		 * Query "ReadDefaultCurrentPhaseDuration"
		 * @see #queryReadDefaultCurrentPhaseDuration
		 */
		DEFAULT_CURRENT_PHASE_DURATION,
		
		/** 
		 * Query "ReadControlledLanes"
		 * @see #queryReadControlledLanes
		 */
		CONTROLLED_LANES,
		
		/** 
		 * Query "ReadControlledLinks"
		 * @see #queryReadControlledLinks
		 */
		CONTROLLED_LINKS,
		
		/** 
		 * Query "ReadCurrentPhase"
		 * @see #queryReadCurrentPhase
		 */
		CURRENT_PHASE,
		
		/** 
		 * Query "ReadCurrentProgram"
		 * @see #queryReadCurrentProgram
		 */
		CURRENT_PROGRAM,
		
		/** 
		 * Query "ReadCompleteDefinition"
		 * @see #queryReadCompleteDefinition
		 */
		COMPLETE_DEFINITION,
		
		/** 
		 * Query "ReadAssumedNextSwitchTime"
		 * @see #queryReadAssumedNextSwitchTime
		 */
		ASSUMED_NEXT_SWITCH_TIME,
		
	}
	
	
	private final ChangeLightsStateQuery csqvar_ChangeLightsState;
	
	private final ChangeObjectVarQuery.ChangeIntegerQ csqvar_ChangePhaseIndex;
	
	private final ChangeObjectVarQuery.ChangeStringQ csqvar_ChangeProgram;
	
	private final ChangeObjectVarQuery.ChangeIntegerQ csqvar_ChangePhaseDuration;
	
	private final ChangeCompleteProgramQuery csqvar_ChangeCompleteProgramDefinition;
	TrafficLight (
		DataInputStream dis,
		DataOutputStream dos, 
		String id
		
			, Repository<Lane> repoLane
	) {
		super(id, Variable.class);

		/*
		 * initialization of read queries
		 */
		
		addReadQuery(Variable.STATE, 
				new ReadTLStateQuery (dis, dos,
						sim.traci4j.src.java.it.polito.appeal.traci.protocol.Constants.CMD_GET_TL_VARIABLE
	, 
				id,
						sim.traci4j.src.java.it.polito.appeal.traci.protocol.Constants.TL_RED_YELLOW_GREEN_STATE
			
				
				));
		
		addReadQuery(Variable.DEFAULT_CURRENT_PHASE_DURATION, 
				new ReadObjectVarQuery.IntegerQ (dis, dos,
						sim.traci4j.src.java.it.polito.appeal.traci.protocol.Constants.CMD_GET_TL_VARIABLE
	, 
				id,
						sim.traci4j.src.java.it.polito.appeal.traci.protocol.Constants.TL_PHASE_DURATION
			
				
				));
		
		addReadQuery(Variable.CONTROLLED_LANES, 
				new LaneListQuery (dis, dos,
						sim.traci4j.src.java.it.polito.appeal.traci.protocol.Constants.CMD_GET_TL_VARIABLE
	, 
				id,
						sim.traci4j.src.java.it.polito.appeal.traci.protocol.Constants.TL_CONTROLLED_LANES
			
				, repoLane
				
				));
		
		addReadQuery(Variable.CONTROLLED_LINKS, 
				new ReadControlledLinksQuery (dis, dos,
						sim.traci4j.src.java.it.polito.appeal.traci.protocol.Constants.CMD_GET_TL_VARIABLE
	, 
				id,
						sim.traci4j.src.java.it.polito.appeal.traci.protocol.Constants.TL_CONTROLLED_LINKS
			
				, repoLane
				
				));
		
		addReadQuery(Variable.CURRENT_PHASE, 
				new ReadObjectVarQuery.IntegerQ (dis, dos,
						sim.traci4j.src.java.it.polito.appeal.traci.protocol.Constants.CMD_GET_TL_VARIABLE
	, 
				id,
						sim.traci4j.src.java.it.polito.appeal.traci.protocol.Constants.TL_CURRENT_PHASE
			
				
				));
		
		addReadQuery(Variable.CURRENT_PROGRAM, 
				new ReadObjectVarQuery.StringQ (dis, dos,
						sim.traci4j.src.java.it.polito.appeal.traci.protocol.Constants.CMD_GET_TL_VARIABLE
	, 
				id,
						sim.traci4j.src.java.it.polito.appeal.traci.protocol.Constants.TL_CURRENT_PROGRAM
			
				
				));
		
		addReadQuery(Variable.COMPLETE_DEFINITION, 
				new ReadCompleteDefinitionQuery (dis, dos,
						sim.traci4j.src.java.it.polito.appeal.traci.protocol.Constants.CMD_GET_TL_VARIABLE
	, 
				id,
						sim.traci4j.src.java.it.polito.appeal.traci.protocol.Constants.TL_COMPLETE_DEFINITION_RYG
			
				
				));
		
		addReadQuery(Variable.ASSUMED_NEXT_SWITCH_TIME, 
				new ReadObjectVarQuery.IntegerQ (dis, dos,
						sim.traci4j.src.java.it.polito.appeal.traci.protocol.Constants.CMD_GET_TL_VARIABLE
	, 
				id,
						sim.traci4j.src.java.it.polito.appeal.traci.protocol.Constants.TL_NEXT_SWITCH
				
				));
		

		/*
		 * initialization of change state queries
		 */
		
		csqvar_ChangeLightsState = new ChangeLightsStateQuery(dis, dos
		
		, id
		)
		;
		
		csqvar_ChangePhaseIndex = new ChangeObjectVarQuery.ChangeIntegerQ(dis, dos
		, sim.traci4j.src.java.it.polito.appeal.traci.protocol.Constants.CMD_SET_TL_VARIABLE
	
		, id
		, sim.traci4j.src.java.it.polito.appeal.traci.protocol.Constants.TL_PHASE_INDEX)
		;
		
		csqvar_ChangeProgram = new ChangeObjectVarQuery.ChangeStringQ(dis, dos
		, sim.traci4j.src.java.it.polito.appeal.traci.protocol.Constants.CMD_SET_TL_VARIABLE
	
		, id
		, sim.traci4j.src.java.it.polito.appeal.traci.protocol.Constants.TL_PROGRAM)
		;
		
		csqvar_ChangePhaseDuration = new ChangeObjectVarQuery.ChangeIntegerQ(dis, dos
		, sim.traci4j.src.java.it.polito.appeal.traci.protocol.Constants.CMD_SET_TL_VARIABLE
	
		, id
		, sim.traci4j.src.java.it.polito.appeal.traci.protocol.Constants.TL_PHASE_DURATION
			)
		;
		
		csqvar_ChangeCompleteProgramDefinition = new ChangeCompleteProgramQuery(dis, dos
		, sim.traci4j.src.java.it.polito.appeal.traci.protocol.Constants.CMD_SET_TL_VARIABLE
	
		, id
		, sim.traci4j.src.java.it.polito.appeal.traci.protocol.Constants.TL_COMPLETE_PROGRAM_RYG
			)
		;
		
	
	}
	
	
	public void nextStep(double step) {
		
		getReadQuery(Variable.STATE).setObsolete();
		
		getReadQuery(Variable.DEFAULT_CURRENT_PHASE_DURATION).setObsolete();
		
		getReadQuery(Variable.CURRENT_PHASE).setObsolete();
		
		getReadQuery(Variable.CURRENT_PROGRAM).setObsolete();
		
		getReadQuery(Variable.ASSUMED_NEXT_SWITCH_TIME).setObsolete();
		
	}
	
	
	
	
	
	/**
	 * @return the instance of {@link ReadTLStateQuery} relative to this query.
	 */
	public ReadTLStateQuery queryReadState() {
		return (ReadTLStateQuery) getReadQuery(Variable.STATE);
	}
	
	
	/**
	 * @return the instance of {@link ReadObjectVarQuery} relative to this query.
	 */
	public ReadObjectVarQuery<java.lang.Integer> queryReadDefaultCurrentPhaseDuration() {
		return (ReadObjectVarQuery.IntegerQ) getReadQuery(Variable.DEFAULT_CURRENT_PHASE_DURATION);
	}
	
	
	/**
	 * Executes an instance of {@link ReadObjectVarQuery} relative to this query,
	 * and returns the corresponding value.
	 */
	public java.lang.Integer getDefaultCurrentPhaseDuration() throws IOException {
		return ((ReadObjectVarQuery.IntegerQ) getReadQuery(Variable.DEFAULT_CURRENT_PHASE_DURATION)).get();
	}
	
	/**
	 * @return the instance of {@link LaneListQuery} relative to this query.
	 */
	public LaneListQuery queryReadControlledLanes() {
		return (LaneListQuery) getReadQuery(Variable.CONTROLLED_LANES);
	}
	
	
	/**
	 * @return the instance of {@link ReadControlledLinksQuery} relative to this query.
	 */
	public ReadControlledLinksQuery queryReadControlledLinks() {
		return (ReadControlledLinksQuery) getReadQuery(Variable.CONTROLLED_LINKS);
	}
	
	
	/**
	 * @return the instance of {@link ReadObjectVarQuery.IntegerQ} relative to this query.
	 */
	public ReadObjectVarQuery.IntegerQ queryReadCurrentPhase() {
		return (ReadObjectVarQuery.IntegerQ) getReadQuery(Variable.CURRENT_PHASE);
	}
	
	
	/**
	 * @return the instance of {@link ReadObjectVarQuery.StringQ} relative to this query.
	 */
	public ReadObjectVarQuery.StringQ queryReadCurrentProgram() {
		return (ReadObjectVarQuery.StringQ) getReadQuery(Variable.CURRENT_PROGRAM);
	}
	
	
	/**
	 * @return the instance of {@link ReadCompleteDefinitionQuery} relative to this query.
	 */
	public ReadCompleteDefinitionQuery queryReadCompleteDefinition() {
		return (ReadCompleteDefinitionQuery) getReadQuery(Variable.COMPLETE_DEFINITION);
	}
	
	
	/**
	 * @return the instance of {@link ReadObjectVarQuery.IntegerQ} relative to this query.
	 */
	public ReadObjectVarQuery.IntegerQ queryReadAssumedNextSwitchTime() {
		return (ReadObjectVarQuery.IntegerQ) getReadQuery(Variable.ASSUMED_NEXT_SWITCH_TIME);
	}
	
	
	/**
	 * @return the instance of {@link ChangeLightsStateQuery} relative to this query.
	 */
	public ChangeLightsStateQuery queryChangeLightsState() {
		return csqvar_ChangeLightsState;
	}
	
	
	/**
	 * Execute an instance of ChangeLightsStateQuery set to the given value.
	 * 
	 * This setter method is equivalent to queryChangeLightsState().setValue(value).run().
	 */
	public void changeLightsState(TLState value) throws IOException {
		ChangeLightsStateQuery q = csqvar_ChangeLightsState;
		q.setValue(value);
		q.run();
	}
	
	/**
	 * @return the instance of {@link ChangeObjectVarQuery.ChangeIntegerQ} relative to this query.
	 */
	public ChangeObjectVarQuery.ChangeIntegerQ queryChangePhaseIndex() {
		return csqvar_ChangePhaseIndex;
	}
	
	
	/**
	 * Execute an instance of ChangeObjectVarQuery.ChangeIntegerQ set to the given value.
	 * 
	 * This setter method is equivalent to queryChangePhaseIndex().setValue(value).run().
	 */
	public void changePhaseIndex(Integer value) throws IOException {
		ChangeObjectVarQuery.ChangeIntegerQ q = csqvar_ChangePhaseIndex;
		q.setValue(value);
		q.run();
	}
	
	/**
	 * @return the instance of {@link ChangeObjectVarQuery.ChangeStringQ} relative to this query.
	 */
	public ChangeObjectVarQuery.ChangeStringQ queryChangeProgram() {
		return csqvar_ChangeProgram;
	}
	
	
	/**
	 * Execute an instance of ChangeObjectVarQuery.ChangeStringQ set to the given value.
	 * 
	 * This setter method is equivalent to queryChangeProgram().setValue(value).run().
	 */
	public void changeProgram(String value) throws IOException {
		ChangeObjectVarQuery.ChangeStringQ q = csqvar_ChangeProgram;
		q.setValue(value);
		q.run();
	}
	
	/**
	 * @return the instance of {@link ChangeObjectVarQuery.ChangeIntegerQ} relative to this query.
	 */
	public ChangeObjectVarQuery.ChangeIntegerQ queryChangePhaseDuration() {
		return csqvar_ChangePhaseDuration;
	}
	
	
	/**
	 * Execute an instance of ChangeObjectVarQuery.ChangeIntegerQ set to the given value.
	 * 
	 * This setter method is equivalent to queryChangePhaseDuration().setValue(value).run().
	 */
	public void changePhaseDuration(Integer value) throws IOException {
		ChangeObjectVarQuery.ChangeIntegerQ q = csqvar_ChangePhaseDuration;
		q.setValue(value);
		q.run();
	}
	
	/**
	 * @return the instance of {@link ChangeCompleteProgramQuery} relative to this query.
	 */
	public ChangeCompleteProgramQuery queryChangeCompleteProgramDefinition() {
		return csqvar_ChangeCompleteProgramDefinition;
	}
	
	
	/**
	 * Execute an instance of ChangeCompleteProgramQuery set to the given value.
	 * 
	 * This setter method is equivalent to queryChangeCompleteProgramDefinition().setValue(value).run().
	 */
	public void changeCompleteProgramDefinition(Logic value) throws IOException {
		ChangeCompleteProgramQuery q = csqvar_ChangeCompleteProgramDefinition;
		q.setValue(value);
		q.run();
	}
	
}

