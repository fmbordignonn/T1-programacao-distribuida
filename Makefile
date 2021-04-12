all:			JogadorClient.class JogoServer.class \
			JogadorClientImpl.class JogoServerImpl.class

JogadorClient.class:	JogadorClient.java
			@javac JogadorClient.java

JogoServer.class:	JogoServer.java
			@javac JogoServer.java

JogadorClientImpl.class:	JogadorClientImpl.java
			@javac JogadorClientImpl.java

JogoServerImpl.class:	JogoServerImpl.java
			@javac JogoServerImpl.java

clean:
			@rm -f *.class *~