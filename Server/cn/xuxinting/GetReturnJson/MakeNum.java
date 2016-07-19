package cn.xuxinting.GetReturnJson;

public class MakeNum extends Thread{
	int Num;
	
	public int getNum(){
		geneNum();
		return Num;
	}
	
	void geneNum(){
		Long timeLong = System.currentTimeMillis();
		Num = (int)((timeLong / 1000) % 1000);
	}
}