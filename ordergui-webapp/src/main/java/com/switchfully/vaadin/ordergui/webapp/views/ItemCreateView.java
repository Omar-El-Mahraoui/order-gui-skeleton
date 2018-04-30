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

public class ItemCreateView extends CustomComponent implements View {

    private Item itemToCreate = new Item();
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
    private Button buttonCreate;
    private Button buttonCancel;
    private BeanFieldGroup<Item> itemBeanFieldGroup;

    @Autowired
    public ItemCreateView(ItemResource itemResource) {
        this.itemResource = itemResource;
        mainLayout = new VerticalLayout();
        mainLayout.removeAllComponents();
        mainLayout.setMargin(true);
        mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setCompositionRoot(mainLayout);
        itemBeanFieldGroup = BeanFieldGroup.bindFieldsBuffered(itemToCreate, this);
    }

    private void init() {
        mainLayout.removeAllComponents();

        addNavigationBar();
        addHeader();
        addNameForm();
        addDescriptionForm();
        addPriceAndAmountOfStockForm();
        addCreateAndCancelButtons();
    }

    private void addNavigationBar() {
        HorizontalLayout navigationBar = new HorizontalLayout();
        ComboBox comboBoxItems = new ComboBox("");
        comboBoxItems.setNullSelectionAllowed(false);

        Map<String, String> navigationOptions = new HashMap<>();
        navigationOptions.put("Search item", OrderGUI.VIEW_ITEM_OVERVIEW);
        navigationOptions.put("Update item", OrderGUI.UPDATE_ITEM);

        comboBoxItems.addItems("Search item");
        comboBoxItems.addItems("Update item");
        //http://zetcode.com/vaadin/combobox/
        comboBoxItems.addValueChangeListener(event -> {
            String item = event.getProperty().getValue().toString();
            getUI().getNavigator().navigateTo(navigationOptions.get(item));
        });

        navigationBar.addComponents(comboBoxItems);
        mainLayout.addComponent(navigationBar);
    }

    private void addHeader() {
        labelHeader = new Label("New Item");
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

    private void addDescriptionForm() {
        description.setInputPrompt("Fill in the item description...");
        description.setSizeFull();
        description.setRequired(true);
        description.setNullRepresentation("");
        description.addValidator(new TextFieldDescriptionValidator());
        mainLayout.addComponents(description);
    }

    private void addPriceAndAmountOfStockForm() {
        priceAndAmountOfStockLayout = new HorizontalLayout();
        priceAndAmountOfStockLayout.setSpacing(true);

        priceAndAmountOfStockLayout.addComponents(addPriceForm(), addAmountOfStockForm());
        mainLayout.addComponents(priceAndAmountOfStockLayout);
    }

    private Component addPriceForm() {
        priceLayout = new VerticalLayout();

        price.setNullRepresentation("");
        price.setRequired(true);
        price.addValidator(new TextFieldPriceValidator());

        priceLayout.addComponents(price);
        return priceLayout;
    }

    private Component addAmountOfStockForm() {
        amountOfStockLayout = new VerticalLayout();
        amountOfStock.setNullRepresentation("");
        amountOfStock.setRequired(true);
        amountOfStock.setImmediate(true);
        amountOfStock.addValidator(new TextFieldAMountOfStockValidator());
        amountOfStockLayout.addComponents(amountOfStock);
        return amountOfStockLayout;
    }

    private void addCreateAndCancelButtons() {
        HorizontalLayout horizontalLayoutCreateAndCancelButtons = new HorizontalLayout();
        horizontalLayoutCreateAndCancelButtons.setSpacing(true);
        horizontalLayoutCreateAndCancelButtons.setMargin(true);

        buttonCreate = new Button("Create");
        buttonCreate.setStyleName(ValoTheme.BUTTON_PRIMARY);
        buttonCreate.addClickListener(event -> save());

        buttonCancel = new Button("Cancel");
        buttonCancel.addClickListener(event -> {
            getUI().getNavigator().navigateTo(OrderGUI.VIEW_ITEM_OVERVIEW);
            name.clear();
            description.clear();
            price.clear();
            amountOfStock.clear();
        });


        horizontalLayoutCreateAndCancelButtons.addComponents(buttonCreate, buttonCancel);

        mainLayout.addComponents(horizontalLayoutCreateAndCancelButtons);
    }

    //https://github.com/stevendecock/vaadinbooking
    private void save() {
        try {
            itemBeanFieldGroup.commit();
            itemResource.createItem(itemToCreate);
        } catch (FieldGroup.CommitException e) {
            // https://coderwall.com/p/im4lja/joining-objects-into-a-string-with-java-8-stream-api
            Notification.show("Cannot create item: \n- "
                            + e.getInvalidFields().values().stream()
                            .map(e1 -> e1.getMessage())
                            .collect(Collectors.joining("\n- "))
                    , Notification.Type.WARNING_MESSAGE);
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        init();
    }
}
