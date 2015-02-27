Audit4j Catalina plugin for Tomcat 7,8.x and Jboss 8
====================================================

Audit4j Catalina plugin for audit HTTP requests with Catalina application container.

Usage:

Put audit4j-core-2.3.1.jar(or higher) and audit4j-tomcat-1.x.x.jar in the tomcat 'lib' directory.


Add the following configurations to Tomcat's server.xml file. The 'Engine' line is used to show context

```xml
<Listener className="org.audit4j.intregration.tomcat.AuditContextLifecycleListener"/>

<Engine name="Catalina" defaultHost="localhost"> 
	<Valve className="org.audit4j.intregration.tomcat.Audit4jTomcatValve"/>
```