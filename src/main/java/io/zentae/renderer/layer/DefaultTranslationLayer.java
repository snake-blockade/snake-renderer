package io.zentae.renderer.layer;

import io.zentae.snake.engine.entity.ArenaEntity;
import io.zentae.snake.engine.entity.Location;
import io.zentae.snake.engine.entity.arena.Arena;
import io.zentae.snake.engine.entity.snake.Snake;
import io.zentae.snake.engine.movement.Movement;
import jakarta.annotation.Nonnull;

import java.util.HashMap;
import java.util.Map;

public class DefaultTranslationLayer extends TranslationLayer {

    public DefaultTranslationLayer(int tileSize, @Nonnull Map<Class<?>, String> stringMap) {
        super(tileSize, stringMap);
    }

    // TODO FAIRE LES VIRAGES DU SERPENT
    @Nonnull
    @Override
    public Map<Location, String> translate(Arena arena) {
        // prepare our render.
        Map<Location, String> render = new HashMap<>();
        // do the borders.
        for (int y = 0; y < arena.getWidth(); y++) {
            for (int x = 0; x < arena.getLength(); x++)
                if (x + 1 == arena.getLength()
                        || x == 0
                        || y == 0
                        || y + 1 == arena.getWidth()) {
                    render.put(new Location(x * getTileSize(), y * getTileSize()), "X");
                } else {
                    render.put(new Location(x * getTileSize(), y * getTileSize()), " ");
                }

        }
        // loop through each object.
        for (ArenaEntity entity : arena.getObjects()) {
            if (entity instanceof Snake)
                continue;
            // try to retrieve the character associated.
            String string = getCharacterMap().get(entity.getType());
            // check if there's character mapped.
            if (string == null)
                continue;
            // loop through each segment.
            for (Location segment : entity.getSegments()) {
                render.put(new Location(segment.getX() * getTileSize(), segment.getY() * getTileSize()),
                        string);
            }
        }
        for (Snake snake : arena.getObjects(Snake.class)) {
            // loop through each segment.
            for (int i = 0; i < snake.getBody().size(); i++) {
                // retrieve the current segment.
                Location segment = snake.getBody().get(i);
                // check if we're dealing with the head.
                if (i == 0) {
                    // calculate the x offset.
                    int xOffset = snake.getHead().getX() - snake.getBody().get(1).getX();
                    // calculate the y offset.
                    int yOffset = snake.getHead().getY() - snake.getBody().get(1).getY();
                    // retrieve the movement associated.
                    // the movement cannot be null.
                    Movement movement = Movement.byOrientation(xOffset, yOffset);
                    // check if the movement is null.
                    if(movement == null)
                        continue;
                    // check if the body is under.
                    render.put(new Location(segment.getX() * getTileSize(), segment.getY() * getTileSize()),
                            "head_" + movement.toString().toLowerCase());
                } else if (i == snake.getBody().size() - 1) {
                    // calculate the x offset.
                    int xOffset = snake.getBody().get(snake.getBody().size() - 2).getX() - snake.getTail().getX();
                    // calculate the y offset.
                    int yOffset = snake.getBody().get(snake.getBody().size() - 2).getY() - snake.getTail().getY();
                    // retrieve the movement associated.
                    // the movement cannot be null.
                    Movement movement = Movement.byOrientation(xOffset, yOffset);
                    // check if the movement is null.
                    if(movement == null)
                        continue;
                    // check if the body is under.
                    render.put(new Location(segment.getX() * getTileSize(), segment.getY() * getTileSize()),
                            "tail_" + movement.toString().toLowerCase());
                } else {
                    // retrieve the body part before and after the current one.
                    Location before = snake.getBody().get(i - 1);
                    Location after = snake.getBody().get(i + 1);
                    // check if we're on a vertical case.
                    if (before.getX() == segment.getX() && after.getX() == segment.getX()) {
                        render.put(new Location(segment.getX() * getTileSize(), segment.getY() * getTileSize()),
                                "body_vertical");
                        continue;
                    }
                    if (before.getY() == segment.getY() && after.getY() == segment.getY()) {
                        render.put(new Location(segment.getX() * getTileSize(), segment.getY() * getTileSize()),
                                "body_horizontal");
                        continue;
                    }
                    if(after.getY() > segment.getY()) {
                        if (before.getX() > segment.getX()) {
                            render.put(new Location(segment.getX() * getTileSize(), segment.getY() * getTileSize()),
                                    "body_top_right");
                            continue;
                        }
                        if (before.getX() < segment.getX()) {
                            render.put(new Location(segment.getX() * getTileSize(), segment.getY() * getTileSize()),
                                    "body_top_left");
                            continue;
                        }
                    }
                    if(after.getY() < segment.getY()) {
                        if(before.getX() > segment.getX()) {
                            render.put(new Location(segment.getX() * getTileSize(), segment.getY() * getTileSize()),
                                    "body_bottom_right");
                            continue;
                        }
                        if(before.getX() < segment.getX()) {
                            render.put(new Location(segment.getX() * getTileSize(), segment.getY() * getTileSize()),
                                    "body_bottom_left");
                            continue;
                        }
                    }
                    if(after.getY() == segment.getY()) {
                        if(before.getY() < segment.getY()) {
                            if(after.getX() > segment.getX()) {
                                render.put(new Location(segment.getX() * getTileSize(), segment.getY() * getTileSize()),
                                        "body_bottom_right");
                                continue;
                            }
                            if(after.getX() < segment.getX()) {
                                render.put(new Location(segment.getX() * getTileSize(), segment.getY() * getTileSize()),
                                        "body_bottom_left");
                                continue;
                            }
                        }
                        if(before.getY() > segment.getY()) {
                            if(after.getX() > segment.getX()) {
                                render.put(new Location(segment.getX() * getTileSize(), segment.getY() * getTileSize()),
                                        "body_top_right");
                                continue;
                            }
                            render.put(new Location(segment.getX() * getTileSize(), segment.getY() * getTileSize()),
                                    "body_top_left");
                        }
                    }
                }
            }
        }
        // return our render.
        return render;
    }
}
