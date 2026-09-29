package com.epitech.ccleaner.hud.progressbar;

import com.badlogic.gdx.graphics.Texture;
import com.epitech.ccleaner.CCleaner;
import com.epitech.ccleaner.sprites.cursor.Cursor;
import com.epitech.ccleaner.tools.TextureColor;


public class CooldownBar extends ProgressBar {

  public static final Texture texture = TextureColor.create(0, 0, 0, 0.2f);

  private Cursor cursor;

  public CooldownBar(Cursor cursor) {
    super(texture, 140 / CCleaner.PPM, 5 / CCleaner.PPM, 60 / CCleaner.PPM, 60 / CCleaner.PPM, cursor.getAttackCooldown(), cursor.getTimeSinceLastAttack(), Direction.TOP_TO_BOTTOM, Type.TO_EMPTY);

    this.cursor = cursor;
  }

  @Override
  public void update() {
    currentValue = cursor.getTimeSinceLastAttack();
    maxValue = cursor.getAttackCooldown();
    super.update();
  }

}
