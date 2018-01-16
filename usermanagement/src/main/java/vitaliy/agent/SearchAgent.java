package vitaliy.agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.Collection;

import jade.core.AID;
import jade.core.Agent;
import vitaliy.User;
import vitaliy.db.DaoFactory;
import vitaliy.db.DatabaseException;

public class SearchAgent extends Agent {

	private AID[] aids;
	
	protected void setup() {
		super.setup();
		System.out.println(getAID().getName() + " started");
		
		DFAgentDescription description = new DFAgentDescription();
		description.setName(getAID());
		ServiceDescription serviceDescription = new ServiceDescription();
		serviceDescription.setName("JADE-searching");
		serviceDescription.setType("searching");
		description.addServices(serviceDescription);
		try {
			DFService.register(this, description);
		} catch (FIPAException e){
			e.printStackTrace();
		}
		
		addBehaviour(new TickerBehaviour(this, 60000) {

			
			

			protected void onTick() {
				DFAgentDescription agentDescription = new DFAgentDescription();
				ServiceDescription serviceDescription = new ServiceDescription();
				serviceDescription.setType("searching");
				agentDescription.addServices(serviceDescription);
				try {
					DFAgentDescription[] descriptions = DFService.search(myAgent, agentDescription);
					aids = new AID[descriptions.length];
					for(int i = 0; i < descriptions.length; i++) {
						DFAgentDescription d = descriptions[i];
						aids[i] = d.getName();
					}
				}catch (FIPAException e){
					e.printStackTrace();
				}
				
			}
			
		});
		addBehaviour(new RequestServer());
	}

	@Override
	protected void takeDown() {
		System.out.println(getAID().getName() + " terminated.");
		try {
			DFService.deregister(this);
		}catch (FIPAException e){
			e.printStackTrace();
		}
		super.takeDown();
	}
	public void search(String firstName, String lastName) {
		 try {
	            Collection<User> users = DaoFactory.getInstance().getUserDao().find(firstName, lastName);
	            if (users.size()>0) {
	            	showUsers(users);
	            } else {
	            	addBehaviour(new SearchReqestBehviour(aids, firstName, lastName));
	            }
	            
	        } catch (DatabaseException e) {
	            e.printStackTrace();
	}
    }

	void showUsers(Collection user) {
		
	}
}


