package entities;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

public class Process extends ReceiverAdapter {
	
	protected JChannel channel;
	protected final static String CLUSTER_CLIENT = "clients";
	protected final static String CLUSTER_PROXY = "proxies";

	
	public Process() throws Exception {
		// TODO Auto-generated constructor stub
		this.channel =  new JChannel();
		
		
		channel.setReceiver(this);
		
	}


	public JChannel getChannel() {
		return channel;
	}


	public void setChannel(JChannel channel) {
		this.channel = channel;
	}
	
}
