package jackson.base;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.ConfigFeature;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

abstract public class JacksonBaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonBaseTest.class);

    protected ObjectMapper objectMapper;

    protected Iterable<ConfigFeature> featuresToEnable() {
        return Collections.emptyList();
    }

    protected Iterable<ConfigFeature> featuresToDisable() {
        return Collections.emptyList();
    }

    @Before
    public void setUpBeforeClass() {
        objectMapper = new ObjectMapper();
        enableFeatures();
        disableFeatures();
    }

    private void disableFeatures() {
        for (ConfigFeature feature : featuresToDisable()) {
            if (feature instanceof MapperFeature) {
                objectMapper.disable((MapperFeature) feature);
            } else if (feature instanceof SerializationFeature) {
                objectMapper.disable((SerializationFeature) feature);
            } else if (feature instanceof DeserializationFeature) {
                objectMapper.disable((DeserializationFeature) feature);
            } else {
                LOGGER.error("Trying to disable not supported config feature: " + feature.getMask());
                throw new IllegalArgumentException("Cannot disable config feature: " + feature.getMask());
            }
        }
    }

    private void enableFeatures() {
        for (ConfigFeature feature : featuresToEnable()) {
            if (feature instanceof MapperFeature) {
                objectMapper.enable((MapperFeature) feature);
            } else if (feature instanceof SerializationFeature) {
                objectMapper.enable((SerializationFeature) feature);
            } else if (feature instanceof DeserializationFeature) {
                objectMapper.enable((DeserializationFeature) feature);
            } else {
                LOGGER.error("Trying to enable not supported config feature: " + feature.getMask());
                throw new IllegalArgumentException("Cannot enable config feature: " + feature.getMask());
            }
        }
    }
}
