package mct;

import org.junit.Test;

public class MyTest {
	
	
	/**
	 * DFA构建算法
	 * 
	 * 
	 * 	 */
	public void DFATest(){
		String pat = "paten"; //构建需要匹配的模式
		String srcStr = "abcfkdhdskahgkjspattjdjdjappabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnabcfkdhdskahgkjspattjdjdjappattrnattrn";//构建需要匹配的源串
		int charSize = 256;//定义字符hashCode 最大长度
		int restartPosition = 0;//初始化回溯扫描的pattern 位置
		int patLength = pat.length();//获取匹配模式的长度
		int[][] dfa = new int[charSize][patLength];//初始化定义DFA数组长度
		dfa[pat.charAt(0)][0] = 1;    //开始位置定义
		
		//遍历匹配模式
		for(int i=1;i<patLength; i++){
			
			for(int j=0;j< charSize;j++){
				//设置未匹配状态
				dfa[j][i] = dfa[j][restartPosition];
			}
			
			//设置匹配的状态
			dfa[pat.charAt(i)][i] = i+1;
			
			//更新重启匹配的位置
			restartPosition = dfa[pat.charAt(i)][restartPosition];
		}
		
		long beginTime = System.currentTimeMillis();
		for(int k=0;k<1000000;k++){
			int status = 0;
			for(int i=0;i<srcStr.length();i++){
				
				if((status = dfa[srcStr.charAt(i)][status]) == (patLength)){
					System.out.println("ok");
					break;
				}
			}
		}
		System.out.println("耗时------>>" + (System.currentTimeMillis() - beginTime));
		
		beginTime = System.currentTimeMillis();
		boolean f ;
		for(int k=0;k<1000000;k++){
			boolean b = srcStr.contains(pat);
			f = b;
		}
		
		System.out.println("耗时------>>" + (System.currentTimeMillis() - beginTime) );
	}
	
	
	
	@Test
	public void test1(){
		Object o1 = new Object(){
			
			
			@Override
			public int hashCode(){
				return 0;
			}
		};
		
		Object o2 = new Object(){
			@Override
			public boolean equals(Object o){
				return true;
			}
			
			
			@Override
			public int hashCode(){
				return 0;
			}
		};
		System.out.println(o1.hashCode());
		System.out.println(o2.hashCode());
		
		
		System.out.println(o1.equals(o2));
		System.out.println(o2.equals(o1));
		
	}
	
}
