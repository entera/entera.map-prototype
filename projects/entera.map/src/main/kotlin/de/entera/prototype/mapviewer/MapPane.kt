package de.entera.prototype.mapviewer

import javafx.collections.FXCollections.observableArrayList
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.Group
import javafx.scene.layout.Pane
import javafx.scene.layout.Region
import javafx.scene.layout.StackPane

class MapPane : Pane() {

    val viewportContainer: ViewportContainer
    val viewport: Viewport

    val eventRegion: EventRegion
    val layerStack: LayerStack

    val layers: ObservableList<Layer> = observableArrayList()

    init {
        viewportContainer = ViewportContainer().apply {
            id = "container"
            styleClass += "map-view"
            alignment = Pos.TOP_LEFT
        }
        viewport = Viewport().apply {
            id = "map"
        }

        eventRegion = EventRegion()
        layerStack = LayerStack()

        children += viewportContainer
        viewportContainer.children += viewport

        layers.addListener(ListChangeListener { change ->
            updateLayers()
        })
        updateLayers()
    }

    private fun updateLayers() {
        viewport.children.setAll(layers)
        layers.forEach { it.isManaged = false }
    }

}

class Viewport : StackPane()
class ViewportContainer : StackPane()

class EventRegion : Region()
class LayerStack : StackPane()
class Layer : Group()
