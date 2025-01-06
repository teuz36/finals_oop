import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class FlightBookingForm {
    private Connection connection;

    public static void main(String[] args) {
        new FlightBookingForm().createAndShowGUI();
    }

    public FlightBookingForm() {
        connectToDatabase();
    }

    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_schema", "root", "omcm");
            System.out.println("Connected to MySQL database!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
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

        // Login Panel
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
                JOptionPane.showMessageDialog(frame, "Registration failed! Username might already exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private boolean validateLogin(String username, String password) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean registerUser(String username, String password) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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

            String bookingDetails = bookFlight(departure, destination, date, seatClass, passengers);

            if (bookingDetails != null) {
                JOptionPane.showMessageDialog(frame, "Booked Successfully, Have a safe flight!\n\n" + bookingDetails, "Flight Details", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Flight booking failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private String bookFlight(String departure, String destination, String date, String seatClass, int passengers) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/flight_schema", "root", "omcm");
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO book (departure, destination, date, seatClass, passengers) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, departure);
            stmt.setString(2, destination);
            stmt.setString(3, date);
            stmt.setString(4, seatClass);
            stmt.setInt(5, passengers);
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int bookingId = generatedKeys.getInt(1);
                return String.format("Booking ID: %d\nDeparture: %s\nDestination: %s\nDate: %s\nSeat Class: %s\nPassengers: %d", bookingId, departure, destination, date, seatClass, passengers);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
