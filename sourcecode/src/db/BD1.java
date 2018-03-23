package db;


import java.sql.ResultSet;

public class BD1 extends BASE1
{
	private BASE1 bd =null;
	@SuppressWarnings("unused")
	private ResultSet rs = null;
	private String sql;

	public BD1(){
		bd = new BASE1();
	}
	
	public boolean table() {
		
		sql = "update test set time='"+20160730+"' where id='"+1+"'";
				System.out.println("update sql :"+sql);
				if (bd.executeUpdate(sql) == 0){
					System.out.println("update wrong!");
					return false;
				}
				else{
					System.out.println("update ok!");
					return true;
				}
			}

	
	public static void main(String args[]){
	//
		
		//Dao d = new Dao();
		//System.out.println(d.queryActivity());
		//d.searchCommByKeyWord("饼干 百奇");
		/*JSONObject myjs = new JSONObject();
		myjs.put("comm", d.searchCommById("6901668001603"));
		System.out.println(myjs.toString());*/
		//d.close();
		
	}
}


