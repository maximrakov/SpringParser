package org.itmo.telegramservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TgUserDAO {
    public static final String HASH_KEY = "User";
    private RedisTemplate template;
    private RedisTemplate reverseTemplate;

    private RedisTemplate jwtTokens;

    @Autowired
    public TgUserDAO(@Qualifier("template") RedisTemplate template,
                     @Qualifier("template") RedisTemplate reverseTemplate,
                     @Qualifier("template") RedisTemplate jwtTokens) {
        this.template = template;
        this.reverseTemplate = template;
        this.jwtTokens = jwtTokens;
    }

    public void save(String login, int chatId) {
        template.opsForHash().put(HASH_KEY, login, chatId);
        reverseTemplate.opsForHash().put(HASH_KEY, Integer.toString(chatId), login);
    }

    public void saveJWT(int chatId, String jwt) {
        jwtTokens.opsForHash().put(HASH_KEY, chatId, jwt);
    }
    public int getChatId(String login) {
        return (int) template.opsForHash().get(HASH_KEY, login);
    }

    public String getLogin(Integer chatId) {
        return (String) reverseTemplate.opsForHash().get(HASH_KEY, chatId.toString());
    }

    public String getJWT(int charId) {
        return (String) jwtTokens.opsForHash().get(HASH_KEY, charId);
    }
}