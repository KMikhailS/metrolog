package ru.kmikhails.metrolog.view.util;

import javax.swing.*;
import java.awt.*;

public class VerticalPanel extends JPanel {
    private static final long serialVersionUID = 240L;

    private final Box subPanel = Box.createVerticalBox();

    private final float horizontalAlign;

    private final int vgap;

    public VerticalPanel() {
        this(5, LEFT_ALIGNMENT);
    }

    public VerticalPanel(Color bkg) {
        this();
        subPanel.setBackground(bkg);
        this.setBackground(bkg);
    }

    public VerticalPanel(int vgap, float horizontalAlign) {
        super(new BorderLayout());
        add(subPanel, BorderLayout.NORTH);
        this.vgap = vgap;
        this.horizontalAlign = horizontalAlign;
    }

    @Override
    public Component add(Component c) {
        if (vgap > 0 && subPanel.getComponentCount() > 0) {
            subPanel.add(Box.createVerticalStrut(vgap));
        }

        if (c instanceof JComponent) {
            ((JComponent) c).setAlignmentX(horizontalAlign);
        }

        return subPanel.add(c);
    }
}
