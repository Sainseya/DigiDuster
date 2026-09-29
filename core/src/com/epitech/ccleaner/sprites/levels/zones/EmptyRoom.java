package com.epitech.ccleaner.sprites.levels.zones;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.epitech.ccleaner.screens.GameScreen;
import com.epitech.ccleaner.sprites.cursor.Cursor;
import com.epitech.ccleaner.sprites.enemies.Scarab;
import com.epitech.ccleaner.sprites.enemies.Spider;
import com.epitech.ccleaner.sprites.enemies.Worm;
import com.epitech.ccleaner.sprites.items.Pin;
import com.epitech.ccleaner.sprites.items.Scissors;


public class EmptyRoom extends LevelZone {

  public EmptyRoom(GameScreen screen, Cursor cursor) {
    super(screen, cursor, new Texture("levels/zones/empty.png"));
  }

  @Override
  public void generateEnemies() {
    for (int i = 0; i < 2; i++) {
      Vector2 position = generateRandomPosition();
      screen.getEnemyManager().createEnemy(() -> new Worm(screen, cursor, position.x, position.y));
    }
    for (int i = 0; i < 3; i++) {
      Vector2 position = generateRandomPosition();
      screen.getEnemyManager().createEnemy(() -> new Spider(screen, cursor, position.x, position.y));
    }
    for (int i = 0; i < 2; i++) {
      Vector2 position = generateRandomPosition();
      screen.getEnemyManager().createEnemy(() -> new Scarab(screen, cursor, position.x, position.y));
    }
  }

  @Override
  public void generateItems() {
    for (int i = 0; i < 5; i++) {
      Vector2 position = generateRandomPosition();
      screen.getItemManager().createItem(() -> new Scissors(screen, position.x, position.y));
    }
  }

  @Override
  public void onEnd() {
    super.onEnd();

    Vector2 position = generateRandomPosition();
    screen.getItemManager().createItem(() -> new Pin(screen, position.x, position.y));
  }

}
