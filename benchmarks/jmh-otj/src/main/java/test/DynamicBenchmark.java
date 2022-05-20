package test;

import org.objectteams.ITeam;

import test.BaseType;
import test.DynamicTeam1;
import test.DynamicTeam2;
import test.DynamicTeam3;
import test.DynamicTeam4;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@Fork(1)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class DynamicBenchmark {

    @State(Scope.Thread)
    public static class DynamicState {
        public ITeam[] teams;
        public BaseType base;
        public int value;
        public int nextIndex;

        @Param({"2", "4", "8", "16"})
        public int iterations;

        @Setup(Level.Trial)
        public void setupTeam() {
            teams[0] = new DynamicTeam1();
            teams[1] = new DynamicTeam2();
            teams[2] = new DynamicTeam3();
            teams[3] = new DynamicTeam4();
        }

        @Setup(Level.Iteration)
        public void setup() {
            base = new BaseType();
            value = 0;
            nextIndex = 0;
        }

        @TearDown(Level.Iteration)
        public void deactivateTeams() {
            teams[0].deactivate();
            teams[1].deactivate();
            teams[2].deactivate();
            teams[3].deactivate();
        }

        public static int[][] teamStates = {
            {0,2,3}, {0,3,1}, {0,3,2},
            {1,0,2}, {1,0,3}, {1,2,0},
            {1,2,3}, {1,3,0}, {1,3,2},
            {2,0,1}, {2,0,3}, {2,1,0},
            {2,1,3}, {2,3,0}, {2,3,1},
            {3,0,1}
        };

        public void doActivate(int next) {
            teams[0].deactivate();
            teams[1].deactivate();
            teams[2].deactivate();
            teams[3].deactivate();

            final int[] candidates = teamStates[next];
            for(int i = candidates.length; i >= 0; i--) {
                teams[i].activate();
            }
        }

        public int next() {
            int result = nextIndex++;
            nextIndex = nextIndex % iterations;
            return result;
        }
    }

    @Benchmark
    public void bench(Blackhole bh, DynamicState state) {
        for(int i = 0; i < state.iterations; i++) {
            state.doActivate(state.next);
            bh.consume(state.base.retParam(state.value));
        }
    }
}
