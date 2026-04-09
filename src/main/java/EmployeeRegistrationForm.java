// Branch 1 - JFrame Setup and Basic Input Fields
// Created JFrame with title Employee Registration System
// Created JTextField for Full Name and Email
// Created JPasswordField for Password input
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import com.toedter.calendar.JCalendar;

public class EmployeeRegistrationForm extends JFrame {

    private JTextField nameField, emailField;
    private JPasswordField passwordField;
    private JComboBox<String> departmentBox;
    private JTree orgTree;
    private JCalendar calendar;

    public EmployeeRegistrationForm() {
        initializeFrame();
        add(createMainPanel());
    }

    
    private void initializeFrame() {
        setTitle("Employee Registration System");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));

        panel.add(new JLabel("Full Name:"));
        panel.add(createNameField());

        panel.add(new JLabel("Email:"));
        panel.add(createEmailField());

        panel.add(new JLabel("Password:"));
        panel.add(createPasswordField());

        panel.add(new JLabel("Department:"));
        panel.add(createDepartmentBox());

        panel.add(new JLabel("Date of Birth:"));
        panel.add(createCalendar());

        panel.add(new JLabel("Organization:"));
        panel.add(createTree());

        panel.add(createSubmitButton());
        panel.add(createClearButton());

        return panel;
    }

  
    private JTextField createNameField() {
        nameField = new JTextField();
        return nameField;
    }

    private JTextField createEmailField() {
        emailField = new JTextField();
        return emailField;
    }

    private JPasswordField createPasswordField() {
        passwordField = new JPasswordField();
        return passwordField;
    }

    private JComboBox<String> createDepartmentBox() {
        String[] departments = {"IT", "Finance", "HR", "Marketing"};
        departmentBox = new JComboBox<>(departments);
        return departmentBox;
    }

    private JCalendar createCalendar() {
        calendar = new JCalendar();
        return calendar;
    }

    private JScrollPane createTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Company");

        DefaultMutableTreeNode it = new DefaultMutableTreeNode("IT");
        it.add(new DefaultMutableTreeNode("Development"));
        it.add(new DefaultMutableTreeNode("Support"));

        DefaultMutableTreeNode hr = new DefaultMutableTreeNode("HR");
        hr.add(new DefaultMutableTreeNode("Recruitment"));
        hr.add(new DefaultMutableTreeNode("Training"));

        root.add(it);
        root.add(hr);

        orgTree = new JTree(root);
        return new JScrollPane(orgTree);
    }

    private JButton createSubmitButton() {
        JButton btn = new JButton("Submit");
        btn.addActionListener(e -> handleSubmit());
        return btn;
    }

    private JButton createClearButton() {
        JButton btn = new JButton("Clear");
        btn.addActionListener(e -> clearForm());
        return btn;
    }

   
    private void handleSubmit() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String department = (String) departmentBox.getSelectedItem();
        Object selectedNode = orgTree.getLastSelectedPathComponent();

        if (!validateInput(name, email, password, selectedNode)) return;

        String summary = buildSummary(name, email, password, department, selectedNode);

        JOptionPane.showMessageDialog(this, summary);
    }

    private boolean validateInput(String name, String email, String password, Object node) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || node == null) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return false;
        }
        return true;
    }

    private String buildSummary(String name, String email, String password,
                                String department, Object node) {

        String maskedPassword = "*".repeat(password.length());

        return "Name: " + name +
                "\nEmail: " + email +
                "\nPassword: " + maskedPassword +
                "\nDepartment: " + department +
                "\nDOB: " + calendar.getDate() +
                "\nOrganization: " + node.toString();
    }

    private void clearForm() {
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        departmentBox.setSelectedIndex(0);
        orgTree.clearSelection();
    }

}
