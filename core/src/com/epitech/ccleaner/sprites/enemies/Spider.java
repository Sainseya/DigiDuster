package com.epitech.ccleaner.sprites.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.epitech.ccleaner.CCleaner;
import com.epitech.ccleaner.screens.GameScreen;
import com.epitech.ccleaner.sprites.cursor.Cursor;


public class Spider extends Enemy {

  public static Texture texture = new Texture("enemies/spider.png");

  /**
   * Crée un ennemi (Constructor)
   *
   * @param screen L'écran du jeu
   * @param cursor Le curseur que l'ennemi doit suivre et attaquer
   * @param x      La position X de l'ennemi
   * @param y      La position Y de l'ennemi
   */
  public Spider(GameScreen screen, Cursor cursor, float x, float y) {
    super(screen, cursor, texture, x, y, 28, 54, 54, 100, 20, BodyDef.BodyType.DynamicBody);
  }

  /**
   * Défini le corps de l'ennemi pour les collisions
   *
   * @param x      La position en x de l'ennemi
   * @param y      La position en y de l'ennemi
   * @param radius Le rayon du corps de l'ennemi
   *
   * @return Le corps de l'ennemi
   */
  @Override
  public FixtureDef defineEnemy(float x, float y, float radius) {
    FixtureDef fdef = super.defineEnemy(x, y, radius);

    CircleShape shape = new CircleShape();
    shape.setRadius(38 / CCleaner.PPM);

    fdef.filter.categoryBits = CCleaner.ENEMY_MELEE_BIT;
    fdef.shape = shape;
    fdef.isSensor = true;

    b2body.createFixture(fdef).setUserData(this);

    return fdef;
  }

  /**
   * Met à jour l'ennemi à chaque frame
   *
   * @param delta Le temps écoulé depuis la dernière frame
   */
  @Override
  public void update(float delta) {
    super.update(delta);
    followCursor();
    attack();
  }

  /**
   * Permet à l'ennemi de suivre le curseur
   */
  public void followCursor() {
    float damping = 0.5f;
    float force = 30f; // Ajustez cette valeur en fonction de la vitesse souhaitée.

    b2body.applyForceToCenter(0, 0, true);
    b2body.setLinearVelocity(b2body.getLinearVelocity().scl(1 - damping));

    // Position du curseur.
    float cursorX = cursor.b2body.getPosition().x;
    float cursorY = cursor.b2body.getPosition().y;

    // Position actuelle de l'ennemi.
    float spiderX = b2body.getPosition().x;
    float spiderY = b2body.getPosition().y;

    // Calculez la direction du curseur par rapport au Spider.
    float directionX = cursorX - spiderX;
    float directionY = cursorY - spiderY;

    // Calcule la longueur de la direction.
    float length = (float) Math.sqrt(directionX * directionX + directionY * directionY);

    if (length >= 0.8f) {
      directionX /= length;
      directionY /= length;

      // Appliquez une force dans la direction du curseur.
      b2body.applyForceToCenter(directionX * force, directionY * force, true);
    }

    float angleRad = MathUtils.atan2(cursorY - spiderY, cursorX - spiderX);
    setRotation(MathUtils.radiansToDegrees * angleRad + 90);
  }

}
