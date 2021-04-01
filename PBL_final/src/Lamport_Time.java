
public class Lamport_Time {
	private int logic_time;
	
	public Lamport_Time(int a) {
		this.logic_time = a;
	}

	public int getLogic_time() {
		return logic_time;
	}

	public void setLogic_time(int logic_time) {
		this.logic_time = logic_time;
	}
	public void up() {
		this.logic_time += 1;
	}
	public int max(int a,int b) {
		if(a>=b) {
			return a;
		}else {
			return b;
		}
	}
}
