import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ArccosFunction {
    private static final double DEGREE_CONVERSION_FACTOR = 180 / Math.PI;
    private static final int ANGLE_INCREMENT = 60;
    private static final int MAX_ANGLE = 360;

    private JFrame frame;
    private JTextField inputField;
    private JTextArea outputArea;
    private JButton calculateButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ArccosFunction().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("Arccos Function");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel inputLabel = new JLabel("Enter value (-1 to 1):");
        inputLabel.setBounds(10, 20, 150, 25);
        panel.add(inputLabel);

        inputField = new JTextField(20);
        inputField.setBounds(160, 20, 200, 25);
        panel.add(inputField);

        calculateButton = new JButton("Calculate");
        calculateButton.setBounds(10, 80, 150, 25);
        panel.add(calculateButton);

        outputArea = new JTextArea();
        outputArea.setBounds(10, 120, 350, 120);
        panel.add(outputArea);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateArccos();
            }
        });
    }

    private void calculateArccos() {
        try {
            double x = Double.parseDouble(inputField.getText());
            validateInput(x);
            List<Double> results = computeArccosResults(x);
            displayResults(results);
        } catch (NumberFormatException e) {
            outputArea.setText("Invalid input. Please enter a number.");
        } catch (IllegalArgumentException e) {
            outputArea.setText(e.getMessage());
        }
    }

    private void validateInput(double x) {
        if (x < -1 || x > 1) {
            throw new IllegalArgumentException("Value must be between -1 and 1.");
        }
    }

    private List<Double> computeArccosResults(double x) {
        List<Double> results = new ArrayList<>();
        for (int i = 0; i < MAX_ANGLE; i += ANGLE_INCREMENT) {
            results.add(toDegrees(Math.acos(x) + Math.toRadians(i)));
        }
        return results;
    }

    private void displayResults(List<Double> results) {
        outputArea.setText("Results:\n");
        for (double result : results) {
            outputArea.append(String.format("%.7f\n", result));
        }
    }

    private double toDegrees(double radians) {
        return radians * DEGREE_CONVERSION_FACTOR;
    }
}
