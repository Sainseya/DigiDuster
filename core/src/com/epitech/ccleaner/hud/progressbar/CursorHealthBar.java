package com.epitech.ccleaner.hud.progressbar;

import com.badlogic.gdx.graphics.Texture;
import com.epitech.ccleaner.CCleaner;
import com.epitech.ccleaner.sprites.cursor.Cursor;
import com.epitech.ccleaner.tools.TextureColor;


public class CursorHealthBar extends ProgressBar {

  public static final Texture texture = TextureColor.create(0, 1, 0, 1);

  private Cursor cursor;

  public CursorHealthBar(Cursor cursor) {
    super(texture, (CCleaner.WIDTH - 160f) / CCleaner.PPM, 4f / CCleaner.PPM, 140f / CCleaner.PPM, 30f / CCleaner.PPM, 100, 100, Direction.RIGHT_TO_LEFT, Type.TO_FILL);

    this.cursor = cursor;
  }

  public void update() {
    currentValue = cursor.getHealth();
    super.update();
  }

}
