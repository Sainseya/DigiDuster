package com.epitech.ccleaner.sprites.items;

import com.badlogic.gdx.graphics.Texture;
import com.epitech.ccleaner.screens.GameScreen;
import com.epitech.ccleaner.sprites.cursor.Cursor;


public class Scissors extends Item {

  public static final Texture texture = new Texture("items/scissors.png");

  public Scissors(GameScreen screen, float x, float y) {
    super(screen, texture, x, y);
  }

  @Override
  public void use(Cursor cursor) {
    super.use(cursor);
    double damageMultiplier = cursor.getDamageMultiplier();
    cursor.setDamageMultiplier(damageMultiplier + (damageMultiplier * 0.1f));
  }

}
