package io.zentae.renderer;

import io.zentae.snake.engine.entity.Location;
import jakarta.annotation.Nonnull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class Renderer {

    // the translation map.
    private static final Map<Character, BiConsumer<Graphics, Location>> TRANSLATION_MAP = new HashMap<>();

    public static void register(@Nonnull Character character, @Nonnull BiConsumer<Graphics, Location> locationConsumer) {
        TRANSLATION_MAP.put(character, locationConsumer);
    }

    public static void render(Character character, Graphics graphics, Location location) {
        if(character == null)
            return;
        BiConsumer<Graphics, Location> locationConsumer = TRANSLATION_MAP.get(character);
        // check if there's no such consumer.
        if(locationConsumer == null)
            throw new RuntimeException(String.format("Character '%s' not mapped.", character));
        locationConsumer.accept(graphics, location);
    }
}
