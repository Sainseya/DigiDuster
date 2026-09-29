package com.epitech.ccleaner.sprites.cursor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.epitech.ccleaner.CCleaner;


public class Shield extends Sprite {

  public static final Texture texture = new Texture("cursor/shield.png");

  private Cursor cursor;

  public Shield(Cursor cursor) {
    super(texture);

    this.cursor = cursor;
  }

  public void update() {
    float with = cursor.getWidth() + 10f / CCleaner.PPM;
    float height = cursor.getHeight() + 10f / CCleaner.PPM;

    float x = cursor.getX() - 5f / CCleaner.PPM;
    float y = cursor.getY() - 5f / CCleaner.PPM;

    setBounds(x, y, with, height);
    setOrigin(getWidth() / 2, getHeight() / 2);
  }

}
