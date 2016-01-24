package orz.yanagin.web2feed.db;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class DaoBaseTest {
	
	static class TestDaobase extends DaoBase {
		
	}

	@Test
	public void testGetSequence() {
		assertThat(new TestDaobase().getSequence("queue") > 0, is(true));
	}

}
