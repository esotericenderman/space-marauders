import korlibs.image.bitmap.*
import korlibs.image.format.*
import korlibs.io.file.std.*
import korlibs.korge.view.*

class Ship(private val main: Stage, private val container: SContainer) {
    private lateinit var image: Bitmap

    suspend fun init(): Ship {
        image = resourcesVfs["ship.png"].readBitmap()

        val rendered = container.image(image) {
            anchor(.5, .5)
            scale(0.8)
        }

        rendered.addUpdater {
            x = main.mousePos.x
            y = main.mousePos.y
        }

        return this
    }
}
