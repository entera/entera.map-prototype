import javafx.event.EventHandler
import javafx.geometry.BoundingBox
import javafx.geometry.Bounds
import javafx.geometry.Point2D
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Line
import javafx.scene.shape.Rectangle
import javafx.stage.Stage
import org.testfx.api.FxToolkit
import org.testfx.util.WaitForAsyncUtils
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    setupStage { stage ->
        // canvas.
        val canvas = Pane()
        canvas.style = "-fx-background-color: white;"

        // plot.
        val plot = plot()
        canvas.children += plot

        // grid.
        val gridRegion = bounds(100.0, 100.0, 300.0, 300.0)
        val gridColor = Color.gray(0.95).interpolate(Color.BLUE, 0.05)

        listOf(gridRegion)
            .map { Rectangle(it.minX, it.minY, it.width, it.height) }
            .map { it.fill = gridColor; it }
            .toCollection(plot.children)
        listOf(0.25, 0.5, 0.75)
            .map { gridRegion.interpolate(point(it, 1.0)) }
            .map { Line(it.x - 0.5, 0.0, it.x - 0.5, it.y ) }
            .map { it.stroke = Color.WHITE; it }
            .toCollection(plot.children)
        listOf(0.25, 0.5, 0.75)
            .map { gridRegion.interpolate(point(1.0, it)) }
            .map { Line(0.0, it.y - 0.5, it.x, it.y - 0.5 ) }
            .map { it.stroke = Color.WHITE; it }
            .toCollection(plot.children)

        // scene.
        stage.scene = Scene(canvas, 500.0, 500.0)
        stage.scene.onKeyPressed = EventHandler {
            when(it.code) { KeyCode.ESCAPE, KeyCode.Q -> stage.close() }
        }
    }
}

fun plot() = Plot()
class Plot : Pane()

private fun point(x: Double, y: Double) = Point2D(x, y)
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

private fun ClosedRange<Double>.interpolate(factor: Double): Double =
    start + ((endInclusive - start) * factor)

private fun Bounds.interpolate(factor: Point2D): Point2D = Point2D(
    (minX..maxX).interpolate(factor.x),
    (minY..maxY).interpolate(factor.y)
)
