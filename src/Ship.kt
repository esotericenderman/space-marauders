import korlibs.image.bitmap.*
import korlibs.image.format.*
import korlibs.io.file.std.*
import korlibs.korge.view.*
import korlibs.math.geom.*
import korlibs.time.*

private const val SPEED = 15.0
private const val ROTATION_SCALE = 0.1

private const val MOVEMENT_DISTANCE = 30
private const val MOVEMENT_DISTANCE_SQUARED = MOVEMENT_DISTANCE * MOVEMENT_DISTANCE

private const val SLOW_DOWN_DISTANCE = 120
private const val SLOW_DOWN_DISTANCE_SQUARED = SLOW_DOWN_DISTANCE * SLOW_DOWN_DISTANCE

class Ship(private val main: Stage, private val container: SContainer) {
    private lateinit var image: Bitmap

    suspend fun init(): Ship {
        image = resourcesVfs["ship.png"].readBitmap()

        val rendered = container.image(image) {
            anchor(.5, 0)
            scale(0.2)
            position(256, 256)
        }

        rendered.addFixedUpdater(60.timesPerSecond) {
            val direction = main.mousePos.minus(pos)

            val currentAngle = rendered.rotation
            val targetAngle = direction.angle.plus(Angle.QUARTER)

            val difference = currentAngle.shortDistanceTo(targetAngle)

            rendered.rotation += difference * ROTATION_SCALE

            val distanceSquared = direction.lengthSquared

            if (distanceSquared < MOVEMENT_DISTANCE_SQUARED) return@addFixedUpdater

            val speed = if (distanceSquared > SLOW_DOWN_DISTANCE_SQUARED) SPEED else SPEED * distanceSquared / SLOW_DOWN_DISTANCE_SQUARED

            println(speed)

            val newHeading = Vector2D.polar(rendered.rotation.minus(Angle.QUARTER))
            pos = pos.plus(newHeading * speed)
        }

        return this
    }
}
