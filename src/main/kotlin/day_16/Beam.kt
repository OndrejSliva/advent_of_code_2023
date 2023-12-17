package day_16

data class Beam(
    val position: MapIndex,
    val direction: Direction,
) {
    fun move(mapCharacter: Char): List<Beam> = when (direction) {
            Direction.RIGHT -> moveRight(mapCharacter)
            Direction.LEFT -> moveLeft(mapCharacter)
            Direction.UP -> moveUp(mapCharacter)
            Direction.DOWN -> moveDown(mapCharacter)
        }

    private fun moveRight(mapCharacter: Char): List<Beam> {
        if (mapCharacter == '.' || mapCharacter == '-') {
            return listOf(Beam(position.right(), Direction.RIGHT))
        }
        if (mapCharacter == '|') {
            return listOf(Beam(position.up(), Direction.UP), Beam(position.down(), Direction.DOWN))
        }
        if (mapCharacter == '/') {
            return listOf(Beam(position.up(), Direction.UP))
        }
        if (mapCharacter == '\\') {
            return listOf(Beam(position.down(), Direction.DOWN))
        }
        throw Exception("Invalid map character: [$mapCharacter]")
    }

    private fun moveLeft(mapCharacter: Char): List<Beam> {
        if (mapCharacter == '.' || mapCharacter == '-') {
            return listOf(Beam(position.left(), Direction.LEFT))
        }
        if (mapCharacter == '|') {
            return listOf(Beam(position.up(), Direction.UP), Beam(position.down(), Direction.DOWN))
        }
        if (mapCharacter == '/') {
            return listOf(Beam(position.down(), Direction.DOWN))
        }
        if (mapCharacter == '\\') {
            return listOf(Beam(position.up(), Direction.UP))
        }
        throw Exception("Invalid map character: [$mapCharacter]")
    }

    private fun moveUp(mapCharacter: Char): List<Beam> {
        if (mapCharacter == '.' || mapCharacter == '|') {
            return listOf(Beam(position.up(), Direction.UP))
        }
        if (mapCharacter == '-') {
            return listOf(Beam(position.left(), Direction.LEFT), Beam(position.right(), Direction.RIGHT))
        }
        if (mapCharacter == '/') {
            return listOf(Beam(position.right(), Direction.RIGHT))
        }
        if (mapCharacter == '\\') {
            return listOf(Beam(position.left(), Direction.LEFT))
        }
        throw Exception("Invalid map character: [$mapCharacter]")
    }

    private fun moveDown(mapCharacter: Char): List<Beam> {
        if (mapCharacter == '.' || mapCharacter == '|') {
            return listOf(Beam(position.down(), Direction.DOWN))
        }
        if (mapCharacter == '-') {
            return listOf(Beam(position.left(), Direction.LEFT), Beam(position.right(), Direction.RIGHT))
        }
        if (mapCharacter == '/') {
            return listOf(Beam(position.left(), Direction.LEFT))
        }
        if (mapCharacter == '\\') {
            return listOf(Beam(position.right(), Direction.RIGHT))
        }
        throw Exception("Invalid map character: [$mapCharacter]")
    }

}
