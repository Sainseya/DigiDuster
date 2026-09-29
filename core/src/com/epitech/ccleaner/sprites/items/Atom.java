package com.epitech.ccleaner.sprites.items;

import com.badlogic.gdx.graphics.Texture;
import com.epitech.ccleaner.screens.GameScreen;
import com.epitech.ccleaner.sprites.cursor.AttackType;
import com.epitech.ccleaner.sprites.cursor.Cursor;


public class Atom extends Item {

  public static final Texture texture = new Texture("items/atom.png");

  public Atom(GameScreen screen, float x, float y) {
    super(screen, texture, x, y, true);
  }

  @Override
  public void use(Cursor cursor) {
    cursor.setAttackType(AttackType.TRIPLE_SHOT);
    screen.getItemManager().destroyAllClassItems();
  }

}
