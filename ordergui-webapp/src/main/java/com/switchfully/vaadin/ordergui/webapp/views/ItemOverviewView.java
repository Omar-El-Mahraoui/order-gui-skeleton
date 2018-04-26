package com.switchfully.vaadin.ordergui.webapp.views;

import com.switchfully.vaadin.ordergui.interfaces.items.Item;
import com.switchfully.vaadin.ordergui.interfaces.items.ItemResource;
import com.switchfully.vaadin.ordergui.webapp.OrderGUI;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

// copied and adapted code from https://github.com/stevendecock/vaadinbooking
// and switchfully repositories

public class ItemOverviewView extends com.vaadin.ui.CustomComponent implements View{

    private Grid grid;
    private VerticalLayout mainLayout;
    private ItemResource itemResource;
    private BeanItemContainer<Item> container;

    @Autowired
    public ItemOverviewView(ItemResource itemResource) {
        this.itemResource = itemResource;
        this.grid = new Grid();
        grid.setSizeFull();
        mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        mainLayout.setSizeFull();
        setCompositionRoot(mainLayout);
        //addNavigationBar();
        addHeader();
        renderItems();
    }

    /*private void addNavigationBar() {
        HorizontalLayout navigationBar = new HorizontalLayout();
        Button buttonItems = new Button("Items"
                , event -> getUI().getNavigator().navigateTo(OrderGUI.VIEW_ITEM_OVERVIEW));
        navigationBar.addComponents(buttonItems);
        mainLayout.addComponent(navigationBar);
    }*/

    private void addHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setSizeFull();
        header.setMargin(true);

        Label labelItems = new Label("ITEMS:");

        TextField filterField = new TextField();
        filterField.setInputPrompt("Filter by name");
        filterField.setSizeFull();
        filterField.addTextChangeListener(event -> {
            container.removeAllContainerFilters();
            container.addContainerFilter(new SimpleStringFilter("name", event.getText()
                    , true, false));

        });

        Button buttonFilter = new Button("Filter");
        buttonFilter.setStyleName(ValoTheme.BUTTON_PRIMARY);
        buttonFilter.addClickListener(event -> {
            container.removeAllContainerFilters();
            container.addContainerFilter("name", filterField.getValue(), true, false);
        });

        Button buttonNewItem = new Button("New Item");
        buttonNewItem.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        buttonNewItem.addClickListener(event -> getUI().getNavigator().navigateTo(OrderGUI.CREATE_ITEM));


        header.addComponents(labelItems, filterField, buttonFilter, buttonNewItem);
        mainLayout.addComponent(header);
    }

    private void renderItems() {
//        itemResource.getItems()
//                .forEach(item ->
//                        mainLayout.addComponent(
//                                new HorizontalLayout(
//                                        new Label("--> " + item.name + " €" + item.price))));
        container = new BeanItemContainer<>(Item.class, itemResource.getItems());
        grid.setColumns("name", "description", "price", "amountOfStock");
        grid.setContainerDataSource(container);
        mainLayout.addComponent(grid);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
