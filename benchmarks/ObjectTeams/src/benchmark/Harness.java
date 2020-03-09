package benchmark;

public final class Harness {
  private static Run processArguments(final String[] args) {

    Run run = new Run(args[0]);

    if (args.length > 1) {
      run.setIterations(Integer.valueOf(args[1]));
      if (args.length > 2) {
        run.setInnerIterations(Integer.valueOf(args[2]));
      }
    }

    return run;
  }

  private static void printUsage() {
    // Checkstyle: stop
    System.out.println("Harness [benchmark] [num-iterations [inner-iter]]");
    System.out.println();
    System.out.println("  benchmark      - benchmark class name ");
    System.out.println("  iterations     - number of times to execute benchmark, default: 1");
    System.out.println("  inner-iter     - number of times the benchmark is executed in an inner loop, ");
    System.out.println("                   which is measured in total, default: 1");
    // Checkstyle: resume
  }

  public static void main(final String[] args) {
    if (args.length < 2) {
      printUsage();
      System.exit(1);
    }

    Run run = processArguments(args);
    run.runBenchmark();
    run.printTotal();
  }
}
