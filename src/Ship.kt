import korlibs.event.*
import korlibs.image.color.*
import korlibs.image.vector.*
import korlibs.korge.view.*
import korlibs.math.geom.*

private const val FORWARD_SPEED = 15.0
private const val BACKWARD_SPEED = -5
private const val SIDEWAYS_SPEED = 7.5

private const val ROTATION_SCALE = 0.1

private const val MOVEMENT_DISTANCE = 30
private const val MOVEMENT_DISTANCE_SQUARED = MOVEMENT_DISTANCE * MOVEMENT_DISTANCE

private const val SLOW_DOWN_DISTANCE = 120
private const val SLOW_DOWN_DISTANCE_SQUARED = SLOW_DOWN_DISTANCE * SLOW_DOWN_DISTANCE

class Ship(private val main: Stage, private val container: SContainer) {
    private lateinit var image: Graphics

    fun init(): Ship {
        val shape = ShapeBuilder(256 ,256)

        shape.polygon(Point(0, 256), Point(128, 128), Point(256, 256), Point(128, 0))
        shape.createColor(RGBA.Companion.float(255.0, 255.0, 255.0, 1.0))
        shape.fill(RGBA.Companion.float(255.0, 255.0, 255.0, 1.0))

        val built = shape.buildShape()

        image = container.graphics(built) {
            anchor(0.5, 0)
            scale(0.2)
            position(main.windowBounds.center)
        }

        image.addFixedUpdater(FRAME_RATE) {
            rotate()

            val keys = main.input.keys

            if (keys.pressing(Key.W)) {
                moveForward()
            }

            if (keys.pressing(Key.S)) {
                moveBackward()
            }

            val movingRight = keys.pressing(Key.D)
            val movingLeft = keys.pressing(Key.A)

            if (movingRight) {
                if (movingLeft) {
                    return@addFixedUpdater
                }

                moveRight()
            } else if (movingLeft) {
                moveLeft()
            }
        }
        image.addFixedUpdater(FRAME_RATE) {
            if (main.input.mouseButtonPressed(MouseButton.LEFT)) {
                fire()
            }
        }

        return this
    }

    private fun rotate() {
        val direction = main.mousePos.minus(image.pos)

        val currentAngle = image.rotation
        val targetAngle = direction.angle.plus(Angle.QUARTER)

        val difference = currentAngle.shortDistanceTo(targetAngle)

        image.rotation += difference * ROTATION_SCALE
    }

    private fun moveForward() {
        val direction = main.mousePos.minus(image.pos)

        val distanceSquared = direction.lengthSquared

        if (distanceSquared < MOVEMENT_DISTANCE_SQUARED) return

        val speed = if (distanceSquared > SLOW_DOWN_DISTANCE_SQUARED) FORWARD_SPEED else FORWARD_SPEED * distanceSquared / SLOW_DOWN_DISTANCE_SQUARED

        val newHeading = Vector2D.polar(image.rotation.minus(Angle.QUARTER))
        image.pos = image.pos.plus(newHeading * speed)
    }

    private fun moveBackward() {
        val speed = BACKWARD_SPEED

        val newHeading = Vector2D.polar(image.rotation.minus(Angle.QUARTER))
        image.pos = image.pos.plus(newHeading * speed)
    }

    private fun moveRight() {
        val newHeading = Vector2D.polar(image.rotation.minus(Angle.HALF))

        image.pos = image.pos.plus(newHeading * SIDEWAYS_SPEED)
    }

    private fun moveLeft() {
        val newHeading = Vector2D.polar(image.rotation)

        image.pos = image.pos.plus(newHeading * SIDEWAYS_SPEED)
    }

    private fun fire() {
        Bullet(main, container, image.pos, Vector2D.polar(image.rotation.minus(Angle.QUARTER)))
    }
}
