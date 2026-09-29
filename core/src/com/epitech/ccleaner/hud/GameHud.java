package com.epitech.ccleaner.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.epitech.ccleaner.CCleaner;
import com.epitech.ccleaner.sprites.cursor.AttackType;
import com.epitech.ccleaner.sprites.cursor.Cursor;
import com.epitech.ccleaner.sprites.items.Atom;
import com.epitech.ccleaner.sprites.items.File;
import com.epitech.ccleaner.sprites.items.Item;
import com.epitech.ccleaner.sprites.items.Wifi;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;


public class GameHud implements Disposable {

  public Stage stage;

  public Table tableAttack;

  public Table tableInventory;

  private Viewport viewport;

  private Cursor cursor;

  public GameHud(SpriteBatch batch, Cursor cursor) {
    viewport = new FitViewport(CCleaner.WIDTH, CCleaner.HEIGHT, new OrthographicCamera());
    stage = new Stage(viewport, batch);
    this.cursor = cursor;

    Table tableBattery = new Table();
    tableBattery.bottom();
    tableBattery.right();
    tableBattery.padRight(20);
    tableBattery.setFillParent(true);

    Image health = new Image(new Texture("hud/battery.png"));
    Image shield = new Image(new Texture("hud/battery.png"));

    tableBattery.add(health).size(150, 30).padBottom(2);
    tableBattery.row();
    tableBattery.add(shield).size(150, 30).padBottom(4);

    stage.addActor(tableBattery);

    tableInventory = new Table();
    tableInventory.bottom();
    tableInventory.left();
    tableInventory.padLeft(260);
    tableInventory.padBottom(5);
    tableInventory.setFillParent(true);

    stage.addActor(tableInventory);

    tableAttack = new Table();
    tableAttack.bottom();
    tableAttack.left();
    tableAttack.padLeft(140);
    tableAttack.padBottom(5);
    tableAttack.setFillParent(true);

    stage.addActor(tableAttack);

  }

  public void update() {
    tableAttack.clear();
    AttackType attackType = cursor.getAttackType();

    if (attackType != null) {
      Texture texture = null;

      switch (attackType) {
        case MELEE:
          texture = File.texture;
          break;
        case WAVE:
          texture = Wifi.texture;
          break;
        case TRIPLE_SHOT:
          texture = Atom.texture;
          break;
      }

      if (texture != null)
        tableAttack.add(new Image(texture)).size(60, 60);
    }

    Iterator<Map.Entry<Class<? extends Item>, Integer>> items = cursor.getItems().entrySet().iterator();
    tableInventory.clear();

    Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.BLACK);

    while (items.hasNext()) {
      Map.Entry<Class<? extends Item>, Integer> itemEntry = items.next();
      Class<? extends Item> itemClass = itemEntry.getKey();

      try {
        Field staticField = itemClass.getField("texture");
        Object fieldValue = staticField.get(null);

        if (fieldValue instanceof Texture) {
          Texture texture = (Texture) fieldValue;
          tableInventory.add(new Image(texture)).size(60, 60).padLeft(20);

          Label label = new Label(itemEntry.getValue().toString(), font);
          label.setFontScale(1.4f);
          tableInventory.add(label).bottom();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void draw() {
    stage.draw();
  }

  public void resize(int width, int height) {
    viewport.update(width, height);
  }

  @Override
  public void dispose() {
    stage.dispose();
  }

}
