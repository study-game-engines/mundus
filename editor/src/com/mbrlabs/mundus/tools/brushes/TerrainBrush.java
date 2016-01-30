package com.mbrlabs.mundus.tools.brushes;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.mbrlabs.mundus.commons.terrain.Terrain;
import com.mbrlabs.mundus.core.project.ProjectContext;
import com.mbrlabs.mundus.tools.Tool;

/**
 * @author Marcus Brummer
 * @version 30-01-2016
 */
public abstract class TerrainBrush extends Tool {

    /**
     *
     */
    public enum BrushMode {
        RAISE_LOWER, PAINT_HEIGHT, SMOOTH, PAINT
    }

    /**
     *
     */
    public class ModeNotSupportedException extends Exception {
        public ModeNotSupportedException(String message) {
            super(message);
        }
    }

    protected BrushMode mode;
    protected Terrain terrain;
    protected float radius;

    public TerrainBrush(ProjectContext projectContext, Shader shader, ModelBatch batch) {
        super(projectContext, shader, batch);
    }

    public BrushMode getMode() {
        return mode;
    }

    public void setMode(BrushMode mode) throws ModeNotSupportedException {
        if(!supportsMode(mode)) {
            throw new ModeNotSupportedException(getName() + " does not support " + mode);
        }
        this.mode = mode;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void scale(float amount) {
        radius *= amount;
    }

    public abstract boolean supportsMode(BrushMode mode);


}