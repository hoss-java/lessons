# Working around how to work with java codes (spiking)
## Spike how to run a simple code without using fremworks
1. A simple code named `HelloWord.java` was created

>```java
>public class HelloWorld {
>    public static void main(String[] args) {
>        System.out.println("Hello, World!");
>    }
>}
>```

2. As I found it can be run by a simple command `javac HelloWorld.java`, or `java  HelloWorld.java` (depends on the installed distribution of java). 
> * `javac` is a part of jdk. It means it needs to install `openjdk`
> * The latest jdk version that can be installed without adding manual repositories, is `openjdk-25-jdk-headless`.
> * As I see all versions 8, 11, 19, 20, 21, 22, 25 are available for install on Ubuntu 24
> * The default version installed on Ubuntu 24 is openjdk-21 (`update-alternatives --config java` can be used to see a list of installed jdk versions and switching between them if needed)

3. Using java inside a docker container - To keep the host clean and make it possible to use different versions of Java at the same time, a lightwight openjdk container based on Alpine was created. 
> * The lates version of openjdk offered on Alpine is 21. If it needs newer version it needs to change the container base to something else such as stable-slim (debian) or other kind of Debian base linux such as ubuntu.
> * For now jdk-21 under Alpine 3.20 is good enough for coming exercises
> * The container can be configured to access a shared folder on the host, It can be done via adding the path to file `.env`. For example the github folder can be shared with the container, in my case the local folder for the cloned github repo(s) is `~github-java`
>> `nano .env`
>>>```
>>># default env
>>>#
>>>DOCKER_DATA_PATH=~/github-java
>>>```
> * To compose openjdk container, from the inside of the container folder `/github-java/container/openjdk/`
>>```
>># To compose openjdk
>>docker compose up -d
>>
>># To remove and clean up openjdk and its related files
>>docker compose down --rmi local
>>```
> * A `README.md` was added to the openjdk container folder to keep trading with the changes on the openjdk container.
> * To Run a simple java code file.
>>```
>># file paths start from the root of the github-java because of the settings on the file `.env`
>> docker exec -it openjdk java lessons/w48/simple/HelloWorld.java
>>```
