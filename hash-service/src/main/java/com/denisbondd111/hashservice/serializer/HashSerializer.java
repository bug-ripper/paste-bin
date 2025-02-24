package com.denisbondd111.hashservice.serializer;

import com.denisbondd111.hashservice.entity.Hash;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;


public class HashSerializer implements RedisSerializer<Hash> {
    private ObjectMapper objectMapper;

    public HashSerializer() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public byte[] serialize(Hash value) throws SerializationException {
        try {
            return objectMapper.writeValueAsBytes(value);
        } catch (JsonProcessingException e) {
            throw new SerializationException(e.getMessage(), e);
        }
    }

    @Override
    public Hash deserialize(byte[] bytes) throws SerializationException {
        if(bytes == null){
            return null;
        }

        try {
            return objectMapper.readValue(bytes, Hash.class);
        } catch (Exception e) {
            throw new SerializationException(e.getMessage(), e);
        }
    }
}
