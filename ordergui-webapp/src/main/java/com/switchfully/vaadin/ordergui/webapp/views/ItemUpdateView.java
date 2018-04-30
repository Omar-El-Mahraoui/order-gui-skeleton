package com.switchfully.vaadin.ordergui.webapp.views;

import com.switchfully.vaadin.ordergui.interfaces.items.Item;
import com.switchfully.vaadin.ordergui.interfaces.items.ItemResource;
import com.switchfully.vaadin.ordergui.webapp.OrderGUI;
import com.switchfully.vaadin.ordergui.webapp.views.validators.TextFieldAMountOfStockValidator;
import com.switchfully.vaadin.ordergui.webapp.views.validators.TextFieldDescriptionValidator;
import com.switchfully.vaadin.ordergui.webapp.views.validators.TextFieldNameValidator;
import com.switchfully.vaadin.ordergui.webapp.views.validators.TextFieldPriceValidator;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

// copied and adapted code from https://github.com/stevendecock/vaadinbooking
// and switchfully repositories
// and book of vaadin

public class ItemUpdateView extends com.vaadin.ui.CustomComponent implements View {
    private final VerticalLayout mainLayout;
    private ItemResource itemResource;
    private Label labelHeader;
    private TextField name = new TextField("Name");
    private TextField description = new TextField("Description");
    private HorizontalLayout priceAndAmountOfStockLayout;
    private VerticalLayout priceLayout;
    private Label labelPrice;
    private HorizontalLayout priceAmountLayout;
    private Label labelEuroSymbol;
    private TextField price = new TextField("Price");
    private VerticalLayout amountOfStockLayout;
    private TextField amountOfStock = new TextField("Amount of Stock");
    private Button buttonUpdate;
    private Button buttonCancel;
    private BeanFieldGroup<Item> itemBeanFieldGroup;
    private Item itemToUpdate = new Item();

    @Autowired
    public ItemUpdateView(ItemResource itemResource) {
        this.itemResource = itemResource;
        mainLayout = new VerticalLayout();
        mainLayout.removeAllComponents();
        mainLayout.setMargin(true);
        mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setCompositionRoot(mainLayout);

    }

    private void init(String itemId) {
        mainLayout.removeAllComponents();
        itemToUpdate = itemResource.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .get();
        itemBeanFieldGroup = new BeanFieldGroup<>(Item.class);
        itemBeanFieldGroup.setItemDataSource(itemToUpdate);
        addNavigationBar();
        addHeader(itemId);
        addNameForm();
        fillInNameForm();
        addDescriptionForm();
        fillInDescriptionForm();
        addPriceAndAmountOfStockForm();
        fillInPriceAndAmountOfStockForm();
        addUpdateAndCancelButtons();
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
        labelHeader = new Label("Update Item: " + itemId);
        labelHeader.setStyleName(ValoTheme.LABEL_H1);
        mainLayout.addComponent(labelHeader);
    }

    private void addNameForm() {
        name.setInputPrompt("Fill in the item name...");
        name.setSizeFull();
        name.setRequired(true);
        name.setNullRepresentation("");
        name.addValidator(new TextFieldNameValidator());
        itemBeanFieldGroup.buildAndBind("name");
        mainLayout.addComponents(name);
    }

    private void fillInNameForm() {
        name.setValue(itemToUpdate.getName());
    }

    private void addDescriptionForm() {
        description.setInputPrompt("Fill in the item description...");
        description.setSizeFull();
        description.setRequired(true);
        description.setNullRepresentation("");
        description.addValidator(new TextFieldDescriptionValidator());
        itemBeanFieldGroup.buildAndBind("description");
        mainLayout.addComponents(description);
    }

    private void fillInDescriptionForm() {
        description.setValue(itemToUpdate.getDescription());
    }

    private void addPriceAndAmountOfStockForm() {
        priceAndAmountOfStockLayout = new HorizontalLayout();
        priceAndAmountOfStockLayout.setSpacing(true);

        priceAndAmountOfStockLayout.addComponents(addPriceForm(), addAmountOfStockForm());
        mainLayout.addComponents(priceAndAmountOfStockLayout);
    }

    private void fillInPriceAndAmountOfStockForm() {
        fillInPriceForm();
        fillInAmountOfStockForm();
    }

    private Component addPriceForm() {
        priceLayout = new VerticalLayout();

        price.setNullRepresentation("");
        price.setRequired(true);
        price.addValidator(new TextFieldPriceValidator());
        itemBeanFieldGroup.buildAndBind("price");
        priceLayout.addComponents(price);
        return priceLayout;
    }

    private void fillInPriceForm() {
        price.setValue(itemToUpdate.getPrice().toString());
    }

    private Component addAmountOfStockForm() {
        amountOfStockLayout = new VerticalLayout();
        amountOfStock.setNullRepresentation("");
        amountOfStock.setRequired(true);
        amountOfStock.setImmediate(true);
        amountOfStock.addValidator(new TextFieldAMountOfStockValidator());
        itemBeanFieldGroup.buildAndBind("amountOfStock");
        amountOfStockLayout.addComponents(amountOfStock);
        return amountOfStockLayout;
    }

    private void fillInAmountOfStockForm() {
        amountOfStock.setValue(itemToUpdate.getAmountOfStock().toString());
    }

    private void addUpdateAndCancelButtons() {
        HorizontalLayout horizontalLayoutCreateAndCancelButtons = new HorizontalLayout();
        horizontalLayoutCreateAndCancelButtons.setSpacing(true);
        horizontalLayoutCreateAndCancelButtons.setMargin(true);

        buttonUpdate = new Button("Update");
        buttonUpdate.setStyleName(ValoTheme.BUTTON_PRIMARY);
        buttonUpdate.addClickListener(event -> {
            update();
            getUI().getNavigator().navigateTo(OrderGUI.VIEW_ITEM_OVERVIEW);
            name.clear();
            description.clear();
            price.clear();
            amountOfStock.clear();
        });

        buttonCancel = new Button("Cancel");
        buttonCancel.addClickListener(event -> {
            getUI().getNavigator().navigateTo(OrderGUI.VIEW_ITEM_OVERVIEW);
//            name.clear();
//            description.clear();
//            price.clear();
//            amountOfStock.clear();
        });


        horizontalLayoutCreateAndCancelButtons.addComponents(buttonUpdate, buttonCancel);

        mainLayout.addComponents(horizontalLayoutCreateAndCancelButtons);
    }

    //https://github.com/stevendecock/vaadinbooking
    private void update() {
        try {
            itemBeanFieldGroup.commit();
            itemResource.updateItem(itemToUpdate.getId(), itemToUpdate);
        } catch (FieldGroup.CommitException e) {
            // https://coderwall.com/p/im4lja/joining-objects-into-a-string-with-java-8-stream-api
            Notification.show("Cannot create item: \n- "
                            + e.getInvalidFields().values().stream()
                            .map(e1 -> e1.getMessage())
                            .collect(Collectors.joining("\n- "))
                    , Notification.Type.WARNING_MESSAGE);
        }
    }

    //https://github.com/stevendecock/vaadinbooking
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        String itemId = event.getParameters();
        init(itemId);
    }
}
