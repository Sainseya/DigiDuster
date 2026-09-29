package com.epitech.ccleaner.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.epitech.ccleaner.CCleaner;

public class ButtonUtil {
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

    public static TextButton createButton(String upImagePath, String downImagePath) {
        Skin buttonSkin = new Skin();
        Texture buttonUp = resizeTexture(upImagePath);
        Texture buttonDown = resizeTexture(downImagePath);

        Drawable drawableUp = new TextureRegionDrawable(buttonUp);
        Drawable drawableDown = new TextureRegionDrawable(buttonDown);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = drawableUp;
        buttonStyle.down = drawableDown;
        buttonStyle.font = new BitmapFont();

        buttonSkin.add("default", buttonStyle);

        return new TextButton("", buttonSkin);
    }

    public static void handleOnClickButton(CCleaner game, TextButton button, Screen screen) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(screen);
            }
        });
    }

    public static void configureButtons(CCleaner game, TextButton playButton, TextButton optionsButton, TextButton creditsButton, TextButton exitButton, TextButton returnButton, Screen playScreen, Screen optionsScreen, Screen creditsScreen) {
        handleOnClickButton(game, playButton, playScreen);
        handleOnClickButton(game, optionsButton, optionsScreen);
        handleOnClickButton(game, creditsButton, creditsScreen);

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

    public static Texture resizeTexture(String path) {
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
}
