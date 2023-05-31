import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class TimetableApp extends JFrame {
    private JCheckBox course1CheckBox;
    private JCheckBox course2CheckBox;
    private JCheckBox course3CheckBox;

    private JPanel timetablePanel;
    private Map<JPanel, Color> originalColors;
    private Map<JPanel, Boolean> originalVisibility;

    public TimetableApp() {
        setTitle("Timetable App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createCheckBoxes();
        createTimetablePanel();

        add(createCheckBoxPanel(), BorderLayout.NORTH);
        add(timetablePanel, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    private void createCheckBoxes() {
        course1CheckBox = new JCheckBox("Course 1");
        course2CheckBox = new JCheckBox("Course 2");
        course3CheckBox = new JCheckBox("Course 3");

        course1CheckBox.setToolTipText("This is course 1");
        course2CheckBox.setToolTipText("This is course 2");
        course3CheckBox.setToolTipText("This is course 3");

        course1CheckBox.addActionListener(new CheckBoxListener());
        course2CheckBox.addActionListener(new CheckBoxListener());
        course3CheckBox.addActionListener(new CheckBoxListener());
    }

    private JPanel createCheckBoxPanel() {
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        checkBoxPanel.add(course1CheckBox);
        checkBoxPanel.add(course2CheckBox);
        checkBoxPanel.add(course3CheckBox);
        return checkBoxPanel;
    }

    private void createTimetablePanel() {
        timetablePanel = new JPanel();
        timetablePanel.setLayout(new GridLayout(13, 6));

        String[] daysOfWeek = {"", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        for (String day : daysOfWeek) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            timetablePanel.add(dayLabel);
        }

        String[] timeSlots = {"8 AM", "9 AM", "10 AM", "11 AM", "12 PM", "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM", "7 PM"};
        originalColors = new HashMap<>();
        originalVisibility = new HashMap<>();
        for (int row = 0; row < 12; row++) {
            JLabel timeLabel = new JLabel(timeSlots[row], SwingConstants.CENTER);
            timeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            timetablePanel.add(timeLabel);

            for (int col = 0; col < 5; col++) {
                JPanel cellPanel = new JPanel();
                cellPanel.setLayout(null); // Set layout manager to null
                cellPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                timetablePanel.add(cellPanel);

                originalColors.put(cellPanel, cellPanel.getBackground());
                originalVisibility.put(cellPanel, cellPanel.isVisible());
            }
        }
    }

    private class CheckBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBox source = (JCheckBox) e.getSource();

            if (source == course1CheckBox) {
                JPanel cellPanel = (JPanel) timetablePanel.getComponent(7);
                toggleCellVisibility(cellPanel, source.isSelected());
                if (source.isSelected()) {
                    cellPanel.setBackground(new Color(0, 0, 255, 255));  // Blue (50% transparent)
                } else {
                    restoreCellState(cellPanel);
                }
            } else if (source == course2CheckBox) {
                JPanel cellPanel = (JPanel) timetablePanel.getComponent(13);
                toggleCellVisibility(cellPanel, source.isSelected());
                if (source.isSelected()) {
                    cellPanel.setBackground(new Color(0, 255, 0, 255));  // Green (50% transparent)
                } else {
                    restoreCellState(cellPanel);
                }
            } else if (source == course3CheckBox) {
                JPanel cellPanel = (JPanel) timetablePanel.getComponent(9);
                toggleCellVisibility(cellPanel, source.isSelected());
                if (source.isSelected()) {
                    cellPanel.setBackground(new Color(255, 0, 0, 255));  // Red (50% transparent)
                } else {
                    restoreCellState(cellPanel);
                }
            }
        }

        private void toggleCellVisibility(JPanel cellPanel, boolean visible) {
            cellPanel.setVisible(visible);
            if (!visible) {
                // Remove checkbox component from the cell panel
                for (Component component : cellPanel.getComponents()) {
                    if (component instanceof JCheckBox) {
                        cellPanel.remove(component);
                    }
                }
            }
        }

        private void restoreCellState(JPanel cellPanel) {
            cellPanel.setBackground(originalColors.get(cellPanel));
            cellPanel.setVisible(originalVisibility.get(cellPanel));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TimetableApp());
    }
}
