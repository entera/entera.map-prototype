package de.entera.prototype.mapviewer

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.Geometry
import com.vividsolutions.jts.geom.LineString
import com.vividsolutions.jts.geom.MultiLineString
import com.vividsolutions.jts.geom.MultiPoint
import com.vividsolutions.jts.geom.MultiPolygon
import com.vividsolutions.jts.geom.Point
import com.vividsolutions.jts.geom.Polygon
import javafx.scene.shape.LineTo
import javafx.scene.shape.MoveTo
import javafx.scene.shape.Path
import javafx.scene.shape.Shape

interface ShapeRenderer {
    fun geometry(geometry: Geometry): Shape

    fun point(point: Point): Shape
    fun lineString(lineString: LineString): Shape
    fun polygon(polygon: Polygon): Shape

    fun multiPoint(multiPoint: MultiPoint): Shape
    fun multiLineString(multiLineString: MultiLineString): Shape
    fun multiPolygon(multiPolygon: MultiPolygon): Shape
}

class ShapeRendererImpl : ShapeRenderer {
    override fun geometry(geometry: Geometry): Shape = when (geometry) {
        is Point -> point(geometry)
        is LineString -> lineString(geometry)
        is Polygon -> polygon(geometry)
        is MultiPoint -> multiPoint(geometry)
        is MultiLineString -> multiLineString(geometry)
        is MultiPolygon -> multiPolygon(geometry)
        else -> throw IllegalArgumentException()
    }

    override fun point(point: Point): Shape {
        throw UnsupportedOperationException()
    }

    override fun lineString(lineString: LineString): Shape {
        throw UnsupportedOperationException()
    }

    override fun polygon(polygon: Polygon): Shape {
        val path = Path()
        drawPolygonCoordinates(path, polygon.exteriorRing.coordinates)
        return path
    }

    override fun multiPoint(multiPoint: MultiPoint): Shape {
        throw UnsupportedOperationException()
    }

    override fun multiLineString(multiLineString: MultiLineString): Shape {
        throw UnsupportedOperationException()
    }

    override fun multiPolygon(multiPolygon: MultiPolygon): Shape {
        val path = Path()
        for (index in 0 until multiPolygon.numGeometries) {
            val polygon = multiPolygon.getGeometryN(index) as Polygon
            drawPolygonCoordinates(path, polygon.exteriorRing.coordinates)
        }
        return path
    }

    private fun drawPolygonCoordinates(path: Path, coordinates: Array<Coordinate>) {
        path.elements += MoveTo(coordinates[0].x, coordinates[0].y)
        for (index in 1 until coordinates.size) {
            path.elements += LineTo(coordinates[index].x, coordinates[index].y)
        }
        path.elements += LineTo(coordinates[0].x, coordinates[0].y)
    }
}
