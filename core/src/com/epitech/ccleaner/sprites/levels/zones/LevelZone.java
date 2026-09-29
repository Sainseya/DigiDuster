package com.epitech.ccleaner.sprites.levels.zones;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.epitech.ccleaner.CCleaner;
import com.epitech.ccleaner.screens.GameScreen;
import com.epitech.ccleaner.sprites.cursor.Cursor;
import com.epitech.ccleaner.tools.MapSize;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public abstract class LevelZone extends Sprite {

  public MapSize mapSize;

  protected GameScreen screen;

  protected Cursor cursor;

  private List<Body> bodies;

  private boolean isEnd;

  public LevelZone(GameScreen screen, Cursor cursor, Texture texture) {
    super(texture);
    this.screen = screen;
    this.cursor = cursor;

    isEnd = false;

    bodies = new ArrayList<>();

    mapSize = new MapSize(1500, 800, 200, 150);
  }

  public LevelZone(GameScreen screen, Cursor cursor) {
    super(getRandomTexture());
    this.screen = screen;
    this.cursor = cursor;

    isEnd = false;

    bodies = new ArrayList<>();

    mapSize = new MapSize(1500, 800, 200, 150);
  }

  public static Texture getRandomTexture() {
    int random = (int) (Math.random() * 3);
    switch (random) {
      case 1:
        return new Texture("levels/zones/folder.png");
      case 2:
        return new Texture("levels/zones/pc.png");
      case 3:
        return new Texture("levels/zones/trash.png");
      default:
        return new Texture("levels/zones/empty.png");
    }
  }

  public boolean isEnd() {
    return isEnd;
  }

  private void createWall(float x, float y, float width, float height) {
    BodyDef bdef = new BodyDef();
    bdef.type = BodyDef.BodyType.StaticBody;
    bdef.position.set((x + width) / CCleaner.PPM, (y + height) / CCleaner.PPM);

    Body b2body = screen.getWorld().createBody(bdef);
    bodies.add(b2body);

    PolygonShape shape = new PolygonShape();
    shape.setAsBox(width / CCleaner.PPM, height / CCleaner.PPM);

    FixtureDef fdef = new FixtureDef();
    fdef.shape = shape;

    fdef.filter.categoryBits = CCleaner.WALL_BIT;
    fdef.filter.maskBits = CCleaner.CURSOR_BIT | CCleaner.ENEMY_BIT;

    b2body.createFixture(fdef);

    shape.dispose();
  }

  private void defineWalls() {
    createWall(200, 150, 750, 0.1f);
    createWall(200, 150, 0.1f, 400);
    createWall(200, 950, 750, 0.1f);
    createWall(1700, 150, 0.1f, 400);
  }

  public Vector2 generateRandomPosition() {
    float x = MathUtils.random(mapSize.x + 30, mapSize.x + mapSize.width - 30);
    float y = MathUtils.random(mapSize.y + 30, mapSize.y + mapSize.height - 30);
    return new Vector2(x, y);
  }

  public void update() {
    if (screen.getEnemyManager().getEnemies().isEmpty() && !isEnd) {
      onEnd();
    }
  }

  public abstract void generateEnemies();

  public abstract void generateItems();

  public void onEnd() {
    isEnd = true;
  }

  public void show(Cursor cursor) {
    defineWalls();
    setBounds(200 / CCleaner.PPM, 150 / CCleaner.PPM, 1500 / CCleaner.PPM, 800 / CCleaner.PPM);

    cursor.b2body.setTransform((float) CCleaner.WIDTH / 2 / CCleaner.PPM, (float) CCleaner.HEIGHT / 2 / CCleaner.PPM, 0);

    generateEnemies();
    generateItems();
  }

  public void close() {
    Iterator<Body> iterator = bodies.iterator();
    while (iterator.hasNext()) {
      Body body = iterator.next();

      screen.getWorld().destroyBody(body);
      iterator.remove();
    }

    screen.getItemManager().destroyAll();

    setBounds(0, 0, 0, 0);
  }

}
