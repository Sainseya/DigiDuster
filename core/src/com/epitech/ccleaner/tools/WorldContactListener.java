package com.epitech.ccleaner.tools;

import com.badlogic.gdx.physics.box2d.*;
import com.epitech.ccleaner.CCleaner;
import com.epitech.ccleaner.sprites.cursor.Bullet;
import com.epitech.ccleaner.sprites.cursor.Cursor;
import com.epitech.ccleaner.sprites.enemies.Enemy;
import com.epitech.ccleaner.sprites.enemies.WormBullet;
import com.epitech.ccleaner.sprites.items.Item;
import com.epitech.ccleaner.sprites.levels.LevelFolder;


public class WorldContactListener implements ContactListener {

  @Override
  public void beginContact(Contact contact) {
    Fixture fixA = contact.getFixtureA();
    Fixture fixB = contact.getFixtureB();

    int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

    switch (cDef) {
      case CCleaner.CURSOR_MELEE_BIT | CCleaner.ENEMY_BIT:
        if (fixA.getFilterData().categoryBits == CCleaner.CURSOR_MELEE_BIT) {
          Cursor cursor = (Cursor) fixA.getUserData();
          Enemy enemy = (Enemy) fixB.getUserData();

          cursor.addEnemyInMeleeRange(enemy);
        } else {
          Cursor cursor = (Cursor) fixB.getUserData();
          Enemy enemy = (Enemy) fixA.getUserData();

          cursor.addEnemyInMeleeRange(enemy);
        }
        break;
      case CCleaner.BULLET_BIT | CCleaner.ENEMY_BIT:
        if (fixA.getFilterData().categoryBits == CCleaner.BULLET_BIT) {
          Bullet bullet = (Bullet) fixA.getUserData();

          ((Enemy) fixB.getUserData()).hit(bullet.getDamage());
          if (bullet.canPiercing()) {
            bullet.addPiercing();
          } else {
            bullet.destroy();
          }
        } else {
          Bullet bullet = (Bullet) fixB.getUserData();

          ((Enemy) fixA.getUserData()).hit(bullet.getDamage());
          if (bullet.canPiercing()) {
            bullet.addPiercing();
          } else {
            bullet.destroy();
          }
        }
        break;
      case CCleaner.ENEMY_MELEE_BIT | CCleaner.CURSOR_BIT:
        if (fixA.getFilterData().categoryBits == CCleaner.CURSOR_BIT) {
          ((Enemy) fixB.getUserData()).setIsAttacking(true);
        } else {
          ((Enemy) fixA.getUserData()).setIsAttacking(true);
        }
        break;
      case CCleaner.CURSOR_BIT | CCleaner.ITEM_BIT:
        if (fixA.getFilterData().categoryBits == CCleaner.CURSOR_BIT) {
          Cursor cursor = (Cursor) fixA.getUserData();
          Item item = (Item) fixB.getUserData();
          item.use(cursor);
        } else {
          Cursor cursor = (Cursor) fixB.getUserData();
          Item item = (Item) fixA.getUserData();
          item.use(cursor);
        }
        break;
      case CCleaner.CURSOR_BIT | CCleaner.LEVEL_FOLDER_BIT:
        if (fixA.getFilterData().categoryBits == CCleaner.CURSOR_BIT) {
          LevelFolder levelFolder = (LevelFolder) fixB.getUserData();
          ((Cursor) fixA.getUserData()).setHoveredLevelFolder(levelFolder);
        } else {
          LevelFolder levelFolder = (LevelFolder) fixA.getUserData();
          ((Cursor) fixB.getUserData()).setHoveredLevelFolder(levelFolder);
        }
        break;
      case CCleaner.CURSOR_BIT | CCleaner.WORM_BULLET_BIT:
        if (fixA.getFilterData().categoryBits == CCleaner.CURSOR_BIT) {
          Cursor cursor = (Cursor) fixA.getUserData();
          WormBullet bullet = (WormBullet) fixB.getUserData();

          cursor.hit(bullet.getDamage());
          bullet.destroy();
        } else {
          Cursor cursor = (Cursor) fixB.getUserData();
          WormBullet bullet = (WormBullet) fixA.getUserData();

          cursor.hit(bullet.getDamage());
          bullet.destroy();
        }
        break;
      case CCleaner.BULLET_BIT | CCleaner.WORM_BULLET_BIT:
        if (fixA.getFilterData().categoryBits == CCleaner.BULLET_BIT) {
          Bullet bullet = (Bullet) fixA.getUserData();
          WormBullet wormBullet = (WormBullet) fixB.getUserData();

          if (bullet.isDestroyToBulletContact()) bullet.destroy();
          wormBullet.destroy();
        } else {
          Bullet bullet = (Bullet) fixB.getUserData();
          WormBullet wormBullet = (WormBullet) fixA.getUserData();

          if (bullet.isDestroyToBulletContact()) bullet.destroy();
          wormBullet.destroy();
        }
        break;
    }
  }

  @Override
  public void endContact(Contact contact) {
    Fixture fixA = contact.getFixtureA();
    Fixture fixB = contact.getFixtureB();

    int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

    switch (cDef) {
      case CCleaner.CURSOR_MELEE_BIT | CCleaner.ENEMY_BIT:
        if (fixA.getFilterData().categoryBits == CCleaner.CURSOR_MELEE_BIT) {
          Cursor cursor = (Cursor) fixA.getUserData();
          Enemy enemy = (Enemy) fixB.getUserData();

          cursor.removeEnemyInMeleeRange(enemy);
        } else {
          Cursor cursor = (Cursor) fixB.getUserData();
          Enemy enemy = (Enemy) fixA.getUserData();

          cursor.removeEnemyInMeleeRange(enemy);
        }
        break;
      case CCleaner.ENEMY_MELEE_BIT | CCleaner.CURSOR_BIT:
        if (fixA.getFilterData().categoryBits == CCleaner.CURSOR_BIT) {
          ((Enemy) fixB.getUserData()).setIsAttacking(false);
        } else {
          ((Enemy) fixA.getUserData()).setIsAttacking(false);
        }
        break;
      case CCleaner.CURSOR_BIT | CCleaner.LEVEL_FOLDER_BIT:
        if (fixA.getFilterData().categoryBits == CCleaner.CURSOR_BIT) {
          Cursor cursor = (Cursor) fixA.getUserData();
          LevelFolder levelFolder = (LevelFolder) fixB.getUserData();
          LevelFolder hoveredLevelFolder = cursor.getHoveredLevelFolder();

          if (hoveredLevelFolder != null && hoveredLevelFolder.equals(levelFolder)) {
            cursor.setHoveredLevelFolder(null);
          }
        } else {
          Cursor cursor = (Cursor) fixB.getUserData();
          LevelFolder levelFolder = (LevelFolder) fixA.getUserData();
          LevelFolder hoveredLevelFolder = cursor.getHoveredLevelFolder();

          if (hoveredLevelFolder != null && hoveredLevelFolder.equals(levelFolder)) {
            cursor.setHoveredLevelFolder(null);
          }
        }
        break;
    }
  }

  @Override
  public void preSolve(Contact contact, Manifold oldManifold) {

  }

  @Override
  public void postSolve(Contact contact, ContactImpulse impulse) {

  }

}
