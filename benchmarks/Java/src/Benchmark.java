public abstract class Benchmark {

    public abstract Object benchmark();
    public abstract boolean verifyResult(Object result);
    public abstract boolean setUp(int innerIterations);

    public boolean innerBenchmarkLoop(final int innerIterations) {
        for (int i = 0; i < innerIterations; i++) {
            if (!verifyResult(benchmark())) {
                return false;
            }
        }
        return true;
    }
}
