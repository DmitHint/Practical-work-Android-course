package com.example.dependency


class BicycleFactory(
    private val wheelDealer: WheelDealer,
    private val frameFactory: FrameFactory,
) {

    fun build(logo: String, color: Int): Bicycle {
        return Bicycle(
            Pair(wheelDealer.getWheel(), wheelDealer.getWheel()),
            frameFactory.getFrame(color),
            logo,
        )
    }

}
