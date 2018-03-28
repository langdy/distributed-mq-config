package com.yjl.distributed.mq.config.common.interceptor.handler;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对于注入攻击不做任何处理
 * 
 * @author zhaoyc@1109
 * @version 创建时间：2017年10月18日 上午10:41:32
 */
public class InjectionAttackPassHandle implements InjectionAttackHandler {


    private InjectionAttackPassHandle() {}

    public static InjectionAttackPassHandle getInstance() {
        return SingletonDefaultInjectionAttackHandler.INSTANCE;
    }

    @Override
    public boolean isInjectionAttack(String rawCharacters) {
        return this.isSqlInjectionAttack(rawCharacters) || this.isXSSInjectionAttack(rawCharacters)
                || this.isSpecialCharactersInjectionAttack(rawCharacters);
    }

    @Override
    public boolean isInjectionAttack(String parameters, String[] ignoreStrings) {
        return false;
    }

    @Override
    public boolean isSqlInjectionAttack(String rawCharacters) {
        return false;
    }

    @Override
    public boolean isSqlInjectionAttack(String rawCharacters, String[] ignoreStrings) {
        return false;
    }

    @Override
    public boolean isXSSInjectionAttack(String rawCharacters) {
        return false;
    }

    @Override
    public boolean isXSSInjectionAttack(String rawCharacters, String[] ignoreStrings) {
        return false;
    }

    @Override
    public boolean isSpecialCharactersInjectionAttack(String rawCharacters) {
        return false;
    }

    @Override
    public boolean isSpecialCharactersInjectionAttack(String rawCharacters,
            String[] ignoreStrings) {
        return false;
    }

    @Override
    public String filter(String rawCharacters) {
        return rawCharacters;
    }

    @Override
    public String filterSqlInjection(String rawCharacters) {
        return rawCharacters;
    }

    @Override
    public String filterXSSInjection(String rawCharacters) {
        return rawCharacters;
    }

    @Override
    public String filterSpecialCharacters(String rawCharacters) {
        return rawCharacters;
    }

    @Override
    public void attackHandle(HttpServletRequest request, HttpServletResponse response,
            String parameters) throws IOException {
        return;
    }

    private static class SingletonDefaultInjectionAttackHandler {
        private static final InjectionAttackPassHandle INSTANCE = new InjectionAttackPassHandle();
    }


}
