package com.epitech.ccleaner.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.epitech.ccleaner.screens.GameScreen;
import com.epitech.ccleaner.sprites.enemies.WormBullet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class WormBulletManager {

  private final Texture texture;

  private List<WormBullet> bullets;

  private GameScreen screen;

  public WormBulletManager(GameScreen screen) {
    this.screen = screen;
    bullets = new ArrayList<>();
    texture = new Texture("cursor/bullet.png");
  }

  public void update(float delta) {
    Iterator<WormBullet> iterator = bullets.iterator();
    while (iterator.hasNext()) {
      WormBullet bullet = iterator.next();
      bullet.update(delta);
      bullet.draw(screen.game.batch);

      if (bullet.isOutOfScreen(screen) || bullet.isDestroyed()) {
        screen.getWorld().destroyBody(bullet.b2body);
        iterator.remove();
      }
    }
  }

  public void createBullet(float x, float y, float angle, double damage) {
    WormBullet bullet = new WormBullet(texture, screen.getWorld(), x, y, angle, damage);
    addBullet(bullet);

    bullet.b2body.setLinearVelocity(new Vector2(1, 0).setAngleDeg(bullet.getRotation()).scl(3f));
  }

  public void addBullet(WormBullet bullet) {
    bullets.add(bullet);
  }

}
