package com.epitech.ccleaner.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.epitech.ccleaner.CCleaner;

public class MainMenuScreen implements Screen {

  /**
   * Main class of the game
   */
  private CCleaner game;

  /**
   * The viewport of the main menu screen
   */
  private Viewport viewport;

  /**
   * The stage of the main menu
   */
  private Stage stage;

  private OrthographicCamera camera;
  private ShapeRenderer shape;
  private TiledMap map;
  private OrthogonalTiledMapRenderer renderer;
  Music music;

  /**
   * Constructor
   * Create the main menu screen with viewport, camera, world...
   *
   * @param game Main class of the game
   */
  public MainMenuScreen(final CCleaner game) {
    this.game = game;

    stage = new Stage(new ScreenViewport()); // Initialisez la scène avec un viewport

    Gdx.input.setInputProcessor(stage); // Définissez la scène comme processeur d'entrée

    // Utilisation de la méthode pour créer les boutons
    TextButton playButton = createButton("MenuUi/MainMenu/Play Button.png", "MenuUi/MainMenu/Play Button Hover.png");
    TextButton optionsButton = createButton("MenuUi/MainMenu/Options Button.png", "MenuUi/MainMenu/Options Button Hover.png");
    TextButton creditsButton = createButton("MenuUi/MainMenu/Credits Button.png", "MenuUi/MainMenu/Credits Button Hover.png");
    TextButton exitButton = createButton("MenuUi/MainMenu/Exit Button.png", "MenuUi/MainMenu/Exit Button Hover.png");
    TextButton returnButton = createButton("MenuUi/MainMenu/Return Button.png", "MenuUi/MainMenu/Return Button Hover.png");

    // Ajoutez les boutons à une table
    Table table = new Table();
    table.setFillParent(true); // La table prend toute la taille de la scène
    table.add(playButton).padBottom(20).row();
    table.add(optionsButton).padBottom(20).row();
    table.add(creditsButton).padBottom(20).row();
    table.padTop(50);
    table.padLeft(450);

    Table exitTable = new Table();
    exitTable.setFillParent(true);
    exitTable.add(exitButton).padTop(660).bottom().padRight(900);

    Table returnTable = new Table();
    returnTable.setFillParent(true);
    returnTable.add(returnButton).padTop(660).bottom().padLeft(900);

    configureButtons(game, playButton, optionsButton, creditsButton, exitButton, returnButton);

    // Ajoutez la table à la scène
    stage.addActor(table);
    stage.addActor(exitTable);
//    stage.addActor(returnTable);
  }
  @Override
  public void show() {
    // Charger le fichier TMX
    map = new TmxMapLoader().load("MenuUi/MainMenu/MainMenu.tmx");
    music = CCleaner.manager.get("Music/WindowsXPInstallationMusic.mp3", Music.class);
    music.setLooping(true);
    music.play();
    // Créer un renderer pour la carte orthogonale
    renderer = new OrthogonalTiledMapRenderer(map, 1 / CCleaner.PPM);
    camera = new OrthographicCamera();
    viewport = new FitViewport(CCleaner.WIDTH / CCleaner.PPM, CCleaner.HEIGHT / CCleaner.PPM, camera);
    camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
  }

  /**
   * Render the main menu screen
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
//    b2dr.render(world, camera.combined);
    game.batch.setProjectionMatrix(camera.combined);
    shape = new ShapeRenderer();
    game.batch.setProjectionMatrix(camera.combined);
    Gdx.input.setInputProcessor(stage);
    stage.draw();
    game.batch.begin();
    stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
    game.batch.end();
  }
  public void update(float delta) {
    renderer.setView(camera);
  }
  /**
   * Resize the main menu screen
   *
   * @param width  Width of the screen
   * @param height Height of the screen
   */
  @Override
  public void resize(int width, int height) {
    viewport.update(width, height);
    camera.update();
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
  @Override
  public void dispose() {
    stage.dispose();
    renderer.dispose();
    music.dispose();
  }
  public Music getMusic() {
    return music;
  }
  public void setMusic(Music music) {
    this.music = music;
  }
  public Texture resizeTexture(String path) {
    // Chargez la texture originale
    Texture originalTexture = new Texture(Gdx.files.internal(path));

    // Obtenez les dimensions de la texture originale
    int originalWidth = originalTexture.getWidth();
    int originalHeight = originalTexture.getHeight();

    // Déterminez les dimensions cibles (par exemple, 100 pixels de large)
    int targetWidth = 300;
    int targetHeight = 50;

    // Créez un Pixmap pour redimensionner la texture
    Pixmap pixmap = new Pixmap(targetWidth, targetHeight, originalTexture.getTextureData().getFormat());
    pixmap.drawPixmap(new Pixmap(Gdx.files.internal(path)), 0, 0, originalWidth, originalHeight, 0, 0, targetWidth, targetHeight);

    // Créez une nouvelle texture à partir du Pixmap
    Texture resizedTexture = new Texture(pixmap);

    // Libérez la texture originale et le Pixmap
    originalTexture.dispose();
    pixmap.dispose();

    return resizedTexture;
  }
  public TextButton createButton(String upImagePath, String downImagePath) {
    // Créez un skin pour le bouton
    Skin buttonSkin = new Skin();

    // Chargez les textures du bouton
    Texture buttonUp = resizeTexture(upImagePath);
    Texture buttonDown = resizeTexture(downImagePath);

    // Créez un style de bouton avec les textures
    Drawable drawableUp = new TextureRegionDrawable(buttonUp);
    Drawable drawableDown = new TextureRegionDrawable(buttonDown);

    TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
    buttonStyle.up = drawableUp;
    buttonStyle.down = drawableDown;
    buttonStyle.font = new BitmapFont();

    // Ajoutez le style au skin
    buttonSkin.add("default", buttonStyle);

    // Créez et retournez le bouton avec le skin approprié
    return new TextButton("", buttonSkin);
  }
  public static void handleOnclickButton(CCleaner game, TextButton button, Screen screen) {
    button.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.setScreen(screen);
      }
    });
  }
  public static void configureButtons(CCleaner game, TextButton playButton, TextButton optionsButton, TextButton creditsButton, TextButton exitButton, TextButton returnButton) {
    Screen playScreen = new GameScreen(game);
    Screen optionsScreen = new OptionsScreen(game);
    Screen creditsScreen = new CreditsScreen(game);
    handleOnclickButton(game, playButton, playScreen);
    handleOnclickButton(game, optionsButton, optionsScreen);
    handleOnclickButton(game, creditsButton, creditsScreen);
    exitButton.addListener(new ClickListener() {
      public void clicked(InputEvent event, float x, float y) {
        Gdx.app.exit();
      }
    });
    returnButton.addListener(new ClickListener() {
      public void clicked(InputEvent event, float x, float y) {
        Gdx.app.exit();
      }
    });
  }
}