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

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

// copied and adapted code from https://github.com/stevendecock/vaadinbooking
// and switchfully repositories
// and book of vaadin

public class ItemOverviewView extends com.vaadin.ui.CustomComponent implements View {

    private Grid grid;
    private VerticalLayout mainLayout;
    private ItemResource itemResource;
    private BeanItemContainer<Item> container;
    private HorizontalLayout header;
    private Label labelItems;
    private TextField filterField;
    private Button buttonClearFilter;
    private Button buttonNewItem;
    private Button buttonUpdateItem;
    private Item itemSelected;

    @Autowired
    public ItemOverviewView(ItemResource itemResource) {
        this.itemResource = itemResource;
        this.grid = new Grid();
        grid.setSizeFull();
        mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        mainLayout.setSizeFull();
        mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setCompositionRoot(mainLayout);
    }

    private void init() {
        mainLayout.removeAllComponents();
        addNavigationBar();
        addHeader();
        renderItems();
    }


    private void addNavigationBar() {
        HorizontalLayout navigationBar = new HorizontalLayout();
        ComboBox comboBoxItems = new ComboBox("");
        comboBoxItems.setNullSelectionAllowed(false);

        Map<String, String> navigationOptions = new HashMap<>();
        navigationOptions.put("Create Item", OrderGUI.CREATE_ITEM);
        navigationOptions.put("Update Item", OrderGUI.UPDATE_ITEM);

        comboBoxItems.addItems("Create Item");
        comboBoxItems.addItems("Update Item");
        //http://zetcode.com/vaadin/combobox/
        comboBoxItems.addValueChangeListener(event -> {
            String item = event.getProperty().getValue().toString();
            getUI().getNavigator().navigateTo(navigationOptions.get(item));
        });

        navigationBar.addComponents(comboBoxItems);
        mainLayout.addComponent(navigationBar);
    }

    private void addHeader() {
        header = new HorizontalLayout();
        header.setSizeFull();
        header.setMargin(true);

        labelItems = new Label("ITEMS:");
        labelItems.setStyleName(ValoTheme.LABEL_H1);

        filterField = new TextField();
        filterField.setInputPrompt("Filter by name");
        filterField.setSizeFull();
        filterField.addTextChangeListener(event -> {
            container.removeAllContainerFilters();
            container.addContainerFilter(new SimpleStringFilter("name", event.getText()
                    , true, false));

        });

        buttonClearFilter = new Button("X");
        buttonClearFilter.setStyleName(ValoTheme.BUTTON_PRIMARY);
        buttonClearFilter.addClickListener(event -> {
            container.removeAllContainerFilters();
            filterField.clear();
        });

        buttonNewItem = new Button("New Item");
        buttonNewItem.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        buttonNewItem.addClickListener(event -> getUI().getNavigator().navigateTo(OrderGUI.CREATE_ITEM));

        buttonUpdateItem = new Button("Update Item");
        buttonUpdateItem.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        buttonUpdateItem.addClickListener(event -> {
            if (itemSelected != null) {
                getUI().getNavigator().navigateTo(OrderGUI.UPDATE_ITEM + "/" + itemSelected.getId());
            }
        });

        header.addComponents(labelItems, filterField, buttonClearFilter, buttonNewItem, buttonUpdateItem);
        mainLayout.addComponent(header);
    }

    private void renderItems() {
        container = new BeanItemContainer<>(Item.class
                , itemResource.getItems().stream()
                .sorted((item1, item2) -> item1.getName().compareToIgnoreCase(item2.getName()))
                .collect(Collectors.toList()));
        grid.setColumns("name", "description", "price", "amountOfStock");
        grid.setContainerDataSource(container);
        grid.setSizeUndefined();
        grid.setSizeFull();

        //https://github.com/stevendecock/vaadinbooking
        grid.addSelectionListener(event -> {
            itemSelected = (Item) event.getSelected().iterator().next();
        });

        mainLayout.addComponent(grid);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        init();
    }
}
