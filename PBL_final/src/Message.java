import java.rmi.RemoteException;

public class Message {
		private String Sender;
		private String receivei;
		private int time_Send;
		private int time_Receive;
		private String Content;
		public Message(String Sender,String receive,int timesend,int timereceive,String Content) {
			this.Sender= Sender;
			this.receivei = receive;
			this.time_Send = timesend;
			this.time_Receive = timereceive;
			this.Content = Content;
		}
		
		public String getSender() {
			return Sender;
		}
		public void setSender(String sender) {
			Sender = sender;
		}
		public String getReceivei() {
			return receivei;
		}
		public void setReceivei(String receivei) {
			this.receivei = receivei;
		}
		public int getTime_Send() {
			return time_Send;
		}

		public void setTime_Send(int time_Send) {
			this.time_Send = time_Send;
		}

		public int getTime_Receive() {
			return time_Receive;
		}

		public void setTime_Receive(int time_Receive) {
			this.time_Receive = time_Receive;
		}

		public String getContent() {
			return Content;
		}
		public void setContent(String content) {
			Content = content;
		}
}
