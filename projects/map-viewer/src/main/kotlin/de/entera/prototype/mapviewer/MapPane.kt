package de.entera.prototype.mapviewer

import javafx.collections.FXCollections.observableArrayList
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.Group
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane

class MapPane : Pane() {

    val layers: ObservableList<Group> = observableArrayList()

    val stackPane: StackPane
    val anchorPane: AnchorPane

    init {
        stackPane = StackPane().apply {
            id = "container"
            styleClass += "map-view"
            alignment = Pos.TOP_LEFT
        }

        anchorPane = AnchorPane().apply {
            id = "map"
        }

        stackPane.children += anchorPane
        children += stackPane

        layers.addListener(ListChangeListener { change ->
            updateLayers()
        })
        updateLayers()
    }

    private fun updateLayers() {
        anchorPane.children.setAll(layers)
        layers.forEach { it.isManaged = false }
    }

}
