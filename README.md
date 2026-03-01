# JavaEE demo

This is the example of JavaEE MVC app by Andrei Korolev.

To run this example:

1. Make sure you have Java, JDK, Postgresql, Tomcat and Maven installed.
1. Clone the repo.
1. Change database credentials in `<root folder>/src/main/resources/db.properties`.
1. In `<root folder>` run ``mvn clean package``.
1. Move created `.war` file from folder `<root folder>/target` to `<your Tomcat folder>/webapps`.
1. Run Tomcat server:
    * Windows: ``Run %CATALINA_HOME%\bin\startup.bat``;
    * Linux/MacOs: ``Run %CATALINA_HOME%\bin\startup.sh``.