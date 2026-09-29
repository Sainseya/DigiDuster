package com.epitech.ccleaner.sprites.levels.zones;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.epitech.ccleaner.screens.GameScreen;
import com.epitech.ccleaner.sprites.cursor.Cursor;
import com.epitech.ccleaner.sprites.enemies.Scarab;
import com.epitech.ccleaner.sprites.enemies.Spider;
import com.epitech.ccleaner.sprites.enemies.Worm;
import com.epitech.ccleaner.sprites.items.*;


public class RandomRoom extends LevelZone {

  private static final int MIN_ENEMIES = 5;

  private static final int MAX_ENEMIES = 10;

  private static final int MIN_ITEMS = 3;

  private static final int MAX_ITEMS = 7;

  public RandomRoom(GameScreen screen, Cursor cursor) {
    super(screen, cursor);
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
  public void generateEnemies() {
    int enemiesCount = MathUtils.random(MIN_ENEMIES, MAX_ENEMIES);

    for (int i = 0; i < enemiesCount; i++) {
      int enemyIndex = (int) (Math.random() * 3);
      Vector2 position = generateRandomPosition();

      switch (enemyIndex) {
        case 0:
          screen.getEnemyManager().createEnemy(() -> new Worm(screen, cursor, position.x, position.y));
          break;
        case 1:
          screen.getEnemyManager().createEnemy(() -> new Spider(screen, cursor, position.x, position.y));
          break;
        case 2:
          screen.getEnemyManager().createEnemy(() -> new Scarab(screen, cursor, position.x, position.y));
          break;
      }
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
  public void onEnd() {
    super.onEnd();
    generateItem();
  }

}
