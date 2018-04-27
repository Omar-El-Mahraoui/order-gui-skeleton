package com.switchfully.vaadin.ordergui.webapp.views;

import com.switchfully.vaadin.ordergui.interfaces.items.Item;
import com.switchfully.vaadin.ordergui.interfaces.items.ItemResource;
import com.switchfully.vaadin.ordergui.webapp.OrderGUI;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.validator.FloatRangeValidator;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

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
        setCompositionRoot(mainLayout);
        itemBeanFieldGroup = BeanFieldGroup.bindFieldsBuffered(itemToCreate, this);
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
        labelHeader = new Label("New Item");
        labelHeader.setStyleName(ValoTheme.LABEL_H1);
        mainLayout.addComponent(labelHeader);
    }

    private void addNameForm() {
        name.setInputPrompt("Fill in the item name...");
        name.setSizeFull();
        name.setRequired(true);
        name.setNullRepresentation("");
        mainLayout.addComponents(name);
    }

    private void addDescriptionForm() {
        description.setInputPrompt("Fill in the item description...");
        description.setSizeFull();
        description.setRequired(true);
        description.setNullRepresentation("");
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
//        labelPrice = new Label("Price");
//
//        priceAmountLayout = new HorizontalLayout();
//        labelEuroSymbol = new Label();
//        labelEuroSymbol.setIcon(FontAwesome.EUR);
//        price.setNullRepresentation("");
//        price.setRequired(true);
//        priceAmountLayout.addComponents(labelEuroSymbol, price);
        price.setNullRepresentation("");
        price.setRequired(true);
        price.addValidator(new FloatRangeValidator("Price cannot be negative.", 0.00000000001f
                , Float.MAX_VALUE));

        priceLayout.addComponents(price);
        return priceLayout;
    }

    private Component addAmountOfStockForm() {
        amountOfStockLayout = new VerticalLayout();
        amountOfStock.setNullRepresentation("");
        amountOfStock.setRequired(true);
        amountOfStock.addValidator(new IntegerRangeValidator("Amount of stock cannot be negative"
                                    , 1, Integer.MAX_VALUE));
        amountOfStockLayout.addComponents(amountOfStock);
        return amountOfStockLayout;
    }

    private void addCreateAndCancelButtons() {
        HorizontalLayout horizontalLayoutCreateAndCancelButtons = new HorizontalLayout();
        horizontalLayoutCreateAndCancelButtons.setSpacing(true);
        horizontalLayoutCreateAndCancelButtons.setMargin(true);

        buttonCreate = new Button("Create");
        buttonCreate.setStyleName(ValoTheme.BUTTON_PRIMARY);
        buttonCreate.addClickListener(event -> {
            save();
            getUI().getNavigator().navigateTo(OrderGUI.VIEW_ITEM_OVERVIEW);
            name.clear();
            description.clear();
            price.clear();
            amountOfStock.clear();
        });

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
            Notification.show(
                    String.format("Cannot create item: \n%s"
                            ,e.getInvalidFields().values().iterator().next().getMessage())
            ,Notification.Type.ERROR_MESSAGE);
        }
    }

//    //https://github.com/stevendecock/vaadinbooking
//    private void showNotificationFor(FieldGroup.CommitException exception) {
//        Notification notification = new Notification("Validation errors",
//                "<br/>Cannot save this item. "
//                        + ((exception.getCause() != null && exception.getCause().getMessage() != null)
//                        ? exception.getCause().getMessage() : ""),
//                Notification.Type.WARNING_MESSAGE, true);
//
//        notification.setDelayMsec(-1);
//        notification.show(Page.getCurrent());
//    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        init();
    }
}
