package com.denisbondd111.hashservice.service;

import com.denisbondd111.hashservice.entity.Hash;
import com.denisbondd111.hashservice.repository.HashRepository;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class HashGenerationService {
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = ALPHABET.length(); // 62
    private static final int HASH_LENGTH = 8;

    private HashRepository hashRepo;
    private Long nextNotUsedId;

    public HashGenerationService(HashRepository hashRepo) {
        this.hashRepo = hashRepo;
    }

    @PostConstruct
    public void init(){
        nextNotUsedId = hashRepo.getNextId();
    }

    public Hash generateHash(){

        HashFunction hashFunction = Hashing.murmur3_32();
        HashCode hashCode = hashFunction.hashLong(nextNotUsedId);
        int hashValue = hashCode.asInt(); // Получаем 32-битное целое число

        StringBuilder hashStringBuilder = new StringBuilder(HASH_LENGTH);

        // Генерируем 8 символов, используя остаток от деления на 62
        for (int i = 0; i < HASH_LENGTH; i++) {
            int index = Math.abs(hashValue % BASE); // Убеждаемся, что индекс положительный
            hashStringBuilder.insert(0, ALPHABET.charAt(index));
            // Сдвигаем хэш для следующей итерации (можно использовать побитовые операции)
            hashValue = hashValue >>> 1; // Сдвиг вправо на 1 бит для нового распределения
        }

        Hash hash = new Hash();
        hash.setId(nextNotUsedId++);
        hash.setHash(hashStringBuilder.toString());
        return hash;
    }

    public Hash generateHash(Long id){

        HashFunction hashFunction = Hashing.murmur3_32();
        HashCode hashCode = hashFunction.hashLong(id);
        int hashValue = hashCode.asInt();

        StringBuilder hashStringBuilder = new StringBuilder(HASH_LENGTH);

        for (int i = 0; i < HASH_LENGTH; i++) {
            int index = Math.abs(hashValue % BASE);
            hashStringBuilder.insert(0, ALPHABET.charAt(index));
            hashValue = hashValue >>> 1;
        }

        Hash hash = new Hash();
        hash.setId(id);
        hash.setHash(hashStringBuilder.toString());
        return hash;
    }

    public List<Hash> generateHash(Integer numberOfHashes){
        List<Hash> hashes = new ArrayList<>();
        for (int i = 0; i < numberOfHashes; i++) {
            hashes.add(generateHash());
        }
        return hashes;
    }
}
