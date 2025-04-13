package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Booking extends JFrame {
    public Booking(ManagerWindow parentWindow) {
        setTitle("Booking Form");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("GetAway", JLabel.CENTER);
        headerLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        add(headerLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); //odsazení

        //jméno
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
        JTextField nameField = new JTextField(); //field pro jméno
        formPanel.add(nameLabel);
        formPanel.add(nameField);

        //telefonni cislo
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
        JTextField phoneField = new JTextField(); //field pro telefonni cislo
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);

        //zaskrtavaci ctverecek pro slevu
        JLabel discountLabel = new JLabel("Student Discount:");
        discountLabel.setFont(new Font("Consolas", Font.PLAIN, 14));

        JPanel discountPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); //panel pro tlacitko
        JCheckBox discountCheckBox = new JCheckBox();
        discountPanel.add(discountCheckBox);
        formPanel.add(discountLabel);
        formPanel.add(discountPanel);

        //destinace
        JLabel optionsLabel = new JLabel("Choose Your Destination:");
        optionsLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JRadioButton beachOption = new JRadioButton("Beach");
        beachOption.setFont(new Font("Consolas", Font.PLAIN, 12));
        JRadioButton mountainOption = new JRadioButton("Mountains");
        mountainOption.setFont(new Font("Consolas", Font.PLAIN, 12));
        JRadioButton cityOption = new JRadioButton("City");
        cityOption.setFont(new Font("Consolas", Font.PLAIN, 12));

        ButtonGroup optionsGroup = new ButtonGroup();
        optionsGroup.add(beachOption);
        optionsGroup.add(mountainOption);
        optionsGroup.add(cityOption);

        optionsPanel.add(beachOption);
        optionsPanel.add(mountainOption);
        optionsPanel.add(cityOption);

        formPanel.add(optionsLabel);
        formPanel.add(optionsPanel);

        //posuvny na dny
        JLabel daysLabel = new JLabel("Days:");
        daysLabel.setFont(new Font("Consolas", Font.PLAIN, 14));

        JSlider daysSlider = new JSlider(5, 105);
        daysSlider.setMajorTickSpacing(25);
        daysSlider.setMinorTickSpacing(5);
        daysSlider.setPaintTicks(true);
        daysSlider.setPaintLabels(true);
        daysSlider.setFont(new Font("Consolas", Font.PLAIN, 12));
        formPanel.add(daysLabel);
        formPanel.add(daysSlider);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Consolas", Font.BOLD, 14));
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Consolas", Font.BOLD, 14));

        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder sb = new StringBuilder();
                if (nameField.getText().isEmpty()){
                    sb.append("\nFull name is missing");
                }
                if (phoneField.getText().isEmpty()){
                    sb.append("\nNo phone number inserted");
                } else{
                    if (phoneField.getText().length() != 9){
                        sb.append("\nIncorrect amount of numerics for phone number");
                    } else {
                        for (int i = 0; i < phoneField.getText().length(); i++) {
                            if (!Character.isDigit(phoneField.getText().charAt(i))){
                                sb.append("\nIncorrect phone number format!");
                                break;
                            }
                        }
                    }
                }

                //studentska sleva + neco jineho nez mesto
                if (discountCheckBox.isSelected() && !cityOption.isSelected()){
                    sb.append("\nStudents can only pick Cities");
                }

                if (beachOption.isSelected() && daysSlider.getValue() > 60){
                    sb.append("\nBeach cannot be reserved for more than 60 days");
                }

                String destination = "";
                if (beachOption.isSelected()) destination = "Beach";
                if (mountainOption.isSelected()) destination = "Mountains";
                if (cityOption.isSelected()) destination = "City";

                if (sb.isEmpty()){
                    Vacation v = new Vacation(nameField.getText(), phoneField.getText(), destination, daysSlider.getValue(), discountCheckBox.isSelected());
                    parentWindow.tableModel.addRow(v.returnAsTableRow());
                    parentWindow.vacations.add(v);
                    JOptionPane.showMessageDialog(null, "All OK, form loaded", "Submitted", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }else {
                    JOptionPane.showMessageDialog(null,sb.toString(),"Errors", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
                phoneField.setText("");
                discountCheckBox.setSelected(false);
                optionsGroup.clearSelection();
                daysSlider.setValue(0);
            }
        });
    }
}
