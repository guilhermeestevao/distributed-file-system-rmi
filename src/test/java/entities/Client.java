package entities;

import org.jgroups.Address;
import org.jgroups.Message;
import org.jgroups.View;

public class Client extends Process {

	public Client() throws Exception {
		super();
		// TODO Auto-generated constructor stub
		this.channel.connect(CLUSTER_CLIENT);
	}
	
	
	@Override
	public void receive(Message msg) {
		// TODO Auto-generated method stub
		super.receive(msg);
		System.out.println(msg.getObject());
	}
	
	@Override
	public void viewAccepted(View view) {
		// TODO Auto-generated method stub
		super.viewAccepted(view);
	}
	
	
	public void requestProxy(Proxy proxy) throws Exception{
		Address src = proxy.getChannel().getAddress();
		channel.send(src, "Requisitando proxy");
	}

}
