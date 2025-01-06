import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class FlightBooking {
    private final List<User> users = new ArrayList<>();

    public static void main(String[] args) {
        new FlightBooking().createAndShowGUI();
    }

    public FlightBooking() {
        users.add(new User("admin", "admin123"));
        users.add(new User("user", "password"));
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("FlyEasy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Welcome To FlyEasy", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel subtitleLabel = new JLabel("Sign up or log in to proceed", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new CardLayout());

        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel loginUsernameLabel = new JLabel("Username:");
        JTextField loginUsernameField = new JTextField(15);

        JLabel loginPasswordLabel = new JLabel("Password:");
        JPasswordField loginPasswordField = new JPasswordField(15);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(loginUsernameLabel, gbc);

        gbc.gridx = 1;
        loginPanel.add(loginUsernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(loginPasswordLabel, gbc);

        gbc.gridx = 1;
        loginPanel.add(loginPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);

        JPanel registerPanel = new JPanel(new GridBagLayout());
        registerPanel.setBackground(Color.WHITE);

        JLabel registerUsernameLabel = new JLabel("Username:");
        JTextField registerUsernameField = new JTextField(15);

        JLabel registerPasswordLabel = new JLabel("Password:");
        JPasswordField registerPasswordField = new JPasswordField(15);

        JButton registerButton = new JButton("Sign Up");
        registerButton.setBackground(new Color(211, 211, 211));
        registerButton.setForeground(Color.BLACK);

        gbc.gridx = 0;
        gbc.gridy = 0;
        registerPanel.add(registerUsernameLabel, gbc);

        gbc.gridx = 1;
        registerPanel.add(registerUsernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        registerPanel.add(registerPasswordLabel, gbc);

        gbc.gridx = 1;
        registerPanel.add(registerPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        registerPanel.add(registerButton, gbc);

        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registerPanel, "Register");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        JButton switchToRegister = new JButton("Sign Up");
        JButton switchToLogin = new JButton("Login");

        switchToRegister.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "Register");
        });

        switchToLogin.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "Login");
        });

        buttonPanel.add(switchToRegister);
        buttonPanel.add(switchToLogin);

        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(subtitleLabel, BorderLayout.CENTER);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        loginButton.addActionListener(e -> {
            String username = loginUsernameField.getText();
            String password = new String(loginPasswordField.getPassword());
            if (validateLogin(username, password)) {
                JOptionPane.showMessageDialog(frame, "Login Successful!");
                frame.dispose();
                showBookingForm();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> {
            String username = registerUsernameField.getText();
            String password = new String(registerPasswordField.getPassword());
            if (registerUser(username, password)) {
                JOptionPane.showMessageDialog(frame, "Registration successful! You can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                switchToLogin.doClick(); // Switch to login panel
            } else {
                JOptionPane.showMessageDialog(frame, "Registration failed! Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private boolean validateLogin(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private boolean registerUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false; // Username already exists
            }
        }
        users.add(new User(username, password));
        return true;
    }

    private void showBookingForm() {
        JFrame frame = new JFrame("FlyEasy Booking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel departureLabel = new JLabel("Departure:");
        JTextField departureField = new JTextField(15);

        JLabel destinationLabel = new JLabel("Destination:");
        JTextField destinationField = new JTextField(15);

        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        JTextField dateField = new JTextField(15);

        JLabel seatClassLabel = new JLabel("Select Seat Class:");
        String[] seatClasses = {"Economy", "Business", "First Class"};
        JComboBox<String> seatClassComboBox = new JComboBox<>(seatClasses);

        JLabel passengersLabel = new JLabel("Number of Passengers:");
        SpinnerModel passengerModel = new SpinnerNumberModel(1, 1, 10, 1);
        JSpinner passengersSpinner = new JSpinner(passengerModel);

        JButton confirmButton = new JButton("Confirm Booking");
        confirmButton.setBackground(Color.BLACK);
        confirmButton.setForeground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(departureLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(departureField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(destinationLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(destinationField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(dateLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(dateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(seatClassLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(seatClassComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(passengersLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(passengersSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(confirmButton, gbc);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        confirmButton.addActionListener(e -> {
            String departure = departureField.getText();
            String destination = destinationField.getText();
            String date = dateField.getText();
            String seatClass = (String) seatClassComboBox.getSelectedItem();
            int passengers = (Integer) passengersSpinner.getValue();

            String bookingDetails = String.format(
                "Departure: %s\nDestination: %s\nDate: %s\nSeat Class: %s\nPassengers: %d",
                departure, destination, date, seatClass, passengers
            );

            JOptionPane.showMessageDialog(frame, "Booked Successfully!\n\n" + bookingDetails, "Flight Details", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    // Inner class to represent users
    private static class User {
        private final String username;
        private final String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}
