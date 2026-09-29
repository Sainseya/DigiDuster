package com.epitech.ccleaner.sprites.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.epitech.ccleaner.managers.WormBulletManager;
import com.epitech.ccleaner.screens.GameScreen;
import com.epitech.ccleaner.sprites.cursor.Cursor;


public class Worm extends Enemy {

  public static Texture texture = new Texture("enemies/worm.png");

  private WormBulletManager bulletManager;

  private float shotCooldown;

  /**
   * Crée un ennemi (Constructor)
   *
   * @param screen L'écran du jeu
   * @param cursor Le curseur que l'ennemi doit suivre et attaquer
   * @param x      La position X de l'ennemi
   * @param y      La position Y de l'ennemi
   */
  public Worm(GameScreen screen, Cursor cursor, float x, float y) {
    super(screen, cursor, texture, x, y, 28, 54, 54, 75, 15, BodyDef.BodyType.StaticBody);

    shotCooldown = MathUtils.random(0.5f, 3f);

    this.bulletManager = screen.getEnemyManager().wormBulletManager;
  }

  /**
   * Met à jour l'ennemi à chaque frame
   *
   * @param delta Le temps écoulé depuis la dernière frame
   */
  @Override
  public void update(float delta) {
    super.update(delta);

    if (timeSinceLastAttack >= shotCooldown) {
      attack();
      timeSinceLastAttack = 0;
      shotCooldown = MathUtils.random(0.5f, 3f);
    }
  }

  @Override
  public void attack() {
    Vector2 wormPosition = b2body.getPosition();
    Vector2 cursorPosition = cursor.b2body.getPosition();

    Vector2 direction = new Vector2(cursorPosition.x - wormPosition.x, cursorPosition.y - wormPosition.y);

    float angleRad = MathUtils.atan2(direction.y, direction.x);
    float angleDeg = MathUtils.radiansToDegrees * angleRad;

    bulletManager.createBullet(wormPosition.x, wormPosition.y, angleDeg, getDamage());
  }

}
