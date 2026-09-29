package com.epitech.ccleaner.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.epitech.ccleaner.CCleaner;

public class CreditsScreen implements Screen {
        private TiledMap tiledMap;
        private MapRenderer mapRenderer;
        private OrthographicCamera camera;
        private Viewport viewport;
        private CCleaner game;
        private Stage stage;

    public CreditsScreen(CCleaner game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
    }

    @Override
        public void show() {
            // Charger le fichier TMX
            tiledMap = new TmxMapLoader().load("MenuUi/CreditsMenu/Credit_Menu.tmx");
            mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / CCleaner.PPM);

            TextButton returnButton = com.epitech.ccleaner.util.ButtonUtil.createButton("MenuUi/MainMenu/Exit Button.png", "MenuUi/MainMenu/Exit Button Hover.png");

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
            // Effacer l'écran
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            // Afficher la carte
            mapRenderer.setView(camera);
            mapRenderer.render();
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
            // Libérer les ressources
            tiledMap.dispose();
            stage.dispose();
        }
    }
