package io.zentae.renderer.layer;

import io.zentae.snake.engine.entity.Location;
import io.zentae.snake.engine.entity.arena.Arena;
import jakarta.annotation.Nonnull;

import java.util.Map;

public abstract class TranslationLayer {

    // the tile size.
    private final int tileSize;
    // the character map.
    private final Map<Class<?>, String> characterMap;

    public TranslationLayer(int tileSize, Map<Class<?>, String> characterMap) {
        this.tileSize = tileSize;
        this.characterMap = characterMap;
    }

    protected int getTileSize() {
        return this.tileSize;
    }

    @Nonnull
    protected Map<Class<?>, String> getCharacterMap() {
        return this.characterMap;
    }

    @Nonnull
    public abstract Map<Location, String> translate(Arena arena);
}
