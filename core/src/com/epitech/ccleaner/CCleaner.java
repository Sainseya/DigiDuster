package com.epitech.ccleaner;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.epitech.ccleaner.screens.MainMenuScreen;


public class CCleaner extends Game {

  /**
   * The width of the game
   */
  public static final int WIDTH = 1920;

  /**
   * The height of the game
   */
  public static final int HEIGHT = 1024;

  /**
   * The pixels per meter ratio
   */
  public static final float PPM = 100;

  public static final short WALL_BIT = 1;

  public static final short CURSOR_BIT = 2;

  public static final short CURSOR_MELEE_BIT = 4;

  public static final short ENEMY_BIT = 8;

  public static final short ENEMY_MELEE_BIT = 16;

  public static final short ITEM_BIT = 32;

  public static final short BULLET_BIT = 64;

  public static final short LEVEL_FOLDER_BIT = 256;

  public static final short WORM_BULLET_BIT = 512;

  /**
   * The SpriteBatch used to render the game
   */
  public SpriteBatch batch;

  /**
   * Create the game before rendering
   */

  public static AssetManager manager;
  @Override
  public void create() {
    batch = new SpriteBatch();
    manager = new AssetManager();
    manager.load("Music/WindowsXPInstallationMusic.mp3", Music.class);
    manager.load("Music/GatestoHellLoopMusicwithOnlyWindowsSounds-BlueWolfProduction - YT.mp3", Music.class);
    manager.load("Music/gameOver.wav", Music.class);
    manager.finishLoading();
    setScreen(new MainMenuScreen(this));
  }

  /**
   * Render the game
   */
  @Override
  public void render() {
    super.render();
  }

  /**
   * Dispose the game after rendering
   */
  @Override
  public void dispose() {
    super.dispose();
    batch.dispose();
    screen.dispose();
  }

}