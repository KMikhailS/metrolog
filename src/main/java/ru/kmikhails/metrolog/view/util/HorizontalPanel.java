package ru.kmikhails.metrolog.view.util;

import javax.swing.*;
import java.awt.*;

public class HorizontalPanel extends JPanel {
    private static final long serialVersionUID = 240L;

    private final Box subPanel = Box.createHorizontalBox();

    private final float verticalAlign;

    private final int hgap;

    public HorizontalPanel() {
        this(5, CENTER_ALIGNMENT);
    }

    public HorizontalPanel(Color bk) {
        this();
        subPanel.setBackground(bk);
        this.setBackground(bk);
    }

    public HorizontalPanel(int hgap, float verticalAlign) {
        super(new BorderLayout());
        add(subPanel, BorderLayout.CENTER);
        this.hgap = hgap;
        this.verticalAlign = verticalAlign;
    }

    @Override
    public Component add(Component c) {
        if (hgap > 0 && subPanel.getComponentCount() > 0) {
            subPanel.add(Box.createHorizontalStrut(hgap));
        }

        if (c instanceof JComponent) {
            ((JComponent) c).setAlignmentY(verticalAlign);
        }
        return subPanel.add(c);
    }
}
