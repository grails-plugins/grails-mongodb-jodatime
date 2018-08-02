package grails.plugin.mongodb.jodatime.codec

import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext
import org.joda.time.Interval

import static grails.plugin.mongodb.jodatime.JodaTimeConstant.FILED_TYPE
import static grails.plugin.mongodb.jodatime.marshaller.IntervalMarshaller.JODA_TYPE
import static grails.plugin.mongodb.jodatime.query.builders.JodaTimeMongoQueryBuilder.INTERVAL_END
import static grails.plugin.mongodb.jodatime.query.builders.JodaTimeMongoQueryBuilder.INTERVAL_START

class IntervalCodec implements Codec<Interval> {
    @Override
    Interval decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument()
        if (reader.readString(FILED_TYPE) == JODA_TYPE) {
            return new Interval(
                    reader.readDateTime(INTERVAL_START),
                    reader.readDateTime(INTERVAL_END))
        }
        return null
    }

    @Override
    void encode(BsonWriter writer, Interval value, EncoderContext encoderContext) {
        writer.writeStartDocument()
        writer.writeString(FILED_TYPE, JODA_TYPE)
        writer.writeDateTime(INTERVAL_START, value.startMillis)
        writer.writeDateTime(INTERVAL_END, value.endMillis)
        writer.writeEndDocument()
    }

    @Override
    Class<Interval> getEncoderClass() {
        Interval
    }
}
