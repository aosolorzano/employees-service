# Thorntail Example

This example takes a normal JAX-RS build, and wraps it into
a `-thorntail` runnable jar.


## Project `pom.xml`

This project is a traditional JAX-RS project, with maven packaging
of `war` in the `pom.xml`

    <packaging>war</packaging>

The project adds a `<plugin>` to configure `thorntail-maven-plugin` to
create the runnable `.jar`.

    <plugin>
      <groupid>io.thorntail</groupId>
      <artifactId>thorntail-maven-plugin</artifactId>
      <version>${version.thorntail}</version>
      <executions>
        <execution>
          <goals>
            <goal>package</goal>
          </goals>
        </execution>
      </executions>
    </plugin>

For this example we're letting the plugin auto detect what dependencies
we might need for our application, so no Thorntail specific dependencies
need to be added.


## Run

You can run it many ways:

* mvn clean package
* java -jar target/employees-service-0.2.0-thorntail.jar
* docker build -t employees-service:0.2.0 .
* docker run -d -p 8080:8080 --name employees-service-container employees-service:0.2.0

If you want to debug the container run: 

* docker run -it -p 8080:8080 --rm --name employees-service-container employees-service:0.2.0


## Use

Since Thorntail apps tend to support one deployment per executable, it
automatically adds a `jboss-web.xml` to the deployment if it doesn't already
exist.  This is used to bind the deployment to the root of the web-server,
instead of using the `.war`'s own name as the application context.

To access the JAX-RS Resource:

    http://localhost:8080/rest/employees/v1/findAll
