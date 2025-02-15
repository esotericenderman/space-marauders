import korlibs.event.*
import korlibs.korge.*
import korlibs.korge.scene.*
import korlibs.korge.view.*
import korlibs.image.color.*
import korlibs.korge.input.*
import korlibs.math.geom.*
import korlibs.time.*

val FRAME_RATE = 60.timesPerSecond

suspend fun main() = Korge(windowSize = Size(1920, 1080), backgroundColor = Colors["#2b2b2b"], title = "Space Marauders") {
    val sceneContainer = sceneContainer()

	sceneContainer.changeTo { GameScene(this@Korge) }
}

class GameScene(private val main: Stage) : Scene() {
    lateinit var ship: Ship

	override suspend fun SContainer.sceneMain() {
        ship = Ship(main, this).init()
        val spawner = EnemySpawner(main, this@GameScene)

        addFixedUpdater(FRAME_RATE) {
            if (input.keys.pressing(Key.ESCAPE)) {
                main.gameWindow.close()
            }
        }
	}
}
