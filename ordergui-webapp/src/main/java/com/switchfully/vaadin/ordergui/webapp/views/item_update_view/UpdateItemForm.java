package com.switchfully.vaadin.ordergui.webapp.views.item_update_view;

// copied and adapted code from https://github.com/stevendecock/vaadinbooking
// and switchfully repositories
// and book of vaadin

import com.google.common.collect.Lists;
import com.switchfully.vaadin.ordergui.interfaces.items.Item;
import com.switchfully.vaadin.ordergui.webapp.OrderGUI;
import com.switchfully.vaadin.ordergui.webapp.views.validators.TextFieldAMountOfStockValidator;
import com.switchfully.vaadin.ordergui.webapp.views.validators.TextFieldDescriptionValidator;
import com.switchfully.vaadin.ordergui.webapp.views.validators.TextFieldNameValidator;
import com.switchfully.vaadin.ordergui.webapp.views.validators.TextFieldPriceValidator;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateItemForm extends CustomComponent {

    private TextField name = new TextField("Name");
    private TextField description = new TextField("Description");
    private TextField price = new TextField("Price");
    private TextField amountOfStock = new TextField("Amount of Stock");
    private VerticalLayout mainLayout = new VerticalLayout();
    private Item itemToUpdate;
    private BeanFieldGroup<Item> binder;

    public UpdateItemForm(Item itemToUpdate) {
        this.itemToUpdate = itemToUpdate;
        mainLayout.setMargin(true);
        mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setCompositionRoot(mainLayout);

        init();
    }

    private void init() {
        this.binder = new BeanFieldGroup<>(Item.class);
        binder.setItemDataSource(itemToUpdate);

        addNavigationBar();
        addHeader(itemToUpdate.getId());
        addNameForm();
        fillInNameForm();
        addDescriptionForm();
        fillInDescriptionForm();
        addPriceAndAmountOfStockForm();
        fillInPriceAndAmountOfStockForm();

        binder.bind(name, "name");
        binder.bind(description, "description");
        binder.bind(price, "price");
        binder.bind(amountOfStock, "amountOfStock");
    }

    private void addNavigationBar() {
        HorizontalLayout navigationBar = new HorizontalLayout();
        ComboBox comboBoxItems = new ComboBox("");
        comboBoxItems.setNullSelectionAllowed(false);

        Map<String, String> navigationOptions = new HashMap<>();
        navigationOptions.put("Search item", OrderGUI.VIEW_ITEM_OVERVIEW);
        navigationOptions.put("Create item", OrderGUI.CREATE_ITEM);

        comboBoxItems.addItems("Search item");
        comboBoxItems.addItems("Create item");
        //http://zetcode.com/vaadin/combobox/
        comboBoxItems.addValueChangeListener(event -> {
            String item = event.getProperty().getValue().toString();
            getUI().getNavigator().navigateTo(navigationOptions.get(item));
        });

        navigationBar.addComponents(comboBoxItems);
        mainLayout.addComponent(navigationBar);
    }

    private void addHeader(String itemId) {
        Label labelHeader = new Label("Update Item: " + itemId);
        labelHeader.setStyleName(ValoTheme.LABEL_H1);
        mainLayout.addComponent(labelHeader);
    }

    private void addNameForm() {
        name.setInputPrompt("Fill in the item name...");
        name.setSizeFull();
        name.setRequired(true);
        name.setNullRepresentation("");
        name.addValidator(new TextFieldNameValidator());
        mainLayout.addComponents(name);
    }

    private void fillInNameForm() {
        name.setValue(itemToUpdate.getName());
        name.addTextChangeListener(event -> name.setValue(event.getText()));
    }

    private void addDescriptionForm() {
        description.setInputPrompt("Fill in the item description...");
        description.setSizeFull();
        description.setRequired(true);
        description.setNullRepresentation("");
        description.addValidator(new TextFieldDescriptionValidator());
        mainLayout.addComponents(description);
    }

    private void fillInDescriptionForm() {
        description.setValue(itemToUpdate.getDescription());
    }

    private void addPriceAndAmountOfStockForm() {
        HorizontalLayout priceAndAmountOfStockLayout = new HorizontalLayout();
        priceAndAmountOfStockLayout.setSpacing(true);

        priceAndAmountOfStockLayout.addComponents(addPriceForm(), addAmountOfStockForm());
        mainLayout.addComponents(priceAndAmountOfStockLayout);
    }

    private void fillInPriceAndAmountOfStockForm() {
        fillInPriceForm();
        fillInAmountOfStockForm();
    }

    private Component addPriceForm() {
        VerticalLayout priceLayout = new VerticalLayout();

        price.setNullRepresentation("");
        price.setRequired(true);
        price.addValidator(new TextFieldPriceValidator());
        priceLayout.addComponents(price);
        return priceLayout;
    }

    private void fillInPriceForm() {
        price.setValue(itemToUpdate.getPrice().toString());
    }

    private Component addAmountOfStockForm() {
        VerticalLayout amountOfStockLayout = new VerticalLayout();
        amountOfStock.setNullRepresentation("");
        amountOfStock.setRequired(true);
        amountOfStock.setImmediate(true);
        amountOfStock.addValidator(new TextFieldAMountOfStockValidator());
        amountOfStockLayout.addComponents(amountOfStock);
        return amountOfStockLayout;
    }

    private void fillInAmountOfStockForm() {
        amountOfStock.setValue(itemToUpdate.getAmountOfStock().toString());
    }

    public List<TextField> getAllTextFields() {
        return Lists.newArrayList(name, description, price, amountOfStock);
    }

    public BeanFieldGroup<Item> getBinder() {
        return binder;
    }
}
