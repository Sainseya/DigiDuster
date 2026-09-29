package com.epitech.ccleaner.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.epitech.ccleaner.CCleaner;
import com.epitech.ccleaner.hud.GameHud;
import com.epitech.ccleaner.managers.EnemyManager;
import com.epitech.ccleaner.managers.ItemManager;
import com.epitech.ccleaner.managers.LevelManager;
import com.epitech.ccleaner.sprites.cursor.Cursor;
import com.epitech.ccleaner.sprites.items.Atom;
import com.epitech.ccleaner.sprites.items.File;
import com.epitech.ccleaner.sprites.items.Wifi;
import com.epitech.ccleaner.sprites.levels.LevelFolder;
import com.epitech.ccleaner.sprites.levels.zones.BossZone;
import com.epitech.ccleaner.sprites.levels.zones.RandomRoom;
import com.epitech.ccleaner.tools.ControllersListener;
import com.epitech.ccleaner.tools.MapSize;
import com.epitech.ccleaner.tools.WorldContactListener;


public class GameScreen implements Screen {

  private static final int MIN_LEVELS = 3;

  private static final int MAX_LEVELS = 10;

  /**
   * Main class of the game
   */
  public CCleaner game;

  private Controller controller;

  /**
   * The size of the map
   */
  private MapSize mapSize;

  /**
   * The viewport of the game screen
   */
  private Viewport viewport;

  /**
   * The world of the game
   */
  private World world;

  /**
   * The camera
   */
  private OrthographicCamera camera;

  /**
   * The cursor (player)
   */
  private Cursor cursor;

  /**
   * DEBUGGER for Box2D
   */
  private Box2DDebugRenderer b2dr;

  /**
   * The map loader for TiledMap
   */
  private TmxMapLoader mapLoader;

  /**
   * The map (tilemap)
   */
  private TiledMap map;

  /**
   * The renderer for the map
   */
  private OrthogonalTiledMapRenderer renderer;

  private GameHud gameHud;

  private EnemyManager enemyManager;

  private ItemManager itemManager;

  private LevelManager levelManager;
  private Music music;
  private MainMenuScreen mainMenuScreen;

  /**
   * Constructor
   * Create the game screen with viewport, camera, world...
   *
   * @param game Main class of the game
   */
  public GameScreen(final CCleaner game) {
    this.game = game;

    controller = Controllers.getCurrent();
    Controllers.addListener(new ControllersListener(this));

    mapSize = new MapSize(CCleaner.WIDTH / CCleaner.PPM, (CCleaner.HEIGHT - 70f) / CCleaner.PPM, 0, 70f / CCleaner.PPM);

    camera = new OrthographicCamera();

    viewport = new FitViewport(CCleaner.WIDTH / CCleaner.PPM, CCleaner.HEIGHT / CCleaner.PPM, camera);

    mapLoader = new TmxMapLoader();
    map = mapLoader.load("desktop.tmx");
    renderer = new OrthogonalTiledMapRenderer(map, 1 / CCleaner.PPM);

    camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

    world = new World(new Vector2(0, 0), true);

    b2dr = new Box2DDebugRenderer();

    cursor = new Cursor(this);

    gameHud = new GameHud(game.batch, cursor);

    world.setContactListener(new WorldContactListener());

    enemyManager = new EnemyManager(this);

    itemManager = new ItemManager(this);

    levelManager = new LevelManager(this);

    defineWorldLimits();
    generateItems();
    generateLevels();
  }

  /**
   * Getter de mapSize
   *
   * @return La taille de la map
   */
  public MapSize getMapSize() {
    return mapSize;
  }

  public World getWorld() {
    return world;
  }

  public ItemManager getItemManager() {
    return itemManager;
  }

  public LevelManager getLevelManager() {
    return levelManager;
  }

  public EnemyManager getEnemyManager() {
    return enemyManager;
  }

  public Controller getController() {
    return controller;
  }

  public void setController(Controller controller) {
    this.controller = controller;
  }

  public Vector2 generateRandomPosition() {
    float x = MathUtils.random(mapSize.x, mapSize.width) * CCleaner.PPM;
    float y = MathUtils.random(mapSize.y, mapSize.height + mapSize.y) * CCleaner.PPM;
    return new Vector2(x, y);
  }

  private Vector2[][] generateLevelsPosition(int number) {
    int size = (int) Math.ceil((double) number / 5);
    Vector2[][] positions = new Vector2[size][];

    int rest = number;
    float x = 100;
    float y = CCleaner.HEIGHT - 100;

    for (int i = 0; i < size; i++) {
      int subSize = Math.min(5, rest);
      positions[i] = new Vector2[subSize];

      for (int j = 0; j < subSize; j++) {
        positions[i][j] = new Vector2(x, y);
        y -= 170;
      }

      rest -= subSize;
      x += 170;
      y = CCleaner.HEIGHT - 100;
    }

    return positions;
  }

  public void generateLevels() {
    int numberOfLevels = MathUtils.random(MIN_LEVELS, MAX_LEVELS);
    Vector2[][] positions = generateLevelsPosition(numberOfLevels);

    int index = 0;

    for (Vector2[] subPositions : positions) {
      for (Vector2 position : subPositions) {
        if (index == numberOfLevels - 1) {
          levelManager.createItem(() -> new LevelFolder(world, new Texture("levels/folder/user.png"), new BossZone(this, cursor), position.x, position.y));
          break;
        } else {
          levelManager.createItem(() -> new LevelFolder(world, new RandomRoom(this, cursor), position.x, position.y));
        }

        index++;
      }
    }
  }

  public void generateItems() {
    float y = (mapSize.y + (mapSize.height / 1.3f)) * CCleaner.PPM;

    itemManager.createItem(() -> new Atom(this, 850, y));
    itemManager.createItem(() -> new File(this, 1000, y));
    itemManager.createItem(() -> new Wifi(this, 1150, y));
  }

  /**
   * Update the game for each frame
   */
  public void update(float delta) {
    world.step(1 / 60f, 6, 2);

    cursor.update(delta);

    gameHud.update();

    renderer.setView(camera);
  }

  private void createWall(float x, float y, float width, float height) {
    BodyDef wallDef = new BodyDef();
    wallDef.type = BodyDef.BodyType.StaticBody;
    wallDef.position.set(x + width / 2, y + height / 2);

    Body wallBody = world.createBody(wallDef);

    PolygonShape wallShape = new PolygonShape();
    wallShape.setAsBox(width / 2, height / 2);

    FixtureDef wallFixtureDef = new FixtureDef();
    wallFixtureDef.shape = wallShape;
    wallFixtureDef.filter.categoryBits = CCleaner.WALL_BIT;
    wallFixtureDef.filter.maskBits = CCleaner.CURSOR_BIT | CCleaner.ENEMY_BIT;
    wallBody.createFixture(wallFixtureDef);

    wallShape.dispose();
  }

  public void defineWorldLimits() {
    float wallThickness = 1 / CCleaner.PPM;

    createWall(mapSize.x, mapSize.y, mapSize.width, wallThickness);
    createWall(mapSize.x, mapSize.y, wallThickness, mapSize.height);
    createWall(mapSize.width, mapSize.y, wallThickness, mapSize.height);
    createWall(mapSize.x, mapSize.y + mapSize.height, mapSize.width, wallThickness);
  }

  @Override
  public void show() {
    music = CCleaner.manager.get("Music/GatestoHellLoopMusicwithOnlyWindowsSounds-BlueWolfProduction - YT.mp3", Music.class);
    music.setLooping(true);
//    music.setVolume(5);
    music.play();
  }

  /**
   * Render the game for each frame
   *
   * @param delta The time in seconds since the last render.
   */
  @Override
  public void render(float delta) {
    update(delta);

    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    camera.update();
    renderer.render();
    game.batch.setProjectionMatrix(camera.combined);

    game.batch.begin();
    levelManager.update();
    itemManager.update();
    enemyManager.update(delta);
    cursor.draw(game.batch, delta);
    game.batch.end();

    gameHud.draw();

//    b2dr.render(world, camera.combined);
  }

  /**
   * Resize the game
   *
   * @param width  The width in pixels
   * @param height The height in pixels
   */
  @Override
  public void resize(int width, int height) {
    viewport.update(width, height);
    gameHud.resize(width, height);
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void hide() {
    music.stop();
  }

  /**
   * Dispose the game after rendering
   */
  @Override
  public void dispose() {
    world.dispose();
    map.dispose();
    renderer.dispose();
    b2dr.dispose();
    music.dispose();
  }

}
