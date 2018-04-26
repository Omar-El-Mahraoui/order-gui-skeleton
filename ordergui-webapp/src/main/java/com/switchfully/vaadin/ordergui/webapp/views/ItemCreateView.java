package com.switchfully.vaadin.ordergui.webapp.views;

import com.switchfully.vaadin.ordergui.interfaces.items.ItemResource;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

// copied and adapted code from https://github.com/stevendecock/vaadinbooking
// and switchfully repositories

public class ItemCreateView extends CustomComponent implements View {

    private final VerticalLayout mainLayout;
    private ItemResource itemResource;

    @Autowired
    public ItemCreateView(ItemResource itemResource) {
        mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        setCompositionRoot(mainLayout);

        init();
    }

    private void init() {
        mainLayout.removeAllComponents();

        addHeader();
        addNameForm();
        addDescriptionForm();
        addPriceAndAmountOfStockForm();
        addCreateAndCancelButtons();
    }

    private void addHeader() {
        Label labelHeader = new Label("New Item");
        labelHeader.setStyleName(ValoTheme.LABEL_H1);
        mainLayout.addComponent(labelHeader);
    }

    private void addNameForm() {
        Label labelName = new Label("Name");
        TextField textFieldName = new TextField();
        textFieldName.setInputPrompt("Fill in the item name...");
        textFieldName.setSizeFull();
        mainLayout.addComponents(labelName, textFieldName);
    }

    private void addDescriptionForm() {
        Label labelDescription = new Label("Description");
        TextField textFieldDescription = new TextField();
        textFieldDescription.setInputPrompt("Fill in the item description...");
        textFieldDescription.setSizeFull();
        mainLayout.addComponents(labelDescription, textFieldDescription);
    }

    private void addPriceAndAmountOfStockForm() {

    }

    private void addCreateAndCancelButtons() {

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        init();
    }
}
