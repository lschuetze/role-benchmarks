package benchmark;

import benchmark.team.Base;
import benchmark.team.Team1;
import benchmark.team.Team2;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

public class TeamBenchmark3 extends Benchmark {

	// static Logger logger = LoggerFactory.getLogger(TeamBenchmark3.class);

	private Team1 team1;
	private Team2 team2;
	private Base a;
	private Base b;

	@Override
	public boolean innerBenchmarkLoop(final int innerIterations) {
		int iterations = innerIterations * innerIterations;
		float n = 100.0f;
		team1.activate();
		team2.activate();
		for (int i = 0; i < iterations; i++) {
			a.operation1(n);
			b.operation2(n);
		}
		team2.deactivate();
		team1.deactivate();

		return true;
	}

	public boolean setUp(final int innerIterations) {
        team1 = new Team1();
        team2 = new Team2();
        a = new Base(1);
        b = new Base(2);

        return true;
    }

    @Override
    public Object benchmark() {
        throw new RuntimeException("Should never be reached");
    }

    @Override
    public boolean verifyResult(Object result) {
        throw new RuntimeException("Should never be reached");
    }
}