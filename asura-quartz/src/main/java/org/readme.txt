1.org.quartz.impl.jdbcjobstore.DriverDelegate
	增加方法：
	String selectJobClassForTrigger(Connection conn, String triggerName,String groupName) throws SQLException;
	
	
2.org.quartz.impl.jdbcjobstore.StdJDBCDelegate
	增加方法：
	String selectJobClassForTrigger(Connection conn, String triggerName,String groupName) throws SQLException{。。。。}
	
	
3.org.quartz.impl.jdbcjobstore.JobStoreSupport
	增加：
	protected RecoverMisfiredJobsResult recoverMisfiredJobs(Connection conn, boolean recovering)
	在上面方法中增加
	==>
	List tempMisfiredTriggers = new ArrayList();
        for (Iterator misfiredTriggerIter = misfiredTriggers.iterator(); misfiredTriggerIter.hasNext();) {
            Key triggerKey = (Key) misfiredTriggerIter.next();
            String jobClass = getDelegate().selectJobClassForTrigger(conn, triggerKey.getName(), triggerKey.getGroup());
            try {
				getClassLoadHelper().loadClass(jobClass);
			} catch (ClassNotFoundException e) {
				tempMisfiredTriggers.add(triggerKey);
				continue;
			}
      }
	
	增加：
	protected Trigger acquireNextTrigger(Connection conn, SchedulingContext ctxt, long noLaterThan)
	在上面方法中增加
	==>
	String jobClass = getDelegate().selectJobClassForTrigger(conn, triggerKey.getName(), triggerKey.getGroup());
    try {
		getClassLoadHelper().loadClass(jobClass);
	} catch (ClassNotFoundException e) {
		continue;
	}

4、定时任务执行日志记录修改：org.quartz.core.JobRunShell
  public void run() 方法中：
   242-252行新增到吗如下
       log.info("start record job log");
                   //execute log
                   try {
                       String currentIp = InetAddress.getLocalHost().getHostAddress();
                       qs.recordJobLog(jec.getTrigger(),startTime,endTime, currentIp);
                       log.info("end record job log");
                   } catch (UnknownHostException e) {
                       log.warn("record execute log error");
                   } catch (SchedulerException e) {
                       log.warn("record execute log error");
                   }


            org.quartz.core.QuartzScheduler类中新增如下方法
            public void recordJobLog(Trigger trigger, long start, long end, String ip) throws SchedulerException {
                try {
                    resources.getJobStore().recordExecuteLog(trigger, start, end, ip);
                } catch (JobPersistenceException e) {
                    throw new SchedulerException(
                            "record job log error");
                }
            }

         org.quartz.spi.JobStore接口中新增如下方法：
             int recordExecuteLog(Trigger trigger,long start,long end,String ip) throws JobPersistenceException;

         对应的实现类
         org.quartz.impl.jdbcjobstore.JobStoreSupport
         实现recordExecuteLog 方法，具体调用jdbc服务

5.org.quartz.impl.jdbcjobstore.StdJDBCDelegate
    修改：
    public JobDetail selectJobDetail(Connection conn, String jobName,
            String groupName, ClassLoadHelper loadHelper)
        throws ClassNotFoundException, IOException, SQLException

    在上面方法中修改
    ==>
    //由于添加任务移动至业务线，恢复定时任务时报ClassNotFoundException,所以修改此行，对定时任务执行没有影响 edit by liangwj
        try{
            job.setJobClass(loadHelper.loadClass(rs.getString(COL_JOB_CLASS)));
        }catch (ClassNotFoundException e){
            //do nothing
        }

6.org.quartz.impl.jdbcjobstore.StdJDBCDelegate
    修改：
    public JobDetail selectJobForTrigger(Connection conn, String triggerName,
                String groupName, ClassLoadHelper loadHelper) throws ClassNotFoundException, SQLException
    在上面方法中修改
        ==>
    //由于添加任务移动至业务线，修改quartz时报ClassNotFoundException,所以修改此行，对定时任务执行没有影响 edit by liangwj
        try{
            job.setJobClass(loadHelper.loadClass(rs.getString(4)));
        } catch (ClassNotFoundException e){
            //do nothing
        }

7.定时任务执行日志记录修改：org.quartz.core.JobRunShell
    public void run() 方法中：
     242-252行新增到吗如下
         log.info("start record job log");
                     //execute log
                     try {
                         String currentIp = InetAddress.getLocalHost().getHostAddress();
                         qs.recordJobLog(jec.getTrigger(),startTime,endTime, currentIp);
                         log.info("end record job log");
                     } catch (UnknownHostException e) {
                         log.warn("record execute log error");
                     } catch (SchedulerException e) {
                         log.warn("record execute log error");
                     }


              org.quartz.core.QuartzScheduler类中新增如下方法
              public void recordJobLog(Trigger trigger, long start, long end, String ip) throws SchedulerException {
                  try {
                      resources.getJobStore().recordExecuteLog(trigger, start, end, ip);
                  } catch (JobPersistenceException e) {
                      throw new SchedulerException(
                              "record job log error");
                  }
              }

           org.quartz.spi.JobStore接口中新增如下方法：
               int recordExecuteLog(Trigger trigger,long start,long end,String ip) throws JobPersistenceException;

           对应的实现类
           org.quartz.impl.jdbcjobstore.JobStoreSupport
           实现recordExecuteLog 方法，具体调用jdbc服务
