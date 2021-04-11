all:			JogadorClient.class JogadorServer.class \
			JogadorClientImpl.class JogadorServerImpl.class

JogadorClient.class:	JogadorClient.java
			@javac JogadorClient.java

JogadorServer.class:	JogadorServer.java
			@javac JogadorServer.java

JogadorClientImpl.class:	JogadorClientImpl.java
			@javac JogadorClientImpl.java

JogadorServerImpl.class:	JogadorServerImpl.java
			@javac JogadorServerImpl.java

clean:
			@rm -f *.class *~