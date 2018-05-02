package com.switchfully.vaadin.ordergui.webapp.views.item_create_view;

import com.switchfully.vaadin.ordergui.interfaces.items.Item;
import com.switchfully.vaadin.ordergui.interfaces.items.ItemResource;
import com.switchfully.vaadin.ordergui.webapp.OrderGUI;
import com.switchfully.vaadin.ordergui.webapp.views.forms.ItemEditForm;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

// copied and adapted code from https://github.com/stevendecock/vaadinbooking
// and switchfully repositories
// and book of vaadin

public class ItemCreateView extends ItemEditForm implements View {

    private Item itemToCreate = new Item();
    private ItemResource itemResource;
    private BeanFieldGroup<Item> itemBeanFieldGroup;

    @Autowired
    public ItemCreateView(ItemResource itemResource) {
        super();
        this.itemResource = itemResource;
        itemBeanFieldGroup = BeanFieldGroup.bindFieldsBuffered(itemToCreate, this);
    }

    @Override
    protected void init() {
        super.init();

        addCreateAndCancelButtons();
    }

    private void addCreateAndCancelButtons() {
        HorizontalLayout horizontalLayoutCreateAndCancelButtons = new HorizontalLayout();
        horizontalLayoutCreateAndCancelButtons.setSpacing(true);
        horizontalLayoutCreateAndCancelButtons.setMargin(true);

        Button buttonCreate = new Button("Create");
        buttonCreate.setStyleName(ValoTheme.BUTTON_PRIMARY);
        buttonCreate.addClickListener(event -> save());

        Button buttonCancel = new Button("Cancel");
        buttonCancel.addClickListener(event -> {
            getUI().getNavigator().navigateTo(OrderGUI.VIEW_ITEM_OVERVIEW);
            super.getAllTextFields().forEach(textField -> textField.clear());
        });


        horizontalLayoutCreateAndCancelButtons.addComponents(buttonCreate, buttonCancel);

        super.getMainLayout().addComponents(horizontalLayoutCreateAndCancelButtons);
    }

    //https://github.com/stevendecock/vaadinbooking
    private void save() {
        try {
            itemBeanFieldGroup.commit();
            itemResource.createItem(itemToCreate);
            getUI().getNavigator().navigateTo(OrderGUI.VIEW_ITEM_OVERVIEW);
        } catch (FieldGroup.CommitException e) {
            // https://coderwall.com/p/im4lja/joining-objects-into-a-string-with-java-8-stream-api
            Notification.show("Cannot create item: \n- "
                            + e.getInvalidFields().values().stream()
                            //from colleague
                            .filter(e1 -> !e1.getMessage().isEmpty())
                            .map(e1 -> e1.getMessage())
                            .collect(Collectors.joining("\n- "))
                    , Notification.Type.ERROR_MESSAGE);
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        init();
    }
}
