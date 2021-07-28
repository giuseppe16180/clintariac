package com.clintariac.components.details.chat.message;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import com.clintariac.services.config.AppColors;
import com.clintariac.services.utils.AppUtils;

public class MessageRenderer implements ListCellRenderer<MessageModel> {

	private JPanel mainPanel;


	/**
	 * @param list
	 * @param reservation
	 * @param index
	 * @param isSelected
	 * @param cellHasFocus
	 * @return Component
	 */
	@Override
	public Component getListCellRendererComponent(
			JList<? extends MessageModel> list,
			MessageModel message,
			int index,
			boolean isSelected,
			boolean cellHasFocus) {

		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());

		GridBagConstraints gbc;

		final JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		panel.setBorder(AppUtils.smallBorderLeft(message.getDateTime()));

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.ipadx = 10;
		gbc.ipady = 10;
		gbc.insets = new Insets(6, 6, 6, 6);
		gbc.anchor = message.isUserSent() ? GridBagConstraints.WEST : GridBagConstraints.EAST;
		mainPanel.add(panel, gbc);


		final JLabel messageLabel = new JLabel();
		messageLabel.setFont(AppUtils.text);
		messageLabel.setText(message.getText());

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(6, 6, 2, 6);
		panel.add(messageLabel, gbc);


		panel.setBackground(message.isUserSent()
				? AppColors.MESSAGE_BACKGROUND1
				: AppColors.MESSAGE_BACKGROUND2);



		return mainPanel;
	}
}
