package main.java.ua.nure.koroliuk.gui;

import main.java.ua.nure.chub.MyBundle;
import main.java.ua.nure.chub.User;
import main.java.ua.nure.chub.db.DatabaseException;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class BrowsePanel extends javax.swing.JPanel implements ActionListener {
    private MainFrame parent;
    private JPanel buttonPanel;
    private JScrollPane tablePanel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton detailsButton;
    private JTable userTable;
    private static final String DELETE_MESSAGE = MyBundle.getString("do.you.really.want.to.delete");


    public BrowsePanel(MainFrame mainFrame) {
        parent = mainFrame;
        initialize();
    }

    private void initialize() {
        this.setName("browsePanel");
        this.setLayout(new BorderLayout());
        this.add(getTablePanel(), BorderLayout.CENTER);
        this.add(getButtonsPanel(), BorderLayout.SOUTH);
    }

    private JPanel getButtonsPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.add(getAddButton(), null);
            buttonPanel.add(getEditButton(), null);
            buttonPanel.add(getDeleteButton(), null);
            buttonPanel.add(getDetailsButton(), null);
        }
        return buttonPanel;
    }

    private JButton getAddButton() {
        if (addButton == null) {
            addButton = new JButton();
            addButton.setText(MyBundle.getString("add"));
            addButton.setName("addButton");
            addButton.setActionCommand("add");
            addButton.addActionListener(this);
        }
        return addButton;
    }

    private JButton getEditButton() {
        if (editButton == null) {
            editButton = new JButton();
            editButton.setText(MyBundle.getString("edit"));
            editButton.setName("editButton");
            editButton.setActionCommand("edit");
            editButton.addActionListener(this);
        }
        return editButton;
    }

    private JButton getDeleteButton() {
        if (deleteButton == null) {
            deleteButton = new JButton();
            deleteButton.setText(MyBundle.getString("delete"));
            deleteButton.setName("deleteButton");
            deleteButton.setActionCommand("delete");
            deleteButton.addActionListener(this);
        }
        return deleteButton;
    }

    private JButton getDetailsButton() {
        if (detailsButton == null) {
            detailsButton = new JButton();
            detailsButton.setText(MyBundle.getString("details"));
            detailsButton.setName("detailsButton");
            detailsButton.setActionCommand("details");
            detailsButton.addActionListener(this);
        }
        return detailsButton;
    }

    private JScrollPane getTablePanel() {
        if (tablePanel == null) {
            tablePanel = new JScrollPane(getUserTable());

        }
        return tablePanel;
    }

    private JTable getUserTable() {
        if (userTable == null) {
            userTable = new JTable();
            userTable.setName("userTable");
        }
        return userTable;
    }

    public void initTable() {
        UserTableModel model = null;
        try {
            model = new UserTableModel(parent.getUserDAO().findAll());
        } catch (DatabaseException e) {
            model = new UserTableModel(new ArrayList<User>());
            JOptionPane.showMessageDialog(this, e.getMessage(), MyBundle.getString("error"), JOptionPane.ERROR_MESSAGE);
        }
        userTable.setModel(model);
    }

    public User getSelectedUser() {
        int selectedRow = getUserTable().getSelectedRow();
        if (selectedRow == -1)
            return null;
        try {
            User user = parent.getUserDAO().findById((Long) getUserTable().getValueAt(selectedRow, 0));
            return user;
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), MyBundle.getString("error"), JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if ("add".equalsIgnoreCase(actionCommand)) {
            this.setVisible(false);
            parent.showAddPanel();
        }

        if ("edit".equalsIgnoreCase(actionCommand)) {
            this.setVisible(false);
            parent.showEditPanel();
        }

        if ("delete".equalsIgnoreCase(actionCommand)) {

            int result = JOptionPane.showConfirmDialog(parent, DELETE_MESSAGE, MyBundle.getString("delete.confirm"), JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                try {
                    parent.getUserDAO().delete(getSelectedUser());

                    getUserTable().setModel(new UserTableModel(parent.getUserDAO().findAll()));
                } catch (DatabaseException e1) {
                    JOptionPane.showMessageDialog(this, e1.getMessage(), MyBundle.getString("error"), JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if ("details".equalsIgnoreCase(e.getActionCommand())) {
            this.setVisible(false);
            parent.showDetailsPanel();
        }
    }
}
