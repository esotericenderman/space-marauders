import korlibs.image.color.*
import korlibs.image.vector.*
import korlibs.korge.view.*
import korlibs.math.geom.*
import korlibs.time.*

private const val TARGET_DISTANCE = 450
private const val TARGET_DISTANCE_SQUARED = TARGET_DISTANCE * TARGET_DISTANCE

class Rusher(private val main: Stage, private val container: GameScene) {
    private lateinit var image: Graphics

    fun init() {
        val builder = ShapeBuilder(256, 256)

        val yellow = RGBA.Companion.float(0.75, 0.75, 0.25, 1.0)

        builder.regularPolygon(3, 128.0)
        builder.createColor(yellow)
        builder.fill(yellow)

        val built = builder.buildShape()

        image = container.sceneContainer.graphics(built) {
            anchor(0.0, 0.0)
            scale(0.2)
            position(main.windowBounds.center)
        }

        image.addFixedUpdater(FRAME_RATE) {
            rotate()
        }

        image.addFixedUpdater(FRAME_RATE) {
            move()
        }

        image.addFixedUpdater(0.1.timesPerSecond) {
            shoot()
        }
    }

    private fun rotate() {
        val shipPosition = container.ship.image.pos
        val currentPos = image.pos

        val direction = shipPosition - currentPos

        image.rotation = direction.angle.plus(Angle.QUARTER)
    }

    private fun move() {
        val shipPosition = container.ship.image.pos
        val currentPos = image.pos

        val direction = shipPosition - currentPos

        val target = shipPosition - direction.normalized * TARGET_DISTANCE
        val movementDirection = target - currentPos

        val distanceToTargetSquared = movementDirection.lengthSquared

        if (distanceToTargetSquared < 200) return

        image.pos += movementDirection.normalized * 5
    }

    private fun shoot() {
        Bullet(main, container.sContainer, image.pos, Vector2D.polar(image.rotation.minus(Angle.QUARTER)))
    }
}
