import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

class Car {
    private int id;
    private String name;
    private String category;
    private int price;
    private boolean available;

    public Car(int id, String name, String category, int price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.available = true;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getPrice() { return price; }
    public boolean isAvailable() { return available; }

    public void rent() { available = false; }
    public void returnCar() { available = true; }
}

class LuxuryCar extends Car {
    public LuxuryCar(int id, String name, String category, int price) {
        super(id, name, category, price);
    }

    @Override
    public int getPrice() {
        return super.getPrice() + 1500;
    }
}

public class CarRentalGUI extends JFrame {

    private java.util.List<Car> cars = new ArrayList<>();
    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> filterBox;

    public CarRentalGUI() {
        setTitle("🚗 Smart Car Rental System");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        // 🔹 HEADER
        JLabel title = new JLabel("🚗 Smart Car Rental Dashboard", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setOpaque(true);
        title.setBackground(new Color(30,144,255));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(title, BorderLayout.NORTH);

        // 🔹 TABLE
        String[] cols = {"ID", "Car Name", "Category", "Price/Day", "Status"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setRowHeight(25);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 🔹 TOP PANEL (Filter)
        JPanel topPanel = new JPanel();
        filterBox = new JComboBox<>(new String[]{"All", "SUV", "Sedan", "Luxury", "Electric"});
        JButton filterBtn = new JButton("Filter");

        topPanel.add(new JLabel("Category:"));
        topPanel.add(filterBox);
        topPanel.add(filterBtn);

        add(topPanel, BorderLayout.BEFORE_FIRST_LINE);

        // 🔹 BUTTON PANEL
        JPanel panel = new JPanel();

        JButton showBtn = createButton("Show Cars");
        JButton rentBtn = createButton("Rent");
        JButton returnBtn = createButton("Return");

        panel.add(showBtn);
        panel.add(rentBtn);
        panel.add(returnBtn);

        add(panel, BorderLayout.SOUTH);

        // 🔹 DATA (MORE VEHICLES 🔥)
        cars.add(new Car(1, "HondaCity", "Sedan", 2000));
        cars.add(new Car(2, "Verna", "Sedan", 2500));
        cars.add(new Car(3, "Thar", "SUV", 5000));
        cars.add(new Car(4, "Scorpio", "SUV", 4000));
        cars.add(new LuxuryCar(5, "BMW", "Luxury", 10000));
        cars.add(new LuxuryCar(6, "Audi", "Luxury", 9000));
        cars.add(new Car(7, "MiniCopper", "Luxury", 11000));
        cars.add(new Car(8, "Nexon EV", "Electric", 4500));

        // 🔹 ACTIONS
        showBtn.addActionListener(e -> loadCars("All"));
        filterBtn.addActionListener(e -> loadCars((String)filterBox.getSelectedItem()));
        rentBtn.addActionListener(e -> rentCar());
        returnBtn.addActionListener(e -> returnCar());
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(0,123,255));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        return btn;
    }

    private void loadCars(String category) {
        model.setRowCount(0);

        for (Car c : cars) {
            if (category.equals("All") || c.getCategory().equals(category)) {
                model.addRow(new Object[]{
                    c.getId(),
                    c.getName(),
                    c.getCategory(),
                    "₹" + c.getPrice(),
                    c.isAvailable() ? "Available" : "Rented"
                });
            }
        }
    }

    private void rentCar() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Car ID:"));

            for (Car c : cars) {
                if (c.getId() == id && c.isAvailable()) {
                    int days = Integer.parseInt(JOptionPane.showInputDialog("Days:"));

                    c.rent();
                    int total = c.getPrice() * days;

                    JOptionPane.showMessageDialog(this, "✅ Rented! Total ₹" + total);
                    loadCars("All");
                    return;
                }
            }

            JOptionPane.showMessageDialog(this, "❌ Not available");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "⚠ Invalid input");
        }
    }

    private void returnCar() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Car ID:"));

            for (Car c : cars) {
                if (c.getId() == id && !c.isAvailable()) {
                    c.returnCar();
                    JOptionPane.showMessageDialog(this, "🔄 Returned");
                    loadCars("All");
                    return;
                }
            }

            JOptionPane.showMessageDialog(this, "❌ Invalid ID");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "⚠ Error");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CarRentalGUI().setVisible(true));
    }
}