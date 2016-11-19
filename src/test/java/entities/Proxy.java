package entities;

import java.util.Scanner;

import org.jgroups.Address;
import org.jgroups.Message;
import org.jgroups.View;

public class Proxy extends Process{

	public Proxy() throws Exception {
		super();
		// TODO Auto-generated constructor stub
		
		this.channel.connect(CLUSTER_PROXY);
		start();
	}
	
	private void start(){
		Scanner in = new Scanner(System.in);
		
		in.nextLine();
		
	}

	@Override
	public void receive(Message msg) {
		// TODO Auto-generated method stub
		super.receive(msg);
		
		System.out.println(msg.getObject());
		
		Address src = msg.src();
		
		
		try {
			channel.send(src, "Recbida a mensagem");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public void viewAccepted(View view) {
		// TODO Auto-generated method stub
		super.viewAccepted(view);
	}

	
	
	
}
