package de.entera.prototype.mapviewer

import javafx.application.Application
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import javafx.stage.Window

fun main(args: Array<String>) {
    Application.launch(MainApp::class.java)
}

class MainApp : Application() {

    override fun start(stage: Stage) {
        val rootPane = StackPane()
        stage.scene = Scene(rootPane, 600.0, 600.0)
        stage.title = javaClass.canonicalName
        registerKeyboardHandlers(stage.scene)
        stage.setOnShown { event -> initRootPane(rootPane) }
        stage.show()
    }

    private fun initRootPane(rootPane: Pane) {
        val stackPane = StackPane().apply {
            alignment = Pos.CENTER
        }
        stackPane.children.add(Label(javaClass.simpleName))
        rootPane.children.add(stackPane)
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

}
