package main.java.ua.nure.koroliuk.gui;

import main.java.ua.nure.chub.MyBundle;
import main.java.ua.nure.chub.User;
import main.java.ua.nure.chub.db.DatabaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.text.SimpleDateFormat;


public class AddPanel extends JPanel implements ActionListener {
    private MainFrame parent;
    private JPanel buttonPanel;
    private JPanel fieldPanel;
    private JButton cancelButton;
    private JButton okButton;
    private JTextField dateOfBirthField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private static final String CANCEL_CREATE = MyBundle.getString("do.you.really.want.to.cancel.the.creation");

    public AddPanel(MainFrame parent) {
        MyBundle bundle = new MyBundle();
        this.parent = parent;
        initialize();
    }

    private void initialize() {
        this.setName("addPanel");
        this.setLayout(new BorderLayout());
        this.add(getFieldPanel(), BorderLayout.NORTH);
        this.add(getButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel getFieldPanel() {
        if (fieldPanel == null) {
            fieldPanel = new JPanel();
            fieldPanel.setLayout(new GridLayout(3, 2));
            addLabeledField(fieldPanel, MyBundle.getString("first.name"), getFirstNameField());
            addLabeledField(fieldPanel, MyBundle.getString("last.name"), getLastNameField());
            addLabeledField(fieldPanel, MyBundle.getString("date.of.birth"), getDateOfBirthField());
        }
        return fieldPanel;
    }

    private JTextField getLastNameField() {
        if (lastNameField == null) {
            lastNameField = new JTextField();
            lastNameField.setName("lastNameField");
        }
        return lastNameField;

    }

    private JTextField getDateOfBirthField() {
        if (dateOfBirthField == null) {
            dateOfBirthField = new JTextField();
            dateOfBirthField.setName("dateOfBirthField");
        }
        return dateOfBirthField;
    }

    private JTextField getFirstNameField() {
        if (firstNameField == null) {
            firstNameField = new JTextField();
            firstNameField.setName("firstNameField");
        }
        return firstNameField;
    }

    private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        label.setLabelFor(textField);
        panel.add(label);
        panel.add(textField);
    }

    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.add(getOKButton(), null);
            buttonPanel.add(getCancelButton(), null);
        }
        return buttonPanel;
    }

    private JButton getCancelButton() {
        if (cancelButton == null) {
            cancelButton = new JButton();
            cancelButton.setText("cancel");
            cancelButton.setName("cancelButton");
            cancelButton.setActionCommand("cancel");
            cancelButton.addActionListener(this);
        }
        return cancelButton;
    }

    private JButton getOKButton() {
        if (okButton == null) {
            okButton = new JButton();
            okButton.setText(MyBundle.getString("ok"));
            okButton.setName("okButton");
            okButton.setActionCommand("ok");
            okButton.addActionListener(this);
        }
        return okButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if ("ok".equalsIgnoreCase(command)) {
            User user = new User();
            user.setFirstName(getFirstNameField().getText());
            user.setLastName(getLastNameField().getText());
            SimpleDateFormat dataFormat = new SimpleDateFormat("dd.MM.yyyy");
            try {
                user.setDateOfBirth(dataFormat.parse(getDateOfBirthField().getText()));
            } catch (Exception e1) {
                getDateOfBirthField().setBackground(Color.RED);
                return;
            }
            try {
                parent.getUserDAO().create(user);
            } catch (DatabaseException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage(), MyBundle.getString("error"), JOptionPane.ERROR_MESSAGE);
            }
        }
        if ("cancel".equalsIgnoreCase(command)) {
            int result = JOptionPane.showConfirmDialog(parent, CANCEL_CREATE, MyBundle.getString("cancel.confirm"), JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                clearFields();
                this.setVisible(false);
                parent.showBrowsePanel();
            }
        }
        clearFields();
        this.setVisible(false);
        parent.showBrowsePanel();
    }

    private void clearFields() {
        getFirstNameField().setText("");
        getFirstNameField().setBackground(Color.WHITE);
        getLastNameField().setText("");
        getLastNameField().setBackground(Color.WHITE);
        getDateOfBirthField().setText("");
        getDateOfBirthField().setBackground(Color.WHITE);
    }
}
