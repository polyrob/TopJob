package com.threerings.challenge.test;

import org.junit.Before;
import org.junit.Test;

import com.threerings.challenge.job.Job;
import com.threerings.challenge.job.JobGenerator;
import com.threerings.challenge.util.Formatter;

public class JobManagerTest {

	JobGenerator jm;

	@Before
	public void init() {
		jm = new JobGenerator();
	}

	@Test
	public void getRandomJobTest() {
		for (int i = 0; i < 10; i++) {
			Job j = jm.getJob();
			System.out.println(j.getJobDisplayName() + ". Salary: "
					+ Formatter.formatMoney(j.getSalary()));
			System.out.println(j.getMinSkills());
		}
	}

}
