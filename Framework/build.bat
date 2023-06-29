javac -d . *.java
jar -cf framework.jar ./etu1932
xcopy framework.jar "../Test-Framework/WEB-INF/lib"
cd "../Test-Framework/WEB-INF/classes"
javac -cp "../lib/framework.jar" -d . *.java
cd "../../"
jar -cf affiche.war .
xcopy affiche.war "C:\Program Files\Apache Software Foundation\Tomcat 10.0\webapps"