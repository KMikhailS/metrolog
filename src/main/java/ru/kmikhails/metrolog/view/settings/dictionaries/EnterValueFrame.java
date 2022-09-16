package ru.kmikhails.metrolog.view.settings.dictionaries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class EnterValueFrame extends JFrame {
    private static final Dimension FORM_SIZE = new Dimension(450, 140);

    private final SaveValueListener listener;
    private final boolean isUpdate;
    private final String oldValue;
    private JTextField enterTextField;

    public EnterValueFrame(SaveValueListener listener, String oldValue, boolean isUpdate) {
        this.listener = listener;
        this.oldValue = oldValue;
        this.isUpdate = isUpdate;
    }

    public void init() {
        int fontSize = 16;
        this.setSize(FORM_SIZE);
        this.setResizable(false);
        this.setTitle("Ввод значения");
        this.setLayout(new BorderLayout());
        Font font = new Font(null, Font.PLAIN, fontSize);

        JPanel valuePanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 10));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel enterLabel = new JLabel("Введите значение");
        enterLabel.setFont(font);
        enterTextField = new JTextField();
        enterTextField.setText(oldValue);
        enterTextField.setFont(font);
        enterTextField.setColumns(20);
        valuePanel.add(enterLabel);
        valuePanel.add(enterTextField);

        Dimension buttonDimension = new Dimension(110, 30);
        JButton saveButton = new JButton("Сохранить");
        saveButton.setFont(font);
        saveButton.setPreferredSize(buttonDimension);
        saveButton.addActionListener(e -> addSaveButtonActionListener());
        JButton cancelButton = new JButton("Отмена");
        cancelButton.setFont(font);
        cancelButton.setPreferredSize(buttonDimension);
        cancelButton.addActionListener(e -> this.dispose());
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(valuePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        this.setLocationRelativeTo(null);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        this.setVisible(true);
    }

    private void addSaveButtonActionListener() {
        String newValue = enterTextField.getText();
        if (newValue == null || newValue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Введите не пустое значение",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        } else {
            if (isUpdate) {
                if (!oldValue.equals(newValue)) {
                    listener.updateValue(oldValue, newValue);
                }
            } else {
                listener.saveValue(newValue);
            }
            this.dispose();
        }
    }
}
