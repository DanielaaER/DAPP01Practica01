/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dapp01practica01;

import java.sql.Connection;
import java.util.List;

/**
 *
 * @author Danielaa
 */
public abstract class SelectionDB<T> {

    public abstract List<T> buscar(Connection con);

}
