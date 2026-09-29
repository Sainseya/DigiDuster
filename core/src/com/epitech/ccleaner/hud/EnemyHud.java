package com.epitech.ccleaner.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.epitech.ccleaner.CCleaner;
import com.epitech.ccleaner.sprites.enemies.Enemy;
import com.epitech.ccleaner.tools.TextureColor;


public class EnemyHud extends Sprite {

  public static Texture texture = TextureColor.create(0, 0.5f, 0, 1);

  private Enemy enemy;

  private double originalHealth;

  public EnemyHud(Enemy enemy, double originalHealth) {
    super(texture);

    this.enemy = enemy;
    this.originalHealth = originalHealth;
  }

  public void update() {
    double healthPercentage = MathUtils.clamp(enemy.getHealth() / originalHealth, 0, 1);
    double barWidth = enemy.getWidth() * healthPercentage;

    // Calcul du centre horizontal de l'ennemi
    float centerX = enemy.getX() + (enemy.getWidth()) / 2;

    // Ajuste les coordonnées pour centrer la barre horizontalement au-dessus de l'ennemi
    float x = (float) (centerX - barWidth / 2);
    float y = enemy.getY() + enemy.getHeight() + 0.15f; // Ajuste la hauteur selon tes préférences

    setBounds(x, y, (float) barWidth, 5f / CCleaner.PPM);
    setPosition(x, y);
    setOrigin(getWidth() / 2, getHeight() / 2);
  }

}
