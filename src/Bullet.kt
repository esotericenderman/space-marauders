import korlibs.korge.view.*
import korlibs.math.geom.*
import korlibs.math.geom.Circle

private const val SPEED = 20

class Bullet(container: SContainer, private val start: Point, private val direction: Vector2D) {
    private val image = container.circle(radius = 3) {
        anchor(0.5, 0.5)
        position(start)

        addFixedUpdater(FRAME_RATE) {
            pos = pos.plus(direction.normalized * SPEED)
        }
    }
}
