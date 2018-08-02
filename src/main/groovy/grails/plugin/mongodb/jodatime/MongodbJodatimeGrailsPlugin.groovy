package grails.plugin.mongodb.jodatime

import grails.plugin.mongodb.jodatime.marshaller.DateTimeMarshaller
import grails.plugin.mongodb.jodatime.marshaller.IntervalMarshaller
import grails.plugin.mongodb.jodatime.query.builders.JodaTimeMongoQueryBuilder
import grails.plugins.Plugin
import org.joda.time.DateTime
import org.joda.time.Interval

class MongodbJodatimeGrailsPlugin extends Plugin {

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.3.6 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Mongodb Jodatime" // Headline display name of the plugin
    def author = "Your name"
    def authorEmail = ""
    def description = '''\
Persists Joda Time types to a Mongo database (using mongodb).
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/mongodb-jodatime"

    // Extra (optional) plugin metadata

    def license = "APACHE"

    def organization = [name: "Spantree Technology Group, LLC", url: "http://www.spantree.net/"]

    def developers = [[name: "Gary Turovsky", email: "gary@spantree.net"],
                      [name: "Kevin Greene", email: "kevin@spantree.net"],
                      [name: "Puneet Behl", email: "behl4485@gmail.com"]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

    Closure doWithSpring() {
        { ->
            jodaTimeMongoQueryBuilder(JodaTimeMongoQueryBuilder)
            intervalMarshaller(IntervalMarshaller, Interval)
            dateTimeMarshaller(DateTimeMarshaller, DateTime)

        }
    }

    void doWithDynamicMethods() {
        // TODO Implement registering dynamic methods to classes (optional)
    }

    void doWithApplicationContext() {
        // TODO Implement post initialization spring config (optional)
    }

    void onChange(Map<String, Object> event) {
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    void onConfigChange(Map<String, Object> event) {
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    void onShutdown(Map<String, Object> event) {
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
