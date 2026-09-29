package com.epitech.ccleaner.sprites.levels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.epitech.ccleaner.CCleaner;
import com.epitech.ccleaner.sprites.cursor.Cursor;
import com.epitech.ccleaner.sprites.levels.zones.LevelZone;


public class LevelFolder extends Sprite {

  public Body b2body;

  private World world;

  private LevelZone zone;

  public LevelFolder(World world, Texture texture, LevelZone zone, float x, float y) {
    super(texture);
    this.world = world;
    this.zone = zone;

    defineBody(x, y);
  }

  public LevelFolder(World world, LevelZone zone, float x, float y) {
    super(getRandomTexture());
    this.world = world;
    this.zone = zone;

    defineBody(x, y);
  }

  public static Texture getRandomTexture() {
    int random = (int) (Math.random() * 3);
    switch (random) {
      case 1:
        return new Texture("levels/folder/folder.png");
      case 2:
        return new Texture("levels/folder/images.png");
      case 3:
        return new Texture("levels/folder/music.png");
      default:
        return new Texture("levels/folder/document.png");
    }
  }

  public LevelZone getZone() {
    return zone;
  }

  public void defineBody(float x, float y) {
    BodyDef bdef = new BodyDef();
    bdef.position.set(x / CCleaner.PPM, y / CCleaner.PPM);
    bdef.type = BodyDef.BodyType.StaticBody;
    b2body = world.createBody(bdef);

    FixtureDef fdef = new FixtureDef();

    PolygonShape shape = new PolygonShape();
    shape.setAsBox(70 / CCleaner.PPM, 70 / CCleaner.PPM);

    fdef.filter.categoryBits = CCleaner.LEVEL_FOLDER_BIT;
    fdef.filter.maskBits = CCleaner.CURSOR_BIT;

    fdef.shape = shape;
    fdef.isSensor = true;
    b2body.createFixture(fdef).setUserData(this);

    setBounds(x, y, 150 / CCleaner.PPM, 150 / CCleaner.PPM);
    setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
  }

  public void showLevel(Cursor cursor) {
    setColor(new Color(0.5f, 0.5f, 1, 1));
    zone.show(cursor);
  }

  public void closeLevel() {
    setColor(new Color(0.5f, 1, 0.5f, 1));
    zone.close();
  }

}
