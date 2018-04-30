package com.switchfully.vaadin.ordergui.webapp.views.login_view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

// copied and adapted code from https://github.com/stevendecock/vaadinbooking
// and switchfully repositories
// and book of vaadin

public class ItemLoginView extends CustomComponent implements View {

    private VerticalLayout mainLayout = new VerticalLayout();
    private TextField textFieldUserName = new TextField("User name");
    private TextField textFieldPassword = new TextField("Password");

    public ItemLoginView() {
        mainLayout.setMargin(true);
        mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setCompositionRoot(mainLayout);
        addUserNameAndPassWordFields();
    }

    private void addUserNameAndPassWordFields() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponents(textFieldUserName, textFieldPassword);
        mainLayout.addComponents(verticalLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
