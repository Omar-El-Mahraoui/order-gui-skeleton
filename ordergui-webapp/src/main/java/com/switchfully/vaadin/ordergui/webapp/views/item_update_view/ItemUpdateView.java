package com.switchfully.vaadin.ordergui.webapp.views.item_update_view;

import com.switchfully.vaadin.ordergui.interfaces.items.Item;
import com.switchfully.vaadin.ordergui.interfaces.items.ItemResource;
import com.switchfully.vaadin.ordergui.webapp.OrderGUI;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

// copied and adapted code from https://github.com/stevendecock/vaadinbooking
// and switchfully repositories
// and book of vaadin

public class ItemUpdateView extends CustomComponent implements View {

    private VerticalLayout mainLayout = new VerticalLayout();
    private ItemResource itemResource;
    private Item itemToUpdate;
    private UpdateItemForm form;

    @Autowired
    public ItemUpdateView(ItemResource itemResource) {
        this.itemResource = itemResource;
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
        form = new UpdateItemForm(itemToUpdate);
        mainLayout.addComponents(form);
        addUpdateAndCancelButtons();
        setCompositionRoot(mainLayout);
    }

    private void addUpdateAndCancelButtons() {
        HorizontalLayout horizontalLayoutCreateAndCancelButtons = new HorizontalLayout();
        horizontalLayoutCreateAndCancelButtons.setSpacing(true);
        horizontalLayoutCreateAndCancelButtons.setMargin(true);

        Button buttonUpdate = new Button("Update");
        buttonUpdate.setStyleName(ValoTheme.BUTTON_PRIMARY);
        buttonUpdate.addClickListener(event -> {
            update();
            getUI().getNavigator().navigateTo(OrderGUI.VIEW_ITEM_OVERVIEW);
            form.getAllTextFields().forEach(textField -> textField.clear());
        });

        Button buttonCancel = new Button("Cancel");
        buttonCancel.addClickListener(event -> {
            getUI().getNavigator().navigateTo(OrderGUI.VIEW_ITEM_OVERVIEW);
        });

        horizontalLayoutCreateAndCancelButtons.addComponents(buttonUpdate, buttonCancel);
        mainLayout.addComponents(horizontalLayoutCreateAndCancelButtons);
    }

    //https://github.com/stevendecock/vaadinbooking
    private void update() {
        try {
            form.getBinder().commit();
            itemResource.updateItem(itemToUpdate.getId(), itemToUpdate);
        } catch (FieldGroup.CommitException e) {
            // https://coderwall.com/p/im4lja/joining-objects-into-a-string-with-java-8-stream-api
            Notification.show("Cannot update item: \n- "
                            + e.getInvalidFields().values().stream()
                            //from colleague
                            .filter(e1 -> !e1.getMessage().isEmpty())
                            .map(e1 -> e1.getMessage())
                            .collect(Collectors.joining("\n- "))
                    , Notification.Type.ERROR_MESSAGE);
        }
    }

    //https://github.com/stevendecock/vaadinbooking
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        String itemId = event.getParameters();
        init(itemId);
    }
}
