package com.switchfully.vaadin.ordergui.webapp;

import com.switchfully.vaadin.ordergui.interfaces.items.ItemResource;
import com.switchfully.vaadin.ordergui.webapp.views.ItemCreateView;
import com.switchfully.vaadin.ordergui.webapp.views.ItemOverviewView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

// copied and adapted code from https://github.com/stevendecock/vaadinbooking
// and switchfully repositories

@SpringUI
@Theme("valo")
public class OrderGUI extends UI {

    private ItemResource itemResource;
    public static final String VIEW_ITEM_OVERVIEW = "items/overview";
    public static final String CREATE_ITEM = "items/create";
    private Navigator navigator;

    @Autowired
    public OrderGUI(ItemResource itemResource) {
        this.itemResource = itemResource;
    }

    @Override
    protected void init(VaadinRequest request) {
        navigator = new Navigator(this,this);
        navigator.addView(VIEW_ITEM_OVERVIEW, new ItemOverviewView(itemResource));
        navigator.addView(CREATE_ITEM, new ItemCreateView(itemResource));
    }
}