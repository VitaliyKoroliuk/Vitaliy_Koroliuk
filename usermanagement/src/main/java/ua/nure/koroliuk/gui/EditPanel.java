package main.java.ua.nure.koroliuk.gui;

import main.java.ua.nure.chub.User;
import main.java.ua.nure.chub.db.DatabaseException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class EditPanel extends JPanel implements ActionListener {

    private MainFrame parentFrame;
    private JPanel buttonPanel;
    private JButton cancelButton;
    private JButton okButton;
    private JPanel fieldPanel;
    private JTextField dateOfBirthField;
    private JTextField lastNameField;
    private JTextField firstNameField;
    private Color bgColor;
    private User user;
    private final String CANCEL_MESSAGE = "Do you really want to cancel all changes?";

    public EditPanel(MainFrame mainFrame) {
        parentFrame = mainFrame;
        user = parentFrame.getSelectedUser();
        initialize();
        bgColor = this.getBackground();
    }

    private void initialize() {
        this.setName("editPanel");
        this.setLayout(new BorderLayout());
        this.add(getFieldPanel(), BorderLayout.NORTH);
        this.add(getButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.add(getOkButton(), null);
            buttonPanel.add(getCancelButton(), null);
        }
        return buttonPanel;
    }

    private JButton getOkButton() {
        if (okButton == null) {
            okButton = new JButton();
            okButton.setText("");
            okButton.setName("okButton");
            okButton.setActionCommand("ok");
            okButton.addActionListener(this);
        }
        return okButton;
    }

    private JButton getCancelButton() {
        if (cancelButton == null) {
            cancelButton = new JButton();
            cancelButton.setText("");
            cancelButton.setName("cancelButton");
            cancelButton.setActionCommand("cancel");
            cancelButton.addActionListener(this);
        }
        return cancelButton;
    }

    private JTextField getFirstNameField() {
        if (firstNameField == null) {
            firstNameField = new JTextField();
            firstNameField.setName("firstNameField");
        }
        return firstNameField;
    }

    private JTextField getDateOfBirthField() {
        if (dateOfBirthField == null) {
            dateOfBirthField = new JTextField();
            dateOfBirthField.setName("dateOfBirthField");
        }
        return dateOfBirthField;
    }

    private JTextField getLastNameField() {
        if (lastNameField == null) {
            lastNameField = new JTextField();
            lastNameField.setName("lastNameField");
        }
        return lastNameField;
    }

    private JPanel getFieldPanel() {
        if (fieldPanel == null) {
            fieldPanel = new JPanel();
            fieldPanel.setLayout(new GridLayout(3, 2));
            addLabeledField(fieldPanel, "", getFirstNameField());
            addLabeledField(fieldPanel, "", getLastNameField());
            addLabeledField(fieldPanel, "", getDateOfBirthField());

        }
        return fieldPanel;
    }

    private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
        JLabel label = new JLabel();
        label.setText(labelText);
        label.setLabelFor(textField);
        panel.add(label);
        panel.add(textField);
    }

    private void clearTextFields() {
        getFirstNameField().setText("");
        getFirstNameField().setBackground(bgColor);

        getLastNameField().setText("");
        getLastNameField().setBackground(bgColor);

        getDateOfBirthField().setText("");
        getDateOfBirthField().setBackground(bgColor);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("ok".equalsIgnoreCase(e.getActionCommand())) {
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
                parentFrame.getUserDAO().update(user);
            } catch (DatabaseException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if ("cancel".equalsIgnoreCase(e.getActionCommand())) {
            int result = JOptionPane.showConfirmDialog(parentFrame, CANCEL_MESSAGE, "Cancel confirm", JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                clearTextFields();
                this.setVisible(false);
                parentFrame.showBrowsePanel();
            }
        }
        clearTextFields();
        this.setVisible(false);
        parentFrame.showBrowsePanel();
    }

}

