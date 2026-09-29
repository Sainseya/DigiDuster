package com.epitech.ccleaner.sprites.cursor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.epitech.ccleaner.CCleaner;
import com.epitech.ccleaner.hud.progressbar.CooldownBar;
import com.epitech.ccleaner.hud.progressbar.CursorHealthBar;
import com.epitech.ccleaner.hud.progressbar.CursorShieldBar;
import com.epitech.ccleaner.managers.BulletManager;
import com.epitech.ccleaner.screens.GameOverScreen;
import com.epitech.ccleaner.screens.GameScreen;
import com.epitech.ccleaner.screens.MainMenuScreen;
import com.epitech.ccleaner.sprites.enemies.Enemy;
import com.epitech.ccleaner.sprites.items.Item;
import com.epitech.ccleaner.sprites.items.Pin;
import com.epitech.ccleaner.sprites.levels.LevelFolder;
import com.epitech.ccleaner.tools.MapSize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Cursor extends Sprite {

  /**
   * Le corps du curseur
   */
  public Body b2body;

  private Controller controller;

  private boolean hasController;

  /**
   * Le HUD de la vie du curseur
   */
  private CursorHealthBar healthBar;

  /**
   * Le HUD du bouclier du curseur
   */
  private CursorShieldBar shieldBar;

  /**
   * L'écran du jeu
   */
  private GameScreen screen;
  private MainMenuScreen mainScreen;

  /**
   * La vie du curseur
   */
  private double health;

  /**
   * Le type d'attaque du curseur
   */
  private AttackType attackType;

  /**
   * Les dégâts du curseur
   */
  private double damage;

  private double damageMultiplier;

  /**
   * La vitesse du curseur
   */
  private double speed;

  /**
   * Le bouclier du curseur
   */
  private double shield;

  /**
   * Le cooldown du tir
   */
  private double attackCooldown;

  private double attackCooldownMultiplier;

  /**
   * Le temps écoulé depuis le dernier tir
   */
  private float timeSinceLastAttack;

  /**
   * Si le curseur est en train de flasher car il a été touché
   */
  private boolean isFlashing;

  /**
   * Le timer du flash
   */
  private float flashTimer;

  private Map<Class<? extends Item>, Integer> items;

  private Shield shieldSprite;

  private List<Enemy> enemiesInMeleeRange;

  private BulletManager bulletManager;

  private boolean tripleShotActive;

  private float tripleShotTimer;

  private int tripleShotNumber;

  private LevelFolder hoveredLevelFolder;

  private CooldownBar cooldownBar;

  /**
   * Constructor
   *
   * @param screen GameScreen
   */
  public Cursor(GameScreen screen) {
    super(new Texture("cursor/cursor.png"));

    this.screen = screen;
    controller = screen.getController();
    hasController = controller != null;

    health = 100;
    shield = 0;

    speed = 1;

    attackType = null;
    damage = 0;
    attackCooldown = 0;
    timeSinceLastAttack = 0;

    damageMultiplier = 1;
    attackCooldownMultiplier = 1;

    tripleShotActive = false;
    tripleShotTimer = 0;
    tripleShotNumber = 0;

    hoveredLevelFolder = null;

    items = new HashMap<>();
    enemiesInMeleeRange = new ArrayList<>();

    defineCursor();

    setBounds(0, 0, 58 / CCleaner.PPM, 58 / CCleaner.PPM);

    healthBar = new CursorHealthBar(this);
    shieldBar = new CursorShieldBar(this);
    cooldownBar = new CooldownBar(this);
    shieldSprite = new Shield(this);

    bulletManager = new BulletManager(screen);
  }

  /**
   * Getter de health
   *
   * @return La vie du curseur
   */
  public double getHealth() {
    return health;
  }

  public void setHealth(double health) {
    if (this.health + health > 100) this.health = 100;
    else this.health = health;
  }

  /**
   * Getter de damage
   *
   * @return Les dégâts du curseur
   */
  public double getDamage() {
    return damage * damageMultiplier;
  }

  public void setDamage(double damage) {
    this.damage = damage;
  }

  public double getDamageMultiplier() {
    return damageMultiplier;
  }

  public void setDamageMultiplier(double damageMultiplier) {
    this.damageMultiplier = damageMultiplier;
  }

  /**
   * Getter de speed
   *
   * @return La vitesse du curseur
   */
  public double getSpeed() {
    return speed;
  }

  /**
   * Setter de speed
   *
   * @param speed La vitesse du curseur
   */
  public void setSpeed(double speed) {
    if (speed >= 1.5) return;

    this.speed = speed;
  }

  /**
   * Getter de shield
   *
   * @return Le bouclier du curseur
   */
  public double getShield() {
    return shield;
  }

  /**
   * Setter de shield
   *
   * @param shield Le bouclier du curseur
   */
  public void setShield(double shield) {
    this.shield = shield;
  }

  /**
   * Getter de shotCooldown
   *
   * @return Le cooldown du tir
   */
  public double getAttackCooldown() {
    return attackCooldown / attackCooldownMultiplier;
  }

  public void setAttackCooldown(double attackCooldown) {
    this.attackCooldown = attackCooldown;
  }

  public double getAttackCooldownMultiplier() {
    return attackCooldownMultiplier;
  }

  public void setAttackCooldownMultiplier(double attackCooldownMultiplier) {
    this.attackCooldownMultiplier = attackCooldownMultiplier;
  }

  public float getTimeSinceLastAttack() {
    return timeSinceLastAttack;
  }

  public AttackType getAttackType() {
    return attackType;
  }

  public void setAttackType(AttackType attackType) {
    this.attackType = attackType;

    switch (attackType) {
      case MELEE:
        setDamage(30);
        setAttackCooldown(2);
        break;
      case TRIPLE_SHOT:
        setDamage(10);
        setAttackCooldown(0.8);
        break;
      case WAVE:
        setDamage(20);
        setAttackCooldown(3);
        break;
    }
  }

  public LevelFolder getHoveredLevelFolder() {
    return hoveredLevelFolder;
  }

  public void setHoveredLevelFolder(LevelFolder hoveredLevelFolder) {
    this.hoveredLevelFolder = hoveredLevelFolder;
  }

  public void addEnemyInMeleeRange(Enemy enemy) {
    if (!enemiesInMeleeRange.contains(enemy)) enemiesInMeleeRange.add(enemy);
  }

  public void removeEnemyInMeleeRange(Enemy enemy) {
    enemiesInMeleeRange.remove(enemy);
  }

  public Map<Class<? extends Item>, Integer> getItems() {
    return items;
  }

  public void addItem(Item item) {
    Class<? extends Item> itemClass = item.getClass();

    if (items.containsKey(itemClass)) {
      items.put(itemClass, items.get(itemClass) + 1);
    } else {
      items.put(itemClass, 1);
    }
  }

  public int getItemCount(Class<? extends Item> itemClass) {
    if (items.containsKey(itemClass)) {
      return items.get(itemClass);
    } else {
      return 0;
    }
  }

  /**
   * Défini le corps du curseur pour les collisions
   */
  public void defineCursor() {
    BodyDef bdef = new BodyDef();

    MapSize mapSize = screen.getMapSize();
    bdef.position.set(mapSize.width / 2, mapSize.height / 2);
    bdef.type = BodyDef.BodyType.DynamicBody;
    b2body = screen.getWorld().createBody(bdef);

    FixtureDef fdef = new FixtureDef();
    CircleShape shape = new CircleShape();
    shape.setRadius(30 / CCleaner.PPM);

    fdef.filter.categoryBits = CCleaner.CURSOR_BIT;
    fdef.filter.maskBits = CCleaner.WALL_BIT | CCleaner.ENEMY_BIT | CCleaner.ENEMY_MELEE_BIT | CCleaner.ITEM_BIT | CCleaner.LEVEL_FOLDER_BIT | CCleaner.WORM_BULLET_BIT;

    fdef.shape = shape;
    b2body.createFixture(fdef).setUserData(this);

    ChainShape handToHandShape = new ChainShape();

    float radius = 64 / CCleaner.PPM;
    int segments = 20; // Ajuste le nombre de segments selon tes besoins
    float angle = 180f / segments;

    Vector2[] vertices = new Vector2[segments + 1];
    for (int i = 0; i <= segments; i++) {
      float theta = angle * i;
      float x = radius * MathUtils.cosDeg(theta);
      float y = radius * MathUtils.sinDeg(theta);
      vertices[i] = new Vector2(x, y);
    }

    handToHandShape.createChain(vertices);

    fdef.filter.categoryBits = CCleaner.CURSOR_MELEE_BIT;
    fdef.filter.maskBits = CCleaner.ENEMY_BIT;
    fdef.shape = handToHandShape;
    fdef.isSensor = true;

    b2body.createFixture(fdef).setUserData(this);
  }

  @Override
  public void draw(Batch batch, float delta) {
    healthBar.draw(batch);
    shieldBar.draw(batch);
    cooldownBar.draw(batch);
    bulletManager.update(delta);

    super.draw(batch);

    if (shield > 0) {
      shieldSprite.draw(batch);
    }
  }

  public void updateController() {
    controller = screen.getController();
    hasController = controller != null;
  }

  /**
   * Met à jour le curseur à chaque frame
   *
   * @param delta Le temps écoulé depuis la dernière frame
   */
  public void update(float delta) {
    if (!tripleShotActive) {
      timeSinceLastAttack += delta;
    }

    updateController();

    if (hasController) {
      hanleInputController(delta);
    } else {
      handleInput(delta);
    }

    float x = b2body.getPosition().x - getWidth() / 2;
    float y = b2body.getPosition().y - getHeight() / 2;

    setPosition(x, y);
    setOrigin(getWidth() / 2, getHeight() / 2);

    flashing(delta);

    healthBar.update();
    shieldBar.update();
    cooldownBar.update();
    shieldSprite.update();

    if (tripleShotActive) {
      if (tripleShotNumber == 3) {
        tripleShotActive = false;
      }

      tripleShotTimer += delta;
      if (tripleShotTimer >= 0.1f) {
        shootBullet();
        tripleShotNumber++;
        tripleShotTimer = 0;
      }
    }
  }

  /**
   * Gère les entrées clavier
   */
  public void handleInput(float delta) {
    float force = 60f;
    float damping = 0.2f;

    float velX = 0;
    float velY = 0;

    if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
      velY = force;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
      velY = -force;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
      velX = -force;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
      velX = force;
    }

    if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
      attack(delta);
    }

    if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && hoveredLevelFolder != null && screen.getLevelManager().getCurrentLevel() == null && !hoveredLevelFolder.getZone().isEnd() && attackType != null) {
      hoveredLevelFolder.showLevel(this);
      screen.getLevelManager().setCurrentLevel(hoveredLevelFolder);
    }

    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && screen.getLevelManager().getCurrentLevel() != null && screen.getLevelManager().getCurrentZone().isEnd()) {
      screen.getLevelManager().getCurrentLevel().closeLevel();
      screen.getLevelManager().setCurrentLevel(null);
    }

    b2body.applyForceToCenter((float) (velX * speed), (float) (velY * speed), true);

    b2body.setLinearVelocity(b2body.getLinearVelocity().scl(1 - damping));

    if (b2body.getLinearVelocity().len2() > 0.001f) {
      float angleRad = MathUtils.atan2(-b2body.getLinearVelocity().x, b2body.getLinearVelocity().y);
      setRotation(MathUtils.radiansToDegrees * angleRad);
      b2body.setTransform(b2body.getPosition(), angleRad);
    }
  }

  public void hanleInputController(float delta) {
    if (controller.getButton(controller.getMapping().buttonA)) {
      attack(delta);
    }

    if (controller.getButton(controller.getMapping().buttonX) && hoveredLevelFolder != null && screen.getLevelManager().getCurrentLevel() == null && !hoveredLevelFolder.getZone().isEnd() && attackType != null) {
      hoveredLevelFolder.showLevel(this);
      screen.getLevelManager().setCurrentLevel(hoveredLevelFolder);
    }

    if (controller.getButton(controller.getMapping().buttonB) && screen.getLevelManager().getCurrentLevel() != null && screen.getLevelManager().getCurrentZone().isEnd()) {
      screen.getLevelManager().getCurrentLevel().closeLevel();
      screen.getLevelManager().setCurrentLevel(null);
    }

    float force = 60f;
    float damping = 0.2f;

    float velX = controller.getAxis(controller.getMapping().axisLeftX) * force;
    float velY = -controller.getAxis(controller.getMapping().axisLeftY) * force;

    b2body.applyForceToCenter((float) (velX * speed), (float) (velY * speed), true);

    b2body.setLinearVelocity(b2body.getLinearVelocity().scl(1 - damping));

    if (b2body.getLinearVelocity().len2() > 0.001f) {
      float angleRad = MathUtils.atan2(-b2body.getLinearVelocity().x, b2body.getLinearVelocity().y);
      setRotation(MathUtils.radiansToDegrees * angleRad);
      b2body.setTransform(b2body.getPosition(), angleRad);
    }
  }

  /**
   * Permet au curseur de tirer
   */
  public void attack(float delta) {
    if (attackType == null || timeSinceLastAttack < attackCooldown) return;

    switch (attackType) {
      case MELEE:
        if (enemiesInMeleeRange.isEmpty()) return;

        for (Enemy enemy : enemiesInMeleeRange) {
          enemy.hit(getDamage());
        }
        timeSinceLastAttack = 0;
        break;
      case TRIPLE_SHOT:
        tripleShotActive = true;
        tripleShotNumber = 0;
        tripleShotTimer = 0.1f;
        timeSinceLastAttack = 0;
        break;
      case WAVE:
        shootWave();
        timeSinceLastAttack = 0;
        break;
    }

  }

  public void shootBullet() {
    float bulletOffsetX = MathUtils.cosDeg(getRotation() + 90) * (getWidth() / 2);
    float bulletOffsetY = MathUtils.sinDeg(getRotation() + 90) * (getHeight() / 2);

    float bulletX = b2body.getPosition().x + bulletOffsetX;
    float bulletY = b2body.getPosition().y + bulletOffsetY;

    int maxPiercing = getItemCount(Pin.class);

    bulletManager.createBullet(bulletX, bulletY, getRotation(), getDamage(), maxPiercing);
  }

  public void shootWave() {
    float bulletOffsetX = MathUtils.cosDeg(getRotation() + 90) * (getWidth() / 2);
    float bulletOffsetY = MathUtils.sinDeg(getRotation() + 90) * (getHeight() / 2);

    float bulletX = b2body.getPosition().x + bulletOffsetX;
    float bulletY = b2body.getPosition().y + bulletOffsetY;

    bulletManager.createWave(bulletX, bulletY, getRotation(), getDamage());
  }

  /**
   * Lorsque le curseur est touché
   *
   * @param damage Les dégâts reçus
   */
  public void hit(double damage) {
    if (shield > 0) {
      shield -= damage;
      if (shield < 0) {
        health += shield;
        shield = 0;
        startFlashing();
      }
    } else {
      health -= damage;
      startFlashing();
    }

    if (health <= 0) {
      screen.game.setScreen(new GameOverScreen(screen.game));
    }
  }

  /**
   * Flasher le curseur lorsqu'il est touché
   */
  private void startFlashing() {
    isFlashing = true;
    flashTimer = 0;
    setColor(1, 0, 0, 1);
  }

  /**
   * Arrêter de flasher le curseur
   */
  private void endFlashing() {
    isFlashing = false;
    setColor(Color.WHITE);
  }

  /**
   * Mettre à jour le flash du curseur à chaque frame
   *
   * @param delta Le temps écoulé depuis la dernière frame
   */
  private void flashing(float delta) {
    if (isFlashing) {
      flashTimer += delta;

      if (flashTimer >= 0.2f) {
        endFlashing();
      }
    }
  }

}
