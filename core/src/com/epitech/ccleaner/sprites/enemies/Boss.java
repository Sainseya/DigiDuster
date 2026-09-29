package com.epitech.ccleaner.sprites.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.epitech.ccleaner.managers.WormBulletManager;
import com.epitech.ccleaner.screens.GameScreen;
import com.epitech.ccleaner.sprites.cursor.Cursor;


public class Boss extends Enemy {

  public static Texture texture = new Texture("boss/DDOS.png");

  private WormBulletManager bulletManager;

  public Boss(GameScreen screen, Cursor cursor, float x, float y) {
    super(screen, cursor, texture, x, y, 100, 206 / 1.2f, 254 / 1.2f, 1000, 30, BodyDef.BodyType.StaticBody);

    this.bulletManager = screen.getEnemyManager().wormBulletManager;
  }

  @Override
  public void update(float delta) {
    super.update(delta);

    if (timeSinceLastAttack >= 0.3f) {
      attack();
      timeSinceLastAttack = 0;
    }
  }

  @Override
  public void attack() {
    Vector2 position = b2body.getPosition();

    float angleDeg = (float) (Math.random() * 360);

    bulletManager.createBullet(position.x, position.y, angleDeg, getDamage());
  }

}
