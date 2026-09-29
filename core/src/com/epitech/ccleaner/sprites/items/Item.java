package com.epitech.ccleaner.sprites.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.epitech.ccleaner.CCleaner;
import com.epitech.ccleaner.screens.GameScreen;
import com.epitech.ccleaner.sprites.cursor.Cursor;


public abstract class Item extends Sprite {

  public static final Texture texture = null;

  private final float width = 60f / CCleaner.PPM;

  private final float height = 60f / CCleaner.PPM;

  public Body b2body;

  protected GameScreen screen;

  protected boolean destroyed;

  protected boolean isClass;

  public Item(GameScreen screen, Texture texture, float x, float y) {
    super(texture);
    this.screen = screen;
    this.isClass = false;

    defineItem(x, y);
    setBounds(0, 0, width, height);
  }

  public Item(GameScreen screen, Texture texture, float x, float y, boolean isClass) {
    super(texture);
    this.screen = screen;
    this.isClass = isClass;

    defineItem(x, y);
    setBounds(0, 0, width, height);
  }

  public boolean isDestroyed() {
    return destroyed;
  }

  public void destroy() {
    destroyed = true;
  }

  public boolean isClass() {
    return isClass;
  }

  public FixtureDef defineItem(float x, float y) {
    BodyDef bdef = new BodyDef();
    bdef.position.set(x / CCleaner.PPM, y / CCleaner.PPM);
    bdef.type = BodyDef.BodyType.StaticBody;
    b2body = screen.getWorld().createBody(bdef);

    FixtureDef fdef = new FixtureDef();
    CircleShape shape = new CircleShape();
    shape.setRadius(30 / CCleaner.PPM);
    fdef.filter.categoryBits = CCleaner.ITEM_BIT;
    fdef.filter.maskBits = CCleaner.CURSOR_BIT;

    fdef.shape = shape;
    fdef.isSensor = true;

    b2body.createFixture(fdef).setUserData(this);

    return fdef;
  }

  public void update() {
    float x = b2body.getPosition().x - getWidth() / 2;
    float y = b2body.getPosition().y - getHeight() / 2;

    setPosition(x, y);
    setOrigin(getWidth() / 2, getHeight() / 2);
  }

  public void use(Cursor cursor) {
    cursor.addItem(this);
    destroy();
  }

}
