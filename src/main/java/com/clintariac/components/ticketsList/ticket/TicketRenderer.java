package com.clintariac.components.ticketsList.ticket;

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
import org.apache.commons.lang3.StringUtils;

public class TicketRenderer implements ListCellRenderer<TicketModel> {

	private JPanel panel1;


	/**
	 * @param list
	 * @param ticket
	 * @param index
	 * @param isSelected
	 * @param cellHasFocus
	 * @return Component
	 */
	@Override
	public Component getListCellRendererComponent(JList<? extends TicketModel> list,
			TicketModel ticket, int index,
			boolean isSelected, boolean cellHasFocus) {

		panel1 = new JPanel();
		panel1.setLayout(new GridBagLayout());

		panel1.setBorder(AppUtils.createSimpleBorder(ticket.getTicketId()));

		final JLabel label1 = new JLabel();
		label1.setFont(AppUtils.text);
		label1.setText(ticket.getFullName());

		GridBagConstraints gbc;
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(2, 6, 2, 6);
		panel1.add(label1, gbc);

		final JPanel spacer1 = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.VERTICAL;
		panel1.add(spacer1, gbc);

		final JLabel label2 = new JLabel();
		label2.setFont(AppUtils.textSmall);
		label2.setText(StringUtils.abbreviate(ticket.getMessagePreview(), 32));

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(2, 6, 2, 6);
		panel1.add(label2, gbc);

		final JPanel spacer2 = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.VERTICAL;
		panel1.add(spacer2, gbc);

		final JLabel label3 = new JLabel();
		label3.setFont(AppUtils.text);
		label3.setText(ticket.getDateTime());

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(2, 6, 0, 6);
		panel1.add(label3, gbc);

		if (isSelected) {
			panel1.setBackground(AppColors.SELECTED_BACKGROUND);
			spacer1.setBackground(AppColors.SELECTED_BACKGROUND);
			spacer2.setBackground(AppColors.SELECTED_BACKGROUND);
		}

		return panel1;
	}
}
