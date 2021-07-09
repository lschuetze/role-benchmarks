package benchmark.team;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

public class BaseNB {

	// static Logger logger = LoggerFactory.getLogger(Base.class);

	public int id;

	public BaseNB(int id) {
		this.id = id;
	}

	public float operation1(float n) {
		// logger.info("BASE Base {} operation1", id);
		return n * 2;
	}

	public float operation2(float n) {
		// logger.info("BASE Base {} operation2", id);
		return n / 2;
	}

}