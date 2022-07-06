package ru.kmikhails.metrolog.view.settings;

import ru.kmikhails.metrolog.view.util.HorizontalPanel;
import ru.kmikhails.metrolog.view.util.VerticalPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.List;

public abstract class AbstractDictionarySettingsFrame extends JFrame
		implements SaveValueListener, ReconfigureDeviceFrameListener {

	private final JButton addButton = new JButton("Добавить");
	private final JButton deleteButton = new JButton("Удалить");
	private final JButton updateButton = new JButton("Редактировать");
	private final JButton okButton = new JButton("OK");
	private final List<String> dictionaryElements;
	protected JList<String> jList;
	protected DefaultListModel<String> listModel;
	private final String frameName;
	private final ReconfigureDeviceFrameListener listener;

	public AbstractDictionarySettingsFrame(List<String> dictionaryElements, String frameName,
										   ReconfigureDeviceFrameListener listener) {
		this.dictionaryElements = dictionaryElements;
		this.frameName = frameName;
		this.listener = listener;
	}

	public void init() {
		listModel = new DefaultListModel<>();
		for (String element : dictionaryElements) {
			listModel.addElement(element);
		}
		int fontSize = 16;
		Font font = new Font(null, Font.PLAIN, fontSize);
		Dimension buttonDimension = new Dimension(150, 40);

		VerticalPanel buttonPanel = new VerticalPanel(10, 0.f);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		JPanel addPanel = new JPanel(new BorderLayout());
		addPanel.setPreferredSize(buttonDimension);
		addButton.setFont(font);
		addPanel.add(addButton);
		addButton.addActionListener(e -> openSaveValueFrame());
		buttonPanel.add(addPanel);
		JPanel deletePanel = new JPanel(new BorderLayout());
		deletePanel.setPreferredSize(buttonDimension);
		deleteButton.setFont(font);
		deletePanel.add(deleteButton);
		deleteButton.addActionListener(e -> deleteElement());
		buttonPanel.add(deletePanel);
		JPanel updatePanel = new JPanel(new BorderLayout());
		updatePanel.setPreferredSize(buttonDimension);
		updateButton.setFont(font);
		updatePanel.add(updateButton);
		updateButton.addActionListener(e -> openUpdateValueFrame());
		buttonPanel.add(updatePanel);
		JPanel emptyPanel = new JPanel();
		emptyPanel.setPreferredSize(new Dimension(150, 328));
		buttonPanel.add(emptyPanel);
		JPanel okPanel = new JPanel(new BorderLayout());
		okPanel.setPreferredSize(buttonDimension);
		okButton.setFont(font);
		okPanel.add(okButton);
		okButton.addActionListener(e -> reconfigureDeviceFrame());
		buttonPanel.add(okPanel);

		jList = new JList<>(listModel);
		jList.setBorder(new EmptyBorder(5, 5, 5, 5));
		jList.setFont(new Font(null, Font.PLAIN, 18));
		jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList.setLayoutOrientation(JList.VERTICAL);

		JScrollPane listScroller = new JScrollPane(jList);
		listScroller.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10),
				BorderFactory.createTitledBorder(frameName)));
		listScroller.setPreferredSize(new Dimension(350, 600));

		Border border = listScroller.getBorder();
		Border margin = new EmptyBorder(10, 10, 10, 10);
		listScroller.setBorder(new CompoundBorder(border, margin));

		HorizontalPanel processPanel = new HorizontalPanel();
		processPanel.add(listScroller);
		processPanel.add(buttonPanel);

		setTitle(frameName);
		setLayout(new BorderLayout(10, 10));
		add(processPanel);
		setSize(new Dimension(600, 600));
		setLocationRelativeTo(null);
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void reconfigureDeviceFrame() {
		listener.reconfigureDeviceFrame();
		this.dispose();
	}

	protected void openSaveValueFrame() {
		EnterValueFrame enterValueFrame = new EnterValueFrame(this, "", false);
		SwingUtilities.invokeLater(enterValueFrame::init);
	}

	protected void openUpdateValueFrame() {
		if (jList.getSelectedValue() != null) {
			EnterValueFrame enterValueFrame = new EnterValueFrame(this, jList.getSelectedValue(), true);
			SwingUtilities.invokeLater(enterValueFrame::init);
		}
	}

	protected abstract void updateElement(String oldValue, String newValue);

	protected abstract void deleteElement();

	protected abstract void addElement(String value);
}
