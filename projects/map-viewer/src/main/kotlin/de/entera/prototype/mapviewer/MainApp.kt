package de.entera.prototype.mapviewer

import javafx.application.Application
import javafx.collections.FXCollections.observableArrayList
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.stage.Stage
import javafx.stage.Window
import java.io.File

fun main(args: Array<String>) {
    Application.launch(MainApp::class.java)
}

class MainApp : Application() {

    lateinit var rootDirectory: File

    override fun init() {
        rootDirectory = File("contrib/TM_WORLD_BORDERS_SIMPL")
    }

    override fun start(stage: Stage) {
        val rootPane = StackPane()
        stage.scene = Scene(rootPane, 300.0 + 600.0, 600.0)
        stage.title = javaClass.canonicalName
        registerKeyboardHandlers(stage.scene)
        stage.setOnShown { event -> initRootPane(rootPane) }
        stage.show()
    }

    private fun initRootPane(rootPane: Pane) {
        val borderPane = BorderPane()
        rootPane.children += borderPane

        val layerPane = buildLayerPane()
        borderPane.left = layerPane
        val mapPane = buildMapPane()
        borderPane.center = mapPane
    }

    private fun buildLayerPane(): ListView<Any> {
        val listView = ListView<Any>().apply {
            prefWidth = 300.0
        }
        val shapefiles = rootDirectory.listFiles { file -> file.name.endsWith(".shp") }
        listView.items = observableArrayList(shapefiles.toList())
        listView.setCellFactory {
            createListCell {
                val item = item
                text = when (item) {
                    is File -> item.name
                    is Any -> item.toString()
                    else -> null
                }
            }
        }
        return listView
    }

    private fun buildMapPane(): MapPane {
        return MapPane().apply {
            layers += Group().apply {
                children += Rectangle(100.0, 100.0, 200.0, 200.0).apply {
                    fill = Color.TOMATO
                }
            }
        }
    }

    private fun registerKeyboardHandlers(scene: Scene) {
        scene.setOnKeyReleased {
            when (it.code) {
                KeyCode.ESCAPE -> closeWindow(scene.window)
                else -> Unit
            }
        }
    }

    private fun closeWindow(window: Window) = window.hide()

    private fun <T> createListCell(updateItem: (ListCell<T>.() -> Unit)): ListCell<T> {
        return object : ListCell<T>() {
            override fun updateItem(item: T?, empty: Boolean) {
                super.updateItem(item, empty)
                updateItem(this)
            }
        }
    }

}
