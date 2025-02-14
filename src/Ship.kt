import korlibs.image.bitmap.*
import korlibs.image.format.*
import korlibs.io.file.std.*
import korlibs.korge.view.*

class Ship(private val container: SContainer) {
    private val x: Int = 0
    private val y: Int = 0

    lateinit var image: Bitmap

    suspend fun init(): Ship {
        image = resourcesVfs["ship.png"].readBitmap()

        container.image(image) {
            anchor(.5, .5)
            scale(0.8)
            position(256, 256)
        }

        return this
    }
}
