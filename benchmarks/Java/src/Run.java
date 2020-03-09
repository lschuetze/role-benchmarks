public final class Run {
    private final String name;
    private final Class<? extends Benchmark> benchmarkSuite;
    private int iterations;
    private int innerIterations;
    private long total;

    public Run(final String name) {
        this.name = name;
        benchmarkSuite = getSuiteFromName(name);
        iterations = 1;
        innerIterations = 1;
    }

    @SuppressWarnings("unchecked")
    private static Class<? extends Benchmark> getSuiteFromName(final String name) {
        try {
            return (Class<? extends Benchmark>) Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void runBenchmark() {
        // Checkstyle: stop
        System.out.println("Starting " + name + " benchmark " + innerIterations +" times ..." );
        // Checkstyle: resume

        try {
            doRuns(benchmarkSuite.newInstance());
        } catch (ReflectiveOperationException | IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        reportBenchmark();

        // Checkstyle: stop
        System.out.println();
        // Checkstyle: resume
    }

    private void measure(final Benchmark bench) {
        long startTime = System.nanoTime();
        if (!bench.innerBenchmarkLoop(innerIterations)) {
            throw new RuntimeException("Benchmark failed");
        }
        long endTime = System.nanoTime();
        long runTime = (endTime - startTime) / 1000;

        printResult(runTime);

        total += runTime;
    }

    private void doRuns(final Benchmark bench) {
        for (int i = 0; i < iterations; ++i) {
            bench.setUp(innerIterations);
            measure(bench);
        }
    }

    private void printResult(final long runTime) {
        // Checkstyle: stop
        System.out.println(name + ": iterations=1 runtime: " + runTime + "us");
        // Checkstyle: resume
    }

    private void reportBenchmark() {
        // Checkstyle: stop
        System.out.println(name + ": iterations=" + iterations + " average: " + (total / iterations) + "us total: "
                           + total + "us\n");
        // Checkstyle: resume
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public void setInnerIterations(int innerIterations) {
        this.innerIterations = innerIterations;
    }

    public void printTotal() {
        // Checkstyle: stop
        System.out.println("Total Runtime: " + total + "us");
        // Checkstyle: resume
    }
}
