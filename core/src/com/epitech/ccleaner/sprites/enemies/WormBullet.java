package com.epitech.ccleaner.sprites.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.epitech.ccleaner.CCleaner;
import com.epitech.ccleaner.screens.GameScreen;
import com.epitech.ccleaner.tools.MapSize;


public class WormBullet extends Sprite {

  public Body b2body;

  private double damage;

  private boolean destroyed;

  private Animation<TextureRegion> animation;

  private float stateTime;

  public WormBullet(Texture texture, World world, float x, float y, float rotation, double damage) {
    super(texture);
    initAnimation();

    stateTime = 0;
    this.damage = damage;

    defineBullet(world, x, y);
    setBounds(0, 0, 32 / CCleaner.PPM, 32 / CCleaner.PPM);
    setRotation(rotation);
  }

  public double getDamage() {
    return damage;
  }

  public boolean isDestroyed() {
    return destroyed;
  }

  public void destroy() {
    destroyed = true;
  }

  private void initAnimation() {
    Texture texture = new Texture("cursor/bullet.png");
    Array<TextureRegion> frames = new Array<>();

    for (int y = 0; y < 6; y++) {
      if (y == 5)
        frames.add(new TextureRegion(texture, 0, y * 75, 75, 75));
      else {
        for (int x = 0; x < 5; x++) {
          frames.add(new TextureRegion(texture, x * 75, y * 75, 75, 75));
        }
      }
    }
    animation = new Animation<>(0.1f, frames);
    frames.clear();
  }

  private void defineBullet(World world, float x, float y) {
    BodyDef bdef = new BodyDef();
    bdef.position.set(x, y);
    bdef.type = BodyDef.BodyType.DynamicBody;
    b2body = world.createBody(bdef);

    FixtureDef fdef = new FixtureDef();
    CircleShape shape = new CircleShape();
    shape.setRadius(32 / 1.7f / CCleaner.PPM);

    fdef.filter.categoryBits = CCleaner.WORM_BULLET_BIT;
    fdef.filter.maskBits = CCleaner.CURSOR_BIT | CCleaner.BULLET_BIT;
    fdef.isSensor = true;

    fdef.shape = shape;
    b2body.createFixture(fdef).setUserData(this);
  }

  public void update(float delta) {
    stateTime += delta;
    setRegion(animation.getKeyFrame(stateTime, true));

    setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    setOriginCenter();
  }

  public boolean isOutOfScreen(GameScreen screen) {
    float bulletX = b2body.getPosition().x;
    float bulletY = b2body.getPosition().y;

    MapSize mapSize = screen.getMapSize();

    return bulletX < mapSize.x || bulletX > mapSize.width || bulletY < mapSize.y || bulletY > mapSize.y + mapSize.height;
  }

}
