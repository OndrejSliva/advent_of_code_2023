package day_08

data class NavigationMap(
    val navigationRows: List<NavigationRow>
) {
    private val map: Map<String, NavigationRow> = navigationRows.associateBy { it.position }

    fun countSteps(steps: List<StepDirection>, startLocation: String, endLocation: String): Int {
        var currentLocation = startLocation
        var stepsTraveled = 0
        while (currentLocation != endLocation) {
            val row = map.getValue(currentLocation)
            val currentStepIdx = stepsTraveled % steps.size
            val currentStepDirection = steps[currentStepIdx]
            currentLocation = if(currentStepDirection == StepDirection.RIGHT) row.rightDestination else row.leftDestination
            stepsTraveled += 1
        }
        return stepsTraveled
    }

    fun countSteps2(steps: List<StepDirection>, startLocations: List<String>, endLocations: List<String>): Long {
        val currentLocations = startLocations.toMutableList()
        val endLocationsSet = endLocations.toSet()
        var stepsTraveled: Long = 0

        while (true) {
            for (idx in 0 until currentLocations.size) {
                val currentLocation = currentLocations[idx]
                val row = map.getValue(currentLocation)
                val currentStepIdx = (stepsTraveled % steps.size).toInt()
                val currentStepDirection = steps[currentStepIdx]
                currentLocations[idx] = if(currentStepDirection == StepDirection.RIGHT) row.rightDestination else row.leftDestination
            }
            stepsTraveled += 1
            if (currentLocations.containsAll(endLocationsSet)) {
                break
            }
        }
        return stepsTraveled
    }

    fun countSteps3(steps: List<StepDirection>, startLocations: List<String>, endLocations: List<String>): List<Long> {
        val currentLocations = startLocations.toMutableList()
        val loopsSizes = endLocations.associateWith { 0L }.toMutableMap()
        val loopStarts = endLocations.associateWith { 0L }.toMutableMap()
        var stepsTraveled: Long = 0

        while (true) {
            for (idx in 0 until currentLocations.size) {
                val currentLocation = currentLocations[idx]
                val row = map.getValue(currentLocation)
                val currentStepIdx = (stepsTraveled % steps.size).toInt()
                val currentStepDirection = steps[currentStepIdx]
                currentLocations[idx] = if(currentStepDirection == StepDirection.RIGHT) row.rightDestination else row.leftDestination
                if (endLocations.contains(currentLocation)) {
                    if (loopStarts[currentLocation] == 0L) {
                        loopStarts[currentLocation] = stepsTraveled
                    } else if (loopsSizes[currentLocation] == 0L) {
                        loopsSizes[currentLocation] = stepsTraveled - loopStarts.getValue(currentLocation)
                    }
                }
            }
            stepsTraveled++
            if (loopsSizes.all { it.value != 0L }) {
                println(loopsSizes)
                break
            }
        }
        return loopsSizes.values.toList()
    }
}
