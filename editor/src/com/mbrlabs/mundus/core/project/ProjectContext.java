package com.mbrlabs.mundus.core.project;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;
import com.mbrlabs.mundus.core.model.PersistableModel;
import com.mbrlabs.mundus.core.model.PersistableModelInstance;
import com.mbrlabs.mundus.terrain.Terrain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcus Brummer
 * @version 28-11-2015
 */
public class ProjectContext implements Disposable {

    public ProjectRef ref = null;

    public Environment environment = new Environment();
    public List<PersistableModelInstance> entities;
    @Tag(0)
    public List<PersistableModel> models;

    public List<Terrain> terrains;
    @Tag(1)
    private long nextAvailableID;

    public ProjectContext() {
        entities = new ArrayList<>();
        models = new ArrayList<>();
        terrains = new ArrayList<Terrain>();
        nextAvailableID = 0;

        PointLight light = new PointLight();
        light.setPosition(400,300,400);
        light.setIntensity(1);
        environment.add(light);
    }

    public void copyFrom(ProjectContext other) {
        ref = other.ref;
        environment = other.environment;
        entities = other.entities;
        models = other.models;
        terrains = other.terrains;
        nextAvailableID = other.nextAvailableID;
    }

    public synchronized long obtainAvailableID() {
        nextAvailableID += 1;
        return nextAvailableID - 1;
    }

    public synchronized long getNextAvailableID() {
        return nextAvailableID;
    }

    @Override
    public void dispose() {
        for(PersistableModel model : models) {
            model.getModel().dispose();
        }
        models = null;
    }

}