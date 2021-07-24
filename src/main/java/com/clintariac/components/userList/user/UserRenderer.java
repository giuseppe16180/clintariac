package com.clintariac.components.userList.user;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import com.clintariac.data.TicketState;
import com.clintariac.services.config.AppColors;
import com.clintariac.services.utils.AppUtils;

public class UserRenderer implements ListCellRenderer<UserModel> {

	private JPanel panel1;

	/**
	 * @param list
	 * @param user
	 * @param index
	 * @param isSelected
	 * @param cellHasFocus
	 * @return Component
	 */
	@Override
	public Component getListCellRendererComponent(

			JList<? extends UserModel> list, UserModel user, int index, boolean isSelected, boolean cellHasFocus) {

		panel1 = new JPanel();
		panel1.setLayout(new GridBagLayout());

		panel1.setBorder(AppUtils.createSimpleBorder(user.getUserId()));

		final JLabel label1 = new JLabel();
		label1.setFont(AppUtils.text);
		label1.setText(user.getFullName());

		GridBagConstraints gbc;
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(6, 6, 2, 6);
		panel1.add(label1, gbc);

		if (isSelected) {
			panel1.setBackground(AppColors.SELECTED_BACKGROUND);
		}

		return panel1;
	}
}
