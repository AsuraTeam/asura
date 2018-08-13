/**
 * Copyright(c) 2018 asura
 */
package com.asura.framework.rabbitmq;

/**
 * <p></p>
 *
 *
 * @author liusq23
 * @since 1.0
 * @version 1.0
 * @Date 2018/3/27 下午9:34
 */
public class RabbitMqConfig {

    private String host;
    private Integer port;
    private String userName;
    private String password;
    private int autoRecoveryEnable;
    private String environment;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public int getAutoRecoveryEnable() {
        return autoRecoveryEnable;
    }

    public void setAutoRecoveryEnable(int autoRecoveryEnable) {
        this.autoRecoveryEnable = autoRecoveryEnable;
    }
}
