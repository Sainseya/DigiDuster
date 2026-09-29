package com.epitech.ccleaner.sprites.items;

import com.badlogic.gdx.graphics.Texture;
import com.epitech.ccleaner.screens.GameScreen;
import com.epitech.ccleaner.sprites.cursor.Cursor;


public class Star extends Item {

  public static final Texture texture = new Texture("items/star.png");

  public Star(GameScreen screen, float x, float y) {
    super(screen, texture, x, y);
  }

  @Override
  public void use(Cursor cursor) {
    super.use(cursor);
    double speed = cursor.getSpeed();
    cursor.setSpeed(speed + (speed * 0.05f));
  }

}
