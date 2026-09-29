package com.epitech.ccleaner.managers;

import com.epitech.ccleaner.screens.GameScreen;
import com.epitech.ccleaner.sprites.levels.LevelFolder;
import com.epitech.ccleaner.sprites.levels.zones.LevelZone;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


public class LevelManager {

  private GameScreen screen;

  private List<LevelFolder> levels;

  private LevelFolder currentLevel;

  public LevelManager(GameScreen screen) {
    this.screen = screen;

    levels = new ArrayList<>();
    currentLevel = null;
  }

  public void update() {
    for (LevelFolder level : levels) {
      level.draw(screen.game.batch);
    }

    if (currentLevel != null) {
      currentLevel.getZone().update();
      currentLevel.getZone().draw(screen.game.batch);
    }
  }

  public void createItem(Supplier<LevelFolder> itemSupplier) {
    LevelFolder level = itemSupplier.get();
    levels.add(level);
  }

  public LevelFolder getCurrentLevel() {
    return currentLevel;
  }

  public void setCurrentLevel(LevelFolder currentLevel) {
    this.currentLevel = currentLevel;
    screen.getItemManager().destroyAllClassItems();
  }

  public LevelZone getCurrentZone() {
    return currentLevel.getZone();
  }

}
