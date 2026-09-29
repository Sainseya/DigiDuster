package com.epitech.ccleaner.sprites.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.epitech.ccleaner.CCleaner;
import com.epitech.ccleaner.screens.GameScreen;
import com.epitech.ccleaner.sprites.cursor.Cursor;


public class HealthOrb extends Item {

  public static final Texture texture = new Texture("items/health_orb.png");

  public HealthOrb(GameScreen screen, float x, float y) {
    super(screen, texture, x, y);

    setBounds(getX(), getY(), 24 / CCleaner.PPM, 24 / CCleaner.PPM);
  }

  @Override
  public FixtureDef defineItem(float x, float y) {
    BodyDef bdef = new BodyDef();
    bdef.position.set(x / CCleaner.PPM, y / CCleaner.PPM);
    bdef.type = BodyDef.BodyType.StaticBody;
    b2body = screen.getWorld().createBody(bdef);

    FixtureDef fdef = new FixtureDef();
    CircleShape shape = new CircleShape();
    shape.setRadius(12 / CCleaner.PPM);
    fdef.filter.categoryBits = CCleaner.ITEM_BIT;
    fdef.filter.maskBits = CCleaner.CURSOR_BIT;

    fdef.shape = shape;
    fdef.isSensor = true;

    b2body.createFixture(fdef).setUserData(this);

    return fdef;
  }

  @Override
  public void use(Cursor cursor) {
    destroy();
    cursor.setHealth(cursor.getHealth() + 4);
  }

}
