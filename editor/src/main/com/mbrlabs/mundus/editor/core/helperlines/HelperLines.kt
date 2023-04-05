package com.mbrlabs.mundus.editor.core.helperlines

import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.mbrlabs.mundus.commons.scene3d.components.TerrainComponent
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Disposable
import com.mbrlabs.mundus.editor.events.TerrainAddedEvent
import com.mbrlabs.mundus.editor.events.TerrainRemovedEvent
import com.mbrlabs.mundus.editor.events.TerrainVerticesChangedEvent

class HelperLines : TerrainVerticesChangedEvent.TerrainVerticesChangedEventListener,
        TerrainAddedEvent.TerrainAddedEventListener,
        TerrainRemovedEvent.TerrainRemovedEventListener,
        Disposable {

    private val helperLineObjects = Array<HelperLineObject>()
    private var width = -1
    private var type = HelperLineType.RECTANGLE


    fun build(type: HelperLineType, width: Int, terrainComponents: Array<TerrainComponent>) {
        this.type = type
        this.width = width

        for (terrainComponent in terrainComponents) {
            helperLineObjects.add(HelperLineObject(type, width, terrainComponent))
        }
    }

    fun render(batch: ModelBatch) {
        for (helperLineObject in helperLineObjects) {
            batch.render(helperLineObject.modelInstance)
        }
    }

    fun hasHelperLines() = helperLineObjects.notEmpty()

    override fun onTerrainVerticesChanged(event: TerrainVerticesChangedEvent) {
        helperLineObjects.filter { it.terrainComponent == event.terrainComponent }.forEach { it.updateVertices() }
    }

    override fun onTerrainAdded(event: TerrainAddedEvent) {
        helperLineObjects.add(HelperLineObject(type, width, event.terrainComponent))
    }

    override fun onTerrainRemoved(event: TerrainRemovedEvent) {
        helperLineObjects.filter { it.terrainComponent == event.terrainComponent }.forEach {
            it.dispose()
            helperLineObjects.removeValue(it, true)
        }
    }

    override fun dispose() {
        helperLineObjects.forEach { helperLineObject -> helperLineObject.dispose() }
        helperLineObjects.clear()
    }

}
