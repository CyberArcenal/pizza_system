import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;



public class PizzaOrderSystem extends JFrame {
    private JCheckBox tomato, greenPepper, cheese, mushroom, extraCheese, pepperoni, sausage;
    private JRadioButton small, medium, large;
    private JRadioButton thinCrust, mediumCrust, pan;
    private JTextField orderQuantity;
    private JTextArea outputArea;

    public PizzaOrderSystem() {
        setTitle("Home Style Pizza");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLayout(new BorderLayout(10, 10));

        // Main Content Panel (3-column layout)
        JPanel mainPanel = new JPanel(new GridLayout(1, 3, 15, 15));
        mainPanel.add(createToppingsPanel());
        mainPanel.add(createSizePanel());
        mainPanel.add(createTypePanel());

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.add(createInputPanel(), BorderLayout.NORTH);
        bottomPanel.add(createOutputPanel(), BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
    private JPanel createToppingsPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder("Each Topping: P20.00"));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        tomato = new JCheckBox("Tomato");
        greenPepper = new JCheckBox("Green Pepper");
        cheese = new JCheckBox("Cheese");
        mushroom = new JCheckBox("Mushroom");
        extraCheese = new JCheckBox("Extra Cheese");
        pepperoni = new JCheckBox("Pepperoni");
        sausage = new JCheckBox("Sausage");
        
        panel.add(tomato);
        panel.add(greenPepper);
        panel.add(cheese);
        panel.add(mushroom);
        panel.add(extraCheese);
        panel.add(pepperoni);
        panel.add(sausage);
        
        return panel;
    }

    private JPanel createSizePanel() {
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder("Pizza Size"));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        ButtonGroup sizeGroup = new ButtonGroup();
        small = new JRadioButton("Small P50");
        medium = new JRadioButton("Medium P70");
        large = new JRadioButton("Large P90");
        
        sizeGroup.add(small);
        sizeGroup.add(medium);
        sizeGroup.add(large);
        
        panel.add(small);
        panel.add(medium);
        panel.add(large);
        
        return panel;
    }

    private JPanel createTypePanel() {
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder("Pizza Type"));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        ButtonGroup typeGroup = new ButtonGroup();
        thinCrust = new JRadioButton("Thin Crust");
        mediumCrust = new JRadioButton("Medium Crust");
        pan = new JRadioButton("Pan");
        
        typeGroup.add(thinCrust);
        typeGroup.add(mediumCrust);
        typeGroup.add(pan);
        
        panel.add(thinCrust);
        panel.add(mediumCrust);
        panel.add(pan);
        
        return panel;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        orderQuantity = new JTextField(5);
        JButton processButton = new JButton("Process Selection");
        
        processButton.addActionListener(this::processOrder);
        
        panel.add(new JLabel("No. of Order:"));
        panel.add(orderQuantity);
        panel.add(processButton);
        
        return panel;
    }

    private JPanel createOutputPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        outputArea = new JTextArea(6, 40);
        outputArea.setEditable(false);
        panel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        return panel;
    }

    private void processOrder(ActionEvent e) {
        // Validate pizza size selection
        if (!small.isSelected() && !medium.isSelected() && !large.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select a pizza size!");
            return;
        }
        
        // Validate pizza type selection
        if (!thinCrust.isSelected() && !mediumCrust.isSelected() && !pan.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select a pizza type!");
            return;
        }
        
        // Validate quantity input
        int quantity;
        try {
            quantity = Integer.parseInt(orderQuantity.getText().trim());
            if (quantity < 1) throw new NumberFormatException();
        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number of orders!");
            return;
        }
        
        // Get selected size
        String size = "";
        int sizePrice = 0;
        if (small.isSelected()) {
            size = "Small";
            sizePrice = 50;
        } else if (medium.isSelected()) {
            size = "Medium";
            sizePrice = 70;
        } else if (large.isSelected()) {
            size = "Large";
            sizePrice = 90;
        }
        
        // Get selected type
        String type = "";
        if (thinCrust.isSelected()) type = "Thin Crust";
        else if (mediumCrust.isSelected()) type = "Medium Crust";
        else if (pan.isSelected()) type = "Pan";
        
        // Get selected toppings
        List<String> toppings = new ArrayList<>();
        if (tomato.isSelected()) toppings.add("Tomato");
        if (greenPepper.isSelected()) toppings.add("Green Pepper");
        if (cheese.isSelected()) toppings.add("Cheese");
        if (mushroom.isSelected()) toppings.add("Mushroom");
        if (extraCheese.isSelected()) toppings.add("Extra Cheese");
        if (pepperoni.isSelected()) toppings.add("Pepperoni");
        if (sausage.isSelected()) toppings.add("Sausage");
        
        // Calculate total price
        int toppingsPrice = toppings.size() * 20;
        double total = (sizePrice + toppingsPrice) * quantity;
        
        // Build output
        StringBuilder output = new StringBuilder();
        output.append("Your Order:\n");
        output.append("Pizza Type: ").append(type).append("\n");
        output.append("Pizza Size: ").append(size).append("\n");
        output.append("Toppings: ");
        if (toppings.isEmpty()) {
            output.append("None");
        } else {
            output.append(String.join(", ", toppings));
        }
        output.append("\nAmount Due: P").append(String.format("%.1f", total));
        
        outputArea.setText(output.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PizzaOrderSystem());
    }
}