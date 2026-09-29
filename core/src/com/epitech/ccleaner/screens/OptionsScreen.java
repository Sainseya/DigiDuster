package com.epitech.ccleaner.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.epitech.ccleaner.CCleaner;

public class OptionsScreen implements Screen {
    private TiledMap tiledMap;
    private MapRenderer mapRenderer;
    private OrthographicCamera camera;
    private Viewport viewport;
    MainMenuScreen mainMenuScreen;
    private CCleaner game;
    private Stage stage;
    private float volumeLevel = 0.5f; // Vous pouvez choisir la valeur par défaut que vous préférez

    public OptionsScreen(final CCleaner game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        // Load the TMX file
        tiledMap = new TmxMapLoader().load("MenuUi/OptionMenu.tmx");
        // Create a renderer for the orthogonal map
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / CCleaner.PPM);
        TextButton returnButton = com.epitech.ccleaner.util.ButtonUtil.createButton("MenuUi/MainMenu/Exit Button.png", "MenuUi/MainMenu/Exit Button Hover.png");

        Skin skin = new Skin();
//        skin.add("logo", new Texture("scissors.png"));
//        Texture logo = skin.get("logo", Texture.class);

//        Slider volumeSlider = new Slider(0, 1, 0.1f, false,skin);
//
//        volumeSlider.setValue(volumeLevel);
//        volumeSlider.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                // Mettez à jour le niveau de volume en fonction de la position du slider
//                volumeLevel = volumeSlider.getValue();
//                // Ajoutez ici le code pour ajuster le volume de votre jeu en fonction de volumeLevel
//            }
//        });

//        Table volumeTable = new Table();
//        volumeTable.setFillParent(true);
//        volumeTable.add(volumeSlider).padTop(600).bottom().padLeft(900);
//
//        stage.addActor(volumeTable);


        Gdx.input.setInputProcessor(stage); // Définissez la scène comme processeur d'entrée
        Table returnTable = new Table();
        returnTable.setFillParent(true);
        returnTable.add(returnButton).padTop(660).bottom().padLeft(900);
        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Code to execute when the return button is clicked
                game.setScreen(new MainMenuScreen(game));
            }
        });


        camera = new OrthographicCamera();
        viewport = new FitViewport(CCleaner.WIDTH / CCleaner.PPM, CCleaner.HEIGHT / CCleaner.PPM, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        stage.addActor(returnTable);
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Display the map
        mapRenderer.setView(camera);
        mapRenderer.render();

        // Draw additional elements or perform other rendering logic
        game.batch.begin();
        // Your additional rendering code here
        game.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
    }

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

    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        stage.dispose();
    }
}
