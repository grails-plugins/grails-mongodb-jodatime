package grails.plugin.mongodb.jodatime.marshaller

import grails.plugin.mongodb.jodatime.query.builders.JodaTimeMongoQueryBuilder
import groovy.transform.InheritConstructors
import org.bson.Document
import org.grails.datastore.mapping.engine.types.AbstractMappingAwareCustomTypeMarshaller
import org.grails.datastore.mapping.model.PersistentProperty
import org.grails.datastore.mapping.query.Query
import org.joda.time.DateTime
import org.joda.time.Interval

import static grails.plugin.mongodb.jodatime.JodaTimeConstant.FILED_TYPE
import static grails.plugin.mongodb.jodatime.query.builders.JodaTimeMongoQueryBuilder.INTERVAL_END
import static grails.plugin.mongodb.jodatime.query.builders.JodaTimeMongoQueryBuilder.INTERVAL_START

@InheritConstructors
class IntervalMarshaller extends AbstractMappingAwareCustomTypeMarshaller<Interval, Document, Document> {

    JodaTimeMongoQueryBuilder jodaTimeMongoQueryBuilder

    static final String JODA_TYPE = DateTime.name

    @Override
    protected Object writeInternal(PersistentProperty property, String key, Interval value, Document nativeTarget) {
        final converted = new Document()
        converted[FILED_TYPE] = JODA_TYPE
        converted[INTERVAL_START] = value.startMillis
        converted[INTERVAL_END] = value.endMillis
        nativeTarget.put(key, converted)
        return converted
    }

    @Override
    protected void queryInternal(PersistentProperty property, String key, Query.PropertyCriterion value, Document nativeQuery) {
        jodaTimeMongoQueryBuilder.buildQuery(property, key, JODA_TYPE, value, nativeQuery)
    }

    @Override
    protected Interval readInternal(PersistentProperty property, String key, Document nativeSource) {
        final value = nativeSource.get(key)
        if (value?.getAt(FILED_TYPE)== JODA_TYPE) {
            return new Interval((long) value[INTERVAL_START], (long) value[INTERVAL_END])
        }
        return null
    }
}
