package io.zentae.renderer;

import io.zentae.snake.engine.entity.Location;
import jakarta.annotation.Nonnull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class Renderer {

    // the translation map.
    private static final Map<String, BiConsumer<Graphics, Location>> TRANSLATION_MAP = new HashMap<>();

    public static void register(@Nonnull String string, @Nonnull BiConsumer<Graphics, Location> locationConsumer) {
        TRANSLATION_MAP.put(string, locationConsumer);
    }

    public static void render(String string, Graphics graphics, Location location) {
        if(string == null)
            return;
        BiConsumer<Graphics, Location> locationConsumer = TRANSLATION_MAP.get(string);
        // check if there's no such consumer.
        if(locationConsumer == null)
            throw new RuntimeException(String.format("String '%s' not mapped.", string));
        locationConsumer.accept(graphics, location);
    }
}
