# Java frameworks
Containers and example codes created throght this spike can be found [here](https://github.com/hoss-java/containers)

## Servlet
* A Jakarta Servlet, formerly Java Servlet is a Java software component that extends the capabilities of a server.
* Tomcat uses two standard Java technologies for web services:
> * the Jakarta RESTful Web Services (JAX-RS 2.0) useful for AJAX, JSON and REST services, and
> * the Jakarta XML Web Services (JAX-WS) useful for SOAP Web Services.
* A Servlet is an object that receives a request and generates a response based on that request.
* The Servlet API, contained in the Java package hierarchy javax.servlet, defines the expected interactions of the web container and a servlet
* Servlets may be packaged in a WAR file as a web application
* A web container is required for deploying and running a servlet. A web container (also known as a servlet container) is essentially the component of a web server that interacts with the servlets. The web container is responsible for managing the lifecycle of servlets, mapping a URL to a particular servlet and ensuring that the URL requester has the correct access rights. 
* Three methods are central to the life cycle of a servlet. These are init(), service(), and destroy(). They are implemented by every servlet and are invoked at specific times by the server. 
>```
>import java.io.IOException;
>
>import jakarta.servlet.ServletConfig;
>import jakarta.servlet.ServletException;
>import jakarta.servlet.http.HttpServlet;
>import jakarta.servlet.http.HttpServletRequest;
>import jakarta.servlet.http.HttpServletResponse;
>
>public class ServletLifeCycleExample extends HttpServlet {
>    private Integer sharedCounter;
>
>    @Override
>    public void init(final ServletConfig config) throws ServletException {
>        super.init(config);
>        getServletContext().log("init() called");
>        sharedCounter = 0;
>    }
>
>    @Override
>    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
>        getServletContext().log("service() called");
>        int localCounter;
>        synchronized (sharedCounter) {
>            sharedCounter++;
>            localCounter = sharedCounter;
>        }
>        response.getWriter().write("Incrementing the count to " + localCounter);  // accessing a local variable
>        response.getWriter().flush();   // flush response
>    }
>
>    @Override
>    public void destroy() {
>        getServletContext().log("destroy() called");
>    }
>}
>```
* servlet container or web container is a technology that has been implemented/used by several software. Tomcat is one of them, Here can be find a list of  web containers software https://en.wikipedia.org/wiki/Web_container
* References :
> * https://en.wikipedia.org/wiki/Jakarta_Servlet
> * https://en.wikipedia.org/wiki/Web_container

## Tomcat
* There is an official container for Tomcat https://hub.docker.com/_/tomcat/
* A container based on https://github.com/davidcaste/docker-alpine-tomcat was created
* It seems when a folder is defined in a compose file (not a full path) , it's created automatically! (I didn't know it)
* There was a long time that I worked with containers and cachase for docker parts become bigger an bigger , here I found how to clean up docker caches https://depot.dev/blog/docker-clear-cache
* Now tomcat container can be accessed via browser from localhost:8083 (8080 was busy on my computer and I mapped it to 8083 via compose file, the port on container level still is 8080)
* I found there is no need all time to use openjdk for example in the case of tomcat container there is no development within the container so jre kan be used instead jdk (alpine package name is `openjdk8-jre`)
* References :
> * https://hub.docker.com/_/tomcat/
> * https://github.com/docker-library/tomcat
> * https://github.com/davidcaste/docker-alpine-tomcat
> * https://depot.dev/blog/docker-clear-cache
* **Update** : During working on other java frameworks (the sections below) I realized the tomcat container that I created here needs to be updated to have have access from outside of the docker network. It also needs to define users (admin and manager)
> * There are two files that needs to be updated to open tomcat web interface to the docker host and adding users, `/data/tomcat/webapps/manager/META-INF/context.xml` and `/opt/tomcat/conf/tomcat-users.xml`
> * `/data` folder has already mapped to a folder outside of the container so `/data/tomcat/webapps/manager/META-INF/context.xml` can be changed without any problem.
>> * The last part of `/data/tomcat/webapps/manager/META-INF/context.xml` sould be commented to have access from out of the container
>>>```
>>><Context antiResourceLocking="false" privileged="true" >
>>><!--
>>>  <Valve className="org.apache.catalina.valves.RemoteAddrValve"
>>>         allow="127\.\d+\.\d+\.\d+|::1|0:0:0:0:0:0:0:1" />
>>>    -->
>>></Context>
>>>```
> * `/opt/tomcat/conf/tomcat-users.xml` is stored inside of the containe, it can be mapped to outside but I don't think so this file needs to be updated after composing up the container but to make it easy I mapped tomcat conf folder to the docker data folder on the host. Now the conf folder can be found on tomcat folder on the host. To add users `docker-data/tomcat/conf/tomcat-users.xml` can be updated with some lines le below :
>>```
>>.
>>.
>><tomcat-users>
>>  <role rolename="manager-gui"/>
>>  <role rolename="admin-gui"/>
>>  <user username="tomcat" password="tomcat" roles="manager-gui, admin-gui"/>
>></tomcat-users>
>>```

## Maven
* Maven is a build automation tool used primarily for Java projects. It can also be used with projects on ther languages such as C/C++, Ruby (Maven is built using a plugin-based architecture that allows it to make use of any application controllable through standard input. A C/C++ native plugin is maintained for Maven 2)
* Maven addresses two aspects of building software: how software is **built** and **its dependencies**
* Maven is a Jakarta servlet like tools (plugins work like war files for tomcat) - *So I don't think it can work under tomcat or other servlet frameworks.*
* It seems there was a tool named **Apache Ant** before that was used as build automation
* XML files are used to describe projects and its dependencies.
* Maven comes with a pre-defined dictionary for performing certain well-defined tasks such as compilation of code and its packaging
* As I understood  **Gradle**( I know this one name from Android project files) and **sbt** are alternative technologies (build automation tools) that are not XML file base (Maven use XML but Gradle not).
* `pom.xml` is the file name that is used for Maven projects (Project Object Model (POM)) https://en.wikipedia.org/wiki/Apache_Maven#Project_Object_Model
* A `pom.xml` looks like something like below :
>```
>< project>
>  <!-- model version is always 4.0.0 for Maven 2.x POMs -->
>  < modelVersion>4.0.0< /modelVersion>
>  
>  <!-- project coordinates, i.e. a group of values which uniquely identify this project -->
>  < groupId>com.mycompany.app< /groupId>
>  < artifactId>my-app< /artifactId>
>  < version>1.0</ version>
>
>  <!-- library dependencies -->
>  < dependencies>
>
>      <!-- The coordinates of a required library.
>           The scope is 'test' to indicate the library
>           is only used for running tests. -->
>      < dependency>
>          < groupId>org.junit.jupiter< /groupId>
>          < artifactId>junit-jupiter-engine< /artifactId>
>          < version>5.9.1< /version>
>          < scope>test< /scope>
>      < /dependency>
>
>  < /dependencies>
>< /project>
>```
* Examples of popular IDEs supporting development with Maven include:
> * Eclipse
> * NetBeans
> * IntelliJ IDEA
> * JBuilder
> * JDeveloper (version 11.1.2)
> * MyEclipse
> * Visual Studio Code
* Larger projects should be divided into several modules, or sub-projects, each with its own POM.
* **OBS!** `make` is also a build automation tool (I can open another card to learn little bit more about make files)
* References:
> * https://maven.apache.org/
> * https://en.wikipedia.org/wiki/Apache_Maven#Project_Object_Model
> * https://en.wikipedia.org/wiki/Apache_Maven#Project_Object_Model
> * https://www.youtube.com/playlist?list=PLa7VYi0yPIH0KbnJQcMv5N9iW8HkZHztH
> * https://dev.to/nilan/getting-started-with-maven-a-beginners-guide-to-java-build-automation-41f2

### Getting hands on Maven
* There is an official container for Maven https://hub.docker.com/_/maven/
* There is also an alpine base container version that can be used to create custom containers
* Instead using official images , I created one by myself based on alpine 3.19. The container contains openjdk + maven.
>```
># to get the varsion of maven inside of the container
>docker exec -it maven mvn --version
>```
* As I found before Maven is a building tool, it means it uses other tools such as compilers to build a project and its dependencies. There are two ways that maven can be used
> 1 . Install Maven on the host and tools on containers. I think it can be used when a tools such as compilers used to several projects
> 2. Creating a container of Maven and needed tools for a project and using it to bulid project files that are hosted on the host. In other words, specifining a continer as the project builder.
* Recap java programing - before staring using Maven, it needs to create a small code to use within a Maven project
> * A simple class with a main an a function (`app.java` stored on `/data/myjavaproject` inside the Maven container)
>>```
>>public class app {
>>    public static void main(String[] args) {
>>        String toPrint = "none";
>>        if (args.length >= 1) {
>>                toPrint = args[0];
>>        }
>>        myPrintln(toPrint);
>>    }
>>    public static void myPrintln(String input) {
>>        System.out.println(input);
>>    }
>>}
>>```
> * To compile and run the code (`javac` to compile and` java` to run)
>>```
>>docker exec -it maven sh -c "cd /data/myjavaproject/&&javac app.java&&java app test-text"
>># To run the code from a folder different of the the folder that the class file is stored in (run without changing directory)
>># java -classpath directory_to_program Program
>>docker exec -it maven sh -c "javac /data/myjavaproject/app.java&&java -classpath /data/myjavaproject app test-text"
>>```
> * **OBS!** The maven continer has already a jdk installed in.
* Before continuing to work on java and maven, I need to learn little bit about `make`,the tool that usually is used for linux projects especially c/c++
> * I add `make` to the maven container
> * I didn't go deep to `make`manuals. I just tried to create a simple `Makefile` for a simple java project that has only one java file with a class named app. according to the offcial guile here (https://www.gnu.org/software/make/manual/make.html) a simple `Makefile` to compile and run a java file is something like below : (It defines first compilers and then files. Some directions such as RM has already defined some other such as JC or JCR were not and needed to be defined. )
>>```
>>JC = javac
>>JCR = java
>>classes = app
>>
>>runapp : $(classes)
>>	$(JCR) $(classes)
>>
>>javaapp = app.java
>>
>>app : $(javaapp)
>>	$(JC) $(javaapp)
>>
>>clean:
>>	$(RM) *.class *~
>>```
> * To improve the Makefile above, I followd the example here (https://stackoverflow.com/questions/9580566/missing-separator-in-makefile#9580615), `.SUFFIXES` , `default`, `RM` have been already difined. Other directions and values such as `JS`, `JCR` , `CLASSES` are dfined here in the `Makefile`
>>```
>>JC = javac
>>JCR = java
>>
>>.SUFFIXES: .java .class
>>.java.class:
>>	$(JC) $*.java
>>
>>CLASSES = \
>>	app.java
>>
>>default: classes exec-app
>>
>>classes: $(CLASSES:.java=.class)
>>
>>clean:
>>	$(RM) *.class *~
>>
>>exec-app: classes
>>	set -e
>>	$(JCR) app
>>```
> * References :
>> * https://makefiletutorial.com/
>> * https://www.gnu.org/software/make/manual/make.html
>> * https://www.gnu.org/software/make/manual/html_node/Suffix-Rules.html
>> * https://stackoverflow.com/questions/33611044/java-makefile-is-making-class-files-instead-of-make-file
>> * http://profesores.elo.utfsm.cl/~agv/elo329/Java/javamakefile.html
* To make it more look like a project, a `src` folder was created and the java file `app.java` moved to folder. `Makefile` was also updared to read sources from `src` and storing compiled files (`.class`) to a folder named `bin`.
>```
># makkefile begins
># define a variable for compiler flags (JFLAGS)
># define a variable for the compiler (JC)  
># define a variable for the Java Virtual Machine (JVM)
># define a variable for a parameter. When you run make, you could use:
>JFLAGS = -g
>JC = javac
>JVM = java
>SRCDIR=src
>BINDIR=bin
>
># Clear any default targets for building .class files from .java files; we 
># will provide our own target entry to do this in this makefile.
># make has a set of default targets for different suffixes (like .c.o) 
># Currently, clearing the default for .java.class is not necessary since 
># make does not have a definition for this target, but later versions of 
># make may, so it doesn't hurt to make sure that we clear any default 
># definitions for these
>.SUFFIXES: .java .class
>
># Here is our target entry for creating .class files from .java files 
># This is a target entry that uses the suffix rule syntax:
>#	DSTS:
>#		rule
># DSTS (Dependency Suffix     Target Suffix)
># 'TS' is the suffix of the target file, 'DS' is the suffix of the dependency 
>#  file, and 'rule'  is the rule for building a target	
># '$*' is a built-in macro that gets the basename of the current target 
># Remember that there must be a < tab > before the command line ('rule') 
>.java.class:
>	$(JC) -d $(BINDIR)/. $(JFLAGS) $*.java
>
># CLASSES is a macro consisting of N words (one for each java source file)
># When a single line is too long, use \<return> to split lines that then will be
># considered as a single line. For example:
># NAME = Camilo \
>#         Juan 
># is understood as
># NAME = Camilo        Juan
>CLASSES = \
>	$(SRCDIR)/app.java
>
># MAIN is a variable with the name of the file containing the main method
>MAIN = app
>
># the default make target entry
># for this example it is the target classes
>default: classes run
>
># Next line is a target dependency line
># This target entry uses Suffix Replacement within a macro: 
># $(macroname:string1=string2)
># In the words in the macro named 'macroname' replace 'string1' with 'string2'
># Below we are replacing the suffix .java of all words in the macro CLASSES 
># with the .class suffix
>classes: $(CLASSES:.java=.class)
>
># Next two lines contain a target for running the program
># Remember the tab in the second line.
># $(JMV) y $(MAIN) are replaced by their values
>run: $(BINDIR)/$(MAIN).class
>	set -e
>	$(JVM) -classpath $(BINDIR)/ $(MAIN)
>
># this line is to remove all unneeded files from
># the directory when we are finished executing(saves space)
># and "cleans up" the directory of unneeded .class files
># RM is a predefined macro in make (RM = rm -f)
>#
>clean:
>	$(RM) -r $(BINDIR)/*.class *~
>```
* **OBS!** In a `Makefile` the command under a section always start with a tab (a real tab, not spaces instead tab) otherwise `make` shows an error about missing command seperators.
* Create a maven project
> * A Maven project has its own structure that looks for files on some specified folders
> * Acording to here (https://dev.to/nilan/getting-started-with-maven-a-beginners-guide-to-java-build-automation-41f2) , to create a maven project, the command below can be used ( the command creates and downloads all plugins need for a simple hello world project
>>```
>>mvn archetype:generate \
>>    -DgroupId=com.myapp.helloworld  \
>>    -DartifactId=my-app  \
>>    -DarchetypeArtifactId=maven-archetype-quickstart  \
>>    -DinteractiveMode=false
>>```
> * To test how maven works, from `my-app` folder where `pom.xml` is stored in
>>```
>># to compile
>>mvn compile
>># to run tests
>>mvn test
>># to create a jar file
>>mvn package
>>```
> * `pom.xml` file created by the mvn generate is very simple xml file ( there are some descriptions for each part on this link https://mkyong.com/maven/how-to-create-a-java-project-with-maven/)
>>```
>><project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>>  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
>>  <modelVersion>4.0.0</modelVersion>
>>  <groupId>com.myapp.helloworld</groupId>
>>  <artifactId>my-app</artifactId>
>>  <packaging>jar</packaging>
>>  <version>1.0-SNAPSHOT</version>
>>  <name>my-app</name>
>>  <url>http://maven.apache.org</url>
>>  <dependencies>
>>    <dependency>
>>      <groupId>junit</groupId>
>>      <artifactId>junit</artifactId>
>>      <version>3.8.1</version>
>>      <scope>test</scope>
>>    </dependency>
>>  </dependencies>
>></project>
>>```
> * `pom.xml` file has tag named `project` with some attributes that define or explain the version of the pom file. `modelVersion`  is a version that is used to define maven files and folders structurs for the project. `artifactId` used on the command line to create a maven project is used as the main folder name. All source files stored in a specefic path that started with `src/main/java` (continued with the path defined by `groupId`, in the case above `src/main/java/com/myapp/helloworld`). Tests are stored on `src/test/java/com/myapp/helloworld`.
> * This maven template project has two files , `my-app/src/main/java/com/myapp/helloworld/App.java` , `my-app/src/test/java/com/myapp/helloworld/AppTest.java`.
> * `pom.xml` also defines a dependency with is `junit` used by tests
> `my-app/src/main/java/com/myapp/helloworld/App.java` :
>>```
>>package com.myapp.helloworld;
>>
>>/**
>>* Hello world!
>>*
>>*/
>>public class App 
>>{
>>    public static void main( String[] args )
>>    {
>>        System.out.println( "Hello World!" );
>>    }
>>}
>>```
> `my-app/src/test/java/com/myapp/helloworld/AppTest.java` :
>>```
>>package com.myapp.helloworld;
>>
>>import junit.framework.Test;
>>import junit.framework.TestCase;
>>import junit.framework.TestSuite;
>>
>>/**
>>* Unit test for simple App.
>>*/
>>public class AppTest 
>>    extends TestCase
>>{
>>    /**
>>     * Create the test case
>>     *
>>     * @param testName name of the test case
>>     */
>>    public AppTest( String testName )
>>    {
>>        super( testName );
>>    }
>>
>>    /**
>>     * @return the suite of tests being tested
>>     */
>>    public static Test suite()
>>    {
>>        return new TestSuite( AppTest.class );
>>    }
>>
>>    /**
>>     * Rigourous Test :-)
>>     */
>>    public void testApp()
>>    {
>>        assertTrue( true );
>>    }
>>}
>>```
> * To add a real test to the template code created by maven, first I added a function named `AppFunc` to the main class `App`
>>```
>>    public static int AppFunc( String inarg )
>>    {
>>        return inarg.length();
>>    }
>>```
> * And then a test for `AppFunc` was added to `AppTest`
>>```
>>    public void testAppFunc()
>>    {
>>        System.out.println( "Test App.AppFunc!" );
>>        assertTrue( App.AppFunc("my") == 2 );
>>    }
>>```
> * However all that I did is only to work with Maven not framework such as Junit, they can be spiked and learn in a sperated story
> * As `pom.xml` file there is no need to define source and test files one by one on the pom file, all java files created on defined paths on `pom.xml` will be compiled and tested if they have test files
> * To have more java source files and tests for them I added two new files to the project, `App2.java` alongside `App.java`and `App2test.java` alongside `AppTest.java`. Now by running `mvn test` 4 tests are run.
> * The code has two mains now, and it make it complicated to build a project and run it, Now I update the code, to have only one main and use function from a library insted function inside the main class
>> * Now the project has two files, App and AppLibe. App use a function from AppLib. Tests also were updated to test only function that are placed on AppLib.
>> * Everythings work fine, only the jar created by `mvn package` has no defined main to run.
>> * I found there are two solutions to run a Maven jar file
>> 1. using `mvn exec:java`
>> 2. using `java -jar`
> * Both of the solutions above need to update pom file with a new section `<build> </build>`. The build section is added inside of the section `<project></project>`.
> * To use method 1 (runing by using `mvn`)
>>```
>>  <build>
>>    <plugins>
>>      <plugin>
>>        <groupId>org.apache.maven.plugins</groupId>
>>        <artifactId>maven-jar-plugin</artifactId>
>>        <version>3.0.2</version>                    
>>        <configuration>
>>          <archive>
>>            <manifest>
>>              <addClasspath>true</addClasspath>
>>              <mainClass>com.myapp.helloworld.App</mainClass>
>>            </manifest>
>>          </archive>
>>        </configuration>
>>      </plugin>
>>    </plugins>
>>  </build>
>>```
> * To run jar far after builing by using `mvn`, from the fe my-app folder
>>```
>>mvn exec:java -Dexec.mainClass="main" -Dexec.args="sdafhjsdkfh"
>>#or (main has already defined in the pom file becuase of the package
>>mvn exec:java -Dexec.args="test-text"
>>#
>># To hide extra INFO or DEBUG infos
>>mvn exec:java -q -Dexec.args="test-text"
>>```
> * To use method 1 (runing by using `java`)
>>```
>>  < build>
>>    < plugins>
>>      < plugin>
>>        < groupId>org.codehaus.mojo< /groupId>
>>        < artifactId>exec-maven-plugin< /artifactId>
>>        < version>1.2.1< /version>
>>        < executions>
>>          < execution>
>>            < goals>
>>              < goal>java< /goal>
>>            < /goals>
>>          < /execution>
>>        < /executions>
>>        < configuration>
>>          < mainClass>com.myapp.helloworld.App< /mainClass>
>>        < /configuration>
>>      < /plugin>
>>    < /plugins>
>>  < /build>
>>```
> * To run jar far after builing by using `java`, from the fe my-app folder
>>```
>>java -jar target/my-app-1.0-SNAPSHOT.jar test-text
>>```
> * As seen above each solution is implemented through a build pluging that updated jar files/configurations under building proccess.
> * **OBS!** it's possible to have the both sections above in a project and use both methodes to run the jar file
>>```
>>  <build>
>>    <plugins>
>>      <plugin>
>>        <groupId>org.apache.maven.plugins</groupId>
>>        <artifactId>maven-jar-plugin</artifactId>
>>        <version>3.0.2</version>
>>        <configuration>
>>          <archive>
>>            <manifest>
>>              <addClasspath>true</addClasspath>
>>              <mainClass>com.myapp.helloworld.App</mainClass>
>>            </manifest>
>>          </archive>
>>        </configuration>
>>      </plugin>
>>      <plugin>
>>        <groupId>org.codehaus.mojo</groupId>
>>        <artifactId>exec-maven-plugin</artifactId>
>>        <version>1.2.1</version>
>>        <executions>
>>          <execution>
>>            <goals>
>>              <goal>java</goal>
>>            </goals>
>>          </execution>
>>        </executions>
>>        <configuration>
>>          <mainClass>com.myapp.helloworld.App</mainClass>
>>        </configuration>
>>      </plugin>
>>    </plugins>
>>  </build>
>>```
>> * References :
>>> * https://medium.com/remkitech/in-maven-pom-xml-specify-the-main-class-in-the-manifest-file-a0d9764bc9f0
>>> * https://www.sonatype.com/maven-complete-reference/android-application-development-with-maven
>>> * https://stackoverflow.com/questions/19850956/maven-java-the-parameters-mainclass-for-goal-org-codehaus-mojoexec-maven-p#21346201
>>> * https://maven.apache.org/shared/maven-archiver/examples/classpath.html
>>> * https://www.sohamkamani.com/java/cli-app-with-maven/
* The Maven project that I worked on untill now was to create jar files which can be used to run by `java` or other jar file. As I found Maven can be used to build Servlet projects also. For example to run under Tomcat. I don't see big differences between standalon projects and Serverlet projects. (Java web application project).
* There are several maven templates that can be used to create a project by using `mvn`, I used one of them until now ( `maven-archetype-quickstart`), a list of available templates can be found herer  https://maven.apache.org/guides/introduction/introduction-to-archetypes.html
* As I see archetypes list , the list not cover all available archetypes, for example `android-quickstart` is not in the list. The list covers only default templates.
* As used before a maven command to create new projects looks like below : (a general command)
>```
>mvn archetype:generate \
>    -DarchetypeGroupId=org.apache.maven.archetypes \
>    -DarchetypeArtifactId=maven-archetype-quickstart \
>    -DarchetypeVersion=1.5 \
>    -DgroupId=com.myapp.helloworld \
>    -DartifactId=my-app \
>    -DinteractiveMode=false
>```
* If `-DarchetypeGroupId` is not defined, `org.apache.maven.archetypes` is used as default `archetypeGroupId`, for example the simple command I used to create my-app was something like below:
>```
>mvn archetype:generate \
>    -DarchetypeArtifactId=maven-archetype-quickstart \
>    -DgroupId=com.myapp.helloworld \
>    -DartifactId=my-app \
>    -DinteractiveMode=false
>```
* A general command should be something below : (in the my example `my-app` , I didn't set `DgroupId` with a company name
>```
>mvn archetype:generate \
>    -DarchetypeGroupId=< archetype-group> \
>    -DarchetypeArtifactId=< archetype>\
>    -DarchetypeVersion=< version> \
>    -DgroupId=< your.company> \
>    -DartifactId=< app-name> \
>    -DinteractiveMode=false
>```
* Back to `archetypeGroupId` , there are some custom groups that offere new tmplates that are not available in the group `org.apache.maven.archetypes`, for example I found group `de.akquinet.android.archetypes` offeres android project template
>```
>mvn archetype:generate \
>    -DarchetypeGroupId=de.akquinet.android.archetypes \
>    -DarchetypeArtifactId=android-quickstart \
>    -DgroupId=your.company \
>    -DartifactId=my-androidapp \
>    -DinteractiveMode=false
>```
* web application offered by `org.apache.maven.archetypes` (https://maven.apache.org/archetypes/index.html) is very simple project that has only a javascript file, no main class used by Tomcat has added to. But the maven project can built war
>```
>mvn archetype:generate \
>    -DarchetypeGroupId=org.apache.maven.archetypes \
>    -DarchetypeArtifactId=maven-archetype-webapp \
>    -DgroupId=your.company \
>    -DartifactId=my-webapp \
>    -DinteractiveMode=false
>```
* I found there is another official `archetypeGroupId` from tomcat developer teams, that offers a servlet web application with cover all parts needed for a profesional servlet project but the project is very old. The last update is from 2013. I tried to create a package from the template but it didn't work. It faces several issue , mostly related to the version of plugings and dependencies. However project can be created with the command below : ( **OBS!** as i siad the created template didn't work corrrectly)
>```
>mvn archetype:generate \
>    -DarchetypeGroupId=org.apache.tomcat.maven \
>    -DarchetypeArtifactId=tomcat-maven-archetype \
>    -DarchetypeVersion=2.3 \
>    -DgroupId=com.hossjava \
>    -DartifactId=my-webapp \
>    -DinteractiveMode=false
>```
* **OBS!** There is a web site named `https://mvnrepository.com`  that has listed all maven repositories. I found serverlet template from this site `https://mvnrepository.com/artifact/org.apache.tomcat.maven/tomcat-maven-archetype`.
* Many of links that I found through trying to fix issues to create package for tomcat template were from many years ago, 2012, 2013.
* There is a mvn command that list all available archetypes `mvn archetype:generate` (https://maven.apache.org/archetype/maven-archetype-plugin/usage.html)
> * https://stackoverflow.com/questions/42525139/maven-build-compilation-error-failed-to-execute-goal-org-apache-maven-plugins#42525941
> * https://stackoverflow.com/questions/36427868/failed-to-execute-goal-org-apache-maven-pluginsmaven-surefire-plugin2-12test#36429564
> * https://stackoverflow.com/questions/13170860/failed-to-execute-goal-org-apache-maven-pluginsmaven-surefire-plugin2-10test
> * https://stackoverflow.com/questions/13637627/how-do-i-deploy-a-maven-created-webapp-to-tomcat
* There are some other sites that I found that explain how to create a servlet project based of the simple template `maven-archetype-webapp` from `org.apache.maven.archetypes`
> * https://medium.com/@AlexanderObregon/creating-and-deploying-java-web-applications-using-maven-and-tomcat-d5cb9a81824a
> * https://www.w3schools.blog/eclipse-maven-servlet-hello-world
> * https://github.com/mkyong/maven-examples/blob/master/java-project/README.md
* References :
> * https://maven.apache.org/
> * https://maven.apache.org/guides/introduction/introduction-to-archetypes.html
> * https://maven.apache.org/archetypes/index.html
> * https://mkyong.com/maven/how-to-create-a-web-application-project-with-maven/
> * https://faculty.kutztown.edu/spiegel/CSc521/Building%20SimpleServlet%20with%20Maven.pdf
> * https://github.com/maciejwalkowiak/servlet3-maven-archetype
> * https://mvnrepository.com
> * https://dev.to/nilan/getting-started-with-maven-a-beginners-guide-to-java-build-automation-41f2
> * https://www.vogella.com/tutorials/JUnit/article.html
* Summary : 
> * I tried several archetypes, simple archetypes such as `maven-archetype-quickstart` , `maven-archetype-webapp` worked with out problem but others such as android or tomcat were little bit complicated. 
> * I think they are only a raw projects that need to be updated to a real project before compile or creating projects.
> * However the goal of this part was to know more about Maven, and to have a hands on the real world I tried some simple java codes.
> * I think it works nice. Some IDE such as Eclips suport maven internally and have some maven templates to start projects.
> * I used a maven container to test things that I learnd. Usually a container is used/accessed via a terminal, I don't know how it can be work visually. Perhaps services such as RDP can help but I didn't test it.
* References:
> * https://hub.docker.com/_/maven/
> * https://mkyong.com/maven/how-to-create-a-java-project-with-maven/
> * https://github.com/mkyong/maven-examples
> * https://dev.to/nilan/getting-started-with-maven-a-beginners-guide-to-java-build-automation-41f2
> * https://www.sohamkamani.com/java/cli-app-with-maven/

## Gradle

## Jupiter (NOT Jupyter)
> * **OBS!** *Jupyter/JupyterLab is another project (https://jupyter.org/) that is a web-based interactive development environment for notebooks, code, and data. Its flexible interface allows users to configure and arrange workflows in data science, scientific computing, computational journalism, and machine learning. A modular design invites extensions to expand and enrich functionality.*
* Jupiter is a part of Junit that is called **JUnit Jupiter**
* It has devined as a part of **JUnit 5** (JUnit 5 = JUnit Platform + JUnit Jupiter + JUnit Vintage), it seems there was no JUnit Jupiter on JUnit 4 or 3.
* As explained on the official site (https://junit.org/junit5/docs/current/user-guide/)
> * **JUnit Jupiter** is the combination of the programming model and extension model for writing tests and extensions in JUnit 5. The Jupiter sub-project provides a TestEngine for running Jupiter based tests on the platform.
> * JUnit Vintage provides a TestEngine for running JUnit 3 and JUnit 4 based tests on the platform. It requires JUnit 4.12 or later to be present on the class path or module path.
* I have already used JUnit on a simple project that coded to learn Maven
* As I see JUnit Jupiter is the Junit5 and the name is used to distinguish version 5 with previous versions.
* There are some differences between writing test on JUnit 4 or older and JUnit 5.
> * JUnit 4 is coded with extending a test class by using `TestCase` defind by JUnit, A simple test code on JUnit 4 looks somthing like below :
>>```
>>package com.myapp.helloworld;
>>
>>import junit.framework.Test;
>>import junit.framework.TestCase;
>>import junit.framework.TestSuite;
>>
>>/**
>>* Unit test for simple App.
>>*/
>>public class AppLibTest 
>>    extends TestCase
>>{
>>    /**
>>     * Create the test case
>>     *
>>     * @param testName name of the test case
>>     */
>>    public AppLibTest( String testName )
>>    {
>>        super( testName );
>>    }
>>
>>    /**
>>     * @return the suite of tests being tested
>>     */
>>    public static Test suite()
>>    {
>>        return new TestSuite( AppLibTest.class );
>>    }
>>
>>    public void testAppFunc()
>>    {
>>        assertTrue( true );
>>    }
>>}
>>```
> * JUnit 5 or JUnit Jupiter is little bit different, 
>>```
>>/**
>>* JUnit Jupiter test for simple App.
>>*/
>>package com.myapp.helloworld;
>>
>>import static org.junit.jupiter.api.Assertions.assertTrue;
>>import org.junit.jupiter.api.Test;
>>
>>class AppJUnitJupiterTests {
>>    private final App myapp = new App();
>>    @Test
>>    void testApp() {
>>        assertTrue( true );
>>    }
>>}
>>```
> * It also needs to add some dependencies and properties to `pom.xml`, (**OBS!** the properties part helps to make a pom.xml more readable by adding some pre-definations that are going to be used by other parts)
>> * properties : it defines version of some plugins and dependencies used by the project
>>>```
>>>  <properties>
>>>    <maven.compiler.source>1.8</maven.compiler.source>
>>>    <maven.compiler.target>1.8</maven.compiler.target>
>>>    <junit-jupiter.version>5.1.0</junit-jupiter.version>
>>>    <!-- optional : if we want to use a junit4 specific version -->
>>>    <junit.version>4.12</junit.version>
>>>  </properties>
>>>```
>> * dependencies : it is updated with adding dependencies for JUnit 4 and 5
>>>```
>>>  <dependencies>
>>>    <!--JUnit Jupiter Engine to depend on the JUnit5 engine and JUnit 5 API -->
>>>    <dependency>
>>>      <groupId>org.junit.jupiter</groupId>
>>>      <artifactId>junit-jupiter-engine</artifactId>
>>>      <version>${junit-jupiter.version}</version>
>>>      <scope>test</scope>
>>>    </dependency>
>>>    <!--JUnit Jupiter Engine to depend on the JUnit4 engine and JUnit 4 API  -->
>>>    <dependency>
>>>      <groupId>org.junit.vintage</groupId>
>>>      <artifactId>junit-vintage-engine</artifactId>
>>>      <version>${junit-jupiter.version}</version>
>>>    </dependency>
>>>    <!-- Optional : override the JUnit 4 API version provided by junit-vintage-engine -->
>>>    <dependency>
>>>      <groupId>junit</groupId>
>>>      <artifactId>junit</artifactId>
>>>      <version>${junit.version}</version>
>>>      <scope>test</scope>
>>>    </dependency>
>>>  </dependencies>
>>>```
* Summey :
> * Jupiter is the name of new method to develop tests started by JUnit 5
> * It seems coding test with JUnit 5 are faster than 4. But I'm not sure it's more readable than 4 :)
* References:
> * https://junit.org/junit5/docs/current/user-guide/
> * https://github.com/junit-team/junit5-samples/blob/r5.11.0/junit5-jupiter-starter-maven/pom.xml
> * https://stackoverflow.com/questions/47158583/executing-junit-4-and-junit-5-tests-in-a-same-build#47158584

## spring and Spring boot
* The Spring Framework is free and open source software
* The current version is 6.2
* The Spring Framework includes several modules that provide a range of services such as `Spring Core Container`, `Aspect-oriented programming` (https://en.wikipedia.org/wiki/Spring_Framework#Modules)
* Spring Boot :  Spring Boot Extension is Spring's convention-over-configuration solution for creating stand-alone, production-grade Spring-based Applications that you can "just run" https://en.wikipedia.org/wiki/Spring_Boot
* I found a tutorial here https://www.baeldung.com/spring-tutorial, acording to this tutorial:
> The Spring Framework is a mature, powerful and highly flexible framework focused on building web applications in Java. One of the core benefits of Spring is that it takes care of most of the low-level aspects of building the application to allow us to actually focus on features and business logic.
* There is a realy good start here https://www.baeldung.com/spring-why-to-choose. Acording to this guide
> * The idea of using frameworks is to focus directlly on the project instead other things such as how to create project, how connect to other sercices which used by our product, and so on
> * So using a framework such as Spring is not mandatory to develop a product but using them opens an opportunity to use their ecosystems.
> * Back to other parts that I worked on before such as Maven, tomcat, Junit, I could see for example finding a template for servlet was not easy most of the were very old, Now according to the spring tutorial above, Spring framework offeres templates for many kind of projects, can emmbed servlet such as tomcat and run it and creat and standalone project, provide modules to connect to databases.... (**OBS!** when I searched to find Maven templates I could see many Spring templates for large variety of projects)
> * According to this part of the tutorial here (https://www.baeldung.com/spring-why-to-choose#spring-in-action), A template can be created easily by using this page https://start.spring.io/ then the code can be added to template and by using some code like below running the code/project
>>```
>>@SpringBootApplication
>>public class Application {
>>    public static void main(String[] args) {
>>        SpringApplication.run(Application.class, args);
>>    }
>>}
>>```
> * As I see on the example explained on Spring in Action part  (https://www.baeldung.com/spring-why-to-choose#spring-in-action), Spring take care of Web service, Database and other parts and the code only focus on data structers and define functionalities that are needed. (https://www.baeldung.com/spring-boot-start)
> * I didn't get all parts of the example still but I could see how it can make faster working on a project.
* References:
> * https://www.baeldung.com/spring-tutorial
> * https://en.wikipedia.org/wiki/Spring_Framework
> * https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/overview.html
> * https://start.spring.io/
> * https://github.com/eugenp/tutorials/tree/master

### Spring : Hands on 
* Here (https://www.codeburps.com/category/spring-boot) I found some simple example that I could follow how they work
* The first example ([How to create a basic Spring 6 project using Maven](https://www.codeburps.com/post/basic-spring-project-using-maven)) creates a simple spring+maven project that prints a simple text on screen, the example is used an Employee class that is improved on there example to work with REST and databases.
> *  source code can be found here https://github.com/nkchauhan003/spring-demo
> *  The guide uses a simple `Hello world` Maven project template (not Spring(s) templates) which is good to understand how to create a basic Spring project (**OBS!** I changed the `groupId` from `com.tb` to `com.hossjava`)
>>```
>>mvn archetype:generate \
>>    -DarchetypeArtifactId=maven-archetype-quickstart \
>>    -DgroupId=com.hossjava \
>>    -DartifactId=my-springapp \
>>    -DinteractiveMode=false
>>```
> * The project created by the command above has only `junit` as dependency, and has no buliding pard. Spring dependency and building part as explaind on https://www.codeburps.com/post/basic-spring-project-using-maven  were added to `pom.xml`
>>```
>>.
>>.
>>  <properties>
>>    <failOnMissingWebXml>false</failOnMissingWebXml>
>>  </properties>
>>.
>>.
>>    <dependency>
>>      <groupId>org.springframework</groupId>
>>      <artifactId>spring-context</artifactId>
>>      <version>6.0.11</version>
>>    </dependency>
>> .
>> .
>> .
>>   <build>
>>    <plugins>
>>      <plugin>
>>        <artifactId>maven-compiler-plugin</artifactId>
>>        <version>3.11.0</version>
>>        <configuration>
>>          <source>17</source>
>>          <target>17</target>
>>        </configuration>
>>      </plugin>
>>    </plugins>
>>  </build>
>>.
>>.
>>
>>```
> * The example uses context of springframework to use annotation, some `Been` is defined with in the class AppConfig with using `@Bean`  annotate. On the main annotates is loaded by using `AnnotationConfigApplicationContext`
>>```
>>public class App {
>>    public static void main(String[] args) {
>>        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
>>        applicationContext.scan("com.hossjava");
>>
>>        var employeeBean = (Employee) applicationContext.getBean("employee");
>>        System.out.println("Employee Name: " + employeeBean.getName());
>>
>>    }
>>}
>>```
> * `employee`  used through `getBean`  has been defined on AppConfig. It is a function that return an instance of `Employee` class (`Employee.java`)
> * In other words, contexts are created by using beans.
> * The example above uses `spring-context`  from `org.springframework` ( one of org.springframework modules)
> * As explained on the tuturial ( https://www.codeburps.com/post/basic-spring-project-using-maven) an IDE named IntelliJ IDEA is used that somehow knows how to run the code and which part of the code is the main. As I learned before there is a need to define a start point for a java package somehow, jar has its own mechanism, war itsown and also maven project defined it by using its own plugins. for example to run a code directly by java progrom, there is a plugin named `exec-maven-plugin`  that defines the start point via pom.xml file and mvn command can use it by command `mvn exex:java`
> * It sould be a plugin and its mvn command line to run spring projects (not spring boot) but I didn't fine it. I used `exec-maven-plugin` by adding the lines below to the pom.xml file to build sextion
>>```
>>     <plugin>
>>       <groupId>org.codehaus.mojo</groupId>
>>       <artifactId>exec-maven-plugin</artifactId>
>>       <version>1.2.1</version>
>>       <executions>
>>         <execution>
>>           <goals>
>>             <goal>java</goal>
>>           </goals>
>>         </execution>
>>       </executions>
>>       <configuration>
>>         <mainClass>com.hossjava.App</mainClass>
>>       </configuration>
>>     </plugin>
>>```
> * After adding the part above the code can be run by using `mvn exec:java`
> * I found another context/bean example here https://medium.com/@metinoktayboz/spring-framework-the-context-english-6c0adc17566b
* I hade a look on `IntelliJ IDEA` to see what it offers to code, It is a JAVA base IDE to code on Java and Kotlin and Groovy, it seems the IJ has an Apache 2 license but is not free to use. 
> * there are two version of  IntelliJ IDEA Community Edition and Ultimate. Ultimate is not free.
> * As I understood there was an othe IDE named JetBrains. IJ (IntelliJ IDEA)  is based on JetBrains
> * I tested IJ to see how it works. I didn'st see something special to make it intersting to move from SubLime to IJ!. Moreover I found it could run the compiled code when the main file is opened. So there is no secret, it is the same as addressing the main via command line to java :)
> *  References :
>> * https://en.wikipedia.org/wiki/IntelliJ_IDEA
* References:
> * https://www.codeburps.com/category/spring-boot
> * https://www.codeburps.com/post/basic-spring-project-using-maven
> * https://github.com/nkchauhan003/spring-demo
> * https://medium.com/@metinoktayboz/spring-framework-the-context-english-6c0adc17566b

## Apache Spark
* 
* References:
> * 

## kafka
*
* References:
> * https://developer.confluent.io/what-is-apache-kafka/

## kotlin
*
* References:
> * 
