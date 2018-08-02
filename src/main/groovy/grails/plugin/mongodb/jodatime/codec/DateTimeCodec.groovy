package grails.plugin.mongodb.jodatime.codec

import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

import static grails.plugin.mongodb.jodatime.JodaTimeConstant.FILED_TYPE
import static grails.plugin.mongodb.jodatime.JodaTimeConstant.ZONE
import static grails.plugin.mongodb.jodatime.marshaller.IntervalMarshaller.JODA_TYPE
import static grails.plugin.mongodb.jodatime.query.builders.JodaTimeMongoQueryBuilder.TIME

class DateTimeCodec implements Codec<DateTime> {
    @Override
    DateTime decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument()
        if (reader.readString(FILED_TYPE) == JODA_TYPE) {
            return new DateTime(
                    reader.readDateTime(TIME),
                    DateTimeZone.forID(reader.readString(ZONE)))
        }
        return null
    }

    @Override
    void encode(BsonWriter writer, DateTime value, EncoderContext encoderContext) {
        writer.writeStartDocument()
        writer.writeString(FILED_TYPE, JODA_TYPE)
        writer.writeDateTime(TIME, value.millis)
        writer.writeString(ZONE, value.zone.ID)
        writer.writeEndDocument()
    }

    @Override
    Class<DateTime> getEncoderClass() {
        return DateTime
    }
}
