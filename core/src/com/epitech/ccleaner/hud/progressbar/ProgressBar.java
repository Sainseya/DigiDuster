package com.epitech.ccleaner.hud.progressbar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;


public abstract class ProgressBar extends Sprite {

  public enum Direction {
    LEFT_TO_RIGHT,
    RIGHT_TO_LEFT,
    TOP_TO_BOTTOM,
    BOTTOM_TO_TOP
  }

  public enum Type {
    TO_EMPTY,
    TO_FILL
  }

  protected float width;

  protected float height;

  protected float x;

  protected float y;

  protected double maxValue;

  protected double currentValue;

  protected Direction direction;

  protected Type type;

  protected ProgressBar(Texture texture, float x, float y, float width, float height, double maxValue, double currentValue, Direction direction, Type type) {
    super(texture);

    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.maxValue = maxValue;
    this.currentValue = currentValue;
    this.direction = direction;
    this.type = type;
  }

  public void update() {
    double valuePercentage;

    if (type == Type.TO_FILL) {
      valuePercentage = MathUtils.clamp(currentValue / maxValue, 0, 1);
    } else {
      valuePercentage = 1 - MathUtils.clamp(currentValue / maxValue, 0, 1);
    }

    switch (direction) {
      case LEFT_TO_RIGHT:
        setBounds(x, y, width * (float) valuePercentage, height);
        break;
      case RIGHT_TO_LEFT:
        setBounds(x + width - (width * (float) valuePercentage), y, width * (float) valuePercentage, height);
        break;
      case TOP_TO_BOTTOM:
        setBounds(x, y, width, height * (float) valuePercentage);
        break;
      case BOTTOM_TO_TOP:
        setBounds(x, y + height - (height * (float) valuePercentage), width, height * (float) valuePercentage);
        break;
    }

    setOriginCenter();
  }

}
