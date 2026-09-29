package com.epitech.ccleaner.sprites.items;

import com.badlogic.gdx.graphics.Texture;
import com.epitech.ccleaner.screens.GameScreen;
import com.epitech.ccleaner.sprites.cursor.Cursor;


public class Admin extends Item {

  public static final Texture texture = new Texture("items/admin.png");

  public Admin(GameScreen screen, float x, float y) {
    super(screen, texture, x, y);
  }

  @Override
  public void use(Cursor cursor) {
    destroy();
    cursor.setShield(cursor.getShield() + 10);
  }

}
