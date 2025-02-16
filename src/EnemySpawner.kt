import korlibs.korge.view.*
import korlibs.math.geom.*
import korlibs.time.*
import kotlin.random.*

class EnemySpawner(private val main: Stage, private val container: GameScene) {

    init {
        container.sceneContainer.addFixedUpdater(0.2.timesPerSecond) {
            val center = main.windowBounds.center

            val angle = Angle.fromDegrees(Random.nextDouble() * 360)
            val vector = Vector2D.polar(angle, 2200.0)

            val position = center + vector

            Rusher(main, container).init(position)
        }
    }
}
