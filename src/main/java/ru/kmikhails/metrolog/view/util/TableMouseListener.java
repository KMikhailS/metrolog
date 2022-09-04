package ru.kmikhails.metrolog.view.util;

import ru.kmikhails.metrolog.domain.Device;
import ru.kmikhails.metrolog.service.DeviceService;
import ru.kmikhails.metrolog.view.ShortDeviceFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TableMouseListener extends MouseAdapter {
	private final JTable table;
	private final DeviceService deviceService;
	private final ShortDeviceFrame shortDeviceFrame;

	public TableMouseListener(JTable table, DeviceService deviceService, ShortDeviceFrame shortDeviceFrame) {
		this.deviceService = deviceService;
		this.table = table;
		this.shortDeviceFrame = shortDeviceFrame;
	}

	@Override
	public void mousePressed(MouseEvent event) {
		Point point = event.getPoint();
		int currentRow = table.rowAtPoint(point);
		table.setRowSelectionInterval(currentRow, currentRow);
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		if (event.getClickCount() == 2) {
			Device device = findDeviceForRow();
			shortDeviceFrame.showExistFrame(device);
		}
	}

	private Device findDeviceForRow() {
		int rowNumber = table.getSelectedRow();
		String name = ((String) table.getValueAt(rowNumber, 0));
		String factoryNumber = (String) table.getValueAt(rowNumber, 5);

		return deviceService.findByNameAndFactoryNumber(name, factoryNumber);
	}
}
