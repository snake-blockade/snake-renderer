package io.zentae.renderer.layer;

import io.zentae.snake.engine.entity.ArenaEntity;
import io.zentae.snake.engine.entity.Location;
import io.zentae.snake.engine.entity.arena.Arena;
import io.zentae.snake.engine.entity.snake.Snake;
import jakarta.annotation.Nonnull;

import java.util.HashMap;
import java.util.Map;

public class DefaultTranslationLayer extends TranslationLayer {

    public DefaultTranslationLayer(int tileSize, @Nonnull Map<Class<?>, Character> characterMap) {
        super(tileSize, characterMap);
    }

    // TODO FAIRE LES VIRAGES DU SERPENT
    @Nonnull
    @Override
    public Map<Location, Character> translate(Arena arena) {
        // prepare our render.
        Map<Location, Character> render = new HashMap<>();
        // do the borders.
        for(int y = 0; y < arena.getWidth(); y++) {
            for(int x = 0; x < arena.getLength(); x++)
                if(x + 1 == arena.getLength()
                        || x == 0
                        || y == 0
                        || y + 1 == arena.getWidth()) {
                    render.put(new Location(x * getTileSize(), y * getTileSize()), 'X');
                } else {
                    render.put(new Location(x * getTileSize(), y * getTileSize()), ' ');
                }

        }
        // loop through each object.
        for(ArenaEntity entity : arena.getObjects()) {
            if(entity instanceof Snake)
                continue;
            // try to retrieve the character associated.
            Character character = getCharacterMap().get(entity.getType());
            // check if there's character mapped.
            if(character == null)
                continue;
            // loop through each segment.
            for(Location segment : entity.getSegments()) {
                render.put(new Location(segment.getX() * getTileSize(), segment.getY() * getTileSize()),
                        character);
            }
        }
        for(Snake snake : arena.getObjects(Snake.class)) {
            // try to retrieve the character associated.
            Character character = getCharacterMap().get(Snake.class);
            // loop through each segment.
            for(Location segment : snake.getBody()) {
                if(snake.getHead().equals(segment)) {
                    render.put(new Location(segment.getX() * getTileSize(), segment.getY() * getTileSize()),
                            'H');
                } else {
                    render.put(new Location(segment.getX() * getTileSize(), segment.getY() * getTileSize()),
                            character);
                }
            }
        }
        // return our render.
        return render;
    }
}
