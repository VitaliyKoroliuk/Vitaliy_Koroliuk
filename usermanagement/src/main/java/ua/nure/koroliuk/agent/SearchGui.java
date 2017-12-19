package main.java.ua.nure.koroliuk.agent;

import main.java.ua.nure.chub.User;
import main.java.ua.nure.chub.gui.UserTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedList;

public class SearchGui extends JFrame {
    private SearchAgent agent;

    private JPanel contentPanel;

    private JPanel tablePanel;

    private JTable table;

    public SearchGui(SearchAgent agent) {
        this.agent = agent;
        initialize();
    }

    public static void main(String[] args) {
        SearchGui gui = new SearchGui(new SearchAgent());
        gui.setVisible(true);
    }

    private void initialize() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setTitle("Searcher");
        this.setContentPane(getContentPanel());
    }

    private JPanel getContentPanel() {
        if (contentPanel == null) {
            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(getSearchPanel(), BorderLayout.NORTH);
            contentPanel.add(new JScrollPane(getTablePanel()), BorderLayout.CENTER);
        }
        return contentPanel;
    }

    private JPanel getTablePanel() {
        if (tablePanel == null) {
            tablePanel = new JPanel(new BorderLayout());
            tablePanel.add(getTable(), BorderLayout.CENTER);
        }
        return tablePanel;
    }

    private JTable getTable() {
        if (table == null) {
            table = new JTable(new UserTableModel(new LinkedList<User>()));
        }
        return table;
    }

    private JPanel getSearchPanel() {
        return new SearchPanel(agent);
    }

    public void addUsers(Collection<User> users) {
        System.out.println("addUsers :" + users);
        UserTableModel model = (UserTableModel) getTable().getModel();
        model.addUsers(users);
        System.out.println(users);
        this.repaint();
    }

    private void clearUsers() {
        System.out.println("clearUsers");
        UserTableModel model = (UserTableModel) getTable().getModel();
        model.clearUsers();
        this.repaint();
    }

    class SearchPanel extends JPanel implements ActionListener {

        SearchAgent agent;

        private JPanel buttonPanel;

        private JPanel fieldPanel;

        private JButton cancelButton;

        private JButton searchButton;

        private JTextField firstNameField;

        private JTextField lastNameField;


        public SearchPanel(SearchAgent agent) {
            this.agent = agent;
            initialize();
        }

        private void initialize() {
            this.setName("addPanel");
            this.setLayout(new BorderLayout());
            this.add(getFieldPanel(), BorderLayout.NORTH);
            this.add(getButtonPanel(), BorderLayout.SOUTH);

        }

        private JPanel getButtonPanel() {
            if (buttonPanel == null) {
                buttonPanel = new JPanel();
                buttonPanel.add(getSearchButton(), null);
                buttonPanel.add(getCancelButton(), null);
            }
            return buttonPanel;
        }

        private JButton getCancelButton() {
            if (cancelButton == null) {
                cancelButton = new JButton();
                cancelButton.setText("Cancel");
                cancelButton.setName("cancelButton");
                cancelButton.setActionCommand("cancel");
                cancelButton.addActionListener(this);
            }
            return cancelButton;
        }

        private JButton getSearchButton() {
            if (searchButton == null) {
                searchButton = new JButton();
                searchButton.setText("search");
                searchButton.setName("search");
                searchButton.setActionCommand("ok");
                searchButton.addActionListener(this);
            }
            return searchButton;
        }

        private JPanel getFieldPanel() {
            if (fieldPanel == null) {
                fieldPanel = new JPanel();
                fieldPanel.setLayout(new GridLayout(2, 3));
                addLabeledField(fieldPanel, "FirstName", getFirstNameField());
                fieldPanel.add(getSearchButton());
                addLabeledField(fieldPanel, "LastName", getLastNameField());
                fieldPanel.add(getCancelButton());
            }
            return fieldPanel;
        }

        protected JTextField getLastNameField() {
            if (lastNameField == null) {
                lastNameField = new JTextField();
                lastNameField.setName("lastNameField");
            }
            return lastNameField;
        }

        private void addLabeledField(JPanel panel, String labelText,
                                     JTextField textField) {
            JLabel label = new JLabel(labelText);
            label.setLabelFor(textField);
            panel.add(label);
            panel.add(textField);
        }

        protected JTextField getFirstNameField() {
            if (firstNameField == null) {
                firstNameField = new JTextField();
                firstNameField.setName("firstNameField");
            }
            return firstNameField;
        }

        protected void doAction(ActionEvent e) {
            if ("ok".equalsIgnoreCase(e.getActionCommand())) {
                String firstName = getFirstNameField().getText();
                String lastName = getLastNameField().getText();
                clearUsers();
                agent.search(firstName, lastName);
            }
            clearFields();
        }

        public void actionPerformed(ActionEvent e) {
            doAction(e);
        }

        private void clearFields() {
            getFirstNameField().setText("");
            getLastNameField().setText("");
        }
    }
}
