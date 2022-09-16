package ru.kmikhails.metrolog.view.settings.periods;

import ru.kmikhails.metrolog.view.util.HorizontalPanel;
import ru.kmikhails.metrolog.view.util.VerticalPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.List;

public class WarnPeriodsFrame extends JFrame {

	private final List<String> deviceNames;
	private JPopupMenu deviceMenu;

	public WarnPeriodsFrame(List<String> deviceNames) {
		this.deviceNames = deviceNames;
	}

	public void init() {
		int fontSize = 10;
		Font font = new Font(null, Font.PLAIN, fontSize);

		VerticalPanel mainPanel = new VerticalPanel(10, 0.f);
		HorizontalPanel buttonPanel = new HorizontalPanel(10, 0.f);
		JButton plusButton = new JButton("Plus");
		deviceMenu = new JPopupMenu();
		for (String deviceName : deviceNames) {
			JMenuItem menuItem = new JMenuItem(deviceName);
			menuItem.setFont(font);
			deviceMenu.add(menuItem);
		}
		plusButton.addActionListener(this::openPopupMenu);
		buttonPanel.add(plusButton);
		mainPanel.add(buttonPanel);




		add(mainPanel);

		setSize(new Dimension(600, 600));
		setLocationRelativeTo(null);
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		setResizable(false);
		setVisible(true);
	}

	private void openPopupMenu(ActionEvent e) {
		Component source = (Component) e.getSource();
		Point point = source.getLocationOnScreen();
		deviceMenu.show(this,0,0);
		deviceMenu.setLocation(point.x,point.y+source.getHeight());
	}
}
