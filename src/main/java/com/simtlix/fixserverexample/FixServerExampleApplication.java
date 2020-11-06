package com.simtlix.fixserverexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import quickfix.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootApplication
public class FixServerExampleApplication {

	public static void main(String[] args) {

		SessionSettings settings = null;
		ExampleServerApplication application = new ExampleServerApplication();
		SocketAcceptor acceptor = null;
		try {
			settings = new SessionSettings(new FileInputStream("src/main/resources/application.properties"));
			acceptor = new SocketAcceptor(application, new MemoryStoreFactory(), settings,
					new ScreenLogFactory(), new DefaultMessageFactory());
			acceptor.start();
		} catch (ConfigError configError) {
			configError.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
