package com.switchfully.vaadin.ordergui.interfaces.items;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

//copied and adapted code from order-jpa-solution switchfully

@Component
public class ItemResource {

    private RestTemplate restTemplate;

    @Autowired
    public ItemResource(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Item> getItems() {
        Item[] items = restTemplate.getForObject("http://localhost:9000/items", Item[].class);
        return Arrays.asList(items);
    }

    public void createItem(Item item) {
        restTemplate.postForObject("http://localhost:9000/items", item, Item.class);
    }

    public void updateItem(String itemId, Item itemToUpdate) {
        restTemplate
                .exchange(format("http://localhost:9000/items/%s", itemId),
                        HttpMethod.PUT,
                        new HttpEntity<>(itemToUpdate),
                        Item.class);
    }


}
