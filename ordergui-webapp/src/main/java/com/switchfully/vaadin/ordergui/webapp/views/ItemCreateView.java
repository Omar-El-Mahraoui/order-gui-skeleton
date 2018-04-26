package com.switchfully.vaadin.ordergui.webapp.views;

import com.switchfully.vaadin.ordergui.interfaces.items.ItemResource;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

public class ItemCreateView extends CustomComponent implements View {

    private final VerticalLayout mainLayout;
    private ItemResource itemResource;

    @Autowired
    public ItemCreateView(ItemResource itemResource) {
        mainLayout = new VerticalLayout();
        mainLayout.addComponent(new Label("Test"));
        setCompositionRoot(mainLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
