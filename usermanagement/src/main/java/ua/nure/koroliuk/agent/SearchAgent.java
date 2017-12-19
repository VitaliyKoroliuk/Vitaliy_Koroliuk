package main.java.ua.nure.koroliuk.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import main.java.ua.nure.chub.User;
import main.java.ua.nure.chub.db.DAOFactory;
import main.java.ua.nure.chub.db.DatabaseException;

import java.util.Collection;

public class SearchAgent extends Agent {
    private AID[] aids;
    private SearchGui gui;

    public SearchAgent() {
        gui = new SearchGui(this);
        gui.setVisible(true);
    }

    @Override
    protected void setup() {
        super.setup();
        System.out.println(getAID().getName() + " started");

        gui = new SearchGui(this);
        gui.setVisible(true);

        DFAgentDescription description = new DFAgentDescription();
        description.setName(getAID());
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setName("JADE-searching");
        serviceDescription.setType("searching");
        description.addServices(serviceDescription);
        try {
            DFService.register(this, description);
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        addBehaviour(new TickerBehaviour(this, 60000) {

            @Override
            protected void onTick() {
                DFAgentDescription agentDescription = new DFAgentDescription();
                ServiceDescription serviceDescription = new ServiceDescription();
                serviceDescription.setType("searching");
                agentDescription.addServices(serviceDescription);
                try {
                    DFAgentDescription[] descriptions =
                            DFService.search(myAgent, agentDescription);
                    aids = new AID[descriptions.length];
                    for (int i = 0; i < descriptions.length; i++) {
                        DFAgentDescription d = descriptions[i];
                        aids[i] = d.getName();
                    }
                } catch (FIPAException e) {
                    e.printStackTrace();
                }

            }
        });
        addBehaviour(new RequestServer());
    }


    @Override
    protected void takeDown() {
        super.takeDown();
        System.out.println(getAID().getName() + " terminated.");
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        gui.setVisible(false);
        gui.dispose();
        super.takeDown();
    }

    public void search(String firstName, String lastName) {
        try {
            Collection<User> users = DAOFactory.getInstance().getUserDAO().find(firstName, lastName);
            if (users.size() > 0) {
                showUsers(users);
            } else {
                addBehaviour(new SearchRequestBehaviour(aids, firstName, lastName));
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    void showUsers(Collection<User> user) {
        gui.addUsers(user);
    }

}
