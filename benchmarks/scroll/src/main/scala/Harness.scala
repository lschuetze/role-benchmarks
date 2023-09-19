@main def harness(benchmark: String, iterations: Int, innerIterations: Int) =
  val run = new Run(benchmark);
  run.setIterations(iterations)
  run.setInnerIterations(innerIterations)

  run.runBenchmark()
  run.printTotal()
