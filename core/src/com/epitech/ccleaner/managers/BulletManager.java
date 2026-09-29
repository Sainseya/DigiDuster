package com.epitech.ccleaner.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.epitech.ccleaner.screens.GameScreen;
import com.epitech.ccleaner.sprites.cursor.Bullet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class BulletManager {

  private final Texture waveTexture;

  private List<Bullet> bullets;

  private GameScreen screen;

  public BulletManager(GameScreen screen) {
    this.screen = screen;
    bullets = new ArrayList<>();
    waveTexture = new Texture("cursor/wifi_wave.png");
  }

  public void update(float delta) {
    Iterator<Bullet> iterator = bullets.iterator();
    while (iterator.hasNext()) {
      Bullet bullet = iterator.next();
      bullet.update(delta);
      bullet.draw(screen.game.batch);

      if (bullet.isOutOfScreen(screen) || bullet.isOutOfMaxDistance() || bullet.isDestroyed()) {
        screen.getWorld().destroyBody(bullet.b2body);
        iterator.remove();
      }
    }
  }

  public void createBullet(float x, float y, float angle, double damage, int maxPiercing) {
    Bullet bullet = new Bullet(screen.getWorld(), x, y, angle, 32, damage, maxPiercing, screen.getMapSize().width, true);
    addBullet(bullet);

    bullet.b2body.setLinearVelocity(new Vector2(1, 0).setAngleDeg(bullet.getRotation() + 90).scl(5.5f));
  }

  public void createWave(float x, float y, float angle, double damage) {
    Bullet bullet = new Bullet(waveTexture, screen.getWorld(), x, y, angle, 256, damage, 999, 7, false);
    addBullet(bullet);

    bullet.b2body.setLinearVelocity(new Vector2(1, 0).setAngleDeg(bullet.getRotation() + 90).scl(3f));
  }

  public void addBullet(Bullet bullet) {
    bullets.add(bullet);
  }

  public void removeBullet(Bullet bullet) {
    bullets.remove(bullet);
  }

}
