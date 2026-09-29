package com.epitech.ccleaner.tools;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;


public class TextureColor {

  public static Texture create(float r, float g, float b, float a) {
    Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
    pixmap.setColor(r, g, b, a);
    pixmap.fill();

    Texture texture = new Texture(pixmap);
    pixmap.dispose();

    return texture;
  }

}
