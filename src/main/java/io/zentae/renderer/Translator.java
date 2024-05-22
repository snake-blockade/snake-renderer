package io.zentae.renderer;

import io.zentae.renderer.layer.TranslationLayer;
import jakarta.annotation.Nonnull;

import java.util.HashMap;
import java.util.Map;

public class Translator {

    // renderer cache.
    private static final Map<Class<? extends TranslationLayer>, TranslationLayer> RENDER_LAYER_MAP = new HashMap<>();

    public static void register(TranslationLayer layer) {
        // register the layer.
        RENDER_LAYER_MAP.put(layer.getClass(), layer);
    }

    public static TranslationLayer getTranslationLayer(@Nonnull Class<? extends TranslationLayer> layerClass) {
        // try to retrieve the layer.
        TranslationLayer layer = RENDER_LAYER_MAP.get(layerClass);
        // check if there's no such render layer.
        if(layer == null)
            throw new RuntimeException("Unknown render layer.");
        // return the render.
        return layer;
    }
}
