package grails.plugin.mongodb.jodatime.marshaller

import grails.plugin.mongodb.jodatime.query.builders.JodaTimeMongoQueryBuilder
import groovy.transform.InheritConstructors
import org.bson.Document
import org.grails.datastore.mapping.engine.types.AbstractMappingAwareCustomTypeMarshaller
import org.grails.datastore.mapping.model.PersistentProperty
import org.grails.datastore.mapping.query.Query
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

import static grails.plugin.mongodb.jodatime.JodaTimeConstant.FILED_TYPE
import static grails.plugin.mongodb.jodatime.JodaTimeConstant.ZONE
import static grails.plugin.mongodb.jodatime.query.builders.JodaTimeMongoQueryBuilder.TIME

@InheritConstructors
class DateTimeMarshaller extends AbstractMappingAwareCustomTypeMarshaller<DateTime, Document, Document> {

    static final String JODA_TYPE = DateTime.name

    JodaTimeMongoQueryBuilder jodaTimeMongoQueryBuilder

    @Override
    protected Object writeInternal(PersistentProperty property, String key, DateTime value, Document nativeTarget) {
        final converted = new Document()
        converted[FILED_TYPE] = JODA_TYPE
        converted[TIME] = value.millis
        converted[ZONE] = value.zone.ID
        nativeTarget.put(key, converted)
        return converted
    }

    @Override
    protected void queryInternal(PersistentProperty property, String key, Query.PropertyCriterion value, Document nativeQuery) {
        jodaTimeMongoQueryBuilder.buildQuery(property, key, JODA_TYPE, value, nativeQuery)
    }

    @Override
    protected DateTime readInternal(PersistentProperty property, String key, Document nativeSource) {
        final value = nativeSource.get(key)
        if (value?.getAt(FILED_TYPE) == JODA_TYPE) {
            return new DateTime((long) value[TIME], DateTimeZone.forID((String) value[ZONE]))
        }
        return null
    }
}
