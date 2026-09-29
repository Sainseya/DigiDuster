package com.epitech.ccleaner.sprites.cursor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.epitech.ccleaner.CCleaner;
import com.epitech.ccleaner.screens.GameScreen;
import com.epitech.ccleaner.tools.MapSize;


public class Bullet extends Sprite {

  public Body b2body;

  private double damage;

  private boolean destroyed;

  private int maxPiercing;

  private int piercing;

  private Vector2 origin;

  private float maxDistance;

  private Animation<TextureRegion> animation;

  private float stateTime;

  private boolean destroyToBulletContact;

  public Bullet(Texture texture, World world, float x, float y, float rotation, float size, double damage, int maxPiercing, float maxDistance, boolean destroyToBulletContact) {
    super(texture);
    this.damage = damage;
    this.piercing = 0;
    this.maxPiercing = maxPiercing;
    origin = new Vector2(x, y);
    this.maxDistance = maxDistance;
    this.destroyToBulletContact = destroyToBulletContact;

    defineBullet(world, x, y, size);
    setBounds(0, 0, size / CCleaner.PPM, size / CCleaner.PPM);
    setRotation(rotation);
  }

  public Bullet(World world, float x, float y, float rotation, float size, double damage, int maxPiercing, float maxDistance, boolean destroyToBulletContact) {
    initAnimation();

    stateTime = 0;
    this.damage = damage;
    this.piercing = 0;
    this.maxPiercing = maxPiercing;
    origin = new Vector2(x, y);
    this.maxDistance = maxDistance;
    this.destroyToBulletContact = destroyToBulletContact;

    defineBullet(world, x, y, size);
    setBounds(0, 0, size / CCleaner.PPM, size / CCleaner.PPM);
    setRotation(rotation);

    setRegion(animation.getKeyFrame(0));
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

  public boolean canPiercing() {
    return piercing < maxPiercing;
  }

  public void addPiercing() {
    piercing++;
  }

  public boolean isDestroyToBulletContact() {
    return destroyToBulletContact;
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

  private void defineBullet(World world, float x, float y, float size) {
    BodyDef bdef = new BodyDef();
    bdef.position.set(x, y);
    bdef.type = BodyDef.BodyType.DynamicBody;
    b2body = world.createBody(bdef);

    FixtureDef fdef = new FixtureDef();
    CircleShape shape = new CircleShape();
    shape.setRadius(size / 1.7f / CCleaner.PPM);

    fdef.filter.categoryBits = CCleaner.BULLET_BIT;
    fdef.filter.maskBits = CCleaner.ENEMY_BIT | CCleaner.WORM_BULLET_BIT;
    fdef.isSensor = true;

    fdef.shape = shape;
    b2body.createFixture(fdef).setUserData(this);
  }

  public void update(float delta) {
    if (animation != null) {
      stateTime += delta;
      setRegion(animation.getKeyFrame(stateTime, true));
    }

    setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    setOrigin(getWidth() / 2, getHeight() / 2);
  }

  public boolean isOutOfMaxDistance() {
    return origin.dst(b2body.getPosition()) > maxDistance;
  }

  public boolean isOutOfScreen(GameScreen screen) {
    float bulletX = b2body.getPosition().x;
    float bulletY = b2body.getPosition().y;

    MapSize mapSize = screen.getMapSize();

    return bulletX < mapSize.x || bulletX > mapSize.width || bulletY < mapSize.y || bulletY > mapSize.y + mapSize.height;
  }

}
