package com.switchfully.vaadin.ordergui.webapp.views.forms;

import com.google.common.collect.Lists;
import com.switchfully.vaadin.ordergui.webapp.views.validators.TextFieldAMountOfStockValidator;
import com.switchfully.vaadin.ordergui.webapp.views.validators.TextFieldDescriptionValidator;
import com.switchfully.vaadin.ordergui.webapp.views.validators.TextFieldNameValidator;
import com.switchfully.vaadin.ordergui.webapp.views.validators.TextFieldPriceValidator;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Collections;
import java.util.List;

// copied and adapted code from https://github.com/stevendecock/vaadinbooking
// and switchfully repositories
// and book of vaadin

public class ItemEditForm extends CustomComponent {

    private final VerticalLayout mainLayout;
    private TextField name = new TextField("Name");
    private TextField description = new TextField("Description");
    private TextField price = new TextField("Price");
    private TextField amountOfStock = new TextField("Amount of Stock");

    public ItemEditForm() {
        mainLayout = new VerticalLayout();
        mainLayout.removeAllComponents();
        mainLayout.setMargin(true);
        mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setCompositionRoot(mainLayout);
    }

    protected void init() {
        mainLayout.removeAllComponents();

        //addNavigationBar();
        addHeader();
        addNameForm();
        addDescriptionForm();
        addPriceAndAmountOfStockForm();
    }

//    protected void addNavigationBar() {
//        HorizontalLayout navigationBar = new HorizontalLayout();
//        ComboBox comboBoxItems = new ComboBox("");
//        comboBoxItems.setNullSelectionAllowed(false);
//
//        Map<String, String> navigationOptions = new HashMap<>();
//        navigationOptions.put("Search item", OrderGUI.VIEW_ITEM_OVERVIEW);
//        navigationOptions.put("Update item", OrderGUI.UPDATE_ITEM);
//
//        comboBoxItems.addItems("Search item");
//        comboBoxItems.addItems("Update item");
//        //http://zetcode.com/vaadin/combobox/
//        comboBoxItems.addValueChangeListener(event -> {
//            String item = event.getProperty().getValue().toString();
//            getUI().getNavigator().navigateTo(navigationOptions.get(item));
//        });
//
//        navigationBar.addComponents(comboBoxItems);
//        mainLayout.addComponent(navigationBar);
//    }

    protected void addHeader() {
        Label labelHeader = new Label("New Item");
        labelHeader.setStyleName(ValoTheme.LABEL_H1);
        mainLayout.addComponent(labelHeader);
    }

    protected void addNameForm() {
        name.setInputPrompt("Fill in the item name...");
        name.setSizeFull();
        name.setRequired(true);
        name.setNullRepresentation("");
        name.addValidator(new TextFieldNameValidator());
        mainLayout.addComponents(name);
    }

    protected void addDescriptionForm() {
        description.setInputPrompt("Fill in the item description...");
        description.setSizeFull();
        description.setRequired(true);
        description.setNullRepresentation("");
        description.addValidator(new TextFieldDescriptionValidator());
        mainLayout.addComponents(description);
    }

    protected void addPriceAndAmountOfStockForm() {
        HorizontalLayout priceAndAmountOfStockLayout = new HorizontalLayout();
        priceAndAmountOfStockLayout.setSpacing(true);

        priceAndAmountOfStockLayout.addComponents(addPriceForm(), addAmountOfStockForm());
        mainLayout.addComponents(priceAndAmountOfStockLayout);
    }

    protected Component addPriceForm() {
        VerticalLayout priceLayout = new VerticalLayout();

        price.setNullRepresentation("");
        price.setRequired(true);
        price.addValidator(new TextFieldPriceValidator());

        priceLayout.addComponents(price);
        return priceLayout;
    }

    protected Component addAmountOfStockForm() {
        VerticalLayout amountOfStockLayout = new VerticalLayout();
        amountOfStock.setNullRepresentation("");
        amountOfStock.setRequired(true);
        amountOfStock.setImmediate(true);
        amountOfStock.addValidator(new TextFieldAMountOfStockValidator());
        amountOfStockLayout.addComponents(amountOfStock);
        return amountOfStockLayout;
    }

    protected VerticalLayout getMainLayout() {
        return mainLayout;
    }

    protected List<TextField> getAllTextFields() {
        return Collections.unmodifiableList(Lists.newArrayList(name, description, price, amountOfStock));
    }
}
