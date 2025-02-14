import korlibs.korge.*
import korlibs.korge.scene.*
import korlibs.korge.view.*
import korlibs.image.color.*
import korlibs.math.geom.*

suspend fun main() = Korge(windowSize = Size(1920, 1080), backgroundColor = Colors["#2b2b2b"], title = "Space Marauders") {
    val sceneContainer = sceneContainer()

	sceneContainer.changeTo { GameScene(this@Korge) }
}

class GameScene(private val main: Stage) : Scene() {
	override suspend fun SContainer.sceneMain() {
        val ship = Ship(main, this).init()
	}
}
