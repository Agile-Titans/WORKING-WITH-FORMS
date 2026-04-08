import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.GraphicsEnvironment;

import com.toedter.calendar.JCalendar;

public class EmployeeRegistrationForm {

    JFrame frame;

    JTextField nameField, emailField;
    JPasswordField passwordField;
    JComboBox<String> departmentBox;
    JTree orgTree;
    JCalendar calendar;

    public EmployeeRegistrationForm() {
    }

    public void createAndShowGUI() {
        if (GraphicsEnvironment.isHeadless()) return;

        initializeFrame();
        frame.add(createMainPanel());
        frame.setVisible(true);
    }

    public void initializeFrame() {
        frame = new JFrame("Employee Registration System");
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    }

    public JPanel createMainPanel() {
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

    public JTextField createNameField() {
        nameField = new JTextField();
        return nameField;
    }

    public JTextField createEmailField() {
        emailField = new JTextField();
        return emailField;
    }

    public JPasswordField createPasswordField() {
        passwordField = new JPasswordField();
        return passwordField;
    }

    public JComboBox<String> createDepartmentBox() {
        String[] departments = {"IT", "Finance", "HR", "Marketing"};
        departmentBox = new JComboBox<>(departments);
        return departmentBox;
    }

    public JCalendar createCalendar() {
        calendar = new JCalendar();
        return calendar;
    }

    public JScrollPane createTree() {
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

    public JButton createSubmitButton() {
        JButton btn = new JButton("Submit");
        btn.addActionListener(e -> handleSubmit());
        return btn;
    }

    public JButton createClearButton() {
        JButton btn = new JButton("Clear");
        btn.addActionListener(e -> clearForm());
        return btn;
    }

    public void handleSubmit() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String department = (String) departmentBox.getSelectedItem();
        Object selectedNode = orgTree.getLastSelectedPathComponent();

        if (!validateInput(name, email, password, selectedNode)) return;

        String summary = buildSummary(name, email, password, department, selectedNode);

        if (!GraphicsEnvironment.isHeadless()) {
            JOptionPane.showMessageDialog(frame, summary);
        }
    }

    boolean validateInput(String name, String email, String password, Object node) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || node == null) {

            if (!GraphicsEnvironment.isHeadless()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields!");
            }

            return false;
        }
        return true;
    }

    String buildSummary(String name, String email, String password,
                        String department, Object node) {

        String maskedPassword = "*".repeat(password.length());

        // FIX: prevent NullPointerException
        String dob = (calendar != null) ? calendar.getDate().toString() : "N/A";

        return "Name: " + name +
                "\nEmail: " + email +
                "\nPassword: " + maskedPassword +
                "\nDepartment: " + department +
                "\nDOB: " + dob +
                "\nOrganization: " + node.toString();
    }

    public void clearForm() {
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        departmentBox.setSelectedIndex(0);
        orgTree.clearSelection();
    }
}