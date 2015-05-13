/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.security.shiro.credentials;

import com.xiyiaoo.entity.User;
import com.xiyiaoo.util.UserPasswordEncryptor;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-4-27 上午10:10
 * 有重试次数限制的凭证（密码）验证器
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher implements UserPasswordEncryptor{
    public static final String CACHE_NAME = "RetryLimitHashedCredentialsMatcher";
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    /**
     * 缓存
     */
    private Cache<String, AtomicInteger> cache;
    /**
     * 限制重试次数
     */
    private int limitTimes = 5;

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        this.cache = cacheManager.getCache(CACHE_NAME);
    }

    /**
     * 校验凭证(密码)
     * @param token 凭证
     * @param info 认证信息
     * @return 是否通过
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String)token.getPrincipal();
        AtomicInteger retryCount = cache.get(username);
        if(retryCount == null) {
            retryCount = new AtomicInteger(0);
            cache.put(username, retryCount);
        }
        //重试+1
        if(retryCount.incrementAndGet() > limitTimes) {
            //重试次数过多
            throw new ExcessiveAttemptsException();
        }

        boolean matches = super.doCredentialsMatch(token, info);
        //验证成功则清除失败的重试次数
        if(matches) {
            cache.remove(username);
        }
        return matches;
    }

    public void setLimitTimes(int limitTimes) {
        this.limitTimes = limitTimes;
    }

    @Override
    public void encrypt(User user) {
        String salt = randomNumberGenerator.nextBytes().toHex();
        user.setSalt(salt);
        String password = hashProvidedCredentials(user.getPassword(), salt, getHashIterations()).toHex();
        user.setPassword(password);
    }

    @Override
    public boolean match(User user, String password) {
        try {
            password = hashProvidedCredentials(password, user.getSalt(), getHashIterations()).toHex();
            return password.equals(user.getPassword());
        } catch (Exception ignored) { }
        return false;
    }
}
