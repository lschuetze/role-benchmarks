import scala.util.boundary, boundary.break

abstract class Benchmark {

  def benchmark(): Object
  def verifyResult(result: Object): Boolean
  def setUp(innerIterations: Int): Boolean

  def innerBenchmarkLoop(innerIterations: Int): Boolean = {
    boundary:
      for (i <- 1 to innerIterations)
        if !verifyResult(benchmark()) then break()

    return true
  }

}
