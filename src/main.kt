import korlibs.korge.*
import korlibs.korge.scene.*
import korlibs.korge.view.*
import korlibs.image.color.*
import korlibs.math.geom.*

suspend fun main() = Korge(windowSize = Size(512, 512), backgroundColor = Colors["#2b2b2b"]) {
    val sceneContainer = sceneContainer()

	sceneContainer.changeTo { GameScene() }
}

class GameScene : Scene() {
	override suspend fun SContainer.sceneMain() {
        val ship = Ship(this).init()
	}
}
