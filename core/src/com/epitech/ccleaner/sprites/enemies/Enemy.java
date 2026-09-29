package com.epitech.ccleaner.sprites.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.epitech.ccleaner.CCleaner;
import com.epitech.ccleaner.hud.EnemyHud;
import com.epitech.ccleaner.screens.GameScreen;
import com.epitech.ccleaner.sprites.cursor.Cursor;
import com.epitech.ccleaner.sprites.items.HealthOrb;


public abstract class Enemy extends Sprite {

  /**
   * Le corps de l'enemi
   * Permet de gérer les collisions
   */
  public Body b2body;

  /**
   * Le HUD de l'ennemi
   * Permet d'afficher la vie de l'ennemi
   */
  public EnemyHud hud;

  /**
   * Si l'ennemi est détruit
   */
  protected boolean destroyed;

  /**
   * La vie de l'ennemi
   */
  protected double health;

  /**
   * Les dégâts de l'ennemi
   */
  protected double damage;

  /**
   * Le curseur que l'ennemi doit suivre et attaquer
   */
  protected Cursor cursor;

  /**
   * L'écran du jeu
   */
  protected GameScreen screen;

  /**
   * Si l'ennemi est en train de flasher car il a été touché
   */
  protected boolean isFlashing;

  /**
   * Le timer du flash
   */
  protected float flashTimer;

  /**
   * Le temps depuis la dernière attaque
   */
  protected float timeSinceLastAttack;

  /**
   * Si l'ennemi est en train d'attaquer
   */
  protected boolean isAttacking = false;

  private BodyDef.BodyType bodyType;

  /**
   * Constructor
   *
   * @param screen  L'écran du jeu
   * @param cursor  Le curseur que l'ennemi doit suivre et attaquer
   * @param texture La texture de l'ennemi
   * @param x       La position en x de l'ennemi
   * @param y       La position en y de l'ennemi
   * @param radius  Le rayon du corps de l'ennemi
   * @param width   La largeur de l'ennemi
   * @param height  La hauteur de l'ennemi
   * @param health  La vie de l'ennemi
   */
  protected Enemy(GameScreen screen, Cursor cursor, Texture texture, float x, float y, float radius, float width, float height, double health, double damage, BodyDef.BodyType bodyType) {
    super(texture);
    this.screen = screen;
    this.cursor = cursor;
    this.health = health;
    this.damage = damage;
    this.bodyType = bodyType;

    hud = new EnemyHud(this, health);

    defineEnemy(x, y, radius);
    setBounds(0, 0, width / CCleaner.PPM, height / CCleaner.PPM);
  }

  /**
   * Getter de destroyed
   *
   * @return Si l'ennemi est détruit
   */
  public boolean isDestroyed() {
    return destroyed;
  }

  /**
   * Getter de health
   *
   * @return La vie de l'ennemi
   */
  public double getHealth() {
    return health;
  }

  /**
   * Getter de damage
   *
   * @return Les dégâts de l'ennemi
   */
  public double getDamage() {
    return damage;
  }

  /**
   * Setter de isAttacking
   *
   * @param isAttacking Si l'ennemi est en train d'attaquer
   */
  public void setIsAttacking(boolean isAttacking) {
    this.isAttacking = isAttacking;
  }

  /**
   * Détruire l'ennemi
   */
  public void destroy() {
    destroyed = true;
  }

  /**
   * Définir le corps de l'ennemi
   *
   * @param x      La position en x de l'ennemi
   * @param y      La position en y de l'ennemi
   * @param radius Le rayon du corps de l'ennemi
   *
   * @return FixtureDef
   */
  public FixtureDef defineEnemy(float x, float y, float radius) {
    // Créer le corps de l'ennemi
    BodyDef bdef = new BodyDef();
    bdef.position.set(x / CCleaner.PPM, y / CCleaner.PPM);
    bdef.type = bodyType;

    // Ajoute le corps de l'ennemi au monde
    b2body = screen.getWorld().createBody(bdef);

    // Définir le corps de l'ennemi
    FixtureDef fdef = new FixtureDef();

    // Créer la forme du corps de l'ennemi
    CircleShape shape = new CircleShape();
    shape.setRadius(radius / CCleaner.PPM);

    // Définir la catégorie du corps de l'ennemi
    fdef.filter.categoryBits = CCleaner.ENEMY_BIT;

    // Définir les catégories avec lesquelles le corps de l'ennemi peut entrer en collision
    fdef.filter.maskBits = CCleaner.WALL_BIT | CCleaner.CURSOR_BIT | CCleaner.CURSOR_MELEE_BIT | CCleaner.BULLET_BIT | CCleaner.ENEMY_BIT;

    fdef.shape = shape;
    b2body.createFixture(fdef).setUserData(this);

    return fdef;
  }

  /**
   * Mettre à jour l'ennemi à chaque frame
   *
   * @param delta Le temps écoulé depuis la dernière frame
   */
  public void update(float delta) {
    timeSinceLastAttack += delta;

    float x = b2body.getPosition().x - getWidth() / 2;
    float y = b2body.getPosition().y - getHeight() / 2;

    float newX = MathUtils.clamp(x, 0, CCleaner.WIDTH / CCleaner.PPM - getWidth());
    float newY = MathUtils.clamp(y, 0.6f, CCleaner.HEIGHT / CCleaner.PPM - getHeight());

    b2body.setTransform(newX + getWidth() / 2, newY + getHeight() / 2, 0);

    setPosition(x, y);
    setOrigin(getWidth() / 2, getHeight() / 2);

    hud.update();
    hud.draw(screen.game.batch);
    flashing(delta);
  }

  /**
   * Lorsque l'ennemi est touché
   *
   * @param damage Les dégâts reçus
   */
  public void hit(double damage) {
    health -= damage;
    if (health <= 0) {
      destroy();
    } else {
      startFlashing();
    }
  }

  /**
   * Flasher l'ennemi lorsqu'il est touché
   */
  protected void startFlashing() {
    isFlashing = true;
    flashTimer = 0;
    setColor(1, 0, 0, 1);
  }

  /**
   * Arrêter de flasher l'ennemi
   */
  protected void endFlashing() {
    isFlashing = false;
    setColor(Color.WHITE);
  }

  /**
   * Mettre à jour le flash de l'ennemi à chaque frame
   *
   * @param delta Le temps écoulé depuis la dernière frame
   */
  protected void flashing(float delta) {
    if (isFlashing) {
      flashTimer += delta;

      if (flashTimer >= 0.2f) {
        endFlashing();
      }
    }
  }

  /**
   * Attaquer le curseur à chaque frame
   * Si le temps écoulé depuis la dernière attaque est supérieur au cooldown de l'attaque
   */
  public void attack() {
    if (!isAttacking) return;

    float shotCooldown = 2f;

    if (timeSinceLastAttack >= shotCooldown) {
      cursor.hit(damage);
      timeSinceLastAttack = 0;
    }
  }

  public void onDie() {
    boolean dropHealthOrb = MathUtils.randomBoolean(0.3f);

    if (dropHealthOrb) {
      Vector2 position = b2body.getPosition();

      screen.getItemManager().createItem(() -> new HealthOrb(screen, position.x * CCleaner.PPM, position.y * CCleaner.PPM));
    }
  }

}
