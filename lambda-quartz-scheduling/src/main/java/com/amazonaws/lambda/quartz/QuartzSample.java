package com.amazonaws.lambda.quartz;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.CalendarIntervalScheduleBuilder.*;
import static org.quartz.JobKey.*;
import static org.quartz.TriggerKey.*;
import static org.quartz.DateBuilder.*;
import static org.quartz.impl.matchers.KeyMatcher.*;
import static org.quartz.impl.matchers.GroupMatcher.*;
import static org.quartz.impl.matchers.AndMatcher.*;
import static org.quartz.impl.matchers.OrMatcher.*;

import java.util.List;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.impl.matchers.EverythingMatcher.*;

public class QuartzSample {

	public static void main(String[] args) throws SchedulerException {
		// the 'default' scheduler is defined in "quartz.properties" found
		// in the current working directory, in the classpath, or
		// resorts to a fall-back default that is in the quartz.jar

		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler = sf.getScheduler();

		// Scheduler will not execute jobs until it has been started 
		// (though they can be scheduled before start())
		scheduler.start();

		// Define job instance
		JobDetail job1 = JobBuilder.newJob(PrintPropsJob.class)
		    .withIdentity("job1", "group1")
		    .usingJobData("someProp", "someValue")
		    .build();

		// Define a Trigger that will fire "now", and not repeat
		Trigger trigger = TriggerBuilder.newTrigger()
		    .withIdentity("trigger1", "group1")
		    .startNow()
		    .build();

		// Schedule the job with the trigger
		scheduler.scheduleJob(job1, trigger);

		// Unschedule a particular trigger from the job (a job may have more than one trigger)
		//scheduler.unscheduleJob(triggerKey("trigger1", "group1"));

		// Schedule the job with the trigger
		//scheduler.deleteJob(jobKey("job1", "group1"));

		// Define a durable job instance (durable jobs can exist without triggers)
		JobDetail job2 = JobBuilder.newJob(PrintPropsJob.class)
		    .withIdentity("job2", "group1")
		    .storeDurably()
		    .build();
		// Add the the job to the scheduler's store
		scheduler.addJob(job2, false);

		// Define a Trigger that will fire "now" and associate it with the existing job
		trigger = newTrigger()
		    .withIdentity("trigger2", "group1")
		    .startNow()
		    .forJob(jobKey("job2", "group1"))
		    .build();
		// Schedule the trigger
		scheduler.scheduleJob(trigger);

		// Add the new job to the scheduler, instructing it to "replace"
		// the existing job with the given name and group (if any)
		JobDetail job3 = newJob(PrintPropsJob.class)
				 .storeDurably()
				.withIdentity("job1", "group1").build();
		// store, and set overwrite flag to 'true'
		scheduler.addJob(job3, true);

		//Replacing a trigger
		// Define a new Trigger
		trigger = newTrigger()
		    .withIdentity("newTrigger", "group1")
		    .startNow()
		    .build();
		// tell the scheduler to remove the old trigger with the given key, and put the new one in its place
		scheduler.rescheduleJob(triggerKey("oldTrigger", "group1"), trigger);

		
		// enumerate each job group
		for(String group: scheduler.getJobGroupNames()) {
		    // enumerate each job in group
		    for(JobKey jobKey : scheduler.getJobKeys(groupEquals(group))) {
		        System.out.println("Found job identified by: " + jobKey);
		    }
		}

		// enumerate each trigger group
		for(String group: scheduler.getTriggerGroupNames()) {
		    // enumerate each trigger in group
		    for(TriggerKey triggerKey : scheduler.getTriggerKeys(groupEquals(group))) {
		        System.out.println("Found trigger identified by: " + triggerKey);
		    }
		}

		//Finding Triggers of a Job
		List<? extends Trigger> jobTriggers = scheduler.getTriggersOfJob(jobKey("job1", "group1"));
		 System.out.println("Found trigger "+ jobTriggers);
		
		 
		 
		 
		//shutdown() does not return until executing Jobs complete execution
		//scheduler.shutdown(true);
		
		//shutdown() returns immediately, but executing Jobs continue running to completion
		//scheduler.shutdown();

	}
}
