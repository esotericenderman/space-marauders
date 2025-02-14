import korlibs.image.bitmap.*
import korlibs.image.format.*
import korlibs.io.file.std.*
import korlibs.korge.view.*
import korlibs.math.geom.*
import korlibs.time.*

class Ship(private val main: Stage, private val container: SContainer) {
    private lateinit var image: Bitmap

    suspend fun init(): Ship {
        image = resourcesVfs["ship.png"].readBitmap()

        val rendered = container.image(image) {
            anchor(.5, .5)
            scale(0.2)
            position(256, 256)
        }

        rendered.addFixedUpdater(60.timesPerSecond) {
            val direction = main.mousePos.minus(pos)
            rendered.rotation = direction.angle.plus(Angle.QUARTER)

            if (direction.lengthSquared < 800) return@addFixedUpdater

            pos = pos.plus(direction.normalized.times(15))
        }

        return this
    }
}
