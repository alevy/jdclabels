SRCS = $(shell find src -name "*.java")
TESTS = $(shell find test -name "*.java")
JUNIT = /usr/share/java/junit.jar
JAVAC = javac
JAVA = java

all:

compile: $(SRCS)
	$(JAVAC) -d build -cp src $(SRCS)

compile_tests: compile $(TESTS)
	$(JAVAC) -d build -cp src:test:$(JUNIT) $(TESTS)

test: compile_tests
	$(JAVA) -cp build:$(JUNIT) org.junit.runner.JUnitCore edu.stanford.scs.difc.dclabel.AllTests

