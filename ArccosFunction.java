import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ArccosFunction extends JFrame {

    private JTextField inputField;
    private JTextArea resultArea;
    private ArccosCalculator calculator = new ArccosCalculator();
    public ArccosFunction() {
        // Create the frame
        setTitle("Arccosine Calculator");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        // Create border and font
        Border border = BorderFactory.createLineBorder(Color.GRAY);
        Font font = new Font("Arial", Font.PLAIN, 14);

        // Create input label and text field
        JLabel label = new JLabel("Enter a number between -1 and 1:");
        label.setFont(font);
        inputField = new JTextField(10);
        inputField.setFont(font);
        inputField.setBorder(border);

        // Create calculate button
        JButton calculateButton = new JButton("Calculate");
        calculateButton.setFont(font);
        calculateButton.addActionListener(new CalculateButtonListener());

        // Create clear button
        JButton clearButton = new JButton("Clear");
        clearButton.setFont(font);
        clearButton.addActionListener(e -> {
            inputField.setText("");
            resultArea.setText("");
        });

        // Create result area
        resultArea = new JTextArea(10, 50);
        resultArea.setFont(font);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setEditable(false);
        resultArea.setBorder(border);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Create panel for input and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input"));

        // Arrange components in the input panel
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(5, 5, 5, 5);
        inputPanel.add(label, constraints);

        constraints.gridx = 1;
        inputPanel.add(inputField, constraints);

        constraints.gridy = 1;
        constraints.gridx = 0;
        inputPanel.add(calculateButton, constraints);

        constraints.gridx = 1;
        inputPanel.add(clearButton, constraints);

        // Create panel for results
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new GridBagLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Results"));

        // Arrange components in the result panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        resultPanel.add(scrollPane, constraints);

        // Arrange panels in the frame
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        add(inputPanel, constraints);

        constraints.gridy = 1;
        add(resultPanel, constraints);
    }

    private class CalculateButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                double input = Double.parseDouble(inputField.getText());
                if (input < -1 || input > 1) {
                    throw new IllegalArgumentException("Input must be in the range [-1, 1]");
                }
                double resultInRadians1 = arccos(input);
                double resultInDegrees1 = toDegrees(resultInRadians1);
                double resultInDegrees2 = 360 - resultInDegrees1;  // Complementary angle in degrees

                StringBuilder resultBuilder = new StringBuilder();
                resultBuilder.append("The arccosine of ").append(input)
                        .append(" is:\n");

                // Output both primary results within [0, 360] degrees
                resultBuilder.append(roundToSevenDecimalPlaces(resultInDegrees1)).append("\n");
                if (resultInDegrees1 != 0 && resultInDegrees1 != 180) {
                    resultBuilder.append(roundToSevenDecimalPlaces(resultInDegrees2)).append("\n");
                }

                // Output extended results beyond 360 degrees
                for (int k = 1; k < 4; k++) {
                    double angle1 = resultInDegrees1 + 360 * k;
                    double angle2 = resultInDegrees2 + 360 * k;
                    resultBuilder.append(roundToSevenDecimalPlaces(angle1)).append("\n");
                    if (resultInDegrees1 != 0 && resultInDegrees1 != 180) {
                        resultBuilder.append(roundToSevenDecimalPlaces(angle2)).append("\n");
                    }
                }

                resultArea.setText(resultBuilder.toString());
            } catch (NumberFormatException ex) {
                showErrorDialog("Invalid Input", "Please enter a valid number.");
            } catch (IllegalArgumentException ex) {
                showErrorDialog("Invalid Input", ex.getMessage());
            } catch (Exception ex) {
                showErrorDialog("Error", "An error occurred. Please try again.");
            }
        }
    }

    // Function to calculate the arccosine of x using Taylor series expansion
    public static double arccos(double x) {
        if (x == -1) {
            return Math.PI;
        } else if (x == 1) {
            return 0;
        }
        double result = Math.PI / 2;
        double term = x;
        double xSquared = x * x;
        int n = 1;
        while (Math.abs(term) > 1e-15) {
            result -= term / (2 * n - 1);
            term *= xSquared * (2 * n - 1) / (2 * n + 1);
            n++;
        }
        return result;
    }

    // Function to convert radians to degrees
    public static double toDegrees(double radians) {
        return radians * (180 / Math.PI);
    }

    // Function to round a double to 7 decimal places
    public static double roundToSevenDecimalPlaces(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(7, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    // Function to show error dialog messages
    private void showErrorDialog(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ArccosFunction frame = new ArccosFunction();
            frame.setVisible(true);
        });
    }
}