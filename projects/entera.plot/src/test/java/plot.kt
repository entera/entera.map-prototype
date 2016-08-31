import javafx.geometry.BoundingBox
import javafx.geometry.Bounds
import javafx.scene.Scene
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import javafx.scene.shape.Line
import javafx.scene.shape.Rectangle
import javafx.stage.Stage
import org.testfx.api.FxToolkit
import org.testfx.util.WaitForAsyncUtils
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    setupStage { stage ->
        val gridColor = Color.gray(0.95).interpolate(Color.BLUE, 0.05)

        val canvas = AnchorPane()
        canvas.style = "-fx-background-color: white;"

        val region = bounds(100.0, 100.0, 300.0, 300.0)

        canvas.children += Rectangle(region.minX, region.minY, region.width, region.height)
            .apply { fill = gridColor }

        canvas.children += Line(250.0 - 0.5, 0.0, 250.0 - 0.5, 500.0)
            .apply { stroke = Color.WHITE; opacity = 1.0 }
        canvas.children += Line(0.0, 250.0 - 0.5, 500.0, 250.0 - 0.5)
            .apply { stroke = Color.WHITE }

        stage.scene = Scene(canvas, 500.0, 500.0)
    }
}

private fun bounds(minX: Double, minY: Double,
                   width: Double, height: Double): Bounds =
    BoundingBox(minX, minY, width, height)

private fun setupStage(action: (stage: Stage) -> Unit) {
    FxToolkit.registerPrimaryStage()
    val stage = FxToolkit.registerStage { Stage() }
    FxToolkit.setupStage { stage -> action(stage) }
    FxToolkit.showStage()
    WaitForAsyncUtils.waitFor(1, TimeUnit.HOURS) { !stage.isShowing }
}
