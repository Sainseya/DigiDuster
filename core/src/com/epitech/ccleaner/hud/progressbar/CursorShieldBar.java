package com.epitech.ccleaner.hud.progressbar;

import com.badlogic.gdx.graphics.Texture;
import com.epitech.ccleaner.CCleaner;
import com.epitech.ccleaner.sprites.cursor.Cursor;
import com.epitech.ccleaner.tools.TextureColor;


public class CursorShieldBar extends ProgressBar {

  public static final Texture texture = TextureColor.create(0, 0, 1, 1);

  private Cursor cursor;

  public CursorShieldBar(Cursor cursor) {
    super(texture, (CCleaner.WIDTH - 160f) / CCleaner.PPM, 36f / CCleaner.PPM, 140f / CCleaner.PPM, 30f / CCleaner.PPM, 100, cursor.getShield(), Direction.RIGHT_TO_LEFT, Type.TO_FILL);

    this.cursor = cursor;
  }

  public void update() {
    currentValue = cursor.getShield();
    super.update();
  }

}
