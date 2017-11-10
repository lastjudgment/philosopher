/**
 * ScientistTask.java 1.0 2014-7-9
 *
 * JYD(Bei Jing) Network Polytron Technologies Inc. All rights reserved.
 * This software is the confidential and proprietary 
 * information of JYD(Bei Jing) Network Polytron Technologies Inc. 
 * ("Confidential Information"). You shall not disclose 
 * such Confidential Information and shall use it only
 * in accordance with the terms of the contract agreement 
 * you entered into with JYD.
 */
package control;

import java.util.Date;

/**
 *
 *
 * @author Ulysses.Liu
 * @version 1.0 bate, 2014-7-9
 * @since JDK 1.5
 *
 */
public class ScientistTask implements Runnable
{
	public static int [] chopsticks_status = {0,0,0,0,0};
	public static boolean [] scientist_status = {false,false,false,false,false};
	private String scientist_name;
	private int scientist_no;
	
	private final int maxSecond=30,minSecond=5;
	
	public static void main(String[] args)
	{
        Thread t1 = new Thread(new ScientistTask("一号哲学家",0)); 
        Thread t2 = new Thread(new ScientistTask("二号哲学家",1));  
        Thread t3 = new Thread(new ScientistTask("三号哲学家",2));  
        Thread t4 = new Thread(new ScientistTask("四号哲学家",3));  
        Thread t5 = new Thread(new ScientistTask("五号哲学家",4));  
        Thread tx = new Thread(new ScientistTask("",5));  
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
//        tx.start();
	}
	
	public ScientistTask(String scientist_name,int scientist_no)
	{
		this.scientist_name=scientist_name;
		this.scientist_no=scientist_no;
	}
	
	private synchronized boolean thinking(long mi) throws InterruptedException
	{
		if (scientist_status[this.scientist_no] && chopsticks_status[this.scientist_no]==1 && chopsticks_status[(this.scientist_no+1)%chopsticks_status.length]==1)
		{
			System.out.println(new Date().getTime()+"\t"+scientist_name+"正在思考("+mi+"ms)！");
			chopsticks_status[this.scientist_no]=0;
			chopsticks_status[(this.scientist_no+1)%chopsticks_status.length]=0;
			scientist_status[this.scientist_no]=false;
//		System.out.println(chopsticks_status[0]+"\t"+chopsticks_status[1]+"\t"+chopsticks_status[2]+"\t"+chopsticks_status[3]+"\t"+chopsticks_status[4]+"===");
			return true;
		}
		else
			return false;
	}
	
	private synchronized boolean eating(long mi) throws InterruptedException
	{
		if (chopsticks_status[this.scientist_no]==0)
			chopsticks_status[this.scientist_no]=10+this.scientist_no;
		if (chopsticks_status[(this.scientist_no+1)%chopsticks_status.length]==0)
			chopsticks_status[(this.scientist_no+1)%chopsticks_status.length]=10+this.scientist_no;
		if (chopsticks_status[this.scientist_no]==10+this.scientist_no && chopsticks_status[(this.scientist_no+1)%chopsticks_status.length]==10+this.scientist_no)
		{
			chopsticks_status[this.scientist_no]=1;
			chopsticks_status[(this.scientist_no+1)%chopsticks_status.length]=1;
			scientist_status[this.scientist_no]=true;
			System.out.println(new Date().getTime()+"\t"+scientist_name+"正在吃饭("+mi+"ms)！");
			return true;
		}
		else
			return false;
	}
	
	public void run()
	{
		try
		{
			while(true)
			{
				if (this.scientist_no==5)
				{
					System.out.println(new Date().getTime()+" "+chopsticks_status[0]+" "+chopsticks_status[1]+" "+chopsticks_status[2]+" "+chopsticks_status[3]+" "+chopsticks_status[4]+" "+scientist_status[0]+" "+scientist_status[1]+" "+scientist_status[2]+" "+scientist_status[3]+" "+scientist_status[4]);
					Thread.sleep(5000);
					continue;
				}
				if (!scientist_status[this.scientist_no])
				{
					long mi = ((long)(Math.random()*maxSecond)%maxSecond+minSecond)*1000;
					if (eating(mi))
					{
						Thread.sleep(mi);
					}
					mi = ((long)(Math.random()*maxSecond)%maxSecond+minSecond)*1000;
					if (thinking(mi))
					{
						Thread.sleep(mi);
					}
				}
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
