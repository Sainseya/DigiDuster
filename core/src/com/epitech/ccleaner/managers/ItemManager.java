package com.epitech.ccleaner.managers;

import com.epitech.ccleaner.screens.GameScreen;
import com.epitech.ccleaner.sprites.items.Item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;


public class ItemManager {

  private GameScreen screen;

  private List<Item> items;

  public ItemManager(GameScreen screen) {
    this.screen = screen;
    items = new ArrayList<>();
  }

  public void update() {
    Iterator<Item> iterator = items.iterator();
    while (iterator.hasNext()) {
      Item item = iterator.next();
      item.update();
      item.draw(screen.game.batch);

      if (item.isDestroyed()) {
        screen.getWorld().destroyBody(item.b2body);
        iterator.remove();
      }
    }
  }

  public <T extends Item> void createItem(Supplier<T> itemSupplier) {
    T item = itemSupplier.get();
    items.add(item);
  }

  public void destroyAllClassItems() {
    for (Item item : items) {
      if (item.isClass()) {
        item.destroy();
      }
    }
  }

  public void destroyAll() {
    for (Item item : items) {
      item.destroy();
    }
  }

}
