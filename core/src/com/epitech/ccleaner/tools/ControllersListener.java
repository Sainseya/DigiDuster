package com.epitech.ccleaner.tools;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.epitech.ccleaner.screens.GameScreen;


public class ControllersListener extends ControllerAdapter {

  private GameScreen screen;

  public ControllersListener(GameScreen screen) {
    this.screen = screen;
  }

  @Override
  public void connected(Controller controller) {
    screen.setController(controller);
  }

  @Override
  public void disconnected(Controller controller) {
    Controller newController = Controllers.getControllers().size > 0 ? Controllers.getControllers().first() : null;
    screen.setController(newController);
  }

}
