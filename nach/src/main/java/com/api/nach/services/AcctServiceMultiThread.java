package com.api.nach.services;


public class AcctServiceMultiThread{
	
	public static void getPanDtls(String acctNo){
		
		String s1 = acctNo;
		
		final String s2 = s1;
		
		Thread t1 = new Thread(new Runnable() {
			
			public void run() {
				
				
					try {
						getFiData(s2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				
			}
		});
		t1.start();
		
		 
	}
	
	public static void getAcctHolder(){
		
		String s1 = "Thread 2";
		
		Thread t1 = new Thread(new Runnable() {
			
			public void run() {
				
				
					try {
						getFiData(s1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				
			}
		});
		t1.start();
		
		 
	}

	public static void getAcctStatus(){
	
	String s1 = "Thread 3";
	
	Thread t1 = new Thread(new Runnable() {
		
		public void run() {
			
			
				try {
					getFiData(s1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			
		}
	});
	t1.start();
	
	 
	}
	
	synchronized static void getFiData(String acctNo) throws InterruptedException {
		
		
		for(int i=0;i<5;i++) {
			Thread.sleep(1000);
			System.out.println("Acct no is "+acctNo);
		}
	}

	public static void main(String[] args) {
		
		
		getPanDtls("acct1");
		getPanDtls("acct2");
		getPanDtls("acct3");
		getAcctHolder();
		getAcctStatus();
		

	}

}
