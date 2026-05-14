package BookingSystem;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
public class SwiingsBus extends JFrame {

    // USERS + HISTORY
    Map<String, String> users = new HashMap<>();
    Map<String, java.util.List<String>> history = new HashMap<>();

    // BUS MODEL
    class Bus {
        String name, route, type, time;
        int price;

        Bus(String n, String r, String t, String tm, int p) {
            name = n; route = r; type = t; time = tm; price = p;
        }
    }

    java.util.List<Bus> buses = new ArrayList<>();
    Map<String, Set<Integer>> seatMap = new HashMap<>();

    String currentUser = "";
    String selectedBus = "";
    int selectedSeat = -1;

    CardLayout card = new CardLayout();
    JPanel mainPanel = new JPanel(card);

    JComboBox<String> busBox, paymentBox;
    JPanel seatPanel;

    // BACKGROUND IMAGE
    Image bgImage;

    public SwiingsBus() {

        bgImage = new ImageIcon("C:/Users/vineetha/Downloads/Beagle Agency.jpg").getImage();

        loadBuses();
        initSeats();

        setTitle("Bus Booking System");
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();

        add(mainPanel);

        setVisible(true);

        loadHistory();
    }

    private void loadHistory() {
		// TODO Auto-generated method stub
		
	}

	// ================= BUS DATA =================
    void loadBuses() {

        buses.add(new Bus("APSRTC Express", "Hyd → Chennai", "AC", "08:00 AM", 500));
        buses.add(new Bus("Orange Travels", "Hyd → Bangalore", "Sleeper", "09:30 AM", 900));
        buses.add(new Bus("Morning Star", "Chennai → Bangalore", "AC", "11:00 AM", 800));
    }

    void initSeats() {
        for (Bus b : buses) seatMap.put(b.name, new HashSet<>());
    }

    // ================= UI =================
    void createUI() {

        mainPanel.add(registerPanel(), "register");
        mainPanel.add(loginPanel(), "login");
        mainPanel.add(busPanel(), "bus");

        card.show(mainPanel, "register");
    }

    // ================= BACKGROUND PANEL =================
    JPanel bgPanel() {
        return new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                if (bgImage != null) {
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
    }

    // ================= REGISTER =================
    JPanel registerPanel() {

        JPanel p = bgPanel();
        p.setLayout(null);

        // MAIN TITLE
        JLabel t = new JLabel("BUS BOOKING SYSTEM");
        t.setBounds(180, 35, 800, 60);

        t.setForeground(new Color(255, 102, 0)); // Vibrant Orange

        t.setFont(new Font("Verdana", Font.BOLD, 42));

        // SUBTITLE
        JLabel sub = new JLabel(
                "Book Your Journey Faster, Safer & Smarter"
        );

        sub.setBounds(140, 100, 800, 40);

        sub.setForeground(new Color(255, 215, 0)); // Gold

        sub.setFont(new Font("Segoe UI", Font.BOLD, 26));

        // REGISTER TEXT
        JLabel regText = new JLabel("REGISTER");

        regText.setBounds(395, 180, 300, 40);

        regText.setForeground(new Color(255, 255, 0)); // Yellow

        regText.setFont(new Font("Tahoma", Font.BOLD, 28));

        JTextField u = new JTextField();

        u.setBounds(370, 245, 240, 38);

        u.setFont(new Font("Arial", Font.PLAIN, 18));

        JPasswordField pa = new JPasswordField();

        pa.setBounds(370, 300, 240, 38);

        pa.setFont(new Font("Arial", Font.PLAIN, 18));

        JButton b = button(
                "REGISTER",
                390,
                370,
                new Color(46, 204, 113)
        );

        JButton go = button(
                "GO LOGIN",
                390,
                425,
                new Color(52, 152, 219)
        );

        b.addActionListener(e -> {

            users.put(
                    u.getText(),
                    new String(pa.getPassword())
            );

            history.putIfAbsent(
                    u.getText(),
                    new ArrayList<>()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Registered Successfully ✔"
            );
        });

        go.addActionListener(e ->
                card.show(mainPanel, "login")
        );

        p.add(t);
        p.add(sub);
        p.add(regText);
        p.add(u);
        p.add(pa);
        p.add(b);
        p.add(go);

        return p;
    }

    // ================= LOGIN =================
    JPanel loginPanel() {

        JPanel p = bgPanel();
        p.setLayout(null);

        JLabel t = label("LOGIN", 430, 50);

        JTextField u = new JTextField();
        JPasswordField pa = new JPasswordField();

        u.setBounds(380, 120, 200, 30);
        pa.setBounds(380, 170, 200, 30);

        JButton b = button("LOGIN", 410, 230, Color.ORANGE);

        JButton go = button("REGISTER", 410, 280, Color.PINK);

        b.addActionListener(e -> {

            String user = u.getText();
            String pass = new String(pa.getPassword());

            if (!users.containsKey(user)) {
                JOptionPane.showMessageDialog(this, "Register First ❌");
                return;
            }

            if (!users.get(user).equals(pass)) {
                JOptionPane.showMessageDialog(this, "Wrong Password ❌");
                return;
            }

            currentUser = user;

            JOptionPane.showMessageDialog(this,
                    "Welcome " + user + "\nHistory:\n" +
                    String.join("\n", history.get(user))
            );

            card.show(mainPanel, "bus");
        });

        go.addActionListener(e -> card.show(mainPanel, "register"));

        p.add(t); p.add(u); p.add(pa); p.add(b); p.add(go);

        return p;
    }

    // ================= BUS DASHBOARD =================
    JPanel busPanel() {

        JPanel p = bgPanel();
        p.setLayout(null);

        JLabel t = label("BUS BOOKING SYSTEM", 350, 20);

        busBox = new JComboBox<>();
        busBox.setBounds(30, 80, 400, 30);

        loadBusList();

        paymentBox = new JComboBox<>(new String[]{"Cash", "UPI", "Card"});
        paymentBox.setBounds(450, 80, 200, 30);

        JButton book = button("BOOK", 700, 80, Color.YELLOW);
        JButton cancel = button("CANCEL", 700, 130, Color.RED);
        JButton logout = button("LOGOUT", 700, 180, Color.GRAY);

        seatPanel = new JPanel(new GridLayout(4, 3, 5, 5));
        seatPanel.setBounds(30, 150, 400, 250);
        seatPanel.setBackground(new Color(0,0,0,120));

        busBox.addActionListener(e -> selectBus());
        book.addActionListener(e -> bookSeat());
        cancel.addActionListener(e -> cancelSeat());
        logout.addActionListener(e -> card.show(mainPanel, "login"));

        p.add(t);
        p.add(busBox);
        p.add(paymentBox);
        p.add(book);
        p.add(cancel);
        p.add(logout);
        p.add(seatPanel);

        return p;
    }

    // ================= BUS LIST =================
    void loadBusList() {

        busBox.removeAllItems();

        for (Bus b : buses) {
            busBox.addItem(b.name + " | " + b.route + " | ₹" + b.price);
        }
    }

    // ================= SELECT BUS =================
    void selectBus() {

        selectedBus = busBox.getSelectedItem().toString().split("\\|")[0].trim();

        selectedSeat = -1;
        showSeats();
    }

    // ================= SEATS =================
    void showSeats() {

        seatPanel.removeAll();

        Set<Integer> booked = seatMap.get(selectedBus);

        for (int i = 1; i <= 12; i++) {

            int seat = i;
            JButton b = new JButton("S" + i);

            if (booked.contains(i)) {
                b.setBackground(Color.RED);
            } else {
                b.setBackground(Color.GREEN);
            }

            b.addActionListener(e -> selectedSeat = seat);

            seatPanel.add(b);
        }

        seatPanel.revalidate();
        seatPanel.repaint();
    }

    // ================= BOOK =================
    void bookSeat() {

        Set<Integer> booked = seatMap.get(selectedBus);

        if (booked.contains(selectedSeat)) {
            JOptionPane.showMessageDialog(this, "Already Booked ❌");
            return;
        }

        booked.add(selectedSeat);

        String pay = paymentBox.getSelectedItem().toString();

        history.get(currentUser).add(
                "Bus: " + selectedBus +
                " Seat: " + selectedSeat +
                " | " + pay
        );

        JOptionPane.showMessageDialog(this, "BOOKED SUCCESS ✔");

        showSeats();
    }

    // ================= CANCEL =================
    void cancelSeat() {

        seatMap.get(selectedBus).remove(selectedSeat);

        JOptionPane.showMessageDialog(this, "CANCELLED ✔");

        showSeats();
    }

    // ================= HELPERS =================
    JLabel label(String t, int x, int y) {
        JLabel l = new JLabel(t);
        l.setForeground(Color.WHITE);
        l.setBounds(x, y, 300, 30);
        return l;
    }

    JButton button(String t, int x, int y, Color c) {
        JButton b = new JButton(t);
        b.setBounds(x, y, 150, 30);
        b.setBackground(c);
        return b;
    }

    // ================= MAIN =================
    public static void main(String[] args) {
        new SwiingsBus();
    }
}