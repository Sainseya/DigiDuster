package com.epitech.ccleaner.managers;

import com.epitech.ccleaner.screens.GameScreen;
import com.epitech.ccleaner.sprites.enemies.Enemy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;


public class EnemyManager {

  public WormBulletManager wormBulletManager;

  private GameScreen screen;

  private List<Enemy> enemies;

  public EnemyManager(GameScreen screen) {
    this.screen = screen;
    enemies = new ArrayList<>();
    wormBulletManager = new WormBulletManager(screen);
  }

  public void update(float delta) {
    Iterator<Enemy> iterator = enemies.iterator();
    while (iterator.hasNext()) {
      Enemy enemy = iterator.next();
      enemy.update(delta);
      enemy.draw(screen.game.batch);

      if (enemy.isDestroyed()) {
        enemy.onDie();
        screen.getWorld().destroyBody(enemy.b2body);
        iterator.remove();
      }
    }

    wormBulletManager.update(delta);
  }

  public <T extends Enemy> void createEnemy(Supplier<T> enemySupplier) {
    T enemy = enemySupplier.get();
    enemies.add(enemy);
  }

  public List<Enemy> getEnemies() {
    return enemies;
  }

}
