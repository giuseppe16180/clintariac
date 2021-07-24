package com.clintariac.components.userList;

import java.awt.Component;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import com.clintariac.components.mvc.View;
import com.clintariac.components.userList.user.UserModel;
import com.clintariac.components.userList.user.UserRenderer;
import com.clintariac.services.utils.AppUtils;

public class UsersListView implements View {

    private JScrollPane mainPanel;
    private JList<UserModel> list;

    public UsersListView() {

        mainPanel = new JScrollPane();
        mainPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.setBackground(null);

        mainPanel.setBorder(AppUtils.createMainBorder("Lista Pazienti"));

        list = new JList<>();
        list.setCellRenderer(new UserRenderer());
        list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

        mainPanel.setViewportView(list);
    }

    /**
     * @return Component
     */
    @Override
    public Component getMainComponent() {
        return this.mainPanel;
    }

    /**
     * @return JList<ReservationModel>
     */
    public JList<UserModel> getList() {
        return list;
    }
}
