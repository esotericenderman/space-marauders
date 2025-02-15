import korlibs.korge.view.*
import korlibs.time.*

class EnemySpawner(private val main: Stage, private val container: GameScene) {

    init {
        container.sceneContainer.addFixedUpdater(0.2.timesPerSecond) {
            Rusher(main, container).init()
        }
    }
}
