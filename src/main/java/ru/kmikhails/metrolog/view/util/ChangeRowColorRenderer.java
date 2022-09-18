package ru.kmikhails.metrolog.view.util;

import ru.kmikhails.metrolog.view.DeviceTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ChangeRowColorRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        DeviceTableModel model = (DeviceTableModel) table.getModel();
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        int trueRow = table.getRowSorter().convertRowIndexToModel(row);
        cell.setBackground(model.getRowColor(trueRow));
        if (isSelected) {
            cell.setBackground(new Color(180, 180, 180));
        }
        return cell;
    }
}
