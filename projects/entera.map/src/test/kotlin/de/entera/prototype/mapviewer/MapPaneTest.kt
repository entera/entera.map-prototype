package de.entera.prototype.mapviewer

import javafx.scene.Scene
import kotlin.test.assertTrue

import org.junit.Before
import org.junit.Test
import org.testfx.api.FxRobot
import org.testfx.api.FxToolkit.registerPrimaryStage
import org.testfx.api.FxToolkit.setupScene
import org.testfx.api.FxToolkit.showStage

class MainPaneTest : FxRobot() {

    val primaryStage = registerPrimaryStage()

    lateinit var mapPane: MapPane

    @Before
    fun setup() {
        setupScene {
            mapPane = MapPane()
            Scene(mapPane, 600.0, 600.0)
        }
        showStage()
    }

    @Test
    fun smoke_test() {
        assertTrue { mapPane.viewportContainer is ViewportContainer }
        assertTrue { mapPane.viewport is Viewport }
    }

}
