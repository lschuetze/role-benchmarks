class Run(rname: String) {
  val name            = rname
  val benchmarkSuite  = getSuiteFromName(name)
  var iterations      = 1
  var innerIterations = 1
  var total: Long     = 0

  def getSuiteFromName[T <: Benchmark](name: String): Class[T] =
    try
      Class.forName(name).asInstanceOf[Class[T]]
    catch {
      case e: ClassNotFoundException =>
        e.printStackTrace()
        throw new RuntimeException(e)
    }

  def runBenchmark() = {
    println("Starting " + name + " benchmark " + innerIterations + " times ...")
    try
      doRuns(benchmarkSuite.newInstance())
    catch {
      case e @ (_: ReflectiveOperationException | _: IllegalArgumentException | _: SecurityException) =>
        e.printStackTrace()
        throw new RuntimeException(e)
    }
    reportBenchmark()
    println()
  }

  def measure(bench: Benchmark) = {
    val startTime: Long = System.nanoTime()
    if (!bench.innerBenchmarkLoop(innerIterations)) {
      throw new RuntimeException("Benchmark failed")
    }
    val endTime: Long = System.nanoTime()
    val runTime: Long = (endTime - startTime) / 1000L

    printResult(runTime)
    total += runTime
  }

  def doRuns(bench: Benchmark) =
    for (i <- 1 to iterations) {
      bench.setUp(innerIterations)
      measure(bench)
    }

  def printResult(runTime: Long) =
    println(name + ": iterations=1 runtime: " + runTime + "us")

  def reportBenchmark() =
    println(
      name + ": iterations=" + iterations + " average: " + (total / iterations) + "us total: "
        + total + "us\n"
    )

  def setIterations(iterations: Int) =
    this.iterations = iterations

  def setInnerIterations(innerIterations: Int) =
    this.innerIterations = innerIterations

  def printTotal() =
    println("Total Runtime: " + total + "us")

}
