import korlibs.event.*
import korlibs.image.format.*
import korlibs.io.file.std.*
import korlibs.korge.view.*
import korlibs.math.geom.*

private const val SPEED = 15.0
private const val ROTATION_SCALE = 0.1

private const val MOVEMENT_DISTANCE = 30
private const val MOVEMENT_DISTANCE_SQUARED = MOVEMENT_DISTANCE * MOVEMENT_DISTANCE

private const val SLOW_DOWN_DISTANCE = 120
private const val SLOW_DOWN_DISTANCE_SQUARED = SLOW_DOWN_DISTANCE * SLOW_DOWN_DISTANCE

class Ship(private val main: Stage, private val container: SContainer) {
    private lateinit var image: Image

    suspend fun init(): Ship {
        image = container.image(resourcesVfs["ship.png"].readBitmap()) {
            anchor(.5, 0)
            scale(0.2)
            position(main.windowBounds.center)
        }

        image.addFixedUpdater(FRAME_RATE) { move() }
        image.addFixedUpdater(FRAME_RATE) {
            if (main.input.mouseButtonPressed(MouseButton.LEFT)) {
                fire()
            }
        }

        return this
    }

    private fun move() {
        val direction = main.mousePos.minus(image.pos)

        val currentAngle = image.rotation
        val targetAngle = direction.angle.plus(Angle.QUARTER)

        val difference = currentAngle.shortDistanceTo(targetAngle)

        image.rotation += difference * ROTATION_SCALE

        val distanceSquared = direction.lengthSquared

        if (distanceSquared < MOVEMENT_DISTANCE_SQUARED) return

        val speed = if (distanceSquared > SLOW_DOWN_DISTANCE_SQUARED) SPEED else SPEED * distanceSquared / SLOW_DOWN_DISTANCE_SQUARED

        val newHeading = Vector2D.polar(image.rotation.minus(Angle.QUARTER))
        image.pos = image.pos.plus(newHeading * speed)
    }

    private fun fire() {
        Bullet(main, container, image.pos, Vector2D.polar(image.rotation.minus(Angle.QUARTER)))
    }
}
