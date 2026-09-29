package com.epitech.ccleaner.sprites.levels.zones;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.epitech.ccleaner.CCleaner;
import com.epitech.ccleaner.screens.GameScreen;
import com.epitech.ccleaner.sprites.cursor.Cursor;
import com.epitech.ccleaner.sprites.enemies.Boss;
import com.epitech.ccleaner.sprites.items.*;


public class BossZone extends LevelZone {

  private static final int MIN_ITEMS = 3;

  private static final int MAX_ITEMS = 7;

  public BossZone(GameScreen screen, Cursor cursor) {
    super(screen, cursor);
  }

  @Override
  public void generateEnemies() {
    float x = (float) CCleaner.WIDTH / 2;
    float y = (float) CCleaner.HEIGHT / 2;

    screen.getEnemyManager().createEnemy(() -> new Boss(screen, cursor, x, y));
  }

  private void generateItem() {
    int itemIndex = (int) (Math.random() * 5);
    Vector2 position = generateRandomPosition();

    switch (itemIndex) {
      case 0:
        screen.getItemManager().createItem(() -> new Scissors(screen, position.x, position.y));
        break;
      case 1:
        screen.getItemManager().createItem(() -> new Pin(screen, position.x, position.y));
        break;
      case 2:
        screen.getItemManager().createItem(() -> new Admin(screen, position.x, position.y));
        break;
      case 3:
        screen.getItemManager().createItem(() -> new Cloud(screen, position.x, position.y));
        break;
      case 4:
        screen.getItemManager().createItem(() -> new Star(screen, position.x, position.y));
        break;
    }
  }

  @Override
  public void generateItems() {
    int itemsCount = MathUtils.random(MIN_ITEMS, MAX_ITEMS);

    for (int i = 0; i < itemsCount; i++) {
      generateItem();
    }
  }

  @Override
  public void show(Cursor cursor) {
    super.show(cursor);

    cursor.b2body.setTransform((float) CCleaner.WIDTH / 3 / CCleaner.PPM, (float) CCleaner.HEIGHT / 2 / CCleaner.PPM, 0);
  }

}
