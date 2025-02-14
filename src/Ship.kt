import korlibs.image.bitmap.*
import korlibs.image.format.*
import korlibs.io.file.std.*
import korlibs.korge.view.*
import korlibs.math.geom.*
import korlibs.time.*
import kotlin.math.*

class Ship(private val main: Stage, private val container: SContainer) {
    private lateinit var image: Bitmap

    suspend fun init(): Ship {
        image = resourcesVfs["ship.png"].readBitmap()

        val rendered = container.image(image) {
            anchor(.5, .5)
            scale(0.2)
            position(256, 256)
        }

        rendered.addFixedUpdater(60.timesPerSecond) {
            val direction = main.mousePos.minus(pos)

            val currentAngle = rendered.rotation
            val targetAngle = direction.angle.plus(Angle.QUARTER)

            val difference = currentAngle.shortDistanceTo(targetAngle)

            rendered.rotation += difference * 0.1

            val distanceSquared = direction.lengthSquared

            if (distanceSquared < 800) return@addFixedUpdater

            val speed = if (distanceSquared > 15000) 15.0 else 15.0 * distanceSquared / 15000

            println(speed)

            val newHeading = Vector2D.polar(rendered.rotation.minus(Angle.QUARTER))
            pos = pos.plus(newHeading * speed)
        }

        return this
    }
}
