package mct;

/**
 * 低位优先字符串排序算法
 * @author yangchao.wang
 *
 * @email  yangchao.wang@flaginfo.com.cn
 * @Desc  
 * 
 * @time 2016年6月29日下午1:28:30
 */
public class LSDTest {

	public static void main(String[] args){
	
		long beginTime = System.currentTimeMillis();
		
		String[] a = {"q234","2223","abu2","brr3","add3","a1f1","2223","abu2","brr3","add3","a1f1","2223","abu2","brr3","add3","a1f1","2223","abu2","brr3","add3","a1f1","2223","abu2","brr3","add3","a1f1","2223","abu2","brr3","add3","a1f1","2223","abu2","brr3","add3","a1f1","2223","abu2","brr3","add3","a1f1","2223","abu2","brr3","add3","a1f1","2223","abu2","brr3","add3","a1f1","2223","abu2","brr3","add3","a1f1","2223","abu2","brr3","add3","a1f1","2223","abu2","brr3","add3","a1f1","2223","abu2","brr3","add3","a1f1","2223","abu2","brr3","add3","a1f1","2223","abu2","brr3","add3","a1f1","2223","abu2","brr3","add3","a1f1"};
		
			LSDSort(a, 4);
		
		System.out.println(System.currentTimeMillis() - beginTime);
	}

	private static void LSDSort(String[] a, int i) {
		
		int R = 256;
		int L = a.length;
		//低位遍历
		for(int m=i-1; m>=0; m--){
			int[] count = new int[R+1];   //键值的频率容器
			for(int k=0;k<L;k++){//计算键值的出现频率
				count[a[k].charAt(m) + 1]++;
			}
			
			for(int k=0; k< R; k++){//将键值的频率转换成索引
				count[k+1] += count[k];
			}
			
			String[] tmp = new String[L];//排序临时容器
			for(int k=0; k<L; k++){ //根据键值的频率对该键排序
				tmp[count[a[k].charAt(m)]++] = a[k];
			}
			
			for(int k=0; k<L;k++){   //数据回填
				a[k] = tmp[k];
			}
		}
		
		
	
	}
	
	
}
