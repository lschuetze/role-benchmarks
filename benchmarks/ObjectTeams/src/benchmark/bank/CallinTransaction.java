package benchmark.bank;

public team class CallinTransaction {

	public class Source playedBy Account {

		callin float withDraw(float amount) {
			//System.out.println("Source replace BEGIN");
			float f = base.withDraw(amount);
			//System.out.println("Source replace END");
			return f;
		}

		public void after() {
		    // System.out.println("Source after");
		}

		public void before(float a) {
		    // System.out.println("Source before");
        }

    	void before(float a) <- before float decrease(float amount);
        //void after() <- after void decrease(float amount);

		float withDraw(float amount) <- replace float decrease(float amount);
	}

	public class Target playedBy Account {
		callin float deposit(float amount) {
			// System.out.println("Target replace BEGIN");
			float f = base.deposit(amount);
			// System.out.println("Target replace END");
			return f;
	  }

		float deposit(float amount) <- replace float increase(float amount);
	}

	// @ImplicitTeamActivation
	public boolean execute(Account f, Account t, float a) {
		// System.out.println("Transaction decrease BEGIN");
		float dec = f.decrease(a);
		// System.out.println("Transaction decrease END");
		//System.out.println(dec);

		// System.out.println("Transaction increase BEGIN");
		float inc = t.increase(a);
		// System.out.println("Transaction increase END");
		return true;
	}
}
