package com.fiap.framesnap.infrastructure.video.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RedisConfigTest {

    private RedisConfig redisConfig;
    private RedisProperties redisProperties;

    @BeforeEach
    void setUp() {
        redisProperties = new RedisProperties();
        redisProperties.setHost("localhost");
        redisProperties.setPort(6379);
        redisProperties.setUsername("default");
        redisProperties.setPassword("test-password");

        redisConfig = new RedisConfig(redisProperties);
    }

    @Test
    void redisConnectionFactory_ShouldCreateFactoryWithCorrectConfiguration() {
        // Act
        LettuceConnectionFactory factory = redisConfig.redisConnectionFactory();

        // Assert
        RedisStandaloneConfiguration config = factory.getStandaloneConfiguration();
        assertEquals("localhost", config.getHostName());
        assertEquals(6379, config.getPort());
        assertEquals("default", config.getUsername());
        assertNotNull(config.getPassword());
    }

    @Test
    void stringRedisTemplate_ShouldCreateTemplateWithFactory() {
        // Arrange
        LettuceConnectionFactory factory = mock(LettuceConnectionFactory.class);

        // Act
        StringRedisTemplate template = redisConfig.stringRedisTemplate(factory);

        // Assert
        assertNotNull(template);
    }

    @Test
    void valueOperations_ShouldCreateOperationsWithTemplate() {
        // Arrange
        StringRedisTemplate template = mock(StringRedisTemplate.class);
        ValueOperations<String, String> mockOps = mock(ValueOperations.class);
        when(template.opsForValue()).thenReturn(mockOps);

        // Act
        ValueOperations<String, String> operations = redisConfig.valueOperations(template);

        // Assert
        assertNotNull(operations);
        assertSame(mockOps, operations);
    }
}