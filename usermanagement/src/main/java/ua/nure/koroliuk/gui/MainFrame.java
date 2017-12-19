package main.java.ua.nure.koroliuk.gui;


import main.java.ua.nure.chub.User;
import main.java.ua.nure.chub.db.DAOFactory;
import main.java.ua.nure.chub.db.UserDAO;

import javax.swing.*;
import java.awt.*;




public class MainFrame extends JFrame {
    private int FRAME_WIDTH = 600;
    private int FRAME_HEIGHT = 800;
    private JPanel contentPanel;
    private JPanel browsePanel;
    private AddPanel addPanel;
    private UserDAO userDAO;
    private EditPanel editPanel;
    private DetailsPanel detailsPanel;

    public MainFrame() {
        super();
        userDAO = DAOFactory.getInstance().getUserDAO();
        initialize();
    }

    private void initialize() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle("user.management");
        this.setContentPane(getContentPanel());
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }


    public Container getContentPanel() {
        if (contentPanel == null) {
            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(getBrowsePanel(), BorderLayout.CENTER);
        }
        return contentPanel;
    }

    public JPanel getBrowsePanel() {
        if (browsePanel == null) {
            browsePanel = new BrowsePanel(this);
        }
        ((BrowsePanel) browsePanel).initTable();
        return browsePanel;
    }

    public void showAddPanel() {
        showBrowsePanel(getAddPanel());
    }

    public void showBrowsePanel() {
        showBrowsePanel(getBrowsePanel());
    }

    public void showEditPanel() {
        showBrowsePanel(getEditPanel());
    }

    public void showDetailsPanel() {
        showBrowsePanel(getDetailsPanel());
    }

    private void showBrowsePanel(JPanel panel) {
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setVisible(true);
        panel.repaint();
    }

    private AddPanel getAddPanel() {
        if (addPanel == null) {
            addPanel = new AddPanel(this);
        }
        return addPanel;
    }

    private EditPanel getEditPanel() {
        if (editPanel == null) {
            editPanel = new EditPanel(this);
        }
        return editPanel;
    }


    public User getSelectedUser() {
        return ((BrowsePanel) browsePanel).getSelectedUser();
    }

    public void showEditPanel(JPanel panel) {
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setVisible(true);
        panel.repaint();
    }

    private DetailsPanel getDetailsPanel() {
        if (detailsPanel == null) {
            detailsPanel = new DetailsPanel(this);
        }
        return detailsPanel;
    }

    public void showDetailsPanel(JPanel panel) {
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setVisible(true);
        panel.repaint();
    }

    private void showPanel(JPanel panel) {
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setVisible(true);
        panel.repaint();
    }


    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);

    }
}
