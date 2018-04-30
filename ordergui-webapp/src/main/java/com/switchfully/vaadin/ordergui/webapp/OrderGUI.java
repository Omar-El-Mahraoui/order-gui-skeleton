package com.switchfully.vaadin.ordergui.webapp;

import com.switchfully.vaadin.ordergui.interfaces.items.ItemResource;
import com.switchfully.vaadin.ordergui.webapp.views.item_create_view.ItemCreateView;
import com.switchfully.vaadin.ordergui.webapp.views.item_overview_view.ItemOverviewView;
import com.switchfully.vaadin.ordergui.webapp.views.item_update_view.ItemUpdateView;
import com.switchfully.vaadin.ordergui.webapp.views.login_view.ItemLoginView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

// copied and adapted code from https://github.com/stevendecock/vaadinbooking
// and switchfully repositories
// and book of vaadin

@SpringUI
@Theme("valo")
public class OrderGUI extends UI {

    private ItemResource itemResource;
    public static final String VIEW_LOGIN = "items/login";
    public static final String VIEW_ITEM_OVERVIEW = "items/overview";
    public static final String CREATE_ITEM = "items/create";
    public static final String UPDATE_ITEM = "items/update";
    private Navigator navigator;

    @Autowired
    public OrderGUI(ItemResource itemResource) {
        this.itemResource = itemResource;
    }

    @Override
    protected void init(VaadinRequest request) {
        navigator = new Navigator(this,this);
        navigator.addView(VIEW_LOGIN, new ItemLoginView());
        navigator.addView(VIEW_ITEM_OVERVIEW, new ItemOverviewView(itemResource));
        navigator.addView(CREATE_ITEM, new ItemCreateView(itemResource));
        navigator.addView(UPDATE_ITEM, new ItemUpdateView(itemResource));
    }
}