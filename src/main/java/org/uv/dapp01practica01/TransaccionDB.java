package org.uv.dapp01practica01;

import java.sql.Connection;

public abstract class TransaccionDB<T> {
    protected T pojo;
    protected TransaccionDB(T pojo){
        this.pojo=pojo;
    }
    public abstract boolean execute(Connection con);
    
}
