package clouds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import Player.Player;
import helpers.GameInfo;

public class CloudsController {

    private World world;

    private Array<Cloud> clouds = new Array<Cloud>();

    private final float DISTANCE_BETWEEN_CLOUDS = 250f;
    private float minX, maxX;
    private float lastCloudPositionY;
    private Random random = new Random();

    private float cameraY;

    public CloudsController(World world) {
        this.world = world;
        minX = GameInfo.WIDTH / 2f - 130;
        maxX = GameInfo.WIDTH / 2f + 130;
        createClouds();
        positionClouds(true);
    }

    void createClouds() {

        for (int i = 0; i < 2; i++) {
            clouds.add(new Cloud(world, "Dark Cloud"));

        }

        int index = 1;

        for (int i = 0; i < 6; i++) {
            clouds.add(new Cloud(world, "Cloud " + index));
            index++;
            if (index > 3) {
                index = 1;
            }
        }

        clouds.shuffle();

    }

    void positionClouds(boolean firstTimeArranging) {

        while (clouds.get(0).getCloudName() == "Dark Cloud") {
            clouds.shuffle();
        }

        float positionY;

        if (firstTimeArranging) {
            positionY = GameInfo.HEIGHT / 2f;
        } else {
            positionY = lastCloudPositionY;
        }

        int controlX = 0;

        for (Cloud c : clouds) {

            if (c.getX() == 0 && c.getY() == 0) {
                float tempX = 0;

                if (controlX == 0) {
                    tempX = randomBetweenNumbers(maxX - 70, maxX);
                    controlX = 1;
                } else if (controlX == 1) {
                    tempX = randomBetweenNumbers(minX, minX + 70);
                    controlX = 0;
                }

                c.setSpritePosition(tempX, positionY);

                positionY -= DISTANCE_BETWEEN_CLOUDS;
                lastCloudPositionY = positionY;

            }

        }

    }

    public void drawClouds(SpriteBatch batch) {
        for (Cloud c : clouds) {
            batch.draw(c, c.getX() - c.getWidth() / 2f,
                    c.getY() - c.getHeight() / 2f);
        }
    }

    public void createAndArrangeNewClouds() {
        for (int i = 0; i < clouds.size; i++) {
            if ((clouds.get(i).getY() - GameInfo.HEIGHT / 2 - 20) > cameraY) {
                //cloud is out of bounds
                clouds.get(i).getTexture().dispose();
                clouds.removeIndex(i);
            }
        }

        if (clouds.size == 4) {
            createClouds();

            positionClouds(false);
        }

    }

    public void setCameraY(float cameraY) {
        this.cameraY = cameraY;
    }

    public Player positionThePlayer(Player player) {
        player = new Player(world, clouds.get(0).getX(),
                clouds.get(0).getY() + 100);
        return player;
    }

    private float randomBetweenNumbers(float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }

} // clouds controller













































